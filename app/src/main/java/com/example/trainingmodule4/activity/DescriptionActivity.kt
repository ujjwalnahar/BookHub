package com.example.trainingmodule4.activity

import android.content.Context
import android.os.AsyncTask
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.appcompat.widget.Toolbar
import androidx.core.content.ContextCompat
import androidx.room.Room
import androidx.room.RoomDatabase
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.example.trainingmodule4.R
import com.example.trainingmodule4.database.BookDatabase
import com.example.trainingmodule4.database.BookEntity
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_description.view.*
import kotlinx.android.synthetic.main.recycler_dashboard_single_row.*
import org.json.JSONObject
import java.lang.Exception
import java.security.AccessControlContext

class DescriptionActivity : AppCompatActivity() {
    lateinit var txtbookName: TextView
    lateinit var txtbookAuthor: TextView
    lateinit var txtbookPrice: TextView
    lateinit var txtbookRating: TextView
    lateinit var imgbookImage: ImageView
    lateinit var txtbookDesc: TextView
    lateinit var btnAddtofavourite: Button
    lateinit var progressLayout: RelativeLayout
    lateinit var progressbar: ProgressBar
    lateinit var toolbar: Toolbar

    var bookId: String? = "100"


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_description)
        txtbookName = findViewById(R.id.SampleText1Des)
        txtbookAuthor = findViewById(R.id.SampleText2Des)
        txtbookPrice = findViewById(R.id.price2Description)
        txtbookRating = findViewById(R.id.ratingDescription)
        imgbookImage = findViewById(R.id.img_book_description)
        txtbookDesc = findViewById(R.id.txtbookDescription)
        btnAddtofavourite = findViewById(R.id.btn_addtofavourites)
        progressLayout = findViewById(R.id.progress_layout)
        progressbar = findViewById(R.id.Progressbar)
        progressbar.visibility = View.VISIBLE
        progressLayout.visibility = View.VISIBLE
        toolbar = findViewById(R.id.toolbar_description)
        setSupportActionBar(toolbar)
        supportActionBar?.title = "Book Details"

        if (intent != null) {
            bookId = intent.getStringExtra("book_id")

        } else {
            finish()
            Toast.makeText(this@DescriptionActivity, "Some error occured", Toast.LENGTH_LONG).show()

        }
        if (bookId == "100") {
            finish()
            Toast.makeText(this@DescriptionActivity, "Some error occured", Toast.LENGTH_LONG).show()
        }

        val queue = Volley.newRequestQueue(this@DescriptionActivity)
        val url = "http://13.235.250.119/v1/book/get_book/"
        val jsonParams = JSONObject()
        jsonParams.put("book_id", bookId)
        val jsonRequest =
            object : JsonObjectRequest(Request.Method.POST, url, jsonParams, Response.Listener {

                try {
                    val success = it.getBoolean("success")
                    if (success) {
                        val bookJsonObject = it.getJSONObject("book_data")
                        progressLayout.visibility = View.GONE
                        val bookImageURL = bookJsonObject.getString(("image"))
                        Picasso.get().load(bookJsonObject.getString("image"))
                            .error(R.drawable.bookpng).into(imgbookImage)
                        txtbookName.text = bookJsonObject.getString("name")
                        txtbookAuthor.text = bookJsonObject.getString("author")
                        txtbookPrice.text = bookJsonObject.getString("price")
                        txtbookRating.text = bookJsonObject.getString("rating")
                        txtbookDesc.text = bookJsonObject.getString("description")
                        val bookEntity = BookEntity(
                            bookId?.toInt() as Int,
                            txtbookName.text.toString(),
                            txtbookAuthor.text.toString(),
                            txtbookPrice.text.toString(),
                            txtbookRating.text.toString(),
                            txtbookDesc.text.toString(),
                            bookImageURL

                        )
                        val checkFav = DBAsynctask(applicationContext, bookEntity, 1).execute()
                        val isFav = checkFav.get()
                        if (isFav) {
                            btnAddtofavourite.text = "Remove from Favourites"
                            val favColor = ContextCompat.getColor(
                                applicationContext,
                                R.color.colorNotFavourite
                            )
                            btnAddtofavourite.setBackgroundColor(favColor)
                        } else {
                            btnAddtofavourite.text = "Add to Favourites"
                            val favColor =
                                ContextCompat.getColor(applicationContext, R.color.Buttons)
                            btnAddtofavourite.setBackgroundColor(favColor)


                        }
                        btnAddtofavourite.setOnClickListener {
                            if (!DBAsynctask(applicationContext, bookEntity, 1).execute().get()) {
                                val async = DBAsynctask(applicationContext, bookEntity, 2).execute()
                                val result = async.get()
                                if (result) {
                                    Toast.makeText(
                                        this@DescriptionActivity,
                                        "Book Added to the favourites",
                                        Toast.LENGTH_LONG
                                    ).show()
                                    btnAddtofavourite.text = "Remove from Favourites"
                                    val favColor = ContextCompat.getColor(
                                        applicationContext,
                                        R.color.colorNotFavourite
                                    )
                                    btnAddtofavourite.setBackgroundColor(favColor)
                                } else {
                                    Toast.makeText(
                                        this@DescriptionActivity,
                                        "Some Error Occured",
                                        Toast.LENGTH_LONG
                                    ).show()
                                }


                            } else {
                                val async = DBAsynctask(applicationContext, bookEntity, 3).execute()
                                val result = async.get()
                                if (result) {
                                    Toast.makeText(
                                        this@DescriptionActivity,
                                        "Book Removed  from the favourites",
                                        Toast.LENGTH_LONG
                                    ).show()
                                    btnAddtofavourite.text = "Add to Favourites"
                                    val favColor = ContextCompat.getColor(
                                        applicationContext,
                                        R.color.Buttons
                                    )
                                    btnAddtofavourite.setBackgroundColor(favColor)

                                }
                                else{
                                    Toast.makeText(
                                        this@DescriptionActivity,
                                        "Some Error Occured",
                                        Toast.LENGTH_LONG
                                    ).show()

                                }

                            }
                        }

                    } else {
                        Toast.makeText(
                            this@DescriptionActivity,
                            "Some Error Occured",
                            Toast.LENGTH_LONG
                        ).show()
                    }

                } catch (e: Exception) {
                    Toast.makeText(
                        this@DescriptionActivity,
                        "Some error Occured",
                        Toast.LENGTH_LONG
                    ).show()

                }
            }, Response.ErrorListener {
                Toast.makeText(this@DescriptionActivity, "Volley Error occured", Toast.LENGTH_LONG)
                    .show()
            }) {
                override fun getHeaders(): MutableMap<String, String> {
                    val headers = HashMap<String, String>()
                    headers["Content-type"] = "application/json"
                    headers["token"] = "0a7e1e14c907fd"
                    return headers
                }

            }
        queue.add(jsonRequest)
    }

    class DBAsynctask(val context: Context, val bookEntity: BookEntity, val mode: Int) :
        AsyncTask<Void, Void, Boolean>() {
        val db = Room.databaseBuilder(context, BookDatabase::class.java, "Book_db").build()
        override fun doInBackground(vararg params: Void?): Boolean {
            when (mode) {
                1 -> {
                    val book: BookEntity? = db.bookDao().getBookById(bookEntity.book_id.toString())
                    db.close()
                    return book != null

                }

                2 -> {
                    db.bookDao().insertBook(bookEntity)
                    db.close()


                    return true

                }


                3 -> {
                    db.bookDao().deleteBook(bookEntity)
                    db.close()
                    return true
                }
            }
        return false}


    }
}

package com.example.trainingmodule4.fragement


import android.content.Context
import android.os.AsyncTask
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.RelativeLayout
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.room.Room
import com.example.trainingmodule4.R
import com.example.trainingmodule4.adapter.Recycler_favourite_adapter
import com.example.trainingmodule4.database.BookDatabase
import com.example.trainingmodule4.database.BookEntity

class Favourites : Fragment() {

    lateinit var recyclerFavourite: RecyclerView
    lateinit var progresslayout: RelativeLayout
    lateinit var progressBar: ProgressBar
    lateinit var layoutmanager: RecyclerView.LayoutManager
    lateinit var recyclerAdapter: Recycler_favourite_adapter
    var favBookList = listOf<BookEntity>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_favourites, container, false)
        // Inflate the layout for this fragment
        recyclerFavourite = view.findViewById(R.id.recyclerFavourite)
        progresslayout = view.findViewById(R.id.progresslayout1)
        progressBar = view.findViewById(R.id.progressBar1)
        layoutmanager = GridLayoutManager(activity as Context, 2)
        favBookList = RetrieveFavourites(activity as Context).execute().get()
        if (activity != null) {
            progresslayout.visibility = View.GONE
            recyclerAdapter = Recycler_favourite_adapter(activity as Context, favBookList)
            recyclerFavourite.adapter = recyclerAdapter
            recyclerFavourite.layoutManager = layoutmanager
        }
        return view

    }

    class RetrieveFavourites(val context: Context) : AsyncTask<Void, Void, List<BookEntity>>() {
        override fun doInBackground(vararg params: Void?): List<BookEntity> {
            val db = Room.databaseBuilder(context, BookDatabase::class.java, "Book_db").build()
            return db.bookDao().getAllBooks()
        }


    }


}

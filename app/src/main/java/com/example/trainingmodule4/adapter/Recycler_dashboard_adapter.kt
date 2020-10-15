package com.example.trainingmodule4.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.trainingmodule4.R
import com.example.trainingmodule4.activity.DescriptionActivity
import com.example.trainingmodule4.model.Book
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.recycler_dashboard_single_row.*

class Recycler_dashboard_adapter(val context:Context,val itemList: ArrayList<Book>) : RecyclerView.Adapter<Recycler_dashboard_adapter.DashboardViewHolder>() {
    class DashboardViewHolder(view:View): RecyclerView.ViewHolder(view){
        val bookName:TextView=view.findViewById(R.id.SampleText1)
        val bookAuthor:TextView=view.findViewById(R.id.SampleText2)
        val bookPrice:TextView=view.findViewById(R.id.price2)
        val bookRating:TextView=view.findViewById(R.id.rating)
        val bookImage:ImageView=view.findViewById(R.id.img_recycler_dashboard)
        val book_parent:RelativeLayout=view.findViewById(R.id.recycler_parent)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DashboardViewHolder {
        val view=LayoutInflater.from(parent.context).inflate(R.layout.recycler_dashboard_single_row,parent,false)
        return DashboardViewHolder(view)
    }

    override fun getItemCount(): Int {
      return itemList.size
    }

    override fun onBindViewHolder(holder: DashboardViewHolder, position: Int) {
        val book=itemList[position]

        holder.bookName.text=book.bookName
        holder.bookAuthor.text=book.bookAuthor
        holder.bookPrice.text=book.bookPrice
        holder.bookRating.text=book.bookRating
        Picasso.get().load(book.bookImage).into(holder.bookImage);

        holder.book_parent.setOnClickListener{
            val intent = Intent(context, DescriptionActivity::class.java)
            intent.putExtra("book_id",book.bookId)
            context.startActivity(intent)

        }
    }
}
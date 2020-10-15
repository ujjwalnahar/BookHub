package com.example.trainingmodule4.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.trainingmodule4.R
import com.example.trainingmodule4.database.BookEntity
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.recycler_favourite_single_row.view.*

class Recycler_favourite_adapter(val context: Context, val bookList:List<BookEntity>):RecyclerView.Adapter<Recycler_favourite_adapter.FavouriteViewHOlder>() {




    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavouriteViewHOlder {
        val view=LayoutInflater.from(parent.context).inflate(R.layout.recycler_favourite_single_row,parent,false)
        return FavouriteViewHOlder(view)
    }

    override fun getItemCount(): Int {
        return bookList.size

    }

    override fun onBindViewHolder(holder: FavouriteViewHOlder, position: Int) {
        val book=bookList[position]
       Picasso.get().load(book.bookImage).error(R.drawable.bookpng).into(holder.imgBookImage)
        holder.txtBookName.text=book.bookName
        holder.txtBookAuthor.text=book.bookAuthor
        holder.txtBookPrice.text=book.bookPrice
        holder.txtBookRating.text=book.bookRating

    }
    class FavouriteViewHOlder(view:View):RecyclerView.ViewHolder(view){
        val txtBookName:TextView=view.findViewById(R.id.txt_bookName_recyclerFav)
        val txtBookAuthor:TextView=view.findViewById(R.id.txt_bookAuthor_recyclerFav)
        val txtBookPrice:TextView=view.findViewById(R.id.txt_bookPrice_recyclerFav)
        val txtBookRating:TextView=view.findViewById(R.id.txt_bookRating_recyclerFav)
        val imgBookImage:ImageView=view.findViewById(R.id.img_recyclerFav)
        val llContent:LinearLayout=view.findViewById(R.id.llFavContent)

    }
}
package com.example.trainingmodule4.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Books")
data class BookEntity(
    @PrimaryKey val book_id : Int,
    @ColumnInfo("bookName") val bookName : String,
    @ColumnInfo("bookAuthor") val bookAuthor : String,
    @ColumnInfo("bookPrice") val bookPrice : String,
    @ColumnInfo("bookRating") val bookRating : String,
    @ColumnInfo("bookDesc") val bookDesc : String,
    @ColumnInfo("bookImage") val bookImage: String
)


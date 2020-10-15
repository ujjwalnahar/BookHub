package com.example.trainingmodule4.database
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query

@Dao
interface bookDao {
    @Insert
    fun insertBook(bookEntity: BookEntity)

    @Delete
    fun deleteBook(bookEntity: BookEntity)

    @Query("SELECT * FROM Books")
    fun getAllBooks():List<BookEntity>

    @Query("SELECT * FROM Books WHERE book_id=:bookId")
    fun getBookById(bookId: String): BookEntity
}
package com.andzhaev.readerticket.ui.books

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.andzhaev.readerticket.R
import com.andzhaev.readerticket.domain.model.Book
/**
Адаптер для книг когда нажимаем учебники / жанры
 */
class BookAdapter(private val bookList: List<Book>) :
    RecyclerView.Adapter<BookAdapter.BookViewHolder>() {

    class BookViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val title: TextView = view.findViewById(R.id.tv_title_book)
        val author: TextView = view.findViewById(R.id.tv_author_book)
        val genre: TextView = view.findViewById(R.id.tv_genre_book)
        val count: TextView = view.findViewById(R.id.tv_count_book)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BookViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_books_info, parent, false)
        return BookViewHolder(view)
    }

    override fun onBindViewHolder(holder: BookViewHolder, position: Int) {
        val book = bookList[position]
        holder.title.text = book.title
        holder.author.text = book.author
        holder.count.text = book.count.toString()
        holder.genre.text = book.genre

        holder.itemView.setOnClickListener {
            val context = holder.itemView.context
            val fragment = DetailBookFragment()
            val bundle = Bundle()
            bundle.putSerializable("book", book)
            fragment.arguments = bundle

            (context as AppCompatActivity).supportFragmentManager.beginTransaction()
                .replace(R.id.main_container, fragment)
                .addToBackStack(null)
                .commit()
        }
    }

    override fun getItemCount() = bookList.size
}
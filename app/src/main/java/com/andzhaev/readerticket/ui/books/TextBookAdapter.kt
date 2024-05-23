package com.andzhaev.readerticket.ui.books

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.recyclerview.widget.RecyclerView
import com.andzhaev.readerticket.R
import com.andzhaev.readerticket.data.network.ApiFactory.apiService
import com.andzhaev.readerticket.domain.model.TextBook
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class TextBookAdapter(private val dataSet: List<TextBook>) :
    RecyclerView.Adapter<TextBookAdapter.TextBookViewHolder>() {

    class TextBookViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val button: Button = view.findViewById(R.id.btTextClassBook)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TextBookViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_book_info, parent, false)

        return TextBookViewHolder(view)
    }

    override fun onBindViewHolder(holder: TextBookViewHolder, position: Int) {
        val textBook = dataSet[position]
        holder.button.text = textBook.title
        holder.button.setOnClickListener {
            // При нажатии на кнопку делаем запрос к серверу
            CoroutineScope(Dispatchers.Main).launch {
                try {
                    val books = apiService.getBooksByTextBooks(textBook.grade.toLong())
                    // Обработка полученных книг
                    // Например, можно обновить UI с полученными книгами
                    Log.d("TextBookAdapter", "Books received: $books")
                } catch (e: Exception) {
                    // Обработка ошибок
                    Log.e("TextBookAdapter", "Error fetching books: ${e.message}")
                }
            }
        }
    }

    override fun getItemCount() = dataSet.size
}
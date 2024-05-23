package com.andzhaev.readerticket.ui.books

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.andzhaev.readerticket.R
import com.andzhaev.readerticket.data.network.ApiFactory.apiService
import com.andzhaev.readerticket.data.network.ApiService
import com.andzhaev.readerticket.domain.model.TextBook
import com.andzhaev.readerticket.ui.ListBooksFragment
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
            CoroutineScope(Dispatchers.Main).launch {
                try {
                    Log.d("TextBookAdapter", "Fetching books for grade: ${textBook.grade.toLong()}")
                    val books = apiService.getBooksByTextBooks(textBook.grade.toLong())
                    Log.d("TextBookAdapter", "Books received: $books")
                    // Преобразовать список книг в список строк перед передачей через Bundle
                    val bookTitles = books.map { it.title }
                    // Передача списка книг в ListGenreOrBookInfoFragment через Bundle
                    val fragment = ListGenreOrBookInfoFragment()
                    val bundle = Bundle()
                    bundle.putSerializable("books", ArrayList(books))
                    fragment.arguments = bundle
// Замена текущего фрагмента на ListGenreOrBookInfoFragment
                    (holder.itemView.context as AppCompatActivity).supportFragmentManager.beginTransaction()
                        .replace(R.id.main_container, fragment)
                        .addToBackStack(null)
                        .commit()
                } catch (e: Exception) {
                    Log.e("TextBookAdapter", "Error fetching books: ${e.message}")
                }
            }
        }
    }


    override fun getItemCount() = dataSet.size
}
package com.andzhaev.readerticket.ui.books

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.andzhaev.readerticket.R
import com.andzhaev.readerticket.data.network.ApiFactory
import com.andzhaev.readerticket.domain.model.Genre
import com.squareup.picasso.Picasso
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class TextGenreAdapter(private val dataSet: List<Genre>) :
    RecyclerView.Adapter<TextGenreAdapter.TextGenreViewHolder>() {

    class TextGenreViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val imageViewGenreInfo: ImageView = view.findViewById(R.id.image_info_genre)
        val textGenre: TextView = view.findViewById(R.id.tvGenre)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TextGenreViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_janr_info, parent, false)

        return TextGenreViewHolder(view)
    }

    override fun onBindViewHolder(holder: TextGenreViewHolder, position: Int) {
        val textBook = dataSet[position]
        // картинка
        holder.textGenre.text = textBook.genre
        holder.imageViewGenreInfo.setOnClickListener {
            CoroutineScope(Dispatchers.Main).launch {
                try {
                    Log.d("TextGenreAdapter", "Fetching books for grade: ${textBook.genre}")
                    val books = ApiFactory.apiService.getBooksByGenre(textBook.genre)
                    Log.d("TextGenreAdapter", "Books received: $books")
                    // Преобразовать список книг в список строк перед передачей через Bundle
                    val bookTitles = books.map { it.title }
                    // Передача списка книг в ListGenreOrBookInfoFragment через Bundle
                    val fragment = ListGenreOrBookInfoFragment()
                    val bundle = Bundle()
                    bundle.putSerializable("books", ArrayList(books))
                    fragment.arguments = bundle
                    (holder.itemView.context as AppCompatActivity).supportFragmentManager.beginTransaction()
                        .replace(R.id.main_container, fragment)
                        .addToBackStack(null)
                        .commit()
                } catch (e: Exception) {
                    Log.e("TextGenreAdapter", "Error fetching books: ${e.message}")
                }
            }
        }
    }

    override fun getItemCount() = dataSet.size
}
package com.andzhaev.readerticket.ui.books

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.andzhaev.readerticket.R
import com.andzhaev.readerticket.domain.model.Genre
import com.andzhaev.readerticket.domain.model.TextBook

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
        holder.textGenre.text = textBook.genre
        holder.imageViewGenreInfo.setOnClickListener {
            // Действие при нажатии на кнопку
            // Например, можно использовать textBook.grade
        }
    }

    override fun getItemCount() = dataSet.size
}
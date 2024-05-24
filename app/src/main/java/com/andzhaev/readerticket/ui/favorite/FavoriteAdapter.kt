package com.andzhaev.readerticket.ui.favorite

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.andzhaev.readerticket.R
import com.andzhaev.readerticket.domain.model.Favorite
import com.andzhaev.readerticket.ui.books.DetailBookFragment

class FavoriteAdapter(private val favorites: List<Favorite>) :
    RecyclerView.Adapter<FavoriteAdapter.FavoriteViewHolder>() {

    class FavoriteViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val title: TextView = itemView.findViewById(R.id.tv_title_book)
        val author: TextView = itemView.findViewById(R.id.tv_author_book)
        val genre: TextView = itemView.findViewById(R.id.tv_genre_book)
        val count: TextView = itemView.findViewById(R.id.tv_count_book)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoriteViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_books_info, parent, false)
        return FavoriteViewHolder(view)
    }

    override fun onBindViewHolder(holder: FavoriteViewHolder, position: Int) {
        val favorite = favorites[position]
        holder.title.text = favorite.title
        holder.author.text = favorite.author
        holder.count.text = favorite.count.toString()
        holder.genre.text = favorite.genre

        holder.itemView.setOnClickListener {
            val context = holder.itemView.context
            val fragment = DetailBookFragment()
            val bundle = Bundle()
            bundle.putSerializable("book", favorite)
            fragment.arguments = bundle

            (context as AppCompatActivity).supportFragmentManager.beginTransaction()
                .replace(R.id.main_container, fragment)
                .addToBackStack(null)
                .commit()
        }
    }

    override fun getItemCount() = favorites.size
}
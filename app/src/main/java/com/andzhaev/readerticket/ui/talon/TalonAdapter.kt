package com.andzhaev.readerticket.ui.talon

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.andzhaev.readerticket.R
import com.andzhaev.readerticket.data.network.ApiFactory.apiService
import com.andzhaev.readerticket.data.network.ApiService
import com.andzhaev.readerticket.domain.model.Talon
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class TalonAdapter(private var talons: MutableList<Talon>, private val apiService: ApiService) : RecyclerView.Adapter<TalonAdapter.TalonViewHolder>() {

    inner class TalonViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val talonNumberBook: TextView = itemView.findViewById(R.id.tvTalonNumberBook)
        val talonTitleBook: TextView = itemView.findViewById(R.id.tvTalonTitleBook)
        val talonAuthorBook: TextView = itemView.findViewById(R.id.tvTalonAuthorBook)
        val talonGenreBook: TextView = itemView.findViewById(R.id.tvTalonGenreBook)
        val talonDate: TextView = itemView.findViewById(R.id.tvTalonDate)
        val button: Button = itemView.findViewById(R.id.btDeleteTalon)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TalonViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.item_talons_info, parent, false)
        return TalonViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: TalonViewHolder, position: Int) {
        val talon = talons[position]
        holder.talonNumberBook.text = talon.number.toString()
        holder.talonTitleBook.text = talon.title
        holder.talonAuthorBook.text = talon.author
        holder.talonGenreBook.text = talon.genre
        holder.talonDate.text = talon.date

        holder.button.setOnClickListener {
            Log.d("TalonsAdapter", "Button clicked for talon number: ${talon.number}")
            deleteTalon(talon.number, position)
        }
    }

    private fun deleteTalon(number: Long, position: Int) {
        apiService.deleteTalon(number).enqueue(object : Callback<Void> {
            override fun onResponse(call: Call<Void>, response: Response<Void>) {
                if (response.isSuccessful) {
                    Log.d("TalonsAdapter", "Successfully deleted talon with number: $number")
                    talons.removeAt(position)
                    notifyItemRemoved(position)
                } else {
                    Log.e("TalonsAdapter", "Failed to delete talon with number: $number, response code: ${response.code()}")
                }
            }

            override fun onFailure(call: Call<Void>, t: Throwable) {
                Log.e("TalonsAdapter", "Failed to delete talon with number: $number", t)
            }
        })
    }

    override fun getItemCount() = talons.size

    fun updateTalons(newTalons: List<Talon>) {
        talons.clear()
        talons.addAll(newTalons)
        notifyDataSetChanged()
    }
}
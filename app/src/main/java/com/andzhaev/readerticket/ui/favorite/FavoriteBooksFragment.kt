package com.andzhaev.readerticket.ui.favorite

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.andzhaev.readerticket.data.network.ApiFactory
import com.andzhaev.readerticket.databinding.FragmentFavoriteBooksBinding
import com.andzhaev.readerticket.domain.model.Book
import com.andzhaev.readerticket.domain.model.Favorite
import com.andzhaev.readerticket.ui.books.BookAdapter
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class FavoriteBooksFragment : Fragment() {
    private var _binding: FragmentFavoriteBooksBinding? = null
    private val binding: FragmentFavoriteBooksBinding
        get() = _binding ?: throw RuntimeException(FRAGMENT_ERROR)

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFavoriteBooksBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val booksSerializable = arguments?.getSerializable("books")
        val books: List<Book> = if (booksSerializable is List<*>) {
            booksSerializable.filterIsInstance<Book>()
        } else {
            emptyList()
        }
        Log.d("ListGenreOrBookInfoFragment", "Books received: $books") // Добавляем лог
        binding.rvLike.layoutManager = LinearLayoutManager(context)
        binding.rvLike.adapter = FavoriteAdapter(emptyList())

        fetchFavorites()
    }

    private fun fetchFavorites() {
        val apiService = ApiFactory.apiService
        apiService.getAllFavorites().enqueue(object : Callback<List<Favorite>> {
            override fun onResponse(call: Call<List<Favorite>>, response: Response<List<Favorite>>) {
                if (response.isSuccessful) {
                    val favorites = response.body() ?: emptyList()
                    binding.rvLike.adapter = FavoriteAdapter(favorites)
                } else {
                    // Handle the error
                }
            }

            override fun onFailure(call: Call<List<Favorite>>, t: Throwable) {
                // Handle the failure
            }
        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        private const val FRAGMENT_ERROR = "FragmentFavoriteBooksBinding is null"
        fun newInstance(): Fragment {
            return FavoriteBooksFragment()
        }
    }
}
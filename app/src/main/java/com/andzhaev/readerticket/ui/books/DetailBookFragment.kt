package com.andzhaev.readerticket.ui.books

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.andzhaev.readerticket.R
import com.andzhaev.readerticket.data.network.ApiFactory
import com.andzhaev.readerticket.data.network.ApiService
import com.andzhaev.readerticket.databinding.FragmentDetailBookBinding
import com.andzhaev.readerticket.databinding.FragmentProfileBinding
import com.andzhaev.readerticket.domain.model.Book
import com.andzhaev.readerticket.domain.model.Favorite
import com.andzhaev.readerticket.ui.talon.CreateTalonFragment
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class DetailBookFragment : Fragment() {

    private var _binding: FragmentDetailBookBinding? = null
    private val binding: FragmentDetailBookBinding
        get() = _binding ?: throw RuntimeException(FRAGMENT_ERROR)

    private lateinit var apiService: ApiService

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDetailBookBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        apiService = ApiFactory.apiService

        val book = arguments?.getSerializable("book") as? Book
        if (book != null) {
            binding.tvTitleBook.text = book.title
            binding.tvAuthorBook.text = book.author
            binding.tvDescriptionBook.text = book.description
            binding.tvGenreBook.text = book.genre
            binding.tvCountBook.text = book.count.toString()

            binding.imageViewFavorite.setOnClickListener {
                saveFavorite(book)
            }
        }
        binding.imageViewShare.setOnClickListener {
            val shareIntent = Intent().apply {
                action = Intent.ACTION_SEND
                type = "text/plain"
                putExtra(Intent.EXTRA_TEXT, "Здесь ваш текст для поделиться: ${binding.tvTitleBook.text}, ${ binding.tvAuthorBook.text}, ${ binding.tvDescriptionBook.text}, ${binding.tvGenreBook.text}, ${ binding.tvCountBook.text}")
            }
            val chooser = Intent.createChooser(shareIntent, "Поделиться через")
            if (shareIntent.resolveActivity(requireContext().packageManager) != null) {
                startActivity(chooser)
            } else {
                Toast.makeText(context, "Нет приложений для поделиться", Toast.LENGTH_SHORT).show()
            }
        }
        binding.btBronBook.setOnClickListener {
            // Создание фрагмента CreateTalonFragment
            val bundle = Bundle().apply {
                putString("bookTitle", binding.tvTitleBook.text.toString())
                putString("bookAuthor", binding.tvAuthorBook.text.toString())
                putString("bookGenre", binding.tvGenreBook.text.toString())
            }

            val createTalonFragment = CreateTalonFragment().apply {
                arguments = bundle
            }

            requireActivity().supportFragmentManager.beginTransaction()
                .setCustomAnimations(
                    R.anim.slide_in_right,
                    R.anim.slide_out_left,
                    R.anim.slide_in_left,
                    R.anim.slide_out_right
                )
                .replace(R.id.main_container, createTalonFragment)
                .addToBackStack(null)
                .commit()
        }

    }

    private fun saveFavorite(book: Book) {
        val favorite = Favorite(
            textBooks = book.textBooks,
            title = book.title,
            author = book.author,
            genre = book.genre,
            description = book.description,
            count = book.count
        )

        val call = apiService.saveFavorite(favorite)
        call.enqueue(object : Callback<Void> {
            override fun onResponse(call: Call<Void>, response: Response<Void>) {
                if (response.isSuccessful) {
                    Toast.makeText(context, "Добавлено в закладки", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(context, "Не добавлено в закладки", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<Void>, t: Throwable) {
                Toast.makeText(context, "An error occurred: ${t.message}", Toast.LENGTH_SHORT).show()
            }
        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        private const val FRAGMENT_ERROR = "DetailBookFragment is null"

        fun newInstance(): Fragment {
            return DetailBookFragment()
        }
    }
}

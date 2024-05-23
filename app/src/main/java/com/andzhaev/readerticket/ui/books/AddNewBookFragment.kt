package com.andzhaev.readerticket.ui.books

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.andzhaev.readerticket.R
import com.andzhaev.readerticket.databinding.FragmentAddNewBookBinding
import com.andzhaev.readerticket.databinding.FragmentFavoriteBooksBinding
import com.andzhaev.readerticket.domain.model.Book
import com.andzhaev.readerticket.ui.ListBooksFragment

class AddNewBookFragment : Fragment() {

    private var _binding: FragmentAddNewBookBinding? = null
    private val binding: FragmentAddNewBookBinding
        get() = _binding ?: throw RuntimeException(FRAGMENT_ERROR)

    private lateinit var viewModel: AddNewBookViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAddNewBookBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this).get(AddNewBookViewModel::class.java)

        // Создание и установка адаптеров для class_books, genre_books и count_books
        setupAdapters()

        binding.btAddBook.setOnClickListener {
            val title = binding.etTitleBook.text.toString()
            val author = binding.etAuthorLibrary.text.toString()
            val description = binding.etDescriptionBook.text.toString()
            val count = binding.etCountBook.text.toString().toIntOrNull()
            val textBooks = binding.etClassBook.text.toString().toLongOrNull()
            val genre = binding.etGenreBook.text.toString()

            Log.d("AddNewBookFragment", "Title: $title")
            Log.d("AddNewBookFragment", "Author: $author")
            Log.d("AddNewBookFragment", "Description: $description")
            Log.d("AddNewBookFragment", "Count: $count")
            Log.d("AddNewBookFragment", "TextBooks: $textBooks")
            Log.d("AddNewBookFragment", "Genre: $genre")

            if (title.isNotBlank() && author.isNotBlank() && description.isNotBlank() && count != null && textBooks != null) {
                val book = Book(
                    id = null,
                    textBooks = textBooks,
                    title = title,
                    author = author,
                    genre = genre,
                    description = description,
                    count = count
                )
                viewModel.saveBook(book)
            } else {
                Toast.makeText(context, "Пожалуйста, заполните все поля", Toast.LENGTH_SHORT).show()
            }
        }

        viewModel.saveBookResult.observe(viewLifecycleOwner) { result ->
            result.onSuccess {
                launchListBooksFragment()
            }
            result.onFailure { exception ->
                Log.e("AddNewBookFragment", "Failed to save book", exception)
                Toast.makeText(context, "Не удалось сохранить книгу: ${exception.message}", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun setupAdapters() {
        // Adapter for class_books
        val classBooksAdapter = ArrayAdapter.createFromResource(
            requireContext(),
            R.array.class_books,
            android.R.layout.simple_dropdown_item_1line
        )
        binding.etClassBook.setAdapter(classBooksAdapter)

        // Adapter for genre_books
        val genreBooksAdapter = ArrayAdapter.createFromResource(
            requireContext(),
            R.array.genre_books,
            android.R.layout.simple_dropdown_item_1line
        )
        binding.etGenreBook.setAdapter(genreBooksAdapter)

        // Adapter for count_books (using class_books for simplicity)
        val countBooksAdapter = ArrayAdapter.createFromResource(
            requireContext(),
            R.array.class_books,
            android.R.layout.simple_dropdown_item_1line
        )
        binding.etCountBook.setAdapter(countBooksAdapter)
    }

    private fun launchListBooksFragment() {
        requireActivity().supportFragmentManager.beginTransaction()
            .setCustomAnimations(
                R.anim.slide_in_right,
                R.anim.slide_out_left,
                R.anim.slide_in_left,
                R.anim.slide_out_right
            )
            .replace(R.id.main_container, ListBooksFragment.newInstance())
            .addToBackStack(null)
            .commit()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        private const val FRAGMENT_ERROR = "AddNewBookFragment is null"
        fun newInstance(): Fragment {
            return AddNewBookFragment()
        }
    }
}
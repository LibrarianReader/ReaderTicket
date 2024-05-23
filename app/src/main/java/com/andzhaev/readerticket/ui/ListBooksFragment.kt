package com.andzhaev.readerticket.ui

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.andzhaev.readerticket.data.network.ApiFactory
import com.andzhaev.readerticket.data.network.ApiFactory.apiService
import com.andzhaev.readerticket.databinding.FragmentListBooksBinding
import com.andzhaev.readerticket.domain.model.Genre
import com.andzhaev.readerticket.domain.model.TextBook
import com.andzhaev.readerticket.domain.repository.UserRepository
import com.andzhaev.readerticket.ui.books.TextBookAdapter
import com.andzhaev.readerticket.ui.books.TextGenreAdapter


class ListBooksFragment : Fragment() {

    private var _binding: FragmentListBooksBinding? = null
    private val binding: FragmentListBooksBinding
        get() = _binding ?: throw RuntimeException(FRAGMENT_ERROR)

    private val bookList = listOf(
        TextBook("Учебники для 1 класса", 1),
        TextBook("Учебники для 2 класса", 2),
        TextBook("Учебники для 3 класса", 3),
        TextBook("Учебники для 4 класса", 4),
        TextBook("Учебники для 5 класса", 5),
        TextBook("Учебники для 6 класса", 6),
        TextBook("Учебники для 7 класса", 7),
        TextBook("Учебники для 8 класса", 8),
        TextBook("Учебники для 9 класса", 9),
        TextBook("Учебники для 10 класса", 10),
        TextBook("Учебники для 11 класса", 11),
    )

    private val genreList = listOf(
        Genre("Фэнтези", 1),
        Genre("Приключения", 2),
        Genre("Боевик", 3),
        Genre("Ужасы", 4),
        Genre("Драма", 5),
        Genre("Комедия", 6),
        Genre("Детективы", 7),
        Genre("Научная фантастика", 8),
        Genre("Психология", 9),
        Genre("Биография", 10),
        Genre("Классика", 11),
        Genre("Детское", 12),
    )

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentListBooksBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupBookRecyclerView()
        setupGenreRecyclerView()
    }

    private fun setupBookRecyclerView() {
        val adapter = TextBookAdapter(bookList)
        binding.rvBookList.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        binding.rvBookList.adapter = adapter
    }

    private fun setupGenreRecyclerView() {
        val adapter = TextGenreAdapter(genreList)
        binding.rvJanrList.adapter = adapter
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        private const val FRAGMENT_ERROR = "ListBooksFragment is null"
        fun newInstance(): Fragment {
            return ListBooksFragment()
        }
    }
}

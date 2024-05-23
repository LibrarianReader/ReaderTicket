package com.andzhaev.readerticket.ui.books

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.andzhaev.readerticket.databinding.FragmentListGenreOrBookInfoBinding
import com.andzhaev.readerticket.domain.model.Book


class ListGenreOrBookInfoFragment : Fragment() {

    private var _binding: FragmentListGenreOrBookInfoBinding? = null
    private val binding: FragmentListGenreOrBookInfoBinding
        get() = _binding ?: throw RuntimeException(FRAGMENT_ERROR)

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentListGenreOrBookInfoBinding.inflate(inflater, container, false)
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
        val adapter = BookAdapter(books)
        binding.rvJanrOrGenreList.layoutManager = LinearLayoutManager(requireContext())
        binding.rvJanrOrGenreList.adapter = adapter
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        private const val FRAGMENT_ERROR = "Home is null"
        fun newInstance(): Fragment {
            return ListGenreOrBookInfoFragment()
        }
    }
}
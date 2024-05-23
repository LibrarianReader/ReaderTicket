package com.andzhaev.readerticket.ui.books

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.andzhaev.readerticket.R
import com.andzhaev.readerticket.databinding.FragmentDetailBookBinding
import com.andzhaev.readerticket.databinding.FragmentProfileBinding
import com.andzhaev.readerticket.domain.model.Book


class DetailBookFragment : Fragment() {

    private var _binding: FragmentDetailBookBinding? = null
    private val binding: FragmentDetailBookBinding
        get() = _binding ?: throw RuntimeException(FRAGMENT_ERROR)

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDetailBookBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val book = arguments?.getSerializable("book") as? Book
        if (book != null) {
            binding.tvTitleDetailBook.text = book.title
            binding.tvAuthorDetailBook.text = book.author
            binding.tvDescriptionDetailBook.text = book.description
            binding.tvGenreDetailBook.text = book.genre
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        private const val FRAGMENT_ERROR = "DetailBookFragment is null"
    }
}
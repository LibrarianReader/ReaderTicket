package com.andzhaev.readerticket.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.andzhaev.readerticket.R
import com.andzhaev.readerticket.databinding.FragmentListBooksBinding


class ListBooksFragment : Fragment() {

    private var _binding: FragmentListBooksBinding? = null
    private val binding: FragmentListBooksBinding
        get() = _binding ?: throw RuntimeException(FRAGMENT_ERROR)


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentListBooksBinding.inflate(inflater, container, false)
        return binding.root
    }

    companion object {
        private const val FRAGMENT_ERROR = "ListBooksFragment is null"
        fun newInstance(): Fragment {
            return ListBooksFragment()
        }
    }
}
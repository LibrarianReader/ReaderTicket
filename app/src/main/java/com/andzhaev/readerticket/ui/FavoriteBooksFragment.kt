package com.andzhaev.readerticket.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.andzhaev.readerticket.databinding.FragmentFavoriteBooksBinding


class FavoriteBooksFragment : Fragment() {
    private var _binding: FragmentFavoriteBooksBinding? = null
    private val binding: FragmentFavoriteBooksBinding
        get() = _binding ?: throw RuntimeException(FRAGMENT_ERROR)



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFavoriteBooksBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
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
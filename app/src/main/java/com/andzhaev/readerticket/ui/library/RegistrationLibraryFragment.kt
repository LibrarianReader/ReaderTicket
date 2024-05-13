package com.andzhaev.readerticket.ui.library

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.andzhaev.readerticket.R
import com.andzhaev.readerticket.databinding.FragmentRegistrationLibraryBinding
import com.andzhaev.readerticket.ui.ListBooksFragment


class RegistrationLibraryFragment : Fragment() {
    private var _binding: FragmentRegistrationLibraryBinding? = null
    private val binding: FragmentRegistrationLibraryBinding
        get() = _binding ?: throw RuntimeException(FRAGMENT_ERROR)

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentRegistrationLibraryBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        showListBooksFragment()
    }

    // Главный экран
    private fun showListBooksFragment() {
        binding.btLoginRegAccount.setOnClickListener {
            launchListBooksFragment()
        }
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
            .commit()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        private const val FRAGMENT_ERROR = "RegistrationLibraryFragment is null"
        fun newInstance(): Fragment {
            return RegistrationLibraryFragment()
        }
    }

}
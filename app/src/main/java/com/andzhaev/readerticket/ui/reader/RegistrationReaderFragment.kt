package com.andzhaev.readerticket.ui.reader

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.andzhaev.readerticket.R
import com.andzhaev.readerticket.databinding.FragmentRegistrationReaderBinding
import com.andzhaev.readerticket.ui.ListBooksFragment
class RegistrationReaderFragment : Fragment() {
    private var _binding: FragmentRegistrationReaderBinding? = null
    private val binding: FragmentRegistrationReaderBinding
        get() = _binding ?: throw RuntimeException(FRAGMENT_ERROR)

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentRegistrationReaderBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        showListBooksFragment()
    }

    // Главный экран
    private fun showListBooksFragment() {
        binding.btRegAccount.setOnClickListener {
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
        private const val FRAGMENT_ERROR = "RegistrationReaderFragment is null"
        fun newInstance(): Fragment {
            return RegistrationReaderFragment()
        }
    }

}
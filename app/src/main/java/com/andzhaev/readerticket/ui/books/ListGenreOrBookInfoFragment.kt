package com.andzhaev.readerticket.ui.books

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.andzhaev.readerticket.R
import com.andzhaev.readerticket.databinding.FragmentHomeScreenBinding
import com.andzhaev.readerticket.databinding.FragmentListGenreOrBookInfoBinding
import com.andzhaev.readerticket.ui.HomeScreenFragment
import com.andzhaev.readerticket.ui.ListBooksFragment
import com.andzhaev.readerticket.ui.library.RegistrationLibraryFragment
import com.andzhaev.readerticket.ui.reader.RegistrationReaderFragment


class ListGenreOrBookInfoFragment : Fragment() {
    class HomeScreenFragment : Fragment() {
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
        }



      /*  //Экран регистрации библиотекаря
        private fun showRegistrationLibraryFragment() {
            binding.imageViewLibrary.setOnClickListener {
                launchRegistrationLibraryFragment()
            }
        }

        private fun launchRegistrationLibraryFragment() {
            requireActivity().supportFragmentManager.beginTransaction()
                .setCustomAnimations(
                    R.anim.slide_in_right,
                    R.anim.slide_out_left,
                    R.anim.slide_in_left,
                    R.anim.slide_out_right
                )
                .replace(R.id.main_container, RegistrationLibraryFragment.newInstance())
                .commit()
        }*/


        override fun onDestroyView() {
            super.onDestroyView()
            _binding = null
        }

        companion object {
            private const val FRAGMENT_ERROR = "Home is null"
            private const val REGISTRATION_USER_DATA_ERROR = "Change data"
            private const val REGISTRATION_EMPTY_DATA = "Not enough data"
            fun newInstance(): Fragment {
                return ListGenreOrBookInfoFragment()
            }
        }
    }
}
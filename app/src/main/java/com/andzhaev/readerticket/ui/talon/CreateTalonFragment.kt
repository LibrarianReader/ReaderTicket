package com.andzhaev.readerticket.ui.talon

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast
import com.andzhaev.readerticket.R
import com.andzhaev.readerticket.data.network.ApiFactory
import com.andzhaev.readerticket.databinding.FragmentCreateTalonBinding
import com.andzhaev.readerticket.domain.model.Talon
import com.andzhaev.readerticket.ui.ListBooksFragment
import com.andzhaev.readerticket.ui.books.DetailBookFragment
import com.andzhaev.readerticket.ui.profile.ProfileFragment
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class CreateTalonFragment : Fragment() {

    private var _binding: FragmentCreateTalonBinding? = null
    private val binding: FragmentCreateTalonBinding
        get() = _binding ?: throw RuntimeException(FRAGMENT_ERROR)

    private lateinit var dateArray: Array<String>
    private var selectedDate = ""


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCreateTalonBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val bookTitle = arguments?.getString("bookTitle") ?: "Убить пересмешника"
        val bookAuthor = arguments?.getString("bookAuthor") ?: "Харпер Ли"
        val bookGenre = arguments?.getString("bookGenre") ?: "Роман"

        val countBooksAdapter = ArrayAdapter.createFromResource(
            requireContext(),
            R.array.date_talon,
            android.R.layout.simple_dropdown_item_1line
        )
        binding.etDateTalon.setAdapter(countBooksAdapter)

        binding.btRegLibrary.setOnClickListener {
            val number = (100_000..999_999).random().toLong()
            val date = binding.etDateTalon.text.toString()

            val talon = Talon(title = bookTitle,
                genre = bookGenre,
                number = number,
                author = bookAuthor,
                date = date)

            saveTalon(talon)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun saveTalon(talon: Talon) {
        val apiService = ApiFactory.apiService
        apiService.saveTalon(talon).enqueue(object : Callback<Void> {
            override fun onResponse(call: Call<Void>, response: Response<Void>) {
                if (response.isSuccessful) {
                    Toast.makeText(requireContext(), "Талон успешно сохранен", Toast.LENGTH_SHORT).show()
                    requireActivity().supportFragmentManager.beginTransaction()
                        .setCustomAnimations(
                            R.anim.slide_in_right,
                            R.anim.slide_out_left,
                            R.anim.slide_in_left,
                            R.anim.slide_out_right
                        )
                        .replace(R.id.main_container, DetailBookFragment.newInstance())
                        .addToBackStack(null)
                        .commit()
                } else {
                    // Ошибка при сохранении талона
                    Toast.makeText(requireContext(), "Ошибка при сохранении талона", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<Void>, t: Throwable) {
                // Ошибка сети или сервера
                Toast.makeText(requireContext(), "Ошибка сети или сервера", Toast.LENGTH_SHORT).show()
            }
        })
    }

    companion object {
        private const val FRAGMENT_ERROR = "CreateTalonFragment is null"
    }
}

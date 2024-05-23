package com.andzhaev.readerticket.ui.library

import android.os.Bundle
import android.text.InputFilter
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.andzhaev.readerticket.R
import com.andzhaev.readerticket.data.network.ApiFactory
import com.andzhaev.readerticket.data.network.ApiService
import com.andzhaev.readerticket.databinding.FragmentRegistrationLibraryBinding
import com.andzhaev.readerticket.domain.model.User
import com.andzhaev.readerticket.domain.repository.UserRepository
import com.andzhaev.readerticket.ui.ListBooksFragment


class RegistrationLibraryFragment : Fragment() {
    private var _binding: FragmentRegistrationLibraryBinding? = null
    private val binding: FragmentRegistrationLibraryBinding
        get() = _binding ?: throw RuntimeException(FRAGMENT_ERROR)

    private lateinit var viewModel: RegistrationLibraryViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentRegistrationLibraryBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Инициализация ViewModel
        val userRepository = UserRepository(ApiFactory.apiService) // Предполагается, что UserRepository уже существует
        val factory = RegistrationLibraryViewModelFactory(userRepository)
        viewModel = ViewModelProvider(this, factory).get(RegistrationLibraryViewModel::class.java)

        setupInputFilters()
        showListBooksFragment()
    }

    private fun setupInputFilters() {
        // Устанавливаем фильтр, который позволяет вводить только цифры
        binding.etIdLibrary.filters = arrayOf(InputFilter { source, _, _, _, _, _ ->
            source.filter { it.isDigit() }
        })

        // Устанавливаем фильтры для других полей, если нужно
        binding.etLibraryPhone.filters = arrayOf(InputFilter { source, _, _, _, _, _ ->
            source.filter { it.isDigit() }
        })
    }

    private fun showListBooksFragment() {
        binding.btRegLibrary.setOnClickListener {
            registerUser()
        }
    }

    private fun registerUser() {
        val idUserStr = binding.etIdLibrary.text.toString().trim()
        val idUser: Long? = idUserStr.toLongOrNull()
        val name = binding.etNameLibrary.text.toString().trim()
        val surname = binding.etLibrarySurname.text.toString().trim()
        val email = binding.etLibraryEmail.text.toString().trim()
        val phone = binding.etLibraryPhone.text.toString().trim()
        val password = binding.etLibraryPassword.text.toString().trim()
        val repeatPassword = binding.etLibraryPasswordRepeat.text.toString().trim()

        var valid = true

        if (name.isEmpty()) {
            binding.tilNameLibrary.error = "Поле не может быть пустым"
            valid = false
        } else {
            binding.tilNameLibrary.error = null
        }

        if (surname.isEmpty()) {
            binding.tilSurnameLibrary.error = "Поле не может быть пустым"
            valid = false
        } else {
            binding.tilSurnameLibrary.error = null
        }

        if (email.isEmpty() || !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            binding.tilEmailLibrary.error = "Некорректный адрес электронной почты"
            valid = false
        } else {
            binding.tilEmailLibrary.error = null
        }

        if (phone.isEmpty() || phone.any { !it.isDigit() }) {
            binding.tilPhoneLibrary.error = "Телефон должен содержать только цифры"
            valid = false
        } else {
            binding.tilPhoneLibrary.error = null
        }

        if (idUser == null) {
            binding.tilIdLibrary.error = "ID должно быть числом"
            valid = false
        } else {
            binding.tilIdLibrary.error = null
        }

        if (password.length < 6) {
            binding.tilLibraryPassword.error = "Пароль должен содержать минимум 6 символов"
            valid = false
        } else {
            binding.tilLibraryPassword.error = null
        }

        if (password != repeatPassword) {
            binding.tilLibraryPasswordRepeat.error = "Пароли не совпадают"
            valid = false
        } else {
            binding.tilLibraryPasswordRepeat.error = null
        }

        if (valid) {
            val user = User(
                id_user = idUser ?: 0L, // Здесь idUser должен быть не null
                name = name,
                surname = surname,
                age = 0,  // age можно оставить как 0 или любое значение, поскольку он не будет сохраняться
                email = email,
                phone = phone,
                password = password
            )
            viewModel.registerUser(user)

            // Переход к следующему фрагменту
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




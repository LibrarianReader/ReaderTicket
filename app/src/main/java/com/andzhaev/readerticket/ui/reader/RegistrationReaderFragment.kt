package com.andzhaev.readerticket.ui.reader

import android.os.Bundle
import android.util.Patterns
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.andzhaev.readerticket.R
import com.andzhaev.readerticket.data.network.ApiFactory
import com.andzhaev.readerticket.databinding.FragmentRegistrationReaderBinding
import com.andzhaev.readerticket.domain.model.User
import com.andzhaev.readerticket.domain.repository.UserRepository
import com.andzhaev.readerticket.ui.ListBooksFragment
class RegistrationReaderFragment : Fragment() {

    private var _binding: FragmentRegistrationReaderBinding? = null
    private val binding get() = _binding!!

    private lateinit var registrationViewModel: RegistrationViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentRegistrationReaderBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val userRepository = UserRepository(ApiFactory.apiService)
        val viewModelFactory = RegistrationViewModelFactory(userRepository)
        registrationViewModel = ViewModelProvider(this, viewModelFactory).get(RegistrationViewModel::class.java)

        binding.btRegAccount.setOnClickListener {
            val idUser = binding.etIdUser.text.toString()
            val name = binding.etNameUser.text.toString()
            val surname = binding.etUserSurname.text.toString()
            val age = binding.etUserAge.text.toString()
            val email = binding.etUserEmail.text.toString()
            val phone = binding.etUserPhone.text.toString()
            val password = binding.etUserPassword.text.toString()
            val passwordRepeat = binding.etUserPasswordRepeat.text.toString()

            if (validateInputs(idUser, name, surname, age, email, phone, password, passwordRepeat)) {
                val user = User(
                    id_user = idUser.toLong(),
                    name = name,
                    surname = surname,
                    age = age.toInt(),
                    email = email,
                    phone = phone,
                    password = password
                )
                registrationViewModel.registerUser(user)
            }
        }

        registrationViewModel.registrationResult.observe(viewLifecycleOwner) { result ->
            result.fold(
                onSuccess = {
                    launchListBooksFragment()
                },
                onFailure = { error ->
                    Toast.makeText(requireContext(), "Registration failed: ${error.message}", Toast.LENGTH_SHORT).show()
                }
            )
        }
    }

    private fun validateInputs(idUser: String, name: String, surname: String, age: String, email: String, phone: String, password: String, passwordRepeat: String): Boolean {
        var isValid = true

        if (password.length < 6) {
            binding.tilUserPassword.error = "Пароль должен быть не менее 6 символов"
            isValid = false
        } else {
            binding.tilUserPassword.error = null
        }

        if (password != passwordRepeat) {
            binding.tilUserPasswordRepeat.error = "Пароли не совпадают"
            isValid = false
        } else {
            binding.tilUserPasswordRepeat.error = null
        }

        if (!age.all { it.isDigit() }) {
            binding.tilAgeUser.error = "Возраст должен содержать только цифры"
            isValid = false
        } else {
            binding.tilAgeUser.error = null
        }

        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            binding.tilEmailUser.error = "Введите корректный адрес электронной почты"
            isValid = false
        } else {
            binding.tilEmailUser.error = null
        }

        if (!phone.all { it.isDigit() }) {
            binding.tilPhoneUser.error = "Телефон должен содержать только цифры"
            isValid = false
        } else {
            binding.tilPhoneUser.error = null
        }

        if (idUser.isEmpty() || name.isEmpty() || surname.isEmpty()) {
            Toast.makeText(requireContext(), "Заполните все поля", Toast.LENGTH_SHORT).show()
            isValid = false
        }

        return isValid
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
        private const val FRAGMENT_ERROR = "RegistrationFragment is null"
        fun newInstance(): Fragment {
            return RegistrationReaderFragment()
        }
    }
}



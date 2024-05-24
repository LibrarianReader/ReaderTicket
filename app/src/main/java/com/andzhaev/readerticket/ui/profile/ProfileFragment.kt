package com.andzhaev.readerticket.ui.profile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.andzhaev.readerticket.data.network.ApiFactory
import com.andzhaev.readerticket.databinding.FragmentProfileBinding
import com.andzhaev.readerticket.ui.talon.TalonAdapter

class ProfileFragment : Fragment() {
    private var _binding: FragmentProfileBinding? = null
    private val binding: FragmentProfileBinding
        get() = _binding ?: throw RuntimeException(FRAGMENT_ERROR)

    private lateinit var viewModel: ProfileViewModel
    private lateinit var talonsAdapter: TalonAdapter
    private val apiService by lazy { ApiFactory.apiService }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(this).get(ProfileViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentProfileBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupRecyclerView()
        fetchTalons()
    }

    private fun setupRecyclerView() {
        binding.rvTalonsList.layoutManager = LinearLayoutManager(context)
        talonsAdapter = TalonAdapter(mutableListOf(), apiService)
        binding.rvTalonsList.adapter = talonsAdapter
    }

    private fun fetchTalons() {
        viewModel.getAllTalons().observe(viewLifecycleOwner, { talons ->
            talonsAdapter.updateTalons(talons)
        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        private const val FRAGMENT_ERROR = "ProfileFragment is null"
        fun newInstance(): Fragment {
            return ProfileFragment()
        }
    }
}
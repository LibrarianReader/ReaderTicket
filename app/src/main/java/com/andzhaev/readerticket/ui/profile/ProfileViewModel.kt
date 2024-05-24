package com.andzhaev.readerticket.ui.profile

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.andzhaev.readerticket.data.network.ApiFactory
import com.andzhaev.readerticket.domain.model.Talon
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ProfileViewModel : ViewModel() {

    private val talonsLiveData = MutableLiveData<List<Talon>>()

    fun getAllTalons(): LiveData<List<Talon>> {
        ApiFactory.apiService.getAllTalons().enqueue(object : Callback<List<Talon>> {
            override fun onResponse(call: Call<List<Talon>>, response: Response<List<Talon>>) {
                if (response.isSuccessful) {
                    talonsLiveData.value = response.body()
                    Log.d("ProfileViewModel", "Successfully fetched talons")
                } else {
                    Log.e("ProfileViewModel", "Failed to fetch talons, response code: ${response.code()}")
                }
            }

            override fun onFailure(call: Call<List<Talon>>, t: Throwable) {
                Log.e("ProfileViewModel", "Failed to fetch talons", t)
            }
        })
        return talonsLiveData
    }
}
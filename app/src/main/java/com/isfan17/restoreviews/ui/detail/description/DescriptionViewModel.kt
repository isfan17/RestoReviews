package com.isfan17.restoreviews.ui.detail.description

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.isfan17.restoreviews.api.ApiConfig
import com.isfan17.restoreviews.models.DetailRestaurantResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DescriptionViewModel: ViewModel() {

    private val _description = MutableLiveData<String>()
    val description: LiveData<String> = _description

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    fun setDescription(id: String) {
        _isLoading.value = true

        val client = ApiConfig.getRestaurantApi().getDetailRestaurant(id)
        client.enqueue(object : Callback<DetailRestaurantResponse> {
            override fun onResponse(
                call: Call<DetailRestaurantResponse>,
                response: Response<DetailRestaurantResponse>
            ) {
                _isLoading.value = false

                if (response.isSuccessful) {
                    _description.value = response.body()?.restaurant?.description
                } else {
                    Log.e(TAG, "onFailure: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<DetailRestaurantResponse>, t: Throwable) {
                _isLoading.value = false
                Log.e(TAG, "onFailure: ${t.message.toString()}")
            }

        })
    }

    companion object {
        private const val TAG = "DescriptionViewModel"
    }

}
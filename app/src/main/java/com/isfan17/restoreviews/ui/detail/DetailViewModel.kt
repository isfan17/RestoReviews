package com.isfan17.restoreviews.ui.detail

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.isfan17.restoreviews.api.ApiConfig
import com.isfan17.restoreviews.models.DetailRestaurantResponse
import com.isfan17.restoreviews.models.Restaurant
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetailViewModel: ViewModel() {

    private val _detailRestaurant = MutableLiveData<Restaurant>()
    val detailRestaurant: LiveData<Restaurant> = _detailRestaurant

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    fun setDetailRestaurant(id: String) {
        _isLoading.value = true

        val client = ApiConfig.getRestaurantApi().getDetailRestaurant(id)
        client.enqueue(object : Callback<DetailRestaurantResponse> {
            override fun onResponse(
                call: Call<DetailRestaurantResponse>,
                response: Response<DetailRestaurantResponse>
            ) {
                _isLoading.value = false

                if (response.isSuccessful && response.body() != null) {
                    _detailRestaurant.value = response.body()?.restaurant
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
        private const val TAG = "DetailViewModel"
    }

}
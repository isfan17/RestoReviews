package com.isfan17.restoreviews.ui.home

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.isfan17.restoreviews.api.ApiConfig
import com.isfan17.restoreviews.models.ListRestaurantResponse
import com.isfan17.restoreviews.models.RestaurantsItem
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HomeViewModel : ViewModel() {

    private val _listRestaurants = MutableLiveData<List<RestaurantsItem>>()
    val listRestaurants: LiveData<List<RestaurantsItem>> = _listRestaurants

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _layoutType = MutableLiveData<Int>()
    val layoutType: LiveData<Int> = _layoutType

    init {
        _layoutType.value = 0
        findRestaurants()
    }

    private fun findRestaurants() {
        _isLoading.value = true

        val client = ApiConfig.getRestaurantApi().getRestaurants()
        client.enqueue(object : Callback<ListRestaurantResponse> {
            override fun onResponse(
                call: Call<ListRestaurantResponse>,
                response: Response<ListRestaurantResponse>
            ) {
                _isLoading.value = false

                if (response.isSuccessful && response.body() != null) {
                    _listRestaurants.value = response.body()?.restaurants
                } else {
                    Log.e(TAG, "onFailure: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<ListRestaurantResponse>, t: Throwable) {
                _isLoading.value = false
                Log.e(TAG, "onFailure: ${t.message.toString()}")
            }

        })
    }

    fun setLayoutType(type: Int) {
        _layoutType.postValue(type)
    }

    companion object {
        private const val TAG = "HomeViewModel"
    }
}
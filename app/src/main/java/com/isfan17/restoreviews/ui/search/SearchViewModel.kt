package com.isfan17.restoreviews.ui.search

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.isfan17.restoreviews.api.ApiConfig
import com.isfan17.restoreviews.models.RestaurantsItem
import com.isfan17.restoreviews.models.SearchRestaurantResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SearchViewModel: ViewModel() {

    private val _listRestaurants = MutableLiveData<List<RestaurantsItem>>()
    val listRestaurants: LiveData<List<RestaurantsItem>> = _listRestaurants

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _isNoResult = MutableLiveData<Boolean>()
    val isNoResult: LiveData<Boolean> = _isNoResult

    fun setSearchRestaurants(query: String) {
        _isLoading.value = true

        val client = ApiConfig.getRestaurantApi().getSearchRestaurants(query)
        client.enqueue(object : Callback<SearchRestaurantResponse> {
            override fun onResponse(
                call: Call<SearchRestaurantResponse>,
                response: Response<SearchRestaurantResponse>
            ) {
                _isLoading.value = false

                if (response.isSuccessful)
                {
                    _isNoResult.value = response.body()?.founded == 0
                    _listRestaurants.value = response.body()?.restaurants
                }
                else
                {
                    _isNoResult.value = false
                    Log.e(TAG, "onFailure: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<SearchRestaurantResponse>, t: Throwable) {
                _isLoading.value = false
                Log.e(TAG, "onFailure: ${t.message.toString()}")
            }

        })
    }

    companion object {
        private const val TAG = "SearchViewModel"
    }

}
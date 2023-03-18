package com.isfan17.restoreviews.ui.detail.reviews

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.isfan17.restoreviews.api.ApiConfig
import com.isfan17.restoreviews.models.AddReviewResponse
import com.isfan17.restoreviews.models.CustomerReviewsItem
import com.isfan17.restoreviews.models.DetailRestaurantResponse
import com.isfan17.restoreviews.models.Restaurant
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ReviewViewModel: ViewModel() {

    private val _listReviews = MutableLiveData<List<CustomerReviewsItem>>()
    val listReviews: LiveData<List<CustomerReviewsItem>> = _listReviews

    private val _restaurant = MutableLiveData<Restaurant>()
    val restaurant: LiveData<Restaurant> = _restaurant

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _isAdded = MutableLiveData<Boolean>()
    val isAdded: LiveData<Boolean> = _isAdded

    fun postReview(id: String, name: String, review: String) {
        val client = ApiConfig.getRestaurantApi().postReview(id, name, review)
        client.enqueue(object : Callback<AddReviewResponse> {
            override fun onResponse(
                call: Call<AddReviewResponse>,
                response: Response<AddReviewResponse>
            ) {
                _isAdded.value = response.isSuccessful
                _listReviews.value = response.body()?.customerReviews
            }

            override fun onFailure(call: Call<AddReviewResponse>, t: Throwable) {
                _isAdded.value = false
                Log.e(TAG, "onFailure: ${t.message.toString()}")
            }
        })
    }

    fun setReviews(id: String) {
        _isLoading.value = true

        val client = ApiConfig.getRestaurantApi().getDetailRestaurant(id)
        client.enqueue(object : Callback<DetailRestaurantResponse> {
            override fun onResponse(
                call: Call<DetailRestaurantResponse>,
                response: Response<DetailRestaurantResponse>
            ) {
               _isLoading.value = false

                if (response.isSuccessful) {
                    _restaurant.value = response.body()?.restaurant
                    _listReviews.value = response.body()?.restaurant?.customerReviews
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
        private const val TAG = "ReviewViewModel"
    }

}
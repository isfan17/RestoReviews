package com.isfan17.restoreviews.api

import com.isfan17.restoreviews.models.AddReviewResponse
import com.isfan17.restoreviews.models.DetailRestaurantResponse
import com.isfan17.restoreviews.models.ListRestaurantResponse
import com.isfan17.restoreviews.models.SearchRestaurantResponse
import retrofit2.Call
import retrofit2.http.*

interface RestaurantAPI {

    @GET("list")
    fun getRestaurants(): Call<ListRestaurantResponse>

    @GET("search")
    fun getSearchRestaurants(
        @Query("q") query: String
    ): Call<SearchRestaurantResponse>

    @GET("detail/{id}")
    fun getDetailRestaurant(
        @Path("id") id: String
    ): Call<DetailRestaurantResponse>

    @FormUrlEncoded
    @Headers("Authorization: token 12345")
    @POST("review")
    fun postReview(
        @Field("id") id: String,
        @Field("name") name: String,
        @Field("review") review: String
    ): Call<AddReviewResponse>
}
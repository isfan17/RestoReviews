package com.isfan17.restoreviews.models

import com.google.gson.annotations.SerializedName

data class ListRestaurantResponse(

	@field:SerializedName("count")
	val count: Int,

	@field:SerializedName("restaurants")
	val restaurants: List<RestaurantsItem>,

	@field:SerializedName("error")
	val error: Boolean,

	@field:SerializedName("message")
	val message: String
)

package com.isfan17.restoreviews.models

import com.google.gson.annotations.SerializedName

data class SearchRestaurantResponse(

	@field:SerializedName("founded")
	val founded: Int,

	@field:SerializedName("restaurants")
	val restaurants: List<RestaurantsItem>
)

package com.isfan17.restoreviews.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.isfan17.restoreviews.databinding.ItemListBinding
import com.isfan17.restoreviews.databinding.ItemRestaurantBinding
import com.isfan17.restoreviews.models.RestaurantsItem

class RestaurantAdapter(
    private val listRestaurants: List<RestaurantsItem>,
    private val itemViewType: Int
): RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private lateinit var onItemClickCallback: OnItemClickCallback

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }

    inner class RestaurantViewHolder(private val itemBinding: ItemRestaurantBinding): RecyclerView.ViewHolder(itemBinding.root) {
        fun bind(restaurant: RestaurantsItem) {
            itemBinding.tvName.text = restaurant.name
            itemBinding.tvCity.text = restaurant.city
            itemBinding.tvRating.text = restaurant.rating.toString()
            Glide.with(itemView)
                .load("https://restaurant-api.dicoding.dev/images/large/${restaurant.pictureId}")
                .centerCrop()
                .into(itemBinding.ivPicture)
        }
    }

    inner class ListViewHolder(private val binding: ItemListBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(restaurant: RestaurantsItem) {
            binding.tvName.text = restaurant.name
            binding.tvCity.text = restaurant.city
            binding.tvRating.text = restaurant.rating.toString()
            Glide.with(itemView)
                .load("https://restaurant-api.dicoding.dev/images/large/${restaurant.pictureId}")
                .centerCrop()
                .into(binding.ivPicture)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (itemViewType) {
            0 -> {
                val itemBinding = ItemRestaurantBinding.inflate(LayoutInflater.from(parent.context), parent, false)
                RestaurantViewHolder(itemBinding)
            }
            1 -> {
                val itemBinding = ItemListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
                ListViewHolder(itemBinding)
            }
            else -> {
                val itemBinding = ItemRestaurantBinding.inflate(LayoutInflater.from(parent.context), parent, false)
                RestaurantViewHolder(itemBinding)
            }
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val restaurant: RestaurantsItem = listRestaurants[position]
        when (holder)
        {
            is RestaurantViewHolder -> {
                holder.bind(restaurant)
                holder.itemView.setOnClickListener {
                    onItemClickCallback.onItemClicked(listRestaurants[position])
                }
            }
            is ListViewHolder -> {
                holder.bind(restaurant)
                holder.itemView.setOnClickListener {
                    onItemClickCallback.onItemClicked(listRestaurants[position])
                }
            }
        }
    }

    override fun getItemCount() = listRestaurants.size

    interface OnItemClickCallback {
        fun onItemClicked(data: RestaurantsItem)
    }
}
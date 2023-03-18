package com.isfan17.restoreviews.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.isfan17.restoreviews.databinding.ItemReviewBinding
import com.isfan17.restoreviews.models.CustomerReviewsItem

class ReviewAdapter(private val listReview: List<CustomerReviewsItem>): RecyclerView.Adapter<ReviewAdapter.ReviewViewHolder>() {

    inner class ReviewViewHolder(private val binding: ItemReviewBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(review: CustomerReviewsItem) {
            binding.tvReview.text = review.review
            binding.tvDate.text = review.date
            binding.tvUser.text = " by ${review.name}"
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ReviewViewHolder {
        val itemBinding = ItemReviewBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ReviewViewHolder(itemBinding)
    }

    override fun getItemCount() = listReview.size

    override fun onBindViewHolder(holder: ReviewViewHolder, position: Int) {
        val review: CustomerReviewsItem = listReview[position]
        holder.bind(review)
    }

}
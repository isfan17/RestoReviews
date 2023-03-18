package com.isfan17.restoreviews.ui.detail

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import androidx.activity.viewModels
import androidx.annotation.StringRes
import com.bumptech.glide.Glide
import com.google.android.material.tabs.TabLayoutMediator
import com.isfan17.restoreviews.R
import com.isfan17.restoreviews.databinding.ActivityDetailBinding
import com.isfan17.restoreviews.models.Restaurant

class DetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailBinding
    private val viewModel: DetailViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = "Restaurant Details"

        val id = intent.getStringExtra(EXTRA_ID)
        if (id != null) viewModel.setDetailRestaurant(id)

        viewModel.detailRestaurant.observe(this) { detailRestaurant ->
            setRestaurantData(detailRestaurant)
        }

        viewModel.isLoading.observe(this) {
            showLoading(it)
        }

        val bundle = Bundle()
        bundle.putString(EXTRA_ID, id)

        val sectionsPagerAdapter = SectionsPagerAdapter(this, bundle)

        val viewPager = binding.viewPager
        viewPager.adapter = sectionsPagerAdapter

        val tabs = binding.tabs
        TabLayoutMediator(tabs, viewPager) { tab, position ->
            tab.text = resources.getString(TAB_TITLES[position])
        }.attach()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                finish()
                true
            }
            else -> {
                super.onOptionsItemSelected(item)
            }
        }
    }

    private fun setRestaurantData(restaurant: Restaurant) {
        binding.apply {
            tvName.text = restaurant.name
            tvRating.text = restaurant.rating.toString()
            tvCity.text = restaurant.city
            tvAddress.text = ", ${restaurant.address}"
        }
        Glide.with(this)
            .load("https://restaurant-api.dicoding.dev/images/large/${restaurant.pictureId}")
            .centerCrop()
            .into(binding.ivPicture)
        binding.apply {
            ivPicture.visibility = View.VISIBLE
            tvName.visibility = View.VISIBLE
            tvRating.visibility = View.VISIBLE
            tvCity.visibility = View.VISIBLE
            tvAddress.visibility = View.VISIBLE
        }
    }

    private fun showLoading(isLoading: Boolean) {
        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }

    companion object {
        const val EXTRA_ID = "ID"

        @StringRes
        private val TAB_TITLES = intArrayOf(
            R.string.tab_title_1,
            R.string.tab_title_2,
            R.string.tab_title_3
        )
    }
}
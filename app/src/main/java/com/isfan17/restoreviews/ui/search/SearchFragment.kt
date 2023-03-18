package com.isfan17.restoreviews.ui.search

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.isfan17.restoreviews.adapters.RestaurantAdapter
import com.isfan17.restoreviews.databinding.FragmentSearchBinding
import com.isfan17.restoreviews.models.RestaurantsItem
import com.isfan17.restoreviews.ui.detail.DetailActivity

class SearchFragment : Fragment() {

    private var _binding: FragmentSearchBinding? = null
    private val binding get() = _binding!!

    private lateinit var viewModel: SearchViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSearchBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val layoutManager = LinearLayoutManager(requireActivity())
        binding.rvRestaurants.layoutManager = layoutManager

        viewModel = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory())[SearchViewModel::class.java]

        binding.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                viewModel.setSearchRestaurants(query)
                binding.searchView.clearFocus()
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                return false
            }
        })

        viewModel.listRestaurants.observe(viewLifecycleOwner) { restaurants ->
            setListRestaurants(restaurants)
        }

        viewModel.isLoading.observe(viewLifecycleOwner) {
            showLoading(it)
        }

        viewModel.isNoResult.observe(viewLifecycleOwner) {
            showNoResultMsg(it)
        }
    }

    private fun setListRestaurants(restaurants: List<RestaurantsItem>) {
        val adapter = RestaurantAdapter(restaurants, 1)
        binding.rvRestaurants.adapter = adapter

        adapter.setOnItemClickCallback(object : RestaurantAdapter.OnItemClickCallback {
            override fun onItemClicked(data: RestaurantsItem) {
                val intent = Intent(requireContext(), DetailActivity::class.java)
                intent.putExtra(DetailActivity.EXTRA_ID, data.id)
                startActivity(intent)
            }
        })
    }

    private fun showNoResultMsg(isNoResult: Boolean) {
        binding.ivNoResult.visibility = if (isNoResult) View.VISIBLE else View.GONE
        binding.tvNoResult.visibility = if (isNoResult) View.VISIBLE else View.GONE
    }

    private fun showLoading(isLoading: Boolean) {
        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}
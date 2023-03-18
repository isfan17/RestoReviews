package com.isfan17.restoreviews.ui.home

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.PopupMenu
import androidx.annotation.MenuRes
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.isfan17.restoreviews.R
import com.isfan17.restoreviews.adapters.RestaurantAdapter
import com.isfan17.restoreviews.databinding.FragmentHomeBinding
import com.isfan17.restoreviews.models.RestaurantsItem
import com.isfan17.restoreviews.ui.detail.DetailActivity

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    private lateinit var viewModel: HomeViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory())[HomeViewModel::class.java]

        viewModel.layoutType.observe(viewLifecycleOwner) { type ->
            val layoutManager =  if (type == 0)
            {
                GridLayoutManager(requireActivity(), 2)
            }
            else
            {
                LinearLayoutManager(requireActivity())
            }
            binding.rvRestaurants.layoutManager = layoutManager

            viewModel.listRestaurants.observe(viewLifecycleOwner) { restaurants ->
                setListRestaurants(restaurants, type)
            }
        }

        viewModel.isLoading.observe(viewLifecycleOwner) {
            showLoading(it)
        }

        binding.btnLayout.setOnClickListener {
            showMenu(it, R.menu.popup_menu)
        }

    }

    private fun setListRestaurants(restaurants: List<RestaurantsItem>, type: Int) {
        val adapter = RestaurantAdapter(restaurants, type)
        binding.rvRestaurants.adapter = adapter

        adapter.setOnItemClickCallback(object : RestaurantAdapter.OnItemClickCallback {
            override fun onItemClicked(data: RestaurantsItem) {
                val intent = Intent(requireContext(), DetailActivity::class.java)
                intent.putExtra(DetailActivity.EXTRA_ID, data.id)
                startActivity(intent)
            }
        })
    }

    private fun showLoading(isLoading: Boolean) {
        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }

    private fun showMenu(v: View, @MenuRes menuRes: Int) {
        val popup = PopupMenu(requireContext(), v)
        popup.menuInflater.inflate(menuRes, popup.menu)

        popup.setOnMenuItemClickListener { menuItem: MenuItem ->
            when (menuItem.itemId)
            {
                R.id.option_grid -> {
                    viewModel.setLayoutType(0)
                    true
                }
                R.id.option_list -> {
                    viewModel.setLayoutType(1)
                    true
                }
                else -> {
                    viewModel.setLayoutType(0)
                    true
                }
            }
        }

        // Show the popup menu.
        popup.show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
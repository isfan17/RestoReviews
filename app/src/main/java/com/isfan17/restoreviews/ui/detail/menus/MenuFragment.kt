package com.isfan17.restoreviews.ui.detail.menus

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.divider.MaterialDividerItemDecoration
import com.isfan17.restoreviews.adapters.MenuAdapter
import com.isfan17.restoreviews.databinding.FragmentMenuBinding
import com.isfan17.restoreviews.models.Menus
import com.isfan17.restoreviews.ui.detail.DetailActivity

class MenuFragment : Fragment() {

    private var _binding: FragmentMenuBinding? = null
    private val binding get() = _binding!!

    private lateinit var viewModel: MenuViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMenuBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val id = arguments?.getString(DetailActivity.EXTRA_ID).toString()

        val layoutManager = LinearLayoutManager(requireActivity())
        val divider = MaterialDividerItemDecoration(requireActivity(), LinearLayoutManager.VERTICAL)
        divider.isLastItemDecorated = false
        binding.rvMenus.layoutManager = layoutManager
        binding.rvMenus.addItemDecoration(divider)

        viewModel = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory())[MenuViewModel::class.java]

        viewModel.setMenus(id)
        viewModel.listMenus.observe(viewLifecycleOwner) { menus ->
            setListMenus(menus)
        }

        viewModel.isLoading.observe(viewLifecycleOwner) {
            showLoading(it)
        }
    }

    private fun setListMenus(menus: Menus) {
        val adapter = MenuAdapter(menus.foods, menus.drinks)
        binding.rvMenus.adapter = adapter
    }

    private fun showLoading(isLoading: Boolean) {
        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
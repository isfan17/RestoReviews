package com.isfan17.restoreviews.ui.detail.description

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.isfan17.restoreviews.databinding.FragmentDescriptionBinding
import com.isfan17.restoreviews.ui.detail.DetailActivity

class DescriptionFragment : Fragment() {

    private var _binding: FragmentDescriptionBinding? = null
    private val binding get() = _binding!!

    private lateinit var viewModel: DescriptionViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDescriptionBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val id = arguments?.getString(DetailActivity.EXTRA_ID).toString()

        viewModel = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory())[DescriptionViewModel::class.java]

        viewModel.setDescription(id)
        viewModel.description.observe(viewLifecycleOwner) { description ->
            binding.tvDescription.text = description
        }

        viewModel.isLoading.observe(viewLifecycleOwner) {
            showLoading(it)
        }
    }

    private fun showLoading(isLoading: Boolean) {
        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
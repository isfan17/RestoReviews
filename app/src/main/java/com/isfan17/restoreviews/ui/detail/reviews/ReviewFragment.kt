package com.isfan17.restoreviews.ui.detail.reviews

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.divider.MaterialDividerItemDecoration
import com.isfan17.restoreviews.adapters.ReviewAdapter
import com.isfan17.restoreviews.databinding.FragmentReviewBinding
import com.isfan17.restoreviews.models.CustomerReviewsItem
import com.isfan17.restoreviews.ui.detail.DetailActivity

class ReviewFragment : Fragment() {

    private var _binding: FragmentReviewBinding? = null
    private val binding get() = _binding!!

    private lateinit var viewModel: ReviewViewModel
    private lateinit var id: String

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentReviewBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        id = arguments?.getString(DetailActivity.EXTRA_ID).toString()

        viewModel = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory())[ReviewViewModel::class.java]

        val layoutManager = LinearLayoutManager(requireActivity())
        val divider = MaterialDividerItemDecoration(requireActivity(), LinearLayoutManager.VERTICAL)
        divider.isLastItemDecorated = false
        binding.rvReviews.layoutManager = layoutManager
        binding.rvReviews.addItemDecoration(divider)

        binding.btnAddReview.setOnClickListener {
            val addReviewDialogFragment = AddReviewDialogFragment()
            val fragmentManager = childFragmentManager
            addReviewDialogFragment.show(fragmentManager, AddReviewDialogFragment::class.java.simpleName)
        }

        viewModel.setReviews(id)
        viewModel.listReviews.observe(viewLifecycleOwner) { reviews ->
            setListReviews(reviews)
            showNoReviewMsg(reviews.size)
        }

        viewModel.isLoading.observe(viewLifecycleOwner) {
            showLoading(it)
        }

        viewModel.isAdded.observe(viewLifecycleOwner) {
            if (it) Toast.makeText(activity,"Review Added Succesfully", Toast.LENGTH_SHORT).show()
        }
    }

    internal var reviewDialogListener: AddReviewDialogFragment.OnReviewDialogListener = object : AddReviewDialogFragment.OnReviewDialogListener {
        override fun onAddedReview(name: String, review: String) {
            viewModel.postReview(id, name, review)
        }
    }

    private fun showNoReviewMsg(reviewCount: Int) {
        binding.ivNoReview.visibility = if (reviewCount == 0) View.VISIBLE else View.GONE
        binding.tvNoReview.visibility = if (reviewCount == 0) View.VISIBLE else View.GONE
    }

    private fun setListReviews(reviews: List<CustomerReviewsItem>) {
        val adapter = ReviewAdapter(reviews)
        binding.rvReviews.adapter = adapter
    }

    private fun showLoading(isLoading: Boolean) {
        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
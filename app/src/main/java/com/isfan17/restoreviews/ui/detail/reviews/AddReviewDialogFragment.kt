package com.isfan17.restoreviews.ui.detail.reviews

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.isfan17.restoreviews.databinding.FragmentAddReviewDialogBinding

class AddReviewDialogFragment : DialogFragment() {

    private var _binding: FragmentAddReviewDialogBinding? = null
    private val binding get() = _binding!!

    private var reviewDialogListener: OnReviewDialogListener? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAddReviewDialogBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnAddReview.setOnClickListener {
            val name = binding.edtUser.text.toString().trim()
            val review = binding.edtReview.text.toString().trim()

            when
            {
                name.isEmpty() -> {
                    binding.edtUser.error = "This field is required"
                }
                review.isEmpty() -> {
                    binding.edtReview.error = "This field is required"
                }
                else -> {
                    reviewDialogListener?.onAddedReview(name, review)
                    dialog?.dismiss()
                }
            }
        }

        binding.btnClose.setOnClickListener {
            dialog?.cancel()
        }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        val fragment = parentFragment
        if (fragment is ReviewFragment) {
            this.reviewDialogListener = fragment.reviewDialogListener
        }
    }
    override fun onDetach() {
        super.onDetach()
        this.reviewDialogListener = null
    }

    interface OnReviewDialogListener {
        fun onAddedReview(name: String, review: String)
    }

}
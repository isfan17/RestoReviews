package com.isfan17.restoreviews.ui.detail

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.isfan17.restoreviews.ui.detail.description.DescriptionFragment
import com.isfan17.restoreviews.ui.detail.menus.MenuFragment
import com.isfan17.restoreviews.ui.detail.reviews.ReviewFragment

class SectionsPagerAdapter(activity: AppCompatActivity, data: Bundle) : FragmentStateAdapter(activity) {
    private var fragmentBundle: Bundle

    init {
        fragmentBundle = data
    }

    override fun createFragment(position: Int): Fragment {
        var fragment: Fragment? = null
        when (position) {
            0 -> fragment = DescriptionFragment()
            1 -> fragment = MenuFragment()
            2 -> fragment = ReviewFragment()
        }
        fragment?.arguments = this.fragmentBundle
        return fragment as Fragment
    }

    override fun getItemCount() = 3

}
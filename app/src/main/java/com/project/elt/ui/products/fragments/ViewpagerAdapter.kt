package com.project.elt.ui.products.fragments

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.project.elt.data.model.filters.CategoryItem

class ViewpagerAdapter(fragment: FragmentManager, lifecycle: Lifecycle, val cat_list: ArrayList<CategoryItem>?) :
    FragmentStateAdapter(fragment, lifecycle) {

    override fun getItemCount(): Int = cat_list?.size!!

    override fun createFragment(position: Int): Fragment {
        // Return a NEW fragment instance in createFragment(int).
        return ProductsFragment(position)
    }
}
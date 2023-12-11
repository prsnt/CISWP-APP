package com.project.elt.ui.products

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.view.View
import androidx.core.content.ContextCompat
import com.google.android.material.tabs.TabLayoutMediator
import com.project.elt.R
import com.project.elt.data.model.filters.CategoryItem
import com.project.elt.data.model.filters.FiltersBaseModel
import com.project.elt.databinding.ActivityProductsBinding
import com.project.elt.ui.BaseActivity
import com.project.elt.ui.dashboard.DashboardActivity.Companion.CAT_LIST
import com.project.elt.ui.dashboard.DashboardActivity.Companion.CAT_POSITION
import com.project.elt.ui.dashboard.DashboardActivity.Companion.FILTER_MODEL
import com.project.elt.ui.filter.FilterBottomDialogFragment
import com.project.elt.ui.products.fragments.ViewpagerAdapter
import com.project.elt.ui.search.SearchStartActivity

class ProductsActivity : BaseActivity() {
    private val TAG = "ProductsActivity"
    private lateinit var productsBinding: ActivityProductsBinding
    private lateinit var viewpagerAdapter: ViewpagerAdapter
    private var cat_position = 0
    lateinit var cat_list: ArrayList<CategoryItem>
    lateinit var filtersBaseModel: FiltersBaseModel
    var isFreeChecked = false
    var isPaidChecked = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        productsBinding = ActivityProductsBinding.inflate(layoutInflater)
        setContentView(productsBinding.root)
        productsBinding.activity = this
        productsBinding.cardViewFree.isChecked = true
        getIntentValue()
        setupViewPager()
        setListners()
    }

    fun redirectSearchPage(view: View) {
        startActivity(Intent(this, SearchStartActivity::class.java))
    }

    fun getCategoryId(position: Int): Int? {
        return cat_list[position].catId
    }

    fun getCategoryName(position: Int): String? {
        return cat_list[position].catName
    }

    fun selectFreePaymentType() {
        isFreeChecked = !isFreeChecked
        isPaidChecked = false
        // Update UI based on the checked state (change background color, text color, etc.)
        if (isFreeChecked) {
            productsBinding.cardViewFree.setCardBackgroundColor(ContextCompat.getColor(this, R.color.black))
            productsBinding.tvFree.setTextColor(ContextCompat.getColor(this, R.color.white))
            productsBinding.imgFree.setColorFilter(ContextCompat.getColor(this, R.color.white))

            productsBinding.cardViewPaid.setCardBackgroundColor(ContextCompat.getColor(this, R.color.white))
            productsBinding.tvPaid.setTextColor(ContextCompat.getColor(this, R.color.black))
            productsBinding.imgPaid.setColorFilter(ContextCompat.getColor(this, R.color.secondary))
        } else {
            productsBinding.cardViewFree.setCardBackgroundColor(ContextCompat.getColor(this, R.color.white))
            productsBinding.tvFree.setTextColor(ContextCompat.getColor(this, R.color.black))
            productsBinding.imgFree.setColorFilter(ContextCompat.getColor(this, R.color.secondary))
        }
    }

    fun selectPaidPaymentType() {
        isPaidChecked = !isPaidChecked
        isFreeChecked = false
        // Update UI based on the checked state (change background color, text color, etc.)
        if (!isPaidChecked) {
            productsBinding.cardViewPaid.setCardBackgroundColor(ContextCompat.getColor(this, R.color.white))
            productsBinding.tvPaid.setTextColor(ContextCompat.getColor(this, R.color.black))
            productsBinding.imgPaid.setColorFilter(ContextCompat.getColor(this, R.color.secondary))
        } else {
            productsBinding.cardViewPaid.setCardBackgroundColor(ContextCompat.getColor(this, R.color.black))
            productsBinding.tvPaid.setTextColor(ContextCompat.getColor(this, R.color.white))
            productsBinding.imgPaid.setColorFilter(ContextCompat.getColor(this, R.color.white))

            productsBinding.cardViewFree.setCardBackgroundColor(ContextCompat.getColor(this, R.color.white))
            productsBinding.tvFree.setTextColor(ContextCompat.getColor(this, R.color.black))
            productsBinding.imgFree.setColorFilter(ContextCompat.getColor(this, R.color.secondary))

        }
    }

    fun setListners()
    {
        productsBinding.cardViewFree.setOnClickListener {
            selectFreePaymentType()
        }
        productsBinding.cardViewPaid.setOnClickListener {
            selectPaidPaymentType()
        }
    }

    //To fetch the value fron the previous activity through Intent
    private fun getIntentValue() {
        if (intent.hasExtra(CAT_POSITION))
            cat_position = intent.getIntExtra(CAT_POSITION, 0)
        if (intent.hasExtra(CAT_LIST))
            cat_list = intent.getParcelableArrayListExtra(CAT_LIST)!!
        if (intent.hasExtra(FILTER_MODEL))
            filtersBaseModel = intent.getParcelableExtra(FILTER_MODEL)!!
    }

    private fun setupViewPager() {
        viewpagerAdapter = ViewpagerAdapter(supportFragmentManager, lifecycle, cat_list)
        productsBinding.pager.adapter = viewpagerAdapter
        TabLayoutMediator(productsBinding.tabLayout, productsBinding.pager) { tab, position ->
            tab.text = cat_list[position].catName
        }.attach()
        Handler().postDelayed({
            productsBinding.pager.setCurrentItem(cat_position, false)
        }, 100)
    }

    fun showDialog(view: View) {
        var isPaid = false
        val filterBottomDialogFragment: FilterBottomDialogFragment? =
            FilterBottomDialogFragment.newInstance(filtersBaseModel)
        filterBottomDialogFragment?.show(
            supportFragmentManager,
            "filter"
        )
    }
}
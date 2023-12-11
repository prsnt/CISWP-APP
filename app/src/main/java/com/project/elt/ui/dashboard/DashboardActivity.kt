package com.project.elt.ui.dashboard

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.View.OnClickListener
import androidx.activity.viewModels
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.core.view.GravityCompat
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import com.project.App
import com.project.elt.R
import com.project.elt.data.model.filters.CategoryItem
import com.project.elt.data.model.filters.FiltersBaseModel
import com.project.elt.databinding.ActivityMainBinding
import com.project.elt.ui.BaseActivity
import com.project.elt.ui.products.ProductsActivity
import com.project.elt.ui.search.SearchStartActivity
import com.project.elt.utils.Status
import com.project.elt.utils.viewModeFactory

class DashboardActivity : BaseActivity(), OnClickListener {
    private lateinit var binding: ActivityMainBinding
    private lateinit var dashboardAdapter: DashboardAdapter
    private var list_category: ArrayList<CategoryItem>? = null
    private lateinit var filtersBaseModel: FiltersBaseModel

    companion object {
        const val CAT_POSITION = "CAT_POSITION"
        const val CAT_LIST = "CAT_LIST"
        const val FILTER_MODEL = "FILTER_MODEL"
    }

    private val dashboardViewModel: DashboardViewModel by viewModels() {
        viewModeFactory { DashboardViewModel(App.appModule.mainRepository, this) }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        installSplashScreen()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)  // This calls BaseActivity's setContentView
        //Setup listners
        setupListners()
        //set Adapter for the Recyclerview of Dashboard
        setupRecyclerview()
        //dashboardViewModel.dummy_getCategories()
        dashboardViewModel.getCategories()
    }

    override fun onClick(p0: View?) {
        when (p0?.id) {
            R.id.btnMenu -> {
                drawerLayout.openDrawer(GravityCompat.START)
            }

            R.id.tvSearch -> {
                startActivity(Intent(this, SearchStartActivity::class.java))
            }
        }
    }

    //Setup listners
    private fun setupListners() {
        //Setup listners
        binding.btnMenu.setOnClickListener(this)
        binding.tvSearch.setOnClickListener(this)
        dashboardViewModel.responseFilters.observe(this, Observer {
            Log.d("PRT", "listners: " + it.data.toString())
            when (it.status) {
                Status.LOADING -> {
                    //binding.progressBar2.visibility = View.VISIBLE
                }

                Status.SUCCESS -> {
                    it.data?.let { it1 ->
                        filtersBaseModel = it1
                        list_category = it1.category as ArrayList<CategoryItem>?
                        dashboardAdapter.setList(list_category)
                        //binding.progressBar2.visibility = View.GONE
                    }
                }

                else -> {
                    //binding.progressBar2.visibility = View.GONE
                }
            }
        })
    }

    //set Adapter for the Recyclerview of Dashboard
    private fun setupRecyclerview() {
        val layoutManager = GridLayoutManager(this, 2)
        binding.recyclerviewDashboard.layoutManager = layoutManager
        dashboardAdapter = DashboardAdapter(
            this,
            DashboardAdapter.OnClickListener { category, position ->
                startActivity(
                    Intent(
                        this,
                        ProductsActivity::class.java
                    ).putExtra(CAT_POSITION, position)
                        .putParcelableArrayListExtra(CAT_LIST, list_category)
                        .putExtra(FILTER_MODEL, filtersBaseModel)
                )
            })
        binding.recyclerviewDashboard.adapter = dashboardAdapter
    }
}
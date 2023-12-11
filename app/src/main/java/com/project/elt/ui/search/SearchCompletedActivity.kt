package com.project.elt.ui.search

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.viewModels
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import com.google.gson.Gson
import com.project.App
import com.project.elt.R
import com.project.elt.data.model.filters.CategoryItem
import com.project.elt.data.model.filters.CountryItem
import com.project.elt.data.model.filters.DevelopersItem
import com.project.elt.data.model.filters.FiltersBaseModel
import com.project.elt.data.model.filters.LanguagesItem
import com.project.elt.data.model.filters.TopicsItem
import com.project.elt.data.model.filters.TypeOfEltItem
import com.project.elt.data.model.products.ProductItem
import com.project.elt.databinding.ActivitySearchCompletedBinding
import com.project.elt.ui.dashboard.DashboardActivity
import com.project.elt.ui.filter.FilterBottomDialogFragment
import com.project.elt.ui.product_detail.ProductDetailActivity
import com.project.elt.ui.products.ProductsActivity
import com.project.elt.ui.products.ProductsAdapter
import com.project.elt.ui.products.fragments.ProductsFragment
import com.project.elt.utils.Constant
import com.project.elt.utils.RecyclerViewLoadMoreScroll
import com.project.elt.utils.Status
import com.project.elt.utils.viewModeFactory

class SearchCompletedActivity : AppCompatActivity() {
    private lateinit var scrollListener: RecyclerViewLoadMoreScroll
    private lateinit var layoutManager: GridLayoutManager
    private lateinit var productsAdapter: ProductsAdapter
    private lateinit var filtersBaseModel: FiltersBaseModel
    private lateinit var binding: ActivitySearchCompletedBinding
    private var receivedSelectedCategories= ArrayList<CategoryItem>()
    private var receivedSelectedCountries= ArrayList<CountryItem>()
    private var receivedSelectedDevelopers= ArrayList<DevelopersItem>()
    private var receivedSelectedTopicAreas= ArrayList<TopicsItem>()
    private var receivedSelectedTypeELTs= ArrayList<TypeOfEltItem>()
    private var receivedSelectedLanguages= ArrayList<LanguagesItem>()
    private var list_Products = ArrayList<ProductItem?>()
    private val viewModel: SearchCompleteViewModel by viewModels {
        viewModeFactory {
            SearchCompleteViewModel(App.appModule.mainRepository)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySearchCompletedBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.vm = viewModel
        binding.activity = this
        getIntentData()
        setData()
        listners()
        setupRecyclerview()
    }

    private fun setData() {
        binding.tvSearchFound.text = "Number Filters applied " +getCount()
    }

    private fun getCount(): Int {
        return receivedSelectedCategories.size + receivedSelectedTopicAreas.size + receivedSelectedTypeELTs.size + receivedSelectedDevelopers.size + receivedSelectedCountries.size + receivedSelectedLanguages.size;
    }

    private fun listners() {
        viewModel.responseProducts.observe(this, Observer {
            Log.d("PRT", "listners: " + it.data.toString())
            when (it.status) {
                Status.LOADING -> {
                    //binding.progressBar2.visibility = View.VISIBLE
                }

                Status.SUCCESS -> {
                    it.data?.let { it1 ->
                        viewModel.setLoadingState(false)
                        productsAdapter.removeLoadingView()
                        it1.data?.let { it2 -> list_Products.addAll(it2) }
                        productsAdapter.setList(list_Products)
                        scrollListener.setLoaded()
                        binding.rviewSearchCompleted.post {
                            productsAdapter.notifyDataSetChanged()
                        }
                        Log.d("PRT", "List Count: " + list_Products.size)
                        if (list_Products.isEmpty()) {
                            binding.tvEmptyView.visibility = View.VISIBLE
                            binding.rviewSearchCompleted.visibility = View.GONE
                        } else {
                            binding.tvEmptyView.visibility = View.GONE
                            binding.rviewSearchCompleted.visibility = View.VISIBLE
                        }
                        //list_Products = (it1.data as ArrayList<ProductItem>?)!!
                        //binding.progressBar2.visibility = View.GONE
                    } ?: run {
                        viewModel.setLoadingState(false)
                        binding.tvEmptyView.visibility = View.VISIBLE
                        binding.rviewSearchCompleted.visibility = View.GONE
                    }
                }

                else -> {
                    viewModel.setLoadingState(false)
                    productsAdapter.removeLoadingView()
                    //binding.progressBar2.visibility = View.GONE
                }
            }
            binding.tvNumberFound.text = "${productsAdapter.itemCount} Records found"
        })
    }

    private fun loadMore() {
        productsAdapter.addLoadingView()
        viewModel.getProducts(
            receivedSelectedCategories.joinToString(", "),
            receivedSelectedTopicAreas.joinToString(", "),
            receivedSelectedTypeELTs.joinToString(", "),
            receivedSelectedDevelopers.joinToString(", "),
            receivedSelectedCountries.joinToString(", "),
            receivedSelectedLanguages.joinToString(", "),
        )
    }

    //set Adapter for the Recyclerview of Dashboard
    private fun setupRecyclerview() {
        viewModel.setLoadingState(true)
        layoutManager = GridLayoutManager(this, 2)
        scrollListener = RecyclerViewLoadMoreScroll(layoutManager)
        scrollListener.setOnLoadMoreListener(object :
            RecyclerViewLoadMoreScroll.OnLoadMoreListener {
            override fun onLoadMore() {
                Log.d("PRT", "onLoadMore: ")
                loadMore()
            }
        })
        binding.rviewSearchCompleted.addOnScrollListener(scrollListener)
        binding.rviewSearchCompleted.layoutManager = layoutManager
        binding.rviewSearchCompleted.setHasFixedSize(true)
        productsAdapter =
            ProductsAdapter(this, ProductsAdapter.OnClickListener {
                startActivity(
                    Intent(
                        this,
                        ProductDetailActivity::class.java
                    ).putExtra(
                        ProductsFragment.PRODUCT_MODEL, it
                    )
                )
            })
        binding.rviewSearchCompleted.adapter = productsAdapter
        layoutManager.spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {
            override fun getSpanSize(position: Int): Int {
                return when (productsAdapter.getItemViewType(position)) {
                    Constant.VIEW_TYPE_ITEM -> 1
                    Constant.VIEW_TYPE_LOADING -> 2 //number of columns of the grid
                    else -> -1
                }
            }
        }
    }

    private fun getIntentData() {
        if (intent.hasExtra(DashboardActivity.FILTER_MODEL))
            filtersBaseModel = intent.getParcelableExtra(DashboardActivity.FILTER_MODEL)!!
        else
            binding.btnFilters.visibility = View.GONE
        if (intent.hasExtra("selectedCategories"))
            receivedSelectedCategories =
                intent.getParcelableArrayListExtra("selectedCategories")!!
        if (intent.hasExtra("selectedCountries"))
            receivedSelectedCountries =
                intent.getParcelableArrayListExtra("selectedCountries")!!
        if (intent.hasExtra("selectedDevelopers"))
            receivedSelectedDevelopers =
                intent.getParcelableArrayListExtra("selectedDevelopers")!!
        if (intent.hasExtra("selectedTopicAreas"))
            receivedSelectedTopicAreas =
                intent.getParcelableArrayListExtra("selectedTopicAreas")!!
        if (intent.hasExtra("selectedTypeELTs"))
            receivedSelectedTypeELTs =
                intent.getParcelableArrayListExtra("selectedTypeELTs")!!
        if (intent.hasExtra("selectedLanguages"))
            receivedSelectedLanguages =
                intent.getParcelableArrayListExtra("selectedLanguages")!!

//        Log.d("PRT", "setListners: " + Gson().toJson(filtersBaseModel).toString())
        getProducts()
    }

    private fun getProducts() {
        viewModel.getProducts(
            receivedSelectedCategories.joinToString(", "),
            receivedSelectedTopicAreas.joinToString(", "),
            receivedSelectedTypeELTs.joinToString(", "),
            receivedSelectedDevelopers.joinToString(", "),
            receivedSelectedCountries.joinToString(", "),
            receivedSelectedLanguages.joinToString(", "),
        )
    }

    fun showDialog(view: View) {
        var isPaid = false
        val filterBottomDialogFragment: FilterBottomDialogFragment =
            FilterBottomDialogFragment.newInstance(filtersBaseModel)
        filterBottomDialogFragment.show(
            supportFragmentManager,
            "filter"
        )
    }
}
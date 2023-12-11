package com.project.elt.ui.products.fragments

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.project.App
import com.project.elt.data.model.products.ProductItem
import com.project.elt.databinding.FragmentProductsBinding
import com.project.elt.ui.product_detail.ProductDetailActivity
import com.project.elt.ui.products.ProductsActivity
import com.project.elt.ui.products.ProductsAdapter
import com.project.elt.utils.Constant.VIEW_TYPE_ITEM
import com.project.elt.utils.Constant.VIEW_TYPE_LOADING
import com.project.elt.utils.RecyclerViewLoadMoreScroll
import com.project.elt.utils.Status
import com.project.elt.utils.viewModeFactory

class ProductsFragment(val position: Int) : Fragment() {

    companion object {
        const val PRODUCT_MODEL = "PRODUCT_MODEL"
    }

    private lateinit var scrollListener: RecyclerViewLoadMoreScroll
    private lateinit var layoutManager: GridLayoutManager
    private lateinit var productsAdapter: ProductsAdapter

    private lateinit var binding: FragmentProductsBinding
    private var list_Products = ArrayList<ProductItem?>()
    var isLoading = false
    private val viewModel: ProductsFragmentViewModel by viewModels {
        viewModeFactory {
            ProductsFragmentViewModel(
                App.appModule.mainRepository,
                requireActivity()
            )
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentProductsBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.vm = viewModel
        binding.categoryName = (activity as ProductsActivity).getCategoryName(position = position)
        setupRecyclerview()
        setupListners()
        viewModel.getProducts(
            (activity as ProductsActivity).getCategoryId(position = position)
                .toString()
        )
    }

    private fun setupListners() {
        viewModel.responseProducts.observe(viewLifecycleOwner, Observer {
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
                        binding.recyclerviewProducts.post {
                            productsAdapter.notifyDataSetChanged()
                        }
                        //list_Products = (it1.data as ArrayList<ProductItem>?)!!
                        //binding.progressBar2.visibility = View.GONE
                    }
                }

                else -> {
                    productsAdapter.removeLoadingView()
                    //binding.progressBar2.visibility = View.GONE
                }
            }
        })
    }

    private fun loadMore() {
        productsAdapter.addLoadingView()
        viewModel.getProducts(
            (activity as ProductsActivity).getCategoryId(position = position)
                .toString()
        )
    }

    //set Adapter for the Recyclerview of Dashboard
    private fun setupRecyclerview() {
        viewModel.setLoadingState(true)
        layoutManager = GridLayoutManager(activity, 2)
        scrollListener = RecyclerViewLoadMoreScroll(layoutManager)
        scrollListener.setOnLoadMoreListener(object :
            RecyclerViewLoadMoreScroll.OnLoadMoreListener {
            override fun onLoadMore() {
                Log.d("PRT", "onLoadMore: ")
                loadMore()
            }
        })
        binding.recyclerviewProducts.addOnScrollListener(scrollListener)
        binding.recyclerviewProducts.layoutManager = layoutManager
        binding.recyclerviewProducts.setHasFixedSize(true)
        productsAdapter =
            ProductsAdapter(requireActivity(), ProductsAdapter.OnClickListener {
                startActivity(
                    Intent(
                        requireActivity(),
                        ProductDetailActivity::class.java
                    ).putExtra(
                        PRODUCT_MODEL, it
                    )
                )
            })
        binding.recyclerviewProducts.adapter = productsAdapter
        layoutManager.spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {
            override fun getSpanSize(position: Int): Int {
                return when (productsAdapter.getItemViewType(position)) {
                    VIEW_TYPE_ITEM -> 1
                    VIEW_TYPE_LOADING -> 2 //number of columns of the grid
                    else -> -1
                }
            }
        }
    }
}
package com.project.elt.ui.product_detail

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.project.App
import com.project.elt.data.model.products.ProductItem
import com.project.elt.databinding.ActivityProductDetailBinding
import com.project.elt.ui.products.fragments.ProductsFragment.Companion.PRODUCT_MODEL
import com.project.elt.utils.Status
import com.project.elt.utils.viewModeFactory


class ProductDetailActivity : AppCompatActivity() {

    private var productId: Int = 0
    lateinit var productItem: ProductItem
    lateinit var binding:ActivityProductDetailBinding
    private val viewModel: ProductDetailViewModel by viewModels {
        viewModeFactory {
            ProductDetailViewModel(
                App.appModule.mainRepository,
                this
            )
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProductDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.vm=viewModel
        getIntentValue()
        setupListners()
    }

    private fun setValues()
    {
        binding.productmodel = productItem
        binding.activity = this
        binding.tvConstruction.isSelected = true
    }

    //To fetch the value fron the previous activity through Intent
    private fun getIntentValue() {
        if (intent.hasExtra(PRODUCT_MODEL)) {
            productItem = intent.getParcelableExtra(PRODUCT_MODEL)!!
            setValues()
        }else if(intent.hasExtra("product_id")) {
            productId = intent.getIntExtra("product_id", 0)
            viewModel.getProducts(productId)
        }

    }

    private fun setupListners() {
        viewModel.responseProducts.observe(this, Observer {
            Log.d("PRT", "listners: " + it.data.toString())
            when (it.status) {
                Status.LOADING -> {
                    //binding.progressBar2.visibility = View.VISIBLE
                }

                Status.SUCCESS -> {
                    it.data?.let { it1 ->
                        viewModel.setLoadingState(false)
                        it1.data?.let { it2 -> productItem = it2[0]}
                        setValues()
                        //list_Products = (it1.data as ArrayList<ProductItem>?)!!
                        //binding.progressBar2.visibility = View.GONE
                    }
                }

                else -> {
                    //binding.progressBar2.visibility = View.GONE
                }
            }
        })
    }

    fun redirectLink(view: View)
    {
        if(!productItem.website.isNullOrEmpty()) {
            val intent = Intent(Intent.ACTION_VIEW)
            intent.data = Uri.parse(productItem.website)
            startActivity(intent)
        }
        else
            Toast.makeText(this, "No website detail available!", Toast.LENGTH_SHORT).show()
    }
}
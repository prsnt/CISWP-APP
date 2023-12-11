package com.project.elt.ui.products.fragments

import android.content.Context
import androidx.databinding.ObservableBoolean
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.gson.Gson
import com.project.elt.data.model.filters.FiltersBaseModel
import com.project.elt.data.model.products.ProductsBaseModel
import com.project.elt.data.repository.MainRepository
import com.project.elt.utils.ReadJSONFromAssets
import com.project.elt.utils.Resource
import kotlinx.coroutines.launch

class ProductsFragmentViewModel(val mainRepository: MainRepository, val context: Context) :
    ViewModel() {
    private val _responseProducts = MutableLiveData<Resource<ProductsBaseModel>>()
    val responseProducts = _responseProducts as LiveData<Resource<ProductsBaseModel>>
    private var page_number = 1
    var isLoading = ObservableBoolean()

    fun dummy_getProducts(cat_id: String) {
        setLoadingState(true)
        val json = ReadJSONFromAssets(context, "products.json")
        _responseProducts.postValue(
            Resource.success(
                Gson().fromJson(
                    json,
                    ProductsBaseModel::class.java
                )
            )
        )
        setLoadingState(false)
    }

    fun getProducts(
        cat_id: String
    ) {
        viewModelScope.launch {
            mainRepository.getProducts(
                page_number++,
                20,
                cat_id
            ).let {
                if (it.isSuccessful) {
                    if (!it.body()?.data.isNullOrEmpty())
                        _responseProducts.postValue(Resource.success(it.body()))
                    else
                        _responseProducts.postValue(Resource.error(it.message()))
                }
            }
        }
    }

    // Function to update the loading state
    fun setLoadingState(isLoading: Boolean) {
        this.isLoading.set(isLoading)
    }
}
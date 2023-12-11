package com.project.elt.ui.product_detail

import android.content.Context
import androidx.databinding.ObservableBoolean
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.project.elt.data.model.products.ProductsBaseModel
import com.project.elt.data.repository.MainRepository
import com.project.elt.utils.Resource
import kotlinx.coroutines.launch

class ProductDetailViewModel(val mainRepository: MainRepository, val context: Context):ViewModel() {
    private val _responseProducts = MutableLiveData<Resource<ProductsBaseModel>>()
    val responseProducts = _responseProducts as LiveData<Resource<ProductsBaseModel>>
    var isLoading = ObservableBoolean()

    fun getProducts(
        product_id: Int
    ) {
        setLoadingState(true)
        viewModelScope.launch {
            mainRepository.getProducts(
                1,
                20,
                product_id
            ).let {
                setLoadingState(false)
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
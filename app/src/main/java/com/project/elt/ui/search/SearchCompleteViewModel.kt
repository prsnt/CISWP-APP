package com.project.elt.ui.search

import androidx.databinding.ObservableBoolean
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.project.elt.data.model.products.ProductsBaseModel
import com.project.elt.data.repository.MainRepository
import com.project.elt.utils.Resource
import kotlinx.coroutines.launch

class SearchCompleteViewModel(val mainRepository: MainRepository) : ViewModel() {
    val _responseProducts = MutableLiveData<Resource<ProductsBaseModel>>()
    val responseProducts = _responseProducts as LiveData<Resource<ProductsBaseModel>>

    private var page_number = 1
    var isLoading = ObservableBoolean()

    fun getProducts(
        cat_id: String,
        topic_id: String,
        type_elt_id: String,
        developer_id: String,
        country_id: String,
        language_id: String
    ) {
        viewModelScope.launch {
            mainRepository.getProducts(
                page_number++,
                20,
                cat_id,
                topic_id,
                type_elt_id,
                developer_id,
                country_id,
                language_id
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

    fun setLoadingState(isLoading: Boolean) {
        this.isLoading.set(isLoading)
    }
}
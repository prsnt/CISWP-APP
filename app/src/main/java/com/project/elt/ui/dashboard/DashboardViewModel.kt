package com.project.elt.ui.dashboard

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.gson.Gson
import com.project.elt.data.model.filters.FiltersBaseModel
import com.project.elt.data.repository.MainRepository
import com.project.elt.utils.ReadJSONFromAssets
import com.project.elt.utils.Resource
import kotlinx.coroutines.launch

class DashboardViewModel(val mainRepository: MainRepository, val context: Context) : ViewModel() {
    private val _responseFilters = MutableLiveData<Resource<FiltersBaseModel>>()
    val responseFilters = _responseFilters as LiveData<Resource<FiltersBaseModel>>

    override fun onCleared() {
        super.onCleared()
    }

    fun getCategories() {
        viewModelScope.launch {
            mainRepository.getFilters().let {
                if (it.isSuccessful) {
                    if (!it.body()?.category.isNullOrEmpty())
                        _responseFilters.postValue(Resource.success(it.body()))
                    else
                        _responseFilters.postValue(Resource.error(it.message()))
                }
            }
        }
    }

    fun dummy_getCategories() {
        val json = ReadJSONFromAssets(context, "filters.json")
        _responseFilters.postValue(Resource.success(Gson().fromJson(json,FiltersBaseModel::class.java)))
    }
}
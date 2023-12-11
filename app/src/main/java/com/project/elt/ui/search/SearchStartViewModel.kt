package com.project.elt.ui.search

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.project.elt.data.model.search.SearchBaseModel
import com.project.elt.data.repository.MainRepository
import com.project.elt.utils.Resource
import kotlinx.coroutines.launch

class SearchStartViewModel(val mainRepository: MainRepository) : ViewModel() {
    private val _responseSearchSuggestion = MutableLiveData<Resource<SearchBaseModel>>()
    val responseSearchSuggestion = _responseSearchSuggestion as LiveData<Resource<SearchBaseModel>>

    fun getSearchSuggestions(search_text: String) {
        viewModelScope.launch {
            mainRepository.getSearchSuggestion(search_text).let {
                if (it.isSuccessful)
                    if (!it.body()?.topics.isNullOrEmpty() || !it.body()?.names.isNullOrEmpty()) {
                        _responseSearchSuggestion.postValue(Resource.success(it.body()))
                    } else {
                        _responseSearchSuggestion.postValue(Resource.error(it.message()))
                    }
            }
        }
    }
}
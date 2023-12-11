package com.project.elt.data.repository

import com.project.elt.data.api.ApiHelper

class MainRepository(private val apiHelper: ApiHelper) {
    suspend fun getFilters() = apiHelper.getFilters()
    suspend fun getProducts(
        page_number: Int,
        page_size: Int,
        cat_id: String,
        topic_id: String,
        type_elt_id: String,
        developer_id: String,
        country_id: String,
        language_id: String
    ) = apiHelper.getProducts(
        page_number, page_size, cat_id, topic_id, type_elt_id, developer_id, country_id, language_id
    )
    suspend fun getProducts(
        page_number: Int,
        page_size: Int,
        cat_id: String
    ) = apiHelper.getProducts(
        page_number, page_size, cat_id
    )

    suspend fun getProducts(
        page_number: Int,
        page_size: Int,
        product_id: Int
    ) = apiHelper.getProducts(
        page_number, page_size, product_id
    )

    suspend fun getSearchSuggestion(
        search_text: String
    ) = apiHelper.getSearchSuggestions(
        search_text
    )
}
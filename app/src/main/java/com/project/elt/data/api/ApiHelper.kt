package com.project.elt.data.api

import com.project.elt.data.model.filters.FiltersBaseModel
import com.project.elt.data.model.products.ProductsBaseModel
import com.project.elt.data.model.search.SearchBaseModel
import retrofit2.Response

interface ApiHelper {
    suspend fun getFilters(): Response<FiltersBaseModel>
    suspend fun getProducts(
        page_number: Int,
        page_size: Int,
        cat_id: String,
        topic_id: String,
        type_elt_id: String,
        developer_id: String,
        country_id: String,
        language_id: String
    ): Response<ProductsBaseModel>

    suspend fun getProducts(
        page_number: Int,
        page_size: Int,
        cat_id: String
    ): Response<ProductsBaseModel>

    suspend fun getProducts(
        page_number: Int,
        page_size: Int,
        product_id: Int
    ): Response<ProductsBaseModel>

    suspend fun getSearchSuggestions(
        search_text: String
    ): Response<SearchBaseModel>
}
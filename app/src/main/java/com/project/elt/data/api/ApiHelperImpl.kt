package com.project.elt.data.api

import com.project.elt.data.model.filters.FiltersBaseModel
import com.project.elt.data.model.products.ProductsBaseModel
import com.project.elt.data.model.search.SearchBaseModel
import retrofit2.Response

class ApiHelperImpl(private val apiInterface: ApiInterface) : ApiHelper {
    override suspend fun getFilters(): Response<FiltersBaseModel> {
        return apiInterface.getFilters()
    }

    override suspend fun getProducts(
        page_number: Int,
        page_size: Int,
        cat_id: String,
        topic_id: String,
        type_elt_id: String,
        developer_id: String,
        country_id: String,
        language_id: String
    ): Response<ProductsBaseModel> {
        return apiInterface.getProducts(
            page_number,
            page_size,
            cat_id,
            topic_id,
            type_elt_id,
            developer_id,
            country_id,
            language_id
        )
    }

    override suspend fun getProducts(
        page_number: Int,
        page_size: Int,
        cat_id: String
    ): Response<ProductsBaseModel> {
        return apiInterface.getProducts(
            page_number,
            page_size,
            cat_id
        )
    }

    override suspend fun getProducts(
        page_number: Int,
        page_size: Int,
        product_id: Int
    ): Response<ProductsBaseModel> {
        return apiInterface.getProducts(
            page_number,
            page_size,
            product_id
        )
    }

    override suspend fun getSearchSuggestions(search_text: String): Response<SearchBaseModel> {
        return apiInterface.getSearchSuggestion(search_text)
    }
}
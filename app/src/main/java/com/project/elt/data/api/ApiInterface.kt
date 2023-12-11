package com.project.elt.data.api

import com.project.elt.data.model.filters.FiltersBaseModel
import com.project.elt.data.model.products.ProductsBaseModel
import com.project.elt.data.model.search.SearchBaseModel
import com.project.elt.utils.TextUtils.Companion.CAT_ID
import com.project.elt.utils.TextUtils.Companion.COUNTRY_ID
import com.project.elt.utils.TextUtils.Companion.DEVELOPER_ID
import com.project.elt.utils.TextUtils.Companion.LANGUAGE_ID
import com.project.elt.utils.TextUtils.Companion.PAGE_NUMBER
import com.project.elt.utils.TextUtils.Companion.PAGE_SIZE
import com.project.elt.utils.TextUtils.Companion.PRODUCT_ID
import com.project.elt.utils.TextUtils.Companion.SEARCH_TEXT
import com.project.elt.utils.TextUtils.Companion.TOPIC_ID
import com.project.elt.utils.TextUtils.Companion.TYPE_ELT_ID
import com.project.elt.utils.TextUtils.Companion.filtersAPI
import com.project.elt.utils.TextUtils.Companion.productsAPI
import com.project.elt.utils.TextUtils.Companion.searchSuggestionAPI
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiInterface {
    @GET(filtersAPI)
    suspend fun getFilters(): Response<FiltersBaseModel>

    @GET(productsAPI)
    suspend fun getProducts(
        @Query(PAGE_NUMBER) page_number: Int,
        @Query(PAGE_SIZE) page_size: Int,
        @Query(CAT_ID) cat_id: String,
        @Query(TOPIC_ID) topic_id: String,
        @Query(TYPE_ELT_ID) type_elt_id: String,
        @Query(DEVELOPER_ID) developer_id: String,
        @Query(COUNTRY_ID) country_id: String,
        @Query(LANGUAGE_ID) language_id: String,
    ): Response<ProductsBaseModel>

    @GET(productsAPI)
    suspend fun getProducts(
        @Query(PAGE_NUMBER) page_number: Int,
        @Query(PAGE_SIZE) page_size: Int,
        @Query(CAT_ID) cat_id: String
    ): Response<ProductsBaseModel>

    @GET(productsAPI)
    suspend fun getProducts(
        @Query(PAGE_NUMBER) page_number: Int,
        @Query(PAGE_SIZE) page_size: Int,
        @Query(PRODUCT_ID) product_id: Int
    ): Response<ProductsBaseModel>

    @GET(searchSuggestionAPI)
    suspend fun getSearchSuggestion(
        @Query(SEARCH_TEXT) search_text:String
    ):Response<SearchBaseModel>
}
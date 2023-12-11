package com.project.elt.data.model.search

import kotlinx.parcelize.Parcelize
import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import com.project.elt.data.model.filters.ProductItemSearch
import com.project.elt.data.model.filters.TopicsItem

@Parcelize
data class SearchBaseModel(

    @field:SerializedName("names")
	val names: List<ProductItemSearch?>? = null,

    @field:SerializedName("success")
	val success: Int? = null,

    @field:SerializedName("topics")
	val topics: List<TopicsItem?>? = null,

    @field:SerializedName("message")
	val message: String? = null,

    @field:SerializedName("error")
	val error: ErrorObj? = null
) : Parcelable

@Parcelize
data class ErrorObj(

	@field:SerializedName("code")
	val code: Int? = null,

	@field:SerializedName("message")
	val message: String? = null
) : Parcelable

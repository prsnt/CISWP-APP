package com.project.elt.data.model.filters

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import com.project.elt.data.model.search.ErrorObj
import kotlinx.parcelize.Parcelize

/*@Parcelize
sealed class CheckableItem( var isChecked: Boolean = false):Parcelable*/

interface CheckableItem {
	val isChecked: Boolean
	// Other common properties or methods can be declared here
}

@Parcelize
data class FiltersBaseModel(

	@field:SerializedName("languages")
	val languages: List<LanguagesItem>? = null,

	@field:SerializedName("developers")
	val developers: List<DevelopersItem>? = null,

	@field:SerializedName("countries")
	val countries: List<CountryItem>? = null,

	@field:SerializedName("success")
	val success: Int? = null,

	@field:SerializedName("topics")
	val topics: List<TopicsItem>? = null,

	@field:SerializedName("typeElts")
	val typeElts: List<TypeOfEltItem>? = null,

	@field:SerializedName("message")
	val message: String? = null,

	@field:SerializedName("error")
	val error: ErrorObj? = null,

	@field:SerializedName("category")
	val category: List<CategoryItem>? = null
):Parcelable

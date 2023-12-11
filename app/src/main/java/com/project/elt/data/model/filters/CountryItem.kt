package com.project.elt.data.model.filters

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class CountryItem(

	@field:SerializedName("country_name")
	val countryName: String? = null,

	@field:SerializedName("country_id")
	val countryId: Int? = null,
	override var isChecked: Boolean = false,

	): Parcelable, CheckableItem
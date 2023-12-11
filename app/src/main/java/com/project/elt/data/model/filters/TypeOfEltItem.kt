package com.project.elt.data.model.filters

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class TypeOfEltItem(

	@field:SerializedName("elt_type_id")
	val eltTypeId: Int? = null,

	@field:SerializedName("elt_type_name")
	val eltTypeName: String? = null,
	override var isChecked: Boolean = false,

	): Parcelable, CheckableItem
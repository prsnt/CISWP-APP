package com.project.elt.data.model.filters

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class ProductItemSearch(
    @field:SerializedName("name")
    val name: String? = null,

    @field:SerializedName("product_id")
    val product_id: Int? = null,
    override val isChecked: Boolean,

    ) : Parcelable,CheckableItem
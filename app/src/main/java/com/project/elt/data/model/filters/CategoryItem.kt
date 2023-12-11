package com.project.elt.data.model.filters

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class CategoryItem(

    @field:SerializedName("cat_name")
    val catName: String? = null,

    @field:SerializedName("cat_image")
    val catImage: String? = null,

    @field:SerializedName("cat_id")
    val catId: Int? = null,
    override var isChecked: Boolean = false,

    ) : Parcelable, CheckableItem
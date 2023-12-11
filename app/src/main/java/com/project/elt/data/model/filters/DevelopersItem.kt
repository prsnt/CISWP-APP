package com.project.elt.data.model.filters

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class DevelopersItem(

    @field:SerializedName("developer_name")
    val developerName: String? = null,

    @field:SerializedName("developer_id")
    val developerId: Int? = null,
    override var isChecked: Boolean = false,

    ) : Parcelable, CheckableItem
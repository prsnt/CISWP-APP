package com.project.elt.data.model.filters

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class LanguagesItem(

    @field:SerializedName("language_id")
    val languageId: Int? = null,

    @field:SerializedName("language_name")
    val languageName: String? = null,
    override var isChecked: Boolean = false,


    ) : Parcelable, CheckableItem
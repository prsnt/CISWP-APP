package com.project.elt.data.model.filters

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class TopicsItem(

    @field:SerializedName("topic_area")
    val topicArea: String? = null,

    @field:SerializedName("topic_id")
    val topicId: Int? = null,
    override var isChecked: Boolean = false,

    ) : Parcelable, CheckableItem
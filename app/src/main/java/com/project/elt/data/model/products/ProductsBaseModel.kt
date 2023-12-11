package com.project.elt.data.model.products

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import com.project.elt.data.model.filters.CategoryItem
import com.project.elt.data.model.filters.DevelopersItem
import com.project.elt.data.model.filters.CountryItem
import com.project.elt.data.model.filters.LanguagesItem
import com.project.elt.data.model.filters.TopicsItem
import com.project.elt.data.model.filters.TypeOfEltItem
import com.project.elt.data.model.search.ErrorObj
import kotlinx.parcelize.Parcelize

@Parcelize
data class ProductsBaseModel(

	@field:SerializedName("data")
	val data: List<ProductItem>? = null,

	@field:SerializedName("success")
	val success: Int? = null,

	@field:SerializedName("message")
	val message: String? = null,

	@field:SerializedName("error")
	val error: ErrorObj? = null
):Parcelable

@Parcelize
data class OshTopicsItem(

	@field:SerializedName("osh_topic_name")
	val oshTopicName: String? = null,

	@field:SerializedName("osh_topic_id")
	val oshTopicId: Int? = null
):Parcelable

@Parcelize
data class ProductItem(

	@field:SerializedName("country")
	val country: List<CountryItem?>? = null,

	@field:SerializedName("website")
	val website: String? = null,

	@field:SerializedName("occupation")
	val occupation: List<OccupationItem?>? = null,

	@field:SerializedName("developers")
	val developers: List<DevelopersItem?>? = null,

	@field:SerializedName("type_of_elt")
	val typeOfElt: List<TypeOfEltItem?>? = null,

	@field:SerializedName("language")
	val language: List<LanguagesItem?>? = null,

	@field:SerializedName("payment_type")
	val paymentType: String? = null,

	@field:SerializedName("Date_of_Release_and_Version_Number")
	val Date_of_Release_and_Version_Number: String? = null,

	@field:SerializedName("Certification_accreditation_for_completion")
	val Certification_accreditation_for_completion: String? = null,

	@field:SerializedName("Duration_min")
	val Duration_min: String? = null,

	@field:SerializedName("product_id")
	val productId: Int? = null,

	@field:SerializedName("name")
	val name: String? = null,

	@field:SerializedName("description")
	val description: String? = null,

	@field:SerializedName("topic")
	val topic: List<TopicsItem?>? = null,

	@field:SerializedName("category")
	val category: List<CategoryItem?>? = null,

	@field:SerializedName("osh_topics")
	val oshTopics: List<OshTopicsItem?>? = null,

	@field:SerializedName("tasks")
	val tasks: List<TasksItem?>? = null,

	@field:SerializedName("hardware")
	val hardware: List<HardwareItem?>? = null
):Parcelable

@Parcelize
data class OccupationItem(

	@field:SerializedName("occupation_name")
	val occupationName: String? = null,

	@field:SerializedName("occupation_id")
	val occupationId: Int? = null
):Parcelable

@Parcelize
data class TasksItem(

	@field:SerializedName("task_name")
	val taskName: String? = null,

	@field:SerializedName("task_id")
	val taskId: Int? = null
):Parcelable

@Parcelize
data class HardwareItem(

	@field:SerializedName("hardware_name")
	val hardwareName: String? = null,

	@field:SerializedName("hardware_id")
	val hardwareId: Int? = null
):Parcelable

package com.project.elt.utils

import android.view.View
import android.widget.RelativeLayout
import androidx.appcompat.widget.AppCompatTextView
import androidx.appcompat.widget.LinearLayoutCompat
import androidx.databinding.BindingAdapter
import com.project.elt.data.model.filters.CategoryItem
import com.project.elt.data.model.filters.LanguagesItem
import com.project.elt.data.model.filters.CountryItem
import com.project.elt.data.model.filters.TypeOfEltItem

class TextUtils {
    companion object {
        const val filtersAPI = "getFilters"
        const val productsAPI = "getProducts"
        const val searchSuggestionAPI = "search_topic_and_name"


        const val PAGE_NUMBER = "page_number"
        const val PAGE_SIZE = "page_size"
        const val SEARCH_TEXT = "search_text"
        const val CAT_ID = "cat_id"
        const val PRODUCT_ID = "product_ic"
        const val TOPIC_ID = "topic_id"
        const val TYPE_ELT_ID = "type_elt_id"
        const val DEVELOPER_ID = "developer_id"
        const val COUNTRY_ID = "country_id"
        const val LANGUAGE_ID = "country_id"

        const val CONTACT_US_URL = "https://research.conestogac.on.ca/canadian-institute-for-safety-wellness-performance#:~:text=Next-,Contact%20us,-Connect%20with%20the"
        const val PRIVACY_URL = "https://www.conestogac.on.ca/about/corporate-information/policies"

        fun isPaid(payment_type: String): Boolean {
            return payment_type == "Paid"
        }

        @JvmStatic
        @BindingAdapter("android:visibiliti")
        fun View.setVisibiliti(visible: Boolean) {
            visibility = if (visible) {
                View.VISIBLE
            } else {
                View.GONE
            }
        }

        @JvmStatic
        @BindingAdapter("android:setString")
        fun AppCompatTextView.setCategoryString(list: List<CategoryItem?>?) {
            var categoryStr = ""
            if (list != null) {
                for (category in list)
                    categoryStr += if (categoryStr.isEmpty())
                        category?.catName
                    else
                        " | " + category?.catName
            }
            text = categoryStr
            isSelected = true
        }

        @JvmStatic
        @BindingAdapter("android:setString")
        fun AppCompatTextView.setTypeELTString(list: List<TypeOfEltItem?>?) {
            var categoryStr = ""
            if (list != null) {
                for (category in list)
                    categoryStr += if (categoryStr.isEmpty())
                        category?.eltTypeName
                    else
                        " | " + category?.eltTypeName
            }
            text = categoryStr
            isSelected = true
        }

        @JvmStatic
        @BindingAdapter("android:setString")
        fun AppCompatTextView.setCountryString(list: List<CountryItem?>?) {
            var categoryStr = ""
            if (list != null) {
                for (category in list)
                    categoryStr += if (categoryStr.isEmpty())
                        category?.countryName
                    else
                        " | " + category?.countryName
            }
            text = categoryStr
            isSelected = true
        }

        @JvmStatic
        @BindingAdapter("android:setString")
        fun AppCompatTextView.setLanguageString(list: List<LanguagesItem?>?) {
            var categoryStr = ""
            if (list != null) {
                for (category in list)
                    categoryStr += if (categoryStr.isEmpty())
                        category?.languageName
                    else
                        " | " + category?.languageName
            }
            text = categoryStr
            isSelected = true
        }

        @JvmStatic
        @BindingAdapter("android:shouldVisible")
        fun LinearLayoutCompat.isVisible(str: String?) {
            visibility = if (str.isNullOrEmpty())
                View.GONE
            else
                View.VISIBLE
        }

        @JvmStatic
        @BindingAdapter("android:shouldVisible")
        fun RelativeLayout.isVisible(str: String?) {
            visibility = if (str.isNullOrEmpty())
                View.GONE
            else
                View.VISIBLE
        }

        @JvmStatic
        @BindingAdapter("android:shouldVisible")
        fun RelativeLayout.isVisibleList(list: List<Any>?) {
            if(list !=null)
                View.VISIBLE
            else
                View.GONE
        }
    }
}
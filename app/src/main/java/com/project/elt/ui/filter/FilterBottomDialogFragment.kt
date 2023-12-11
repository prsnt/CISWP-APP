package com.project.elt.ui.filter

import android.app.Dialog
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.View.OnClickListener
import android.view.ViewGroup
import android.view.Window
import androidx.appcompat.widget.AppCompatButton
import androidx.appcompat.widget.AppCompatTextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipGroup
import com.google.gson.Gson
import com.project.elt.R
import com.project.elt.data.model.filters.CategoryItem
import com.project.elt.data.model.filters.CheckableItem
import com.project.elt.data.model.filters.FiltersBaseModel
import com.project.elt.data.model.filters.TopicsItem
import com.project.elt.data.model.filters.CountryItem
import com.project.elt.data.model.filters.DevelopersItem
import com.project.elt.data.model.filters.LanguagesItem
import com.project.elt.data.model.filters.TypeOfEltItem
import com.project.elt.databinding.FragmentFilterBottomDialogBinding
import com.project.elt.ui.dashboard.DashboardActivity
import com.project.elt.ui.dashboard.DashboardActivity.Companion.FILTER_MODEL
import com.project.elt.ui.search.SearchCompletedActivity
import com.project.elt.utils.Constant.CATEGORY_FILTER
import com.project.elt.utils.Constant.COUNTRY_FILTER
import com.project.elt.utils.Constant.DEVELOPER_FILTER
import com.project.elt.utils.Constant.LANGUAGE_FILTER
import com.project.elt.utils.Constant.TOPICAREA_FILTER
import com.project.elt.utils.Constant.TYPEELT_FILTER
import com.project.elt.utils.setDialogSize

class FilterBottomDialogFragment(private val filtersBaseModel: FiltersBaseModel) :
    BottomSheetDialogFragment(), OnClickListener {

    private val selectedCategories = mutableListOf<String>()
    private val selectedCountries = mutableListOf<String>()
    private val selectedDevelopers = mutableListOf<String>()
    private val selectedTopicAreas = mutableListOf<String>()
    private val selectedTypeELTs = mutableListOf<String>()
    private val selectedLanguages = mutableListOf<String>()

    private lateinit var filterBottomDialogBinding: FragmentFilterBottomDialogBinding
    private val filterMap = mapOf(
        CATEGORY_FILTER to Pair(R.string.category, filtersBaseModel.category),
        TYPEELT_FILTER to Pair(R.string.type_elt, filtersBaseModel.typeElts),
        DEVELOPER_FILTER to Pair(R.string.developer, filtersBaseModel.developers),
        COUNTRY_FILTER to Pair(R.string.country, filtersBaseModel.countries),
        LANGUAGE_FILTER to Pair(R.string.language, filtersBaseModel.languages),
        TOPICAREA_FILTER to Pair(R.string.topic_area, filtersBaseModel.topics)
    )

    companion object {
        fun newInstance(filtersBaseModel: FiltersBaseModel): FilterBottomDialogFragment {
            return FilterBottomDialogFragment(filtersBaseModel)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        filterBottomDialogBinding = FragmentFilterBottomDialogBinding.inflate(inflater)
        return filterBottomDialogBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setListners()
        addChips()
    }

    private fun setListners() {
        filterBottomDialogBinding.cardViewAddTopicArea.setOnClickListener(this)
        filterBottomDialogBinding.cardViewAddLanguage.setOnClickListener(this)
        filterBottomDialogBinding.cardViewAddCountry.setOnClickListener(this)
        filterBottomDialogBinding.cardViewAddDeveloper.setOnClickListener(this)
        filterBottomDialogBinding.cardViewAddTypeELT.setOnClickListener(this)
        filterBottomDialogBinding.cardViewAddCategory.setOnClickListener(this)
        filterBottomDialogBinding.btnApplyFilter.setOnClickListener {
            Log.d("PRT", "setListners: " + Gson().toJson(filtersBaseModel).toString())
            val intent = Intent(requireActivity(), SearchCompletedActivity::class.java)
            intent.putExtra(
                FILTER_MODEL,
                filtersBaseModel
            )
            intent.putStringArrayListExtra("selectedCategories", ArrayList(selectedCategories))
            intent.putStringArrayListExtra("selectedCountries", ArrayList(selectedCountries))
            intent.putStringArrayListExtra("selectedDevelopers", ArrayList(selectedDevelopers))
            intent.putStringArrayListExtra("selectedTopicAreas", ArrayList(selectedTopicAreas))
            intent.putStringArrayListExtra("selectedTypeELTs", ArrayList(selectedTypeELTs))
            intent.putStringArrayListExtra("selectedLanguages", ArrayList(selectedLanguages))
            startActivity(intent)
            if(this.requireActivity() == SearchCompletedActivity::class.java)
                this.requireActivity().finish()
            dismiss()
        }
    }

    private fun dialogFilter(filterType: Int) {
        val dialog = Dialog(requireActivity())
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setContentView(R.layout.filter_more)
        dialog.setDialogSize(0.9f)
        dialog.setCancelable(false)

        val title = dialog.findViewById<AppCompatTextView>(R.id.tvFilterName)
        val rvFilter = dialog.findViewById<RecyclerView>(R.id.recyclerFilterMore)
        val btnApply = dialog.findViewById<AppCompatButton>(R.id.btnApply)

        rvFilter.layoutManager = LinearLayoutManager(requireActivity())

        val (titleResId, items) = filterMap[filterType] ?: return // Get title resource ID and items
        title.text = getString(titleResId)

        val filterMoreAdapter = items?.let { FilterMoreAdapter(requireActivity(), it) }
        rvFilter.adapter = filterMoreAdapter

        btnApply.setOnClickListener {
            addChips()
            dialog.dismiss()
        }

        dialog.show()
    }

    private fun addChips() {
        addChipsToChipGroup(
            filterBottomDialogBinding.chipGroupCategory,
            filtersBaseModel.category!!
        ) { item ->
            (item as CategoryItem).apply {
                if (isChecked) selectedCategories.add(item.catId.toString())
            }
            item.catName
        }
        addChipsToChipGroup(
            filterBottomDialogBinding.chipGroupCountry,
            filtersBaseModel.countries!!
        ) { item ->
            (item as CountryItem).apply {
                if (isChecked) selectedCountries.add(item.countryId.toString())
            }
            item.countryName
        }
        addChipsToChipGroup(
            filterBottomDialogBinding.chipGroupDeveloper,
            filtersBaseModel.developers!!
        ) { item ->
            (item as DevelopersItem).apply {
                if (isChecked) selectedDevelopers.add(item.developerId.toString())
            }
            item.developerName
        }
        addChipsToChipGroup(
            filterBottomDialogBinding.chipGroupTopicArea,
            filtersBaseModel.topics!!
        ) { item ->
            (item as TopicsItem).apply {
                if (isChecked) selectedTopicAreas.add(item.topicId.toString())
            }
            item.topicArea
        }
        addChipsToChipGroup(
            filterBottomDialogBinding.chipGroupTypeELT,
            filtersBaseModel.typeElts!!
        ) { item ->
            (item as TypeOfEltItem).apply {
                if (isChecked) selectedTypeELTs.add(item.eltTypeId.toString())
            }
            item.eltTypeName
        }
        addChipsToChipGroup(
            filterBottomDialogBinding.chipGroupLanguage,
            filtersBaseModel.languages!!
        ) { item ->
            (item as LanguagesItem).apply {
                if (isChecked) selectedLanguages.add(item.languageId.toString())
            }
            item.languageName
        }
    }

    // Function to create and add chips to ChipGroup
    private fun addChipsToChipGroup(
        chipGroup: ChipGroup,
        items: List<CheckableItem>, // Change 'Any' to the type of your item (e.g., Category, Country, Developer, etc.)
        chipTextProvider: (CheckableItem) -> String? // Change 'Any' to the type of your item
    ) {
        chipGroup.removeAllViews()
        for (item in items) {
            if (item.isChecked) {
                val chip = Chip(requireActivity()) // Create a new chip programmatically
                chip.text =
                    chipTextProvider(item) // Set chip text dynamically using the provided lambda
                chip.isChecked = true
                chip.isCheckable = true // Set if chip is checkable (true/false)
                chipGroup.addView(chip)

            }
        }
    }

    override fun onClick(p0: View?) {
        when (p0?.id) {
            R.id.cardViewAddCategory -> {
                dialogFilter(CATEGORY_FILTER)
            }

            R.id.cardViewAddTypeELT -> {
                dialogFilter(TYPEELT_FILTER)
            }

            R.id.cardViewAddDeveloper -> {
                dialogFilter(DEVELOPER_FILTER)
            }

            R.id.cardViewAddCountry -> {
                dialogFilter(COUNTRY_FILTER)
            }

            R.id.cardViewAddLanguage -> {
                dialogFilter(LANGUAGE_FILTER)
            }

            R.id.cardViewAddTopicArea -> {
                dialogFilter(TOPICAREA_FILTER)
            }
        }
    }
}
package com.project.elt.ui.search

import android.adservices.topics.Topic
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.project.App
import com.project.elt.R
import com.project.elt.data.model.filters.CheckableItem
import com.project.elt.data.model.filters.ProductItemSearch
import com.project.elt.data.model.filters.TopicsItem
import com.project.elt.databinding.ActivitySearchStartBinding
import com.project.elt.ui.product_detail.ProductDetailActivity
import com.project.elt.ui.products.fragments.ProductsFragment
import com.project.elt.utils.Status
import com.project.elt.utils.viewModeFactory

class SearchStartActivity : AppCompatActivity(), (CheckableItem) -> Unit {
    private var isTopics: Boolean = false
    private var isNames: Boolean = false
    private lateinit var binding: ActivitySearchStartBinding
    private var adapterSearch = SearchSuggestionAdapter(this)
    private var recentSearches: MutableList<String> = mutableListOf()
    val viewModel: SearchStartViewModel by viewModels {
        viewModeFactory {
            SearchStartViewModel(App.appModule.mainRepository)
        }
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySearchStartBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.activity = this
        getRecentSearches()
        setData()
        setupListners()
    }

    private fun setData() {
        setVisibility(true)
        binding.rviewRecentSearches.apply {
            layoutManager = LinearLayoutManager(this@SearchStartActivity)
            adapter = SearchHistoryAdapter(recentSearches) { searchText ->
                binding.tvSearch.setText(searchText)
                binding.tvSearch.setSelection(binding.tvSearch.length())
            }
            addItemDecoration(DividerItemDecoration(this.context, DividerItemDecoration.VERTICAL))
        }
        binding.rviewSearchResult.apply {
            layoutManager = LinearLayoutManager(this@SearchStartActivity)
            adapter = adapterSearch
            addItemDecoration(DividerItemDecoration(this.context, DividerItemDecoration.VERTICAL))
        }
    }

    private fun setupListners() {
        binding.tvSearch.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun afterTextChanged(editable: Editable?) {
                val query = editable.toString()
                if (query.isNotEmpty()) {
                    viewModel.getSearchSuggestions(query)
                } else
                    setVisibility(true)
            }
        })
        viewModel.responseSearchSuggestion.observe(this, Observer { it ->
            Log.d("PRT", "listners: " + it.data.toString())
            when (it.status) {
                Status.SUCCESS -> {
                    it.data.let {
                        if (!it?.topics.isNullOrEmpty()) {
                            isTopics = true
                            adapterSearch.setListOne(it?.topics)
                        } else
                            isTopics = false
                        if (!it?.names.isNullOrEmpty()) {
                            isNames = true
                            adapterSearch.setListTwo(it?.names)
                        } else
                            isNames = false
                        setVisibility(false)
                    }
                }

                Status.LOADING -> {

                }

                Status.ERROR -> {
                    isNames = false
                    isTopics = false
                    setVisibility(false)
                    Log.d("TAG", "setupListners: $it")
                }
            }
        })
    }

    // Function to add a new search
    private fun addRecentSearch(query: String) {
        recentSearches.add(0, query)
        if (recentSearches.size > 10) {
            recentSearches.removeAt(recentSearches.size - 1)
        }
        saveRecentSearches(recentSearches)
    }

    // Save the updated set of recent searches in SharedPreferences
    private fun saveRecentSearches(recentSearches: MutableList<String>) {
        val sharedPreferences = App.appModule.sharedPreferences // Get SharedPreferences reference
        val editor = sharedPreferences.edit()

        // Convert the MutableList to a Set<String> before saving
        val searchSet = recentSearches.toSet()
        editor.putStringSet("recentSearches", searchSet)
        editor.apply()
    }

    private fun redirectActivity(model: TopicsItem) {
        val listTopics = ArrayList<TopicsItem>()
        listTopics.add(model)
        startActivity(
            Intent(this, SearchCompletedActivity::class.java).putStringArrayListExtra(
                "selectedTopicAreas",
                getTopicsString(listTopics)
            )
        )
    }

    private fun getTopicsString(listTopics: ArrayList<TopicsItem>): ArrayList<String> {
        val list = ArrayList<String>()
        for (item in listTopics) {
            list.add(item.topicId.toString())
        }
        return list
    }

    private fun redirectActivity(model: ProductItemSearch) {
        startActivity(
            Intent(this, ProductDetailActivity::class.java).putExtra(
                "product_id",
                model.product_id
            )
        )
    }

    private fun getRecentSearches(): MutableList<String> {
        binding.tvSearch.requestFocus()
        val sharedPreferences = App.appModule.sharedPreferences
        recentSearches =
            sharedPreferences.getStringSet("recentSearches", mutableSetOf())?.toMutableList()!!
        return recentSearches
    }

    private fun setVisibility(isRecent: Boolean) {
        if (isRecent) {
            binding.tvRecentSearches.text = getString(R.string.recent_searches)
            binding.rviewSearchResult.visibility = View.GONE
            binding.rviewRecentSearches.visibility =
                if (recentSearches.isNotEmpty()) View.VISIBLE else View.GONE
            binding.tvNoDataFound.visibility =
                if (recentSearches.isEmpty()) View.VISIBLE else View.GONE
            binding.tvNoDataFound.text =
                if (recentSearches.isEmpty()) getString(R.string.no_recent_search) else ""

        } else {
            binding.tvRecentSearches.text = getString(R.string.search_result)
            binding.rviewSearchResult.visibility =
                if (isTopics || isNames) View.VISIBLE else View.GONE
            binding.rviewRecentSearches.visibility = View.GONE
            binding.tvNoDataFound.visibility =
                if (!isTopics && !isNames) View.VISIBLE else View.GONE
            binding.tvNoDataFound.text =
                if (!isTopics && !isNames) getString(R.string.no_data_found) else ""

        }
    }

    override fun invoke(model: CheckableItem) {
        if (model is TopicsItem)
            model.topicArea?.let {
                addRecentSearch(it)
                redirectActivity(model)
            }
        else if (model is ProductItemSearch)
            model.name?.let {
                addRecentSearch(it)
                redirectActivity(model)
            }
    }
}
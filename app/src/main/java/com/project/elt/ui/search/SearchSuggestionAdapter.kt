package com.project.elt.ui.search

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.project.elt.data.model.filters.CheckableItem
import com.project.elt.data.model.filters.ProductItemSearch
import com.project.elt.data.model.filters.TopicsItem
import com.project.elt.databinding.CellSearchSuggestionBinding

class SearchSuggestionAdapter(val onClick: (CheckableItem) -> Unit) :
    RecyclerView.Adapter<SearchSuggestionAdapter.MyViewHolder>() {

    companion object {
        private const val VIEW_TYPE_TOPIC = 1
        private const val VIEW_TYPE_PRODUCT = 2
    }

    private val listTopic = mutableListOf<TopicsItem>()
    private val listProduct = mutableListOf<ProductItemSearch>()
    private lateinit var binding: CellSearchSuggestionBinding

    class MyViewHolder(val binding: CellSearchSuggestionBinding) :
        RecyclerView.ViewHolder(binding.root) {

    }

    override fun getItemViewType(position: Int): Int {
        return if (position < listTopic.size) {
            VIEW_TYPE_TOPIC
        } else {
            VIEW_TYPE_PRODUCT
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        binding =
            CellSearchSuggestionBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return listTopic.size + listProduct.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {

        when (holder.itemViewType) {
            VIEW_TYPE_TOPIC -> {
                holder.binding.tvSuggestion.text = listTopic[position].topicArea
                holder.binding.tvSearchFromSuggestion.text = "from TOPICS"
                holder.itemView.setOnClickListener {
                    onClick(listTopic[position])
                }
            }

            VIEW_TYPE_PRODUCT -> {
                holder.binding.tvSuggestion.text = listProduct[position - listTopic.size].name
                holder.binding.tvSearchFromSuggestion.text = "from PRODUCTS"
                holder.itemView.setOnClickListener {
                    onClick(listProduct[position - listTopic.size])
                }
            }
        }
    }

    fun setListOne(items: List<TopicsItem?>?) {
        listTopic.clear()
        items.let { topicsItems ->
            topicsItems?.filterNotNull()?.let { listTopic.addAll(it) }
            notifyDataSetChanged()
        }
    }

    fun setListTwo(items: List<ProductItemSearch?>?) {
        listProduct.clear()
        items.let { topicsItems ->
            topicsItems?.filterNotNull()?.let { listProduct.addAll(it) }
            notifyDataSetChanged()
        }
    }
}
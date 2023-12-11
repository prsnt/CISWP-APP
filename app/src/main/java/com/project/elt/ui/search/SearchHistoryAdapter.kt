package com.project.elt.ui.search

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.project.elt.databinding.CellRecentSearchBinding

class SearchHistoryAdapter(val list: MutableList<String>, val itemClick: (String) -> Unit) :
    RecyclerView.Adapter<SearchHistoryAdapter.MyViewHolder>() {
    inner class MyViewHolder(val binding: CellRecentSearchBinding) :
        RecyclerView.ViewHolder(binding.root) {
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding =
            CellRecentSearchBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.binding.tvRecentSearch.text = list[position]
        holder.itemView.setOnClickListener {
            itemClick(list[position])
        }
    }
}
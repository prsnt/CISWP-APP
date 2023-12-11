package com.project.elt.ui.filter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.CompoundButton
import androidx.recyclerview.widget.RecyclerView
import com.project.elt.data.model.filters.CategoryItem
import com.project.elt.data.model.filters.LanguagesItem
import com.project.elt.data.model.filters.TopicsItem
import com.project.elt.data.model.filters.CountryItem
import com.project.elt.data.model.filters.DevelopersItem
import com.project.elt.data.model.filters.TypeOfEltItem
import com.project.elt.databinding.CellFilterListBinding

class FilterMoreAdapter<T>(val context: Context, var list: List<T>) :
    RecyclerView.Adapter<FilterMoreAdapter.MyViewHolder>() {
    lateinit var binding: CellFilterListBinding
    private var originalList: List<T> = ArrayList(list)

    class MyViewHolder(val binding: CellFilterListBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MyViewHolder {
        binding = CellFilterListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        if (list[position] is CategoryItem) {
            holder.binding.tvCellFilter.text = (list[position] as CategoryItem).catName
            holder.binding.chkboxCellFilter.isChecked = (list[position] as CategoryItem).isChecked
        } else if (list[position] is TypeOfEltItem) {
            holder.binding.tvCellFilter.text = (list[position] as TypeOfEltItem).eltTypeName
            holder.binding.chkboxCellFilter.isChecked = (list[position] as TypeOfEltItem).isChecked
        } else if (list[position] is DevelopersItem) {
            holder.binding.tvCellFilter.text =
                (list[position] as DevelopersItem).developerName
            holder.binding.chkboxCellFilter.isChecked = (list[position] as DevelopersItem).isChecked
        } else if (list[position] is CountryItem) {
            holder.binding.tvCellFilter.text = (list[position] as CountryItem).countryName
            holder.binding.chkboxCellFilter.isChecked = (list[position] as CountryItem).isChecked
        } else if (list[position] is LanguagesItem) {
            holder.binding.tvCellFilter.text =
                (list[position] as LanguagesItem).languageName
            holder.binding.chkboxCellFilter.isChecked = (list[position] as LanguagesItem).isChecked
        } else if (list[position] is TopicsItem) {
            holder.binding.tvCellFilter.text = (list[position] as TopicsItem).topicArea
            holder.binding.chkboxCellFilter.isChecked = (list[position] as TopicsItem).isChecked
        }
        holder.binding.chkboxCellFilter.setOnCheckedChangeListener(object : CompoundButton.OnCheckedChangeListener {
            override fun onCheckedChanged(p0: CompoundButton?, p1: Boolean) {
                selectLogic(holder)
            }
        })
    }

    override fun getItemCount(): Int {
        return list.size
    }

    private fun selectLogic(holder: MyViewHolder) {
        if (list[holder.adapterPosition] is CategoryItem)
            (list[holder.adapterPosition] as CategoryItem).isChecked =
                holder.binding.chkboxCellFilter.isChecked
        else if (list[holder.adapterPosition] is TypeOfEltItem)
            (list[holder.adapterPosition] as TypeOfEltItem).isChecked = true
        else if (list[holder.adapterPosition] is DevelopersItem)
            (list[holder.adapterPosition] as DevelopersItem).isChecked =
                holder.binding.chkboxCellFilter.isChecked
        else if (list[holder.adapterPosition] is CountryItem)
            (list[holder.adapterPosition] as CountryItem).isChecked = true
        else if (list[holder.adapterPosition] is LanguagesItem)
            (list[holder.adapterPosition] as LanguagesItem).isChecked =
                holder.binding.chkboxCellFilter.isChecked
        else if (list[holder.adapterPosition] is TopicsItem)
            (list[holder.adapterPosition] as TopicsItem).isChecked =
                holder.binding.chkboxCellFilter.isChecked

    }

    fun resetAdapterToDefault() {
        list = ArrayList(originalList)
        notifyDataSetChanged() // Notify adapter of data change
    }
}
package com.project.elt.ui.dashboard

import android.content.Context
import android.graphics.BitmapFactory
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatImageView
import androidx.recyclerview.widget.RecyclerView
import com.project.elt.data.model.filters.CategoryItem
import com.project.elt.databinding.CellDashboardBinding


class DashboardAdapter(
    val context: Context,
    private val onClickListener: OnClickListener
) : RecyclerView.Adapter<DashboardAdapter.MyViewHolder>() {
    private var list = ArrayList<CategoryItem>()
    private lateinit var binding: CellDashboardBinding

    class MyViewHolder(val binding: CellDashboardBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        binding =
            CellDashboardBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.binding.tvCategoryCell.text = list[position].catName
        holder.itemView.setOnClickListener { onClickListener.onClick(list[position],position) }
        list[position].catImage?.let { setImage(it,holder.binding.ivCategoryCell) }
    }

    private fun setImage(imagename: String, view: AppCompatImageView) {
        val PACKAGE_NAME: String = context.packageName
        val imgId: Int =
            context.resources.getIdentifier("$PACKAGE_NAME:drawable/$imagename", null, null)
        println("IMG ID :: $imgId")
        println("PACKAGE_NAME :: $PACKAGE_NAME")
        view.setImageBitmap(BitmapFactory.decodeResource(context.resources, imgId))
    }

    fun setList(list: List<CategoryItem?>?) {
        this.list = list as ArrayList<CategoryItem>
        notifyDataSetChanged()
    }

    class OnClickListener(val clickListener: (category: CategoryItem, position: Int) -> Unit) {
        fun onClick(category: CategoryItem, position: Int) = clickListener(category,position)
    }
}
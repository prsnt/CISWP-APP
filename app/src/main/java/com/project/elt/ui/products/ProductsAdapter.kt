package com.project.elt.ui.products


import android.content.res.ColorStateList
import android.os.Handler
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.shape.CornerFamily
import com.google.android.material.shape.CornerSize
import com.google.android.material.shape.MaterialShapeDrawable
import com.google.android.material.shape.ShapeAppearanceModel
import com.project.elt.R
import com.project.elt.data.model.products.ProductItem
import com.project.elt.databinding.CellLoadingBinding
import com.project.elt.databinding.CellProductBinding
import com.project.elt.utils.TextUtils


class ProductsAdapter(
    private val context: FragmentActivity,
    val onClickListener: OnClickListener
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private lateinit var binding: ViewDataBinding
    var product_list = ArrayList<ProductItem?>()
    private val VIEW_TYPE_ITEM = 0
    private val VIEW_TYPE_LOADING = 1


    class MyViewHolder(val binding: CellProductBinding) : RecyclerView.ViewHolder(binding.root) {


    }

    class LoadingViewHolder(val binding: CellLoadingBinding) : RecyclerView.ViewHolder(binding.root) {


    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): RecyclerView.ViewHolder {
        return if (viewType == VIEW_TYPE_ITEM) {
            binding = CellProductBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            MyViewHolder(binding as CellProductBinding)
        } else {
            binding = CellLoadingBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            LoadingViewHolder(binding as CellLoadingBinding)
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val productItem = product_list[position]


        if(holder is MyViewHolder) {
            holder.binding.model = productItem

            if (productItem?.paymentType?.let { TextUtils.isPaid(it) } == true) {
                updateUI(holder.binding, false)
            } else
                updateUI(holder.binding, true)

            var categoryStr = ""
            for (category in productItem?.category!!)
                categoryStr += if (categoryStr.isEmpty())
                    category?.catName
                else
                    " | " + category?.catName

            holder.binding.tvCategory.text = categoryStr
            holder.itemView.setOnClickListener { onClickListener.onClick(productItem) }
        }
    }

    override fun getItemCount(): Int {
        return product_list.size
    }

    override fun getItemViewType(position: Int): Int {
        return if (product_list[position] == null) VIEW_TYPE_LOADING else VIEW_TYPE_ITEM
    }

    //setting up UI based on the model response
    private fun updateUI(cellProductBinding: CellProductBinding, isFree: Boolean) {
        val leftShapePathModel = ShapeAppearanceModel().toBuilder()
        // You can change style and size

        leftShapePathModel.setTopRightCorner(
            CornerFamily.ROUNDED,
            CornerSize { return@CornerSize 8F })
        val bg = MaterialShapeDrawable(leftShapePathModel.build())
        if (isFree) {
            bg.fillColor = ColorStateList.valueOf(
                context.resources.getColor(R.color.free)
            )
            cellProductBinding.tvFreePaid.text = context.getString(R.string.free)
        } else {
            bg.fillColor = ColorStateList.valueOf(
                context.resources.getColor(R.color.paid)
            )
            cellProductBinding.tvFreePaid.text = context.getString(R.string.paid)
        }
        cellProductBinding.cardViewFreePaid.background = bg
    }

    fun addLoadingView() {
        //Add loading item
        Log.d("PRT1", "addLoadingView: ")
        Handler().post {
            product_list.add(product_list.size,null)
            notifyItemInserted(product_list.size - 1)
        }
    }

    fun removeLoadingView() {
        Log.d("PRT1", "removeLoadingView: ")
        //Remove loading item
        if (product_list.size != 0) {
            product_list.removeAt(product_list.size - 1)
            notifyItemRemoved(product_list.size)
        }
    }


    fun setList(list: ArrayList<ProductItem?>) {
        this.product_list.addAll(list)
        notifyDataSetChanged()
    }

    class OnClickListener(val clickListener: (product: ProductItem) -> Unit) {
        fun onClick(product: ProductItem) = clickListener(product)
    }
}
package com.aboback.wanandroidjetpack.rv

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView
import com.aboback.base.rv.BaseItemViewModel

/**
 * Created by jhb on 2020-01-19.
 */
open class BaseRecyclerViewAdapter(@LayoutRes private val layoutId: Int, private val mData: List<BaseItemViewModel>) : RecyclerView.Adapter<BaseViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder {
        val binding = DataBindingUtil.inflate<ViewDataBinding>(LayoutInflater.from(parent.context), layoutId, parent, false)

        return BaseViewHolder(binding)
    }

    override fun getItemViewType(position: Int) = position

    override fun getItemCount(): Int = if (mData.isEmpty()) 0 else mData.size

    override fun onBindViewHolder(holder: BaseViewHolder, position: Int) {
        val binding = DataBindingUtil.getBinding<ViewDataBinding>(holder.itemView)
        binding?.setVariable(mData[position].id(), mData[position])
        binding?.executePendingBindings()
    }
}
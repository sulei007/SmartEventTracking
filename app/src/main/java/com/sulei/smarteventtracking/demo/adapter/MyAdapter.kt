package com.sulei.smarteventtracking.demo.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.sulei.smarteventtracking.data.EventTrackBean
import com.sulei.smarteventtracking.demo.bean.CustomBean
import com.sulei.smarteventtracking.demo.bean.Info
import com.sulei.smarteventtracking.demo.databinding.RecycleViewItemBinding
import com.sulei.smarteventtracking.helper.ExposureTrackHelper

class MyAdapter(val name: String, val mList: ArrayList<Info>) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        println("name $name")

        return ItemViewHolder(
            RecycleViewItemBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is ItemViewHolder) {
            holder.bindData(position)
        }
    }

    override fun getItemCount(): Int {
        return mList.size
    }

    inner class ItemViewHolder(private val binding: RecycleViewItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bindData(position: Int) {

            binding.root.setOnLongClickListener {
                mList.removeAt(position)
                notifyItemRemoved(position)
                true
            }
            val item = mList[position]
            val left = item.list?.get(0)
            binding.tv1.text = left?.name

            ExposureTrackHelper.injectExposureUnit(
                binding.layout1,
                EventTrackBean(left?.id.toString(), name, CustomBean(left?.name ?: "")),
                binding.llContent
            )

            val right = item.list?.get(1)
            binding.tv2.text = right?.name
            ExposureTrackHelper.injectExposureUnit(
                binding.layout2,
                EventTrackBean(right?.id.toString(), name, CustomBean(right?.name ?: "")),
                binding.llContent
            )
            binding.executePendingBindings()
        }
    }

}
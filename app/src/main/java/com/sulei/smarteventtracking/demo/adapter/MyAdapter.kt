package com.sulei.smarteventtracking.demo.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.sulei.smarteventtracking.data.EventTrackBean
import com.sulei.smarteventtracking.demo.bean.AppEventTrackBean
import com.sulei.smarteventtracking.demo.bean.CartBean
import com.sulei.smarteventtracking.demo.databinding.RecycleViewItemBinding
import com.sulei.smarteventtracking.helper.ClickTrackHelper
import com.sulei.smarteventtracking.helper.ExposureTrackHelper

class MyAdapter(val pageName: String, val mList: ArrayList<CartBean>) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
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
            val item = mList[position]
            val left = item.list[0]
            binding.tv1.text = left.name
            binding.layout1.setOnClickListener {
                ClickTrackHelper.executeClickTrack(it)
            }

            ExposureTrackHelper.injectExposureUnit(
                binding.layout1,
                EventTrackBean(
                    left.id.toString(),
                    pageName,
                    AppEventTrackBean(left.id.toString(), left.name)
                )
            )

            val right = item.list[1]
            binding.tv2.text = right.name
            binding.layout2.setOnClickListener {
                ClickTrackHelper.executeClickTrack(it)
            }

            ExposureTrackHelper.injectExposureUnit(
                binding.layout2,
                EventTrackBean(
                    right.id.toString(),
                    pageName,
                    AppEventTrackBean(right.id.toString(), right.name)
                )
            )
            binding.executePendingBindings()
        }
    }

}
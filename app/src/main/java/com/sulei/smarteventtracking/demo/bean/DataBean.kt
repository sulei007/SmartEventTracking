package com.sulei.smarteventtracking.demo.bean

data class CartBean(val list: ArrayList<ItemBean>)

data class ItemBean(val id: Int, val name: String) {
}
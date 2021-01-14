package com.example.todolistkotlin.activity.model

import com.example.todolistkotlin.activity.classes.Category
import java.util.*


object CategoryModel {
    private var items: ArrayList<Category>? = null
    fun getItems(): ArrayList<Category>? {
        if (items == null) {
            items = ArrayList<Category>()
            items!!.add(Category("Category_Home", "Home", 1))
            items!!.add(Category("Category_Working", "Work", 1))
            items!!.add(Category("Category_Studying", "Study", 1))
            items!!.add(Category("Category_Sport", "Sport", 1))
            items!!.add(Category("Category_Entertainment", "Entertainment", 1))
            items!!.add(Category("Category_Others", "Others", 1))
        }
        return items
    }

    fun getNameByID(id: String?): String? {
        if (items == null) {
            getItems()
        } else {
            for (i in items!!.indices) {
                val getID: String = getItems()!![i].getId()
                if (getID.equals(id, ignoreCase = true)) {
                    return getItems()!![i].getName()
                }
            }
        }
        return null
    }

    fun getItemsByID(id: String?): Category? {
        if (items == null) {
            getItems()
        } else {
            for (i in items!!.indices) {
                val getID: String = getItems()!![i].getId()
                if (getID.equals(id, ignoreCase = true)) {
                    return items!![i]
                }
            }
        }
        return null
    }
}
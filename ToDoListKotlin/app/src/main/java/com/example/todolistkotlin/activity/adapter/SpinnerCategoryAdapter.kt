package com.example.todolistkotlin.activity.adapter

import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import com.example.todolistkotlin.activity.classes.Category
import com.example.todolistkotlin.R
import java.util.*

class SpinnerCategoryAdapter(
    getContext: Context,
    private val resource: Int,
    objects: ArrayList<Category>
) : ArrayAdapter<Category?>(getContext, resource, objects as List<Category?>) {

    private val items: ArrayList<Category> = objects
    private val listColors = arrayOf("#b0c4de", "#20b2aa")

    internal class ViewHolder(view: View?) {
        var tvSpinnerCategory: TextView = view!!.findViewById(R.id.tvSpinnerCategory)
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        return getCustomView(position, convertView, parent, true)!!
    }

    override fun getDropDownView(position: Int, convertView: View?, parent: ViewGroup): View {
        return getCustomView(position, convertView, parent, false)!!
    }

    private fun getCustomView(
        position: Int,
        convertView: View?,
        parent: ViewGroup,
        flag: Boolean
    ): View? {
        var view = convertView
        val viewHolder: ViewHolder

        if (view == null) {
            val inflater =
                context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
            view = inflater.inflate(resource, parent, false)
            viewHolder = ViewHolder(view)
            view.tag = viewHolder
        } else {
            viewHolder = view.tag as ViewHolder
        }

        val category: Category = items[position]
        viewHolder.tvSpinnerCategory.text = category.getName()
        if (!flag) {
            view!!.setBackgroundColor(Color.parseColor(listColors[position % 2]))
        }
        return view
    }
}
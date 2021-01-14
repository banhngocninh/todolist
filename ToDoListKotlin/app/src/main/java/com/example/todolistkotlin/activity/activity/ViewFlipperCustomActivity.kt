package com.example.todolistkotlin.activity.activity

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.widget.ImageView
import android.widget.RelativeLayout
import com.example.todolistkotlin.R


@SuppressLint("ViewConstructor")
class ViewFlipperCustomActivity(context: Context, iconName: String?) :
    RelativeLayout(context) {
    companion object {
        fun getResource(context: Context, imageName: String?): Int {
            return context.resources.getIdentifier(imageName, "drawable", context.packageName)
        }
    }

    init {
        LayoutInflater.from(context).inflate(R.layout.viewflipper_custom, this, true)
        val imgTask = findViewById<ImageView>(R.id.imgTask)
        imgTask.setImageResource(getResource(context, iconName))
    }
}
package com.example.todolistkotlin.activity.classes

import java.util.*

class Task(id: String, name: String, time: Calendar, icon: Int, rating: Int, type: String) {
    private var id: String = ""
    private var name: String = ""
    private var time: Calendar
    private var icon: Int = 0
    private var rating: Int = 0
    private var type: String = ""

    init {
        this.id = id
        this.name = name
        this.time = time
        this.icon = icon
        this.rating = rating
        this.type = type
    }

    fun getId(): String {
        return id
    }

    fun setId(id: String) {
        this.id = id
    }

    fun getName(): String {
        return name
    }

    fun setName(name: String) {
        this.name = name
    }

    fun getTime(): Calendar {
        return time
    }

    fun setTime(time: Calendar) {
        this.time = time
    }

    fun getIcon(): Int {
        return icon
    }

    fun setIcon(icon: Int) {
        this.icon = icon
    }

    fun getRating(): Int {
        return rating
    }

    fun setRating(rating: Int) {
        this.rating = rating
    }

    fun getType(): String {
        return type
    }

    fun setType(type: String) {
        this.type = type
    }
}


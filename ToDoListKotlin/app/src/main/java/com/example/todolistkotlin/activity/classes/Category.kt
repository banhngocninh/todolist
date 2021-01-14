package com.example.todolistkotlin.activity.classes


class Category(id: String, name: String, icon: Int) {
    private var id: String = ""
    private var name: String = ""
    private var icon: Int = 0

    init {
        this.id = id
        this.name = name
        this.icon = icon
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

    fun getIcon(): Int {
        return icon
    }

    fun setIcon(icon: Int) {
        this.icon = icon
    }
}

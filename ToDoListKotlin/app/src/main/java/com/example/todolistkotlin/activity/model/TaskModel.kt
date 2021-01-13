package com.example.todolistkotlin.activity.model

import com.example.todolistkotlin.activity.activity.MainActivity
import com.example.todolistkotlin.activity.classes.Task
import java.util.*


object TaskModel {

    private fun getItems(): ArrayList<Task>? {
        if (MainActivity.task == null) {
            MainActivity.task = ArrayList()

//            items.add(new Task("Task_01", "Watch TV", Calendar.getInstance(), R.drawable.watching_64, 1, "Category_Home"));
//            items.add(new Task("Task_02", "Do Homework", Calendar.getInstance(), R.drawable.homework_64, 2, "Category_Home"));
//            items.add(new Task("Task_03", "Meeting", Calendar.getInstance(), R.drawable.meeting_64, 3, "Category_Working"));
//            items.add(new Task("Task_04", "Do Exam", Calendar.getInstance(), R.drawable.exam_64, 4, "Category_Studying"));
//            items.add(new Task("Task_05", "Running", Calendar.getInstance(), R.drawable.running_64, 5, "Category_Sport"));
        }
        return MainActivity.task
    }

    fun editTask(
        ID: String?,
        name: String,
        time: Calendar?,
        icon: Int,
        rating: Int,
        type: String?
    ) {
        val currentTask = getItemsByID(ID) ?: return
        currentTask.setName(name)
        if (time != null) {
            currentTask.setTime(time)
        }
        currentTask.setRating(rating)
        currentTask.setIcon(icon)
        if (type != null) {
            currentTask.setType(type)
        }
    }

    fun addTask(
        ID: String?,
        name: String?,
        time: Calendar?,
        icon: Int,
        rating: Int,
        type: String?
    ) {
        val newTask = Task(ID!!, name!!, time!!, rating, icon, type!!)
        MainActivity.task!!.add(newTask)
    }

    fun getItemsByID(id: String?): Task? {
        if (MainActivity.task == null) {
            getItems()
        } else {
            for (i in 0 until MainActivity.task!!.size) {
                val getID: String = MainActivity.task!![i].getId()
                if (getID.equals(id, ignoreCase = true)) {
                    return MainActivity.task!![i]
                }
            }
        }
        return null
    }
}
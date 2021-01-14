package com.example.todolistkotlin.activity.sqlite

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.example.todolistkotlin.activity.classes.Task
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*


class SQLite(context: Context?) : SQLiteOpenHelper(context, DB_NAME, null, DB_VERSION) {
    private var formatter: SimpleDateFormat? = null
    private var timeFormat: Date? = null
    var myDB: SQLiteDatabase = this.writableDatabase

    @SuppressLint("SimpleDateFormat")
    fun add(task: Task): Boolean {
        val contentValues = ContentValues()
        formatter = SimpleDateFormat("HH:mm")
        val timeFormat = formatter!!.format(task.getTime().time)
        contentValues.put(TABLE_TASK_ID, task.getId())
        contentValues.put(TABLE_TASK_NAME, task.getName())
        contentValues.put(TABLE_TASK_TIME, timeFormat)
        contentValues.put(TABLE_TASK_ICON, task.getIcon())
        contentValues.put(TABLE_TASK_RATING, task.getRating())
        contentValues.put(TABLE_TASK_TYPE, task.getType())
        val check = myDB.insert(TABLE_TASK, null, contentValues)
        return check != -1L
    }

    @SuppressLint("SimpleDateFormat")
    fun showList(): ArrayList<Task> {
        val items: ArrayList<Task> = ArrayList<Task>()
        @SuppressLint("Recycle") val cursor = myDB.rawQuery("select * from $TABLE_TASK", null)
        if (cursor.count > 0) {
            cursor.moveToFirst()
            while (!cursor.isAfterLast) {
                val valueID = cursor.getString(0)
                val valueName = cursor.getString(1)
                val valueTime = cursor.getString(2)
                val valueIcon = cursor.getInt(3)
                val valueRating = cursor.getInt(4)
                val valueType = cursor.getString(5)
                formatter = SimpleDateFormat("HH:mm")
                try {
                    timeFormat = formatter!!.parse(valueTime)
                } catch (e: ParseException) {
                    e.printStackTrace()
                }
                val calendar = Calendar.getInstance()
                calendar.time = timeFormat
                val task = Task(valueID, valueName, calendar, valueIcon, valueRating, valueType)
                items.add(task)
                cursor.moveToNext()
            }
        }
        return items
    }

    fun delete(task: Task): Boolean {
        val row = myDB.delete(TABLE_TASK, "$TABLE_TASK_ID = ?", arrayOf(task.getId()))
        return row > 0
    }

    @SuppressLint("SimpleDateFormat")
    fun edit(task: Task): Boolean {
        formatter = SimpleDateFormat("HH:mm")
        val timeFormat = formatter!!.format(task.getTime().time)
        val contentValues = ContentValues()
        contentValues.put(TABLE_TASK_NAME, task.getName())
        contentValues.put(TABLE_TASK_TIME, timeFormat)
        contentValues.put(TABLE_TASK_ICON, task.getIcon())
        contentValues.put(TABLE_TASK_RATING, task.getRating())
        contentValues.put(TABLE_TASK_TYPE, task.getType())
        val row =
            myDB.update(TABLE_TASK, contentValues, "$TABLE_TASK_ID = ?", arrayOf(task.getId()))
        return row > 0
    }

    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL(SQL_CREATE_TABLE_TASK)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS $TABLE_TASK")
        onCreate(db)
    }

    companion object {
        const val DB_NAME = "toDoList.db"
        const val DB_VERSION = 1
        const val TABLE_TASK = "task"
        const val TABLE_TASK_ID = "id"
        const val TABLE_TASK_NAME = "name"
        const val TABLE_TASK_TIME = "time"
        const val TABLE_TASK_ICON = "icon"
        const val TABLE_TASK_RATING = "rating"
        const val TABLE_TASK_TYPE = "type"
        private const val TABLE_CATEGORY = "category"
        private const val TABLE_CATEGORY_ID = "id"
        private const val TABLE_CATEGORY_NAME = "name"
        const val SQL_CREATE_TABLE_TASK =
            "create table " + TABLE_TASK + " (" + TABLE_TASK_ID + " text primary key not null," + TABLE_TASK_NAME + " text not null," + TABLE_TASK_TIME + " text not null," +
                    TABLE_TASK_ICON + " integer not null," + TABLE_TASK_RATING + " integer not null," + TABLE_TASK_TYPE + " text not null)"
        const val SQL_CREATE_TABLE_CATEGORY =
            "create table $TABLE_CATEGORY ($TABLE_CATEGORY_ID TEXT primary key autoincrement, $TABLE_CATEGORY_NAME text)"
    }

}
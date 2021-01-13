package com.example.todolistkotlin.activity.activity

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.Toast
import com.example.todolistkotlin.activity.classes.Task
import com.example.todolistkotlin.activity.model.CategoryModel
import com.example.todolistkotlin.activity.model.TaskModel
import com.example.todolistkotlin.R
import kotlinx.android.synthetic.main.activity_info_task.*
import java.text.SimpleDateFormat


class InfoTaskActivity : AppCompatActivity() {

    private var currentID: String? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_info_task)
        currentID = intent.getStringExtra("getID")
        loadData()
    }

    private fun loadData() {
        val currentTask: Task? = TaskModel.getItemsByID(currentID)
        if (currentTask != null) {
            tvTaskName?.text = currentTask.getName()
            tvTaskCategory?.text = CategoryModel.getNameByID(currentTask.getType())
            @SuppressLint("SimpleDateFormat") val simpleDateFormat = SimpleDateFormat("HH:mm")
            tvTaskTime!!.text = simpleDateFormat.format(currentTask.getTime().time)
            imgIcon!!.setImageResource(currentTask.getIcon())
            ratingTask!!.rating = currentTask.getRating().toFloat()
        }
    }

    @SuppressLint("NonConstantResourceId")
    fun onClick(view: View) {
        var flag = false
        when (view.id) {
            R.id.btnCancel -> {
                if (flag) {
                    setResult(MainActivity.REQUEST_CODE_TASK_EDIT_DONE, intent)
                }
                finish()
            }
            R.id.btnDelete -> {
                intent.putExtra("getID", currentID)
                setResult(MainActivity.REQUEST_CODE_TASK_DELETE, intent)
                finish()
            }
            R.id.btnEdit -> {
                flag = true
                val myIntent = Intent(this@InfoTaskActivity, AddTaskActivity::class.java)
                myIntent.putExtra("getID", currentID)
                startActivityForResult(myIntent, MainActivity.REQUEST_CODE_TASK_EDIT)
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == MainActivity.REQUEST_CODE_TASK_EDIT && resultCode == MainActivity.REQUEST_CODE_TASK_EDIT_DONE) {
            loadData()
            Toast.makeText(this@InfoTaskActivity, "Success", Toast.LENGTH_LONG).show()
        }
    }
}
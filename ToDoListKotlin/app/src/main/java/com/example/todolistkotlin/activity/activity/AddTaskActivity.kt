package com.example.todolistkotlin.activity.activity

import android.annotation.SuppressLint
import android.app.TimePickerDialog
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.text.TextUtils
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.*
import android.widget.AdapterView.OnItemSelectedListener
import com.example.todolistkotlin.activity.adapter.SpinnerCategoryAdapter
import com.example.todolistkotlin.activity.classes.Category
import com.example.todolistkotlin.activity.classes.Task
import com.example.todolistkotlin.activity.model.CategoryModel
import com.example.todolistkotlin.activity.model.TaskModel
import com.example.todolistkotlin.activity.sqlite.SQLite
import com.example.todolistkotlin.R
import kotlinx.android.synthetic.main.activity_add_task.*
import java.text.SimpleDateFormat
import java.util.*


class AddTaskActivity : AppCompatActivity() {
    private var timePickerDialog: TimePickerDialog? = null
    private var calendar: Calendar? = null
    private lateinit var itemsIcon: Array<String>
    private var type: String? = null
    private var category: Category? = null
    private var currentID: String? = null
    private var spinnerCategoryAdapter: SpinnerCategoryAdapter? = null
    private var taskID: Task? = null
    private var sqLite: SQLite? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_task)
        sqLite = SQLite(applicationContext)
        findViewByIDs()
        addAppBar()
        spinnerCategoryAdapter = CategoryModel.getItems()?.let {
            SpinnerCategoryAdapter(
                    this@AddTaskActivity,
                    R.layout.spinner_category,
                    it
        )
        }
        spinnerCategory!!.adapter = spinnerCategoryAdapter
        addSpinner(false)
        addTime()
        addViewFlipperIcons(R.array.task_icon)
        currentID = intent.getStringExtra("getID")
        if (currentID != null) {
            addValue()
        }
    }

    @SuppressLint("SetTextI18n")
    private fun addValue() {
        taskID = TaskModel.getItemsByID(currentID)
        edtNameTask.setText(taskID!!.getName())
        @SuppressLint("SimpleDateFormat") val simpleDateFormat = SimpleDateFormat("HH:mm")
        btnCreate!!.text = "Starting at: " + simpleDateFormat.format(taskID!!.getTime().time)
        ratingTask!!.rating = taskID!!.getRating().toFloat()
        val position: Int? = spinnerCategoryAdapter?.getPosition(CategoryModel.getItemsByID(taskID!!.getType()))
        if (position != null) {
            spinnerCategory!!.setSelection(position)
        }
        addSpinner(true)
    }

    private fun addSpinner(flag: Boolean) {
        spinnerCategory!!.onItemSelectedListener = object : OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View,
                position: Int,
                id: Long
            ) {
                category = spinnerCategory!!.selectedItem as Category
                type = category!!.getId()
                when (type) {
                    "Category_Home" -> {
                        viewFlipperImage!!.removeAllViews()
                        addViewFlipperIcons(R.array.home_icon)
                        if (flag) {
                            val iconName = taskID?.let { resources.getResourceEntryName(it.getIcon()) }
                            val viewFlipperPos = listOf(*itemsIcon).indexOf(iconName)
                            viewFlipperImage!!.displayedChild = viewFlipperPos
                        }
                    }
                    "Category_Working" -> {
                        viewFlipperImage!!.removeAllViews()
                        addViewFlipperIcons(R.array.work_icon)
                        if (flag) {
                            val iconName = taskID?.let { resources.getResourceEntryName(it.getIcon()) }
                            val viewFlipperPos = listOf(*itemsIcon).indexOf(iconName)
                            viewFlipperImage!!.displayedChild = viewFlipperPos
                        }
                    }
                    "Category_Sport" -> {
                        viewFlipperImage!!.removeAllViews()
                        addViewFlipperIcons(R.array.sport_icon)
                        if (flag) {
                            val iconName = taskID?.let { resources.getResourceEntryName(it.getIcon()) }
                            val viewFlipperPos = listOf(*itemsIcon).indexOf(iconName)
                            viewFlipperImage!!.displayedChild = viewFlipperPos
                        }
                    }
                    "Category_Entertainment" -> {
                        viewFlipperImage!!.removeAllViews()
                        addViewFlipperIcons(R.array.entertainment_item)
                        if (flag) {
                            val iconName = taskID?.let { resources.getResourceEntryName(it.getIcon()) }
                            val viewFlipperPos = listOf(*itemsIcon).indexOf(iconName)
                            viewFlipperImage!!.displayedChild = viewFlipperPos
                        }
                    }
                    "Category_Others" -> {
                        viewFlipperImage!!.removeAllViews()
                        addViewFlipperIcons(R.array.other_item)
                        if (flag) {
                            val iconName = taskID?.let { resources.getResourceEntryName(it.getIcon()) }
                            val viewFlipperPos = listOf(*itemsIcon).indexOf(iconName)
                            viewFlipperImage!!.displayedChild = viewFlipperPos
                        }
                    }
                    "Category_Studying" -> {
                        viewFlipperImage!!.removeAllViews()
                        addViewFlipperIcons(R.array.study_item)
                        if (flag) {
                            val iconName = taskID?.let { resources.getResourceEntryName(it.getIcon()) }
                            val viewFlipperPos = listOf(*itemsIcon).indexOf(iconName)
                            viewFlipperImage!!.displayedChild = viewFlipperPos
                        }
                    }
                    else -> {
                        viewFlipperImage!!.removeAllViews()
                        addViewFlipperIcons(R.array.task_icon)
                        if (flag) {
                            val iconName = taskID?.let { resources.getResourceEntryName(it.getIcon()) }
                            val viewFlipperPos = listOf(*itemsIcon).indexOf(iconName)
                            viewFlipperImage!!.displayedChild = viewFlipperPos
                        }
                    }
                }
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {}
        }
    }

    @SuppressLint("SetTextI18n")
    private fun findViewByIDs() {
        calendar = Calendar.getInstance()
        @SuppressLint("SimpleDateFormat") val simpleDateFormat = SimpleDateFormat("HH:mm")
        btnCreate.text = "Starting at: " + simpleDateFormat.format(calendar!!.time)
    }

    private fun addAppBar() {
        setSupportActionBar(myAddToolBar)
        val actionBar = this.supportActionBar!!
        actionBar.setDisplayHomeAsUpEnabled(true) //thêm nút back
    }

    private fun addViewFlipperIcons(allItems: Int) {
        itemsIcon = resources.getStringArray(allItems)
        for (icons in itemsIcon) {
            viewFlipperImage!!.addView(ViewFlipperCustomActivity(this@AddTaskActivity, icons))
        }
    }

    @SuppressLint("SetTextI18n")
    private fun addTime() {
        calendar = Calendar.getInstance()
        timePickerDialog = TimePickerDialog(
            this@AddTaskActivity,
            { _: TimePicker?, hourOfDay: Int, minute: Int ->
                calendar!!.set(Calendar.HOUR_OF_DAY, hourOfDay)
                calendar!!.set(Calendar.MINUTE, minute)
                @SuppressLint("SimpleDateFormat") val simpleDateFormat =
                    SimpleDateFormat("HH:mm")
                btnCreate!!.text = "Starting at: " + simpleDateFormat.format(calendar!!.time)
            }, calendar!!.get(Calendar.HOUR), calendar!!.get(Calendar.MINUTE), true
        )
    }

    @SuppressLint("NonConstantResourceId")
    fun onClick(view: View) {
        when (view.id) {
            R.id.imgBtnPrevious -> viewFlipperImage!!.showPrevious()
            R.id.imgBtnNext -> viewFlipperImage!!.showNext()
            R.id.btnCreate -> timePickerDialog!!.show()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_add, menu)
        return super.onCreateOptionsMenu(menu)
    }

    @SuppressLint("NonConstantResourceId")
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.action_check) {
            val name = edtNameTask!!.text.toString()
            if (TextUtils.isEmpty(name)) {
                Toast.makeText(
                    this@AddTaskActivity,
                    "Please enter your task name!!!",
                    Toast.LENGTH_LONG
                ).show()
            } else {
                val ID = "Task_" + randomString()
                val rating = ratingTask!!.rating.toInt()
                val icon: Int = ViewFlipperCustomActivity.getResource(
                    this@AddTaskActivity,
                    itemsIcon[viewFlipperImage!!.displayedChild]
                )
                if (currentID != null) {
                    TaskModel.editTask(currentID, name, calendar, icon, rating, type)
                    val currentTask: Task? = TaskModel.getItemsByID(currentID)
                    if (currentTask != null) {
                        sqLite?.edit(currentTask)
                    }
                    setResult(MainActivity.REQUEST_CODE_TASK_EDIT_DONE, intent)
                } else {
                    val task = Task(ID, name, calendar!!, icon, rating, type!!)
                    if (sqLite?.add(task) == true) {
                        Toast.makeText(this@AddTaskActivity, "Success!!!", Toast.LENGTH_SHORT)
                            .show()
                    } else {
                        Toast.makeText(this@AddTaskActivity, "Fail!!!", Toast.LENGTH_SHORT).show()
                    }
                    TaskModel.addTask(ID, name, calendar, icon, rating, type)
                    setResult(MainActivity.REQUEST_CODE_TASK_ADD_DONE, intent)
                }
                //Toast.makeText(AddTaskActivity.this, "Successful!!!", Toast.LENGTH_LONG).show();
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun randomString(): String {
        val characters = "abcdefghijklmnopqrstuvwxyz0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ"
        val characters_length = characters.length
        val random = Random()
        val id = StringBuilder()
        for (i in 0..3) {
            id.append(characters[random.nextInt(characters_length)])
        }
        return id.toString()
    }
}
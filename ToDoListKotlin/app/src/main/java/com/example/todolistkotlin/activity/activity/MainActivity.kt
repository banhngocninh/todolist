package com.example.todolistkotlin.activity.activity

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.support.v4.view.MenuItemCompat
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.SearchView
import android.support.v7.widget.Toolbar
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.AdapterView
import android.widget.AdapterView.OnItemClickListener
import android.widget.ListView
import android.widget.Toast
import com.example.todolistkotlin.activity.adapter.ListViewTaskAdapter
import com.example.todolistkotlin.activity.classes.Task
import com.example.todolistkotlin.activity.event.Multi_Delete
import com.example.todolistkotlin.activity.model.TaskModel
import com.example.todolistkotlin.activity.sqlite.SQLite
import com.example.todolistkotlin.R
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*


class MainActivity : AppCompatActivity() {
    private var listViewTaskAdapter: ListViewTaskAdapter? = null

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        sqLite = SQLite(applicationContext)
        sqLite!!.writableDatabase
        task = ArrayList<Task>()
        task = sqLite!!.showList()

        val myToolBar = findViewById<Toolbar>(R.id.myToolBar)
        setSupportActionBar(myToolBar)

        val actionBar = this.supportActionBar!!
        actionBar.show()

        listViewTaskAdapter = ListViewTaskAdapter(
            this@MainActivity,
            R.layout.custom_listview_task, task!!
        )
        lvTask!!.adapter = listViewTaskAdapter
        lvTask!!.onItemClickListener =
            OnItemClickListener { parent: AdapterView<*>, _: View?, position: Int, _: Long ->
                val currentTask: Task = parent.getItemAtPosition(position) as Task
                val intent = Intent(this@MainActivity, InfoTaskActivity::class.java)
                intent.putExtra("getID", currentTask.getId())
                startActivityForResult(intent, REQUEST_CODE_TASK_INFO)
            }

        lvTask!!.choiceMode = ListView.CHOICE_MODE_MULTIPLE_MODAL
        lvTask!!.setMultiChoiceModeListener(
            Multi_Delete(
                this@MainActivity,
                lvTask!!, listViewTaskAdapter!!
            )
        )
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_CODE_TASK_INFO && resultCode == REQUEST_CODE_TASK_DELETE) {
            val currentID = data!!.getStringExtra("getID")
            val currentTask: Task? = TaskModel.getItemsByID(currentID)
            if (currentTask != null) {
                sqLite?.delete(currentTask)
            }
            task!!.remove(currentTask)
            listViewTaskAdapter?.updateItems(currentTask)
            listViewTaskAdapter?.notifyDataSetChanged()
            Toast.makeText(this@MainActivity, "Success", Toast.LENGTH_LONG).show()
        }
        if (requestCode == REQUEST_CODE_TASK_ADD && resultCode == REQUEST_CODE_TASK_ADD_DONE) {
            listViewTaskAdapter?.notifyDataSetChanged()
            Toast.makeText(this@MainActivity, "Success", Toast.LENGTH_LONG).show()
        }
        if (requestCode == REQUEST_CODE_TASK_INFO && resultCode == REQUEST_CODE_TASK_EDIT_DONE) {
            listViewTaskAdapter?.notifyDataSetChanged()
        }
    }


    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        val searchItem = menu.findItem(R.id.action_search)
        val searchView = MenuItemCompat.getActionView(searchItem) as SearchView
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String): Boolean {
                listViewTaskAdapter?.filter(newText)
                return false
            }
        })
        return super.onCreateOptionsMenu(menu)
    }

    @SuppressLint("NonConstantResourceId")
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.sort_name_AZ -> {
                task?.sortedBy { it.getName() }
                listViewTaskAdapter?.notifyDataSetChanged()
            }
            R.id.sort_name_ZA -> {
                task?.sortedByDescending { it.getName() }
                listViewTaskAdapter?.notifyDataSetChanged()
            }
            R.id.sort_rating_ASC -> {
                task?.sortedBy { it.getRating() }
                listViewTaskAdapter?.notifyDataSetChanged()
            }
            R.id.sort_rating_DESC -> {
                task?.sortedByDescending { it.getRating() }
                listViewTaskAdapter?.notifyDataSetChanged()
            }
            R.id.action_add -> {
                val myIntent = Intent(this@MainActivity, AddTaskActivity::class.java)
                startActivityForResult(myIntent, REQUEST_CODE_TASK_ADD)
            }
        }
        return super.onOptionsItemSelected(item)
    }

    companion object {
        var sqLite: SQLite? = null
        var task: ArrayList<Task>? = null
        const val REQUEST_CODE_TASK_DELETE = 999
        const val REQUEST_CODE_TASK_INFO = 998
        const val REQUEST_CODE_TASK_ADD = 997
        const val REQUEST_CODE_TASK_ADD_DONE = 996
        const val REQUEST_CODE_TASK_EDIT = 995
        const val REQUEST_CODE_TASK_EDIT_DONE = 994
    }
}

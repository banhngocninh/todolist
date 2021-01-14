package com.example.todolistkotlin.activity.event

import android.support.v7.app.AlertDialog
import android.view.ActionMode
import android.view.Menu
import android.view.MenuItem
import android.widget.AbsListView.MultiChoiceModeListener
import android.widget.ListView
import android.widget.Toast
import com.example.todolistkotlin.R
import com.example.todolistkotlin.activity.activity.MainActivity
import com.example.todolistkotlin.activity.adapter.ListViewTaskAdapter


class Multi_Delete(mainActivity: MainActivity, private val lvTask: ListView, listViewTaskAdapter: ListViewTaskAdapter) : MultiChoiceModeListener {
    private val listViewTaskAdapter: ListViewTaskAdapter = listViewTaskAdapter
    private val mainActivity: MainActivity = mainActivity

    private fun showMyDialog(mode: ActionMode, setTitle: String?, setMessage: String?, setIcon: Int) {
        val builder = AlertDialog.Builder(mainActivity)
        builder.setIcon(setIcon)
        builder.setTitle(setTitle)
        builder.setMessage(setMessage)

        builder.setPositiveButton("YES") { _, _ ->
            listViewTaskAdapter.actionItemClicked()
            mode.finish()
            Toast.makeText(mainActivity, "Success", Toast.LENGTH_LONG).show()
        }
        builder.setNegativeButton("NO") { dialog, _ -> dialog.cancel() }
        builder.setCancelable(false)
        builder.create().show()
    }

    override fun onItemCheckedStateChanged(mode: ActionMode, position: Int, id: Long, checked: Boolean) {
        val checkCount = lvTask.checkedItemCount
        mode.title = "$checkCount selected"
        listViewTaskAdapter.checkedStateChanged(position)
    }

    override fun onCreateActionMode(mode: ActionMode, menu: Menu): Boolean {
        mode.menuInflater.inflate(R.menu.menu_multi_delete, menu)
        mainActivity.supportActionBar?.hide()
        return true
    }

    override fun onPrepareActionMode(mode: ActionMode, menu: Menu): Boolean {
        return false
    }

    override fun onActionItemClicked(mode: ActionMode, item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_multi_delete -> {
                val setTitle = "Do you want to delete???"
                val setMessage = "Delete!!!"
                val setIcon: Int = R.drawable.garbage_24
                showMyDialog(mode, setTitle, setMessage, setIcon)
            }
        }
        return true
    }

    override fun onDestroyActionMode(mode: ActionMode) {
        mainActivity.supportActionBar?.show()
        listViewTaskAdapter.destroyActionMode()
    }

}
package com.example.todolistkotlin.activity.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.content.res.ColorStateList
import android.graphics.Color
import android.graphics.Typeface
import android.text.Spannable
import android.text.SpannableString
import android.text.Spanned
import android.text.style.TextAppearanceSpan
import android.util.SparseBooleanArray
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.RatingBar
import android.widget.TextView
import com.example.todolistkotlin.R
import com.example.todolistkotlin.activity.activity.MainActivity
import com.example.todolistkotlin.activity.classes.Task
import com.example.todolistkotlin.activity.model.CategoryModel
import java.text.SimpleDateFormat
import java.util.*


class ListViewTaskAdapter(
    getContext: Context,
    private val resource: Int,
    objects: ArrayList<Task>
) : ArrayAdapter<Task?>(getContext, resource, objects as List<Task?>) {
    private val items: ArrayList<Task> = objects
    private val itemsTemp: ArrayList<Task> = ArrayList<Task>()
    private var keyWord = ""
    private val selectedItems: SparseBooleanArray//lưu trữ các phần tử được click vào. ví dụ:

    // position: 0 id1 - Ninh
    // position: 1 id2 - Hiển
    // position: 2 id3 - Tân
    // khi được click, ví dụ:
    // id1 - Ninh - click
    // id2 - Hiển - click
    // thì nó sẽ lưu:
    // 0, true
    // 1, true

    internal class ViewHolder(view: View?) {
        var imgIcon: ImageView = view!!.findViewById(R.id.imgIcon)
        lateinit var ratingTask: RatingBar
        lateinit var tvTaskName: TextView
        lateinit var tvTaskCategory: TextView
        lateinit var tvTaskTime: TextView

        init {
            if (view != null) {
                ratingTask = view.findViewById(R.id.ratingTask)
                tvTaskName = view.findViewById(R.id.tvTaskName)
                tvTaskCategory = view.findViewById(R.id.tvTaskCategory)
                tvTaskTime = view.findViewById(R.id.tvTaskTime)
            }

        }
    }

    fun filter(search: String) {
        keyWord = search
        items.clear()
        if (keyWord.isEmpty()) {
            items.addAll(itemsTemp)
        } else {
            for (i in itemsTemp.indices) {
                val currentTask: Task = itemsTemp[i]
                if (itemsTemp[i].getName().toLowerCase(Locale.ROOT)
                        .contains(keyWord.toLowerCase(Locale.ROOT))
                ) {
                    items.add(currentTask)
                }
            }
        }
        notifyDataSetChanged()
    }

    fun checkedStateChanged(position: Int) {
        if (selectedItems[position]) {
            selectedItems.delete(position)
        } else {
            selectedItems.put(position, true)
        }
        notifyDataSetChanged()
    }

    fun destroyActionMode() {
        selectedItems.clear()
        notifyDataSetChanged()
    }

    fun actionItemClicked() {
        for (i in selectedItems.size() - 1 downTo 0) {
            if (selectedItems.valueAt(i)) {
                val currentTask: Task? = MainActivity.task?.get(selectedItems.keyAt(i))
                if (currentTask != null) {
                    MainActivity.sqLite?.delete(currentTask)
                }
                items.remove(currentTask)
                updateItems(currentTask)
                notifyDataSetChanged()
            }
        }
    }

    fun updateItems(currentTask: Task?) {
        itemsTemp.remove(currentTask)
    }

    @SuppressLint("SetTextI18n")
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
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

        val task: Task = items[position]
        viewHolder.tvTaskName.text = task.getName()
        viewHolder.tvTaskCategory.text = CategoryModel.getNameByID(task.getType())
        viewHolder.ratingTask.rating = task.getRating().toFloat()
        @SuppressLint("SimpleDateFormat") val simpleDateFormat = SimpleDateFormat("HH:mm")
        viewHolder.tvTaskTime.text =
            "Starting at: " + simpleDateFormat.format(task.getTime().time)
        viewHolder.imgIcon.setImageResource(task.getIcon())

        if (task.getName().toLowerCase(Locale.ROOT).contains(keyWord)) {
            val posStart: Int = task.getName().toLowerCase(Locale.ROOT).indexOf(keyWord)
            val posEnd = posStart + keyWord.length
            val spannable: Spannable = SpannableString(task.getName())
            val textAppearanceSpan = TextAppearanceSpan(
                null, Typeface.ITALIC, -1, ColorStateList.valueOf(
                    Color.BLACK
                ), null
            )
            spannable.setSpan(
                textAppearanceSpan,
                posStart,
                posEnd,
                Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
            )
            viewHolder.tvTaskName.text = spannable
        }

        view!!.setBackgroundResource(R.color.nude)
        if (selectedItems.size() > 0) {
            if (selectedItems[position]) {
                view.setBackgroundColor(Color.parseColor("#a9a9a9"))
            }
        }
        return view
    }

    init {
        itemsTemp.addAll(objects)
        selectedItems = SparseBooleanArray()
    }
}

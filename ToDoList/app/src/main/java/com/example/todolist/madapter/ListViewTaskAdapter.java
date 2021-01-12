package com.example.todolist.madapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.Typeface;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.TextAppearanceSpan;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.example.todolist.R;
import com.example.todolist.mactivity.MainActivity;
import com.example.todolist.mclass.Task;
import com.example.todolist.mmodel.CategoryModel;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class ListViewTaskAdapter extends ArrayAdapter<Task> {

    private final Context context;
    private final int resource;
    private final ArrayList<Task> items;
    private final ArrayList<Task> itemsTemp;
    private String keyWord = "";
    private final SparseBooleanArray selectedItems; //lưu trữ các phần tử được click vào. ví dụ:
    // position: 0 id1 - Ninh
    // position: 1 id2 - Hiển
    // position: 2 id3 - Tân
    // khi được click, ví dụ:
    // id1 - Ninh - click
    // id2 - Hiển - click
    // thì nó sẽ lưu:
    // 0, true
    // 1, true


    static class ViewHolder {
        ImageView imgIcon;
        RatingBar ratingTask;
        TextView tvTaskName, tvTaskCategory, tvTaskTime;

        public ViewHolder(View view) {
            this.imgIcon = view.findViewById(R.id.imgIcon);
            this.ratingTask = view.findViewById(R.id.ratingTask);
            this.tvTaskName = view.findViewById(R.id.tvTaskName);
            this.tvTaskCategory = view.findViewById(R.id.tvTaskCategory);
            this.tvTaskTime = view.findViewById(R.id.tvTaskTime);
        }
    }

    public ListViewTaskAdapter(@NonNull Context context, int resource, @NonNull ArrayList<Task> objects) {
        super(context, resource, objects);
        this.context = context;
        this.resource = resource;
        this.items = objects;
        this.itemsTemp = new ArrayList<>();
        this.itemsTemp.addAll(objects);
        this.selectedItems = new SparseBooleanArray();
    }

    public void filter(String search) {
        keyWord = search;
        items.clear();
        if (keyWord.length() == 0) {
            items.addAll(itemsTemp);
        } else {
            for (int i = 0; i < itemsTemp.size(); i++) {
                Task currentTask = itemsTemp.get(i);
                if (itemsTemp.get(i).getName().toLowerCase().contains(keyWord.toLowerCase())) {
                    items.add(currentTask);
                }
            }
        }
        notifyDataSetChanged();
    }

    public void checkedStateChanged(int position) {
        if (selectedItems.get(position)) {
            selectedItems.delete(position);
        } else {
            selectedItems.put(position, true);
        }
        notifyDataSetChanged();
    }

    public void destroyActionMode() {
        selectedItems.clear();
        notifyDataSetChanged();
    }

    public void actionItemClicked() {
        for (int i = selectedItems.size() - 1; i >= 0; i--) {
            if (selectedItems.valueAt(i)) {
                Task currentTask = MainActivity.task.get(selectedItems.keyAt(i));
                MainActivity.sqLite.delete(currentTask);
                items.remove(currentTask);
                updateItems(currentTask);
                notifyDataSetChanged();
            }
        }
    }

    public void updateItems(Task currentTask) {
        itemsTemp.remove(currentTask);
    }

    @SuppressLint("SetTextI18n")
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View view = convertView;
        ViewHolder viewHolder;

        if (view == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(resource, parent, false);
            viewHolder = new ViewHolder(view);
            view.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) view.getTag();
        }

        Task task = items.get(position);
        if (task != null) {
            viewHolder.tvTaskName.setText(task.getName());
            viewHolder.tvTaskCategory.setText(CategoryModel.getNameByID(task.getType()));
            viewHolder.ratingTask.setRating(task.getRating());
            @SuppressLint("SimpleDateFormat") SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm");
            viewHolder.tvTaskTime.setText("Starting at: " + simpleDateFormat.format(task.getTime().getTime()));
            viewHolder.imgIcon.setImageResource(task.getIcon());

            if (task.getName().toLowerCase().contains(keyWord)) {
                int posStart = task.getName().toLowerCase().indexOf(keyWord);
                int posEnd = posStart + keyWord.length();

                Spannable spannable = new SpannableString(task.getName());
                TextAppearanceSpan textAppearanceSpan = new TextAppearanceSpan(null, Typeface.ITALIC, -1, ColorStateList.valueOf(Color.BLACK), null);
                spannable.setSpan(textAppearanceSpan, posStart, posEnd, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                viewHolder.tvTaskName.setText(spannable);
            }

            view.setBackgroundResource(android.R.color.transparent);
            if (selectedItems.size() > 0) {
                if (selectedItems.get(position)) {
                    view.setBackgroundColor(Color.parseColor("#a9a9a9"));
                }
            }
        }
        return view;
    }
}

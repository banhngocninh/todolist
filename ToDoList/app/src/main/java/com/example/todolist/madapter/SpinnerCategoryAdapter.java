package com.example.todolist.madapter;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.todolist.R;
import com.example.todolist.mclass.Category;

import java.util.ArrayList;

public class SpinnerCategoryAdapter extends ArrayAdapter<Category> {

    private final Context context;
    private final int resource;
    private final ArrayList<Category> items;
    private final String[] listColors = {"#b0c4de", "#20b2aa"};

    static class ViewHolder {
        TextView tvSpinnerCategory;

        public ViewHolder(View view) {
            this.tvSpinnerCategory = view.findViewById(R.id.tvSpinnerCategory);
        }
    }

    public SpinnerCategoryAdapter(@NonNull Context context, int resource, @NonNull ArrayList<Category> objects) {
        super(context, resource, objects);
        this.context = context;
        this.resource = resource;
        this.items = objects;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        return getCustomView(position, convertView, parent, true);
    }

    @Override
    public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        return getCustomView(position, convertView, parent, false);
    }

    private View getCustomView(int position, @Nullable View convertView, @NonNull ViewGroup parent, boolean flag) {
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

        Category category = items.get(position);
        if (category != null) {
            viewHolder.tvSpinnerCategory.setText(category.getName());
        }
        if (!flag) {
            view.setBackgroundColor(Color.parseColor(listColors[position % 2]));
        }
        return view;

    }
}

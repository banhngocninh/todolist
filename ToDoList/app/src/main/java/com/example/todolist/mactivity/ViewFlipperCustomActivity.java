package com.example.todolist.mactivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.example.todolist.R;

@SuppressLint("ViewConstructor")
public class ViewFlipperCustomActivity extends RelativeLayout {
    public ViewFlipperCustomActivity(Context context, String iconName) {
        super(context);

        LayoutInflater.from(context).inflate(R.layout.viewflipper_custom, this, true);
        ImageView imgTask = findViewById(R.id.imgTask);
        imgTask.setImageResource(getResource(context, iconName));

    }

    public static int getResource(Context context, String imageName) {
        return context.getResources().getIdentifier(imageName, "drawable", context.getPackageName());
    }
}

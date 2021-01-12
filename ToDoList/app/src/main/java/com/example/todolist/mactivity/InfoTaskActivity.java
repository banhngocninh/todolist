package com.example.todolist.mactivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.todolist.R;
import com.example.todolist.mclass.Task;
import com.example.todolist.mmodel.CategoryModel;
import com.example.todolist.mmodel.TaskModel;

import java.text.SimpleDateFormat;

public class InfoTaskActivity extends AppCompatActivity {

    private TextView tvTaskName, tvTaskCategory, tvTaskTime;
    private ImageView imgIcon;
    private RatingBar ratingTask;
    private String currentID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info_task);

        findViewByIDs();

        currentID = getIntent().getStringExtra("getID");

        loadData();
    }

    private void findViewByIDs() {
        tvTaskName = findViewById(R.id.tvTaskName);
        tvTaskCategory = findViewById(R.id.tvTaskCategory);
        tvTaskTime = findViewById(R.id.tvTaskTime);
        imgIcon = findViewById(R.id.imgIcon);
        ratingTask = findViewById(R.id.ratingTask);
    }

    private void loadData() {
        Task currentTask = TaskModel.getItemsByID(currentID);
        tvTaskName.setText(currentTask.getName());
        tvTaskCategory.setText(CategoryModel.getNameByID(currentTask.getType()));
        @SuppressLint("SimpleDateFormat") SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm");
        tvTaskTime.setText(simpleDateFormat.format(currentTask.getTime().getTime()));
        imgIcon.setImageResource(currentTask.getIcon());
        ratingTask.setRating(currentTask.getRating());
    }

    @SuppressLint("NonConstantResourceId")
    public void onClick(View view) {
        boolean flag = false;
        switch (view.getId()) {
            case R.id.btnCancel:
                if (flag = true) {
                    setResult(MainActivity.REQUEST_CODE_TASK_EDIT_DONE, getIntent());
                }
                finish();
                break;
            case R.id.btnDelete:
                getIntent().putExtra("getID", currentID);
                setResult(MainActivity.REQUEST_CODE_TASK_DELETE, getIntent());
                finish();
                break;
            case R.id.btnEdit:
                flag = true;
                Intent myIntent = new Intent(InfoTaskActivity.this, AddTaskActivity.class);
                myIntent.putExtra("getID", currentID);
                startActivityForResult(myIntent, MainActivity.REQUEST_CODE_TASK_EDIT);
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == MainActivity.REQUEST_CODE_TASK_EDIT && resultCode == MainActivity.REQUEST_CODE_TASK_EDIT_DONE) {
            loadData();
            Toast.makeText(InfoTaskActivity.this, "Success", Toast.LENGTH_LONG).show();
        }
    }
}
package com.example.todolist.mactivity;

import android.annotation.SuppressLint;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.Spinner;
import android.widget.Toast;
import android.widget.ViewFlipper;

import com.example.todolist.R;
import com.example.todolist.madapter.SpinnerCategoryAdapter;
import com.example.todolist.mclass.Category;
import com.example.todolist.mclass.Task;
import com.example.todolist.mmodel.CategoryModel;
import com.example.todolist.mmodel.TaskModel;
import com.example.todolist.msqlite.SQLite;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Random;

public class AddTaskActivity extends AppCompatActivity {
    private ViewFlipper viewFlipperImage;
    private Toolbar myToolBar;
    private Spinner spinnerCategory;
    private TimePickerDialog timePickerDialog;
    private Button btnCreate;
    private EditText edtNameTask;
    private Calendar calendar;
    private RatingBar ratingTask;
    private String[] itemsIcon;
    private String type;
    private Category category;
    private String currentID;
    private SpinnerCategoryAdapter spinnerCategoryAdapter;
    private Task taskID;
    private SQLite sqLite;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_task);
        sqLite = new SQLite(getApplicationContext());

        findViewByIDs();
        addAppBar();

        spinnerCategoryAdapter = new SpinnerCategoryAdapter(AddTaskActivity.this, R.layout.spinner_category, CategoryModel.getItems());
        spinnerCategory.setAdapter(spinnerCategoryAdapter);
        addSpinner(false);

        addTime();
        addViewFlipperIcons(R.array.task_icon);

        currentID = getIntent().getStringExtra("getID");
        if (currentID != null) {
            addValue();
        }
    }

    @SuppressLint("SetTextI18n")
    private void addValue() {
        taskID = TaskModel.getItemsByID(currentID);

        assert taskID != null;
        edtNameTask.setText(taskID.getName());

        @SuppressLint("SimpleDateFormat") SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm");
        btnCreate.setText("Starting at: " + simpleDateFormat.format(taskID.getTime().getTime()));

        ratingTask.setRating(taskID.getRating());

        int position = spinnerCategoryAdapter.getPosition(CategoryModel.getItemsByID(taskID.getType()));
        spinnerCategory.setSelection(position);

        addSpinner(true);
    }

    private void addSpinner(boolean flag) {
        spinnerCategory.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                category = (Category) spinnerCategory.getSelectedItem();
                type = category.getId();

                switch (type) {
                    case "Category_Home":
                        viewFlipperImage.removeAllViews();
                        addViewFlipperIcons(R.array.home_icon);
                        if (flag) {
                            String iconName = getResources().getResourceEntryName(taskID.getIcon());
                            int viewFlipperPos = Arrays.asList(itemsIcon).indexOf(iconName);
                            viewFlipperImage.setDisplayedChild(viewFlipperPos);
                        }
                        break;
                    case "Category_Working":
                        viewFlipperImage.removeAllViews();
                        addViewFlipperIcons(R.array.work_icon);
                        if (flag) {
                            String iconName = getResources().getResourceEntryName(taskID.getIcon());
                            int viewFlipperPos = Arrays.asList(itemsIcon).indexOf(iconName);
                            viewFlipperImage.setDisplayedChild(viewFlipperPos);
                        }
                        break;
                    case "Category_Sport":
                        viewFlipperImage.removeAllViews();
                        addViewFlipperIcons(R.array.sport_icon);
                        if (flag) {
                            String iconName = getResources().getResourceEntryName(taskID.getIcon());
                            int viewFlipperPos = Arrays.asList(itemsIcon).indexOf(iconName);
                            viewFlipperImage.setDisplayedChild(viewFlipperPos);
                        }
                        break;
                    case "Category_Entertainment":
                        viewFlipperImage.removeAllViews();
                        addViewFlipperIcons(R.array.entertainment_item);
                        if (flag) {
                            String iconName = getResources().getResourceEntryName(taskID.getIcon());
                            int viewFlipperPos = Arrays.asList(itemsIcon).indexOf(iconName);
                            viewFlipperImage.setDisplayedChild(viewFlipperPos);
                        }
                        break;
                    case "Category_Others":
                        viewFlipperImage.removeAllViews();
                        addViewFlipperIcons(R.array.other_item);
                        if (flag) {
                            String iconName = getResources().getResourceEntryName(taskID.getIcon());
                            int viewFlipperPos = Arrays.asList(itemsIcon).indexOf(iconName);
                            viewFlipperImage.setDisplayedChild(viewFlipperPos);
                        }
                        break;
                    case "Category_Studying":
                        viewFlipperImage.removeAllViews();
                        addViewFlipperIcons(R.array.study_item);
                        if (flag) {
                            String iconName = getResources().getResourceEntryName(taskID.getIcon());
                            int viewFlipperPos = Arrays.asList(itemsIcon).indexOf(iconName);
                            viewFlipperImage.setDisplayedChild(viewFlipperPos);
                        }
                        break;
                    default:
                        viewFlipperImage.removeAllViews();
                        addViewFlipperIcons(R.array.task_icon);
                        if (flag) {
                            String iconName = getResources().getResourceEntryName(taskID.getIcon());
                            int viewFlipperPos = Arrays.asList(itemsIcon).indexOf(iconName);
                            viewFlipperImage.setDisplayedChild(viewFlipperPos);
                        }
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    @SuppressLint("SetTextI18n")
    private void findViewByIDs() {
        viewFlipperImage = findViewById(R.id.viewFlipperImage);
        myToolBar = findViewById(R.id.myAddToolBar);
        spinnerCategory = findViewById(R.id.spinnerCategory);

        btnCreate = findViewById(R.id.btnCreate);
        calendar = Calendar.getInstance();
        @SuppressLint("SimpleDateFormat") SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm");
        btnCreate.setText("Starting at: " + simpleDateFormat.format(calendar.getTime()));

        edtNameTask = findViewById(R.id.edtNameTask);
        ratingTask = findViewById(R.id.ratingTask);
    }

    private void addAppBar() {
        setSupportActionBar(myToolBar);
        ActionBar actionBar = this.getSupportActionBar();
        assert actionBar != null;
        actionBar.setDisplayHomeAsUpEnabled(true); //thêm nút back
    }

    private void addViewFlipperIcons(int allItems) {
        itemsIcon = getResources().getStringArray(allItems);
        for (String icons : itemsIcon) {
            viewFlipperImage.addView(new ViewFlipperCustomActivity(AddTaskActivity.this, icons));
        }
    }

    @SuppressLint("SetTextI18n")
    private void addTime() {
        calendar = Calendar.getInstance();
        timePickerDialog = new TimePickerDialog(AddTaskActivity.this, (view, hourOfDay, minute) -> {
            calendar.set(Calendar.HOUR_OF_DAY, hourOfDay);
            calendar.set(Calendar.MINUTE, minute);

            @SuppressLint("SimpleDateFormat") SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm");
            btnCreate.setText("Starting at: " + simpleDateFormat.format(calendar.getTime()));
        }, calendar.get(Calendar.HOUR), calendar.get(Calendar.MINUTE), true);
    }

    @SuppressLint("NonConstantResourceId")
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.imgBtnPrevious:
                viewFlipperImage.showPrevious();
                break;
            case R.id.imgBtnNext:
                viewFlipperImage.showNext();
                break;
            case R.id.btnCreate:
                timePickerDialog.show();
                break;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_add, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.action_check) {

            String name = edtNameTask.getText().toString();
            if (TextUtils.isEmpty(name)) {
                Toast.makeText(AddTaskActivity.this, "Please enter your task name!!!", Toast.LENGTH_LONG).show();
            } else {
                String ID = "Task_" + randomString();
                int rating = (int) ratingTask.getRating();
                int icon = ViewFlipperCustomActivity.getResource(AddTaskActivity.this, itemsIcon[viewFlipperImage.getDisplayedChild()]);
                if (currentID != null) {
                    TaskModel.editTask(currentID, name, calendar, icon, rating, type);
                    Task currentTask = TaskModel.getItemsByID(currentID);
                    sqLite.edit(currentTask);
                    setResult(MainActivity.REQUEST_CODE_TASK_EDIT_DONE, getIntent());
                } else {
                    Task task = new Task(ID, name, calendar, icon, rating, type);
                    if (sqLite.add(task)) {
                        Toast.makeText(AddTaskActivity.this, "Success!!!", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(AddTaskActivity.this, "Fail!!!", Toast.LENGTH_SHORT).show();
                    }
                    TaskModel.addTask(ID, name, calendar, icon, rating, type);
                    setResult(MainActivity.REQUEST_CODE_TASK_ADD_DONE, getIntent());
                }
                //Toast.makeText(AddTaskActivity.this, "Successful!!!", Toast.LENGTH_LONG).show();
            }
        }
        return super.onOptionsItemSelected(item);
    }

    private String randomString() {
        String characters = "abcdefghijklmnopqrstuvwxyz0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        int characters_length = characters.length();
        Random random = new Random();
        StringBuilder id = new StringBuilder();
        for (int i = 0; i < 4; i++) {
            id.append(characters.charAt(random.nextInt(characters_length)));
        }
        return id.toString();
    }
}
package com.example.todolist.mactivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.Toast;

import com.example.todolist.R;
import com.example.todolist.madapter.ListViewTaskAdapter;
import com.example.todolist.mclass.Task;
import com.example.todolist.mevent.Multi_Delete;
import com.example.todolist.mmodel.TaskModel;
import com.example.todolist.msqlite.SQLite;

import java.util.ArrayList;
import java.util.Collections;

public class MainActivity extends AppCompatActivity {
    private ListView lvTask;
    public static SQLite sqLite;
    private ListViewTaskAdapter listViewTaskAdapter;
    public static ArrayList<Task> task;
    public static final int REQUEST_CODE_TASK_DELETE = 999;
    public static final int REQUEST_CODE_TASK_INFO = 998;
    public static final int REQUEST_CODE_TASK_ADD = 997;
    public static final int REQUEST_CODE_TASK_ADD_DONE = 996;
    public static final int REQUEST_CODE_TASK_EDIT = 995;
    public static final int REQUEST_CODE_TASK_EDIT_DONE = 994;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sqLite = new SQLite(getApplicationContext());
        sqLite.getWritableDatabase();
        task = new ArrayList<Task>();
        task = sqLite.showList();

        Toolbar myToolBar = findViewById(R.id.myToolBar);
        setSupportActionBar(myToolBar);
        ActionBar actionBar = this.getSupportActionBar();
        assert actionBar != null;
        actionBar.show();

        findViewByIDs();
        listViewTaskAdapter = new ListViewTaskAdapter(MainActivity.this, R.layout.custom_listview_task, task);
        lvTask.setAdapter(listViewTaskAdapter);
        lvTask.setOnItemClickListener((parent, view, position, id) -> {
            Task currentTask = (Task) parent.getItemAtPosition(position);
            Intent intent = new Intent(MainActivity.this, InfoTaskActivity.class);
            intent.putExtra("getID", currentTask.getId());
            startActivityForResult(intent, REQUEST_CODE_TASK_INFO);
        });

        lvTask.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE_MODAL);
        lvTask.setMultiChoiceModeListener(new Multi_Delete(MainActivity.this, lvTask, listViewTaskAdapter));
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE_TASK_INFO && resultCode == REQUEST_CODE_TASK_DELETE) {
            assert data != null;
            String currentID = data.getStringExtra("getID");
            Task currentTask = TaskModel.getItemsByID(currentID);
            sqLite.delete(currentTask);
            task.remove(currentTask);
            listViewTaskAdapter.updateItems(currentTask);
            listViewTaskAdapter.notifyDataSetChanged();
            Toast.makeText(MainActivity.this, "Success", Toast.LENGTH_LONG).show();
        }

        if (requestCode == REQUEST_CODE_TASK_ADD && resultCode == REQUEST_CODE_TASK_ADD_DONE) {
            listViewTaskAdapter.notifyDataSetChanged();
            Toast.makeText(MainActivity.this, "Success", Toast.LENGTH_LONG).show();
        }

        if (requestCode == REQUEST_CODE_TASK_INFO && resultCode == REQUEST_CODE_TASK_EDIT_DONE) {
            listViewTaskAdapter.notifyDataSetChanged();
        }
    }

    private void findViewByIDs() {
        lvTask = findViewById(R.id.lvTask);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);

        MenuItem searchItem = menu.findItem(R.id.action_search);
        SearchView searchView = (SearchView) MenuItemCompat.getActionView(searchItem);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                listViewTaskAdapter.filter(newText);
                return false;
            }
        });
        return super.onCreateOptionsMenu(menu);
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.sort_name_AZ:
                Collections.sort(task, (o1, o2) -> o1.getName().compareTo(o2.getName()));
                listViewTaskAdapter.notifyDataSetChanged();
                break;
            case R.id.sort_name_ZA:
                Collections.sort(task, (o1, o2) -> o2.getName().compareTo(o1.getName()));
                listViewTaskAdapter.notifyDataSetChanged();
                break;
            case R.id.sort_rating_ASC:
                Collections.sort(task, (o1, o2) -> {
                    if (o1.getRating() > o2.getRating()) {
                        return 1;
                    } else return -1;
                });
                listViewTaskAdapter.notifyDataSetChanged();
                break;
            case R.id.sort_rating_DESC:
                Collections.sort(task, (o1, o2) -> {
                    if (o2.getRating() > o1.getRating()) {
                        return 1;
                    } else return -1;
                });
                listViewTaskAdapter.notifyDataSetChanged();
                break;
            case R.id.action_add:
                Intent myIntent = new Intent(MainActivity.this, AddTaskActivity.class);
                startActivityForResult(myIntent, REQUEST_CODE_TASK_ADD);
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
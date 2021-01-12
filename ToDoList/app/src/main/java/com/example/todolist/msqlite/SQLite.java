package com.example.todolist.msqlite;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.annotation.Nullable;

import com.example.todolist.mclass.Task;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class SQLite extends SQLiteOpenHelper {

    private SimpleDateFormat formatter;
    private Date timeFormat;
    public SQLiteDatabase myDB;
    public static final String DB_NAME = "toDoList.db";
    public static final int DB_VERSION = 1;

    public static final String TABLE_TASK = "task";
    public static final String TABLE_TASK_ID = "id";
    public static final String TABLE_TASK_NAME = "name";
    public static final String TABLE_TASK_TIME = "time";
    public static final String TABLE_TASK_ICON = "icon";
    public static final String TABLE_TASK_RATING = "rating";
    public static final String TABLE_TASK_TYPE = "type";

    public static final String TABLE_CATEGORY = "category";
    public static final String TABLE_CATEGORY_ID = "id";
    public static final String TABLE_CATEGORY_NAME = "name";

    public static final String SQL_CREATE_TABLE_TASK = "create table " + TABLE_TASK + " (" + TABLE_TASK_ID + " text primary key not null," + TABLE_TASK_NAME + " text not null," + TABLE_TASK_TIME + " text not null," +
            TABLE_TASK_ICON + " integer not null," + TABLE_TASK_RATING + " integer not null," + TABLE_TASK_TYPE + " text not null)";
    public static final String SQL_CREATE_TABLE_CATEGORY = "create table " + TABLE_CATEGORY + " (" + TABLE_CATEGORY_ID + " TEXT primary key autoincrement, " + TABLE_CATEGORY_NAME + " text)";

    public SQLite(@Nullable Context context) {
        super(context, DB_NAME, null, DB_VERSION);
        myDB = this.getWritableDatabase();
    }

    @SuppressLint("SimpleDateFormat")
    public boolean add(Task task) {
        ContentValues contentValues = new ContentValues();
        formatter = new SimpleDateFormat("HH:mm");
        String timeFormat = formatter.format(task.getTime().getTime());
        contentValues.put(TABLE_TASK_ID, task.getId());
        contentValues.put(TABLE_TASK_NAME, task.getName());
        contentValues.put(TABLE_TASK_TIME, timeFormat);
        contentValues.put(TABLE_TASK_ICON, task.getIcon());
        contentValues.put(TABLE_TASK_RATING, task.getRating());
        contentValues.put(TABLE_TASK_TYPE, task.getType());

        long check = myDB.insert(TABLE_TASK, null, contentValues);
        return check != -1;
    }

    @SuppressLint("SimpleDateFormat")
    public ArrayList<Task> showList() {
        ArrayList<Task> items = new ArrayList<>();
        @SuppressLint("Recycle") Cursor cursor = myDB.rawQuery("select * from " + TABLE_TASK, null);
        if (cursor.getCount() > 0) {
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                String valueID = cursor.getString(0);
                String valueName = cursor.getString(1);
                String valueTime = cursor.getString(2);
                int valueIcon = cursor.getInt(3);
                int valueRating = cursor.getInt(4);
                String valueType = cursor.getString(5);

                formatter = new SimpleDateFormat("HH:mm");
                try {
                    timeFormat = formatter.parse(valueTime);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                Calendar calendar = Calendar.getInstance();
                calendar.setTime(timeFormat);
                Task task = new Task(valueID, valueName, calendar, valueIcon, valueRating, valueType);
                items.add(task);
                cursor.moveToNext();
            }
        }
        return items;
    }

    public boolean delete(Task task) {
        int row = myDB.delete(TABLE_TASK, TABLE_TASK_ID + " = ?", new String[]{task.getId()});
        return row > 0;
    }

    @SuppressLint("SimpleDateFormat")
    public boolean edit(Task task) {
        formatter = new SimpleDateFormat("HH:mm");
        String timeFormat = formatter.format(task.getTime().getTime());
        ContentValues contentValues = new ContentValues();
        contentValues.put(TABLE_TASK_NAME, task.getName());
        contentValues.put(TABLE_TASK_TIME, timeFormat);
        contentValues.put(TABLE_TASK_ICON, task.getIcon());
        contentValues.put(TABLE_TASK_RATING, task.getRating());
        contentValues.put(TABLE_TASK_TYPE, task.getType());

        int row = myDB.update(TABLE_TASK, contentValues, TABLE_TASK_ID + " = ?", new String[]{task.getId()});
        return row > 0;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_TABLE_TASK);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_TASK);
        onCreate(db);
    }
}

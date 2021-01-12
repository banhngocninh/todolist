package com.example.todolist.mmodel;


import com.example.todolist.mactivity.MainActivity;
import com.example.todolist.mclass.Task;

import java.util.ArrayList;
import java.util.Calendar;

public class TaskModel {
    private static ArrayList<Task> items;

    public static ArrayList<Task> getItems() {
        if (MainActivity.task == null) {
            MainActivity.task = new ArrayList<Task>();

//            items.add(new Task("Task_01", "Watch TV", Calendar.getInstance(), R.drawable.watching_64, 1, "Category_Home"));
//            items.add(new Task("Task_02", "Do Homework", Calendar.getInstance(), R.drawable.homework_64, 2, "Category_Home"));
//            items.add(new Task("Task_03", "Meeting", Calendar.getInstance(), R.drawable.meeting_64, 3, "Category_Working"));
//            items.add(new Task("Task_04", "Do Exam", Calendar.getInstance(), R.drawable.exam_64, 4, "Category_Studying"));
//            items.add(new Task("Task_05", "Running", Calendar.getInstance(), R.drawable.running_64, 5, "Category_Sport"));
        }
        return MainActivity.task;
    }

    public static void editTask(String ID, String name, Calendar time, int icon, int rating, String type) {
        Task currentTask = getItemsByID(ID);
        if (currentTask == null) return;
        currentTask.setName(name);
        currentTask.setTime(time);
        currentTask.setRating(rating);
        currentTask.setIcon(icon);
        currentTask.setType(type);
    }

    public static void addTask(String ID, String name, Calendar time, int icon, int rating, String type) {
        Task newTask = new Task(ID, name, time, rating, icon, type);
        MainActivity.task.add(newTask);
    }

    public static Task getItemsByID(String id) {
        if (MainActivity.task == null) {
            getItems();
        } else {
            for (int i = 0; i < MainActivity.task.size(); i++) {
                String getID = MainActivity.task.get(i).getId();
                if (getID.equalsIgnoreCase(id)) {
                    return MainActivity.task.get(i);
                }
            }
        }
        return null;
    }
}

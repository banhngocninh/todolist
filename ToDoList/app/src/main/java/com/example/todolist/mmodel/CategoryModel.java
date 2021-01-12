package com.example.todolist.mmodel;

import com.example.todolist.mclass.Category;

import java.util.ArrayList;

public class CategoryModel {
    private static ArrayList<Category> items;

    public static ArrayList<Category> getItems() {
        if (items == null) {
            items = new ArrayList<Category>();

            items.add(new Category("Category_Home", "Home", 1));
            items.add(new Category("Category_Working", "Work", 1));
            items.add(new Category("Category_Studying", "Study", 1));
            items.add(new Category("Category_Sport", "Sport", 1));
            items.add(new Category("Category_Entertainment", "Entertainment", 1));
            items.add(new Category("Category_Others", "Others", 1));

        }
        return items;
    }

    public static String getNameByID(String id) {
        if (items == null) {
            getItems();
        } else {
            for (int i = 0; i < items.size(); i++) {
                String getID = getItems().get(i).getId();
                if (getID.equalsIgnoreCase(id)) {
                    return getItems().get(i).getName();
                }
            }
        }
        return null;
    }

    public static Category getItemsByID(String id) {
        if (items == null) {
            getItems();
        } else {
            for (int i = 0; i < items.size(); i++) {
                String getID = getItems().get(i).getId();
                if (getID.equalsIgnoreCase(id)) {
                    return items.get(i);
                }
            }
        }
        return null;
    }
}

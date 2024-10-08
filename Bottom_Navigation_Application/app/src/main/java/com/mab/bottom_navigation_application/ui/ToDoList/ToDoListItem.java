package com.mab.bottom_navigation_application.ui.ToDoList;

public class ToDoListItem {
    private int imageResId;
    private String description;
    private boolean isChecked;

    public ToDoListItem(int imageResId, String description, boolean isChecked) {
        this.imageResId = imageResId;
        this.description = description;
        this.isChecked = isChecked;
    }

    public int getImageResId() {
        return imageResId;
    }

    public void setImageResId(int imageResId) {
        this.imageResId = imageResId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isChecked() {
        return isChecked;
    }

    public void setChecked(boolean checked) {
        isChecked = checked;
    }
}
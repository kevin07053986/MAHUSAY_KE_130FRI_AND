package com.mab.linearlayout_application;

public class ItemModel {
    private String text;
    private int imageResId;
    private boolean isChecked;

    public ItemModel(String text, int imageResId, boolean isChecked) {
        this.text = text;
        this.imageResId = imageResId;
        this.isChecked = isChecked;
    }

    public String getText() {
        return text;
    }

    public int getImageResId() {
        return imageResId;
    }

    public boolean isChecked() {
        return isChecked;
    }

    public void setChecked(boolean checked) {
        isChecked = checked;
    }

    public void setText(String text) {
        this.text = text;
    }
}
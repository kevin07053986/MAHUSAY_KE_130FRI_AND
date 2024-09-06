package com.mab.linearlayout_application;

public class ItemModel {
    private boolean checked;
    private String text;
    private int imageResource;

    public ItemModel(String text, int imageResource, boolean checked) {
        this.text = text;
        this.imageResource = imageResource;
        this.checked = checked;
    }

    public boolean isChecked() {
        return checked;
    }

    public void setChecked(boolean checked) {
        this.checked = checked;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public int getImageResource() {
        return imageResource;
    }

    public void setImageResource(int imageResource) {
        this.imageResource = imageResource;
    }
}
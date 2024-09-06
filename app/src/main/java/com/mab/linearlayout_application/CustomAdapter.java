package com.mab.linearlayout_application;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;
import java.util.ArrayList;

public class CustomAdapter extends BaseAdapter {

    private final Context context;
    private final ArrayList<ItemModel> items;

    public CustomAdapter(Context context, ArrayList<ItemModel> items) {
        this.context = context;
        this.items = items;
    }

    @Override
    public int getCount() {
        return items.size();
    }

    @Override
    public Object getItem(int position) {
        return items.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            LayoutInflater inflater = LayoutInflater.from(context);
            convertView = inflater.inflate(R.layout.list_item, parent, false);
        }

        CheckBox checkBox = convertView.findViewById(R.id.item_checkbox);
        ImageView imageView = convertView.findViewById(R.id.item_image);
        TextView textView = convertView.findViewById(R.id.item_text);

        // Get the current item
        ItemModel currentItem = items.get(position);

        // Set the data for the current item
        checkBox.setChecked(currentItem.isChecked());
        imageView.setImageResource(currentItem.getImageResource());
        textView.setText(currentItem.getText());

        return convertView;
    }

    // Method to update the item at a specific position
    public void updateItem(int position, String newText) {
        items.get(position).setText(newText);
        notifyDataSetChanged();
    }

    // Method to delete an item
    public void deleteItem(int position) {
        items.remove(position);
        notifyDataSetChanged();
    }
}
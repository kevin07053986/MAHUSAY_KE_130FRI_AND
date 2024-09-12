package com.mab.linearlayout_application;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;
import java.util.List;

public class CustomAdapter extends BaseAdapter {
    private Context context;
    private List<ItemModel> itemList;

    public CustomAdapter(Context context, List<ItemModel> itemList) {
        this.context = context;
        this.itemList = itemList;
    }

    @Override
    public int getCount() {
        return itemList.size();
    }

    @Override
    public Object getItem(int position) {
        return itemList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.list_item, parent, false);
        }

        ItemModel currentItem = (ItemModel) getItem(position);

        CheckBox checkBox = convertView.findViewById(R.id.item_checkbox);
        ImageView imageView = convertView.findViewById(R.id.item_image);
        TextView textView = convertView.findViewById(R.id.item_text);

        checkBox.setChecked(currentItem.isChecked());
        imageView.setImageResource(currentItem.getImageResId());
        textView.setText(currentItem.getText());

        return convertView;
    }

    public void updateItem(int position, ItemModel newItem) {
        itemList.set(position, newItem);
        notifyDataSetChanged();
    }

    public void removeItem(int position) {
        itemList.remove(position);
        notifyDataSetChanged();
    }
}
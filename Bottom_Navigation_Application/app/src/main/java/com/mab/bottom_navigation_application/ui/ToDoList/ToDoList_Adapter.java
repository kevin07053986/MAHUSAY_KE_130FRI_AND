package com.mab.bottom_navigation_application.ui.ToDoList;

import android.app.AlertDialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import com.mab.bottom_navigation_application.R;
import java.util.ArrayList;
import java.util.List;

public class ToDoList_Adapter extends BaseAdapter {

    private final Context context;
    private final List<ToDoListItem> items;
    private List<ToDoListItem> filteredItems;

    public ToDoList_Adapter(Context context, List<ToDoListItem> items) {
        this.context = context;
        this.items = items;
        this.filteredItems = new ArrayList<>(items);
    }

    @Override
    public int getCount() {
        return filteredItems.size();
    }

    @Override
    public Object getItem(int position) {
        return filteredItems.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view;
        ViewHolder viewHolder;

        if (convertView == null) {
            view = LayoutInflater.from(context).inflate(R.layout.to_do_list_layout, parent, false);
            viewHolder = new ViewHolder(view);
            view.setTag(viewHolder);
        } else {
            view = convertView;
            viewHolder = (ViewHolder) view.getTag();
        }

        ToDoListItem listItem = filteredItems.get(position);
        viewHolder.imageView.setImageResource(listItem.getImageResId());
        viewHolder.textView.setText(listItem.getDescription());
        viewHolder.checkBox.setChecked(listItem.isChecked());
        viewHolder.checkBox.setOnCheckedChangeListener((buttonView, isChecked) -> listItem.setChecked(isChecked));

        return view;
    }

    public void addItem(ToDoListItem item) {
        filteredItems.add(item);
        items.add(item); // Also add it to the main list if needed for filtering
        notifyDataSetChanged();
    }

    private static class ViewHolder {
        final ImageView imageView;
        final TextView textView;
        final CheckBox checkBox;

        ViewHolder(View view) {
            imageView = view.findViewById(R.id.img);
            textView = view.findViewById(R.id.description);
            checkBox = view.findViewById(R.id.checkbox);
        }
    }

    public void filter(String query) {
        filteredItems.clear();
        if (query.isEmpty()) {
            filteredItems.addAll(items);
        } else {
            for (ToDoListItem item : items) {
                if (item.getDescription().toLowerCase().contains(query.toLowerCase())) {
                    filteredItems.add(item);
                }
            }
        }
        notifyDataSetChanged();
    }
}
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
            // Inflate the custom row layout
            view = LayoutInflater.from(context).inflate(R.layout.to_do_list_layout, parent, false);
            viewHolder = new ViewHolder(view);
            view.setTag(viewHolder);
        } else {
            view = convertView;
            viewHolder = (ViewHolder) view.getTag();
        }

        // Get the data for the current item
        ToDoListItem listItem = filteredItems.get(position);

        // Bind the data to the views
        viewHolder.imageView.setImageResource(listItem.getImageResId());
        viewHolder.textView.setText(listItem.getDescription());
        viewHolder.checkBox.setChecked(listItem.isChecked());

        viewHolder.checkBox.setOnCheckedChangeListener((buttonView, isChecked) -> listItem.setChecked(isChecked));

        view.setOnTouchListener(new View.OnTouchListener() {
            private long lastTouchTime = 0;
            private final long doubleClickInterval = 300; // milliseconds

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    long currentTime = System.currentTimeMillis();
                    if (currentTime - lastTouchTime < doubleClickInterval) {
                        showEditDeleteDialog(position);
                        return true;
                    }
                    lastTouchTime = currentTime;
                }
                return false;
            }
        });

        return view;
    }

    private void showEditDeleteDialog(int position) {
        ToDoListItem listItem = filteredItems.get(position);
        View dialogView = LayoutInflater.from(context).inflate(R.layout.edit_delete_layout, null);

        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setView(dialogView);
        builder.setTitle("Options");

        Button editButton = dialogView.findViewById(R.id.btn_edit);
        Button deleteButton = dialogView.findViewById(R.id.btn_delete);

        AlertDialog dialog = builder.create();

        editButton.setOnClickListener(v -> {
            View editDialogView = LayoutInflater.from(context).inflate(R.layout.edit_layout, null);
            EditText editText = editDialogView.findViewById(R.id.editTaskDescription);
            editText.setText(listItem.getDescription());

            AlertDialog.Builder editDialogBuilder = new AlertDialog.Builder(context);
            editDialogBuilder.setView(editDialogView);
            editDialogBuilder.setTitle("Edit Task");

            Button saveButton = editDialogView.findViewById(R.id.btnSave);

            AlertDialog editDialog = editDialogBuilder.create();
            editDialog.show();

            saveButton.setOnClickListener(view -> {
                String newDescription = editText.getText().toString();
                listItem.setDescription(newDescription);
                notifyDataSetChanged();
                editDialog.dismiss();
            });

            dialog.dismiss();
        });

        deleteButton.setOnClickListener(v -> {
            new AlertDialog.Builder(context)
                    .setTitle("Delete Task")
                    .setMessage("Are you sure you want to delete '" + listItem.getDescription() + "'?")
                    .setPositiveButton("Yes", (dialogInterface, i) -> {
                        filteredItems.remove(position);
                        notifyDataSetChanged();
                        dialog.dismiss();
                        Toast.makeText(context, "Task deleted", Toast.LENGTH_SHORT).show();
                    })
                    .setNegativeButton("No", null)
                    .show();
        });

        dialog.show();
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
package com.mab.linearlayout_application;

import android.os.Bundle;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private List<ItemModel> itemList;
    private CustomAdapter adapter;
    private EditText inputText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ListView listView = findViewById(R.id.item_list);
        inputText = findViewById(R.id.input_text);
        Button addButton = findViewById(R.id.add_button);

        itemList = new ArrayList<>();
        adapter = new CustomAdapter(this, itemList);
        listView.setAdapter(adapter);

        addButton.setOnClickListener(v -> {
            String text = inputText.getText().toString();
            if (!text.isEmpty()) {
                ItemModel newItem = new ItemModel(text, R.drawable.ic_launcher_background, false);
                itemList.add(newItem);
                adapter.notifyDataSetChanged();
                inputText.setText("");
            }
        });

        // Gesture detector for detecting double-tap
        final GestureDetector gestureDetector = new GestureDetector(this, new GestureDetector.SimpleOnGestureListener() {
            @Override
            public boolean onDoubleTap(MotionEvent e) {
                return true;
            }
        });

        // Set an item click listener on the ListView
        listView.setOnTouchListener((v, event) -> {
            View child = listView.getChildAt(listView.pointToPosition((int) event.getX(), (int) event.getY()));
            if (gestureDetector.onTouchEvent(event) && child != null) {
                int position = listView.getPositionForView(child);
                showEditDeleteDialog(position);
            }
            return false;
        });
    }

    private void showEditDeleteDialog(int position) {
        // Create an AlertDialog to show options Edit and Delete
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Choose an option");

        // Create a dialog with two options: Edit and Delete
        builder.setItems(new CharSequence[]{"Edit", "Delete"}, (dialog, which) -> {
            if (which == 0) {
                // Edit option selected
                showEditDialog(position);
            } else if (which == 1) {
                // Delete option selected
                adapter.removeItem(position);
            }
        });

        builder.show();
    }

    private void showEditDialog(int position) {
        // Create an AlertDialog to edit the text
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Edit Item");

        final EditText input = new EditText(this);
        input.setText(itemList.get(position).getText());
        builder.setView(input);

        // Set up Edit and Cancel buttons
        builder.setPositiveButton("Save", (dialog, which) -> {
            String newText = input.getText().toString();
            if (!newText.isEmpty()) {
                ItemModel updatedItem = new ItemModel(newText, itemList.get(position).getImageResId(), itemList.get(position).isChecked());
                adapter.updateItem(position, updatedItem);
            }
        });

        builder.setNegativeButton("Cancel", (dialog, which) -> dialog.cancel());

        builder.show();
    }
}
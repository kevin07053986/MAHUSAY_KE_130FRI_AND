package com.mab.bottom_navigation_application.ui.ToDoList;

import android.app.AlertDialog;
import android.content.Context;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import com.mab.bottom_navigation_application.R;
import java.util.ArrayList;
import java.util.List;


public class ToDoListFragment extends Fragment {

    private EditText edtSearch, inputText;
    private List<ToDoListItem> itemList;
    private ToDoList_Adapter adapter;
    private ListView listView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_to_do_list, container, false);

        edtSearch = view.findViewById(R.id.edtSearch);
        inputText = view.findViewById(R.id.input_text);
        Button addButton = view.findViewById(R.id.add_button);
        listView = view.findViewById(R.id.toDoListView);

        // Initialize the list with existing items
        itemList = new ArrayList<>();
        itemList.add(new ToDoListItem(R.drawable.img1, "Rodrigo Roa Duterte", true));
        itemList.add(new ToDoListItem(R.drawable.img1, "Benigno Simeon Aquino III", false));
        itemList.add(new ToDoListItem(R.drawable.img1, "Maria Gloria Macaraeg Macapagal-Arroyo", false));
        itemList.add(new ToDoListItem(R.drawable.img1, "Joseph Ejercito Estrada", false));
        itemList.add(new ToDoListItem(R.drawable.img1, "Fidel Valdez Ramos", false));
        itemList.add(new ToDoListItem(R.drawable.img1, "Maria Corazon Sumulong Cojuangco-Aquino", false));

        // Set up the adapter and attach it to the ListView
        adapter = new ToDoList_Adapter(requireContext(), itemList);
        listView.setAdapter(adapter);

        // Add new item functionality
        addButton.setOnClickListener(v -> {
            String text = inputText.getText().toString();
            if (!text.isEmpty()) {
                ToDoListItem newItem = new ToDoListItem(R.drawable.img1, text, false);
                itemList.add(newItem);
                adapter.addItem(newItem);
                adapter.notifyDataSetChanged();
                inputText.setText("");
            }
        });

        // Implement search functionality
        edtSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void afterTextChanged(Editable s) {}

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                adapter.filter(s.toString());
            }
        });

        // Gesture detector for detecting double-tap
        GestureDetector gestureDetector = new GestureDetector(requireContext(), new GestureDetector.SimpleOnGestureListener() {
            @Override
            public boolean onDoubleTap(MotionEvent e) {
                return true;
            }
        });

        // Set an item click listener on the ListView for double-tap editing
        listView.setOnTouchListener((v, event) -> {
            View child = listView.getChildAt(listView.pointToPosition((int) event.getX(), (int) event.getY()));
            if (gestureDetector.onTouchEvent(event) && child != null) {
                int position = listView.getPositionForView(child);
                showEditDeleteDialog(position);
            }
            return false;
        });

        return view;
    }

    private void showEditDeleteDialog(int position) {
        ToDoListItem listItem = itemList.get(position);
        View dialogView = LayoutInflater.from(requireContext()).inflate(R.layout.edit_delete_layout, null);

        AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());
        builder.setView(dialogView);
        builder.setTitle("Options");

        Button editButton = dialogView.findViewById(R.id.btn_edit);
        Button deleteButton = dialogView.findViewById(R.id.btn_delete);

        AlertDialog dialog = builder.create();

        editButton.setOnClickListener(v -> {
            showEditDialog(position, listItem);
            dialog.dismiss();
        });

        deleteButton.setOnClickListener(v -> {
            itemList.remove(position);
            adapter.notifyDataSetChanged();
            dialog.dismiss();
            Toast.makeText(requireContext(), "Task deleted", Toast.LENGTH_SHORT).show();
        });

        dialog.show();
    }

    private void showEditDialog(int position, ToDoListItem listItem) {
        View editDialogView = LayoutInflater.from(requireContext()).inflate(R.layout.edit_layout, null);
        EditText editText = editDialogView.findViewById(R.id.editTaskDescription);
        editText.setText(listItem.getDescription());

        AlertDialog.Builder editDialogBuilder = new AlertDialog.Builder(requireContext());
        editDialogBuilder.setView(editDialogView);
        editDialogBuilder.setTitle("Edit Task");

        Button saveButton = editDialogView.findViewById(R.id.btnSave);
        AlertDialog editDialog = editDialogBuilder.create();

        saveButton.setOnClickListener(view -> {
            String newDescription = editText.getText().toString();
            if (!newDescription.isEmpty()) {
                listItem.setDescription(newDescription);
                adapter.notifyDataSetChanged();
                editDialog.dismiss();
            }
        });

        editDialog.show();
    }
}
package com.mab.linearlayout_application;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    ListView listView;
    CustomAdapter customAdapter;
    ArrayList<ItemModel> items;
    EditText inputEditText;
    private int clickedPosition = -1;
    private long lastClickTime = 0;
    private static final long DOUBLE_CLICK_TIME_DELTA = 300;  // milliseconds

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listView = findViewById(R.id.custom_listview);
        inputEditText = findViewById(R.id.input_edit_text);

        // Initialize the list of items
        items = new ArrayList<>();
        items.add(new ItemModel("Item 1", R.mipmap.ic_launcher, false));
        items.add(new ItemModel("Item 2", R.mipmap.ic_launcher, false));

        // Create an instance of the custom adapter and set it to the ListView
        customAdapter = new CustomAdapter(this, items);
        listView.setAdapter(customAdapter);

        // Set up the double-click listener to edit/delete items
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
                long clickTime = System.currentTimeMillis();
                if (clickTime - lastClickTime < DOUBLE_CLICK_TIME_DELTA) {
                    // Double click detected - show edit options (edit or delete)
                    showEditOptions(position);
                }
                lastClickTime = clickTime;
            }
        });
    }

    private void showEditOptions(final int position) {
        inputEditText.setVisibility(View.VISIBLE);
        inputEditText.setText(items.get(position).getText());

        // Listener for updating item text
        inputEditText.setOnEditorActionListener((v, actionId, event) -> {
            String updatedText = inputEditText.getText().toString();
            customAdapter.updateItem(position, updatedText);
            inputEditText.setText("");
            inputEditText.setVisibility(View.GONE);
            return true;
        });

        // To delete the item
        new Handler().postDelayed(() -> {
            if (!inputEditText.isShown()) {
                customAdapter.deleteItem(position);
            }
        }, 3000);
    }
}
package com.mab.linearlayout_application;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;

public class MainActivity extends AppCompatActivity {

    ListView listView;
    String[] titles = {"Item 1", "Item 2", "Item 3", "Item 4"};
    String[] subtitles = {"Subtitle 1", "Subtitle 2", "Subtitle 3", "Subtitle 4"};
    int[] images = {R.mipmap.ic_launcher, R.mipmap.ic_launcher, R.mipmap.ic_launcher, R.mipmap.ic_launcher};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listView = findViewById(R.id.custom_listview);

        // Create an instance of the custom adapter and set it to the ListView
        CustomAdapter customAdapter = new CustomAdapter(this, titles, subtitles, images);
        listView.setAdapter(customAdapter);
    }
}
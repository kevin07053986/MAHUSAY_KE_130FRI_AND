package com.mab.linearlayout_application;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class CustomAdapter extends BaseAdapter {

    private final Context context;
    private final String[] titles;
    private final String[] subtitles;
    private final int[] images;

    public CustomAdapter(Context context, String[] titles, String[] subtitles, int[] images) {
        this.context = context;
        this.titles = titles;
        this.subtitles = subtitles;
        this.images = images;
    }

    @Override
    public int getCount() {
        return titles.length;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            LayoutInflater inflater = LayoutInflater.from(context);
            convertView = inflater.inflate(R.layout.list_item, parent, false);
        }

        ImageView imageView = convertView.findViewById(R.id.item_image);
        TextView titleView = convertView.findViewById(R.id.item_title);
        TextView subtitleView = convertView.findViewById(R.id.item_subtitle);

        // Set the data
        imageView.setImageResource(images[position]);
        titleView.setText(titles[position]);
        subtitleView.setText(subtitles[position]);

        return convertView;
    }
}

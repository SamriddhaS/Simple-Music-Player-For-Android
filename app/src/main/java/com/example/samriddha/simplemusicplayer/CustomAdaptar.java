package com.example.samriddha.simplemusicplayer;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class CustomAdaptar extends ArrayAdapter<String> {

    public CustomAdaptar(@NonNull Context context, String[] myItems) {
        super(context, R.layout.song_custom_layout, myItems);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(getContext());
        View customView = inflater.inflate(R.layout.song_custom_layout,parent,false);

        String songname = getItem(position);

        TextView textView = customView.findViewById(R.id.customsongname);
        ImageView imageView = customView.findViewById(R.id.customimage);

        textView.setText(songname);
        imageView.setImageResource(R.drawable.songicon2);


        return customView;
    }
}


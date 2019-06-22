package com.example.samriddha.simplemusicplayer;

import android.content.Intent;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.io.File;
import java.util.ArrayList;

public class SongListActivity extends AppCompatActivity {

    ListView listView ;
    String[] myitems ;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_song_list);
        listView = findViewById(R.id.songlistview);



        final ArrayList<File> mysongs = findsongs (Environment.getExternalStorageDirectory());
        myitems = new String[mysongs.size()];

        for (int i= 0 ; i<mysongs.size();i++){
            myitems[i] = mysongs.get(i).getName().toString().replace(".mp3","");
        }
        ArrayAdapter<String> adapter = new CustomAdaptar(this,myitems);
        listView.setAdapter(adapter);




        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {


                startActivity(new Intent(getApplicationContext(),MainActivity.class).putExtra("position",position).putExtra("songlist",mysongs));
            }
        });




    }

    private ArrayList<File> findsongs(File externalStorageDir) {
        ArrayList<File> returnlist = new ArrayList<File>();

        File[] filelist = externalStorageDir.listFiles();
        for (File singlefile : filelist){
            if (singlefile.isDirectory() && !singlefile.isHidden()){
                returnlist.addAll(findsongs(singlefile));

            }
            else if(singlefile.getName().endsWith(".mp3")){
                returnlist.add(singlefile);

        }

    }
    return returnlist;
}
}

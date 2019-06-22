package com.example.samriddha.simplemusicplayer;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    Button play,stop,next , previous , songlist;

    static MediaPlayer mediaPlayer;
    SeekBar seekBar ;
    TextView time , remtime , songnametv;
    int totaltime;
    Uri uri;
    String [] myitems ;
    ArrayList mySongs ;
    int position;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        play = (Button)findViewById(R.id.playbutton);
        stop= (Button)findViewById(R.id.stopbutton);
        time = findViewById(R.id.time);
        remtime = findViewById(R.id.remtime);
        seekBar= findViewById(R.id.seekbar1);
        songnametv = findViewById(R.id.songname);
        previous = findViewById(R.id.pretrack);
        next = findViewById(R.id.nexttrack);
        songlist = findViewById(R.id.slistbutton);


        //mediaPlayer = MediaPlayer.create(getApplicationContext(),R.raw.shapeofyou);

        play.setOnClickListener(this);
        stop.setOnClickListener(this);
        songlist.setOnClickListener(this);
        next.setOnClickListener(this);
        previous.setOnClickListener(this);




        ////////////////////////////////////////////////////////////////////////////////////////////

        ///////////////////Reciving Data From SonglistActivity///////////////////////////

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();

        if(bundle==null){
            // to handel the bundel is null exception .
            Toast.makeText(this,"Please goto Songlist To Play Your Song",Toast.LENGTH_SHORT).show();
        }
        else{
            mySongs = (ArrayList) bundle.getParcelableArrayList("songlist");
            position = bundle.getInt("position", 0);

            if (!(mediaPlayer==null)){
                mediaPlayer.stop();
                mediaPlayer.release();
            }
            uri = Uri.parse(mySongs.get(position).toString());
            mediaPlayer = MediaPlayer.create(getApplicationContext(), uri);
            mediaPlayer.start();

            play.setBackgroundResource(R.drawable.ic_pause_black_24dp);

            /* mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                @Override
                public void onCompletion(MediaPlayer mp) {

                    if (position + 1 == mySongs.size()) {
                        position = mySongs.size() - mySongs.size();
                    } else {
                        position = position + 1;
                    }
                    uri = Uri.parse(mySongs.get(position).toString());
                    mediaPlayer = MediaPlayer.create(getApplicationContext(), uri);
                    mediaPlayer.start();
                    totaltime = mediaPlayer.getDuration();
                    seekBar.setMax(totaltime);

                }
            }); */

            }


        ///// seek bar codes ///////////////////////////////////////////////////////////////////////
        if(mediaPlayer == null){
            // to handel the mediaplayer is null exception .
        }
        else{
        totaltime = mediaPlayer.getDuration();
        seekBar.setMax(totaltime);
        seekBar.setOnSeekBarChangeListener(
                new SeekBar.OnSeekBarChangeListener() {
                    @Override
                    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                        if(fromUser){
                            mediaPlayer.seekTo(progress);
                            seekBar.setProgress(progress);
                        }
                    }

                    @Override
                    public void onStartTrackingTouch(SeekBar seekBar) {

                    }

                    @Override
                    public void onStopTrackingTouch(SeekBar seekBar) {

                    }
                }
        );}

        new Thread(new Runnable() {
            @Override
            public void run() {
                while (mediaPlayer!=null){
                    try {
                        Message message = new Message();
                        message.what = mediaPlayer.getCurrentPosition();
                        handler.sendMessage(message);
                        Thread.sleep(1000);



                    }catch (IllegalThreadStateException e){
                        e.printStackTrace();
                    }catch (IllegalStateException e){
                            e.printStackTrace();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }

            }
        }).start();


    }
    @SuppressLint("HandlerLeak")
    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            int currentpos= msg.what;
            seekBar.setProgress(currentpos);

            String elspedtime = createTimeLabel(currentpos);
            time.setText(elspedtime);

            String remtimelebel = createTimeLabel( totaltime );
            remtime.setText(" "+remtimelebel);
        }
    };

    public String createTimeLabel(int totaltime){
        String timelebel = "";
        int min = totaltime / 1000 / 60;
        int sec = totaltime/ 1000 %60 ;
        timelebel = min + ":";
        if(sec<10 ) timelebel += "0";
        timelebel += sec;
        return timelebel;
    }
    ////////////////////////////////////////////////////////////////////////////////////////////////

    /////////////////////////////////////////On Click Methods . Media player button codes/////////////////////////////////////
    @Override
    public void onClick(View v) {

        switch (v.getId()){
            case R.id.playbutton:
                if(mediaPlayer == null){
                    // to handel the mediaplayer is null exception .

                }else{
                if(!mediaPlayer.isPlaying()){
                    mediaPlayer.start();
                play.setBackgroundResource(R.drawable.ic_pause_black_24dp);
                }
               else if (mediaPlayer.isPlaying()){
                    mediaPlayer.pause();
                    play.setBackgroundResource(R.drawable.ic_play_arrow_black_24dp);
                }}

                break;

                case R.id.stopbutton:
                    if(mediaPlayer==null){
                        // to handel the mediaplayer is null exception .

                    }else {
                if(mediaPlayer!=null) {
                    mediaPlayer.pause();
                    mediaPlayer.seekTo(0);
                    play.setBackgroundResource(R.drawable.ic_play_arrow_black_24dp);

                    }}
                break;

            case R.id.slistbutton :
                startActivity(new Intent(this,SongListActivity.class));
                break;
            case R.id.nexttrack:
                if(mediaPlayer==null){
                    // to handel the media player is null exception .

                }else {

                    mediaPlayer.stop();
                    mediaPlayer.release();
                    if (position + 1 == mySongs.size()) {
                        position = mySongs.size() - mySongs.size();
                    } else {
                        position = position + 1;
                    }
                    uri = Uri.parse(mySongs.get(position).toString());
                    mediaPlayer = MediaPlayer.create(getApplicationContext(), uri);
                    mediaPlayer.start();
                    totaltime = mediaPlayer.getDuration();
                    seekBar.setMax(totaltime);
                }

                break;


            case R.id.pretrack:
                if(mediaPlayer==null){
                    // to handel the media player is null exception .
                }
                else {
                    mediaPlayer.stop();
                    mediaPlayer.release();
                    position = ((position - 1) < 0) ? (mySongs.size() - 1) : position - 1;

                /*if( (position-1) <0 ){
                    position = mySongs.size() - 1 ;
                }else {
                    position = position - 1 ;
                }*/

                    uri = Uri.parse(mySongs.get(position).toString());
                    mediaPlayer = MediaPlayer.create(getApplicationContext(), uri);
                    mediaPlayer.start();
                    totaltime = mediaPlayer.getDuration();
                    seekBar.setMax(totaltime);
                }
                break;

        }

    }
    /////////////////////////////////////////////////////////////////////////////////////////////////////////////////
}

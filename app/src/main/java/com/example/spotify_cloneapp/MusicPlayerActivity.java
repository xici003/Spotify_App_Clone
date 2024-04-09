package com.example.spotify_cloneapp;

import android.content.Intent;
import android.media.MediaMetadataRetriever;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.spotify_cloneapp.APIs.Service;
import com.example.spotify_cloneapp.Models.Song;
import com.squareup.picasso.Picasso;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MusicPlayerActivity extends AppCompatActivity {

    private Song song;

    int position = 0;
    private TextView albumName,songName,artistName,durationPlayed,durationTotal;
    private ImageView imgSong, playPauseBtn, nextBtn, prevBtn;
    private SeekBar seekBar;
    static Uri uri;
    static MediaPlayer mediaPlayer;
    private Handler handler = new Handler();

    private Thread playThread, prevThread, nextThread;
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_musicplayer_view);
        loadComponent();

        Intent intent = getIntent();
        if(intent != null){
            int idSong = intent.getIntExtra("idSong",position);

            Service.api.getSongsById(idSong).enqueue(new Callback<List<Song>>() {
                @Override
                public void onResponse(Call<List<Song>> call, Response<List<Song>> response) {
                    if(response.body().size()>0) {
                        song = response.body().get(position);

                        loadData();
                        getMusicPlayer();
                        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                            @Override
                            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                                if(mediaPlayer != null){
                                    mediaPlayer.seekTo(progress * 1000);
                                }
                            }

                            @Override
                            public void onStartTrackingTouch(SeekBar seekBar) {

                            }

                            @Override
                            public void onStopTrackingTouch(SeekBar seekBar) {

                            }
                        });

                        MusicPlayerActivity.this.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                if(mediaPlayer != null){
                                    int mCurrentPosition = mediaPlayer.getCurrentPosition() / 1000;
                                    seekBar.setProgress(mCurrentPosition);
                                    durationPlayed.setText(formattedTime(mCurrentPosition));
                                }

                                handler.postDelayed(this, 1000);
                            }
                        });

                    }
                }

                @Override
                public void onFailure(Call<List<Song>> call, Throwable t) {

                }
            });

        }


    }

    @Override
    protected void onResume() {
        playThreadBtn();
        super.onResume();
    }
    private void playThreadBtn() {
        playThread = new Thread(){
            @Override
            public void run() {
                super.run();
                playPauseBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        playPauseBtnClicked();
                    }
                });
            }
        };
        playThread.start();
    }

    private void playPauseBtnClicked() {
        if(mediaPlayer.isPlaying()){
            playPauseBtn.setImageResource(R.drawable.play2_icon);
            mediaPlayer.pause();
            seekBar.setMax(mediaPlayer.getDuration() / 1000);
            MusicPlayerActivity.this.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    if (mediaPlayer != null) {
                        int mCurrPosition = mediaPlayer.getCurrentPosition() / 1000;
                        seekBar.setProgress(mCurrPosition);
                    }
                    handler.postDelayed(this, 1000);
                }
            });
        }else{
            playPauseBtn.setImageResource(R.drawable.pause_icon);
            mediaPlayer.start();
            seekBar.setMax(mediaPlayer.getDuration()/1000);
            MusicPlayerActivity.this.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    if (mediaPlayer != null) {
                        int mCurrPosition = mediaPlayer.getCurrentPosition() / 1000;
                        seekBar.setProgress(mCurrPosition);
                    }
                    handler.postDelayed(this, 1000);
                }
            });
        }
    }

    protected void loadComponent(){
        albumName = findViewById(R.id.MV_txtAlbumName);
        imgSong = findViewById(R.id.MV_imgSong);
        songName = findViewById(R.id.MV_txtNameSong);
        artistName = findViewById(R.id.MV_txtNameArtist);
        playPauseBtn = findViewById(R.id.MV_playIcon);
        nextBtn = findViewById(R.id.MV_nextIcon);
        prevBtn = findViewById(R.id.MV_prevIcon);
        seekBar = findViewById(R.id.MV_seekBar);
        durationPlayed = findViewById(R.id.MV_durationPlayed);
        durationTotal = findViewById(R.id.MV_durationTotal);
    }

    protected void  loadData(){
        songName.setText(this.song.getNameSong());
        artistName.setText(this.song.getNameArtist());
        if(this.song.getThumbnail()!=null){
            Picasso.get().load(this.song.getThumbnail()).into(imgSong);
        }
    }

    protected void getMusicPlayer(){
        if(this.song != null){
            playPauseBtn.setImageResource(R.drawable.pause_icon);
            uri = Uri.parse(this.song.getURLmp3());
        }

        if(mediaPlayer != null){
            mediaPlayer.stop();
            mediaPlayer.release();
            mediaPlayer = MediaPlayer.create(getApplicationContext(), uri);
            mediaPlayer.start();
        }
        else
        {
            mediaPlayer = MediaPlayer.create(getApplicationContext(),uri);
            mediaPlayer.start();
        }

        seekBar.setMax(mediaPlayer.getDuration() / 1000);

        durationTotal.setText(this.song.getDuration());
    }

    private String formattedTime(int mCurrentPosition){
        String totalOut = "";
        String totalNew = "";
        String seconds = String.valueOf(mCurrentPosition % 60);
        String minutes = String.valueOf(mCurrentPosition / 60);
        totalOut = minutes + ":" + seconds;
        totalNew = minutes + ":0" + seconds;

        if(seconds.length() == 1){
            return totalNew;
        }else{
            return totalOut;
        }
    }

}

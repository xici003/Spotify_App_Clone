package com.example.spotify_cloneapp;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.spotify_cloneapp.Models.Song;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.List;

public class MusicPlayerActivity extends AppCompatActivity {

    private Song song;
    int position = 0;
    private TextView albumName, songName, artistName, lyrics, durationPlayed, durationTotal;
    private ImageView imgSong, playPauseBtn, nextBtn, prevBtn;
    private SeekBar seekBar;
    static Uri uri;
    static MediaPlayer mediaPlayer;
    private Handler handler = new Handler();

    private Thread playThread, prevThread, nextThread;

    private boolean apiCalled = false;
    boolean isSeeking = false;

    private List<Song> queue;
    private Runnable updateSeekBar = new Runnable() {
        @Override
        public void run() {
            if (mediaPlayer != null) {
                int mCurrentPosition = mediaPlayer.getCurrentPosition() / 1000;
                seekBar.setProgress(mCurrentPosition);
                durationPlayed.setText(formattedTime(mCurrentPosition));
            }
            handler.postDelayed(this, 1000);
        }
    };

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_musicplayer_view);
        loadComponent();

        Intent intent = getIntent();
        if (intent != null) {
            int idSong = intent.getIntExtra("idSong", position);
            String albumName = intent.getStringExtra("albumName");
            this.albumName.setText(albumName);
            queue = getQueue(albumName);

            if (!apiCalled) {
                for (int i = 0; i < queue.size(); i++) {
                    if (queue.get(i).getID_Song() == idSong) {
                        song = queue.get(i);
                        break;
                    }
                }
                loadData();
                getMusicPlayer();
                createService(song);
                apiCalled = true;
            }

            seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                @Override
                public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                    if (fromUser && mediaPlayer != null) {
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
        }

    }

    @Override
    protected void onResume() {
        playThreadBtn();
        super.onResume();
    }

    public List<Song> getQueue(String key) {
        SharedPreferences prefs = getSharedPreferences(key, Context.MODE_PRIVATE);
        Gson gson = new Gson();
        String json = prefs.getString(key, null);
        Type type = new TypeToken<List<Song>>() {
        }.getType();
        return gson.fromJson(json, type);
    }

    private void setBtnNextandPre() {
        nextBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                nextSong();
            }
        });
        prevBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                prevSong();
            }
        });
    }

    private void playThreadBtn() {
        playThread = new Thread() {
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
        if (mediaPlayer.isPlaying()) {
            playPauseBtn.setImageResource(R.drawable.play2_icon);
            mediaPlayer.pause();
            seekBar.setMax(mediaPlayer.getDuration() / 1000);
            MusicPlayerActivity.this.runOnUiThread(updateSeekBar);
        } else {
            playPauseBtn.setImageResource(R.drawable.pause_icon);
            mediaPlayer.start();
            seekBar.setMax(mediaPlayer.getDuration() / 1000);
            MusicPlayerActivity.this.runOnUiThread(updateSeekBar);
        }


    }

    private void createService(Song song) {
        Intent intent = new Intent(this, MusicService.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("song", song);
        intent.putExtras(bundle);
        startService(intent);
    }

    protected void loadComponent() {
        albumName = findViewById(R.id.MV_txtAlbumName);
        imgSong = findViewById(R.id.MV_imgSong);
        songName = findViewById(R.id.MV_txtNameSong);
        artistName = findViewById(R.id.MV_txtNameArtist);
        lyrics = findViewById(R.id.MV_playlist);
        playPauseBtn = findViewById(R.id.MV_playIcon);
        nextBtn = findViewById(R.id.MV_nextIcon);
        prevBtn = findViewById(R.id.MV_prevIcon);
        seekBar = findViewById(R.id.MV_seekBar);
        durationPlayed = findViewById(R.id.MV_durationPlayed);
        durationTotal = findViewById(R.id.MV_durationTotal);
        setBtnNextandPre();

    }

    private void nextSong() {
        int pos = queue.indexOf(song) + 1;
        pos %= queue.size();
        song = queue.get(pos);
        loadData();
        getMusicPlayer();
    }

    private void prevSong() {
        int pos = queue.indexOf(song) - 1;
        pos = pos == -1 ? queue.size() - 1 : pos % queue.size();
        song = queue.get(pos);
        loadData();
        getMusicPlayer();
    }

    protected void loadData() {
        songName.setText(this.song.getNameSong());
        artistName.setText(this.song.getNameArtist());
        if (this.song.getThumbnail() != null) {
            Picasso.get().load(this.song.getThumbnail()).placeholder(R.drawable.hinhnen).into(imgSong);
        }
        lyrics.setText(this.song.getLyrics());
    }

    protected void getMusicPlayer() {
        if (this.song != null) {
            playPauseBtn.setImageResource(R.drawable.pause_icon);
            uri = Uri.parse(this.song.getURLmp3());
        }

        if (mediaPlayer != null) {
            mediaPlayer.stop();
            mediaPlayer.release();
            mediaPlayer = new MediaPlayer();
            try {
                mediaPlayer.setDataSource(getApplicationContext(), uri);
                mediaPlayer.prepareAsync();
                mediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                    @Override
                    public void onPrepared(MediaPlayer mp) {
                        mediaPlayer.start();
                        seekBar.setMax(mediaPlayer.getDuration() / 1000);
                        MusicPlayerActivity.this.runOnUiThread(updateSeekBar);
                        handler.postDelayed(updateSeekBar, 0); // Start updating the SeekBar
                    }
                });
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            mediaPlayer = new MediaPlayer();
            try {
                mediaPlayer.setDataSource(getApplicationContext(), uri);
                mediaPlayer.prepareAsync();
                mediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                    @Override
                    public void onPrepared(MediaPlayer mp) {
                        mediaPlayer.start();
                        seekBar.setMax(mediaPlayer.getDuration() / 1000);
                        MusicPlayerActivity.this.runOnUiThread(updateSeekBar);
                        handler.postDelayed(updateSeekBar, 0); // Start updating the SeekBar
                    }
                });
            } catch (IOException e) {
                e.printStackTrace();
            }
            mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                @Override
                public void onCompletion(MediaPlayer mp) {
                    nextSong();
                }
            });
        }

        durationTotal.setText(this.song.getDuration());
    }

    private String formattedTime(int mCurrentPosition) {
        String totalOut = "";
        String totalNew = "";
        String seconds = String.valueOf(mCurrentPosition % 60);
        String minutes = String.valueOf(mCurrentPosition / 60);
        totalOut = minutes + ":" + seconds;
        totalNew = minutes + ":0" + seconds;

        if (seconds.length() == 1) {
            return totalNew;
        } else {
            return totalOut;
        }
    }

}

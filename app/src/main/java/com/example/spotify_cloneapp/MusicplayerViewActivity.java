package com.example.spotify_cloneapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.spotify_cloneapp.APIs.Service;
import com.example.spotify_cloneapp.Models.Song;
import com.squareup.picasso.Picasso;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MusicplayerViewActivity extends AppCompatActivity {
    private Song song;
    private ImageView thumbnailIV;
    private ImageView playIV;
    private TextView songNameTV;
    private TextView artistNameTV;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_musicplayer_view);

        loadComponent();

        Intent intent = getIntent();
        if (intent != null) {
            int songID=intent.getIntExtra("playSong",0);
            Service.api.getAllSong().enqueue(new Callback<List<Song>>() {
                @Override
                public void onResponse(Call<List<Song>> call, Response<List<Song>> response) {
                    for (Song s : response.body()) {
                        if (s.getID_Song() == songID) {
                            song = s;
                            break;
                        }
                    }
                    loadData();
                }

                @Override
                public void onFailure(Call<List<Song>> call, Throwable t) {

                }
            });
        }
    }

    private void loadComponent() {
        thumbnailIV = findViewById(R.id.MV_imgAblum);
        playIV = findViewById(R.id.MV_playIcon);
        songNameTV = findViewById(R.id.MV_txtNameSong);
        artistNameTV = findViewById(R.id.MV_txtNameArtist);
        playIV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(playIV.getDrawable().getConstantState().equals(getResources().getDrawable(R.drawable.pause2_icon).getConstantState())) {
                    playIV.setImageResource(R.drawable.play2_icon);
                }
                else if(playIV.getDrawable().getConstantState().equals(getResources().getDrawable(R.drawable.play2_icon).getConstantState())) {
                    playIV.setImageResource(R.drawable.pause2_icon);
                }
            }
        });
    }

    private void loadData() {
        songNameTV.setText(song.getNameSong());
        artistNameTV.setText(song.getNameArtist());
        if (song.getThumbnail() != null) {
            Picasso.get().load(song.getThumbnail()).placeholder(R.drawable.hinhnen).into(thumbnailIV);
        } else {
            thumbnailIV.setImageResource(R.drawable.hinhnen);
        }
    }
}
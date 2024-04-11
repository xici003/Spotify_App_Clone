package com.example.spotify_cloneapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.spotify_cloneapp.APIs.Service;
import com.example.spotify_cloneapp.Adapters.SongAdapter;
import com.example.spotify_cloneapp.Models.Album;
import com.example.spotify_cloneapp.Models.Song;
import com.google.gson.Gson;
import com.squareup.picasso.Picasso;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AlbumDetailActivity extends AppCompatActivity {
    private Album album;
    private ImageView albumImageView;
    private RecyclerView songsOfAlbumRV;
    private SongAdapter songsOfAlbumAdapter;
    private TextView albumNameTV;
    private TextView albumDescriptionTV;
    private ImageView playAllSongs;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_album_detail);
        loadComponent();
        Intent intent=getIntent();
        if(intent!=null){
            int idAlbum=intent.getIntExtra("idAlbum",0);
            Service.api.getAlbumById(idAlbum).enqueue(new Callback<List<Album>>() {
                @Override
                public void onResponse(Call<List<Album>> call, Response<List<Album>> response) {
                    if(response.body().size()>0) {
                        album = response.body().get(0);
                        loadData();
                    }
                }

                @Override
                public void onFailure(Call<List<Album>> call, Throwable t) {

                }
            });
            Service.api.getSongsByAlbum(idAlbum).enqueue(new Callback<List<Song>>() {
                @Override
                public void onResponse(Call<List<Song>> call, Response<List<Song>> response) {
                    List<Song> songs=response.body();
                    songsOfAlbumAdapter.setSongList(songs);
                    saveQueueofSongs(songs);
                    songsOfAlbumAdapter.notifyDataSetChanged();
                }

                @Override
                public void onFailure(Call<List<Song>> call, Throwable t) {

                }
            });
        }
    }
    public void setPlayAllSongs(){
        playAllSongs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(AlbumDetailActivity.this,MusicPlayerActivity.class);
                intent.putExtra("idSong",songsOfAlbumAdapter.getSongList().get(0).getID_Song());
                intent.putExtra("albumName",album.getName());
                startActivity(intent);
            }
        });
    }
    protected void loadComponent(){
        albumImageView=findViewById(R.id.imgViewAlbum);
        songsOfAlbumRV=findViewById(R.id.idRVSongs);
        albumNameTV=findViewById(R.id.txtAlbumName);
        albumDescriptionTV=findViewById(R.id.txtArtistName);
        playAllSongs=findViewById(R.id.idFaPlay); // Sửa lại ID của ImageView
        songsOfAlbumAdapter=new SongAdapter();
        songsOfAlbumRV.setAdapter(songsOfAlbumAdapter);
        setPlayAllSongs();
    }
    protected void loadData(){
        albumNameTV.setText(this.album.getName());
        albumDescriptionTV.setText(this.album.getDescription());
        if(this.album.getThumbnail()!=null){
            Picasso.get().load(this.album.getThumbnail()).into(albumImageView);
        }
        songsOfAlbumAdapter.setAlbumName(album.getName());

    }
    private void saveQueueofSongs(List<Song> list){
        SharedPreferences prefs= getSharedPreferences(album.getName(), MODE_PRIVATE);
        SharedPreferences.Editor editor=prefs.edit();
        Gson gson=new Gson();
        String json=gson.toJson(list);
        editor.putString(album.getName(),json);
        editor.apply();
    }
}
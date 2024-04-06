package com.example.spotify_cloneapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.spotify_cloneapp.APIs.Service;
import com.example.spotify_cloneapp.Adapters.SongAdapter;
import com.example.spotify_cloneapp.Models.Album;
import com.example.spotify_cloneapp.Models.Song;
import com.squareup.picasso.Picasso;

import java.util.List;

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
                    songsOfAlbumAdapter.setSongList(response.body());
                    songsOfAlbumAdapter.notifyDataSetChanged();
                }

                @Override
                public void onFailure(Call<List<Song>> call, Throwable t) {

                }
            });
        }
    }
    protected void loadComponent(){
        albumImageView=findViewById(R.id.imgViewAlbum);
        songsOfAlbumRV=findViewById(R.id.idRVSongs);
        albumNameTV=findViewById(R.id.txtAlbumName);
        albumDescriptionTV=findViewById(R.id.txtArtistName);

        songsOfAlbumAdapter=new SongAdapter();
        songsOfAlbumRV.setAdapter(songsOfAlbumAdapter);

    }
    protected void loadData(){
        albumNameTV.setText(this.album.getName());
        albumDescriptionTV.setText(this.album.getDescription());
        if(this.album.getThumbnail()!=null){
            Picasso.get().load(this.album.getThumbnail()).into(albumImageView);
        }
    }
}
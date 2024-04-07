package com.example.spotify_cloneapp.Adapters;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;
import com.example.spotify_cloneapp.Models.Song;
import com.example.spotify_cloneapp.MusicplayerViewActivity;
import com.example.spotify_cloneapp.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class SongAdapter extends RecyclerView.Adapter<SongAdapter.SongViewHolder> {

    private List<Song> songList;

    public SongAdapter() {
        this.songList = new ArrayList<>();
    }
    public void setSongList(List<Song> songList) {
        this.songList.addAll(songList);
    }

    @Override
    public SongViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.song_item, parent, false);
        return new SongViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(SongViewHolder holder, @SuppressLint("RecyclerView") int position) {
        Song song = songList.get(position);
        // Đặt hình ảnh và các sự kiện khác ở đây
        holder.name.setText(song.getNameSong());
        holder.artist.setText(song.getNameArtist());
        if (songList.get(position).getThumbnail() != null) {
            Picasso.get().load(song.getThumbnail()).placeholder(R.drawable.hinhnen).into(holder.thumbnail);
        }
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Song playSong=songList.get(position);
                Intent intent=new Intent(v.getContext(), MusicplayerViewActivity.class);
                intent.putExtra("playSong",playSong.getID_Song());
                v.getContext().startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return songList.size();
    }

    public class SongViewHolder extends RecyclerView.ViewHolder {
        public TextView name;
        public TextView artist;
        public ImageView thumbnail;

        public SongViewHolder(View view) {
            super(view);
            name=view.findViewById(R.id.txtSongName);
            artist=view.findViewById(R.id.txtSongArtist);
            thumbnail=view.findViewById(R.id.idImgSong);
        }
    }
}

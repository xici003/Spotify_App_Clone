package com.example.spotify_cloneapp.Adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.spotify_cloneapp.Models.Playlist;
import com.example.spotify_cloneapp.Models.Song;
import com.example.spotify_cloneapp.MusicPlayerActivity;
import com.example.spotify_cloneapp.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class PlaylistAdapter extends RecyclerView.Adapter<PlaylistAdapter.PlaylistViewHolder> {
    private List<Playlist> playlists;
    private String namePlaylist;
    private Context mContext;
    public PlaylistAdapter() {
        this.playlists = new ArrayList<>();
    }
    public void setPlaylists(List<Playlist> playlists) {
        this.playlists.addAll(playlists);
        notifyDataSetChanged();
    }

    public List<Playlist> getPlaylists() {
        return playlists;
    }
    @NonNull
    @Override
    public PlaylistViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.song_item, parent, false);
        return new PlaylistViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull PlaylistViewHolder holder, int position) {
        Playlist playlist = playlists.get(position);
        // Đặt hình ảnh và các sự kiện khác ở đây
        holder.namePlaylist.setText(playlist.getName());
        holder.description.setText(playlist.getDescription());
        try{
            Picasso.get().load(playlist.getThumbnail()).placeholder(R.drawable.hinhnen).into(holder.thumbnail);
        }
        catch (Exception e){
            holder.thumbnail.setImageResource(R.drawable.hinhnen);
        }

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(mContext, "" + playlist.getName(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return playlists.size();
    }

    public class PlaylistViewHolder extends RecyclerView.ViewHolder {
        public TextView namePlaylist;
        public TextView description;
        public ImageView thumbnail;

        public PlaylistViewHolder(View view) {
            super(view);
            namePlaylist = view.findViewById(R.id.txtSongName);
            description = view.findViewById(R.id.txtSongArtist);
            thumbnail = view.findViewById(R.id.idImgSong);
        }
    }
}

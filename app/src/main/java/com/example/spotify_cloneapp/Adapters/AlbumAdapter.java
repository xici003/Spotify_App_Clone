package com.example.spotify_cloneapp.Adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.spotify_cloneapp.Models.Album;
import com.example.spotify_cloneapp.R;

import java.util.ArrayList;
import java.util.List;

import com.squareup.picasso.Picasso;

public class AlbumAdapter extends RecyclerView.Adapter<AlbumAdapter.SongViewHolder> {

    private List<Album> albumList;
    public AlbumAdapter() {
        this.albumList = new ArrayList<>();
    }

    public AlbumAdapter(List<Album> albumList) {
        this.albumList = albumList;
    }

    public void setAlbumList(List<Album> albumList) {
        this.albumList.addAll(albumList);
        this.albumList.addAll(albumList);
    }
    @Override
    public SongViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.album_item, parent, false);
        return new SongViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(SongViewHolder holder, int position) {
        Album album = albumList.get(position);
        // Đặt hình ảnh và các sự kiện khác ở đây
        holder.name.setText(album.getName());
        holder.detail.setText(album.getDescription());
        if(albumList.get(position).getThumbnail()!=null){
            Picasso.get().load(album.getThumbnail()).into(holder.thumbnail);

        }
    }

    @Override
    public int getItemCount() {
        return albumList.size();
    }

    public class SongViewHolder extends RecyclerView.ViewHolder {
        public TextView name, detail;
        public ImageView thumbnail;

        public SongViewHolder(View view) {
            super(view);
            // Khởi tạo các thành phần giao diện người dùng khác ở đây
            name=view.findViewById(R.id.idAlbumName);
            detail=view.findViewById(R.id.idALbumDetails);
            thumbnail=view.findViewById(R.id.idAlbumThumbnail);
        }
    }
}
package com.example.spotify_cloneapp.Fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.spotify_cloneapp.Adapters.PlaylistAdapter;
import com.example.spotify_cloneapp.Database.PlayListDB;
import com.example.spotify_cloneapp.Database.PlaylistSongDB;
import com.example.spotify_cloneapp.Models.Playlist;
import com.example.spotify_cloneapp.R;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FavoriteFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FavoriteFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private RecyclerView idRVPlaylist;
    private PlaylistAdapter playlistAdapter;
    private PlayListDB playlisttbl;
    private PlaylistSongDB playlistSongtbl;
    private List<Playlist> playlists = new ArrayList<>();

    public FavoriteFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment FavoriteFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static FavoriteFragment newInstance(String param1, String param2) {
        FavoriteFragment fragment = new FavoriteFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_favorite, container, false);;
        this.loadComponent(view);
        return view;
    }

    private void loadComponent(View view) {
        idRVPlaylist = view.findViewById(R.id.idRVPlaylist);
        playlisttbl = new PlayListDB(this.getContext(),"playlist", null, 1);
        playlistSongtbl = new PlaylistSongDB(this.getContext(), "playlistSong", null, 1);

        // Khởi tạo playlistAdapter
        playlistAdapter = new PlaylistAdapter();

        // Lấy dữ liệu từ cơ sở dữ liệu
        playlists = playlisttbl.getAllPlaylist();

        // Đặt dữ liệu vào adapter và gán vào RecyclerView
        playlistAdapter.setPlaylists(playlists);
        idRVPlaylist.setAdapter(playlistAdapter);

        // Cập nhật giao diện
        playlistAdapter.notifyDataSetChanged();
        Toast.makeText(view.getContext(), "" + playlists.size(), Toast.LENGTH_SHORT).show();
    }
}
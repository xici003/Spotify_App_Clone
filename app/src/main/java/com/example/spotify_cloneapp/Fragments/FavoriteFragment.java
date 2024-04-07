package com.example.spotify_cloneapp.Fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.spotify_cloneapp.Adapters.SongAdapter;
import com.example.spotify_cloneapp.Models.Song;
import com.example.spotify_cloneapp.R;

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
    private RecyclerView songsRecyclerView;
    private SongAdapter songAdapter;
    private Button myPlaylistsButton;
    private Button artistButton;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

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
        View v= inflater.inflate(R.layout.fragment_favorite, container, false);
        loadComponent(v);
        return v;
    }

    protected void loadComponent(View view){
        songsRecyclerView = view.findViewById(R.id.idRVSongs);
        myPlaylistsButton= view.findViewById(R.id.F_btnPlaylist);
        artistButton = view.findViewById(R.id.F_btnArtist);
        songAdapter = new SongAdapter();
        songsRecyclerView.setAdapter(songAdapter);
    }
    public void loadData(List<Song> songs){
        songAdapter.setSongList(songs);
    }
    public void notifyDataChange(){
        songAdapter.notifyDataSetChanged();
    }
}
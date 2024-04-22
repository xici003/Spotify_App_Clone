package com.example.spotify_cloneapp.Fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.example.spotify_cloneapp.APIs.Service;
import com.example.spotify_cloneapp.Adapters.AlbumAdapter;
import com.example.spotify_cloneapp.LoginActivity;
import com.example.spotify_cloneapp.MainActivity;
import com.example.spotify_cloneapp.Models.Album;
import com.example.spotify_cloneapp.R;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link HomeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HomeFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private CircleImageView avatar;
    private Service service;
    private RecyclerView recommendedAlbumRView;
    private RecyclerView popularAlbumRView;
    private RecyclerView trendingAlbum;

    private AlbumAdapter recommendedAlbumAdapter;
    private AlbumAdapter popularAlbumAdapter;
    private AlbumAdapter trendingAlbumAdapter;
    private ImageView H_avatar;

    public HomeFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment HomeFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static HomeFragment newInstance(String param1, String param2) {
        HomeFragment fragment = new HomeFragment();
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
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        this.loadComponent(view);

        // action khi đăng nhập:
        LoginAction();
        // Inflate the layout for this fragment
        this.loadData();
        return view;
    }

    private void LoginAction() {
        H_avatar.setOnClickListener(v->{
            Intent intent = new Intent(getContext(), LoginActivity.class);
            startActivityForResult(intent, 111);
        });
    }

    public void loadComponent(View view){
        recommendedAlbumRView =view.findViewById(R.id.idRVAlbums);
        popularAlbumRView =view.findViewById(R.id.idRVPopularAlbums);
        trendingAlbum =view.findViewById(R.id.idRVTrendingAlbums);

        recommendedAlbumAdapter= new AlbumAdapter();
        popularAlbumAdapter=new AlbumAdapter();
        trendingAlbumAdapter=new AlbumAdapter();

        recommendedAlbumAdapter.setContext((MainActivity)getActivity());
        popularAlbumAdapter.setContext((MainActivity)getActivity());
        trendingAlbumAdapter.setContext((MainActivity)getActivity());

        recommendedAlbumRView.setAdapter(recommendedAlbumAdapter);
        popularAlbumRView.setAdapter(popularAlbumAdapter);
        trendingAlbum.setAdapter(trendingAlbumAdapter);

        H_avatar = view.findViewById(R.id.H_avatar);
    }

    public void loadData(){
        Service.api.getAllAlbum().enqueue(new Callback<List<Album>>() {
            @Override
            public void onResponse(Call<List<Album>> call, Response<List<Album>> response) {
                if(response.code()==500){
                    loadData();
                }
                else{
                    List<Album> albumList = new ArrayList<>();
                    albumList.addAll(response.body());
                    recommendedAlbumAdapter.setAlbumList(albumList);
                    popularAlbumAdapter.setAlbumList(albumList);
                    trendingAlbumAdapter.setAlbumList(albumList);
                    notifyDataChange();
                }
            }

            @Override
            public void onFailure(Call<List<Album>> call, Throwable t) {

            }
        });
    }
    public void notifyDataChange(){
        recommendedAlbumAdapter.notifyDataSetChanged();
        popularAlbumAdapter.notifyDataSetChanged();
        trendingAlbumAdapter.notifyDataSetChanged();
    }
}
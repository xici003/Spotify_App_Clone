package com.example.spotify_cloneapp;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.spotify_cloneapp.APIs.Service;
import com.example.spotify_cloneapp.Fragments.FavoriteFragment;
import com.example.spotify_cloneapp.Fragments.HomeFragment;
import com.example.spotify_cloneapp.Fragments.SearchFragment;
import com.example.spotify_cloneapp.Models.Album;
import com.example.spotify_cloneapp.databinding.ActivityMainBinding;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    final int MENU_HOME_ID = R.id.mnuHome;
    final int MENU_SEARCH_ID = R.id.mnuSearch;
    final int MENU_FAVOURITE_ID = R.id.mnuFavorite;
    ActivityMainBinding binding;
    private HomeFragment homeFragment;
    private SearchFragment searchFragment;
    private FavoriteFragment favoriteFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        homeFragment= new HomeFragment();
        loadDataRecommendAlbum();
        replaceFragment(homeFragment);

        searchFragment=new SearchFragment();
        favoriteFragment=new FavoriteFragment();

        // Bottom Navigation View
        binding.bottomNavigationView.setOnItemSelectedListener(item -> {
            int itemId = item.getItemId();
            if (itemId == MENU_HOME_ID) {
                loadDataRecommendAlbum();
                homeFragment.notifyDataChange();
                replaceFragment(homeFragment);
            }
            if (itemId == MENU_SEARCH_ID) {
                replaceFragment(searchFragment);
            }
            if (itemId == MENU_FAVOURITE_ID) {
                replaceFragment(favoriteFragment);
            }
            return true;
        });

    }

    private void replaceFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frameLayout, fragment);
        fragmentTransaction.commit();
    }

    private void loadDataRecommendAlbum() {

        Service.api.getAllAlbum().enqueue(new Callback<List<Album>>() {
            @Override
            public void onResponse(Call<List<Album>> call, Response<List<Album>> response) {
                if(response.isSuccessful()){
                    List<Album> data= response.body();
                    homeFragment.loadData(data);
                    homeFragment.notifyDataChange();
                }
            }

            @Override
            public void onFailure(Call<List<Album>> call, Throwable t) {

            }
        });
    }
}
package com.example.spotify_cloneapp;

import android.Manifest;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.IBinder;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.spotify_cloneapp.APIs.Service;
import com.example.spotify_cloneapp.Database.PlayListDB;
import com.example.spotify_cloneapp.Database.PlaylistSongDB;
import com.example.spotify_cloneapp.Fragments.AlbumDetailFragment;
import com.example.spotify_cloneapp.Fragments.FavoriteFragment;
import com.example.spotify_cloneapp.Fragments.HomeFragment;
import com.example.spotify_cloneapp.Fragments.MusicFragment;
import com.example.spotify_cloneapp.Fragments.SearchFragment;
import com.example.spotify_cloneapp.Models.Album;
import com.example.spotify_cloneapp.Models.Playlist;
import com.example.spotify_cloneapp.Models.Song;
import com.example.spotify_cloneapp.databinding.ActivityMainBinding;
import com.squareup.picasso.Picasso;

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
    private AlbumDetailFragment albumDetailFragment;
    private static final int PERMISSION_REQUEST_CODE = 1001;
    private RelativeLayout layoutPlayerBottom;
    private ImageView imgSong, iconLove, iconPlay;
    private TextView txtSongName, txtSongArtist;
    AudioManager audioManager;
    PlayListDB playlisttbl;
    PlaylistSongDB playlistSongtbl;
    private Song currentSong;
    private String albumName;
    private MusicService musicService;
    private List<Song> queue;
    private MediaPlayer mediaPlayer;
    private boolean isServiceBound = false;
    private ServiceConnection serviceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            MusicService.LocalBinder binder = (MusicService.LocalBinder) service;
            musicService = binder.getService();
            isServiceBound = true;
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            isServiceBound = false;
        }
    };

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (isServiceBound) {
            unbindService(serviceConnection);
            isServiceBound = false;
        }
        Intent stopServiceIntent = new Intent(this, MusicService.class);
        stopService(stopServiceIntent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        musicService = new MusicService();
        Intent startServiceIntent = new Intent(this, MusicService.class);
        startService(startServiceIntent);

        // Liên kết dịch vụ
        Intent bindServiceIntent = new Intent(this, MusicService.class);
        bindService(bindServiceIntent, serviceConnection, Context.BIND_AUTO_CREATE);

        homeFragment= new HomeFragment();
        loadDataRecommendAlbum();
        replaceFragment(homeFragment,false,"home_fragment");

        searchFragment=new SearchFragment();
        favoriteFragment=new FavoriteFragment();

        // Bottom Navigation View
        binding.bottomNavigationView.setOnItemSelectedListener(item -> {
            int itemId = item.getItemId();
            if (itemId == MENU_HOME_ID) {
                loadDataRecommendAlbum();
                homeFragment.notifyDataChange();
                replaceFragment(homeFragment,false,"home_fragment");
            }
            if (itemId == MENU_SEARCH_ID) {
                replaceFragment(searchFragment,false,"search_fragment");
            }
            if (itemId == MENU_FAVOURITE_ID) {
                replaceFragment(favoriteFragment,false,"favorite_fragment");
            }
            return true;
        });

        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.FOREGROUND_SERVICE,
                        Manifest.permission.POST_NOTIFICATIONS}, PERMISSION_REQUEST_CODE);

        audioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
        setupPlayerBottom();
        updatePlayerVisibility(false);
//        InitDB();
    }

    private void InitDB() {
        playlisttbl = new PlayListDB(this,"playlist", null, 1);
        playlistSongtbl = new PlaylistSongDB(this, "playlistSong", null, 1);

        //add playlist
//        playlisttbl.addPlaylist(new Playlist(1, "My playlist1", "Nhạc nhẹ", ""));
//        playlisttbl.addPlaylist(new Playlist(2, "My playlist2", "Nhạc nấu cơm", ""));
//        playlisttbl.addPlaylist(new Playlist(3, "My playlist3", "Nhạc nghe vui", ""));

//        playlistSongtbl.AddSongIntoPlaylist(8, 1);
//        playlistSongtbl.AddSongIntoPlaylist(9, 1);
//        playlistSongtbl.AddSongIntoPlaylist(11, 1);
//        playlistSongtbl.AddSongIntoPlaylist(11, 2);
//        playlistSongtbl.AddSongIntoPlaylist(15, 2);

        ArrayList<Playlist> playlists = playlisttbl.getAllPlaylist();
        for (Playlist p: playlists){
            System.out.println(p.getName());
        }
        ArrayList<Integer> index = playlistSongtbl.getAllSongInPlaylist(1);
        for (int i : index){
            System.out.println(i);
        }
    }

    private void setupPlayerBottom() {
        layoutPlayerBottom = findViewById(R.id.layout_player_bottom);
        imgSong = findViewById(R.id.imgSong);
        iconLove = findViewById(R.id.iconLove);
        iconPlay = findViewById(R.id.iconPlay);
        txtSongName = findViewById(R.id.txtSongName);
        txtSongArtist = findViewById(R.id.txtSongArtist);


        iconPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (audioManager.isMusicActive()) {
                    musicService.pauseMusic();
                    iconPlay.setImageResource(R.drawable.play_icon);
                } else {
                    musicService.continueMusic();
                    iconPlay.setImageResource(R.drawable.pause2_icon);
                }
            }
        });

        layoutPlayerBottom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                replaceFragment(new MusicFragment(currentSong, musicService,albumName),true,"MUSIC_FRAGMENT");
                updatePlayerVisibility(false);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    private void updatePlayerVisibility(boolean isVisible){
        if(isVisible){
            layoutPlayerBottom.setVisibility(View.VISIBLE);
            iconPlay.setImageResource(R.drawable.pause2_icon);
            Picasso.get().load(Uri.parse(currentSong.getThumbnail())).placeholder(R.drawable.hinhnen).into(imgSong);
            txtSongName.setText(currentSong.getNameSong());
            txtSongArtist.setText(currentSong.getNameArtist());
        }else{
            layoutPlayerBottom.setVisibility(View.INVISIBLE);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == PERMISSION_REQUEST_CODE) {
            // Xử lý phản hồi từ yêu cầu quyền ở đây
        } else {
            super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }

    private void replaceFragment(Fragment fragment,boolean isStack,String tag) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frameLayout, fragment,tag);
        if(isStack){
            fragmentTransaction.addToBackStack(null);
        }
        fragmentTransaction.commit();
    }

    private void loadDataRecommendAlbum() {
        homeFragment.loadData();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode==118 && requestCode==103){
            Bundle bundle= data.getExtras();
            if(bundle!=null) {
                currentSong= (Song) bundle.getSerializable("curSong");
                if(currentSong!=null){
                    txtSongName.setText(currentSong.getNameSong());
                    txtSongArtist.setText(currentSong.getNameArtist());
                    Picasso.get().load(currentSong.getThumbnail()).into(imgSong);

                }
                albumName= data.getStringExtra("albumName");
            }
        }
    }
    public void callAlbumDetailFragment(int idAlbum){
        albumDetailFragment=new AlbumDetailFragment();
        Bundle bundle=new Bundle();
        bundle.putInt("idAlbum",idAlbum);
        albumDetailFragment.setArguments(bundle);
        replaceFragment(albumDetailFragment,true,"album_detail_fragment");
    }

    public void startMusic(Song song) {
        if (isServiceBound && musicService != null) {
            mediaPlayer=musicService.startMusic(song, queue);
            albumDetailFragment=(AlbumDetailFragment) getSupportFragmentManager().findFragmentByTag("album_detail_fragment");
            if(albumDetailFragment!=null){
                albumName=albumDetailFragment.getAlbumName();
            }else {
                albumName="";
            }
            MusicFragment musicFragment = new MusicFragment(song, musicService,albumName);

            replaceFragment(musicFragment,true,"MUSIC_FRAGMENT");
            updatePlayerVisibility(false);
        } else {
            Toast.makeText(musicService, "No service", Toast.LENGTH_SHORT).show();
        }
    }
    public void setListSong(List<Song> songList) {
        queue = songList;
    }
    @Override
    public void onBackPressed() {
        // Kiểm tra xem MusicFragment có đang được hiển thị hay không
        MusicFragment musicFragment = (MusicFragment) getSupportFragmentManager().findFragmentByTag("MUSIC_FRAGMENT");
        if (musicFragment != null && musicFragment.isVisible()) {
            // Nếu MusicFragment đang được hiển thị, hiển thị BottomLayout
            currentSong=musicService.getSong();
            updatePlayerVisibility(true);
            // Nếu không, hành động mặc định khi nhấn nút back sẽ được thực hiện
        }
        super.onBackPressed();

    }
}
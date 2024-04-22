package com.example.spotify_cloneapp.Fragments;

import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.text.method.ScrollingMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.example.spotify_cloneapp.MainActivity;
import com.example.spotify_cloneapp.Models.Song;
import com.example.spotify_cloneapp.MusicService;
import com.example.spotify_cloneapp.R;
import com.squareup.picasso.Picasso;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link MusicFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MusicFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private String albumNameStr;
    private static MediaPlayer mediaPlayer;
    private TextView albumName, songName, artistName, lyrics, durationPlayed, durationTotal;
    private ImageView imgSong, playPauseBtn, nextBtn, prevBtn;
    private SeekBar seekBar;
    private ImageView imgBack;
    private Song song;
    private Handler handler = new Handler();
    private MusicService musicService;

    public MusicFragment(Song song, MusicService musicService,String albumNameStr) {
        this.song = song;
        this.musicService = musicService;
        this.albumNameStr=albumNameStr;
        mediaPlayer=musicService.getMediaPlayer();
    }

    // TODO: Rename and change types and number of parameters
    public static MusicFragment newInstance(String param1, String param2, Song song, MusicService musicService, String al) {
        MusicFragment fragment = new MusicFragment(song, musicService,al);
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
        View view =  inflater.inflate(R.layout.fragment_musicplayer_view, container, false);
        this.loadComponent(view);
        this.loadData(song);
        setMediaPlayerAction();
        return view;
    }

    private void loadData(Song song) {
        albumName.setText(albumNameStr);
        songName.setText(song.getNameSong());
        artistName.setText(song.getNameArtist());
        lyrics.setText(song.getLyrics());
        Picasso.get().load(Uri.parse(song.getThumbnail())).placeholder(R.drawable.hinhnen).into(imgSong);
        seekBarAction();
        handler.post(updateSeekBar);
    }

    private Runnable updateSeekBar = new Runnable() {
        @Override
        public void run() {
            if (mediaPlayer != null && mediaPlayer.isPlaying()) {
                int mCurrentPosition = mediaPlayer.getCurrentPosition() / 1000;
                seekBar.setProgress(mCurrentPosition);
                durationPlayed.setText(formattedTime(mCurrentPosition));
                handler.postDelayed(this, 1000);
            }
            if (seekBar.getProgress() == seekBar.getMax()-1) {
                song = musicService.nextMusic();
                loadData(song);
                setMediaPlayerAction();
            }
        }
    };

    private void seekBarAction() {
        if (mediaPlayer != null) {
            seekBar.setMax(mediaPlayer.getDuration() / 1000);  // Chỉ đặt max nếu MediaPlayer không null
            seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                @Override
                public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                    if (fromUser && mediaPlayer != null) {
                        mediaPlayer.seekTo(progress * 1000);
                    }
                }

                @Override
                public void onStartTrackingTouch(SeekBar seekBar) {
                    handler.removeCallbacks(updateSeekBar);
                }

                @Override
                public void onStopTrackingTouch(SeekBar seekBar) {
                    handler.post(updateSeekBar); // Tiếp tục cập nhật SeekBar khi người dùng dừng việc kéo

                }
            });
            handler.postDelayed(updateSeekBar, 0);  // Bắt đầu cập nhật SeekBar ngay lập tức
        }
    }

    private String formattedTime(int mCurrentPosition) {
        String totalOut = "";
        String totalNew = "";
        String seconds = String.valueOf(mCurrentPosition % 60);
        String minutes = String.valueOf(mCurrentPosition / 60);
        totalOut = minutes + ":" + seconds;
        totalNew = minutes + ":0" + seconds;

        if (seconds.length() == 1) {
            return totalNew;
        } else {
            return totalOut;
        }
    }

    protected void loadComponent(View view) {
        albumName = view.findViewById(R.id.MV_txtAlbumName);
        imgSong = view.findViewById(R.id.MV_imgSong);
        songName = view.findViewById(R.id.MV_txtNameSong);
        artistName = view.findViewById(R.id.MV_txtNameArtist);
        lyrics = view.findViewById(R.id.MV_playlist);
        playPauseBtn = view.findViewById(R.id.MV_playIcon);
        nextBtn = view.findViewById(R.id.MV_nextIcon);
        prevBtn = view.findViewById(R.id.MV_prevIcon);
        seekBar = view.findViewById(R.id.MV_seekBar);
        durationPlayed = view.findViewById(R.id.MV_durationPlayed);
        durationTotal = view.findViewById(R.id.MV_durationTotal);
        lyrics.setMovementMethod(new ScrollingMovementMethod());
        imgBack = view.findViewById(R.id.MV_iconBack);
        setBtnNextandPre();
        setPlayPauseBtnAction();
    }

    private void setPlayPauseBtnAction() {
        this.playPauseBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mediaPlayer.isPlaying()) {
                    musicService.pauseMusic();
                    playPauseBtn.setImageResource(R.drawable.play2_icon);
                    handler.removeCallbacks(updateSeekBar);
                } else {
                    musicService.continueMusic();
                    playPauseBtn.setImageResource(R.drawable.pause_icon);
                    handler.post(updateSeekBar);
                }
            }
        });
    }
    private void setMediaPlayerAction(){
        durationPlayed.setText("0:00");
        mediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {
                seekBar.setMax(mp.getDuration()/1000);
                mp.start();
                durationTotal.setText(formattedTime(mp.getDuration()/1000));
                handler.post(updateSeekBar);
            }
        });

    }
    private void setBtnNextandPre() {
        nextBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                song=musicService.nextMusic();
                loadData(song);
                setMediaPlayerAction();
            }
        });
        prevBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                song=musicService.prevMusic();
                loadData(song);
                setMediaPlayerAction();
            }
        });
    }
}
package com.example.spotify_cloneapp.Fragments;

import android.media.MediaPlayer;
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
import com.example.spotify_cloneapp.R;

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
    private MainActivity mContext;
    private static MediaPlayer mediaPlayer;

    private TextView albumName, songName, artistName, lyrics, durationPlayed, durationTotal;
    private ImageView imgSong, playPauseBtn, nextBtn, prevBtn;
    private SeekBar seekBar;
    private ImageView imgBack;
    private Song song;
    private Handler handler = new Handler();

    public MusicFragment(Song song, MediaPlayer mediaPlayer) {
        this.song = song;
        this.mediaPlayer = mediaPlayer;
    }

    // TODO: Rename and change types and number of parameters
    public static MusicFragment newInstance(String param1, String param2, Song song) {
        MusicFragment fragment = new MusicFragment(song, mediaPlayer);
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
        this.loadAction();
        return view;
    }

    private void loadData(Song song) {
        songName.setText(song.getNameSong());
        artistName.setText(song.getNameArtist());
        lyrics.setText(song.getLyrics());
        durationTotal.setText(song.getDuration());
    }

    private void loadAction() {
        seekBarAction();
    }

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
                    handler.post(updateSeekBar);
                }
            });

            handler.postDelayed(updateSeekBar, 1000);  // Bắt đầu cập nhật SeekBar
            getActivity().runOnUiThread(updateSeekBar);
//            durationTotal.setText(song.getDuration());
        }
    }
    private Runnable updateSeekBar = new Runnable() {
        @Override
        public void run() {
            if (mediaPlayer != null) {
                int mCurrentPosition = mediaPlayer.getCurrentPosition() / 1000;
                seekBar.setProgress(mCurrentPosition);
                durationPlayed.setText(formattedTime(mCurrentPosition));
                System.out.println(durationPlayed.getText().toString());
            }
            handler.postDelayed(this, 1000);
        }
    };

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

//        setImgBack();
    }

    private void setBtnNextandPre() {
        nextBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                nextSong();
            }
        });
        prevBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                prevSong();
            }
        });
    }
}
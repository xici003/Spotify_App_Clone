package com.example.spotify_cloneapp;

import static com.example.spotify_cloneapp.MyApplication.CHANNEL_ID;

import android.annotation.SuppressLint;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;
import android.widget.RemoteViews;
import android.widget.Toast;

import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.example.spotify_cloneapp.Models.Song;

import java.io.IOException;

public class MusicService extends Service {
    private MediaPlayer mediaPlayer;

    @Override
    public IBinder onBind(Intent intent) {
        return binder;
    }

    private final IBinder binder = new LocalBinder();

    public class LocalBinder extends Binder {
        MusicService getService() {
            // Trả về instance của MusicService để clients có thể gọi các phương thức công cộng
            return MusicService.this;
        }
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if (intent != null) {
            if ("com.example.spotify_cloneapp.ACTION_PAUSE_MUSIC".equals(intent.getAction())) {
                pauseMusic();
            } else if ("com.example.spotify_cloneapp.ACTION_CONTINUE_MUSIC".equals(intent.getAction())) {
                continueMusic();
            } else {
                // Xử lý các Intent khác
                Bundle bundle = intent.getExtras();
                if (bundle != null) {
                    Song song = (Song) bundle.getSerializable("song");
                    if (song != null) {
                        sendNotification(song);
                    }
                }
            }
        }
        return START_NOT_STICKY;
    }

    private BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            changeBtnPause(intent);
        }
    };

    private void changeBtnPause(Intent intent) {
        if ("com.example.spotify_cloneapp.ACTION_PAUSE_MUSIC".equals(intent.getAction())) {
            if (mediaPlayer != null && mediaPlayer.isPlaying()) {
                pauseMusic();
            } else {
                continueMusic();
            }
        }
    }
    protected MediaPlayer getMusicPlayer(){
        return mediaPlayer;
    }
    @Override
    public void onCreate() {
        super.onCreate();
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("com.example.spotify_cloneapp.ACTION_PAUSE_MUSIC");
        registerReceiver(broadcastReceiver, intentFilter);
    }
    protected MediaPlayer getMusicPlayer(Song song) {
        Uri uri = null;
        if (song != null) {
            uri = Uri.parse(song.getURLmp3());
        }

        if (mediaPlayer != null) {
            mediaPlayer.stop();
            mediaPlayer.release();
        }
        mediaPlayer = new MediaPlayer();
        try {
            mediaPlayer.setDataSource(getApplicationContext(), uri);
            mediaPlayer.prepareAsync();
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            return mediaPlayer;
        }
    }
    private void startMusic(Song song) {
        if(mediaPlayer == null){
            mediaPlayer = MediaPlayer.create(getApplicationContext(),
                    Uri.parse(song.getURLmp3()));
        }
        mediaPlayer.start();
    }

    private void sendNotification(Song song) {
        Toast.makeText(this, "Có Noti", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(this, MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent,
                PendingIntent.FLAG_IMMUTABLE);
        RemoteViews remoteViews = new RemoteViews(getPackageName(), R.layout.layout_custom_notification);
        remoteViews.setTextViewText(R.id.tv_name_song_notification, song.getNameSong());
        remoteViews.setTextViewText(R.id.tv_name_artist_notification, song.getNameArtist());
        remoteViews.setImageViewUri(R.id.img_notification, Uri.parse(song.getThumbnail()));
        remoteViews.setImageViewResource(R.id.img_play_or_pause_notification, R.drawable.pause_icon);


        Intent pauseIntent = new Intent("com.example.spotify_cloneapp.ACTION_PAUSE_MUSIC");
        PendingIntent pausePendingIntent = PendingIntent.getBroadcast(this, 0, pauseIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        remoteViews.setOnClickPendingIntent(R.id.img_play_or_pause_notification, pausePendingIntent);


        Notification noti = new NotificationCompat.Builder(this, CHANNEL_ID)
                .setSmallIcon(R.drawable.logo1)
                .setContentText("Spotify")
                .setContentTitle("Notification")
                .setContentIntent(pendingIntent)
                .setCustomContentView(remoteViews)
                .setSound(null)
                .build();
        startForeground(1, noti);
    }
    public void pauseMusic() {
        if(mediaPlayer != null && mediaPlayer.isPlaying()){
            mediaPlayer.pause();
        }
    }
    public void continueMusic() {
        if(mediaPlayer != null && !mediaPlayer.isPlaying()){
            mediaPlayer.start(); // Tiếp tục phát nhạc từ điểm đã tạm ngừng
        }
    }
    public void stopMusic() {
        if(mediaPlayer != null && mediaPlayer.isPlaying()){
            mediaPlayer.stop();
            mediaPlayer.release();
            mediaPlayer = null;
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mediaPlayer != null) {
            mediaPlayer.release();
            mediaPlayer = null;
        }
        unregisterReceiver(broadcastReceiver);
    }
}
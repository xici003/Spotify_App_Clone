package com.example.spotify_cloneapp;

import static com.example.spotify_cloneapp.MyApplication.CHANNEL_ID;

import android.annotation.SuppressLint;
import android.app.Notification;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.IBinder;
import android.widget.RemoteViews;
import android.widget.Toast;

import androidx.core.app.NotificationCompat;

import com.example.spotify_cloneapp.Models.Song;

public class MusicService extends Service {

    private MediaPlayer mediaPlayer;

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Bundle bundle = intent.getExtras();
        if(bundle != null){
            Song song = (Song) bundle.getSerializable("song");
            if(song != null){
//                startMusic(song);
                sendNotification(song);
            }
        }

        return START_NOT_STICKY;
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

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mediaPlayer != null) {
            mediaPlayer.release();
            mediaPlayer = null;
        }
    }
}
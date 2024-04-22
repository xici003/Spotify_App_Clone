package com.example.spotify_cloneapp;

import static com.example.spotify_cloneapp.MyApplication.CHANNEL_ID;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;
import android.widget.ImageView;
import android.widget.RemoteViews;

import androidx.core.app.NotificationCompat;

import com.example.spotify_cloneapp.Fragments.MusicFragment;
import com.example.spotify_cloneapp.Models.Song;

import java.io.IOException;
import java.util.List;

public class MusicService extends Service {
    private MediaPlayer mediaPlayer;

    public MediaPlayer getMediaPlayer() {
        return mediaPlayer;
    }

    private ImageView btnPauseOrPlay;
    private RemoteViews remoteViews;
    private Notification noti;
    private Context mContext;
    private Song currentSong;

    public MusicService() {
        mediaPlayer=null;
    }

    private List<Song> queueSong;

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

    private final BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if ("com.example.spotify_cloneapp.ACTION_PAUSE_MUSIC".equals(intent.getAction())) {
                if (mediaPlayer != null && mediaPlayer.isPlaying()) {
                    pauseMusic();
                } else {
                    continueMusic();
                }
            } else if ("com.example.spotify_cloneapp.ACTION_PREVIOUS".equals(intent.getAction())) {
                // Xử lý hành động pre ở đây
                playPreviousSong();
            } else if ("com.example.spotify_cloneapp.ACTION_NEXT".equals(intent.getAction())) {
                // Xử lý hành động next ở đây
                playNextSong();
            }
            updateNotificationIcon();
        }
    };

    private void updateNotificationIcon() {
        int playPauseIcon = mediaPlayer.isPlaying() ? R.drawable.pause_icon : R.drawable.play2_icon;
        // Cập nhật icon trong RemoteViews
        remoteViews.setImageViewResource(R.id.img_play_or_pause_notification, playPauseIcon);
        // Cập nhật thông báo
        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(1, noti);
    }

    protected MediaPlayer getMusicPlayer(){
        return mediaPlayer;
    }
    @Override
    public void onCreate() {
        super.onCreate();
        mContext = getApplicationContext();
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("com.example.spotify_cloneapp.ACTION_PAUSE_MUSIC");
        intentFilter.addAction("com.example.spotify_cloneapp.ACTION_PREVIOUS");
        intentFilter.addAction("com.example.spotify_cloneapp.ACTION_NEXT");
        registerReceiver(broadcastReceiver, intentFilter);
    }

    private void sendNotification(Song song) {
        Intent intent = new Intent(this, MusicFragment.class);
        intent.putExtra("song", song); // Đưa dữ liệu về bài hát hiện tại vào Intent

        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0,
                intent, PendingIntent.FLAG_UPDATE_CURRENT);

        remoteViews = new RemoteViews(getPackageName(), R.layout.layout_custom_notification);

        int playPauseIcon = R.drawable.pause_icon;
        remoteViews.setImageViewResource(R.id.img_play_or_pause_notification, playPauseIcon);
        remoteViews.setTextViewText(R.id.tv_name_song_notification, song.getNameSong());
        remoteViews.setTextViewText(R.id.tv_name_artist_notification, song.getNameArtist());
        remoteViews.setImageViewUri(R.id.img_notification, Uri.parse(song.getThumbnail()));
        remoteViews.setImageViewResource(R.id.img_pre_notification, R.drawable.prev_icon2);
        remoteViews.setImageViewResource(R.id.img_next_notification, R.drawable.next_icon2);

        Intent pauseIntent = new Intent("com.example.spotify_cloneapp.ACTION_PAUSE_MUSIC");
        PendingIntent pausePendingIntent = PendingIntent.getBroadcast(this, 0,
                pauseIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        remoteViews.setOnClickPendingIntent(R.id.img_play_or_pause_notification, pausePendingIntent);

        Intent previousIntent = new Intent("com.example.spotify_cloneapp.ACTION_PREVIOUS");
        PendingIntent previousPendingIntent = PendingIntent.getBroadcast(this, 0,
                previousIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        remoteViews.setOnClickPendingIntent(R.id.img_pre_notification, previousPendingIntent);

        Intent nextIntent = new Intent("com.example.spotify_cloneapp.ACTION_NEXT");
        PendingIntent nextPendingIntent = PendingIntent.getBroadcast(this, 0,
                nextIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        remoteViews.setOnClickPendingIntent(R.id.img_next_notification, nextPendingIntent);

        noti = new NotificationCompat.Builder(this, CHANNEL_ID)
                .setSmallIcon(R.drawable.spotify)
                .setContentText("Spotify")
                .setContentTitle("Notification")
                .setContentIntent(pendingIntent) // Thiết lập PendingIntent là mục tiêu khi ấn vào thông báo
                .setCustomContentView(remoteViews)
                .setSound(null)
                .build();

        startForeground(1, noti);
    }
    public MediaPlayer startMusic(Song song, List<Song> queue) {
        Uri uri = Uri.parse(song.getURLmp3());
        currentSong = song;
        queueSong = queue;
        initMusic(uri);
        return mediaPlayer;

    }
    public void initMusic(Uri uri){

        if(mediaPlayer == null){
            mediaPlayer = new MediaPlayer();
        } else {
            mediaPlayer.reset();
        }

        try {
            mediaPlayer.setDataSource(getApplicationContext(), uri);
            mediaPlayer.prepareAsync();
            mediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                @Override
                public void onPrepared(MediaPlayer mp) {
                    mp.start();

                }
            });

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void pauseMusic() {
        if(mediaPlayer != null && mediaPlayer.isPlaying()){
            mediaPlayer.pause();
        }
    }
    public void continueMusic() {
        if(mediaPlayer != null && !mediaPlayer.isPlaying()){
            mediaPlayer.start();
        }
    }
    public Song nextMusic(){
        if(mediaPlayer != null){
            int pos=this.queueSong.indexOf(currentSong);
            currentSong=this.queueSong.get((pos+1)%this.queueSong.size());
            Uri uri = Uri.parse(currentSong.getURLmp3());
            initMusic(uri);
        }
        return currentSong;
    }
    public Song prevMusic(){
        if(mediaPlayer != null && mediaPlayer.isPlaying()){
            int pos=this.queueSong.indexOf(currentSong);
            currentSong=this.queueSong.get((pos-1+this.queueSong.size())%this.queueSong.size());
            Uri uri = Uri.parse(currentSong.getURLmp3());
            initMusic(uri);
        }
        return currentSong;
    }
    public void stopMusic() {
        if(mediaPlayer != null && mediaPlayer.isPlaying()){
            mediaPlayer.stop();
            mediaPlayer.release();
            mediaPlayer = null;
        }
    }

    public void playPreviousSong() {
        Intent intent = new Intent(this, MusicFragment.class);
        intent.setAction("ACTION_PREVIOUS");
        startActivity(intent);
    }
    public void playNextSong() {
        Intent intent = new Intent(this, MusicFragment.class);
        intent.setAction("ACTION_NEXT");
        startActivity(intent);
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
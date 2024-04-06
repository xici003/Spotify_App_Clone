package com.example.spotify_cloneapp.Models;

public class Song {
    private int ID_Song;
    private String Thumbnail;
    private String URLmp3;
    private String nameSong;
    private String Duration;
    private String Category;
    private int TotalPlayTime;
    private int Like;
    private int Disliked;
    private int ViewCount;
    private String Description;
    private String Lyrics;
    private String nameAuthor;
    private String nameArtist;

    public int getID_Song() {
        return ID_Song;
    }

    public String getThumbnail() {
        return Thumbnail;
    }

    public String getURLmp3() {
        return URLmp3;
    }

    public String getNameSong() {
        return nameSong;
    }

    public String getDuration() {
        return Duration;
    }

    public String getCategory() {
        return Category;
    }

    public int getTotalPlayTime() {
        return TotalPlayTime;
    }

    public int getLike() {
        return Like;
    }

    public int getDisliked() {
        return Disliked;
    }

    public int getViewCount() {
        return ViewCount;
    }

    public String getDescription() {
        return Description;
    }

    public String getLyrics() {
        return Lyrics;
    }

    public String getNameAuthor() {
        return nameAuthor;
    }

    public String getNameArtist() {
        return nameArtist;
    }
}

package com.example.spotify_cloneapp.Models;

public class Account {
    int ID_Acc;
    String Email, Name, Thumbnail, Password;

    public Account(int ID_Acc, String email, String name, String thumbnail, String password) {
        this.ID_Acc = ID_Acc;
        Email = email;
        Name = name;
        Thumbnail = thumbnail;
        Password = password;
    }

    public int getID_Acc() {
        return ID_Acc;
    }

    public void setID_Acc(int ID_Acc) {
        this.ID_Acc = ID_Acc;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getThumbnail() {
        return Thumbnail;
    }

    public void setThumbnail(String thumbnail) {
        Thumbnail = thumbnail;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String password) {
        Password = password;
    }
}

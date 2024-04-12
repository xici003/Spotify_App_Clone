package com.example.spotify_cloneapp.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.example.spotify_cloneapp.Models.Playlist;

import java.util.ArrayList;

public class PlayListDB extends SQLiteOpenHelper {
    public static final String TableName = "PlayList";
    public static final String Id = "id";
    public static final String Name = "namePlaylist";
    public static final String Description = "description";
    public static final String Thumbnail = "thumbnail";
    public PlayListDB(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sqlCreate = "Create table if not exists " + TableName + "("
                + Id + " Integer primary key autoincrement, "
                + Name + " Text, "
                + Description + " Text, "
                + Thumbnail + " Text NULL)";

        db.execSQL(sqlCreate);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("Drop table if exists " + TableName);
        onCreate(db);
    }

    public ArrayList<Playlist> getAllPlaylist(){
        ArrayList<Playlist> list = new ArrayList<>();
        String sql = "Select * from " + TableName;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(sql, null);

        if(cursor != null){
            while (cursor.moveToNext()){
                Playlist p = new Playlist(cursor.getInt(0), cursor.getString(1),
                        cursor.getString(2), cursor.getString(3));

                list.add(p);
            }
        }
        return list;
    }

    public void addPlaylist(Playlist playlist){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(Id, playlist.getId());
        values.put(Name, playlist.getName());
        values.put(Description, playlist.getDescription());
        values.put(Thumbnail, playlist.getThumbnail());

        try {
            db.insert(TableName, null, values);
        }
        catch (Exception e){
            System.out.println(e);
        }
        db.close();
    }

    public void UpdatePlaylist(Playlist playlist, int id){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(Id, playlist.getId());
        values.put(Name, playlist.getName());
        values.put(Description, playlist.getDescription());
        values.put(Thumbnail, playlist.getThumbnail());

        db.update(TableName, values, Id + "=?",
                new String[]{String.valueOf(id)});
        db.close();
    }

    public void DeletePlaylist(int id){
        SQLiteDatabase db = getWritableDatabase();
        String sql = "Delete from " + TableName + " Where Id = " + id;
        db.execSQL(sql);
        db.close();
    }

    public void RunSQL(String sql){
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL(sql);
    }
}

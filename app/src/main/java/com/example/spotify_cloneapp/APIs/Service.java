package com.example.spotify_cloneapp.APIs;

import com.example.spotify_cloneapp.Models.Account;
import com.example.spotify_cloneapp.Models.Album;
import com.example.spotify_cloneapp.Models.ApiResponse;
import com.example.spotify_cloneapp.Models.LoginRequest;
import com.example.spotify_cloneapp.Models.LoginResponse;
import com.example.spotify_cloneapp.Models.Song;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.List;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface Service {
//    String BASE_SONG_URL = "https://spotifybygoats.000webhostapp.com/Server/";
//    String BASE_SONG_URL = "http://192.168.1.9:5000/";
    String BASE_SONG_URL = "http://192.168.1.9:5000/";
    Gson gson = new GsonBuilder()
            .setDateFormat("dd-MM-yyyy")
            .create();

    Service api= new Retrofit.Builder()
            .baseUrl(BASE_SONG_URL)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
            .create(Service.class);

    @GET("song/getall")
    Call<List<Song>> getAllSong();
    @GET("album/getall")
    Call<List<Album>> getAllAlbum();
    @GET("song/getSongsbyAlbum/{id}")
    Call<List<Song>> getSongsByAlbum(@Path("id") int idAlbum);
    @GET("album/getAlbumById/{id}")
    Call<List<Album>> getAlbumById(@Path("id") int albumId);
    @GET("song/getSongById/{id}")
    Call<List<Song>> getSongsById(@Path("id") int idSong);
    @GET("song/getSongByName/{Name}")
    Call<List<Song>> getSongByName(@Path("Name") String name);
    @GET("album/getAlbumByName/{Name}")
    Call<List<Album>> getAlbumByName(@Path("Name") String name);
    @POST("account/checkLogin/")
    Call<LoginResponse> checkLogin(@Body LoginRequest loginRequest);
    @POST("/account/add")
    Call<ApiResponse> addAccount(@Body Account account);
    @PUT("/account/update/{id}")
    Call<ApiResponse> updateAccount(
            @Path("id") int id,
            @Body Account updatedAccount
    );
    @DELETE("account/delete/{id}")
    Call<ApiResponse> deleteAccount(@Path("id") int id);
}

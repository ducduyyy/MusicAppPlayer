package com.example.musicappplayer.service;

import com.example.musicappplayer.model.Album;
import com.example.musicappplayer.model.Banner;
import com.example.musicappplayer.model.ChudeVaTheLoai;
import com.example.musicappplayer.model.Playlist;
import com.example.musicappplayer.model.SongHot;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface Dataservice {
    @GET("songbanner.php")
    Call<List<Banner>> getDataBanner();
    @GET("playlistforcurrentday.php")
    Call<List<Playlist>> getPlaylistCurrenDay();
    @GET("chudevatheloaitrongngay.php")
    Call<ChudeVaTheLoai> GetCategoryMusic();

    @GET("albumhotforcurrentday.php")
    Call<List<Album>> getAlbumhot();

    @GET("baihatyeuthich.php")
    Call<List<SongHot>> getSongs();

    @FormUrlEncoded
    @POST("danhsachbaihat.php")
    Call<List<SongHot>> Getlistsongbybanner(@Field("idquangcao") String idquangcao);

    @FormUrlEncoded
    @POST("danhsachbaihat.php")
    Call<List<SongHot>> Getlistsongbyplaylist(@Field("idplaylist") String idplaylist);

    @GET("danhsachplaylist.php")
    Call<List<Playlist>> Getlistplaylist();

    @FormUrlEncoded
    @POST("danhsachbaihat.php")
    Call<List<SongHot>> GetlistsongbyTheloai(@Field("idtheloai") String idtheloai);
}

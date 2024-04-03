package com.example.musicappplayer.service;

import com.example.musicappplayer.model.Album;
import com.example.musicappplayer.model.Banner;
import com.example.musicappplayer.model.ChuDe;
import com.example.musicappplayer.model.ChudeVaTheLoai;
import com.example.musicappplayer.model.Playlist;
import com.example.musicappplayer.model.SongHot;
import com.example.musicappplayer.model.TheLoai;

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
    @GET("tatcachude.php")
    Call<List<ChuDe>> GetAllChuDe();
    @FormUrlEncoded
    @POST("theloaitheochude.php")
    Call<List<TheLoai>> GetTheloaitheochude(@Field("idchude") String idchude);
}
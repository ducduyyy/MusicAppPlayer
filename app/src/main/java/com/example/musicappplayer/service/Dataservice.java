package com.example.musicappplayer.service;

import com.example.musicappplayer.model.Album;
import com.example.musicappplayer.model.Banner;
import com.example.musicappplayer.model.ChudeVaTheLoai;
import com.example.musicappplayer.model.Playlist;
import com.example.musicappplayer.model.SongHot;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

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
    @GET("tatcachude.php")
    Call<List<ChuDe>> GetAllChuDe();
    @FormUrlEncoded
    @POST("theloaitheochude.php")
    Call<List<TheLoai>> GetTheloaitheochude(@Field("idchude") String idchude);

    @GET("tatcaalbum.php")
    Call<List<Album>> GetAllAlbum();

    @FormUrlEncoded
    @POST("danhsachbaihat.php")
    Call<List<SongHot>> GetDanhsachbaihattheoalbum(@Field("idalbum") String idalbum);

    @FormUrlEncoded
    @POST("updateluotthich.php")
    Call<String> UpdateLuotThich(@Field("luotthich") String luotthich , @Field("idbaihat") String idbaihat);

    @FormUrlEncoded
    @POST("searchbaihat.php")
    Call<List<SongHot>> GetSearchBaihat(@Field("tukhoa") String tukhoa);
}

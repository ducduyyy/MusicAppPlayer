package com.example.musicappplayer.service;

import com.example.musicappplayer.model.Banner;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface Dataservice {
    @GET("songbanner.php")
    Call<List<Banner>> getDataBanner();
}

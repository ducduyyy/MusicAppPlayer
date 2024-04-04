package com.example.musicappplayer.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.widget.ImageView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.musicappplayer.R;
import com.example.musicappplayer.adapter.ListSongAdapter;
import com.example.musicappplayer.model.Album;
import com.example.musicappplayer.model.Banner;
import com.example.musicappplayer.model.Playlist;
import com.example.musicappplayer.model.SongHot;
import com.example.musicappplayer.model.TheLoai;
import com.example.musicappplayer.service.APIService;
import com.example.musicappplayer.service.Dataservice;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ListSongActivity extends AppCompatActivity {

    Banner banner;
    CoordinatorLayout coordinatorLayout;
    CollapsingToolbarLayout collapsingToolbarLayout;
    Toolbar toolbar;
    RecyclerView recyclerViewlistsong;
    FloatingActionButton floatingActionButtonlistsong;
    ImageView imageViewlistsong;
    ArrayList<SongHot> mangbaihat;
    ListSongAdapter listSongAdapter;
    Playlist playlist;
    TheLoai theLoai;
    Album album;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_list_song);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        DataIntent();
        anhxa();
        init();
        if (banner != null && !banner.getTenBaiHat().isEmpty()){
            try {
                setValueInView(banner.getTenBaiHat(), banner.getHinhBaiHat());
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            GetDataBanner(banner.getId());
        }
        if (playlist != null && !playlist.getTen().isEmpty()){
            try {
                setValueInView(playlist.getTen(), playlist.getHinhNen());
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            GetDataPlaylist(playlist.getIdPlayList());
        }
        if (theLoai != null && !theLoai.getTenTheLoai().isEmpty()){
            try {
                setValueInView(theLoai.getTenTheLoai(), theLoai.getHinhTheLoai());
            }catch (IOException e) {
                throw new RuntimeException(e);
            }
            GetDataTheloai(theLoai.getIdTheLoai());
        }
        if (album != null && !album.getTenAlbum().equals("")){
            try {
                setValueInView(album.getTenAlbum(),album.getHinhAlbum());
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            GetDataAlbum(album.getIdalbum());
        }
    }

    private void GetDataAlbum(String idalbum) {
        Dataservice dataservice = APIService.getService();
        Call<List<SongHot>> callbcak = dataservice.GetDanhsachbaihattheoalbum(idalbum);
        callbcak.enqueue(new Callback<List<SongHot>>() {
            @Override
            public void onResponse(Call<List<SongHot>> call, Response<List<SongHot>> response) {
                mangbaihat = (ArrayList<SongHot>) response.body();
                listSongAdapter = new ListSongAdapter(ListSongActivity.this, mangbaihat);
                recyclerViewlistsong.setLayoutManager(new LinearLayoutManager(ListSongActivity.this));
                recyclerViewlistsong.setAdapter(listSongAdapter);
            }

            @Override
            public void onFailure(Call<List<SongHot>> call, Throwable t) {

            }
        });
    }

    private void GetDataTheloai(String idtheLoai) {
        Dataservice dataservice = APIService.getService();
        Call<List<SongHot>> callback = dataservice.GetlistsongbyTheloai(idtheLoai);
        callback.enqueue(new Callback<List<SongHot>>() {
            @Override
            public void onResponse(Call<List<SongHot>> call, Response<List<SongHot>> response) {
                mangbaihat = (ArrayList<SongHot>) response.body();
                listSongAdapter = new ListSongAdapter(ListSongActivity.this, mangbaihat);
                recyclerViewlistsong.setLayoutManager(new LinearLayoutManager(ListSongActivity.this));
                recyclerViewlistsong.setAdapter(listSongAdapter);
            }

            @Override
            public void onFailure(Call<List<SongHot>> call, Throwable t) {

            }
        });
    }


    private void GetDataPlaylist(String idPlayList) {
        Dataservice dataservice = APIService.getService();
        Call<List<SongHot>> callback = dataservice.Getlistsongbyplaylist(idPlayList);
        callback.enqueue(new Callback<List<SongHot>>() {
            @Override
            public void onResponse(Call<List<SongHot>> call, Response<List<SongHot>> response) {
                mangbaihat = (ArrayList<SongHot>) response.body();
                listSongAdapter = new ListSongAdapter(ListSongActivity.this, mangbaihat);
                recyclerViewlistsong.setLayoutManager(new LinearLayoutManager(ListSongActivity.this));
                recyclerViewlistsong.setAdapter(listSongAdapter);
            }

            @Override
            public void onFailure(Call<List<SongHot>> call, Throwable t) {

            }
        });
    }

    private void setValueInView(String name, String picture) throws IOException {
        collapsingToolbarLayout.setTitle(name);
        new Thread(new Runnable() {
            @Override
            public void run() {
                Bitmap bitmap = null;
                try {
                    bitmap = Picasso.get().load(picture).get();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                BitmapDrawable bitmapDrawable = new BitmapDrawable(getResources(), bitmap);
                collapsingToolbarLayout.setBackground(bitmapDrawable);
            }
        }).start();
        Picasso.get().load(picture).into(imageViewlistsong);
    }

    private void GetDataBanner(String idbanner) {
        Dataservice dataservice = APIService.getService();
        Call<List<SongHot>> callback = dataservice.Getlistsongbybanner(idbanner);
        callback.enqueue(new Callback<List<SongHot>>() {
            @Override
            public void onResponse(Call<List<SongHot>> call, Response<List<SongHot>> response) {
                mangbaihat = (ArrayList<SongHot>) response.body();
                listSongAdapter = new ListSongAdapter(ListSongActivity.this, mangbaihat);
                recyclerViewlistsong.setLayoutManager(new LinearLayoutManager(ListSongActivity.this));
                recyclerViewlistsong.setAdapter(listSongAdapter);
            }

            @Override
            public void onFailure(Call<List<SongHot>> call, Throwable t) {

            }
        });
    }

    private void init(){
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(v->finish());
        collapsingToolbarLayout.setExpandedTitleColor(Color.WHITE);
        collapsingToolbarLayout.setCollapsedTitleTextColor(Color.WHITE);
    }

    private void anhxa() {
        coordinatorLayout = findViewById(R.id.main);
        collapsingToolbarLayout = findViewById(R.id.collapsingtoolbar);
        toolbar = findViewById(R.id.toolbarlistsong);
        recyclerViewlistsong = findViewById(R.id.recyclerviewlistsong);
        imageViewlistsong = findViewById(R.id.imageviewlistsong);
    }

    private void DataIntent() {
        Intent intent = getIntent();
        if (intent!= null){
            if (intent.hasExtra("banner")){
                banner = (Banner) intent.getSerializableExtra("banner");
            }
            if (intent.hasExtra("itemplaylist")){
                playlist = (Playlist) intent.getSerializableExtra("itemplaylist");
            }
            if (intent.hasExtra("idtheloai")){
                theLoai =(TheLoai) intent.getSerializableExtra("idtheloai");
            }
            if(intent.hasExtra("album")){
                album = (Album) intent.getSerializableExtra("album");
            }
        }
    }
}
package com.example.musicappplayer.fragment;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.musicappplayer.R;
import com.example.musicappplayer.adapter.AlbumAdapter;
import com.example.musicappplayer.adapter.AlbumThuvienAdapter;
import com.example.musicappplayer.adapter.DownloadedMusicAdapter;
import com.example.musicappplayer.model.Album;
import com.example.musicappplayer.model.Songs;
import com.example.musicappplayer.service.APIService;
import com.example.musicappplayer.service.Dataservice;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Nullable;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FragmentAlbumThuvien extends Fragment {
    View view;
    RecyclerView recyclerviewthuvien;
    AlbumThuvienAdapter albumThuvienAdapter;
    ArrayList<Songs> downloaded_song ;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, @Nullable Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_album_thuvien,container,false);
        anhxa();
        getData();
        return view;
    }

    @SuppressLint("WrongViewCast")
    private void anhxa() {
        recyclerviewthuvien = view.findViewById(R.id.recyclelerviewalbumthuvien);
    }

    private void getData() {
        Dataservice dataservice = APIService.getService();
        Call<List<Album>> callback = dataservice.getAlbumhot();
        callback.enqueue(new Callback<List<Album>>() {
            @Override
            public void onResponse(Call<List<Album>> call, Response<List<Album>> response) {
                ArrayList<Album> albumArrayList = (ArrayList<Album>) response.body();

                albumThuvienAdapter = new AlbumThuvienAdapter(getActivity(),albumArrayList);

                LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
                linearLayoutManager.setOrientation(RecyclerView.HORIZONTAL);
                recyclerviewthuvien.setLayoutManager(linearLayoutManager);
                recyclerviewthuvien.setAdapter(albumThuvienAdapter);

            }

            @Override
            public void onFailure(Call<List<Album>> call, Throwable t) {

            }
        });
    }
}

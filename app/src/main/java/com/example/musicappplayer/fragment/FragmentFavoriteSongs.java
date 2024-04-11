package com.example.musicappplayer.fragment;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.musicappplayer.R;
import com.example.musicappplayer.adapter.FavoriteSongsAdapter;
import com.example.musicappplayer.adapter.SongHistoryAdapter;
import com.example.musicappplayer.model.Songs;
import com.example.musicappplayer.service.APIService;
import com.example.musicappplayer.service.Dataservice;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Nullable;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FragmentFavoriteSongs extends Fragment {
    View view;
    RecyclerView recyclerViewfavoritesong;
    FavoriteSongsAdapter favoriteSongsAdapter;
    ArrayList<Songs> song_played ;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, @Nullable Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_favorite_songs,container,false);
        anhxa();
        getData();
        return view;
    }

    @SuppressLint("WrongViewCast")
    private void anhxa() {
        recyclerViewfavoritesong = view.findViewById(R.id.recyclelerfavoritesong);

    }

    private void getData() {
//        favoriteSongsAdapter = new FavoriteSongsAdapter(getActivity(),song_played);
//
//        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
//        linearLayoutManager.setOrientation(RecyclerView.HORIZONTAL);
//        recyclerViewfavoritesong.setLayoutManager(linearLayoutManager);
//        recyclerViewfavoritesong.setAdapter(favoriteSongsAdapter);

    }
}

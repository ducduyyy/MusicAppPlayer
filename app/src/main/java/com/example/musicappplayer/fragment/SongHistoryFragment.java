package com.example.musicappplayer.fragment;

import static android.content.Intent.getIntent;
import static android.content.Intent.getIntentOld;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.LifecycleObserver;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.musicappplayer.R;
import com.example.musicappplayer.adapter.ListSongAdapter;
import com.example.musicappplayer.adapter.SongHistoryAdapter;
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

public class SongHistoryFragment extends Fragment {
    View view;
    RecyclerView recyclerViewsonghistory;
    SongHistoryAdapter songHistoryAdapter;
    ArrayList<Songs> songs_played;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, @Nullable Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_song_history,container,false);
        anhxa();
        getData();
        return view;
    }

    private void anhxa() {
        recyclerViewsonghistory = view.findViewById(R.id.recyclelersonghistory);
    }

    public void getData() {
//        if (songs_played!= null){
//            ListSongAdapter listSongAdapter = new ListSongAdapter(getActivity(), songs_played);
//            songHistoryAdapter = new SongHistoryAdapter(getActivity(), songs_played);
//
//            songs_played = listSongAdapter.getSongsPlayed();
//
//            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
//            linearLayoutManager.setOrientation(RecyclerView.HORIZONTAL);
//            recyclerViewsonghistory.setLayoutManager(linearLayoutManager);
//            recyclerViewsonghistory.setAdapter(songHistoryAdapter);
//        }

    }
}
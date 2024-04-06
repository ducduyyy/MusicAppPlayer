package com.example.musicappplayer.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.musicappplayer.R;
import com.example.musicappplayer.adapter.SongHotAdapter;
import com.example.musicappplayer.model.Songs;
import com.example.musicappplayer.service.APIService;
import com.example.musicappplayer.service.Dataservice;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FragmentSongsHot extends Fragment {
    View view;
    RecyclerView recyclerView;
    SongHotAdapter songHotAdapter;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_songs_hot,container,false);
        getData();
        recyclerView = view.findViewById(R.id.recyclelerSongHot);
        return view;
    }

    private void getData() {
        Dataservice dataservice = APIService.getService();
        Call<List<Songs>> callback = dataservice.getSongs();
        callback.enqueue(new Callback<List<Songs>>() {
            @Override
            public void onResponse(Call<List<Songs>> call, Response<List<Songs>> response) {
                ArrayList<Songs> songArrayList = (ArrayList<Songs>) response.body();
                songHotAdapter = new SongHotAdapter(getActivity(),songArrayList);
                LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
                linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
                recyclerView.setLayoutManager(linearLayoutManager);
                recyclerView.setAdapter(songHotAdapter);
            }

            @Override
            public void onFailure(Call<List<Songs>> call, Throwable t) {

            }
        });
    }
}

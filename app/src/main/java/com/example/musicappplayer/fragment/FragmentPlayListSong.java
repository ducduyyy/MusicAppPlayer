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
import com.example.musicappplayer.activity.PlayNhacActivity;
import com.example.musicappplayer.adapter.PlayNhacAdapter;

public class FragmentPlayListSong extends Fragment {
    View view;
    RecyclerView recyclerViewPlay;
    PlayNhacAdapter playNhacAdapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_play_list_song,container,false);
        recyclerViewPlay = view.findViewById(R.id.recyclelerPlaySong);

        if (PlayNhacActivity.songArrayList.size()>0){
            playNhacAdapter = new PlayNhacAdapter(getActivity(), PlayNhacActivity.songArrayList);
            recyclerViewPlay.setLayoutManager(new LinearLayoutManager(getActivity()));
            recyclerViewPlay.setAdapter(playNhacAdapter);
        }

        return view;
    }
}

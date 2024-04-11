package com.example.musicappplayer.fragment;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.musicappplayer.R;
import com.example.musicappplayer.adapter.AlbumAdapter;
import com.example.musicappplayer.adapter.SongHistoryAdapter;
import com.example.musicappplayer.model.Album;
import com.example.musicappplayer.model.Songs;
import com.example.musicappplayer.service.APIService;
import com.example.musicappplayer.service.Dataservice;
import com.google.android.material.appbar.AppBarLayout;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FragmentThuVien extends Fragment {
    View view;
    TextView textviewcuatoi;
//    RecyclerView recyclerView;
    AppBarLayout appBarLayout;
    TextView textviewCuaToi;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_thuvien,container,false);
        textviewCuaToi = view.findViewById(R.id.textviewCuaToi);
        appBarLayout = view.findViewById(R.id.appBarLayoutSearchThuVienMusic);

        return view;
    }



}

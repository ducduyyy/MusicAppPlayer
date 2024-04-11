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
import com.example.musicappplayer.adapter.DownloadedMusicAdapter;
import com.example.musicappplayer.adapter.FavoriteSongsAdapter;
import com.example.musicappplayer.model.Songs;
import com.example.musicappplayer.service.APIService;
import com.example.musicappplayer.service.Dataservice;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Nullable;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FragmentDownloadedMusic extends Fragment {
    View view;
    RecyclerView recyclerviewdownloadedmusic;
    DownloadedMusicAdapter downloadedMusicAdapter;
    ArrayList<Songs> downloaded_song ;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, @Nullable Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_downloaded_music,container,false);
        anhxa();
        getData();
        return view;
    }

    @SuppressLint("WrongViewCast")
    private void anhxa() {
        recyclerviewdownloadedmusic = view.findViewById(R.id.recyclelerdownloadedmusic);
    }

    private void getData() {
//        @SuppressLint("SdCardPath") String filePath = "/sdcard/Download"; // Đường dẫn tới thư mục chứa file bài hát
//
//        File directory = new File(filePath);
//        if (directory.exists() && directory.isDirectory()) { // Kiểm tra xem thư mục tồn tại và là thư mục
//            File[] files = directory.listFiles(); // Lấy danh sách các file trong thư mục
//
//            downloaded_song = new ArrayList<>(); // Khởi tạo danh sách bài hát tải xuống
//
//            assert files != null;
//            for (File file : files) {
//                // Tạo đối tượng Songs từ thông tin của file
//                String songName = file.getName();
//                String songPath = file.getAbsolutePath();
//
//                Songs song = new Songs(songName, songPath);
//                downloaded_song.add(song);
//            }
//
//            downloadedMusicAdapter = new DownloadedMusicAdapter(getActivity(), downloaded_song);
//            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
//            linearLayoutManager.setOrientation(RecyclerView.HORIZONTAL);
//            recyclerviewdownloadedmusic.setLayoutManager(linearLayoutManager);
//            recyclerviewdownloadedmusic.setAdapter(downloadedMusicAdapter);
//        }
    }

}

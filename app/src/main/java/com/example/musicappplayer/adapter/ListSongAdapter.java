package com.example.musicappplayer.adapter;

import static androidx.core.content.ContextCompat.checkSelfPermission;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.musicappplayer.R;
import com.example.musicappplayer.activity.MainActivity;
import com.example.musicappplayer.activity.PlayNhacActivity;
import com.example.musicappplayer.fragment.SongHistoryFragment;
import com.example.musicappplayer.model.Songs;
import com.example.musicappplayer.service.APIService;
import com.example.musicappplayer.service.Dataservice;
import com.example.musicappplayer.untils.DownloadMusicManager;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ListSongAdapter extends RecyclerView.Adapter<ListSongAdapter.ViewHolder>{
    Context context;
    ArrayList<Songs> mangbaihat;
    public ListSongAdapter(Context context, ArrayList<Songs> mangbaihat) {
        this.context = context;
        this.mangbaihat = mangbaihat;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.list_song_adapter, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Songs songs = mangbaihat.get(position);
        holder.txtsingername.setText(songs.getCasi());
        holder.txtsongname.setText(songs.getTenBaiHat());
        holder.txtindex.setText(position+1+"");
    }

    @Override
    public int getItemCount() {
        return mangbaihat.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView txtindex, txtsongname, txtsingername;
        ImageView imageViewluotthich, imageviewdownloadmusic;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txtsingername = itemView.findViewById(R.id.textviewsingername);
            txtsongname = itemView.findViewById(R.id.textviewsongname);
            txtindex = itemView.findViewById(R.id.textviewlistindex);
            imageViewluotthich  =itemView.findViewById(R.id.imageviewluotthichlistsong);
            imageviewdownloadmusic = itemView.findViewById(R.id.imageviewdownloadsong);

            imageViewluotthich.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(imageViewluotthich.getDrawable().getConstantState().equals(context.getDrawable(R.drawable.ic_favorite).getConstantState())){
                        imageViewluotthich.setImageResource(R.drawable.icon_love_red);
                        Dataservice dataservice = APIService.getService();
                        Call<String> callback = dataservice.UpdateLuotThich("1", mangbaihat.get(getPosition()).getIdBaiHat());
                        callback.enqueue(new Callback<String>() {
                            @Override
                            public void onResponse(Call<String> call, Response<String> response) {
                                String ketqua = response.body();
                                if(ketqua.equals("Success")){
                                    Toast.makeText(context, "Đã thích", Toast.LENGTH_SHORT).show();
                                }else{
                                    Toast.makeText(context, "Lỗi!!", Toast.LENGTH_SHORT).show();
                                }
                            }

                            @Override
                            public void onFailure(Call<String> call, Throwable t) {

                            }
                        });
                    }else {
                        imageViewluotthich.setImageResource(R.drawable.ic_favorite);
                        Dataservice dataservice = APIService.getService();
                        Call<String> callback = dataservice.UpdateLuotThich("-1", mangbaihat.get(getPosition()).getIdBaiHat());
                        callback.enqueue(new Callback<String>() {
                            @Override
                            public void onResponse(Call<String> call, Response<String> response) {
                                String ketqua = response.body();
                                if(ketqua.equals("Success")){
                                    Toast.makeText(context, "Đã bỏ thích", Toast.LENGTH_SHORT).show();
                                }else{
                                    Toast.makeText(context, "Lỗi!!", Toast.LENGTH_SHORT).show();
                                }
                            }

                            @Override
                            public void onFailure(Call<String> call, Throwable t) {

                            }
                        });
                    }

                }
            });

            imageviewdownloadmusic.setOnClickListener(v -> {
                int currentPosition = getAdapterPosition(); // Lấy vị trí hiện tại của item trong danh sách
                if (currentPosition != RecyclerView.NO_POSITION) {

                    Songs selectedSong = mangbaihat.get(currentPosition); // Lấy đối tượng Songs tại vị trí hiện tại
                    DownloadMusicManager downloadMusicManager = new DownloadMusicManager(context);
                    downloadMusicManager.checkPermission((Activity) context ,selectedSong, new DownloadMusicManager.PermissionCallback() {
                        @Override
                        public void onPermissionGranted(Songs songs) {
                            downloadMusicManager.checkPermissonAndDownloadSong(songs);
                        }

                        @Override
                        public void onPermissionDenied() {

                        }
                    });
                }
            });


            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, PlayNhacActivity.class);
                    intent.putExtra("song",mangbaihat.get(getAdapterPosition()));
                    context.startActivity(intent);
                }
            });

        }
    }
}

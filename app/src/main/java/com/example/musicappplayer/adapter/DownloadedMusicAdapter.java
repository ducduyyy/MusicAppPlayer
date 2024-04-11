package com.example.musicappplayer.adapter;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.musicappplayer.R;
import com.example.musicappplayer.activity.PlayNhacActivity;
import com.example.musicappplayer.model.Songs;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class DownloadedMusicAdapter extends RecyclerView.Adapter<DownloadedMusicAdapter.ViewHolder>{
    Context context;
    ArrayList<Songs> songList;

    public DownloadedMusicAdapter(Context context,ArrayList<Songs> songList) {
        this.context = context;
        this.songList = songList;
    }

    @NonNull
    @Override
    public DownloadedMusicAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_downloaded_music, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull DownloadedMusicAdapter.ViewHolder holder, int position) {
        Songs song = songList.get(position);
        holder.txttendownloadedsong.setText(song.getTenBaiHat());
        holder.txtcasidownloadedsong.setText(song.getCasi());
        Picasso.get().load(song.getHinhBaiHat()).into(holder.imageViewdownloadedsong);

    }

    @Override
    public int getItemCount() {
        return songList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imageViewdownloadedsong;
        TextView txttendownloadedsong, txtcasidownloadedsong;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imageViewdownloadedsong = itemView.findViewById(R.id.imageviewdownloadedmusic);
            txtcasidownloadedsong = itemView.findViewById(R.id.textviewTenCaSidownloadedmusic);
            txttendownloadedsong = itemView.findViewById(R.id.textviewtendownloadedmusic);
        }

    }


}

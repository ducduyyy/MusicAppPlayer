package com.example.musicappplayer.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.musicappplayer.R;
import com.example.musicappplayer.model.Album;
import com.example.musicappplayer.model.Songs;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class AlbumThuvienAdapter extends RecyclerView.Adapter<AlbumThuvienAdapter.ViewHolder>{
    Context context;
    ArrayList<Album> songList;

    public AlbumThuvienAdapter(Context context,ArrayList<Album> songList) {
        this.context = context;
        this.songList = songList;
    }

    @NonNull
    @Override
    public AlbumThuvienAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_album_thuvien, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull AlbumThuvienAdapter.ViewHolder holder, int position) {
        Album song = songList.get(position);
        holder.txttenalbumthuvien.setText(song.getTenAlbum());
        holder.txtcasialbumthuvien.setText(song.getTenCASiAlbum());
        Picasso.get().load(song.getHinhAlbum()).into(holder.imageviewalbumthuvien);

    }

    @Override
    public int getItemCount() {
        return songList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imageviewalbumthuvien;
        TextView txttenalbumthuvien, txtcasialbumthuvien;

        @SuppressLint("WrongViewCast")
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imageviewalbumthuvien = itemView.findViewById(R.id.imageviewalbumthuvien);
            txtcasialbumthuvien = itemView.findViewById(R.id.textviewTenCaSialbumthuvien);
            txttenalbumthuvien = itemView.findViewById(R.id.textviewtenalbumthuvien);
        }

    }
}

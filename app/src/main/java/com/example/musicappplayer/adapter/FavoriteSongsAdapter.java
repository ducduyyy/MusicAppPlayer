package com.example.musicappplayer.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.musicappplayer.R;
import com.example.musicappplayer.model.Songs;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class FavoriteSongsAdapter extends RecyclerView.Adapter<FavoriteSongsAdapter.ViewHolder>{
    Context context;
    ArrayList<Songs> songList;

    public FavoriteSongsAdapter(Context context,ArrayList<Songs> songList) {
        this.context = context;
        this.songList = songList;
    }

    @NonNull
    @Override
    public FavoriteSongsAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_favorite_songs, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull FavoriteSongsAdapter.ViewHolder holder, int position) {
        Songs song = songList.get(position);
        holder.txttenfavoritesong.setText(song.getTenBaiHat());
        holder.txtcasifavoritesong.setText(song.getCasi());
        Picasso.get().load(song.getHinhBaiHat()).into(holder.imageViewfavoritesong);
    }

    @Override
    public int getItemCount() {
        return songList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imageViewfavoritesong;
        TextView txttenfavoritesong, txtcasifavoritesong;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imageViewfavoritesong = itemView.findViewById(R.id.imageviewfavoritesong);
            txtcasifavoritesong = itemView.findViewById(R.id.textviewTenCaSifavoritesong);
            txttenfavoritesong = itemView.findViewById(R.id.textviewtenfavoritesong);
        }
    }
}

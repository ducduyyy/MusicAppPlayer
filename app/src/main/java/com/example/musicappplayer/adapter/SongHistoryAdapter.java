package com.example.musicappplayer.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.musicappplayer.R;
import com.example.musicappplayer.fragment.FragmentHome;
import com.example.musicappplayer.fragment.FragmentInfoUser;
import com.example.musicappplayer.fragment.FragmentThuVien;
import com.example.musicappplayer.model.Songs;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class SongHistoryAdapter extends RecyclerView.Adapter<SongHistoryAdapter.ViewHolder> {
    public static ArrayList<Songs> songs_played;
    Context context;
    public ArrayList<Songs> songs_playded;

    public SongHistoryAdapter(Context context, ArrayList<Songs> songs_playded) {
        this.context = context;
        this.songs_playded = songs_playded;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_song_history, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Songs song = songs_playded.get(position);
        holder.txttenhistorysong.setText(song.getTenBaiHat());
        holder.txtcasisonghistory.setText(song.getCasi());
        Picasso.get().load(song.getHinhBaiHat()).into(holder.imageViewsonghistory);
    }

    @Override
    public int getItemCount() {
        return songs_playded.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imageViewsonghistory;
        TextView txttenhistorysong, txtcasisonghistory;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
             imageViewsonghistory = itemView.findViewById(R.id.imageviewsonghistory);
             txtcasisonghistory = itemView.findViewById(R.id.textviewTenCaSisonghistory);
             txttenhistorysong = itemView.findViewById(R.id.textviewtensonghistory);
        }


    }
}

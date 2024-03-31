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
import com.example.musicappplayer.model.SongHot;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;


public class SongHotAdapter extends RecyclerView.Adapter<SongHotAdapter.ViewHolder> {
    Context context;
    ArrayList<SongHot> songHotArrayList;

    public SongHotAdapter(Context context, ArrayList<SongHot> songHotArrayList) {
        this.context = context;
        this.songHotArrayList = songHotArrayList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.item_song_hot,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        SongHot songHot = songHotArrayList.get(position);
        holder.txtcasi.setText(songHot.getCasi());
        holder.txtten.setText(songHot.getTenBaiHat());
        Picasso.get()
                .load(songHot.getHinhBaiHat())
                .into(holder.imghinh);

    }

    @Override
    public int getItemCount() {
        return songHotArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        TextView txtten,txtcasi;
        ImageView imghinh,imgluotthich;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txtten = itemView.findViewById(R.id.textviewTenbaihat);
            txtcasi = itemView.findViewById(R.id.textviewTencasi);
            imghinh = itemView.findViewById(R.id.imgviewSongHot);
            imgluotthich = itemView.findViewById(R.id.imgviewluotthich);
        }
    }

}

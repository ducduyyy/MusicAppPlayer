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

import java.util.ArrayList;

public class ListSongAdapter extends RecyclerView.Adapter<ListSongAdapter.ViewHolder>{
    Context context;
    ArrayList<SongHot> mangbaihat;

    public ListSongAdapter(Context context, ArrayList<SongHot> mangbaihat) {
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
        SongHot songHot = mangbaihat.get(position);
        holder.txtsingername.setText(songHot.getCasi());
        holder.txtsongname.setText(songHot.getTenBaiHat());
        holder.txtindex.setText(position+1+"");
    }

    @Override
    public int getItemCount() {
        return mangbaihat.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView txtindex, txtsongname, txtsingername;
        ImageView imageViewluotthich;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txtsingername = itemView.findViewById(R.id.textviewsingername);
            txtsongname = itemView.findViewById(R.id.textviewsongname);
            txtindex = itemView.findViewById(R.id.textviewlistindex);
            imageViewluotthich  =itemView.findViewById(R.id.imageviewluotthichlistsong);
        }
    }
}

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
import com.example.musicappplayer.model.Album;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class AlbumAdapter extends RecyclerView.Adapter<AlbumAdapter.ViewHolder> {
    Context context;
    ArrayList<Album> albumArrayList;

    public AlbumAdapter(Context context, ArrayList<Album> albumArrayList) {
        this.context = context;
        this.albumArrayList = albumArrayList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //gắn layout vào
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.item_album,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        //gán giá trị vao item
        Album album = albumArrayList.get(position);
        holder.txtCasiAlbum.setText(album.getTenCASiAlbum());
        holder.txtTenAlbum.setText(album.getTenAlbum());
        Picasso.get()
                .load(album.getHinhAlbum())
                .into(holder.imgAlbum);

    }

    @Override
    public int getItemCount() {
        //số item trong recyclerView
        return albumArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        ImageView imgAlbum;
        TextView txtTenAlbum, txtCasiAlbum;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imgAlbum = itemView.findViewById(R.id.imgviewAlbum);
            txtTenAlbum = itemView.findViewById(R.id.textviewTenAlbum);
            txtCasiAlbum = itemView.findViewById(R.id.textviewTenCaSiAlbum);
        }
    }
}

package com.example.musicappplayer.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.musicappplayer.R;
import com.example.musicappplayer.activity.ListSongActivity;
import com.example.musicappplayer.model.Playlist;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class ListPlaylistAdapter extends RecyclerView.Adapter<ListPlaylistAdapter.ViewHolder>{
    Context context;
    ArrayList<Playlist> mangplaylist;

    public ListPlaylistAdapter(Context context, ArrayList<Playlist> mangplaylist) {
        this.context = context;
        this.mangplaylist = mangplaylist;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.list_playlist_adapter,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Playlist playlist = mangplaylist.get(position);
        Picasso.get().load(playlist.getHinhNen()).into(holder.imageViewlistplaylist);
        holder.txtplaylistname.setText(playlist.getTen());
    }

    @Override
    public int getItemCount() {
        return mangplaylist.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        ImageView imageViewlistplaylist;
        TextView txtplaylistname;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imageViewlistplaylist = itemView.findViewById(R.id.imageviewlistplaylist);
            txtplaylistname = itemView.findViewById(R.id.textviewlistplaylist);
            itemView.setOnClickListener(v->{
                Intent intent = new Intent(context, ListSongActivity.class);
                intent.putExtra("itemplaylist", mangplaylist.get(getPosition()));
                context.startActivity(intent);
            });
        }
    }
}

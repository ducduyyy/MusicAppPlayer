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
import com.example.musicappplayer.activity.DanhsachtheloaitheochudeActivity;
import com.example.musicappplayer.model.ChuDe;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class DanhsachtatcachudeAdapter extends  RecyclerView.Adapter<DanhsachtatcachudeAdapter.ViewHolder> {
    static Context context;
    ArrayList<ChuDe> mangchude;

    public DanhsachtatcachudeAdapter(Context context,ArrayList<ChuDe> mangchude){
        this.context = context;
        this.mangchude = mangchude;
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.item_cac_chu_de,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ChuDe chude = mangchude.get(position);
        Picasso.get()
                .load(chude.getHinhCHuDe())
                .into(holder.imgchude);

    }

    @Override
    public int getItemCount() {
        return mangchude.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        ImageView imgchude;
        public ViewHolder(View itemView) {
            super(itemView);
            imgchude = itemView.findViewById(R.id.imageviewitemcacchude);
            imgchude.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, DanhsachtheloaitheochudeActivity.class);
                    intent.putExtra("chude",mangchude.get(getPosition()));
                    context.startActivity(intent);
                }
            });
        }
    }
}

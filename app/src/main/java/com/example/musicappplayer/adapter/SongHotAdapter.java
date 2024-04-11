package com.example.musicappplayer.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.musicappplayer.R;
import com.example.musicappplayer.activity.PlayNhacActivity;
import com.example.musicappplayer.model.Songs;
import com.example.musicappplayer.service.APIService;
import com.example.musicappplayer.service.Dataservice;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class SongHotAdapter extends RecyclerView.Adapter<SongHotAdapter.ViewHolder> {
    Context context;
    ArrayList<Songs> songsArrayList;

    public SongHotAdapter(Context context, ArrayList<Songs> songsArrayList) {
        this.context = context;
        this.songsArrayList = songsArrayList;
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

        Songs songs = songsArrayList.get(position);
        holder.txtcasi.setText(songs.getCasi());
        holder.txtten.setText(songs.getTenBaiHat());
        Picasso.get()
                .load(songs.getHinhBaiHat())
                .into(holder.imghinh);

    }

    @Override
    public int getItemCount() {
        return songsArrayList.size();
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
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, PlayNhacActivity.class);
                    intent.putExtra("song", songsArrayList.get(getAdapterPosition()));
                    v.getContext().startActivity(intent);
                }
            });
            imgluotthich.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(imgluotthich.getDrawable().getConstantState().equals(context.getDrawable(R.drawable.ic_favorite).getConstantState())){
                        imgluotthich.setImageResource(R.drawable.icon_love_red);
                        Dataservice dataservice = APIService.getService();
                        Call<String> callback = dataservice.UpdateLuotThich("1", songsArrayList.get(getPosition()).getIdBaiHat());
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
                        imgluotthich.setImageResource(R.drawable.ic_favorite);
                        Dataservice dataservice = APIService.getService();
                        Call<String> callback = dataservice.UpdateLuotThich("-1", songsArrayList.get(getPosition()).getIdBaiHat());
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
        }
    }

}

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

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ListSongAdapter extends RecyclerView.Adapter<ListSongAdapter.ViewHolder>{
    Context context;
    ArrayList<Songs> mangbaihat;

    public ListSongAdapter(Context context, ArrayList<Songs> mangbaihat) {
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
        Songs songs = mangbaihat.get(position);
        holder.txtsingername.setText(songs.getCasi());
        holder.txtsongname.setText(songs.getTenBaiHat());
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
            imageViewluotthich.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    imageViewluotthich.setImageResource(R.drawable.icon_love_red);
                    Dataservice dataservice = APIService.getService();
                    Call<String> callback = dataservice.UpdateLuotThich("1",mangbaihat.get(getPosition()).getIdBaiHat());
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
                    imageViewluotthich.setEnabled(false);
                }
            });

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, PlayNhacActivity.class);
                    intent.putExtra("song",mangbaihat.get(getAdapterPosition()));
                    context.startActivity(intent);
                }
            });
        }
    }
}

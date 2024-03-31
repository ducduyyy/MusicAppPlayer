package com.example.musicappplayer.fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import com.example.musicappplayer.R;
import com.example.musicappplayer.model.ChuDe;
import com.example.musicappplayer.model.ChudeVaTheLoai;
import com.example.musicappplayer.model.TheLoai;
import com.example.musicappplayer.service.APIService;
import com.example.musicappplayer.service.Dataservice;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Fragment_chude_theloai_today extends Fragment {
    View view;
    HorizontalScrollView horizontalScrollView;
    TextView txtxemthemchudevatheloai;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_chude_theloai_today,container,false);
        anhxa();
        getdata();
        return view;
    }

    private void anhxa() {
        horizontalScrollView = view.findViewById(R.id.horizontalScrollview);
        txtxemthemchudevatheloai = view.findViewById(R.id.textviewmore);
    }

    private void getdata() {
        Dataservice dataservice = APIService.getService();
        Call<ChudeVaTheLoai> callback = dataservice.GetCategoryMusic();
        callback.enqueue(new Callback<ChudeVaTheLoai>() {
            @Override
            public void onResponse(Call<ChudeVaTheLoai> call, Response<ChudeVaTheLoai> response) {
                ChudeVaTheLoai chudeVaTheLoai = response.body();

                //lấy dữ liệu đưa vào mảng chủ đề
                final ArrayList<ChuDe> chuDeArrayList = new ArrayList<>();
                chuDeArrayList.addAll(chudeVaTheLoai.getChuDe());

                //lấy dữ liệu đưa vào mảng thể loại
                final ArrayList<TheLoai> theLoaiArrayList = new ArrayList<>();
                theLoaiArrayList.addAll(chudeVaTheLoai.getTheLoai());

                //đưa mảng vào trong viewgroup sau đó đưa vào horizontal
                LinearLayout linearLayout = new LinearLayout(getActivity());
                linearLayout.setOrientation(LinearLayout.HORIZONTAL);//chọn chiều

                LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(620,350);
                layoutParams.setMargins(10,20,10,30);
                for (int i = 0; i<(chuDeArrayList.size()); i++){
                    CardView cardView = new CardView(getActivity());
                    cardView.setRadius(10);
                    ImageView imageView = new ImageView(getActivity());
                    imageView.setScaleType(ImageView.ScaleType.FIT_XY);
                    if (chuDeArrayList.get(i).getHinhCHuDe()!=null){
                        Picasso.get()
                                .load(chuDeArrayList.get(i).getHinhCHuDe())
                                .into(imageView);
                    }
                    cardView.setLayoutParams(layoutParams);
                    cardView.addView(imageView);
                    linearLayout.addView(cardView);
                }
                for (int j = 0; j<(theLoaiArrayList.size()); j++){
                    CardView cardView = new CardView(getActivity());
                    cardView.setRadius(10);
                    ImageView imageView = new ImageView(getActivity());
                    imageView.setScaleType(ImageView.ScaleType.FIT_XY);
                    if (theLoaiArrayList.get(j).getHinhTheLoai()!=null){
                        Picasso.get()
                                .load(theLoaiArrayList.get(j).getHinhTheLoai())
                                .into(imageView);
                    }
                    cardView.setLayoutParams(layoutParams);
                    cardView.addView(imageView);
                    linearLayout.addView(cardView);
                }
                horizontalScrollView.addView(linearLayout);
            }

            @Override
            public void onFailure(Call<ChudeVaTheLoai> call, Throwable t) {

            }
        });
    }
}

package com.example.musicappplayer.fragment;

import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.example.musicappplayer.R;
import com.example.musicappplayer.adapter.BannerAdapter;
import com.example.musicappplayer.model.Banner;
import com.example.musicappplayer.service.APIService;
import com.example.musicappplayer.service.Dataservice;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Fragment_Banner extends Fragment {
    View view;
    ViewPager viewPager;
    BannerAdapter bannerAdapter;
    Runnable runnable;
    Handler handler;
    int currentItem;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_banner,container,false);
        GetData();
        anhxa();
        return view;
    }

    private void anhxa() {
        viewPager = view.findViewById(R.id.viewpagerbanner1);
    }

    private void GetData() {
        Dataservice dataservice = APIService.getService();
        Call<List<Banner>> callBack = dataservice.getDataBanner();
        callBack.enqueue(new Callback<List<Banner>>() {
            @Override
            public void onResponse(Call<List<Banner>> call, Response<List<Banner>> response) {
                ArrayList<Banner> banners = (ArrayList<Banner>) response.body();
                bannerAdapter = new BannerAdapter(getActivity(),banners);
                viewPager.setAdapter(bannerAdapter);
                //banner tu dong thay doi
                handler = new Handler();
                //lang nghe su kien khi handler goi den
                currentItem = 0;
                runnable = new Runnable() {
                    @Override
                    public void run() {
                        if (viewPager.getAdapter()!=null){
                            currentItem++;
                            if(currentItem>viewPager.getAdapter().getCount()){
                                currentItem =0;
                            }
                            viewPager.setCurrentItem(currentItem,true);
                        }
                        handler.postDelayed(runnable,5000);
                    }
                };
                handler.postDelayed(runnable,5000);
            }

            @Override
            public void onFailure(Call<List<Banner>> call, Throwable t) {

            }
        });
    }
}

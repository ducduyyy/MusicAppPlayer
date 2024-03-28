package com.example.musicappplayer.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import com.example.musicappplayer.R;
import com.example.musicappplayer.model.Banner;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class BannerAdapter extends PagerAdapter {
    //truyen du lieu de custom pager
    Context context;
    ArrayList<Banner> bannerArrayList;

    public BannerAdapter(Context context, ArrayList<Banner> bannerArrayList) {
        this.context = context;
        this.bannerArrayList = bannerArrayList;
    }

    @Override
    public int getCount() {
        return bannerArrayList.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object;
    }
    //dinh hinh va gan du lieu cho obj
    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.dong_banner,null);

        ImageView imageViewBackground = view.findViewById(R.id.imgviewbackgroundbanner);
        ImageView imageViewsongbanner = view.findViewById(R.id.imgviewbanner);
        TextView txtViewsongbanner = view.findViewById(R.id.textviewtitlebannerbaihat);
        TextView txtViewinfobanner = view.findViewById(R.id.textviewinfobanner);

        Picasso.get()
                .load(bannerArrayList.get(position).getHinhAnh())
                .fit()
                .into(imageViewBackground);
        Picasso.get()
                .load(bannerArrayList.get(position).getHinhBaiHat())
                .fit()
                .into(imageViewsongbanner);
        txtViewsongbanner.setText(bannerArrayList.get(position).getTenBaiHat());
        txtViewinfobanner.setText(bannerArrayList.get(position).getNoiDung());
        container.addView(view);
        return view;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View) object);
    }
}

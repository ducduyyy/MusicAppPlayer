package com.example.musicappplayer.activity;

import static androidx.core.app.ActivityCompat.requestPermissions;
import static com.example.musicappplayer.untils.DownloadMusicManager.REQUEST_PERMISSION_CODE;
import static com.example.musicappplayer.untils.DownloadMusicManager.context;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.Settings;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.example.musicappplayer.R;
import com.example.musicappplayer.adapter.MainViewPagerAdapter;
import com.example.musicappplayer.fragment.FragmentHome;
import com.example.musicappplayer.fragment.FragmentInfoUser;
import com.example.musicappplayer.fragment.FragmentSearch;
import com.example.musicappplayer.model.Songs;
import com.example.musicappplayer.networks.DownloadMusic;
import com.example.musicappplayer.untils.DownloadMusicManager;
import com.google.android.material.tabs.TabLayout;

public class MainActivity extends AppCompatActivity {

    private TabLayout mTablayout;
    private ViewPager viewPager;
    DownloadMusicManager downloadMusicManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        downloadMusicManager = new DownloadMusicManager();

        mapping();
        innit();
    }

    private void innit() {
        MainViewPagerAdapter mainViewPagerAdapter = new MainViewPagerAdapter(getSupportFragmentManager());
        mainViewPagerAdapter.addFragment(new FragmentHome(),"Home");
        mainViewPagerAdapter.addFragment(new FragmentSearch(),"Search");
        mainViewPagerAdapter.addFragment(new FragmentInfoUser(),"Menu");
        viewPager.setAdapter(mainViewPagerAdapter);
        mTablayout.setupWithViewPager(viewPager);
        mTablayout.getTabAt(0).setIcon(R.drawable.ic_home);
        mTablayout.getTabAt(1).setIcon(R.drawable.ic_search);
        mTablayout.getTabAt(2).setIcon(R.drawable.ic_storage);
    }

    private void mapping() {
        mTablayout = findViewById(R.id.myTabLayout);
        viewPager = findViewById(R.id.myViewpager);
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults){
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        downloadMusicManager.onRequestPermissionResult(requestCode, permissions, grantResults);

    }




}
package com.example.musicappplayer.activity;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.viewpager.widget.ViewPager;

import com.example.musicappplayer.R;
import com.example.musicappplayer.adapter.MainViewPagerAdapter;
import com.example.musicappplayer.fragment.FragmentHome;
import com.example.musicappplayer.fragment.FragmentInfoUser;
import com.example.musicappplayer.fragment.FragmentSearch;
import com.google.android.material.tabs.TabLayout;

public class MainActivity extends AppCompatActivity {

    private TabLayout mTablayout;
    private ViewPager viewPager;

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
}
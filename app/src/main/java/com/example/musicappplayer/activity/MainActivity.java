package com.example.musicappplayer.activity;

import static androidx.core.app.ActivityCompat.requestPermissions;
import static com.example.musicappplayer.untils.DownloadMusicManager.REQUEST_PERMISSION_CODE;
import static com.example.musicappplayer.untils.DownloadMusicManager.context;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.Uri;
import android.os.BatteryManager;
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
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
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
import com.example.musicappplayer.fragment.FragmentThuVien;
import com.example.musicappplayer.model.Songs;
import com.example.musicappplayer.networks.DownloadMusic;
import com.example.musicappplayer.networks.LowBatteryReceiver;
import com.example.musicappplayer.networks.NetworkChangeReceiver;
import com.example.musicappplayer.untils.DownloadMusicManager;
import com.google.android.material.tabs.TabLayout;

public class MainActivity extends AppCompatActivity {

    private TabLayout mTablayout;
    private ViewPager viewPager;
    DownloadMusicManager downloadMusicManager;
    private NetworkChangeReceiver receiver;
    private LowBatteryReceiver lowBatteryReceiver;
    private static final int REQUEST_PERMISSION_CODE = 10;


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

        // Tạo BroadcastReceiver
        receiver = new NetworkChangeReceiver();
        // Đăng ký BroadcastReceiver
        IntentFilter filterinternet = new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION);
        registerReceiver(receiver, filterinternet);


        // Tạo LowBatteryReceiver
        lowBatteryReceiver = new LowBatteryReceiver();
        // Đăng ký LowBatteryReceiver
        IntentFilter filterbattery = new IntentFilter(Intent.ACTION_BATTERY_CHANGED);
        registerReceiver(lowBatteryReceiver, filterbattery);

        checkPermissions();

    }

    private void checkPermissions() {
//        if (ActivityCompat.checkSelfPermission(MainActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED ||
//                ActivityCompat.checkSelfPermission(MainActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
//            // Yêu cầu cấp quyền từ người dùng
//            ActivityCompat.requestPermissions(MainActivity.this,
//                    new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE},
//                    REQUEST_PERMISSION_CODE);
//        }
        if (ActivityCompat.checkSelfPermission(MainActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED ||
                ActivityCompat.checkSelfPermission(MainActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {

            // Kiểm tra xem người dùng đã từ chối cấp quyền trước đó hay chưa
            if (ActivityCompat.shouldShowRequestPermissionRationale(MainActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE) ||
                    ActivityCompat.shouldShowRequestPermissionRationale(MainActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE)) {

                // Hiển thị lời giải thích cho người dùng
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setTitle("Yêu cầu quyền truy cập");
                builder.setMessage("Ứng dụng cần truy cập vào file hệ thống để thực hiện các hoạt động chính. Vui lòng cấp quyền truy cập để tiếp tục.");
                builder.setPositiveButton("Cấp quyền", (dialog, which) -> {
                    // Yêu cầu cấp quyền từ người dùng
                    ActivityCompat.requestPermissions(MainActivity.this,
                            new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE},
                            REQUEST_PERMISSION_CODE);
                });
                builder.setNegativeButton("Thoát", (dialog, which) -> {
                    finish();
                });
                builder.setCancelable(false);
                AlertDialog dialog = builder.create();
                dialog.show();

            } else {
                // Yêu cầu cấp quyền từ người dùng
                ActivityCompat.requestPermissions(MainActivity.this,
                        new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE},
                        REQUEST_PERMISSION_CODE);
            }
        }
    }
    private void showPermissionDeniedDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Yêu cầu quyền truy cập");
        builder.setMessage("Ứng dụng cần truy cập vào file hệ thống để thực hiện các hoạt động chính. Vui lòng cấp quyền truy cập để tiếp tục.");
        builder.setPositiveButton("Cấp quyền", (dialog, which) -> {
            // Yêu cầu cấp quyền từ người dùng (có thể sử dụng lại yêu cầu quyền ban đầu)
            requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE},
                    REQUEST_PERMISSION_CODE);
        });
        builder.setNegativeButton("Thoát", (dialog, which) -> {
            finish();
        });
        builder.setCancelable(false);
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    private void innit() {
        MainViewPagerAdapter mainViewPagerAdapter = new MainViewPagerAdapter(getSupportFragmentManager());
        mainViewPagerAdapter.addFragment(new FragmentHome(), "Home");
        mainViewPagerAdapter.addFragment(new FragmentThuVien(), "Lib");
        mainViewPagerAdapter.addFragment(new FragmentInfoUser(), "Menu");
        viewPager.setAdapter(mainViewPagerAdapter);
        mTablayout.setupWithViewPager(viewPager);
        mTablayout.getTabAt(0).setIcon(R.drawable.ic_home);
        mTablayout.getTabAt(1).setIcon(R.drawable.ic_dashboard);
        mTablayout.getTabAt(2).setIcon(R.drawable.ic_storage);
    }

    private void mapping() {
        mTablayout = findViewById(R.id.myTabLayout);
        viewPager = findViewById(R.id.myViewpager);
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_PERMISSION_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                // Quyền đã được cấp, thực hiện các hoạt động chính trong ứng dụng
                Toast.makeText(this, "Cấp quyền truy cập thành công!", Toast.LENGTH_SHORT).show();
            } else {
                // Người dùng từ chối cấp quyền, xử lý tương ứng (ví dụ: hiển thị thông báo)
//                showPermissionDeniedDialog();
                checkPermissions();
            }

        }



    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // Hủy đăng ký BroadcastReceiver khi Activity bị hủy
        unregisterReceiver(receiver);
        unregisterReceiver(lowBatteryReceiver);
    }



}
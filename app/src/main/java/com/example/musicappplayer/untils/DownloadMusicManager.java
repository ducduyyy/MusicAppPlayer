package com.example.musicappplayer.untils;

import static android.content.ContentValues.TAG;
import static androidx.activity.result.ActivityResultCallerKt.registerForActivityResult;
import static androidx.browser.customtabs.CustomTabsClient.getPackageName;
import static androidx.core.app.ActivityCompat.requestPermissions;
import static androidx.core.app.ActivityCompat.startActivityForResult;
import static androidx.core.content.ContextCompat.checkSelfPermission;
import static androidx.core.content.ContextCompat.getSystemService;

import android.Manifest;
import android.app.Activity;
import android.app.DownloadManager;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.Settings;
import android.util.Log;
import android.widget.Toast;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContract;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.content.ContextCompat;

import com.example.musicappplayer.R;
import com.example.musicappplayer.activity.ListSongActivity;
import com.example.musicappplayer.activity.MainActivity;
import com.example.musicappplayer.model.Songs;
import com.example.musicappplayer.networks.CheckFileSize;
import com.example.musicappplayer.networks.DownloadMusic;

import java.io.File;
import java.util.ArrayList;
import java.util.Random;

public class DownloadMusicManager {

    private static final String LOG_TAG = "DownloadMusicManager";
    private static final String FILENAME_EXTENSION = ".mp3";
    public static final int REQUEST_PERMISSION_CODE = 10;

    public static Context context;

    public DownloadMusicManager() {
    }

    public DownloadMusicManager(Context context) {
        this.context = context;
    }

    public interface PermissionCallback{
        void onPermissionGranted(Songs songs);
        void onPermissionDenied();
    }

    @RequiresApi(api = Build.VERSION_CODES.R)
    public void downloadSong(Songs songs) {
        if (isFileExists(songs)) {
            // Bài hát đã được tải xuống
            Toast.makeText(context, "This song was downloaded.", Toast.LENGTH_SHORT).show();
        } else {
            // Kiểm tra quyền truy cập
            if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.R) {
                if (context.checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_DENIED
                        && context.checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_DENIED) {
                    ActivityCompat.requestPermissions((Activity) context,
                            new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE},
                            REQUEST_PERMISSION_CODE);
                } else {
                    startDownload(songs);
                }
            } else {
                startDownload(songs);
            }
        }
    }

    private void startDownload(Songs songs) {
        DownloadManager downloadManager = (DownloadManager) context.getSystemService(Context.DOWNLOAD_SERVICE);
        DownloadManager.Request request = new DownloadManager.Request(Uri.parse(songs.getLinkBaiHat()))
                .setAllowedNetworkTypes(DownloadManager.Request.NETWORK_MOBILE | DownloadManager.Request.NETWORK_WIFI)
                .setTitle(songs.getTenBaiHat())
                .setDescription("Downloading " + songs.getTenBaiHat())
                .setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);

        if (downloadManager != null) {
            String uniqueFileName = String.valueOf(System.currentTimeMillis());
            File downloadsDir = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).getPath());
            File destinationFile = new File(downloadsDir, uniqueFileName + ".mp3");
            request.setDestinationUri(Uri.fromFile(destinationFile));
            long downloadId = downloadManager.enqueue(request);
            // Lưu downloadId để theo dõi tiến trình tải xuống

            // Hiển thị thông báo khi tải xuống hoàn thành
            BroadcastReceiver onCompleteReceiver = new BroadcastReceiver() {
                @Override
                public void onReceive(Context context, Intent intent) {
                    long completedDownloadId = intent.getLongExtra(DownloadManager.EXTRA_DOWNLOAD_ID, -1);
                    if (completedDownloadId == downloadId) {
                        Toast.makeText(context, "Download completed", Toast.LENGTH_SHORT).show();
                        // Thực hiện các hoạt động sau khi tải xuống hoàn thành
                    }
                }
            };
            context.registerReceiver(onCompleteReceiver, new IntentFilter(DownloadManager.ACTION_DOWNLOAD_COMPLETE));

            Toast.makeText(context, "Downloading " + songs.getTenBaiHat(), Toast.LENGTH_SHORT).show();
        }
    }
//    public void downloadSong(Songs songs) {
//        if (isFileExists(songs)) {
//            //checkFileSize
//            CheckFileSize checkFileSize = new CheckFileSize(context);
//            checkFileSize.setOnPostExecuteListener(new CheckFileSize.OnPostExecuteListener() {
//                @Override
//                public void onPostExecuteListener(boolean isSameSize) {
//                    if (isSameSize) {
//                        Toast.makeText(context, "This song was downloaded.", Toast.LENGTH_SHORT).show();
//                    } else {
//                        Toast.makeText(context, "This song is downloading.", Toast.LENGTH_SHORT).show();
//                    }
//                }
//            });
//            checkFileSize.execute(songs);
//        } else {
//
//                DownloadMusic downloadMusic = new DownloadMusic(context);
//                DownloadManager downloadManager = (DownloadManager) context.getSystemService(Context.DOWNLOAD_SERVICE);
//
//                DownloadManager.Request request = new DownloadManager.Request(Uri.parse(songs.getLinkBaiHat()))
//                        .setAllowedNetworkTypes(DownloadManager.Request.NETWORK_MOBILE|DownloadManager.Request.NETWORK_WIFI)
//                        .setTitle(songs.getTenBaiHat())
//                        .setDescription("Downloading " + songs.getTenBaiHat())
//                        .setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
//
//                if (downloadManager!=null){
//                    String uniqueFileName = String.valueOf(System.currentTimeMillis());
//                    File downloadsDir = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).getPath());
//                    File destinationFile = new File(downloadsDir, uniqueFileName+".mp3");
//                    request.setDestinationUri(Uri.fromFile(destinationFile));
//                    downloadManager.enqueue(request);
//                    downloadMusic.execute(songs);
//                    Toast.makeText(context, "Đã lưu: " + destinationFile, Toast.LENGTH_SHORT).show();
//                }
//
//        }
//    }

    public void checkPermission(Activity activity, Songs songs, PermissionCallback callback){
        if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.R) {
            if (context.checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_DENIED
            && context.checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_DENIED) {
                ActivityCompat.requestPermissions((Activity) context,
                        new String[] {Manifest.permission.WRITE_EXTERNAL_STORAGE
                        , Manifest.permission.READ_EXTERNAL_STORAGE}
                        , REQUEST_PERMISSION_CODE);
            } else {
                callback.onPermissionGranted(songs);
            }
        }
        else {
            callback.onPermissionGranted(songs);
        }
    }

    public void checkPermissonAndDownloadSong(Songs songs){
        checkPermission((Activity) context, songs, new DownloadMusicManager.PermissionCallback() {
            @Override
            public void onPermissionGranted(Songs songs) {
                downloadSong(songs);
            }

            @Override
            public void onPermissionDenied() {
                Toast.makeText(context, "Download Fail", Toast.LENGTH_SHORT).show();
            }
        });
    }



    public static File getFile(Songs songs) {
        File externalFile = context.getExternalFilesDir(null).getAbsoluteFile();
        String filename = Hash.md5(songs.getLinkBaiHat());
        return new File(externalFile, filename.concat(FILENAME_EXTENSION));
    }

    public int getFileSize(Songs songs) {
        File externalFile = context.getExternalFilesDir(null).getAbsoluteFile();
        String filename = Hash.md5(songs.getLinkBaiHat());
        File file = new File(externalFile, filename.concat(FILENAME_EXTENSION));

        return (int) file.length();
    }

    public static boolean isFileExists(Songs songs) {
        return getFile(songs).exists();
    }

}

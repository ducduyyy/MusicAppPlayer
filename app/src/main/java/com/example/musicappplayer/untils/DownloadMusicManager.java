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
import android.content.Context;
import android.content.Intent;
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
            //checkFileSize
            CheckFileSize checkFileSize = new CheckFileSize(context);
            checkFileSize.setOnPostExecuteListener(new CheckFileSize.OnPostExecuteListener() {
                @Override
                public void onPostExecuteListener(boolean isSameSize) {
                    if (isSameSize) {
                        Toast.makeText(context, "This song was downloaded.", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(context, "This song is downloading.", Toast.LENGTH_SHORT).show();
                    }
                }
            });
            checkFileSize.execute(songs);
        } else {

            // Mở hộp thoại chọn thư mục đích sử dụng SAF
//            Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT_TREE);
//            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
//            intent.addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
//            intent.addFlags(Intent.FLAG_GRANT_PERSISTABLE_URI_PERMISSION);
//
//            // Kiểm tra xem có ứng dụng nào hỗ trợ SAF không
//            if (intent.resolveActivity(context.getPackageManager()) != null) {
//                context.startActivity(intent);
                DownloadMusic downloadMusic = new DownloadMusic(context);
                DownloadManager downloadManager = (DownloadManager) context.getSystemService(Context.DOWNLOAD_SERVICE);

                DownloadManager.Request request = new DownloadManager.Request(Uri.parse(songs.getLinkBaiHat()))
                        .setAllowedNetworkTypes(DownloadManager.Request.NETWORK_MOBILE|DownloadManager.Request.NETWORK_WIFI)
                        .setTitle(songs.getTenBaiHat())
                        .setDescription("Downloading " + songs.getTenBaiHat())
                        .setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);

                if (downloadManager!=null){
                    String uniqueFileName = String.valueOf(System.currentTimeMillis());
                    File downloadsDir = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).getPath());
                    File destinationFile = new File(downloadsDir, uniqueFileName+".mp3");
                    request.setDestinationUri(Uri.fromFile(destinationFile));
                    downloadManager.enqueue(request);
                    downloadMusic.execute(songs);
                }
//            } else {
//                Toast.makeText(context, "No file manager app found.", Toast.LENGTH_SHORT).show();
//            }

        }
    }

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


    public void onRequestPermissionResult(int requestCode, @NonNull String[] permissons, @NonNull int[] grantResults){
        Songs songs = new Songs();
        if (requestCode == REQUEST_PERMISSION_CODE){
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.R) {
                    downloadSong(songs);
                }
            }else {
                Toast.makeText(context, "Permission Denied", Toast.LENGTH_SHORT).show();
            }
        }
        return;
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

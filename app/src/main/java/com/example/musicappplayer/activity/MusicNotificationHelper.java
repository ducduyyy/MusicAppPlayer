package com.example.musicappplayer.activity;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.Build;

import androidx.core.app.NotificationCompat;

import com.example.musicappplayer.R;

public class MusicNotificationHelper {
    private static final String CHANNEL_ID = "music_channel";
    private static final int NOTIFICATION_ID = 1;

    private Context mContext;
    private NotificationManager mNotificationManager;

    public MusicNotificationHelper(Context context) {
        mContext = context;
        mNotificationManager = (NotificationManager) mContext.getSystemService(Context.NOTIFICATION_SERVICE);

        // Tạo kênh thông báo (chỉ cần thực hiện trên Android 8.0 trở lên)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, "Music Channel", NotificationManager.IMPORTANCE_LOW);
            mNotificationManager.createNotificationChannel(channel);
        }
    }

    public void showNotification(String title, String artist, Bitmap artwork) {
        NotificationCompat.Builder builder = new NotificationCompat.Builder(mContext, CHANNEL_ID)
                .setSmallIcon(R.drawable.ic_pause)
                .setContentTitle(title)
                .setContentText(artist)
                .setLargeIcon(artwork)
                .setPriority(NotificationCompat.PRIORITY_LOW);

        Notification notification = builder.build();
        mNotificationManager.notify(NOTIFICATION_ID, notification);
    }

    public void cancelNotification() {
        mNotificationManager.cancel(NOTIFICATION_ID);
    }
}

package com.example.musicappplayer.networks;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.example.musicappplayer.model.Songs;
import com.example.musicappplayer.untils.DownloadMusicManager;

import java.io.IOException;
import java.net.URL;
import java.net.URLConnection;

public class CheckFileSize extends AsyncTask<Songs, Integer, Boolean> {
    private static final String LOG_TAG = "CheckFileSize";
    private Context context;
    private DownloadMusicManager downloadMusicManager;

    public CheckFileSize(Context context) {
        this.context = context;
        downloadMusicManager = new DownloadMusicManager(context);
    }

    @Override
    protected Boolean doInBackground(Songs... songs) {
        try {
            URL url = new URL(songs[0].getLinkBaiHat());
            URLConnection conection = url.openConnection();
            conection.connect();
            int lengthOfFile = conection.getContentLength();

            int fileSize = new DownloadMusicManager(context).getFileSize(songs[0]);

            Log.d(LOG_TAG, "lengthOfFile: " + lengthOfFile + ", fileSize: " + fileSize);
            return lengthOfFile == fileSize;
        } catch (IOException e) {
            e.printStackTrace();
        }

        return false;
    }

    public interface OnPostExecuteListener {
        void onPostExecuteListener(boolean isSameSize);
    }

    private OnPostExecuteListener onPostExecuteListener;

    public void setOnPostExecuteListener(OnPostExecuteListener onPostExecuteListener) {
        this.onPostExecuteListener = onPostExecuteListener;
    }

    @Override
    protected void onPostExecute(Boolean isSameSize) {
        if (onPostExecuteListener != null) {
            onPostExecuteListener.onPostExecuteListener(isSameSize);
        }
    }
}

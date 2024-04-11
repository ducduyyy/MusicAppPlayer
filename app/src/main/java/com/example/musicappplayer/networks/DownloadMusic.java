package com.example.musicappplayer.networks;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import com.example.musicappplayer.events.UpdateDowloadedSongs;
import com.example.musicappplayer.model.Songs;
import com.example.musicappplayer.untils.DownloadMusicManager;

import org.greenrobot.eventbus.EventBus;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;


public class DownloadMusic extends AsyncTask<Songs, Integer, Songs>{

    private static final String LOG_TAG = "DowmloadMusic";
    private Context context;

    private DownloadMusicManager downloadMusicManager;


    public DownloadMusic(Context context) {
        this.context = context;
        this.downloadMusicManager = downloadMusicManager;
    }

    @Override
    protected Songs doInBackground(Songs... songs) {
        int count;
        try{
            URL url = new URL(songs[0].getLinkBaiHat());
            URLConnection urlConnection = url.openConnection();
            urlConnection.connect();

            DownloadMusicManager downloadMusicManager = new DownloadMusicManager(context);
            InputStream inputStream = new BufferedInputStream(url.openStream(), 9999);
            OutputStream outputStream = new FileOutputStream(downloadMusicManager.getFile(songs[0]));
            byte data[] = new byte[1024];
            while ((count = inputStream.read(data)) != -1) {
                // writing data to file
                outputStream.write(data, 0, count);
            }

            // flushing output
            outputStream.flush();

            // closing streams
            outputStream.close();
            inputStream.close();
        }catch (Exception e){
            e.printStackTrace();
        }
        return songs[0];
    }

    @Override
    protected void onPreExecute() {
        Toast.makeText(context, "Downloading...", Toast.LENGTH_SHORT).show();

    }

    @Override
    protected void onPostExecute(Songs songs) {
        Toast.makeText(context, "Downloading \"" + songs.getTenBaiHat() + "\"", Toast.LENGTH_SHORT).show();

        EventBus.getDefault().post(new UpdateDowloadedSongs());
        File file = downloadMusicManager.getFile(songs);
        Log.d(LOG_TAG, "file size: " + file.length());
    }
}
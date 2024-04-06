package com.example.musicappplayer.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.StrictMode;
import android.view.View;
import android.widget.ImageButton;
import android.widget.SeekBar;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.viewpager.widget.ViewPager;

import com.example.musicappplayer.R;
import com.example.musicappplayer.adapter.ViewPagerPlaySong;
import com.example.musicappplayer.fragment.FragmentDiscography;
import com.example.musicappplayer.fragment.FragmentPlayListSong;
import com.example.musicappplayer.model.Songs;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Random;

public class PlayNhacActivity extends AppCompatActivity {

    Toolbar toolbarplay;
    TextView txttimesong, txtTotaltimesong, txtTencasi, txtTenbaihat;
    SeekBar sktime;
    ImageButton imgplay, imgrepeat, imgnext, imgpre, imgrandom;
    ViewPager viewPagerplay;
    public static ArrayList<Songs> songArrayList = new ArrayList<>();
    public static ViewPagerPlaySong viewPagerPlaySong;
    FragmentDiscography fragmentDiscography;
    FragmentPlayListSong fragmentPlayListSong;
    MediaPlayer mediaPlayer;
    int position = 0;
    boolean repeat = false, checkrandom = false, next = false;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_play_nhac);
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.mainPlay), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        getDataFromIntent();
        mapping();
        updateTime();
        eventClick();

    }

    private void eventClick() {
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (viewPagerPlaySong.getItem(1) != null) {
                    if (songArrayList.size() > 0) {
                        fragmentDiscography.PlayNhac(songArrayList.get(0).getHinhBaiHat());
                        txtTencasi.setText(songArrayList.get(0).getCasi());
                        handler.removeCallbacks(this);
                    } else {
                        handler.postDelayed(this, 300);
                    }
                }
            }
        }, 500);
        imgplay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mediaPlayer.isPlaying()) {
                    mediaPlayer.pause();
                    imgplay.setImageResource(R.drawable.ic_play_arrow_white_64dp);
                } else {
                    mediaPlayer.start();
                    imgplay.setImageResource(R.drawable.ic_pause_white_64dp);
                }
            }
        });
        imgrepeat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (repeat == false) {
                    if (checkrandom == true) {
                        checkrandom = false;
                        imgrandom.setImageResource(R.drawable.ic_shuffle);
                        imgrepeat.setImageResource(R.drawable.ic_repeat_white_circle);
                    }
                    imgrepeat.setImageResource(R.drawable.ic_repeat_white_circle);

                    repeat = true;
                } else {
                    imgrepeat.setImageResource(R.drawable.ic_repeat);
                    repeat = false;
                }
            }
        });
        imgrandom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checkrandom == false) {
                    if (repeat == true) {
                        repeat = false;
                        imgrandom.setImageResource(R.drawable.ic_app_shortcut_shuffle_all);
                        imgrepeat.setImageResource(R.drawable.ic_repeat);
                    }
                    imgrandom.setImageResource(R.drawable.ic_app_shortcut_shuffle_all);
                    checkrandom = true;
                } else {
                    imgrandom.setImageResource(R.drawable.ic_shuffle);
                    checkrandom = false;
                }
            }
        });
        sktime.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

                mediaPlayer.seekTo(seekBar.getProgress());
            }
        });
        imgnext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (songArrayList.size() > 0) {
                    if (mediaPlayer.isPlaying() || mediaPlayer != null) {
                        mediaPlayer.stop();
                        mediaPlayer.release();
                        mediaPlayer = null;
                    }
                    if (position < (songArrayList.size())) {
                        imgplay.setImageResource(R.drawable.ic_pause_white_64dp);
                        position++;
                        if (repeat == true) {
                            if (position == 0) {
                                position = songArrayList.size();
                            }
                            position -= 1;
                        }
                        if (checkrandom == true) {
                            Random random = new Random();
                            int index = random.nextInt(songArrayList.size());
                            if (index == position) {
                                position = index - 1;
                            }
                            position = index;
                        }
                        if (position > (songArrayList.size() - 1)) {
                            position = 0;
                        }
                        new PlayMp3().execute(songArrayList.get(position).getLinkBaiHat());
                        fragmentDiscography.PlayNhac(songArrayList.get(position).getHinhBaiHat());
                        txtTencasi.setText(songArrayList.get(position).getCasi());
                        txtTenbaihat.setText(songArrayList.get(position).getTenBaiHat());
                        updateTime();
                    }
                }
                imgrepeat.setClickable(false);
                imgnext.setClickable(false);
                Handler handler1 = new Handler();
                handler1.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        imgrepeat.setClickable(true);
                        imgnext.setClickable(true);
                    }
                }, 5000);
            }
        });
        imgpre.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (songArrayList.size() > 0) {
                    if (mediaPlayer.isPlaying() || mediaPlayer != null) {
                        mediaPlayer.stop();
                        mediaPlayer.release();
                        mediaPlayer = null;
                    }
                    if (position < (songArrayList.size())) {
                        imgplay.setImageResource(R.drawable.ic_pause_white_64dp);
                        position--;
                        if (position < 0) {
                            position = songArrayList.size() - 1;
                        }
                        if (repeat == true) {

                            position += 1;
                        }
                        if (checkrandom == true) {
                            Random random = new Random();
                            int index = random.nextInt(songArrayList.size());
                            if (index == position) {
                                position = index - 1;
                            }
                            position = index;
                        }
                        new PlayMp3().execute(songArrayList.get(position).getLinkBaiHat());
                        fragmentDiscography.PlayNhac(songArrayList.get(position).getHinhBaiHat());
                        txtTencasi.setText(songArrayList.get(position).getCasi());
                        txtTenbaihat.setText(songArrayList.get(position).getTenBaiHat());
                        updateTime();
                    }
                }
                imgrepeat.setClickable(false);
                imgpre.setClickable(false);
                Handler handler1 = new Handler();
                handler1.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        imgrepeat.setClickable(true);
                        imgpre.setClickable(true);
                    }
                }, 5000);
            }
        });
    }

    private void getDataFromIntent() {
        Intent intent = getIntent();
        if (intent != null) {
            if (intent.hasExtra("song")) {
                Bundle bundle = intent.getExtras();
                Songs songs = bundle.getParcelable("song");
                songArrayList.add(songs);
            }
            if (intent.hasExtra("listsong")) {
                Bundle bundle = intent.getExtras();
                ArrayList<Songs> listsong = bundle.getParcelableArrayList("listsong");
                songArrayList = listsong;

            }
        }

    }

    private void mapping() {
        toolbarplay = findViewById(R.id.toolbarplaynhac);
        txttimesong = findViewById(R.id.textViewruntime);
        txtTotaltimesong = findViewById(R.id.textViewtimetotal);
        txtTencasi = findViewById(R.id.textViewtencasiplaynhac);
        txtTenbaihat = findViewById(R.id.textViewtenbaihatplaynhac);
        sktime = findViewById(R.id.seekBartime);
        imgplay = findViewById(R.id.imageButtonplaypause);
        imgrandom = findViewById(R.id.imageButtonrandom);
        imgpre = findViewById(R.id.imageButtonpreview);
        imgnext = findViewById(R.id.imageButtonnext);
        imgrepeat = findViewById(R.id.imageButtonlap);
        viewPagerplay = findViewById(R.id.viewPagerdianhac);
        setSupportActionBar(toolbarplay);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbarplay.setNavigationOnClickListener(v -> {
            finish();
            mediaPlayer.stop();
            songArrayList.clear();
        });
        toolbarplay.setTitleTextColor(Color.BLACK);
        fragmentDiscography = new FragmentDiscography();
        fragmentPlayListSong = new FragmentPlayListSong();
        viewPagerPlaySong = new ViewPagerPlaySong(getSupportFragmentManager());
        viewPagerPlaySong.addFragment(fragmentPlayListSong);
        viewPagerPlaySong.addFragment(fragmentDiscography);
        viewPagerplay.setAdapter(viewPagerPlaySong);
        fragmentDiscography = (FragmentDiscography) viewPagerPlaySong.getItem(1);
        if (songArrayList.size() > 0) {
            txtTencasi.setText(songArrayList.get(position).getCasi());
            txtTenbaihat.setText(songArrayList.get(position).getTenBaiHat());
            new PlayMp3().execute(songArrayList.get(0).getLinkBaiHat());
            imgplay.setImageResource(R.drawable.ic_pause_white_64dp);
        }
    }

    class PlayMp3 extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... strings) {
            return strings[0];
        }

        @Override
        protected void onPostExecute(String song) {
            super.onPostExecute(song);
            try {
                mediaPlayer = new MediaPlayer();
                mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
                mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                    @Override
                    public void onCompletion(MediaPlayer mp) {

                        mediaPlayer.stop();
                        mediaPlayer.reset();
                    }
                });
                mediaPlayer.setDataSource(song);
                mediaPlayer.prepare();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            mediaPlayer.start();
            TimeSong();
        }
    }

    private void TimeSong() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("mm:ss");
        txtTotaltimesong.setText(simpleDateFormat.format(mediaPlayer.getDuration()));
        sktime.setMax(mediaPlayer.getDuration());
    }

    private void updateTime() {
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (mediaPlayer != null) {
                    sktime.setProgress(mediaPlayer.getCurrentPosition());
                    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("mm:ss");
                    txttimesong.setText(simpleDateFormat.format(mediaPlayer.getCurrentPosition()));
                    handler.postDelayed(this, 300);
                    mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                        @Override
                        public void onCompletion(MediaPlayer mp) {
                            next = true;
                            try {
                                Thread.sleep(1000);
                            } catch (InterruptedException e) {
                                throw new RuntimeException(e);
                            }
                        }
                    });
                }
            }
        }, 300);
        Handler handler1 = new Handler();
        handler1.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (next == true) {
                    if (position < (songArrayList.size())) {
                        imgplay.setImageResource(R.drawable.ic_pause_white_64dp);
                        position++;
                        if (repeat == true) {
                            if (position == 0) {
                                position = songArrayList.size();
                            }
                            position -= 1;
                        }
                        if (checkrandom == true) {
                            Random random = new Random();
                            int index = random.nextInt(songArrayList.size());
                            if (index == position) {
                                position = index - 1;
                            }
                            position = index;
                        }
                        if (position > (songArrayList.size() - 1)) {
                            position = 0;
                        }
                        new PlayMp3().execute(songArrayList.get(position).getLinkBaiHat());
                        fragmentDiscography.PlayNhac(songArrayList.get(position).getHinhBaiHat());
                        getSupportActionBar().setTitle(songArrayList.get(position).getTenBaiHat());
                    }
                    imgrepeat.setClickable(false);
                    imgnext.setClickable(false);
                    Handler handler1 = new Handler();
                    handler1.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            imgrepeat.setClickable(true);
                            imgnext.setClickable(true);
                        }
                    }, 5000);
                    next = false;
                } else {
                    handler1.postDelayed(this, 1000);
                }
            }
        }, 1000);
    }

}
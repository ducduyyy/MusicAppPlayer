package com.example.musicappplayer.activity;

import static androidx.compose.ui.tooling.data.SlotTreeKt.getPosition;

import android.annotation.SuppressLint;
import android.app.Activity;

import android.content.Context;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;

import android.graphics.drawable.BitmapDrawable;

import android.graphics.drawable.Drawable;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.StrictMode;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.RelativeLayout;

import android.widget.ImageView;

import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.viewpager.widget.ViewPager;

import com.example.musicappplayer.R;
import com.example.musicappplayer.adapter.ViewPagerPlaySong;
import com.example.musicappplayer.fragment.FragmentDiscography;
import com.example.musicappplayer.fragment.FragmentPlayListSong;
import com.example.musicappplayer.model.Songs;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import com.example.musicappplayer.service.APIService;
import com.example.musicappplayer.service.Dataservice;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Random;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PlayNhacActivity extends AppCompatActivity {


    Toolbar toolbarplay;
    TextView txttimesong, txtTotaltimesong, txtTencasi, txtTenbaihat;
    RelativeLayout relativeLayout;
    SeekBar sktime;
    ImageButton imgplay, imgrepeat, imgnext, imgpre, imgrandom;
    ViewPager viewPagerplay;
    ImageView imgtimplaynhac,imgtimborder;
    public static ArrayList<Songs> songArrayList = new ArrayList<>();
    ViewPagerPlaySong viewPagerPlaySong;
    FragmentDiscography fragmentDiscography;
    FragmentPlayListSong fragmentPlayListSong;
    static MediaPlayer mediaPlayer;
    int position = 0;
    boolean repeat = false, checkrandom = false, next = false;

    static PlayMp3 playMp3;

    Context context = this;
    FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
    FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();

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
        if (mediaPlayer!=null){
            mediaPlayer.stop();
            mediaPlayer.release();
            mediaPlayer=null;

        }
        getDataFromIntent();
        mapping();
        updateTime();
        eventClick();
    }

    private void eventClick() {
        setSupportActionBar(toolbarplay);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbarplay.setNavigationOnClickListener(v -> {
            finish();
        });
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
                        handler.postDelayed(this, 500);
                    }
                }
            }

        }, 100);
        imgtimplaynhac.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("test", "onClick: "+imgtimplaynhac.getDrawable().getConstantState().toString());
                Log.d("test", "onClick: "+context.getDrawable(R.drawable.ic_favorite_border).getConstantState().toString());
                if (firebaseUser!=null){
                    if(imgtimplaynhac.getDrawable().getConstantState().equals(context.getDrawable(R.drawable.ic_favorite_border).getConstantState())){
                        imgtimplaynhac.setImageResource(R.drawable.icon_love_red);
                        Dataservice dataservice = APIService.getService();
                        Call<String> callback = dataservice.UpdateLuotThich("1", songArrayList.get(0).getIdBaiHat());
                        callback.enqueue(new Callback<String>() {
                            @Override
                            public void onResponse(Call<String> call, Response<String> response) {
                                String ketqua = response.body();
                                if(ketqua.equals("Success")){
                                    Toast.makeText(context, "Đã thích", Toast.LENGTH_SHORT).show();
                                }else{
                                    Toast.makeText(context, "Lỗi!!", Toast.LENGTH_SHORT).show();
                                }
                            }

                            @Override
                            public void onFailure(Call<String> call, Throwable t) {

                            }
                        });
                    }else {
                        imgtimplaynhac.setImageResource(R.drawable.ic_favorite_border);
                        Dataservice dataservice = APIService.getService();
                        Call<String> callback = dataservice.UpdateLuotThich("-1", songArrayList.get(0).getIdBaiHat());
                        callback.enqueue(new Callback<String>() {
                            @Override
                            public void onResponse(Call<String> call, Response<String> response) {
                                String ketqua = response.body();
                                if(ketqua.equals("Success")){
                                    Toast.makeText(context, "Đã bỏ thích", Toast.LENGTH_SHORT).show();
                                }else{
                                    Toast.makeText(context, "Lỗi!!", Toast.LENGTH_SHORT).show();
                                }
                            }

                            @Override
                            public void onFailure(Call<String> call, Throwable t) {

                            }
                        });
                    }
                }else {
                    // Người dùng chưa đăng nhập, hiển thị thông báo yêu cầu đăng nhập
                    AlertDialog.Builder builder = new AlertDialog.Builder(context);
                    builder.setTitle("Thông báo")
                            .setMessage("Bạn chưa đăng nhập. Vui lòng đăng nhập để tiếp tục.")
                            .setPositiveButton("Đăng nhập", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    // Chuyển hướng người dùng đến màn hình đăng nhập
                                    startActivity(new Intent(context, SignInActivity.class));
                                }
                            })
                            .setNegativeButton("Thoát", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    // Xử lý khi người dùng chọn thoát
                                    dialog.dismiss();

                                }
                            });
                    AlertDialog alertDialog = builder.create();
                    alertDialog.show();
                }



            }
        });
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
        imgrepeat.setOnClickListener(v -> {
            if (!repeat) {
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
        });
        imgrandom.setOnClickListener(v -> {
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
                }, 2000);
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
                }, 2000);
            }
        });
    }


    private void getDataFromIntent() {
        Intent intent = getIntent();
        if (intent != null) {
            if (intent.hasExtra("song")) {
                Bundle bundle = intent.getExtras();
                Songs songs = bundle.getParcelable("song");
                if (songArrayList!=null){
                    songArrayList.clear();
                    songArrayList.add(songs);
                }
            }
            if (intent.hasExtra("listsong")) {
                Bundle bundle = intent.getExtras();
                ArrayList<Songs> listsong = bundle.getParcelableArrayList("listsong");
                if (songArrayList!=null){
                    songArrayList.clear();
                    songArrayList = listsong;
                }
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
        imgtimplaynhac = findViewById(R.id.imageViewtimplaynhac);
        imgtimborder = findViewById(R.id.icon_tim_border);
        imgrepeat = findViewById(R.id.imageButtonlap);
        viewPagerplay = findViewById(R.id.viewPagerdianhac);
        relativeLayout = findViewById(R.id.mainPlay);
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
            imgplay.setImageResource(R.drawable.ic_pause_white_64dp);
            new PlayMp3().execute(songArrayList.get(0).getLinkBaiHat());
//            new Thread(() -> {
//                try {
//                    mediaPlayer = new MediaPlayer();
//                    mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
//                    mediaPlayer.setOnCompletionListener(mp -> {
//                        mp.stop();
//                        mp.reset();
//                    });
//                    mediaPlayer.setDataSource(songArrayList.get(0).getLinkBaiHat());
//                    mediaPlayer.prepare();
//                } catch (IOException e) {
//                    throw new RuntimeException(e);
//                }
//                mediaPlayer.start();
//                runOnUiThread(new Runnable() {
//                    @Override
//                    public void run() {
//                        TimeSong();
//                        updateTime();
//                    }
//                });
//
//
//            }).start();
        }
    }


    public class PlayMp3 extends AsyncTask<String, Void, String> {

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
                mediaPlayer.setOnCompletionListener(mp -> {
                    mp.stop();
                    mp.reset();
                });
                mediaPlayer.setDataSource(song);
                mediaPlayer.prepare();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            mediaPlayer.start();
            TimeSong();
            updateTime();

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

    @Override
    protected void onResume() {
        super.onResume();
        firebaseUser = firebaseAuth.getCurrentUser();
    }
}
package com.example.musicappplayer.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.musicappplayer.R;
import com.example.musicappplayer.fragment.FragmentInfoUser;
import com.example.musicappplayer.model.ReadWriteUserDetails;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class UserProfileActivity extends AppCompatActivity {

    TextView textViewWelcome, textViewFullName,textViewEmail, textViewDoB, textViewGender, textViewMobile;
    ProgressBar progressBar;
    String fullname, email, gender,doB,mobile;
    ImageView imageView;
    FirebaseAuth firebaseAuth;
    Toolbar toolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_user_profile);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        anhxa();
        firebaseAuth = FirebaseAuth.getInstance();
        FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
        if (firebaseUser == null){
            Toast.makeText(UserProfileActivity.this, "Something wnet wrong! User's details are not available at the moment",
                    Toast.LENGTH_LONG).show();
        }else {
            progressBar.setVisibility(View.VISIBLE);
            showUserProfile(firebaseUser);
        }
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Profile");
        toolbar.setNavigationOnClickListener(v->finish());

    }


    private void showUserProfile(FirebaseUser firebaseUser) {
        String userID = firebaseUser.getUid();

        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("SignUp");
        databaseReference.child(userID).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                ReadWriteUserDetails readWriteUserDetails = snapshot.getValue(ReadWriteUserDetails.class);
                if (readWriteUserDetails !=null){
                    fullname = firebaseUser.getDisplayName();
                    email = firebaseUser.getEmail();
                    doB = readWriteUserDetails.doB;
                    gender = readWriteUserDetails.gender;
                    mobile = readWriteUserDetails.PhoneNumber;

                    textViewWelcome.setText("Welcome, "+fullname+"!");
                    textViewFullName.setText(fullname);
                    textViewEmail.setText(email);
                    textViewDoB.setText(doB);
                    textViewGender.setText(gender);
                    textViewMobile.setText(mobile);
                }
                progressBar.setVisibility(View.GONE);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(UserProfileActivity.this, "Something wnet wrong!",
                        Toast.LENGTH_LONG).show();
                progressBar.setVisibility(View.GONE);
            }
        });
    }

    private void anhxa() {
        textViewWelcome = findViewById(R.id.textview_show_welcom);
        textViewFullName = findViewById(R.id.textview_fullname);
        textViewEmail = findViewById(R.id.textview_email);
        textViewDoB = findViewById(R.id.textview_dob);
        textViewGender = findViewById(R.id.textview_gender);
        textViewMobile = findViewById(R.id.textview_mobile);
        progressBar = findViewById(R.id.progressBarProfile);
        toolbar = findViewById(R.id.toolbar_user_profile);

    }
}
package com.example.musicappplayer.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.musicappplayer.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class ChangePasswordActivity extends AppCompatActivity {
    FirebaseAuth firebaseAuth;
    EditText editTextPwdCurr, editTextPwdNew, editTextPwdConfirmNew;
    TextView textViewAuthenticaed;
    Button buttonChangePwd, buttonReAuthenticate;
    ProgressBar progressBar;
    Toolbar toolbar;
    String userPwdCurr;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_change_password);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        mapping();
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Change Password");
        toolbar.setNavigationOnClickListener(v->finish());

        editTextPwdNew.setEnabled(false);
        editTextPwdConfirmNew.setEnabled(false);
        buttonChangePwd.setEnabled(false);
        firebaseAuth = FirebaseAuth.getInstance();
        FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
        
        if (firebaseUser.equals("")){
            Toast.makeText(ChangePasswordActivity.this, "Something went wrong! User's details not available", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(ChangePasswordActivity.this, UserProfileActivity.class);
            startActivity(intent);
            finish();
        }else {
            reAuthenticateUser(firebaseUser);
        }
    }

    private void reAuthenticateUser(FirebaseUser firebaseUser) {
        buttonReAuthenticate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                userPwdCurr = editTextPwdCurr.getText().toString();
                if (TextUtils.isEmpty(userPwdCurr)){
                    Toast.makeText(ChangePasswordActivity.this, "Password is needed", Toast.LENGTH_SHORT).show();
                    editTextPwdCurr.setError("Please enter your current password to authenticate");
                    editTextPwdCurr.requestFocus();
                }else {
                    progressBar.setVisibility(View.VISIBLE);

                    AuthCredential credential = EmailAuthProvider.getCredential(firebaseUser.getEmail(),userPwdCurr);
                    firebaseUser.reauthenticate(credential).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()){
                                progressBar.setVisibility(View.GONE);


                                editTextPwdCurr.setEnabled(false);
                                editTextPwdNew.setEnabled(true);
                                editTextPwdConfirmNew.setEnabled(true);

                                buttonReAuthenticate.setEnabled(false);
                                buttonChangePwd.setEnabled(true);

                                textViewAuthenticaed.setText("Xác nhận thành công"+"Bạn có thể đổi mật khẩu bây giờ!");
                                Toast.makeText(ChangePasswordActivity.this, "Password has been verified."+"Change password now!", Toast.LENGTH_SHORT).show();

                                buttonReAuthenticate.setBackgroundResource(R.drawable.custom_button_change_pass);
                                buttonChangePwd.setBackgroundResource(R.drawable.btn_background);
                                buttonChangePwd.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        changePwd(firebaseUser);
                                    }
                                });
                            }else {
                                try {
                                    throw task.getException();

                                }catch (Exception e){
                                    Toast.makeText(ChangePasswordActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                                }
                            }
                            progressBar.setVisibility(View.GONE);
                        }
                    });
                }
            }
        });
    }

    private void changePwd(FirebaseUser firebaseUser) {

        String userPwdNew = editTextPwdNew.getText().toString();
        String userPwdConfirmNew = editTextPwdConfirmNew.getText().toString();

        if (TextUtils.isEmpty(userPwdNew)){
            Toast.makeText(ChangePasswordActivity.this, "New Password id needed", Toast.LENGTH_SHORT).show();
            editTextPwdNew.setError("Please enter your new password");
            editTextPwdNew.requestFocus();
        } else if (TextUtils.isEmpty(userPwdConfirmNew)) {
            Toast.makeText(ChangePasswordActivity.this, "Please confirm your new password ", Toast.LENGTH_SHORT).show();
            editTextPwdConfirmNew.setError("Please re-enter your new password");
            editTextPwdConfirmNew.requestFocus();

        } else if (!userPwdNew.matches(userPwdConfirmNew)) {
            Toast.makeText(ChangePasswordActivity.this, "Password did not match ", Toast.LENGTH_SHORT).show();
            editTextPwdConfirmNew.setError("Please re-enter your new password");
            editTextPwdConfirmNew.requestFocus();

        } else if (userPwdCurr.matches(userPwdNew)) {
            Toast.makeText(ChangePasswordActivity.this, "Mật khẩu mới không được giống với mật khẩu cũ ", Toast.LENGTH_SHORT).show();
            editTextPwdNew.setError("Please enter your new password");
            editTextPwdNew.requestFocus();
        }else {
            progressBar.setVisibility(View.VISIBLE);
            firebaseUser.updatePassword(userPwdNew).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if (task.isSuccessful()){
                        Toast.makeText(ChangePasswordActivity.this, "Password has been changed", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(ChangePasswordActivity.this, UserProfileActivity.class);
                        startActivity(intent);
                        finish();
                    }else {
                        try {
                            throw task.getException();
                        }catch (Exception e){
                            Toast.makeText(ChangePasswordActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                    progressBar.setVisibility(View.GONE);
                }
            });
        }
    }

    private void mapping() {
        toolbar = findViewById(R.id.toolbar_change_password);
        editTextPwdNew = findViewById(R.id.editText_change_pwd_new);
        textViewAuthenticaed = findViewById(R.id.textView_change_pwd_authenticated);
        editTextPwdCurr = findViewById(R.id.editText_change_pwd_current);
        editTextPwdConfirmNew = findViewById(R.id.editText_confirm_pwd_new);
        progressBar = findViewById(R.id.progressBarChangePassword);
        buttonReAuthenticate = findViewById(R.id.button_change_pwd_authenticate);
        buttonChangePwd = findViewById(R.id.button_change_pwd);
    }
    //create actionBar Menu


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.common_menu,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.menu_refresh){
            startActivity(getIntent());
            finish();
            overridePendingTransition(0,0);

        } else if (id==R.id.menu_update_profile) {
            Intent intent = new Intent(ChangePasswordActivity.this,UpdateProfileActivity.class);
            startActivity(intent);
            finish();
        } else if (id==R.id.menu_change_password) {
            Intent intent = new Intent(ChangePasswordActivity.this,ChangePasswordActivity.class);
            startActivity(intent);
        } else if (id == R.id.menu_logout) {
            firebaseAuth.signOut();
            Toast.makeText(ChangePasswordActivity.this, "Logged Out", Toast.LENGTH_LONG).show();
            finish();
        }else {
            Toast.makeText(ChangePasswordActivity.this, "Something went wrong!", Toast.LENGTH_LONG).show();
        }
        return super.onOptionsItemSelected(item);
    }
}
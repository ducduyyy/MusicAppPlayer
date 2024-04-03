package com.example.musicappplayer.activity;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.musicappplayer.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class SignInActivity extends AppCompatActivity {
    EditText editTextEmail, editTextPassword;

    Button signIn;
    TextView signUp;
    ProgressBar progressBar;
    FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_sign_in);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        editTextEmail = findViewById(R.id.email);
        editTextPassword = findViewById(R.id.password);
        signIn = findViewById(R.id.btn_sign_in);
        signUp = findViewById(R.id.sign_up);
        progressBar = findViewById(R.id.progressBarSignIn);
        editTextEmail.setText(get_email());
        editTextPassword.setText(get_pass());
        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SignInActivity.this, SignUpActivity.class);
                startActivity(intent);

            }
        });
        signIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email, password;
                email = String.valueOf(editTextEmail.getText());
                password = String.valueOf(editTextPassword.getText());

                if(TextUtils.isEmpty(email)){
                    Toast.makeText(SignInActivity.this, "Enter email", Toast.LENGTH_LONG).show();
                    editTextEmail.setError("Email is required");
                    editTextEmail.requestFocus();
                } else if (TextUtils.isEmpty(email)){
                    Toast.makeText(SignInActivity.this, "Please re-enter your email", Toast.LENGTH_LONG).show();
                    editTextEmail.setError("Valid email is required");
                    editTextEmail.requestFocus();
                } else if (TextUtils.isEmpty(password)) {
                    Toast.makeText(SignInActivity.this, "Enter password", Toast.LENGTH_LONG).show();
                    editTextPassword.setError("Password is required");
                    editTextPassword.requestFocus();
                }else {
                    progressBar.setVisibility(View.VISIBLE);
                    signInUser(email,password);
                }
            }
        });
    }

    private void signInUser(String email, String password) {
        firebaseAuth.signInWithEmailAndPassword(email,password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
//                            if (firebaseUser.isEmailVerified()){
                                Toast.makeText(SignInActivity.this, "Login successful", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(SignInActivity.this,UserProfileActivity.class);
                                startActivity(intent);
//                            }else {
//                                firebaseUser.sendEmailVerification();
//                                firebaseAuth.signOut();
//                                showAlerDialog();
//                            }
                        }
                        else {
                            Toast.makeText(SignInActivity.this, "Authentication failed", Toast.LENGTH_SHORT).show();
                        }
                        progressBar.setVisibility(View.GONE);
                    }
                });

    }

    private void showAlerDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(SignInActivity.this);
        builder.setTitle("Email Not Verified");
        builder.setMessage("Please verify your email now. You can not login without email verfication.");

        builder.setPositiveButton("Continue", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent intent = new Intent(Intent.ACTION_MAIN);
                intent.addCategory(Intent.CATEGORY_APP_EMAIL);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }
        });
        AlertDialog alertDialog = builder.create();

        alertDialog.show();
    }
    //check if user already logged in


//    @Override
//    protected void onStart() {
//        super.onStart();
//        if (firebaseAuth.getCurrentUser()!=null){
//            Toast.makeText(SignInActivity.this, "Already Logged In!", Toast.LENGTH_SHORT).show();
//            startActivity(new Intent(SignInActivity.this,UserProfileActivity.class));
//            finish();
//        }
//        else {
//            Toast.makeText(SignInActivity.this, "You can login now!", Toast.LENGTH_SHORT).show();
//        }
//    }

    public  String get_email(){
        Intent intent = getIntent();
        Bundle bundle = intent.getBundleExtra("user_info");
        if (bundle!=null){
           return bundle.getString("email");
        }else {
            return "";
        }
    }
    public String get_pass(){
        Intent intent = getIntent();
        Bundle bundle = intent.getBundleExtra("user_info");
        if (bundle!=null){
            return bundle.getString("pass");
        }else {
            return "";
        }
    }
}
package com.example.musicappplayer.activity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Rect;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.ScrollView;
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
import com.example.musicappplayer.model.ReadWriteUserDetails;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseAuthWeakPasswordException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Calendar;

public class SignUpActivity extends AppCompatActivity {
    EditText editTextEmail, editTextPassword, editTextConfirmPassword, editTextFullname, editTextPhone, editTextDoB;
    ProgressBar progressBar;
    RadioGroup radioGroupGender;
    RadioButton radioButtonGenderSelected;
    ScrollView scrollView;

    Button signUp;
    TextView signIn;
    private static final String TAG= "SignUpActivity";
    private DatePickerDialog picker;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_sign_up);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.scrollview_signup), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        mapping();
        signIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SignUpActivity.this, SignInActivity.class);
                startActivity(intent);

            }
        });
        //setting up DoB
        editTextDoB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar calendar = Calendar.getInstance();
                int day = calendar.get(calendar.DAY_OF_MONTH);
                int month = calendar.get(Calendar.MONTH);
                int year = calendar.get(Calendar.YEAR);

                //date picker dialog
                picker = new DatePickerDialog(SignUpActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        editTextDoB.setText(dayOfMonth+"/"+(month+1)+"/"+year);

                    }
                }, year, month,day);
                picker.show();
            }

        });
        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int selectedGenderId = radioGroupGender.getCheckedRadioButtonId();
                radioButtonGenderSelected = findViewById(selectedGenderId);

                String txtFullname = editTextFullname.getText().toString();
                String txtEmail = editTextEmail.getText().toString();
                String txtDoB = editTextDoB.getText().toString();
                String txtPhoneNumber = editTextPhone.getText().toString();
                String txtpassword = editTextPassword.getText().toString();
                String txtConfirmpassword = editTextConfirmPassword.getText().toString();
                String txtGender ;

                if (TextUtils.isEmpty(txtFullname)){
                    Toast.makeText(SignUpActivity.this, "Please enter your full name", Toast.LENGTH_LONG).show();
                    editTextFullname.setError("Full Name is required");
                    editTextFullname.requestFocus();
                } else if (TextUtils.isEmpty(txtEmail)) {
                    Toast.makeText(SignUpActivity.this, "Please enter your email", Toast.LENGTH_LONG).show();
                    editTextFullname.setError("Email is required");
                    editTextFullname.requestFocus();
                } else if (!Patterns.EMAIL_ADDRESS.matcher(txtEmail).matches()) {
                    Toast.makeText(SignUpActivity.this, "Please re-enter your email", Toast.LENGTH_LONG).show();
                    editTextFullname.setError("Valid email is required");
                    editTextFullname.requestFocus();
                } else if (TextUtils.isEmpty(txtDoB)) {
                    Toast.makeText(SignUpActivity.this, "Please your date of birth", Toast.LENGTH_LONG).show();
                    editTextFullname.setError("Date of Birth is required");
                    editTextFullname.requestFocus();
                } else if (radioGroupGender.getCheckedRadioButtonId() == -1) {
                    Toast.makeText(SignUpActivity.this, "Please select your gender", Toast.LENGTH_LONG).show();
                    radioButtonGenderSelected.setError("Gender is required");
                    radioButtonGenderSelected.requestFocus();
                } else if (TextUtils.isEmpty(txtPhoneNumber)) {
                    Toast.makeText(SignUpActivity.this, "Please enter your phone number", Toast.LENGTH_LONG).show();
                    editTextPhone.setError("Phone number is required");
                    editTextPhone.requestFocus();
                } else if (txtPhoneNumber.length()!=10) {
                    Toast.makeText(SignUpActivity.this, "Please re-enter your phone number", Toast.LENGTH_LONG).show();
                    editTextPhone.setError("Phone number should be 10 digits");
                    editTextPhone.requestFocus();
                } else if (TextUtils.isEmpty(txtpassword)) {
                    Toast.makeText(SignUpActivity.this, "Please enter your password", Toast.LENGTH_LONG).show();
                    editTextPhone.setError("Password is required");
                    editTextPhone.requestFocus();
                } else if (txtpassword.length()<6) {
                    Toast.makeText(SignUpActivity.this, "Please re-enter your password", Toast.LENGTH_LONG).show();
                    editTextPhone.setError("Password too weak");
                    editTextPhone.requestFocus();
                } else if (TextUtils.isEmpty(txtConfirmpassword)) {
                    Toast.makeText(SignUpActivity.this, "Please confirm your password", Toast.LENGTH_LONG).show();
                    editTextPhone.setError("Password Confirmation is required");
                    editTextPhone.requestFocus();
                } else if (!txtpassword.equals(txtConfirmpassword)) {
                    Toast.makeText(SignUpActivity.this, "Please same same password", Toast.LENGTH_LONG).show();
                    editTextPassword.setError("Password Confirm is required");
                    editTextPassword.requestFocus();
                    editTextPassword.clearComposingText();
                    editTextConfirmPassword.clearComposingText();
                }else {
                    txtGender = radioButtonGenderSelected.getText().toString();
                    progressBar.setVisibility(View.VISIBLE);
                    registerUser(txtFullname,txtEmail,txtDoB,txtGender,txtPhoneNumber,txtpassword);
                }

            }
        });
    }

    private void registerUser(String txtFullname, String txtEmail, String txtDoB, String txtGender, String txtPhoneNumber, String txtpassword) {
        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
        firebaseAuth.createUserWithEmailAndPassword(txtEmail,txtpassword).addOnCompleteListener(SignUpActivity.this,
                new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()){
                    FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
                    UserProfileChangeRequest userProfileChangeRequest = new UserProfileChangeRequest.Builder().setDisplayName(txtFullname).build();
                    firebaseUser.updateProfile(userProfileChangeRequest);
                    //đưa data vào firebaseRealtime
                    ReadWriteUserDetails writeUserDetails = new ReadWriteUserDetails(txtFullname,txtDoB,txtGender,txtPhoneNumber);
                    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("SignUp");
                    databaseReference.child(firebaseUser.getUid()).setValue(writeUserDetails).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()){
                                //send verification email
                                firebaseUser.sendEmailVerification();
                                Toast.makeText(SignUpActivity.this, "User registered successfully", Toast.LENGTH_LONG).show();
                                Intent intent = new Intent(SignUpActivity.this,SignInActivity.class);
                                Bundle bundle = new Bundle();
                                bundle.putString("email",txtEmail);
                                bundle.putString("pass",txtpassword);
                                intent.putExtra("user_info",bundle);
                                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
                                startActivity(intent);
                                finish();
                            }else {
                                Toast.makeText(SignUpActivity.this, "User registered failed", Toast.LENGTH_LONG).show();
                                progressBar.setVisibility(View.GONE);
                            }
                        }
                    });

                }else {
                    try {
                        throw task.getException();

                    }catch (FirebaseAuthWeakPasswordException e){
                        editTextPassword.setError("Mật khẩu của bạn quá yếu. Vui lòng sử dụng kết hợp bảng chữ cái, số và ký tự đặc biệt");
                        editTextPassword.requestFocus();
                    }catch (FirebaseAuthInvalidCredentialsException e){
                        editTextPassword.setError("Email của bạn không hợp lệ hoặc đã được sử dụng. Vui lòng nhập lại");
                        editTextPassword.requestFocus();
                    }catch (FirebaseAuthUserCollisionException e){
                        editTextPassword.setError("Người dùng đã đăng ký với email này. Sử dụng email khác");
                        editTextPassword.requestFocus();
                    }catch (Exception e){
                        Log.e(TAG,e.getMessage());
                        Toast.makeText(SignUpActivity.this, e.getMessage(), Toast.LENGTH_LONG).show();
                    }
                    progressBar.setVisibility(View.GONE);
                }
            }
        });
    }

    private void mapping() {
        editTextEmail = findViewById(R.id.edt_signUp_email);
        editTextFullname = findViewById(R.id.edt_signUp_fullname);
        editTextDoB = findViewById(R.id.edt_signUp_birthday);
        editTextPhone = findViewById(R.id.edt_signUp_mobile);
        editTextPassword = findViewById(R.id.edt_signUp_password);
        editTextConfirmPassword = findViewById(R.id.edt_signUp_confirm_password);
        signIn = findViewById(R.id.sign_in);
        signUp = findViewById(R.id.btn_sign_up);
        progressBar = findViewById(R.id.progressBarSignUp);
        //radioButton
        radioGroupGender = findViewById(R.id.radio_group_gender);
        radioGroupGender.clearCheck();
        scrollView = findViewById(R.id.scrollview_signup);


    }

}
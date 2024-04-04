package com.example.musicappplayer.fragment;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.musicappplayer.R;
import com.example.musicappplayer.activity.SignInActivity;
import com.example.musicappplayer.activity.UserProfileActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class FragmentInfoUser extends Fragment {
    View view;
    LinearLayout linearLayout_welcome, linearLayout_dangxuat;
    TextView txtname,txtdangxuat;

    FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
    FirebaseUser firebaseUser;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_info_user,container,false);
        linearLayout_welcome = view.findViewById(R.id.linear_welcome);
        txtname = view.findViewById(R.id.textview_name);
        txtdangxuat = view.findViewById(R.id.textview_dangxuat);
        Log.d("create", "onCreateView ");
        firebaseUser = firebaseAuth.getCurrentUser();
        linearLayout_welcome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (firebaseUser!=null) {
                    Intent intent = new Intent(getActivity(), UserProfileActivity.class);
                    startActivity(intent);
                }else {
                    // Người dùng chưa đăng nhập, hiển thị thông báo yêu cầu đăng nhập
                    AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                    builder.setTitle("Thông báo")
                            .setMessage("Bạn chưa đăng nhập. Vui lòng đăng nhập để tiếp tục.")
                            .setPositiveButton("Đăng nhập", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    // Chuyển hướng người dùng đến màn hình đăng nhập
                                    startActivity(new Intent(getActivity(), SignInActivity.class));
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
        txtdangxuat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setTitle("Thông báo")
                        .setMessage("Bạn có chắc chắn muốn đăng xuất?")
                        .setPositiveButton("Đăng xuất", (dialog, which) -> {
                            firebaseAuth.signOut();
                            onResume();
                            Toast.makeText(getActivity(), "Đã đăng xuất!", Toast.LENGTH_SHORT).show();
                            dialog.dismiss();

                        })
                        .setNegativeButton("Hủy", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        });
                AlertDialog alertDialog = builder.create();
                alertDialog.show();
            }
        });
        return view;

    }
    @Override
    public void onResume() {
        super.onResume();
        Log.d("create", "onResume ");
        firebaseUser = firebaseAuth.getCurrentUser();
        if (firebaseUser !=null){
            String name = firebaseUser.getDisplayName();
            txtname.setText(name);
        }
        else {
            txtname.setText("Đăng nhập/Đăng ký");
        }

    }

}

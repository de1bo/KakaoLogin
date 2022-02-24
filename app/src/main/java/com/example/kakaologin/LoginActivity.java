package com.example.kakaologin;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.kakao.usermgmt.UserManagement;
import com.kakao.usermgmt.callback.LogoutResponseCallback;

public class LoginActivity extends AppCompatActivity
{
    private String strNick, strProfileImg, strEmail;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // onSuccess 에서 보낸 데이터 받는 부분
        Intent intent = getIntent();
        strNick = intent.getStringExtra("name");
        strProfileImg = intent.getStringExtra("profileImg");
        strEmail = intent.getStringExtra("email");

        TextView tv_nick = findViewById(R.id.tv_name);
        TextView tv_email = findViewById(R.id.tv_email);
        ImageView iv_profile = findViewById(R.id.iv_profileimage);


        tv_nick.setText(strNick);
        tv_email.setText(strEmail);
        Glide.with(this).load(strProfileImg).into(iv_profile);

        // 로그아웃
        findViewById(R.id.btn_logout).setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                UserManagement.getInstance().requestLogout(new LogoutResponseCallback()
                {
                    @Override
                    public void onCompleteLogout()
                    {
                        // 로그아웃 성공시 수행하는 지점
                        finish(); // 현재 액티비티 종료
                    }
                });
            }
        });


    }
}
package com.inanhu.wenjiaosuo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SplashActivity extends AppCompatActivity {

    @BindView(R.id.ivAvatar)
    ImageView ivAvatar;
    @BindView(R.id.tvMessage)
    TextView tvMessage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        ButterKnife.bind(this);
        tvMessage.setText("测试");
    }

    @OnClick(R.id.btnClick)
    public void onClick() {
        Toast.makeText(this, tvMessage.getText().toString(), Toast.LENGTH_SHORT).show();
    }
}

package com.mubly.xinma.login.view;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;

import com.mubly.xinma.home.MainActivity;
import com.mubly.xinma.utils.AppConfig;
import com.mubly.xinma.utils.CommUtil;
import com.tbruyelle.rxpermissions2.RxPermissions;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import io.reactivex.functions.Consumer;


public class StartActivity extends AppCompatActivity {
    RxPermissions rxPermissions;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        rxPermissions = new RxPermissions(this);
        rxPermissions.request(Manifest.permission.CAMERA, Manifest.permission.READ_PHONE_STATE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE).subscribe(new Consumer<Boolean>() {
            @Override
            public void accept(Boolean permission) throws Exception {
                if (permission) {
                    startMainTo();
                } else {
                    CommUtil.ToastU.showToast("请打开所有的权限");
                    finish();
                }
            }
        });

    }

    private void startMainTo() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                startActivity(new Intent(StartActivity.this, TextUtils.isEmpty(AppConfig.token.get()) ? LoginActivity.class : MainActivity.class));
                finish();
            }
        }, 600);
    }
}

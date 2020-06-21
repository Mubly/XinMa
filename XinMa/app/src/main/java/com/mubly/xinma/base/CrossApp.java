package com.mubly.xinma.base;

import android.app.ActivityManager;
import android.app.Application;
import android.content.Context;
import android.graphics.Color;
import android.os.Process;

import com.lzy.okgo.OkGo;
import com.lzy.okgo.cache.CacheEntity;
import com.lzy.okgo.cache.CacheMode;
import com.lzy.okgo.cookie.CookieJarImpl;
import com.lzy.okgo.cookie.store.SPCookieStore;
import com.lzy.okgo.interceptor.HttpLoggingInterceptor;
import com.lzy.okgo.model.HttpHeaders;


import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;

import okhttp3.OkHttpClient;


public class CrossApp extends Application {
    //    首页按返回键的次数
    public static int BackKeyCount = 0;
    private static CrossApp sCrossApp;

    public static CrossApp get() {
        return sCrossApp;
    }


    @Override
    public void onCreate() {
        super.onCreate();
        sCrossApp = this;
        initOkGo();

    }

    public void initOkGo() {

        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        //使用sp保持cookie，如果cookie不过期，则一直有效
        builder.cookieJar(new CookieJarImpl(new SPCookieStore(this)));
        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor("OkGo");
        loggingInterceptor.setPrintLevel(HttpLoggingInterceptor.Level.BODY);
        //log颜色级别，决定了log在控制台显示的颜色
        loggingInterceptor.setColorLevel(Level.INFO);
        builder.addInterceptor(loggingInterceptor);
        builder.readTimeout(12000, TimeUnit.MILLISECONDS);      //全局的读取超时时间
        builder.writeTimeout(12000, TimeUnit.MILLISECONDS);     //全局的写入超时时间
        builder.connectTimeout(12000, TimeUnit.MILLISECONDS);   //全局的连接超时时间
        OkGo.getInstance()
                .init(this)
                .addCommonHeaders(new HttpHeaders("Content-Type", "application/json"))
//                .addCommonHeaders(new HttpHeaders("x-qomolangma-wx-token", AppConfig.token.get()))
//                .addCommonParams(new HttpParams("x-qomolangma-wx-token", "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJhdWQiOiI0NSJ9.KqqDkqyz07FC0k5eDzr1Yy2qbeKTgCevo9ZpCcqU2n4"))
                .setOkHttpClient(builder.build()) //设置OkHttpClient，不设置将使用默认的
                .setCacheMode(CacheMode.NO_CACHE)
                .setCacheTime(CacheEntity.CACHE_NEVER_EXPIRE)
                .setRetryCount(1);

    }

    private boolean shouldInit() {
        ActivityManager am = ((ActivityManager) getSystemService(Context.ACTIVITY_SERVICE));
        List<ActivityManager.RunningAppProcessInfo> processInfos = am.getRunningAppProcesses();
        String mainProcessName = getPackageName();
        int myPid = Process.myPid();
        for (ActivityManager.RunningAppProcessInfo info : processInfos) {
            if (info.pid == myPid && mainProcessName.equals(info.processName)) {
                return true;
            }
        }
        return false;
    }
}

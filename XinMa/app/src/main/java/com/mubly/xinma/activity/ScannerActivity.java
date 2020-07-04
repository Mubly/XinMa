package com.mubly.xinma.activity;


import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Vibrator;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.mubly.xinma.R;
import com.mubly.xinma.base.BaseActivity;
import com.mubly.xinma.databinding.ActivityScannerBinding;
import com.mubly.xinma.iview.IScannerView;
import com.mubly.xinma.presenter.ScannerPresenter;
import com.mubly.xinma.utils.CommUtil;

import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import cn.bingoogolapple.qrcode.core.QRCodeView;
import cn.bingoogolapple.qrcode.zbar.ZBarView;
import io.reactivex.disposables.Disposable;


/**
 * Description:
 *
 * @author
 * @date 06/10/2019 11:09
 */
public class ScannerActivity extends BaseActivity<ScannerPresenter, IScannerView> implements IScannerView, QRCodeView.Delegate {
    public static final String KEY_SCANNER_TYPE = "scannerType";
    ActivityScannerBinding binding = null;
    private static final int DEF_RESULT_CODE = 999;
    /**
     * 只扫描一次
     */
    public static final int TYPE_SCANNER_ONLY_ONECTIME = 8001;
    public static final int SCAN_REQUEST_CODE = 10000;//复核中扫码请求码
    public final static String SCAN_RESULT = "result";


    /**
     * 1001 核销
     */
    private int scannerType;


    private boolean scannerFlag = false;


    @Override
    public void initView() {
        binding.zBarView.setDelegate(this);

    }

    @Override
    protected ScannerPresenter createPresenter() {
        return null;
    }

    @Override
    protected void getLayoutId() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_scanner);
        scannerType = getIntent().getIntExtra(KEY_SCANNER_TYPE, DEF_RESULT_CODE);
    }

    @Override
    protected void onStart() {
        super.onStart();
        //打开后置摄像头开始预览
        binding.zBarView.startCamera();

//        //显示扫描框
        binding.zBarView.startSpotAndShowRect();
    }

    public void closeAc(int type, String msg) {
        Intent intent = new Intent();
        intent.putExtra(SCAN_RESULT, msg);
        setResult(type, intent);
        ScannerActivity.this.finish();
    }

    /**
     * 扫描成功回调
     *
     * @param result
     */
    @Override
    public void onScanQRCodeSuccess(String result) {


        if (!TextUtils.isEmpty(result)) {
            Intent intent = new Intent();
            intent.putExtra(SCAN_RESULT, result);
            setResult(DEF_RESULT_CODE,intent);
        }

        finish();

    }


    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if (hasFocus) {
            binding.zBarView.startSpotAndShowRect();
        }
    }

    /**
     * 当前摄像头环境过暗回调
     *
     * @param isDark
     */
    @Override
    public void onCameraAmbientBrightnessChanged(boolean isDark) {
        if (isDark)
        CommUtil.ToastU.showToast("环境过暗，建议打开手电");
    }

    /**
     * 打开相机出错回调
     */
    @Override
    public void onScanQRCodeOpenCameraError() {
        CommUtil.ToastU.showToast("打开相机出错");

    }


    @Override
    protected void onStop() {
        super.onStop();
        //关闭摄像头预览，并且隐藏扫描框
        binding.zBarView.stopCamera();
    }

    @Override
    protected void onDestroy() {
        binding.zBarView.onDestroy();
        super.onDestroy();
    }


}

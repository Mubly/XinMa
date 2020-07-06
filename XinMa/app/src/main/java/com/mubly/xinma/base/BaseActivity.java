package com.mubly.xinma.base;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.res.Resources;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.StrictMode;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;


import com.gyf.barlibrary.ImmersionBar;
import com.mubly.xinma.R;
import com.mubly.xinma.activity.PrintActivity;
import com.mubly.xinma.activity.ScannerActivity;
import com.mubly.xinma.activity.SettingActivity;
import com.mubly.xinma.utils.AdaptScreenUtils;
import com.mubly.xinma.utils.AppConfig;
import com.mubly.xinma.utils.CommUtil;
import com.shehuan.nicedialog.BaseNiceDialog;
import com.shehuan.nicedialog.NiceDialog;
import com.shehuan.nicedialog.ViewConvertListener;
import com.shehuan.nicedialog.ViewHolder;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;


public abstract class BaseActivity<P extends BasePresenter<V>, V extends BaseMvpView>
        extends AppCompatActivity implements BaseMvpView, View.OnClickListener {

    public static final int ANIMTOPANDBOTTOM = 1;// 从上至下
    public static final int ANIMLEFTANDRIGHT = 0;// 从左至右
    private ProgressDialog progressDialog;
    protected P mPresenter;

    public Context mContext;
    protected Handler mHandler;
    private int animType = ANIMLEFTANDRIGHT;// activity 进入动画默认为从左至右
    private ImageView backBtn, rightAddBtn, printBtn, settingBtn;
    private TextView titleTv, rightTv;
    private LinearLayout rightImgLayout;
    private BaseNiceDialog loadDialog = null;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        CrossApp.get().addAct(this);
        ImmersionBar.with(this).fitsSystemWindows(true).statusBarColor(R.color.main_blue).init();
        //判断是否使用MVP模式
        mContext = this;
        mPresenter = createPresenter();
        if (null != mPresenter) {
            mPresenter.attachView((V) this);
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
            StrictMode.setVmPolicy(builder.build());
        }

        //子类不需要再设置布局ID,也不需要使用ButterKnife.bind();
//        setContentView(R.layout.top_bar_layout);
        getLayoutId();
        addLayoutView();
        initView();
        initData();
        initEvent();
        mHandler = new Handler(Looper.getMainLooper());
    }

    protected void addLayoutView() {
        backBtn = findViewById(R.id.root_back_btn);
        titleTv = findViewById(R.id.root_title_tv);
        rightTv = findViewById(R.id.root_right_btn);
        rightAddBtn = findViewById(R.id.right_add);
        rightImgLayout = findViewById(R.id.top_right_icon_layout);
        printBtn = findViewById(R.id.print_icon);
        settingBtn = findViewById(R.id.setting_icon);
        backBtn.setOnClickListener(this);
        rightTv.setOnClickListener(this);
        rightAddBtn.setOnClickListener(this);
        printBtn.setOnClickListener(this);
        settingBtn.setOnClickListener(this);
    }


    public abstract void initView();

    public void initData() {
    }

    public void initEvent() {
    }

    //用于创建Presenter(由子类实现)
    protected abstract P createPresenter();

    //得到当前界面的布局文件id
    protected abstract void getLayoutId();


    @Override
    public ProgressDialog getProgressDialog() {
        if (null == progressDialog) {
            progressDialog = new ProgressDialog(this);//实例化progressDialog
        }
        return progressDialog;
    }

    @Override
    public void showProgress(String msg) {
        getProgressDialog().setMessage(msg);//设置进度条加载内容
        if (!progressDialog.isShowing())//如果进度条没有显示
            progressDialog.show();//显示进度条
    }

    @Override
    public void hideProgress() {

        if (getProgressDialog().isShowing())
            progressDialog.dismiss();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mPresenter != null) {
            //presenter与view断开连接
            mPresenter.detachView();
        }

        if (progressDialog != null) {
            progressDialog.dismiss();
        }

        if (mHandler != null) {
            mHandler.removeCallbacksAndMessages(null);
            mHandler = null;
        }
        if (null != loadDialog) {
            if (loadDialog.isVisible())
                loadDialog.dismiss();
        }
        CrossApp.get().deAct(this);
        ImmersionBar.with(this).destroy();
    }


    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    public void checkNetCode(int code, String msg) {
        switch (code) {
            case 401: //nast-token 失效或过期
                CommUtil.ToastU.showToast(msg);
                AppConfig.token.remove();
                break;
//            case USERFORBID:// 账号禁用
//                OffsiteLanding("账号已被禁用");
//                break;
//            case UUIDERROR://多端登陆
//                OffsiteLanding("账号已经在别处登录,请重新登录");
//                break;
            default:
                CommUtil.ToastU.showToast(msg);
                break;
        }
    }

    @Override
    public void startActivityForResult(Class<?> act, int requstCode) {
        Intent intent = new Intent(this, ScannerActivity.class);
        startActivityForResult(intent, requstCode);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.root_back_btn:
                finish();
                break;
            case R.id.root_right_btn:
                onRightClickEvent(rightTv);
                break;
            case R.id.right_add:
                onRightAddEvent(rightAddBtn);
                break;
            case R.id.print_icon:
                startActivity(PrintActivity.class);
                break;
            case R.id.setting_icon:
                startActivity(SettingActivity.class);
                break;
        }
    }

    public void onRightAddEvent(ImageView rightAddBtn) {
    }

    public void setRightAddBtnEnable(Boolean enable) {
        rightAddBtn.setVisibility(enable ? View.VISIBLE : View.GONE);
    }

    public void setRightBtnResId(int imgRsId) {
        rightAddBtn.setVisibility(View.VISIBLE);
        rightAddBtn.setImageResource(imgRsId);
    }

    public void setBackBtnEnable(Boolean enable) {
        backBtn.setVisibility(enable ? View.VISIBLE : View.GONE);
    }

    public void setRightImgLayoutEnable(Boolean enable) {
        rightImgLayout.setVisibility(enable ? View.VISIBLE : View.GONE);
    }

    public void setTitle(String rightStr) {
        titleTv.setText(rightStr);
    }

    public void setTitle(int rightId) {
        titleTv.setText(getResources().getString(rightId));
    }

    public void setRightTv(String rightStr) {
        rightTv.setVisibility(View.VISIBLE);
        rightTv.setText(rightStr);
    }

    public void onRightClickEvent(TextView rightTv) {
    }

    public void startActivity(Class<?> act) {
        startActivity(new Intent(this, act));
    }

    @Override
    public void startActivity(Class<?> act, boolean closeAct) {
        startActivity(act);
        if (closeAct)
            finish();
    }

    @Override
    public Resources getResources() {
        return AdaptScreenUtils.adaptWidth(super.getResources(), 375);
    }

    @Override
    public void closeCurrentAct() {
        finish();
    }

    @Override
    public void closeAllAct() {
        CrossApp.get().closeAllAct();
    }

    public void forScanResult(String code) {
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == 999) {
            String code = data.getStringExtra("result");
            forScanResult(code);
        }
    }


    public void showLoading(String msg) {
        if (null == loadDialog) {
            loadDialog = NiceDialog.init().setLayoutId(R.layout.toast_style_view)
                    .setConvertListener(new ViewConvertListener() {
                        @Override
                        protected void convertView(ViewHolder holder, BaseNiceDialog dialog) {
                            holder.setText(R.id.progress_message, msg);
                        }
                    })
                    .setWidth(90)
                    .setHeight(90)
                    .show(getSupportFragmentManager());
        } else {
            loadDialog.show(getSupportFragmentManager());
        }

    }

    public void hideLoading() {
        loadDialog.dismiss();
    }
}

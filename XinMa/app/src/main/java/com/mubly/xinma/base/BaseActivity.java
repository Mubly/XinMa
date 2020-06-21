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
import com.mubly.xinma.utils.AdaptScreenUtils;
import com.mubly.xinma.utils.AppConfig;
import com.mubly.xinma.utils.CommUtil;

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
    private ImageView backBtn;
    private TextView titleTv, rightTv;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

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
        backBtn.setOnClickListener(this);
        rightTv.setOnClickListener(this);
    }


    public void initView() {
    }

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
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.root_back_btn:
                finish();
                break;
            case R.id.root_right_btn:
                onRightClickEvent(rightTv);
                break;
        }
    }

    public void setBackBtnEnable(Boolean enable) {
        backBtn.setVisibility(enable ? View.VISIBLE : View.GONE);
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
    public Resources getResources() {
        return AdaptScreenUtils.adaptWidth(super.getResources(), 375);
    }

}

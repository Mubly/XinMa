package com.mubly.xinma.home;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.work.Constraints;
import androidx.work.NetworkType;
import androidx.work.OneTimeWorkRequest;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mubly.xinma.R;
import com.mubly.xinma.activity.AssetsDetialActivity;
import com.mubly.xinma.activity.ScannerActivity;
import com.mubly.xinma.adapter.HomeMenuAdapter;
import com.mubly.xinma.base.BaseActivity;
import com.mubly.xinma.databinding.ActivityMainBinding;
import com.mubly.xinma.db.XinMaDatabase;
import com.mubly.xinma.model.AssetBean;

public class MainActivity extends BaseActivity<HomePresenter, IHomeView> implements IHomeView {
    ActivityMainBinding binding = null;

    @Override
    protected HomePresenter createPresenter() {
        return new HomePresenter();
    }

    @Override
    protected void getLayoutId() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
    }

    @Override
    public void initEvent() {
        super.initEvent();
        binding.scanIv.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        super.onClick(view);
        switch (view.getId()) {
            case R.id.scan_iv:
                Intent scan = new Intent(MainActivity.this, ScannerActivity.class);
                scan.putExtra(ScannerActivity.KEY_SCANNER_TYPE, ScannerActivity.TYPE_SCANNER_ONLY_ONECTIME);
                startActivityForResult(scan, ScannerActivity.SCAN_REQUEST_CODE);
                break;
        }
    }

    @Override
    public void forScanResult(String code) {
        super.forScanResult(code);
        if (!TextUtils.isEmpty(code)) {
            Observable.create(new ObservableOnSubscribe<AssetBean>() {
                @Override
                public void subscribe(ObservableEmitter<AssetBean> emitter) throws Exception {
                    emitter.onNext(XinMaDatabase.getInstance().assetBeanDao().getAssetBeanByNo(code));
                }
            }).subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Consumer<AssetBean>() {
                        @Override
                        public void accept(AssetBean assetBean) throws Exception {
                            Intent intent = new Intent(MainActivity.this, AssetsDetialActivity.class);
                            intent.putExtra("from", "manScan");
                            intent.putExtra("assetBean", assetBean);
                            startActivity(intent);
                        }
                    });

        }
    }

    @Override
    public void initView() {
        binding.setPersenter(mPresenter);
        binding.setLifecycleOwner(this);
        mPresenter.init();
        setBackBtnEnable(false);
        setRightImgLayoutEnable(true);
        setTitle(R.string.app_name_title);

    }

    @Override
    public void showMenu(HomeMenuAdapter adapter) {
        binding.menuRv.setLayoutManager(new GridLayoutManager(this, 3));
        binding.menuRv.setAdapter(adapter);
    }


}

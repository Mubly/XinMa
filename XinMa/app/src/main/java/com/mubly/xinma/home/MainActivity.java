package com.mubly.xinma.home;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.GridLayoutManager;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mubly.xinma.R;
import com.mubly.xinma.adapter.HomeMenuAdapter;
import com.mubly.xinma.base.BaseActivity;
import com.mubly.xinma.databinding.ActivityMainBinding;

public class MainActivity extends BaseActivity<HomePresenter, IHomeView> implements IHomeView{
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
    public void initView() {
        super.initView();
        binding.setPersenter(mPresenter);
        binding.setLifecycleOwner(this);
        mPresenter.init();
        setBackBtnEnable(false);
        setTitle(R.string.app_name_title);
        binding.ldleTv.setText("660");
        binding.usingTv.setText("78");
        binding.repairTv.setText("23");
    }

    @Override
    public void showMenu(HomeMenuAdapter adapter) {
        binding.menuRv.setLayoutManager(new GridLayoutManager(this,3));
        binding.menuRv.setAdapter(adapter);
    }
}

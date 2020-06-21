package com.mubly.xinma.home;

import android.util.Log;

import com.mubly.xinma.adapter.HomeMenuAdapter;
import com.mubly.xinma.base.BasePresenter;
import com.mubly.xinma.common.Constant;
import com.mubly.xinma.db.DataBaseUtils;
import com.mubly.xinma.model.AssetBean;
import com.mubly.xinma.model.AssetInfoBean;
import com.mubly.xinma.model.HomeMenuBean;
import com.mubly.xinma.utils.CommUtil;

import java.util.ArrayList;
import java.util.List;

public class HomePresenter extends BasePresenter<IHomeView> {
    private List<HomeMenuBean> homeMenuBeanList = new ArrayList<>();
    HomeMenuAdapter homeMenuAdapter = null;

    public HomePresenter() {
        if (homeMenuBeanList == null) {
            homeMenuBeanList = new ArrayList<>();
        }
        initMenuData();
    }

    public void init() {
        homeMenuAdapter = new HomeMenuAdapter(homeMenuBeanList);
        getMvpView().showMenu(homeMenuAdapter);
    }

    private void initMenuData() {
        for (int i = 0; i < 11; i++) {
            homeMenuBeanList.add(new HomeMenuBean(Constant.menuName[i], Constant.menuIconResId[i], i + 1));
        }
    }

    public void getData1() {
        Log.i("homePresenter", "开始准备");
        new Thread(new Runnable() {
            @Override
            public void run() {
                List<AssetBean> da = DataBaseUtils.getInstance().getAssetBeanList();
                if (null != da)
                    Log.i("homePresenter", "有数据");
            }
        }).start();

    }

    public void getData2() {
        Log.i("homePresenter", "开始准备");
        new Thread(new Runnable() {
            @Override
            public void run() {
                List<AssetInfoBean> das = DataBaseUtils.getInstance().getAssetInfoBeanList();
                if (null != das)
                    Log.i("homePresenter", "有数据");
            }
        }).start();

    }
}

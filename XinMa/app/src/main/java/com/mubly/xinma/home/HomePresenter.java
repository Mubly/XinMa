package com.mubly.xinma.home;

import com.mubly.xinma.activity.AnalysisActivity;
import com.mubly.xinma.activity.DisposeActivity;
import com.mubly.xinma.activity.GroupActivity;
import com.mubly.xinma.activity.SortClassActivity;
import com.mubly.xinma.adapter.HomeMenuAdapter;
import com.mubly.xinma.activity.AssetsListActivity;
import com.mubly.xinma.activity.CreateActivity;
import com.mubly.xinma.activity.RepairActivity;
import com.mubly.xinma.activity.ReturnActivity;
import com.mubly.xinma.base.BasePresenter;
import com.mubly.xinma.activity.BrrorowActivity;
import com.mubly.xinma.activity.CheckListActivity;
import com.mubly.xinma.common.CallBack;
import com.mubly.xinma.common.Constant;
import com.mubly.xinma.activity.GetUseActivity;
import com.mubly.xinma.model.HomeMenuBean;

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
        homeMenuAdapter = new HomeMenuAdapter(homeMenuBeanList, new CallBack<HomeMenuBean>() {
            @Override
            public void callBack(HomeMenuBean obj) {
                switch (obj.type){
                    case 1:
                        getMvpView().startActivity(AssetsListActivity.class);
                        break;
                    case 2:
                        getMvpView().startActivity(CreateActivity.class);
                        break;
                    case 3:
                        getMvpView().startActivity(CheckListActivity.class);
                        break;
                    case 4:
                        getMvpView().startActivity(GetUseActivity.class);
                        break;
                    case 5:
                        getMvpView().startActivity(BrrorowActivity.class);
                        break;
                    case 6:
                        getMvpView().startActivity(ReturnActivity.class);
                        break;
                    case 7:
                        getMvpView().startActivity(RepairActivity.class);
                        break;
                    case 8:
                        getMvpView().startActivity(DisposeActivity.class);
                        break;
                    case 9:
                        getMvpView().startActivity(AnalysisActivity.class);
                        break;
                    case 10:
                        getMvpView().startActivity(SortClassActivity.class);
                        break;
                    case 11:
                        getMvpView().startActivity(GroupActivity.class);
                        break;
                }

            }
        });
        getMvpView().showMenu(homeMenuAdapter);
    }

    private void initMenuData() {
        for (int i = 0; i < 11; i++) {
            homeMenuBeanList.add(new HomeMenuBean(Constant.menuName[i], Constant.menuIconResId[i], i + 1));
        }
    }
}

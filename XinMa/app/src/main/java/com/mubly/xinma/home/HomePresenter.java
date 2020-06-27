package com.mubly.xinma.home;

import android.util.Log;

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
import com.mubly.xinma.db.DataBaseUtils;
import com.mubly.xinma.db.XinMaDatabase;
import com.mubly.xinma.model.AssetBean;
import com.mubly.xinma.model.CategoryDataBean;
import com.mubly.xinma.model.CheckData;
import com.mubly.xinma.model.CompanyDataBean;
import com.mubly.xinma.model.HomeMenuBean;
import com.mubly.xinma.model.StaffDataBean;
import com.mubly.xinma.utils.AppConfig;

import java.util.ArrayList;
import java.util.List;

import androidx.lifecycle.MutableLiveData;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class HomePresenter extends BasePresenter<IHomeView> {
    private List<HomeMenuBean> homeMenuBeanList = new ArrayList<>();
    HomeMenuAdapter homeMenuAdapter = null;
    private MutableLiveData<String> ldle = new MutableLiveData<>();
    private MutableLiveData<String> using = new MutableLiveData<>();
    private MutableLiveData<String> repair = new MutableLiveData<>();

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
                switch (obj.type) {
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
        initData();
        gainStaffData();
        gainCheckData();
    }

    private void gainCheckData() {
        CheckData.getNetCheckData();
    }

    public MutableLiveData<String> getLdle() {
        return ldle;
    }

    public MutableLiveData<String> getUsing() {
        return using;
    }

    public MutableLiveData<String> getRepair() {
        return repair;
    }

    private void initData() {
        Observable.create(new ObservableOnSubscribe<List<String>>() {

            @Override
            public void subscribe(ObservableEmitter<List<String>> emitter) throws Exception {
                List<String> topCount = new ArrayList<>();
                topCount.add(String.valueOf(XinMaDatabase.getInstance().assetBeanDao().getCountByStatus("1")));
                topCount.add(String.valueOf(XinMaDatabase.getInstance().assetBeanDao().getCountByStatus("3")));
                topCount.add(String.valueOf(XinMaDatabase.getInstance().assetBeanDao().getCountByStatus("6")));
                emitter.onNext(topCount);
            }
        }).observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Consumer<List<String>>() {
                    @Override
                    public void accept(List<String> assetBeanList) throws Exception {
                        if (assetBeanList != null && assetBeanList.size() == 3) {
                            ldle.setValue(assetBeanList.get(0));
                            using.setValue(assetBeanList.get(1));
                            repair.setValue(assetBeanList.get(2));
                        }
                    }
                });
        gainCompanyInfo();
    }

    private void gainCompanyInfo() {
        if (null != AppConfig.companyInfo.get()) return;
        CompanyDataBean.getCompanyInfo(new CallBack<CompanyDataBean>() {
            @Override
            public void callBack(CompanyDataBean obj) {

            }
        });
    }

    private void initMenuData() {
        for (int i = 0; i < 11; i++) {
            homeMenuBeanList.add(new HomeMenuBean(Constant.menuName[i], Constant.menuIconResId[i], i + 1));
        }
    }


    public void gainStaffData() {
        StaffDataBean.getStaffList(new CallBack<StaffDataBean>() {
            @Override
            public void callBack(StaffDataBean obj) {

            }
        });
    }
}

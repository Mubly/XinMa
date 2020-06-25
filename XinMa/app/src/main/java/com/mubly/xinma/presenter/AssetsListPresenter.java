package com.mubly.xinma.presenter;

import com.lzy.okgo.db.DBUtils;
import com.mubly.xinma.adapter.AssetsListPageAdapter;
import com.mubly.xinma.base.BasePresenter;
import com.mubly.xinma.db.DataBaseUtils;
import com.mubly.xinma.db.XinMaDatabase;
import com.mubly.xinma.iview.IAssetListView;
import com.mubly.xinma.iview.IReturnView;
import com.mubly.xinma.model.AssetBean;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class AssetsListPresenter extends BasePresenter<IAssetListView> {
    List<AssetBean> allAssetBeanList = new ArrayList<>();
    List<String> titleList = new ArrayList<>();
    AssetsListPageAdapter pageAdapter = null;

    public void init() {
        Observable.create(new ObservableOnSubscribe<List<AssetBean>>() {
            @Override
            public void subscribe(ObservableEmitter<List<AssetBean>> emitter) throws Exception {
                emitter.onNext(DataBaseUtils.getInstance().getAssetBeanList());
            }
        }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<List<AssetBean>>() {
                    @Override
                    public void accept(List<AssetBean> assetBeanList) throws Exception {
                        int allCount = 0;
                        int ldleCount = 0;
                        int usingCount = 0;
                        int brrorowCount = 0;
                        int repairCount = 0;
                        int disposeCount = 0;
                        if (null != assetBeanList) {
                            allCount = assetBeanList.size();
                            for (AssetBean assetBean : assetBeanList) {
                                if (assetBean.getStatus().equals("1")) {
                                    ldleCount++;
                                } else if (assetBean.getStatus().equals("3")) {
                                    usingCount++;
                                } else if (assetBean.getStatus().equals("5")) {
                                    brrorowCount++;
                                } else if (assetBean.getStatus().equals("6")) {
                                    repairCount++;
                                } else if (assetBean.getStatus().equals("8")) {
                                    disposeCount++;
                                }
                            }
                            allAssetBeanList.addAll(assetBeanList);
                        }
                        titleList.add("全部(" + allCount + ")");
                        titleList.add("闲置(" + ldleCount + ")");
                        titleList.add("在用(" + usingCount + ")");
                        titleList.add("借用(" + brrorowCount + ")");
                        titleList.add("维修(" + repairCount + ")");
                        titleList.add("处置(" + disposeCount + ")");
                        pageAdapter = new AssetsListPageAdapter(getMvpView().getFgManager(), titleList);
                        getMvpView().showPageView(pageAdapter);
                    }
                });


    }

    public List<AssetBean> getAllAssetBeanList() {
        return allAssetBeanList;
    }
}

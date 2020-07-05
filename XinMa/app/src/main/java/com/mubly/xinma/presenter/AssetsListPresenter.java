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
    //    List<AssetBean> allAssetBeanList = new ArrayList<>();
    List<String> titleList = new ArrayList<>();
    AssetsListPageAdapter pageAdapter = null;

    public void init() {
        Observable.create(new ObservableOnSubscribe<List<String>>() {
            @Override
            public void subscribe(ObservableEmitter<List<String>> emitter) throws Exception {
                int allCount = 0;
                int ldleCount = 0;
                int usingCount = 0;
                int brrorowCount = 0;
                int repairCount = 0;
                int disposeCount = 0;
                List<String> titleData = new ArrayList<>();
                allCount = XinMaDatabase.getInstance().assetBeanDao().getAllCount();
                ldleCount = XinMaDatabase.getInstance().assetBeanDao().getCountByStatus("1");
                usingCount = XinMaDatabase.getInstance().assetBeanDao().getCountByStatus("3");
                brrorowCount = XinMaDatabase.getInstance().assetBeanDao().getCountByStatus("5");
                repairCount = XinMaDatabase.getInstance().assetBeanDao().getCountByStatus("6");
                disposeCount = XinMaDatabase.getInstance().assetBeanDao().getCountByStatus("8");
                titleData.add("全部(" + allCount + ")");
                titleData.add("闲置(" + ldleCount + ")");
                titleData.add("在用(" + usingCount + ")");
                titleData.add("借用(" + brrorowCount + ")");
                titleData.add("维修(" + repairCount + ")");
                titleData.add("处置(" + disposeCount + ")");
                emitter.onNext(titleData);
            }
        }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<List<String>>() {
                    @Override
                    public void accept(List<String> assetBeanList) throws Exception {
                        titleList.clear();
                        titleList.addAll(assetBeanList);
                        pageAdapter = new AssetsListPageAdapter(getMvpView().getFgManager(), titleList);
                        getMvpView().showPageView(pageAdapter);
                    }
                });


    }

}

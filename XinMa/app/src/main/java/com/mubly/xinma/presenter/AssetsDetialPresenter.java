package com.mubly.xinma.presenter;

import androidx.lifecycle.MutableLiveData;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

import com.mubly.xinma.base.BasePresenter;
import com.mubly.xinma.common.CallBack;
import com.mubly.xinma.db.XinMaDatabase;
import com.mubly.xinma.iview.IAssetsDetialView;
import com.mubly.xinma.model.AssetBean;
import com.mubly.xinma.model.AssetInfoBean;
import com.mubly.xinma.model.CheckData;
import com.mubly.xinma.utils.LiveDataBus;

import java.util.List;

public class AssetsDetialPresenter extends BasePresenter<IAssetsDetialView> {
    private MutableLiveData<String> currentStatus = new MutableLiveData<>();
    private MutableLiveData<AssetBean> assetsBean = new MutableLiveData<>();

    public MutableLiveData<AssetBean> getAssetsBean() {
        return assetsBean;
    }

    public MutableLiveData<String> getCurrentStatus() {
        return currentStatus;
    }

    public void init(AssetBean bean) {
        assetsBean.setValue(bean);
        currentStatus.setValue("当前状态:" + bean.getStatusName());
        searchAssetsInfo(bean.getAssetID());
    }

    private void searchAssetsInfo(String assetID) {
        Observable.create(new ObservableOnSubscribe<List<AssetInfoBean>>() {
            @Override
            public void subscribe(ObservableEmitter<List<AssetInfoBean>> emitter) throws Exception {
                emitter.onNext(XinMaDatabase.getInstance().assetInfoBeanDao().getAllByAssetsId(assetID));
            }
        }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<List<AssetInfoBean>>() {
                    @Override
                    public void accept(List<AssetInfoBean> infoBeans) throws Exception {
                        getMvpView().showCustomParam(infoBeans);
                    }
                });
    }

    public String imgUrl(String headUrl) {
        ImageUrlPersenter imageUrlPersenter = new ImageUrlPersenter();
        return imageUrlPersenter.getAssetListUrl(headUrl);
    }

    public void checkOperate(String checkId, String status, String remark) {
        CheckData.checkOperate(checkId, status, remark, assetsBean.getValue().getAssetID(), new CallBack<Boolean>() {
            @Override
            public void callBack(Boolean obj) {
                LiveDataBus.get().with("chekRefresh").postValue(true);
                getMvpView().closeCurrentAct();
            }
        });
    }
}

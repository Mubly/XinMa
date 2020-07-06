package com.mubly.xinma.presenter;

import com.mubly.xinma.base.BasePresenter;
import com.mubly.xinma.common.CallBack;
import com.mubly.xinma.db.XinMaDatabase;
import com.mubly.xinma.iview.IChangeView;
import com.mubly.xinma.model.AssetBean;
import com.mubly.xinma.model.ChangeDataBean;
import com.mubly.xinma.utils.CommUtil;

import androidx.lifecycle.MutableLiveData;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class ChangePresenter extends BasePresenter<IChangeView> {
    private MutableLiveData<String> changeTime = new MutableLiveData<>();
    private MutableLiveData<String> departStaff = new MutableLiveData<>();

    public MutableLiveData<String> getDepartStaff() {
        return departStaff;
    }

    public MutableLiveData<String> getChangeTime() {
        return changeTime;
    }

    public void init() {
        changeTime.setValue(CommUtil.getCurrentTimeHM());
    }

    public void saveChange(AssetBean bean) {
        ChangeDataBean.createChange(bean.getAssetID(), changeTime.getValue(), bean.getDepart(), bean.getStaff(), bean.getSeat(), bean.getPrice(), bean.getRemainder(), bean.getRemark(), new CallBack<ChangeDataBean>() {
            @Override
            public void callBack(ChangeDataBean obj) {
                bean.setStamp(obj.getAsset().get(0).getStamp());
                Observable.create(new ObservableOnSubscribe<Boolean>() {
                    @Override
                    public void subscribe(ObservableEmitter<Boolean> emitter) throws Exception {
                        XinMaDatabase.getInstance().assetBeanDao().update(bean);
                        emitter.onNext(true);
                    }
                }).subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Consumer<Boolean>() {
                            @Override
                            public void accept(Boolean aBoolean) throws Exception {
                                getMvpView().closeCurrentAct();
                            }
                        });
            }
        });
    }
}

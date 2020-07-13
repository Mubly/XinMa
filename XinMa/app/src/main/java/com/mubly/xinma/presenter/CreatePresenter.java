package com.mubly.xinma.presenter;

import com.lzy.okgo.OkGo;
import com.lzy.okgo.model.Response;
import com.mubly.xinma.base.BaseModel;
import com.mubly.xinma.base.BasePresenter;
import com.mubly.xinma.common.CallBack;
import com.mubly.xinma.db.XinMaDatabase;
import com.mubly.xinma.db.dao.CategoryInfoDao;
import com.mubly.xinma.iview.ICreateView;
import com.mubly.xinma.iview.IReturnView;
import com.mubly.xinma.model.AssetDataBean;
import com.mubly.xinma.model.CategoryInfoBean;
import com.mubly.xinma.model.resbean.AssetsCreateRes;
import com.mubly.xinma.net.JsonCallback;
import com.mubly.xinma.net.URLConstant;
import com.mubly.xinma.utils.CommUtil;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class CreatePresenter extends BasePresenter<ICreateView> {
    public void choosePhoto() {
        getMvpView().choosePhoto();
    }

    public void init() {
    }

    public void customeParam() {
        getMvpView().customeParam();
    }

    public void imageUpload(String Headimg, CallBack<String> callBack) {
        OkGo.<BaseModel>post(URLConstant.ASSET_DATA_IMAGE_UPLOAD_URL)
                .params("Headimg", Headimg)
                .execute(new JsonCallback<BaseModel>() {
                    @Override
                    public void onSuccess(Response<BaseModel> response) {
                        if (response.body().getCode() == 1) {
                            callBack.callBack(response.body().getMsg());
                        }
                    }
                });
    }

    public void createAssets(String assetsId, String headimg, String assetNo, String assetName, String assetModel, String assetUnit
            , String assetSupply, String PurchaseDate, String original, String depreciated, String guaranteed, String Depart
            , String Staff, String seat, String Category, String CategoryId, String param, String status, String statusNme, CallBack<AssetsCreateRes> callBack) {
        AssetDataBean.assetsCreate(assetsId, headimg, assetNo, assetName, assetModel, assetUnit, assetSupply, PurchaseDate, original,
                depreciated, guaranteed, Depart, Staff, seat, Category, CategoryId, param, status, statusNme, new CallBack<AssetsCreateRes>() {
                    @Override
                    public void callBack(AssetsCreateRes obj) {
                        callBack.callBack(obj);
                    }
                });
    }

    public void getCategoryInfo(String selectCategoryId) {
        Observable.create(new ObservableOnSubscribe<List<CategoryInfoBean>>() {
            @Override
            public void subscribe(ObservableEmitter<List<CategoryInfoBean>> emitter) throws Exception {
                emitter.onNext(XinMaDatabase.getInstance().categoryInfoDao().getAllById(selectCategoryId));
            }
        }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<List<CategoryInfoBean>>() {
                    @Override
                    public void accept(List<CategoryInfoBean> categoryInfoDaos) throws Exception {
                        getMvpView().createCustomerParam(categoryInfoDaos);
                    }
                });
    }

    public void searchCode(String code) {

        Observable.create(new ObservableOnSubscribe<Boolean>() {
            @Override
            public void subscribe(ObservableEmitter<Boolean> emitter) throws Exception {
                emitter.onNext(XinMaDatabase.getInstance().assetBeanDao().getAssetBeanByNo(code) == null);
            }
        }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<Boolean>() {
                    @Override
                    public void accept(Boolean aBoolean) throws Exception {
                        if (!aBoolean)
                            CommUtil.ToastU.showToast("该资产已添加");
                    }
                });
    }
}

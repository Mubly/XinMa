package com.mubly.xinma.presenter;

import com.lzy.okgo.OkGo;
import com.lzy.okgo.model.Response;
import com.mubly.xinma.base.BaseModel;
import com.mubly.xinma.base.BasePresenter;
import com.mubly.xinma.common.CallBack;
import com.mubly.xinma.iview.ICreateView;
import com.mubly.xinma.iview.IReturnView;
import com.mubly.xinma.model.AssetDataBean;
import com.mubly.xinma.net.JsonCallback;
import com.mubly.xinma.net.URLConstant;

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

    public void createAssets(String headimg, String assetNo, String assetName, String assetModel, String assetUnit
            , String assetSupply, String PurchaseDate, String original, String depreciated, String guaranteed, String Depart
            , String Staff, String seat, String Category, String CategoryId, CallBack<Boolean> callBack) {
        AssetDataBean.assetsCreate(headimg, assetNo, assetName, assetModel, assetUnit, assetSupply, PurchaseDate, original,
                depreciated, guaranteed, Depart, Staff, seat, Category, CategoryId, new CallBack<Boolean>() {
                    @Override
                    public void callBack(Boolean obj) {
                        callBack.callBack(obj);
                    }
                });
    }
}

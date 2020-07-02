package com.mubly.xinma.model;

import com.lzy.okgo.OkGo;
import com.lzy.okgo.model.Response;
import com.mubly.xinma.base.BaseModel;
import com.mubly.xinma.base.ResponseData;
import com.mubly.xinma.common.CallBack;
import com.mubly.xinma.db.DataBaseUtils;
import com.mubly.xinma.db.XinMaDatabase;
import com.mubly.xinma.model.resbean.AssetsCreateRes;
import com.mubly.xinma.net.JsonCallback;
import com.mubly.xinma.net.URLConstant;
import com.mubly.xinma.utils.CommUtil;

import java.util.List;

import androidx.databinding.DataBindingUtil;

public class AssetDataBean extends BaseModel {
    public List<AssetBean> Asset;
    public List<AssetInfoBean> AssetInfo;

    public static void pullAssetData(final CallBack<AssetDataBean> callBack) {
        OkGo.<AssetDataBean>post(URLConstant.ASSET_DATA_DOWNLOAD_URL)
                .execute(new JsonCallback<AssetDataBean>() {
                    @Override
                    public void onSuccess(final Response<AssetDataBean> response) {
                        new Thread(new Runnable() {
                            @Override
                            public void run() {
                                if (null != response.body().Asset)
                                    DataBaseUtils.getInstance().setAssetBeanList(response.body().Asset);
                                if (null != response.body().AssetInfo)
                                    DataBaseUtils.getInstance().setAssetInfoBeanList(response.body().AssetInfo);
                                callBack.callBack(response.body());
                            }
                        }).start();

                    }
                });
    }


    public static void assetsCreate(String headimg, String assetNo, String assetName, String assetModel, String assetUnit
            , String assetSupply, String PurchaseDate, String original, String depreciated, String guaranteed, String Depart
            , String Staff, String seat, String Category, String CategoryId, CallBack<Boolean> callBack) {
        OkGo.<AssetsCreateRes>post(URLConstant.ASSET_DATA_UpdateAsset_URL)
                .params("Headimg", headimg)
                .params("AssetNo", assetNo)
                .params("AssetName", assetName)
                .params("CategoryID", CategoryId)
                .params("Category", Category)
                .params("AssetModel", assetModel)
                .params("Unit", assetUnit)
                .params("Supply", assetSupply)
                .params("PurchaseDate", PurchaseDate)
                .params("Guaranteed", guaranteed)
                .params("Original", original)
                .params("Depreciated", depreciated)
                .params("Depart", Depart)
                .params("Staff", Staff)
                .params("Seat", seat)
                .params("Status", "1")
                .params("StatusName", "闲置")

                .execute(new JsonCallback<AssetsCreateRes>() {
                    @Override
                    public void onSuccess(final Response<AssetsCreateRes> response) {
                        new Thread(new Runnable() {
                            @Override
                            public void run() {
                                if (response.body().getCode() == 1) {
                                    AssetBean assetBean = new AssetBean();
                                    assetBean.setAssetID(response.body().getAssetID());
                                    assetBean.setHeadimg(headimg);
                                    assetBean.setAssetNo(assetNo);
                                    assetBean.setAssetName(assetName);
                                    assetBean.setCategory(Category);
                                    assetBean.setCategoryID(CategoryId);
                                    assetBean.setAssetModel(assetModel);
                                    assetBean.setUnit(assetUnit);
                                    assetBean.setSupply(assetSupply);
                                    assetBean.setPurchaseDate(PurchaseDate);
                                    assetBean.setGuaranteed(guaranteed);
                                    assetBean.setOriginal(original);
                                    assetBean.setDepart(Depart);
                                    assetBean.setStaff(Staff);
                                    assetBean.setSeat(seat);
                                    assetBean.setStatus("1");
                                    assetBean.setStatusName("闲置");
                                    assetBean.setCreateTime(response.body().getStamp());
                                    XinMaDatabase.getInstance().assetBeanDao().insert(assetBean);
                                    callBack.callBack(true);
                                }

                            }
                        }).start();

                    }
                });
    }
}

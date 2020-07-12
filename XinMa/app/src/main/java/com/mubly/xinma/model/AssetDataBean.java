package com.mubly.xinma.model;

import android.text.TextUtils;

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
import com.mubly.xinma.utils.StringUtils;

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


    public static void assetsCreate(String assetsId, String headimg, String assetNo, String assetName, String assetModel, String assetUnit
            , String assetSupply, String PurchaseDate, String original, String depreciated, String guaranteed, String Depart
            , String Staff, String seat, String Category, String CategoryId, String param, CallBack<AssetsCreateRes> callBack) {
        OkGo.<AssetsCreateRes>post(URLConstant.ASSET_DATA_UpdateAsset_URL)
                .params("Headimg", StringUtils.notNull(headimg))
                .params("AssetID", StringUtils.notNull(assetsId))
                .params("AssetNo", StringUtils.notNull(assetNo))
                .params("AssetName", StringUtils.notNull(assetName))
                .params("CategoryID", StringUtils.notNull(CategoryId))
                .params("Category", StringUtils.notNull(Category))
                .params("AssetModel", StringUtils.notNull(assetModel))
                .params("Unit", StringUtils.notNull(assetUnit))
                .params("Supply", StringUtils.notNull(assetSupply))
                .params("PurchaseDate", StringUtils.notNull(PurchaseDate))
                .params("Guaranteed", StringUtils.notNull(guaranteed))
                .params("Original", StringUtils.notNull(original))
                .params("Depreciated", StringUtils.notNull(depreciated))
                .params("Depart", StringUtils.notNull(Depart))
                .params("Staff", StringUtils.notNull(Staff))
                .params("Seat", StringUtils.notNull(seat))
                .params("Status", "1")
                .params("StatusName", "闲置")
                .params("Param", StringUtils.notNull(param))

                .execute(new JsonCallback<AssetsCreateRes>() {
                    @Override
                    public void onSuccess(final Response<AssetsCreateRes> response) {
                        new Thread(new Runnable() {
                            @Override
                            public void run() {
                                if (response.body().getCode() == 1) {
                                    AssetBean assetBean = new AssetBean();
                                    assetBean.setAssetID(response.body().getAssetID());
                                    assetBean.setStamp(response.body().getStamp());
                                    assetBean.setPrice(response.body().getAsset().get(0).getPrice());
                                    assetBean.setRemainder(response.body().getAsset().get(0).getRemainder());
                                    assetBean.setExpireDate(response.body().getAsset().get(0).getExpireDate());
                                    assetBean.setHeadimg(headimg);
                                    assetBean.setAssetNo(response.body().getAsset().get(0).getAssetNo());
                                    assetBean.setDepreciated(response.body().getAsset().get(0).getDepreciated());
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
                                    assetBean.setCreateTime(CommUtil.getCurrentTime());
                                    XinMaDatabase.getInstance().assetBeanDao().insert(assetBean);
                                    callBack.callBack(response.body());
                                }
                            }
                        }).start();

                    }

                    @Override
                    public void onError(Response<AssetsCreateRes> response) {
                        super.onError(response);
                        TextUtils.isEmpty("网络异常");
                    }
                });
    }
}

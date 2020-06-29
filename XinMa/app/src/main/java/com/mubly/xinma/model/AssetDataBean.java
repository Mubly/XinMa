package com.mubly.xinma.model;

import com.lzy.okgo.OkGo;
import com.lzy.okgo.model.Response;
import com.mubly.xinma.base.BaseModel;
import com.mubly.xinma.base.ResponseData;
import com.mubly.xinma.common.CallBack;
import com.mubly.xinma.db.DataBaseUtils;
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
}

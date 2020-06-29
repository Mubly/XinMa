package com.mubly.xinma.model;

import com.lzy.okgo.OkGo;
import com.lzy.okgo.model.Response;
import com.mubly.xinma.base.BaseModel;
import com.mubly.xinma.net.JsonCallback;
import com.mubly.xinma.net.URLConstant;
import com.mubly.xinma.utils.CommUtil;

import java.util.List;

public class SynDataFastData extends BaseModel {
    private List<AssetBean> Asset;
    private List<AssetInfoBean> AssetInfo;
    private List<OperateBean> Operate;
    private List<ProcessBean> Process;

    public List<AssetBean> getAsset() {
        return Asset;
    }

    public void setAsset(List<AssetBean> asset) {
        Asset = asset;
    }

    public List<AssetInfoBean> getAssetInfo() {
        return AssetInfo;
    }

    public void setAssetInfo(List<AssetInfoBean> assetInfo) {
        AssetInfo = assetInfo;
    }

    public List<OperateBean> getOperate() {
        return Operate;
    }

    public void setOperate(List<OperateBean> operate) {
        Operate = operate;
    }

    public List<ProcessBean> getProcess() {
        return Process;
    }

    public void setProcess(List<ProcessBean> process) {
        Process = process;
    }

    public static void synData() {
        if (!CommUtil.isNetWorkConnected()) return;
        OkGo.<SynDataFastData>post(URLConstant.API_Sync_SYNC_URL)
                .execute(new JsonCallback<SynDataFastData>() {
                    @Override
                    public void onSuccess(Response<SynDataFastData> response) {
                        if (response.body().getCode() == 1) {
                            new Thread(new Runnable() {
                                @Override
                                public void run() {

                                }
                            }).start();
                        }
                    }
                });
    }
}

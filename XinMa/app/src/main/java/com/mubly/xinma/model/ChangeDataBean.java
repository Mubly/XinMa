package com.mubly.xinma.model;

import android.text.TextUtils;

import com.lzy.okgo.OkGo;
import com.lzy.okgo.model.Response;
import com.mubly.xinma.base.BaseModel;
import com.mubly.xinma.common.CallBack;
import com.mubly.xinma.net.JsonCallback;
import com.mubly.xinma.net.URLConstant;
import com.mubly.xinma.utils.CommUtil;
import com.mubly.xinma.utils.StringUtils;

import java.util.List;

public class ChangeDataBean extends BaseModel {
    private String ChangeID;
    private List<AssetBean> Asset;
    private List<ChangeBean> Change;

    public List<ChangeBean> getChange() {
        return Change;
    }

    public void setChange(List<ChangeBean> change) {
        Change = change;
    }

    public String getChangeID() {
        return ChangeID;
    }

    public void setChangeID(String changeID) {
        ChangeID = changeID;
    }

    public List<AssetBean> getAsset() {
        return Asset;
    }

    public void setAsset(List<AssetBean> asset) {
        Asset = asset;
    }

    public static void createChange(String AssetID, String ProcessTime, String Depart, String Staff, String Seat, String Price, String Remainder, String Remark, CallBack<ChangeDataBean> callBack) {
        if (TextUtils.isEmpty(Depart) || TextUtils.isEmpty(Seat) || TextUtils.isEmpty(Price) || TextUtils.isEmpty(Remainder) || TextUtils.isEmpty(Remark)) {
            CommUtil.ToastU.showToast("请完善变更信息");
            return;
        }

        OkGo.<ChangeDataBean>post(URLConstant.API_Change_InsertChange_Url)
                .params("AssetID", StringUtils.notNull(AssetID))
                .params("ProcessTime", StringUtils.notNull(ProcessTime))
                .params("AssetID", StringUtils.notNull(AssetID))
                .params("Depart", StringUtils.notNull(Depart))
                .params("Staff", StringUtils.notNull(Staff))
                .params("Seat", StringUtils.notNull(Seat))
                .params("Price", StringUtils.notNull(Price))
                .params("Remainder", StringUtils.notNull(Remainder))
                .params("Remark", StringUtils.notNull(Remark))
                .execute(new JsonCallback<ChangeDataBean>() {
                    @Override
                    public void onSuccess(Response<ChangeDataBean> response) {
                        if (response.body().getCode() == 1)
                            callBack.callBack(response.body());
                        else
                            CommUtil.ToastU.showToast(response.body().getMsg());
                    }
                });
    }

    public static void gainChangeData(String assetId, CallBack<ChangeDataBean> callBack) {
        OkGo.<ChangeDataBean>post(URLConstant.CHANGE_GAIN_URL)
                .params("AssetID", assetId)
                .execute(new JsonCallback<ChangeDataBean>() {
                    @Override
                    public void onSuccess(Response<ChangeDataBean> response) {
                        callBack.callBack(response.body());
                    }

                    @Override
                    public void onError(Response<ChangeDataBean> response) {
                        super.onError(response);
                        CommUtil.ToastU.showToast("网络连接错误");
                    }
                });

    }

}

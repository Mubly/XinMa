package com.mubly.xinma.model;

import android.text.TextUtils;

import com.lzy.okgo.OkGo;
import com.lzy.okgo.model.Response;
import com.mubly.xinma.base.BaseModel;
import com.mubly.xinma.common.CallBack;
import com.mubly.xinma.net.JsonCallback;
import com.mubly.xinma.net.URLConstant;
import com.mubly.xinma.utils.CommUtil;

import java.util.List;

public class ChangeDataBean extends BaseModel {
    private String ChangeID;
    private List<AssetBean> Asset;

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
       if ( TextUtils.isEmpty(Depart)|| TextUtils.isEmpty(Seat)|| TextUtils.isEmpty(Price)|| TextUtils.isEmpty(Remainder)|| TextUtils.isEmpty(Remark)){
           CommUtil.ToastU.showToast("请完善变更信息");
           return;
       }

        OkGo.<ChangeDataBean>post(URLConstant.API_Change_InsertChange_Url)
                .params("AssetID", AssetID)
                .params("ProcessTime", ProcessTime)
                .params("AssetID", AssetID)
                .params("Depart", Depart)
                .params("Staff", Staff)
                .params("Seat", Seat)
                .params("Price", Price)
                .params("Remainder", Remainder)
                .params("Remark", Remark)
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
}

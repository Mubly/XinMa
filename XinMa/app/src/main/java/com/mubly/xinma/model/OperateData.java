package com.mubly.xinma.model;

import com.alibaba.fastjson.JSON;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.model.Response;
import com.mubly.xinma.base.BaseModel;
import com.mubly.xinma.common.CallBack;
import com.mubly.xinma.model.resbean.OperateDataRes;
import com.mubly.xinma.net.JsonCallback;
import com.mubly.xinma.net.URLConstant;
import com.mubly.xinma.utils.CommUtil;
import com.mubly.xinma.utils.StringUtils;

import org.json.JSONArray;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class OperateData extends BaseModel {
    public static void operate(String ProcessCate, String ProcessTime, String Depart, String Staff, String Seat, String Remark
            , String AssetID, String Fee, CallBack<OperateDataRes> callBack) {

        OkGo.<OperateDataRes>post(URLConstant.API_Operate_InsertOperate_Url)
                .params("ProcessCate", StringUtils.notNull(ProcessCate))
                .params("ProcessTime", StringUtils.notNull(ProcessTime))
                .params("Depart", StringUtils.notNull(Depart))
                .params("Staff", StringUtils.notNull(Staff))
                .params("Seat", StringUtils.notNull(Seat))
                .params("Remark", StringUtils.notNull(Remark))
                .params("Param", StringUtils.notNull(AssetID))
                .params("Fee", StringUtils.notNull(Fee))
                .execute(new JsonCallback<OperateDataRes>() {
                    @Override
                    public void onSuccess(Response<OperateDataRes> response) {
                        if (response.body().getCode() == 1) {
                            callBack.callBack(response.body());
                        } else {
                            CommUtil.ToastU.showToast(response.body().getMsg());
                        }
                    }
                });
    }
}

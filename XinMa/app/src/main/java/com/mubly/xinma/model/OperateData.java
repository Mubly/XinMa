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

import org.json.JSONArray;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class OperateData extends BaseModel {
    public static void operate(String ProcessCate, String ProcessTime, String Depart, String Staff, String Seat, String Remark
            ,String AssetID, String Fee, CallBack<OperateDataRes> callBack) {
        Map<String, Object> paramMap = new HashMap<>();
//        paramMap.put("ProcessCate", ProcessCate);
//        paramMap.put("ProcessTime", ProcessTime);
//        paramMap.put("Depart", Depart);
//        paramMap.put("Staff", Staff);
//        paramMap.put("Seat", Seat);
//        paramMap.put("Remark", Remark);
//        paramMap.put("Param", AssetID);
//        paramMap.put("Fee", Fee);
        OkGo.<OperateDataRes>post(URLConstant.API_Operate_InsertOperate_Url)
        .params("ProcessCate", ProcessCate)
       .params("ProcessTime", ProcessTime)
       .params("Depart", Depart)
       .params("Staff", Staff)
       .params("Seat", Seat)
       .params("Remark", Remark)
       .params("Param", AssetID)
       .params("Fee", Fee)
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

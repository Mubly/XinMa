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
    private List<OperateBean> Operate;
    private List<ProcessBean> Process;


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


    public static void getOperateLog(CallBack<OperateData> callBack) {
        OkGo.<OperateData>post(URLConstant.API_Operate_ListOperate_Url)
                .execute(new JsonCallback<OperateData>() {
                    @Override
                    public void onSuccess(Response<OperateData> response) {
                        callBack.callBack(response.body());
                    }

                    @Override
                    public void onError(Response<OperateData> response) {
                        super.onError(response);
                        CommUtil.ToastU.showToast("网络连接异常");
                    }
                });
    }

    public static void getOperateLogList(String assetId, CallBack<OperateData> callBack) {
        OkGo.<OperateData>post(URLConstant.API_Process_SelectProcess_URL)
                .params("AssetID",assetId)
                .execute(new JsonCallback<OperateData>() {
                    @Override
                    public void onSuccess(Response<OperateData> response) {
                        callBack.callBack(response.body());
                    }

                    @Override
                    public void onError(Response<OperateData> response) {
                        super.onError(response);
                        CommUtil.ToastU.showToast("网络连接异常");
                    }
                });
    }

    public static void getOperateAssetInfo(String operateID, CallBack<OperateData> callBack) {
        OkGo.<OperateData>post(URLConstant.API_Process_SelectOperate_URL)
                .params("OperateID",operateID)
                .execute(new JsonCallback<OperateData>() {
                    @Override
                    public void onSuccess(Response<OperateData> response) {
                        callBack.callBack(response.body());
                    }

                    @Override
                    public void onError(Response<OperateData> response) {
                        super.onError(response);
                        CommUtil.ToastU.showToast("网络连接异常");
                    }
                });
    }

}

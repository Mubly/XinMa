package com.mubly.xinma.model;

import com.lzy.okgo.OkGo;
import com.lzy.okgo.model.Response;
import com.mubly.xinma.base.BaseModel;
import com.mubly.xinma.common.CallBack;
import com.mubly.xinma.db.DataBaseUtils;
import com.mubly.xinma.db.XinMaDatabase;
import com.mubly.xinma.model.resbean.DepartDataRes;
import com.mubly.xinma.net.JsonCallback;
import com.mubly.xinma.net.URLConstant;
import com.mubly.xinma.utils.CommUtil;

import java.util.List;

public class GroupData extends BaseModel {
    List<GroupBean> Depart;

    public List<GroupBean> getDepart() {
        return Depart;
    }

    public void setDepart(List<GroupBean> depart) {
        Depart = depart;
    }

    public static void getGroupData(final CallBack<GroupData> callBack) {
        OkGo.<GroupData>post(URLConstant.API_Depart_ListDepart_URL)
                .execute(new JsonCallback<GroupData>() {
                    @Override
                    public void onSuccess(final Response<GroupData> response) {
                        final GroupData groupData = response.body();
                        if (groupData.getCode() == 1) {
                            if (null != groupData.getDepart()) {
                                new Thread(new Runnable() {
                                    @Override
                                    public void run() {
                                        XinMaDatabase.getInstance().groupBeanDao().insertAll(groupData.Depart);
                                        callBack.callBack(response.body());
                                    }
                                }).start();
                            }
                        }
                    }
                });
    }


    public static void dele(String DepartID, CallBack<DepartDataRes> callBack) {
        OkGo.<DepartDataRes>post(URLConstant.API_Depart_UPDATE_URL)
                .params("DepartID", DepartID)
                .execute(new JsonCallback<DepartDataRes>() {
                    @Override
                    public void onSuccess(Response<DepartDataRes> response) {
                        if (response.body().getCode() == 1) {
                            callBack.callBack(response.body());
                        }else {
                            CommUtil.ToastU.showToast(response.body().getMsg());
                        }
                    }
                });
    }

    //添加修改
    public static void update(String DepartID, String Depart, CallBack<DepartDataRes> callBack) {
        OkGo.<DepartDataRes>post(URLConstant.API_Depart_UPDATE_URL)
                .params("DepartID", DepartID)
                .params("Depart", Depart)
                .execute(new JsonCallback<DepartDataRes>() {
                    @Override
                    public void onSuccess(Response<DepartDataRes> response) {
                        if (response.body().getCode() == 1) {
                            callBack.callBack(response.body());
                        }else {
                            CommUtil.ToastU.showToast(response.body().getMsg());
                        }
                    }
                });
    }
}

package com.mubly.xinma.model;

import android.text.TextUtils;

import com.alibaba.fastjson.JSON;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.model.Response;
import com.mubly.xinma.base.BaseModel;
import com.mubly.xinma.common.CallBack;
import com.mubly.xinma.db.XinMaDatabase;
import com.mubly.xinma.model.resbean.StaffDataRes;
import com.mubly.xinma.net.JsonCallback;
import com.mubly.xinma.net.URLConstant;
import com.mubly.xinma.utils.AppConfig;
import com.mubly.xinma.utils.CommUtil;
import com.mubly.xinma.utils.StringUtils;

import java.util.List;

public class StaffDataBean extends BaseModel {
    private List<StaffBean> Staff;

    public List<StaffBean> getStaff() {
        return Staff;
    }

    public void setStaff(List<StaffBean> staff) {
        Staff = staff;
    }

    public static void getStaffList(final CallBack<StaffDataBean> callBack) {
        OkGo.<StaffDataBean>post(URLConstant.API_Staff_ListStaff_URL)
                .execute(new JsonCallback<StaffDataBean>() {
                    @Override
                    public void onSuccess(final Response<StaffDataBean> response) {
                        final StaffDataBean staffDataBean = response.body();
                        if (staffDataBean.getCode() == 1) {
                            new Thread(new Runnable() {
                                @Override
                                public void run() {
                                    if (null != staffDataBean.Staff) {
                                        XinMaDatabase.getInstance().staffBeanDao().insertAll(staffDataBean.Staff);
                                        callBack.callBack(response.body());
                                    }

                                }
                            }).start();

                        }
                    }
                });
    }

    public static void upDateStaff(String staffId, String depart, String staff, String postion, String phone, String status, String DepartID, CallBack<StaffDataRes> callBack) {
        OkGo.<StaffDataRes>post(URLConstant.API_Staff_UpdateStaff_URL)
                .params("Depart", StringUtils.notNull(depart))
                .params("DepartID", StringUtils.notNull(DepartID))
                .params("StaffID", StringUtils.notNull(staffId))
                .params("Staff", StringUtils.notNull(staff))
                .params("Position", StringUtils.notNull(postion))
                .params("Phone", StringUtils.notNull(phone))
                .execute(new JsonCallback<StaffDataRes>() {
                    @Override
                    public void onSuccess(Response<StaffDataRes> response) {
                        new Thread(new Runnable() {
                            @Override
                            public void run() {
                                StaffBean staffBean = null;
                                if (TextUtils.isEmpty(staffId)) {
                                    staffBean = new StaffBean();
                                    staffBean.setDepart(depart);
                                    staffBean.setDepartID(DepartID);
                                    staffBean.setStaffID(response.body().StaffID);
                                    staffBean.setStaff(staff);
                                    staffBean.setPosition(postion);
                                    staffBean.setPhone(phone);
                                    staffBean.setStatus(status);
                                    XinMaDatabase.getInstance().staffBeanDao().insert(staffBean);
                                } else {
                                    staffBean = XinMaDatabase.getInstance().staffBeanDao().getStaffById(staffId);
                                    staffBean.setStaff(staff);
                                    staffBean.setPosition(postion);
                                    staffBean.setPhone(phone);
                                    staffBean.setStatus(status);
                                    XinMaDatabase.getInstance().staffBeanDao().update(staffBean);
                                }
                                callBack.callBack(response.body());
                            }
                        }).start();
                    }
                });
    }

    public static void dele(String StaffID, CallBack<Boolean> callBack) {
        OkGo.<BaseModel>post(URLConstant.API_Staff_DelStaff_URL)
                .params("StaffID", StaffID)
                .execute(new JsonCallback<BaseModel>() {
                    @Override
                    public void onSuccess(Response<BaseModel> response) {
                        if (response.body().getCode() == 1)
                            callBack.callBack(true);
                        else
                            CommUtil.ToastU.showToast(response.body().getMsg());
                    }
                });
    }
}

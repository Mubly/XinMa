package com.mubly.xinma.model;

import com.alibaba.fastjson.JSON;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.model.Response;
import com.mubly.xinma.base.BaseModel;
import com.mubly.xinma.common.CallBack;
import com.mubly.xinma.db.XinMaDatabase;
import com.mubly.xinma.net.JsonCallback;
import com.mubly.xinma.net.URLConstant;
import com.mubly.xinma.utils.AppConfig;

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
}

package com.mubly.xinma.presenter;

import android.text.TextUtils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.mubly.xinma.base.BasePresenter;
import com.mubly.xinma.common.CallBack;
import com.mubly.xinma.db.XinMaDatabase;
import com.mubly.xinma.iview.IDepartAndStaffCreateView;
import com.mubly.xinma.model.CompanyBean;
import com.mubly.xinma.model.GroupBean;
import com.mubly.xinma.model.GroupData;
import com.mubly.xinma.model.StaffBean;
import com.mubly.xinma.model.StaffDataBean;
import com.mubly.xinma.model.UserInfoBean;
import com.mubly.xinma.model.resbean.DepartDataRes;
import com.mubly.xinma.model.resbean.StaffDataRes;
import com.mubly.xinma.utils.AppConfig;
import com.mubly.xinma.utils.LiveDataBus;

import androidx.lifecycle.MutableLiveData;

public class DepartAndStaffCreatePresenter extends BasePresenter<IDepartAndStaffCreateView> {
    private MutableLiveData<String> companyName = new MutableLiveData<>();
    private MutableLiveData<String> departName = new MutableLiveData<>();

    public MutableLiveData<String> getCompanyName() {
        return companyName;
    }

    public MutableLiveData<String> getDepartName() {
        return departName;
    }

    public void initDeportMentCreate() {
        try {
            String userInfo = AppConfig.userInfo.get();
            UserInfoBean userInfoBean = JSON.parseObject(userInfo, UserInfoBean.class);
            if (null != userInfoBean)
                companyName.setValue(userInfoBean.getCompany());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void initStaffCreate(String depart, String departId, String staffId, String staff, String postion, String phone, String status) {
        StaffDataBean.upDateStaff(staffId, depart, staff, postion, phone, status, departId, new CallBack<StaffDataRes>() {
            @Override
            public void callBack(StaffDataRes obj) {
                getMvpView().closeCurrentAct();
            }
        });
    }

    //添加，更新部门成功
    public void ackDepart(String departId, String departName) {
        GroupData.update(departId, departName, new CallBack<DepartDataRes>() {
            @Override
            public void callBack(DepartDataRes obj) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        GroupBean groupBean = null;
                        if (TextUtils.isEmpty(departId)) {
                            groupBean = new GroupBean();
                            groupBean.setDepart(departName);
                            groupBean.setDepartID(obj.DepartID);
                            XinMaDatabase.getInstance().groupBeanDao().insert(groupBean);
                        } else {
                            groupBean = XinMaDatabase.getInstance().groupBeanDao().getGroupBeanById(departId);
                            groupBean.setDepart(departName);
                            if (null != groupBean) {
                                XinMaDatabase.getInstance().groupBeanDao().update(groupBean);
                            }
                        }
                        LiveDataBus.get().with("refreshGroup").postValue(true);
                        getMvpView().closeCurrentAct();
                    }
                }).start();

            }
        });
    }

    //
    public void delectStaff(String staffID) {
        StaffDataBean.dele(staffID, new CallBack<Boolean>() {
            @Override
            public void callBack(Boolean obj) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        StaffBean staffBean = XinMaDatabase.getInstance().staffBeanDao().getStaffById(staffID);
                        XinMaDatabase.getInstance().staffBeanDao().delete(staffBean);
                        getMvpView().closeCurrentAct();
                    }
                }).start();
            }
        });
    }
}

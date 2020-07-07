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
import com.mubly.xinma.utils.CommUtil;

import java.util.List;

public class ParamBeanData extends BaseModel {
    private List<CompanyBean> Company;
    private List<CategoryBean> Category;
    private List<CategoryInfoBean> CategoryInfo;
    private List<GroupBean> Depart;
    private List<StaffBean> Staff;
    private List<PropertyBean> Property;

    public List<CompanyBean> getCompany() {
        return Company;
    }

    public void setCompany(List<CompanyBean> company) {
        Company = company;
    }

    public List<CategoryBean> getCategory() {
        return Category;
    }

    public void setCategory(List<CategoryBean> category) {
        Category = category;
    }

    public List<CategoryInfoBean> getCategoryInfo() {
        return CategoryInfo;
    }

    public void setCategoryInfo(List<CategoryInfoBean> categoryInfo) {
        CategoryInfo = categoryInfo;
    }

    public List<GroupBean> getDepart() {
        return Depart;
    }

    public void setDepart(List<GroupBean> depart) {
        Depart = depart;
    }

    public List<StaffBean> getStaff() {
        return Staff;
    }

    public void setStaff(List<StaffBean> staff) {
        Staff = staff;
    }

    public List<PropertyBean> getProperty() {
        return Property;
    }

    public void setProperty(List<PropertyBean> property) {
        Property = property;
    }

    public static void synData(CallBack<ParamBeanData> callBack) {
        if (!CommUtil.isNetWorkConnected()) return;
        OkGo.<ParamBeanData>post(URLConstant.API_SYNC_PARAM_URL)
                .execute(new JsonCallback<ParamBeanData>() {
                    @Override
                    public void onSuccess(Response<ParamBeanData> response) {
                        if (response.body().getCode() == 1) {
                            new Thread(new Runnable() {
                                @Override
                                public void run() {
                                    if (null != response.body().getCategory())
                                        XinMaDatabase.getInstance().categoryDao().insertAll(response.body().getCategory());
                                    if (null != response.body().getCategoryInfo())
                                        XinMaDatabase.getInstance().categoryInfoDao().insertAll(response.body().getCategoryInfo());
                                    if (null != response.body().getDepart())
                                        XinMaDatabase.getInstance().groupBeanDao().insertAll(response.body().getDepart());
                                    if (null != response.body().getStaff())
                                        XinMaDatabase.getInstance().staffBeanDao().insertAll(response.body().getStaff());
                                    if (null != response.body().getProperty())
                                        XinMaDatabase.getInstance().propertyBeanDao().insertAll(response.body().getProperty());
                                    if (null != response.body().getCompany())
                                        AppConfig.companyInfo.put(JSON.toJSONString(response.body().getCompany()));
                                    callBack.callBack(response.body());
                                }
                            }).start();
                        }else {
                            callBack.callBack(response.body());
                        }
                    }
                });
    }
}

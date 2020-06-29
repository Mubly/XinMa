package com.mubly.xinma.model;

import com.lzy.okgo.OkGo;
import com.lzy.okgo.model.Response;
import com.mubly.xinma.base.BaseModel;
import com.mubly.xinma.net.JsonCallback;
import com.mubly.xinma.net.URLConstant;
import com.mubly.xinma.utils.CommUtil;

import java.util.List;

public class ParamBeanData extends BaseModel {
    List<CompanyBean>Company;
    List<CategoryBean>Category;
    List<CategoryInfoBean>CategoryInfo;
    List<GroupBean>Depart;
    List<StaffBean>Staff;
    List<PropertyBean>Property;

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

    public static void synData() {
        if (!CommUtil.isNetWorkConnected()) return;
        OkGo.<ParamBeanData>post(URLConstant.API_SYNC_PARAM_URL)
                .execute(new JsonCallback<ParamBeanData>() {
                    @Override
                    public void onSuccess(Response<ParamBeanData> response) {
                        if (response.body().getCode() == 1) {
                            new Thread(new Runnable() {
                                @Override
                                public void run() {

                                }
                            }).start();
                        }
                    }
                });
    }
}

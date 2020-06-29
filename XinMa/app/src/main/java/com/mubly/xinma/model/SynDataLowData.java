package com.mubly.xinma.model;

import com.lzy.okgo.OkGo;
import com.lzy.okgo.model.Response;
import com.mubly.xinma.base.BaseModel;
import com.mubly.xinma.net.JsonCallback;
import com.mubly.xinma.net.URLConstant;
import com.mubly.xinma.utils.CommUtil;

import java.util.List;

public class SynDataLowData extends BaseModel {
    private List<CheckBean> Check;
    private List<InventoryBean> Inventory;
    private List<CategoryBean> Category;
    private List<CategoryInfoBean> CategoryInfo;
    private List<PropertyBean> Property;
    private List<GroupBean> Depart;
    private List<StaffBean> Staff;


    public List<CheckBean> getCheck() {
        return Check;
    }

    public void setCheck(List<CheckBean> check) {
        Check = check;
    }

    public List<InventoryBean> getInventory() {
        return Inventory;
    }

    public void setInventory(List<InventoryBean> inventory) {
        Inventory = inventory;
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

    public List<PropertyBean> getProperty() {
        return Property;
    }

    public void setProperty(List<PropertyBean> property) {
        Property = property;
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

    public static void synData() {
        if (!CommUtil.isNetWorkConnected()) return;
        OkGo.<SynDataLowData>post(URLConstant.API_Sync_LOWSYNC_URL)
                .execute(new JsonCallback<SynDataLowData>() {
                    @Override
                    public void onSuccess(Response<SynDataLowData> response) {
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

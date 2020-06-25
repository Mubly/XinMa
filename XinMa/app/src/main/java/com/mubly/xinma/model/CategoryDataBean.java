package com.mubly.xinma.model;

import com.lzy.okgo.OkGo;
import com.lzy.okgo.model.Response;
import com.mubly.xinma.base.BaseModel;
import com.mubly.xinma.common.CallBack;
import com.mubly.xinma.db.DataBaseUtils;
import com.mubly.xinma.db.XinMaDatabase;
import com.mubly.xinma.net.JsonCallback;
import com.mubly.xinma.net.URLConstant;

import java.util.List;

public class CategoryDataBean extends BaseModel {
    private List<CategoryBean> Category;
    private List<CategoryInfoBean> CategoryInfo;

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

    public static void getCateGoryData(final CallBack<CategoryDataBean> callBack) {
        OkGo.<CategoryDataBean>post(URLConstant.API_Category_ListCategory)
                .execute(new JsonCallback<CategoryDataBean>() {
                    @Override
                    public void onSuccess(final Response<CategoryDataBean> response) {
                        final CategoryDataBean categoryDataBean = response.body();
                        if (categoryDataBean.getCode() == 1) {
                            new Thread(new Runnable() {
                                @Override
                                public void run() {
                                    if (null != categoryDataBean.Category)
                                        XinMaDatabase.getInstance().categoryDao().insertAll(categoryDataBean.Category);
                                    if (null != categoryDataBean.CategoryInfo)
                                        XinMaDatabase.getInstance().categoryInfoDao().insertAll(categoryDataBean.getCategoryInfo());

                                    callBack.callBack(response.body());
                                }
                            }).start();
                        }
                    }
                });
    }
}

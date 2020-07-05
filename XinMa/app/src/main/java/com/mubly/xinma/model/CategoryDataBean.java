package com.mubly.xinma.model;

import com.lzy.okgo.OkGo;
import com.lzy.okgo.model.Response;
import com.mubly.xinma.base.BaseModel;
import com.mubly.xinma.common.CallBack;
import com.mubly.xinma.db.DataBaseUtils;
import com.mubly.xinma.db.XinMaDatabase;
import com.mubly.xinma.model.resbean.CategoryDataRes;
import com.mubly.xinma.net.JsonCallback;
import com.mubly.xinma.net.URLConstant;
import com.mubly.xinma.utils.CommUtil;

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

    //更新，添加
    public static void categoryUpDate(String CategoryID, String Category, String Param, CallBack<CategoryDataRes> callBack) {
        OkGo.<CategoryDataRes>post(URLConstant.API_Category_UpdateCategory)
                .params("CategoryID", CategoryID)
                .params("Category", Category)
                .params("Param", Param)
                .execute(new JsonCallback<CategoryDataRes>() {
                    @Override
                    public void onSuccess(Response<CategoryDataRes> response) {
                        if (response.body().getCode() == 1) {
                            callBack.callBack(response.body());
                        } else {
                            CommUtil.ToastU.showToast(response.body().getMsg());
                        }
                    }
                });
    }


    public static void dele(String CategoryID, CallBack callBack) {
        OkGo.<BaseModel>post(URLConstant.API_Category_DelCategory)
                .params("CategoryID", CategoryID)
                .execute(new JsonCallback<BaseModel>() {
                    @Override
                    public void onSuccess(Response<BaseModel> response) {
                        if (response.body().getCode() == 1) {
                            new Thread(new Runnable() {
                                @Override
                                public void run() {
                                    XinMaDatabase.getInstance().categoryDao().deleteById(CategoryID);
                                    XinMaDatabase.getInstance().categoryInfoDao().deleteById(CategoryID);
                                    callBack.callBack(null);
                                }
                            }).start();
                        } else {
                            CommUtil.ToastU.showToast(response.body().getMsg());
                        }
                    }
                });
    }
}

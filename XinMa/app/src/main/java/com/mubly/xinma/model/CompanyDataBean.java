package com.mubly.xinma.model;

import com.alibaba.fastjson.JSON;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.model.Response;
import com.mubly.xinma.base.BaseModel;
import com.mubly.xinma.common.CallBack;
import com.mubly.xinma.net.JsonCallback;
import com.mubly.xinma.net.URLConstant;
import com.mubly.xinma.utils.AppConfig;
import com.mubly.xinma.utils.CommUtil;

import java.util.List;

public class CompanyDataBean extends BaseModel {
    List<CompanyBean> Company;

    public static void getCompanyInfo(final CallBack<CompanyDataBean> callBack) {
        OkGo.<CompanyDataBean>post(URLConstant.API_Company_SelectCompany_Url)
                .execute(new JsonCallback<CompanyDataBean>() {
                    @Override
                    public void onSuccess(final Response<CompanyDataBean> response) {
                        if (response.body().getCode() == 1) {
                            AppConfig.companyInfo.put(JSON.toJSONString(response.body().Company.get(0)));
                            callBack.callBack(response.body());
                        }
                    }
                });
    }


    public static void changeCompanyName(String companyName, final CallBack<BaseModel> callBack) {
        OkGo.<BaseModel>post(URLConstant.API_Company_UpdateCompany_Url)
                .params("Company", companyName)
                .execute(new JsonCallback<BaseModel>() {
                    @Override
                    public void onSuccess(final Response<BaseModel> response) {

                        callBack.callBack(response.body());

                    }

                    @Override
                    public void onError(Response<BaseModel> response) {
                        super.onError(response);
                        callBack.callBack(response.body());
                    }
                });
    }

    public static void changeUserName(String userName, final CallBack<BaseModel> callBack) {
        OkGo.<BaseModel>post(URLConstant.API_USER_NAME_CHANGE_Url)
                .params("FullName", userName)
                .execute(new JsonCallback<BaseModel>() {
                    @Override
                    public void onSuccess(final Response<BaseModel> response) {
                        callBack.callBack(response.body());

                    }

                    @Override
                    public void onError(Response<BaseModel> response) {
                        super.onError(response);
                        callBack.callBack(response.body());
                    }
                });
    }

    public static void changePhoneNo(String phoneNo, String code, final CallBack<BaseModel> callBack) {
        OkGo.<BaseModel>post(URLConstant.API_PHONE_NO_CHANGE_Url)
                .params("Phone", phoneNo)
                .params("Code", code)
                .execute(new JsonCallback<BaseModel>() {
                    @Override
                    public void onSuccess(final Response<BaseModel> response) {
                        callBack.callBack(response.body());
                    }

                    @Override
                    public void onError(Response<BaseModel> response) {
                        super.onError(response);
                        CommUtil.ToastU.showToast("网络连接异常");
                    }
                });
    }
}

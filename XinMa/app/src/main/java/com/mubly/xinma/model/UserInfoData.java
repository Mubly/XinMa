package com.mubly.xinma.model;

import com.alibaba.fastjson.JSON;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.model.Response;
import com.mubly.xinma.base.BaseModel;
import com.mubly.xinma.common.CallBack;
import com.mubly.xinma.net.JsonCallback;
import com.mubly.xinma.net.URLConstant;
import com.mubly.xinma.utils.AppConfig;

import org.json.JSONObject;

import java.util.List;

public class UserInfoData extends BaseModel {
    public List<UserInfoBean> UserInfo;

    public static void getUserInfo(final CallBack<UserInfoData> callBack) {
        OkGo.<UserInfoData>post(URLConstant.GAIN_USERINFO_URL)
                .execute(new JsonCallback<UserInfoData>() {
                    @Override
                    public void onSuccess(final Response<UserInfoData> response) {

                        if (response.body().getCode() == 1) {
                            if (null != response.body().UserInfo && response.body().UserInfo.size() > 0) {
                                AppConfig.isAutoNo.put(response.body().UserInfo.get(0).getIsAutoNo() == 1 ? "1" : "0");
                                AppConfig.companyId.put(response.body().UserInfo.get(0).getCompanyID());
                                AppConfig.userInfo.put(JSON.toJSONString(response.body().UserInfo.get(0)));
                            }

                            callBack.callBack(response.body());
                        }
                    }
                });
    }
}

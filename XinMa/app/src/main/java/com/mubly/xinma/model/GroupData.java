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

public class GroupData extends BaseModel {
    List<GroupBean> Depart;

    public List<GroupBean> getDepart() {
        return Depart;
    }

    public void setDepart(List<GroupBean> depart) {
        Depart = depart;
    }

    public static void getGroupData(final CallBack<GroupData> callBack) {
        OkGo.<GroupData>post(URLConstant.API_Depart_ListDepart_URL)
                .execute(new JsonCallback<GroupData>() {
                    @Override
                    public void onSuccess(final Response<GroupData> response) {
                        final GroupData groupData=response.body();
                        if (groupData.getCode() == 1) {
                            if (null!=groupData.getDepart()){
                                new Thread(new Runnable() {
                                    @Override
                                    public void run() {
                                        XinMaDatabase.getInstance().groupBeanDao().insertAll(groupData.Depart);
                                        callBack.callBack(response.body());
                                    }
                                }).start();
                            }
                        }
                    }
                });
    }
}

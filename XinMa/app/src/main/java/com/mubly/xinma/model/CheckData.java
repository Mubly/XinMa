package com.mubly.xinma.model;

import com.alibaba.fastjson.JSON;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.model.Response;
import com.mubly.xinma.base.BaseModel;
import com.mubly.xinma.common.CallBack;
import com.mubly.xinma.db.XinMaDatabase;
import com.mubly.xinma.model.resbean.CheckCreateResBean;
import com.mubly.xinma.model.resbean.CheckOperateResData;
import com.mubly.xinma.net.JsonCallback;
import com.mubly.xinma.net.URLConstant;
import com.mubly.xinma.utils.CommUtil;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class CheckData extends BaseModel {
    private List<CheckBean> Check;
    private List<InventoryBean> Inventory;


    public List<InventoryBean> getInventory() {
        return Inventory;
    }

    public void setInventory(List<InventoryBean> inventory) {
        Inventory = inventory;
    }

    public List<CheckBean> getCheck() {
        return Check;
    }

    public void setCheck(List<CheckBean> check) {
        Check = check;
    }

    public static void getCheckDatabyStatus(final String status, final CallBack<List<CheckBean>> callBack) {
        Observable.create(new ObservableOnSubscribe<List<CheckBean>>() {
            @Override
            public void subscribe(ObservableEmitter<List<CheckBean>> emitter) throws Exception {
                emitter.onNext(XinMaDatabase.getInstance().checkBeanDao().getAllByStatus(status));
            }
        }).observeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<List<CheckBean>>() {
                    @Override
                    public void accept(List<CheckBean> checkBeanList) throws Exception {
                        if (checkBeanList != null) {
                            callBack.callBack(checkBeanList);
                        }
                    }
                });
    }

    public static void getNetCheckData() {
        OkGo.<CheckData>post(URLConstant.API_Check_ListCheck_URL)
                .execute(new JsonCallback<CheckData>() {
                    @Override
                    public void onSuccess(final Response<CheckData> response) {
                        final CheckData checkData = response.body();
                        if (checkData.getCode() == 1) {
                            new Thread(new Runnable() {
                                @Override
                                public void run() {
                                    XinMaDatabase.getInstance().inventoryBeanDao().insertAll(checkData.getInventory());
                                    XinMaDatabase.getInstance().checkBeanDao().insertAll(checkData.getCheck());
                                }
                            }).start();
                        }
                    }
                });
    }

    public static void getCheckCountByStatus(final String status, final CallBack<String> callBack) {
        Observable.create(new ObservableOnSubscribe<String>() {
            @Override
            public void subscribe(ObservableEmitter<String> emitter) throws Exception {
                int count = XinMaDatabase.getInstance().checkBeanDao().getCountByStatus(status);
                emitter.onNext(String.valueOf(count));

            }
        }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<String>() {
                    @Override
                    public void accept(String s) throws Exception {
                        callBack.callBack(s);
                    }
                });

    }

    public static void getAllCount(final CallBack<Integer> callBack) {
        Observable.create(new ObservableOnSubscribe<Integer>() {
            @Override
            public void subscribe(ObservableEmitter<Integer> emitter) throws Exception {
                emitter.onNext(XinMaDatabase.getInstance().checkBeanDao().getAllCount());

            }
        }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<Integer>() {
                    @Override
                    public void accept(Integer s) throws Exception {
                        callBack.callBack(s);
                    }
                });
    }


    public static void checkCreate(List<String> AssetID, CallBack<String> callBack) {
        OkGo.<CheckCreateResBean>post(URLConstant.API_Check_InsertCheck_URL)
                .params("AssetID", JSON.toJSONString(AssetID))
                .execute(new JsonCallback<CheckCreateResBean>() {
                    @Override
                    public void onSuccess(Response<CheckCreateResBean> response) {
                        if (response.body().getCode() == 1)
                            callBack.callBack(response.body().CheckID);
                        else
                            CommUtil.ToastU.showToast(response.body().getMsg());
                    }
                });
    }

    public static void checkOperate(String checkId, String status, String remark, String assetId, CallBack<Boolean> callBack) {
        OkGo.<CheckOperateResData>post(URLConstant.API_Check_UpdateInventory_URL)
                .params("CheckID", checkId)
                .params("AssetID", assetId)
                .params("Status", status)
                .params("Remark", remark)
                .execute(new JsonCallback<CheckOperateResData>() {
                    @Override
                    public void onSuccess(Response<CheckOperateResData> response) {
                        if (response.body().getCode() == 1) {
                            new Thread(new Runnable() {
                                @Override
                                public void run() {
                                    XinMaDatabase.getInstance().checkBeanDao().update(response.body().getCheck().get(0));
                                    List<InventoryBean> inventoryBeanList = XinMaDatabase.getInstance().inventoryBeanDao().getAllById(checkId, assetId);
                                    if (inventoryBeanList.size()>0){
                                        inventoryBeanList.get(0).setStatus(status);
                                        XinMaDatabase.getInstance().inventoryBeanDao().update( inventoryBeanList.get(0));
                                    }
                                    callBack.callBack(true);
                                }
                            }).start();

                        } else {
                            CommUtil.ToastU.showToast(response.body().getMsg());
                        }
                    }
                });
    }
}

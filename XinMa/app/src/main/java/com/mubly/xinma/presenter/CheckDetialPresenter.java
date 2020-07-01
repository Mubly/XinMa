package com.mubly.xinma.presenter;

import com.lzy.okgo.OkGo;
import com.lzy.okgo.model.Response;
import com.mubly.xinma.R;
import com.mubly.xinma.adapter.SmartAdapter;
import com.mubly.xinma.base.BaseModel;
import com.mubly.xinma.base.BasePresenter;
import com.mubly.xinma.common.Constant;
import com.mubly.xinma.common.weight.RotateTextView;
import com.mubly.xinma.db.XinMaDatabase;
import com.mubly.xinma.iview.ICheckDetialView;
import com.mubly.xinma.model.AssetBean;
import com.mubly.xinma.model.CheckData;
import com.mubly.xinma.model.InventoryBean;
import com.mubly.xinma.net.JsonCallback;
import com.mubly.xinma.net.URLConstant;
import com.mubly.xinma.utils.CommUtil;

import java.util.ArrayList;
import java.util.List;

import androidx.lifecycle.MutableLiveData;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

public class CheckDetialPresenter extends BasePresenter<ICheckDetialView> {
    private String checkId;
    public MutableLiveData<String> currentTime = new MutableLiveData<>();
    public MutableLiveData<String> waitCheck = new MutableLiveData<>();
    public MutableLiveData<String> goodOne = new MutableLiveData<>();
    public MutableLiveData<String> diff = new MutableLiveData<>();
    public MutableLiveData<String> less = new MutableLiveData<>();
    SmartAdapter<AssetBean> adapter = null;
    private List<AssetBean> dataList = new ArrayList<>();

    public MutableLiveData<String> getCurrentTime() {
        return currentTime;
    }

    public MutableLiveData<String> getWaitCheck() {
        return waitCheck;
    }

    public MutableLiveData<String> getGoodOne() {
        return goodOne;
    }

    public MutableLiveData<String> getDiff() {
        return diff;
    }

    public MutableLiveData<String> getLess() {
        return less;
    }

    public CheckDetialPresenter() {
        currentTime.setValue(CommUtil.getCurrentTime());
        waitCheck.setValue("待盘点(0)");
        goodOne.setValue("正常(0)");
        diff.setValue("差异(0)");
        less.setValue("亏损(0)");
        loadData();
    }

    private void loadData() {
        CheckData.getNetCheckData();
    }

    public void manualAdd() {
    }

    public void scanAdd() {
    }

    public void init() {
        adapter = new SmartAdapter<AssetBean>(dataList) {
            @Override
            public int getLayout(int viewType) {
                return R.layout.item_check_info_inventory_layout;
            }

            @Override
            public void dealView(VH holder, AssetBean data, int position) {
                ImageUrlPersenter imageUrlPersenter = new ImageUrlPersenter();
                RotateTextView textView = (RotateTextView) holder.getChildView(R.id.status_name);
                holder.setNetImage(R.id.item_check_inventory_img, imageUrlPersenter.getAssetListUrl(data.getHeadimg()));
                holder.setText(R.id.item_check_inventory_name, data.getAssetName());
                holder.setText(R.id.item_check_inventory_type, data.getAssetModel());
                textView.setText(data.getStatusName());
                holder.setLocalImg(R.id.item_check_inventory_type_img, imageUrlPersenter.getAssetIcon(data.getStatus()));
            }
        };
        getMvpView().showRv(adapter);
    }

    public void initTab(String checkId) {
        this.checkId = checkId;
        Observable.create(new ObservableOnSubscribe<List<String>>() {
            @Override
            public void subscribe(ObservableEmitter<List<String>> emitter) throws Exception {
                List<String> tabCount = new ArrayList<>();
                List<InventoryBean> inventoryBeans1 = XinMaDatabase.getInstance().inventoryBeanDao().getAllByCheckId(checkId, "0");
                List<InventoryBean> inventoryBeans2 = XinMaDatabase.getInstance().inventoryBeanDao().getAllByCheckId(checkId, "1");
                List<InventoryBean> inventoryBeans3 = XinMaDatabase.getInstance().inventoryBeanDao().getAllByCheckId(checkId, "2");
                List<InventoryBean> inventoryBeans4 = XinMaDatabase.getInstance().inventoryBeanDao().getAllByCheckId(checkId, "3");
                tabCount.add(String.valueOf(inventoryBeans1.size()));
                tabCount.add(String.valueOf(inventoryBeans2.size()));
                tabCount.add(String.valueOf(inventoryBeans3.size()));
                tabCount.add(String.valueOf(inventoryBeans4.size()));
                emitter.onNext(tabCount);
            }
        }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<List<String>>() {
                    @Override
                    public void accept(List<String> strings) throws Exception {
                        waitCheck.setValue("待盘点(" + strings.get(0) + ")");
                        goodOne.setValue("正常(" + strings.get(1) + ")");
                        diff.setValue("差异(" + strings.get(2) + ")");
                        less.setValue("亏损(" + strings.get(3) + ")");
                    }
                });
    }

    public void initdata(String checkId, String status) {
        this.checkId = checkId;
        Observable.create(new ObservableOnSubscribe<List<AssetBean>>() {

            @Override
            public void subscribe(ObservableEmitter<List<AssetBean>> emitter) throws Exception {
                List<AssetBean> allAssetsList = new ArrayList<>();
                List<InventoryBean> inventoryBeans = XinMaDatabase.getInstance().inventoryBeanDao().getAllByCheckId(checkId, status);
                for (InventoryBean inventoryBean : inventoryBeans) {
                    List<AssetBean> assetBeans = XinMaDatabase.getInstance().assetBeanDao().getAllById(inventoryBean.getAssetID());
                    if (assetBeans.size() > 0)
                        allAssetsList.add(assetBeans.get(0));
                }
                emitter.onNext(allAssetsList);
            }
        }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<List<AssetBean>>() {
                    @Override
                    public void accept(List<AssetBean> assetBeans) throws Exception {
                        dataList.clear();
                        dataList.addAll(assetBeans);
                        adapter.notifyDataSetChanged();
                        if (status.equals("0")) {
                            waitCheck.setValue("待盘点(" + dataList.size() + ")");
                        } else if (status.equals("1")) {
                            goodOne.setValue("正常(" + dataList.size() + ")");
                        } else if (status.equals("2")) {
                            diff.setValue("差异(" + dataList.size() + ")");
                        } else if (status.equals("3")) {
                            less.setValue("亏损(" + dataList.size() + ")");
                        }
                    }
                });

    }

    public void delect() {
        getMvpView().showDelectpromapt();

    }
    public void delectAck(){
        OkGo.<BaseModel>post(URLConstant.API_Check_DELECT_URL)
                .params("CheckId", checkId)
                .execute(new JsonCallback<BaseModel>() {
                    @Override
                    public void onSuccess(Response<BaseModel> response) {
                        if (response.body().getCode() == 1) {
                            delectLocal();
                        }
                    }
                });
    }

    private void delectLocal() {
        Observable.create(new ObservableOnSubscribe<Boolean>() {
            @Override
            public void subscribe(ObservableEmitter<Boolean> emitter) throws Exception {
                XinMaDatabase.getInstance().checkBeanDao().deleteById(checkId);
                XinMaDatabase.getInstance().inventoryBeanDao().deleteByCheckId(checkId);
                emitter.onNext(true);
            }
        }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<Boolean>() {
                    @Override
                    public void accept(Boolean aBoolean) throws Exception {
                        getMvpView().delectSuc();
                    }
                });
    }
}

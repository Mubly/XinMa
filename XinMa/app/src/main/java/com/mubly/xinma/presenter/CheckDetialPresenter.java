package com.mubly.xinma.presenter;

import com.mubly.xinma.R;
import com.mubly.xinma.adapter.SmartAdapter;
import com.mubly.xinma.base.BasePresenter;
import com.mubly.xinma.iview.ICheckDetialView;
import com.mubly.xinma.model.AssetBean;
import com.mubly.xinma.model.InventoryBean;

import java.util.ArrayList;
import java.util.List;

import androidx.lifecycle.MutableLiveData;

public class CheckDetialPresenter extends BasePresenter<ICheckDetialView> {
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
        waitCheck.setValue("待盘点(0)");
        goodOne.setValue("正常(0)");
        diff.setValue("差异(0)");
        less.setValue("亏损(0)");
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

            }
        };
    }
}

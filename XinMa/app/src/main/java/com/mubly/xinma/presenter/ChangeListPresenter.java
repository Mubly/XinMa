package com.mubly.xinma.presenter;

import android.graphics.Color;

import com.mubly.xinma.R;
import com.mubly.xinma.adapter.SmartAdapter;
import com.mubly.xinma.base.BasePresenter;
import com.mubly.xinma.common.CallBack;
import com.mubly.xinma.common.weight.TimeLineView;
import com.mubly.xinma.iview.IOperateLogListView;
import com.mubly.xinma.model.AssetBean;
import com.mubly.xinma.model.ChangeBean;
import com.mubly.xinma.model.ChangeDataBean;
import com.mubly.xinma.model.OperateData;
import com.mubly.xinma.model.ProcessBean;
import com.mubly.xinma.utils.CommUtil;
import com.mubly.xinma.utils.StringUtils;

import java.util.ArrayList;
import java.util.List;

import androidx.lifecycle.MutableLiveData;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class ChangeListPresenter extends BasePresenter<IOperateLogListView> {
    private MutableLiveData<String> currentPrice = new MutableLiveData<>();
    private MutableLiveData<String> originalPrice = new MutableLiveData<>();
    private List<ChangeBean> dataList = new ArrayList<>();
    String assetId;
    SmartAdapter<ChangeBean> adapter = null;

    public MutableLiveData<String> getCurrentPrice() {
        return currentPrice;
    }

    public MutableLiveData<String> getOriginalPrice() {
        return originalPrice;
    }

    public void init(String assetId) {
        this.assetId = assetId;
        currentPrice.setValue("0.00");
        originalPrice.setValue("0.00");
        adapter = new SmartAdapter<ChangeBean>(dataList) {
            @Override
            public int getLayout(int viewType) {
                return R.layout.item_change_layout;
            }

            @Override
            public void dealView(VH holder, ChangeBean data, int position) {
                holder.setText(R.id.change_create_time, data.getChangeTime());
                holder.setText(R.id.change_create_reason, data.getTag());
                holder.setText(R.id.change_create_count, CommUtil.getDouble2String(data.getDecrease()));
            }
        };
        getMvpView().showRv(adapter);
        getChangeData();
    }

    public void getChangeData() {
        dataList.clear();
        ChangeDataBean.gainChangeData(assetId, new CallBack<ChangeDataBean>() {
            @Override
            public void callBack(ChangeDataBean obj) {
                if (obj.getCode() == 1) {
                    if (obj.getChange() != null)
                        dataList.addAll(obj.getChange());
                    adapter.notifyDataSetChanged();
                }else {
                    getMvpView().checkNetCode(obj.getCode(),obj.getMsg());
                }
            }
        });
    }
}

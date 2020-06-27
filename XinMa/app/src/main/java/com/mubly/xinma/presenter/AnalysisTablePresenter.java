package com.mubly.xinma.presenter;

import com.github.mikephil.charting.data.PieEntry;
import com.mubly.xinma.db.DataBaseUtils;
import com.mubly.xinma.iview.IAnalysisTableView;
import com.mubly.xinma.model.AssetBean;
import com.mubly.xinma.utils.ArithUtils;
import com.mubly.xinma.utils.CommUtil;

import java.util.ArrayList;
import java.util.List;

import androidx.lifecycle.MutableLiveData;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class AnalysisTablePresenter {
    private IAnalysisTableView rootView;
    private int type;
    private MutableLiveData<String> ldleValue = new MutableLiveData<>();
    private MutableLiveData<String> usingValue = new MutableLiveData<>();
    private MutableLiveData<String> brrorowValue = new MutableLiveData<>();
    private MutableLiveData<String> repairValue = new MutableLiveData<>();
    private MutableLiveData<String> disposeValue = new MutableLiveData<>();
    private List<PieEntry> dataList = new ArrayList<>();

    public MutableLiveData<String> getLdleValue() {
        return ldleValue;
    }

    public MutableLiveData<String> getUsingValue() {
        return usingValue;
    }

    public MutableLiveData<String> getBrrorowValue() {
        return brrorowValue;
    }

    public MutableLiveData<String> getRepairValue() {
        return repairValue;
    }

    public MutableLiveData<String> getDisposeValue() {
        return disposeValue;
    }

    public AnalysisTablePresenter(int type, IAnalysisTableView rootView) {
        this.type = type;
        this.rootView = rootView;
        init();
    }

    private void init() {
        dataList.clear();
        initData();
    }

    public void initData() {
        Observable.create(new ObservableOnSubscribe<List<PieEntry>>() {
            @Override
            public void subscribe(ObservableEmitter<List<PieEntry>> emitter) throws Exception {
                double ldle = 0, use = 0, brorrow = 0, repair = 0, disopse = 0;
                List<PieEntry> pieEntries = new ArrayList<>();
                List<AssetBean> assetBeanList = DataBaseUtils.getInstance().getAssetBeanList();
                if (null != assetBeanList) {
                    for (AssetBean assetBean : assetBeanList) {
                        if (type == 0) {
                            if (assetBean.getStatus().equals("1"))
                                ldle++;
                            else if (assetBean.getStatus().equals("3"))
                                use++;
                            else if (assetBean.getStatus().equals("5"))
                                brorrow++;
                            else if (assetBean.getStatus().equals("6"))
                                repair++;
                            else if (assetBean.getStatus().equals("8"))
                                disopse++;
                        } else {
                            if (assetBean.getStatus().equals("1"))
                                ldle = ArithUtils.add(ldle, Double.valueOf(assetBean.getOriginal()));
                            else if (assetBean.getStatus().equals("3"))
                                use = ArithUtils.add(use, Double.valueOf(assetBean.getOriginal()));
                            else if (assetBean.getStatus().equals("5"))
                                brorrow = ArithUtils.add(brorrow, Double.valueOf(assetBean.getOriginal()));
                            else if (assetBean.getStatus().equals("6"))
                                repair = ArithUtils.add(repair, Double.valueOf(assetBean.getOriginal()));
                            else if (assetBean.getStatus().equals("8"))
                                disopse = ArithUtils.add(disopse, Double.valueOf(assetBean.getOriginal()));
                        }
                    }
                }
                pieEntries.add(new PieEntry((float) ldle, "闲置"));
                pieEntries.add(new PieEntry((float) use, "在用"));
                pieEntries.add(new PieEntry((float) brorrow, "借用"));
                pieEntries.add(new PieEntry((float) repair, "维修"));
                pieEntries.add(new PieEntry((float) disopse, "处置"));
                emitter.onNext(pieEntries);


            }
        }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<List<PieEntry>>() {
                    @Override
                    public void accept(List<PieEntry> pieEntries) throws Exception {
                        ldleValue.setValue(CommUtil.getDoubleString(Double.valueOf(String.valueOf(pieEntries.get(0).getValue()))));
                        usingValue.setValue(CommUtil.getDoubleString(Double.valueOf(String.valueOf(pieEntries.get(1).getValue()))));
                        brrorowValue.setValue(CommUtil.getDoubleString(Double.valueOf(String.valueOf(pieEntries.get(2).getValue()))));
                        repairValue.setValue(CommUtil.getDoubleString(Double.valueOf(String.valueOf(pieEntries.get(3).getValue()))));
                        disposeValue.setValue(CommUtil.getDoubleString(Double.valueOf(String.valueOf(pieEntries.get(4).getValue()))));
                        float totalValue = pieEntries.get(0).getValue() + pieEntries.get(1).getValue()
                                + pieEntries.get(2).getValue() + pieEntries.get(3).getValue()
                                + pieEntries.get(4).getValue();
                        dataList.addAll(pieEntries);
                        rootView.showPie(dataList,CommUtil.getDoubleString(Double.valueOf(String.valueOf(totalValue))));
                    }
                });
    }
}

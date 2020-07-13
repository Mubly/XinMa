package com.mubly.xinma.presenter;

import android.drm.ProcessedData;
import android.graphics.Color;
import android.view.View;

import com.mubly.xinma.R;
import com.mubly.xinma.adapter.SmartAdapter;
import com.mubly.xinma.base.BasePresenter;
import com.mubly.xinma.common.CallBack;
import com.mubly.xinma.common.weight.TimeLineView;
import com.mubly.xinma.iview.IOperateLogListView;
import com.mubly.xinma.model.OperateData;
import com.mubly.xinma.model.ProcessBean;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class OperateLogListPresenter extends BasePresenter<IOperateLogListView> {
    private List<ProcessBean> dataList = new ArrayList<>();
    SmartAdapter<ProcessBean> adapter = null;
    private String assetId;

    public void init(String assetId) {
        this.assetId = assetId;
        adapter = new SmartAdapter<ProcessBean>(dataList) {
            @Override
            public int getLayout(int viewType) {
                return R.layout.item_operate_log_layout;
            }

            @Override
            public void dealView(VH holder, ProcessBean data, int position) {
                holder.setText(R.id.process_title_label, "[" + data.getProcessCate() + "]");
                holder.setText(R.id.process_depart_staff, data.getDepart() + "-" + data.getStaff());
                holder.setText(R.id.process_create_time_label, data.getProcessCate() + "时间");
                holder.setText(R.id.process_create_time, data.getProcessTime());
                TimeLineView lineView = (TimeLineView) holder.getChildView(R.id.time_line_view);
                if (data.getProcessCate().equals("维修")) {
                    lineView.setBgColor(Color.parseColor("#57bc6a"));
                    holder.setTextColor(R.id.process_title_label, Color.parseColor("#57bc6a"));
                } else if (data.getProcessCate().equals("领用")) {
                    lineView.setBgColor(Color.parseColor("#d76b54"));
                    holder.setTextColor(R.id.process_title_label, Color.parseColor("#d76b54"));
                } else if (data.getProcessCate().equals("盘点")) {
                    lineView.setBgColor(Color.parseColor("#ec6e2c"));
                    holder.setTextColor(R.id.process_title_label, Color.parseColor("#ec6e2c"));
                } else if (data.getProcessCate().equals("变更")) {
                    lineView.setBgColor(Color.parseColor("#4dacf7"));
                    holder.setTextColor(R.id.process_title_label, Color.parseColor("#4dacf7"));
                } else if (data.getProcessCate().equals("借用")) {
                    lineView.setBgColor(Color.parseColor("#00bfa5"));
                    holder.setTextColor(R.id.process_title_label, Color.parseColor("#00bfa5"));
                } else if (data.getProcessCate().equals("归还")) {
                    lineView.setBgColor(Color.parseColor("#527BD7"));
                    holder.setTextColor(R.id.process_title_label, Color.parseColor("#527BD7"));
                } else {
                    lineView.setBgColor(Color.parseColor("#cccccc"));
                    holder.setTextColor(R.id.process_title_label, Color.parseColor("#cccccc"));
                }
                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (data.getProcessCate().equals("变更")) {
                            getMvpView().toChangeView(data);
                        } else {
                            getMvpView().toDesPage(data.getOperateID(), data.getProcessCate());
                        }

                    }
                });
            }
        };
        getMvpView().showRv(adapter);
        initData();

    }

    public void initData() {
        OperateData.getOperateLogList(assetId, new CallBack<OperateData>() {
            @Override
            public void callBack(OperateData obj) {
                if (obj.getCode() == 1) {
                    dataList.clear();
                    if (null != obj.getProcess()) {
                        dataList.addAll(obj.getProcess());
                    }

                    adapter.notifyDataSetChanged();
                } else {
                    getMvpView().checkNetCode(obj.getCode(), obj.getMsg());
                }
            }
        });

//        OperateData.getOperateLog(new CallBack<OperateData>() {
//            @Override
//            public void callBack(OperateData obj) {
//                if (obj.getCode() == 1) {
//                    dealData(obj);
//                } else {
//                    getMvpView().checkNetCode(obj.getCode(), obj.getMsg());
//                }
//            }
//        });
    }

    private void dealData(OperateData obj) {
        Observable.create(new ObservableOnSubscribe<List<ProcessBean>>() {
            @Override
            public void subscribe(ObservableEmitter<List<ProcessBean>> emitter) throws Exception {
                List<ProcessBean> selectList = new ArrayList<>();

                if (null != obj && obj.getProcess() != null) {
                    for (ProcessBean processBean : obj.getProcess()) {
                        if (processBean.getAssetID().equals(assetId)) {
                            selectList.add(processBean);
                        }
                    }
                    emitter.onNext(selectList);
                }
            }
        }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<List<ProcessBean>>() {
                    @Override
                    public void accept(List<ProcessBean> processBeans) throws Exception {
                        dataList.clear();
                        dataList.addAll(processBeans);
                        adapter.notifyDataSetChanged();
                    }
                });
    }
}

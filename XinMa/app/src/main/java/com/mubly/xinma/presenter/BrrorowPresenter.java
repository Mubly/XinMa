package com.mubly.xinma.presenter;

import com.mubly.xinma.activity.ScannerActivity;
import com.mubly.xinma.adapter.AssetsListCallBackAdapter;
import com.mubly.xinma.base.BasePresenter;
import com.mubly.xinma.common.CallBack;
import com.mubly.xinma.iview.IBrrorowView;
import com.mubly.xinma.iview.IReturnView;
import com.mubly.xinma.model.AssetBean;
import com.mubly.xinma.model.OperateData;
import com.mubly.xinma.model.ProcessBean;
import com.mubly.xinma.net.EnvironmentType;
import com.mubly.xinma.utils.CommUtil;

import java.util.ArrayList;
import java.util.List;

import androidx.lifecycle.MutableLiveData;

public class BrrorowPresenter extends BaseOperatPresenter<IBrrorowView> {
    List<AssetBean> selectDataList = new ArrayList<>();
    AssetsListCallBackAdapter adapter = null;
    MutableLiveData<String> creatDate = new MutableLiveData<>();
    private boolean hideDelect;

    public void setHideDelect(boolean hideDelect) {
        this.hideDelect = hideDelect;
    }
    public MutableLiveData<String> getCreatDate() {
        return creatDate;
    }

    @Override
    public void init() {
        creatDate.setValue(CommUtil.getCurrentTimeHM());
        adapter = new AssetsListCallBackAdapter(selectDataList,hideDelect);
        getMvpView().showRv(adapter);
    }

    @Override
    public void scanAdd() {
        getMvpView().startActivityForResult(ScannerActivity.class,666);
    }

    @Override
    public void manualAdd() {
        getMvpView().toSelectAssetsAct();
    }

    @Override
    public void notifyDataChange(List<AssetBean> newDataList) {
        selectDataList.clear();
        selectDataList.addAll(newDataList);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void gainOperateData(String operateID) {
        OperateData.getOperateAssetInfo(operateID, new CallBack<OperateData>() {
            @Override
            public void callBack(OperateData obj) {
                if (obj.getCode() == 1) {
                    if (obj.getOperate() != null && obj.getOperate().size() > 0) {
                        getMvpView().showOperateLogInfo(obj.getOperate().get(0));
                    }
                    if (null != obj.getProcess() && obj.getProcess().size() > 0) {
                        makeAssetBeanData(obj.getProcess());
                    }
                } else {
                    getMvpView().checkNetCode(obj.getCode(), obj.getMsg());
                }
            }
        });
    }

    private void makeAssetBeanData(List<ProcessBean> process) {
        selectDataList.clear();
        for (ProcessBean bean : process) {
            AssetBean assetBean = new AssetBean();
            assetBean.setHeadimg(bean.getHeadimg());
            assetBean.setAssetModel(bean.getAssetModel());
            assetBean.setAssetName(bean.getAssetName());
            assetBean.setAssetNo(bean.getAssetNo());
            assetBean.setAssetID(bean.getAssetID());
            selectDataList.add(assetBean);
        }
        adapter.notifyDataSetChanged();
    }
}

package com.mubly.xinma.activity;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.bigkoo.pickerview.view.OptionsPickerView;
import com.mubly.xinma.R;
import com.mubly.xinma.adapter.AssetsListCallBackAdapter;
import com.mubly.xinma.base.BaseActivity;
import com.mubly.xinma.base.BaseOperateActivity;
import com.mubly.xinma.common.CallBack;
import com.mubly.xinma.common.GroupSelectCallBack;
import com.mubly.xinma.databinding.ActivityReturnBinding;
import com.mubly.xinma.db.XinMaDatabase;
import com.mubly.xinma.iview.IReturnView;
import com.mubly.xinma.model.AssetBean;
import com.mubly.xinma.model.AssetParam;
import com.mubly.xinma.model.GroupBean;
import com.mubly.xinma.model.SelectAssetsBean;
import com.mubly.xinma.model.StaffBean;
import com.mubly.xinma.model.resbean.OperateDataRes;
import com.mubly.xinma.presenter.ReturnPresenter;
import com.mubly.xinma.utils.CommUtil;
import com.mubly.xinma.utils.DialogUtils;
import com.mubly.xinma.utils.EditViewUtil;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import static com.mubly.xinma.activity.AssetSelectActivity.BRROROW_REQUEST_CODE;
import static com.mubly.xinma.activity.AssetSelectActivity.RESULT_OK_CODE;
import static com.mubly.xinma.activity.AssetSelectActivity.RETURN_REQUEST_CODE;

public class ReturnActivity extends BaseOperateActivity<ReturnPresenter, IReturnView> implements IReturnView {

    ActivityReturnBinding binding = null;
    SelectAssetsBean selectAssetsBean = null;
    private OptionsPickerView assetStatusDialog;
    private List<String> returnStatusList = new ArrayList<>();
    private String ProcessCate = "归还";
    private String Depart;

    private String Staff;
    private String Seat;

    private String Remark;
    private JSONArray AssetIDList = new JSONArray();
    @Override
    protected ReturnPresenter createPresenter() {
        return new ReturnPresenter();
    }

    @Override
    public void initView() {
        setTitle(R.string.return_name);
        setRightTv("保存");
        binding.setPresenter(mPresenter);
        binding.setLifecycleOwner(this);
        mPresenter.init();
        initReturnStatus();
    }

    @Override
    public void onRightClickEvent(TextView rightTv) {
        super.onRightClickEvent(rightTv);
        if (TextUtils.isEmpty(Depart) || TextUtils.isEmpty(Seat)) {
            CommUtil.ToastU.showToast("请完善归还信息");
            return;
        }
        if (null == selectAssetsBean || selectAssetsBean.getSelectBean() == null || selectAssetsBean.getSelectBean().size() < 1) {
            CommUtil.ToastU.showToast("请添加要领用的资产");
            return;
        } else {
            AssetIDList = new JSONArray();
            for (AssetBean bean : selectAssetsBean.getSelectBean()) {
                JSONObject object = new JSONObject();
                try {
                    object.put("AssetID", bean.getAssetID());
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                AssetIDList.put(object);
            }
        }
        mPresenter.operate(ProcessCate, mPresenter.getCreatDate().getValue(), Depart, Staff, Seat, Remark, AssetIDList.toString(), null, new CallBack<OperateDataRes>() {
            @Override
            public void callBack(OperateDataRes obj) {
                CommUtil.ToastU.showToast("归还成功");
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        for (AssetBean bean : selectAssetsBean.getSelectBean()) {
                            bean.setStatusName("闲置");
                            bean.setStatus("1");
                            bean.setDepart(Depart);
                            bean.setStaff(Staff);
                            bean.setSeat(Seat);
                            bean.setLastProcessTime(mPresenter.getCreatDate().getValue());
                            XinMaDatabase.getInstance().assetBeanDao().update(bean);
                        }
                        closeAllAct();
                    }
                }).start();
            }
        });
    }
    @Override
    public void initEvent() {
        super.initEvent();
        binding.returnTimeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showTimeSelectDialog(new CallBack<String>() {
                    @Override
                    public void callBack(String obj) {
                        mPresenter.getCreatDate().setValue(obj);
                    }
                });
            }
        });
        binding.returnDepartLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showGroupStaffDialog(new GroupSelectCallBack() {
                    @Override
                    public void callback(GroupBean groupBean, StaffBean staffBean) {
                        Depart = groupBean.getDepart();
                        Staff = staffBean.getStaff();
                        binding.returnDepartTv.setText(groupBean.getDepart());
                        binding.returnStaffTv.setText(staffBean.getStaff());
                    }
                });
            }
        });
        binding.returnStatusLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                assetStatusDialog.show();
            }
        });
        EditViewUtil.EditDatachangeLister(binding.returnAddressTv, new CallBack<String>() {
            @Override
            public void callBack(String obj) {
                Seat = obj;
            }
        });
    }
    @Override
    protected void getLayoutId() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_return);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK_CODE) {
            selectAssetsBean = (SelectAssetsBean) data.getSerializableExtra("selectData");
            mPresenter.notifyDataChange(selectAssetsBean.getSelectBean());
        }
    }

    @Override
    public void toSelectAssetsAct() {
        Intent intent = new Intent(this, AssetSelectActivity.class);
        intent.putExtra("status", "1");
        if (null != selectAssetsBean) {
            intent.putExtra("selectedData", selectAssetsBean);
        }
        startActivityForResult(intent, RETURN_REQUEST_CODE);
    }

    @Override
    public void showRv(AssetsListCallBackAdapter adapter) {
        binding.returnRv.setLayoutManager(new LinearLayoutManager(this));
        binding.returnRv.setAdapter(adapter);
    }

    @Override
    public boolean isTimeSelectInit() {
        return true;
    }

    @Override
    public boolean showSecond() {
        return true;
    }

    @Override
    public boolean isCategorySelectInit() {
        return false;
    }

    @Override
    public boolean isGroupSelectInit() {
        return true;
    }

    private void initReturnStatus() {
        returnStatusList.clear();
        returnStatusList.add("外观及功能完好");
        returnStatusList.add("外观破损");
        returnStatusList.add("功能异常");
        returnStatusList.add("外观破损且功能异常");

        assetStatusDialog = DialogUtils.showSelectDialog(this, new DialogUtils.SelectListener() {
            @Override
            public void selected(int index1, int index2, int index3, View v) {
                Seat = returnStatusList.get(index1);
                binding.returnStatusTv.setText(Seat);
            }
        });
        assetStatusDialog.setPicker(returnStatusList);
    }
}

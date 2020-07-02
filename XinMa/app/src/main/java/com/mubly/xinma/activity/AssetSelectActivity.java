package com.mubly.xinma.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bigkoo.pickerview.builder.OptionsPickerBuilder;
import com.bigkoo.pickerview.builder.TimePickerBuilder;
import com.bigkoo.pickerview.listener.OnOptionsSelectChangeListener;
import com.bigkoo.pickerview.listener.OnOptionsSelectListener;
import com.bigkoo.pickerview.listener.OnTimeSelectChangeListener;
import com.bigkoo.pickerview.listener.OnTimeSelectListener;
import com.bigkoo.pickerview.view.OptionsPickerView;
import com.bigkoo.pickerview.view.TimePickerView;
import com.mubly.xinma.R;
import com.mubly.xinma.adapter.SmartAdapter;
import com.mubly.xinma.base.BaseActivity;
import com.mubly.xinma.databinding.ActivityAssetSelectBinding;
import com.mubly.xinma.databinding.FilterLayoutBinding;
import com.mubly.xinma.db.XinMaDatabase;
import com.mubly.xinma.iview.IAssetSelectView;
import com.mubly.xinma.model.AssetBean;
import com.mubly.xinma.model.CategoryBean;
import com.mubly.xinma.model.GroupBean;
import com.mubly.xinma.model.SelectAssetsBean;
import com.mubly.xinma.model.StaffBean;
import com.mubly.xinma.presenter.AssetSelectPresenter;
import com.mubly.xinma.utils.CommUtil;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

public class AssetSelectActivity extends BaseActivity<AssetSelectPresenter, IAssetSelectView> implements IAssetSelectView {
    ActivityAssetSelectBinding binding = null;
    public static final int GET_USE_REQUEST_CODE = 1001;//领用
    public static final int BRROROW_REQUEST_CODE = 1002;//借用
    public static final int RETURN_REQUEST_CODE = 1003;//归还
    public static final int REPAIR_REQUEST_CODE = 1004;//维修
    public static final int CHECK_CREATE_REQUEST_CODE = 1005;//盘点创建

    //    返回码
    public static final int RESULT_OK_CODE = 10010;//数据返回成功
    public static final int RESULT_NULL_CODE = 10011;//无数据返回成功
    ConstraintLayout filterLayout;
    LinearLayout filterContentLayout;
    //记录之前选中的
    private SelectAssetsBean selectBean;
    //资产的状态 默认全部
    private String status = null;


    //    筛选区
    TimePickerView pvTime;
    TextView selectTv;
    private OptionsPickerView pvOptions, departMentPicker;
    private List<String> options1Items = new ArrayList<>();

    private List<GroupBean> groupBeanList = new ArrayList<>();
    private List<List<StaffBean>> staffBeanList = new ArrayList<>();

    private boolean filterInit;

    private List<CategoryBean> categoryBeanList = new ArrayList<>();


    @Override
    public void initView() {
        setTitle(R.string.assets_name);
        setRightTv("确认");
        mPresenter.init(selectBean, status);
        initfilterData();
        initFilter();
    }

    @Override
    protected AssetSelectPresenter createPresenter() {
        return new AssetSelectPresenter();
    }

    @Override
    protected void getLayoutId() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_asset_select);
        selectBean = (SelectAssetsBean) getIntent().getSerializableExtra("selectedData");
        status = getIntent().getStringExtra("status");
        filterLayout = findViewById(R.id.filter_layout);
        filterContentLayout = findViewById(R.id.filter_content_layout);
        filterLayout.setOnClickListener(this);
        filterContentLayout.setOnClickListener(this);
        binding.sortAssetBtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        super.onClick(view);
        switch (view.getId()) {
            case R.id.filter_layout:
                filterLayout.setVisibility(View.GONE);
                break;
            case R.id.filter_content_layout:
                break;
            case R.id.sort_asset_btn:
                filterLayout.setVisibility(View.VISIBLE);
                break;
        }
    }

    private void initfilterData() {
        if (filterInit) return;
        Observable.create(new ObservableOnSubscribe<List<CategoryBean>>() {
            @Override
            public void subscribe(ObservableEmitter<List<CategoryBean>> emitter) throws Exception {
                emitter.onNext(XinMaDatabase.getInstance().categoryDao().getAll());
            }
        }).
                subscribeOn(Schedulers.io())
                .subscribe(new Consumer<List<CategoryBean>>() {
                    @Override
                    public void accept(List<CategoryBean> categoryBeans) throws Exception {
                        CategoryBean categoryBean = new CategoryBean();
                        categoryBean.setCategory("无限制");
                        categoryBeanList.add(categoryBean);
                        categoryBeanList.addAll(categoryBeans);
                        filterInit = true;
                    }
                });
        Observable.create(new ObservableOnSubscribe<List<GroupBean>>() {
            @Override
            public void subscribe(ObservableEmitter<List<GroupBean>> emitter) throws Exception {

                emitter.onNext(XinMaDatabase.getInstance().groupBeanDao().getAll());
            }
        })
                .subscribeOn(Schedulers.io())
                .map(new Function<List<GroupBean>, List<List<StaffBean>>>() {
                    @Override
                    public List<List<StaffBean>> apply(List<GroupBean> groupBeanList) throws Exception {
                        groupBeanList.addAll(groupBeanList);
                        List<List<StaffBean>> staffArr = new ArrayList<>();
                        List<StaffBean> topStaff = new ArrayList<>();
                        StaffBean staffBean = new StaffBean();
                        staffBean.setStaff("无限制");
                        topStaff.add(staffBean);
                        staffArr.add(topStaff);
                        for (GroupBean groupBean : groupBeanList) {
                            staffArr.add(XinMaDatabase.getInstance().staffBeanDao().getAllByDepartId(groupBean.getDepartID()));
                        }
                        return staffArr;
                    }
                }).subscribe(new Consumer<List<List<StaffBean>>>() {
            @Override
            public void accept(List<List<StaffBean>> lists) throws Exception {
                GroupBean groupBean = new GroupBean();
                groupBean.setDepart("无限制");
                groupBeanList.add(0, groupBean);
                filterInit = true;
            }
        });
    }

    @Override
    public void onRightClickEvent(TextView rightTv) {
        super.onRightClickEvent(rightTv);
        Intent intent = new Intent();
        if (null != mPresenter.getLocalSelectBean()) {
            intent.putExtra("selectData", mPresenter.getLocalSelectBean());
            setResult(RESULT_OK_CODE, intent);
        } else {
            setResult(RESULT_NULL_CODE);
        }
        finish();
    }

    @Override
    public void showRv(SmartAdapter adapter) {
        binding.assetSelectRv.setLayoutManager(new LinearLayoutManager(this));
        binding.assetSelectRv.setAdapter(adapter);
    }

    private void initFilter() {
        initTimePicker();
        initCategoryPicker();
        initDepartmentPicker();
        FilterLayoutBinding filterBinding = DataBindingUtil.bind(filterLayout);
        filterBinding.filterDepartmentLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                departMentPicker.show();
            }
        });
        filterBinding.filterCategoryLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectTv = filterBinding.filterCategoryTv;
                pvOptions.show();
            }
        });
        filterBinding.filterStartdateLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectTv = filterBinding.filterStartdateTv;
                pvTime.show(filterBinding.filterStartdateTv);
            }
        });
        filterBinding.filterEnddateLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectTv = filterBinding.filterEnddateTv;
                pvTime.show(filterBinding.filterEnddateTv);
            }
        });
        filterBinding.filterMaturityLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectTv = filterBinding.filterMaturityTv;
                pvTime.show(filterBinding.filterMaturityTv);
            }
        });
    }

    private void initTimePicker() {//Dialog 模式下，在底部弹出
        pvTime = new TimePickerBuilder(this, new OnTimeSelectListener() {
            @Override
            public void onTimeSelect(Date date, View v) {
                selectTv.setText(CommUtil.getTime(date));

            }
        })
                .setType(new boolean[]{true, true, true, false, false, false})
                .isDialog(true) //默认设置false ，内部实现将DecorView 作为它的父控件。
                .setItemVisibleCount(5) //若设置偶数，实际值会加1（比如设置6，则最大可见条目为7）
                .setLineSpacingMultiplier(2.0f)
                .isAlphaGradient(true)
                .build();

        Dialog mDialog = pvTime.getDialog();
        if (mDialog != null) {

            FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT,
                    Gravity.BOTTOM);

            params.leftMargin = 0;
            params.rightMargin = 0;
            pvTime.getDialogContainerLayout().setLayoutParams(params);

            Window dialogWindow = mDialog.getWindow();
            if (dialogWindow != null) {
                dialogWindow.setWindowAnimations(com.bigkoo.pickerview.R.style.picker_view_slide_anim);//修改动画样式
                dialogWindow.setGravity(Gravity.BOTTOM);//改成Bottom,底部显示
                dialogWindow.setDimAmount(0.3f);
            }
        }
    }


    private void initCategoryPicker() {//分类选择
        pvOptions = new OptionsPickerBuilder(this, new OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int options2, int options3, View v) {
                //返回的分别是三个级别的选中位置
                String tx = categoryBeanList.get(options1).toString();
                selectTv.setText(tx);

            }
        })
                .setTitleText("请选择")
                .setContentTextSize(20)//设置滚轮文字大小
                .setDividerColor(Color.LTGRAY)//设置分割线的颜色
                .setSelectOptions(0, 1)//默认选中项
//                .setBgColor(Color.BLACK)
//                .setTitleBgColor(Color.DKGRAY)
//                .setTitleColor(Color.LTGRAY)
//                .setCancelColor(Color.YELLOW)
//                .setSubmitColor(Color.YELLOW)
//                .setTextColorCenter(Color.LTGRAY)
                .isRestoreItem(true)//切换时是否还原，设置默认选中第一项。
                .isCenterLabel(false) //是否只显示中间选中项的label文字，false则每项item全部都带有label。
//                .setLabels("省", "市", "区")
                .setOutSideColor(0x00000000) //设置外部遮罩颜色
//                .setOptionsSelectChangeListener(new OnOptionsSelectChangeListener() {
//                    @Override
//                    public void onOptionsSelectChanged(int options1, int options2, int options3) {
//
//                    }
//                })
                .build();

//        pvOptions.setSelectOptions(1,1);
        pvOptions.setPicker(categoryBeanList);//一级选择器*/
//        pvOptions.setPicker(options1Items, options2Items);//二级选择器
//        /*pvOptions.setPicker(options1Items, options2Items,options3Items);//三级选择器*/
    }


    private void initDepartmentPicker() {//部门选择
        departMentPicker = new OptionsPickerBuilder(this, new OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int options2, int options3, View v) {
                //返回的分别是三个级别的选中位置
                String tx = groupBeanList.get(options1).toString() + staffBeanList.get(options1).get(options2).toString();
                selectTv.setText(tx);

            }
        })
                .setTitleText("请选择")
                .setContentTextSize(20)//设置滚轮文字大小
                .setDividerColor(Color.LTGRAY)//设置分割线的颜色
                .setSelectOptions(0, 1)//默认选中项
//                .setBgColor(Color.BLACK)
//                .setTitleBgColor(Color.DKGRAY)
//                .setTitleColor(Color.LTGRAY)
//                .setCancelColor(Color.YELLOW)
//                .setSubmitColor(Color.YELLOW)
//                .setTextColorCenter(Color.LTGRAY)
                .isRestoreItem(true)//切换时是否还原，设置默认选中第一项。
                .isCenterLabel(false) //是否只显示中间选中项的label文字，false则每项item全部都带有label。
//                .setLabels("省", "市", "区")
                .setOutSideColor(0x00000000) //设置外部遮罩颜色
//                .setOptionsSelectChangeListener(new OnOptionsSelectChangeListener() {
//                    @Override
//                    public void onOptionsSelectChanged(int options1, int options2, int options3) {
//
//                    }
//                })
                .build();

//        pvOptions.setSelectOptions(1,1);
        departMentPicker.setPicker(groupBeanList, staffBeanList);
    }
}

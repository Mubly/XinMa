package com.mubly.xinma.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.EditText;
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
import com.mubly.xinma.common.CallBack;
import com.mubly.xinma.databinding.ActivityAssetSelectBinding;
import com.mubly.xinma.databinding.FilterLayoutBinding;
import com.mubly.xinma.db.XinMaDatabase;
import com.mubly.xinma.iview.IAssetSelectView;
import com.mubly.xinma.model.AssetBean;
import com.mubly.xinma.model.CategoryBean;
import com.mubly.xinma.model.FilterBean;
import com.mubly.xinma.model.GroupBean;
import com.mubly.xinma.model.SelectAssetsBean;
import com.mubly.xinma.model.StaffBean;
import com.mubly.xinma.presenter.AssetSelectPresenter;
import com.mubly.xinma.utils.CommUtil;
import com.mubly.xinma.utils.EditViewUtil;
import com.mubly.xinma.utils.LiveDataBus;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.android.schedulers.AndroidSchedulers;
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
    public static final int DISPOSE_REQUEST_CODE = 1006;//处置
    public static final int PRINT_REQUEST_CODE = 1007;//打印
    private int from;//对应如上code
    //    返回码
    public static final int RESULT_OK_CODE = 10010;//数据返回成功
    public static final int RESULT_NULL_CODE = 10011;//无数据返回成功

    //记录之前选中的
    private SelectAssetsBean selectBean;
    //资产的状态 默认全部
    private String[] statusData = null;

    //    筛选区
    ConstraintLayout filterLayout;
    LinearLayout filterContentLayout;
    TimePickerView pvTime;
    TextView selectTv;
    private OptionsPickerView catoryPicker, periodPicker, departMentPicker;

    private List<GroupBean> groupBeanList = new ArrayList<>();
    private List<List<StaffBean>> staffBeanList = new ArrayList<>();

    private boolean filterInit;

    private List<CategoryBean> categoryBeanList = new ArrayList<>();
    private List<String> periodList = new ArrayList<>();
    private Map<String, String> filterMap = new HashMap<>();

    private EditText searchEt;
    private String departMentId, departMent, staff, staffId, categroyId;
    private String startTime, endTime, peroidTime;
    TextView startTv, endTv;

    @Override
    public void initView() {
        setTitle(R.string.assets_name);
        setRightTv("确认");
        initStatus();
        mPresenter.init(selectBean, statusData);
        initfilterData();
    }
//1闲置3在用5借用6维修8处置
    private void initStatus() {
        switch (from) {
            case GET_USE_REQUEST_CODE://领用
            case DISPOSE_REQUEST_CODE://处置
            case BRROROW_REQUEST_CODE://借用
                statusData = new String[]{"1"};
                break;
            case RETURN_REQUEST_CODE://归还
                statusData = new String[]{"3", "5", "6"};
                break;
            case REPAIR_REQUEST_CODE://维修
                statusData = new String[]{"1", "3", "5"};
                break;

            case CHECK_CREATE_REQUEST_CODE://盘点
                statusData = new String[]{"1", "3", "5", "6"};
                break;
            case PRINT_REQUEST_CODE://打印
                statusData = new String[]{"1", "3", "5", "6"};
            default:
                statusData = new String[]{"1"};
                break;
        }
    }

    @Override
    protected AssetSelectPresenter createPresenter() {
        return new AssetSelectPresenter();
    }

    @Override
    protected void getLayoutId() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_asset_select);
        selectBean = (SelectAssetsBean) getIntent().getSerializableExtra("selectedData");
        from = getIntent().getIntExtra("from", GET_USE_REQUEST_CODE);
        filterLayout = findViewById(R.id.filter_layout);
        filterContentLayout = findViewById(R.id.filter_content_layout);
        searchEt = findViewById(R.id.search_et);
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

    @Override
    public void initEvent() {
        super.initEvent();
        EditViewUtil.EditActionListener(searchEt, new CallBack<String>() {
            @Override
            public void callBack(String obj) {
                if (TextUtils.isEmpty(obj)) return;
                mPresenter.searchDataEt(obj);
            }
        });
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
                    public List<List<StaffBean>> apply(List<GroupBean> groupBeans) throws Exception {
                        groupBeanList.addAll(groupBeans);
                        List<List<StaffBean>> staffArr = new ArrayList<>();
                        List<StaffBean> topStaff = new ArrayList<>();
                        StaffBean staffBean = new StaffBean();
                        staffBean.setStaff("无限制");
                        topStaff.add(staffBean);
                        staffArr.add(topStaff);
                        for (GroupBean groupBean : groupBeanList) {
                            List<StaffBean> localStaffs = XinMaDatabase.getInstance().staffBeanDao().getAllByDepartId(groupBean.getDepartID());
                            localStaffs.add(0, new StaffBean("无限制"));
                            staffArr.add(localStaffs);
                        }
                        return staffArr;
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<List<List<StaffBean>>>() {
                    @Override
                    public void accept(List<List<StaffBean>> lists) throws Exception {
                        staffBeanList.addAll(lists);
                        GroupBean groupBean = new GroupBean();
                        groupBean.setDepart("无限制");
                        groupBeanList.add(0, groupBean);
                        filterInit = true;
                        periodList.add("无限制");
                        periodList.add("已到期");
                        periodList.add("30天到期");
                        initFilter();
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

    @Override
    public void isEmpty(boolean isEmpty) {
        binding.assetSelectEmpty.setVisibility(isEmpty ? View.VISIBLE : View.GONE);
    }

    //筛选模块
    private void initFilter() {
        initTimePicker();
        initPeriodPicker();
        initCategoryPicker();
        initDepartmentPicker();
        FilterLayoutBinding filterBinding = DataBindingUtil.bind(filterLayout);
        filterBinding.filterDepartmentLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectTv = filterBinding.filterDepartmentTv;
                departMentPicker.show();
            }
        });
        filterBinding.filterCategoryLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectTv = filterBinding.filterCategoryTv;
                catoryPicker.show();
            }
        });
        filterBinding.filterStartdateLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectTv = filterBinding.filterStartdateTv;
                pvTime.show(filterBinding.filterStartdateTv);
                startTv = selectTv;
            }
        });
        filterBinding.filterEnddateLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectTv = filterBinding.filterEnddateTv;
                pvTime.show(filterBinding.filterEnddateTv);
                endTv = selectTv;
            }
        });
        filterBinding.filterMaturityLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectTv = filterBinding.filterMaturityTv;
                periodPicker.show();
            }
        });
        filterBinding.resetFilterParam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectTv = null;
                filterMap.clear();
                filterBinding.filterDepartmentTv.setText("无限制");
                filterBinding.filterCategoryTv.setText("无限制");
                filterBinding.filterStartdateTv.setText("无限制");
                filterBinding.filterEnddateTv.setText("无限制");
                filterBinding.filterMaturityTv.setText("无限制");
                categroyId = null;
                departMent = null;
                departMentId = null;
                staff = null;
                staffId = null;
                startTime = null;
                endTime = null;
                peroidTime = null;
            }
        });
        filterBinding.filterAck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FilterBean filterBean = new FilterBean();
                filterBean.setCategoryID(categroyId);
                filterBean.setDepart(departMent);
                filterBean.setDepartID(departMentId);
                filterBean.setStaff(staff);
                filterBean.setStaffID(staffId);
                filterBean.setPurchaseDate(startTime);
                filterBean.setExpireDate(endTime);
                filterBean.setRemainder(peroidTime);
                filterLayout.setVisibility(View.GONE);
                mPresenter.filterData(filterBean);
            }
        });
    }

    //时间选择器
    private void initTimePicker() {//Dialog 模式下，在底部弹出
        pvTime = new TimePickerBuilder(this, new OnTimeSelectListener() {
            @Override
            public void onTimeSelect(Date date, View v) {
                selectTv.setText(CommUtil.getTime(date));
                if (selectTv == startTv)
                    startTime = CommUtil.getTime(date);
                else if (selectTv == endTv)
                    endTime = CommUtil.getTime(date);
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

    //时间段选择器
    private void initPeriodPicker() {//分类选择
        periodPicker = new OptionsPickerBuilder(this, new OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int options2, int options3, View v) {
                String tx = periodList.get(options1);
                selectTv.setText(tx);//TODO 期限需要重新处理一下
                if (options1 == 0) {//无限期
                    peroidTime = "0";
                } else if (options1 == 1) {//已到期
                    peroidTime = "0";
                } else if (options1 == 2) {//30天
                    peroidTime = "0";
                }
            }
        })
                .setTitleText("请选择")
                .setContentTextSize(20)//设置滚轮文字大小
                .setDividerColor(Color.LTGRAY)//设置分割线的颜色
                .setSelectOptions(0, 1)//默认选中项
                .isRestoreItem(true)//切换时是否还原，设置默认选中第一项。
                .isCenterLabel(false) //是否只显示中间选中项的label文字，false则每项item全部都带有label。
                .setOutSideColor(0x00000000) //设置外部遮罩颜色
                .build();
        periodPicker.setPicker(periodList);
    }

    //初始化分类选择器
    private void initCategoryPicker() {//分类选择
        catoryPicker = new OptionsPickerBuilder(this, new OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int options2, int options3, View v) {
                String tx = categoryBeanList.get(options1).toString();
                selectTv.setText(tx);
                categroyId = categoryBeanList.get(options1).getCategoryID();
            }
        })
                .setTitleText("请选择")
                .setContentTextSize(20)//设置滚轮文字大小
                .setDividerColor(Color.LTGRAY)//设置分割线的颜色
                .setSelectOptions(0, 1)//默认选中项
                .isRestoreItem(true)//切换时是否还原，设置默认选中第一项。
                .isCenterLabel(false) //是否只显示中间选中项的label文字，false则每项item全部都带有label。
                .setOutSideColor(0x00000000) //设置外部遮罩颜色
                .build();
        catoryPicker.setPicker(categoryBeanList);
    }

    //部门选择
    private void initDepartmentPicker() {
        departMentPicker = new OptionsPickerBuilder(this, new OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int options2, int options3, View v) {
                String tx = groupBeanList.get(options1).toString() + " — " + staffBeanList.get(options1).get(options2).toString();
                selectTv.setText(tx);
                departMent = groupBeanList.get(options1).getDepart();
                departMentId = groupBeanList.get(options1).getDepartID();
                staff = staffBeanList.get(options1).get(options2).getStaff();
                staffId = staffBeanList.get(options1).get(options2).getStaffID();
            }
        })
                .setTitleText("请选择")
                .setContentTextSize(20)//设置滚轮文字大小
                .setDividerColor(Color.LTGRAY)//设置分割线的颜色
                .setSelectOptions(0, 1)//默认选中项
                .isRestoreItem(true)//切换时是否还原，设置默认选中第一项。
                .isCenterLabel(false) //是否只显示中间选中项的label文字，false则每项item全部都带有label。
                .setOutSideColor(0x00000000) //设置外部遮罩颜色
                .build();
        departMentPicker.setPicker(groupBeanList, staffBeanList);
    }
}

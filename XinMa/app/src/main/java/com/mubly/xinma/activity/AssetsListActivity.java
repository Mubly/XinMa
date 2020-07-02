package com.mubly.xinma.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.FragmentManager;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

import android.app.Dialog;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bigkoo.pickerview.builder.OptionsPickerBuilder;
import com.bigkoo.pickerview.builder.TimePickerBuilder;
import com.bigkoo.pickerview.listener.OnOptionsSelectListener;
import com.bigkoo.pickerview.listener.OnTimeSelectListener;
import com.bigkoo.pickerview.view.OptionsPickerView;
import com.bigkoo.pickerview.view.TimePickerView;
import com.mubly.xinma.R;
import com.mubly.xinma.adapter.AssetsListPageAdapter;
import com.mubly.xinma.base.BaseActivity;
import com.mubly.xinma.databinding.ActivityAssetsListBinding;
import com.mubly.xinma.databinding.FilterLayoutBinding;
import com.mubly.xinma.db.XinMaDatabase;
import com.mubly.xinma.iview.IAssetListView;
import com.mubly.xinma.model.AssetBean;
import com.mubly.xinma.model.CategoryBean;
import com.mubly.xinma.model.GroupBean;
import com.mubly.xinma.model.StaffBean;
import com.mubly.xinma.presenter.AssetsListPresenter;
import com.mubly.xinma.utils.CommUtil;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 资产列表页
 */
public class AssetsListActivity extends BaseActivity<AssetsListPresenter, IAssetListView> implements IAssetListView {
    ActivityAssetsListBinding binding = null;

    //筛选相关
    TimePickerView pvTime;
    TextView selectTv;
    ConstraintLayout filterLayout;
    LinearLayout filterContentLayout;
    private OptionsPickerView catoryPicker, periodPicker, departMentPicker;
    private List<GroupBean> groupBeanList = new ArrayList<>();
    private List<List<StaffBean>> staffBeanList = new ArrayList<>();

    private boolean filterInit;

    private List<CategoryBean> categoryBeanList = new ArrayList<>();
    private List<String> periodList = new ArrayList<>();
    private Map<String, String> filterMap = new HashMap<>();




    @Override
    public void initView() {
        setTitle(R.string.assets_name);
        mPresenter.init();
        initfilterData();
    }

    @Override
    protected AssetsListPresenter createPresenter() {
        return new AssetsListPresenter();
    }

    @Override
    protected void getLayoutId() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_assets_list);
        filterLayout = findViewById(R.id.filter_layout);
        filterContentLayout = findViewById(R.id.filter_content_layout);
        filterLayout.setOnClickListener(this);
        filterContentLayout.setOnClickListener(this);
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
        }
    }

    @Override
    public FragmentManager getFgManager() {
        return getSupportFragmentManager();
    }

    @Override
    public void showPageView(AssetsListPageAdapter pageAdapter) {
        binding.mViewPager.setOffscreenPageLimit(5);
        binding.mViewPager.setAdapter(pageAdapter);
        binding.mTabLayout.setupWithViewPager(binding.mViewPager);
    }

    public List<AssetBean> getAllAssetBeanList() {
        return mPresenter.getAllAssetBeanList();
    }

    public void refreshTab(int index, String count) {
        binding.mTabLayout.getTabAt(index).setText(count);
    }

    public void clickEvent(View view) {
        filterLayout.setVisibility(View.VISIBLE);
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
                            staffArr.add(XinMaDatabase.getInstance().staffBeanDao().getAllByDepartId(groupBean.getDepartID()));
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
            }
        });
        filterBinding.filterAck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                filterLayout.setVisibility(View.GONE);
            }
        });
    }

    //时间选择器
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

    //时间段选择器
    private void initPeriodPicker() {//分类选择
        periodPicker = new OptionsPickerBuilder(this, new OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int options2, int options3, View v) {
                String tx = periodList.get(options1);
                selectTv.setText(tx);

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
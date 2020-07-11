package com.mubly.xinma.activity;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.work.Data;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.mubly.xinma.R;
import com.mubly.xinma.adapter.AssetsListCallBackAdapter;
import com.mubly.xinma.base.BaseActivity;
import com.mubly.xinma.base.CrossApp;
import com.mubly.xinma.databinding.ActivityPrintOperateBinding;
import com.mubly.xinma.db.XinMaDatabase;
import com.mubly.xinma.iview.IPrintOperateView;
import com.mubly.xinma.model.AssetBean;
import com.mubly.xinma.model.PrintContentBean;
import com.mubly.xinma.model.PrinterData;
import com.mubly.xinma.model.SelectAssetsBean;
import com.mubly.xinma.model.TemplateBean;
import com.mubly.xinma.model.UserInfoBean;
import com.mubly.xinma.presenter.ImageUrlPersenter;
import com.mubly.xinma.presenter.PrintOperatePresenter;
import com.mubly.xinma.utils.AppConfig;
import com.mubly.xinma.utils.CommUtil;
import com.mubly.xinma.utils.ImageUtils;
import com.mubly.xinma.utils.LiveDataBus;
import com.mubly.xinma.utils.PrintCenterManager;

import java.util.ArrayList;
import java.util.List;

import static com.mubly.xinma.activity.AssetSelectActivity.GET_USE_REQUEST_CODE;
import static com.mubly.xinma.activity.AssetSelectActivity.PRINT_REQUEST_CODE;
import static com.mubly.xinma.activity.AssetSelectActivity.RESULT_OK_CODE;

public class PrintOperateActivity extends BaseActivity<PrintOperatePresenter, IPrintOperateView> implements IPrintOperateView {
    private AssetBean assetsBean = null;
    ActivityPrintOperateBinding binding = null;
    private TemplateBean printMode = null;
    SelectAssetsBean selectAssetsBean = null;
    private String companyName;

    @Override
    public void initView() {
        setTitle("模板");
        showPrintModel(printMode);
        setRightTv("打印");
        binding.setPresenter(mPresenter);
        binding.setLifecycleOwner(this);
        mPresenter.init();
        if (null != assetsBean) {
            initSelectAssetsBean();
            selectAssetsBean.getSelectBean().add(assetsBean);
            mPresenter.notifyDataChange(selectAssetsBean.getSelectBean());
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        mPresenter.getPrnitStatus().setValue(PrintCenterManager.getInstance().isPrinterConnected() ? PrintCenterManager.getInstance().getCurrentPrint().shownName : "未连接");
    }

    @Override
    public void onRightClickEvent(TextView rightTv) {
        super.onRightClickEvent(rightTv);
        if (mPresenter.getPrnitStatus().getValue().equals("未连接")) {
            CommUtil.ToastU.showToast("请连接打印机");
            return;
        }
        if (null == selectAssetsBean || null == selectAssetsBean.getSelectBean() || selectAssetsBean.getSelectBean().size() < 1) {
            CommUtil.ToastU.showToast("请选择要打印的资产");
            return;
        }
        if (null == printMode) {
            CommUtil.ToastU.showToast("请选择要打印模板");
            return;
        }
        initPrintbean();
    }

    private void initPrintbean() {
        PrintContentBean contentBean = JSON.parseObject(printMode.getPrintArray(), PrintContentBean.class);
        for (AssetBean assetBean : selectAssetsBean.getSelectBean()) {
            if (null != contentBean && null != contentBean.getText()) {
                for (PrintContentBean.TextBean textBean : contentBean.getText()) {
                    if (textBean.getContent().contains("CompanyName"))
                        textBean.setContent(companyName);
                    else if (textBean.getContent().contains("AssetName"))
                        textBean.setContent(assetBean.getAssetName());
                    else if (textBean.getContent().contains("AssetModel"))
                        textBean.setContent(assetBean.getAssetModel());

                    else if (textBean.getContent().contains("Depart"))
                        textBean.setContent(assetBean.getAssetModel());
                    else if (textBean.getContent().contains("Staff"))
                        textBean.setContent(assetBean.getAssetModel());
                    else if (textBean.getContent().contains("Category"))
                        textBean.setContent(assetBean.getAssetModel());

                    else if (textBean.getContent().contains("AssetNo"))
                        textBean.setContent(assetBean.getAssetModel());

                    else if (textBean.getContent().contains("PurchaseDate"))
                        textBean.setContent(assetBean.getPurchaseDate());
                    else if (textBean.getContent().contains("Seat"))
                        textBean.setContent(assetBean.getAssetModel());
                    else if (textBean.getContent().contains("Depreciated"))
                        textBean.setContent(assetBean.getAssetModel());
                    else if (textBean.getContent().contains("Remainder"))
                        textBean.setContent(assetBean.getAssetModel());
                    else if (textBean.getContent().contains("Unit"))
                        textBean.setContent(assetBean.getAssetModel());
                }
                printMode.setPrintArray(JSON.toJSONString(contentBean));
            }
            PrintCenterManager.getInstance().prnit(printMode);
        }
    }

    @Override
    protected PrintOperatePresenter createPresenter() {
        return new PrintOperatePresenter();
    }

    private void initSelectAssetsBean() {
        if (null == selectAssetsBean) {
            selectAssetsBean = new SelectAssetsBean();
            List<AssetBean> assetBeans = new ArrayList<>();
            selectAssetsBean.setSelectBean(assetBeans);
        }
    }

    private void showPrintModel(TemplateBean templateBean) {
        if (null == templateBean) return;
        binding.printOperateModeName.setText(templateBean.getTemplateName());
        RequestOptions options = new RequestOptions().placeholder(R.mipmap.img_defaut);
        options.disallowHardwareConfig();
        Glide.with(PrintOperateActivity.this).asBitmap().load(ImageUrlPersenter.getPrintModeurl(templateBean.getViews())).apply(options).into(binding.printOperateModeImg);
    }

    @Override
    protected void getLayoutId() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_print_operate);
        assetsBean = (AssetBean) getIntent().getSerializableExtra("assetsBean");
        if (null != AppConfig.printModel.get()) {
            printMode = JSON.parseObject(AppConfig.printModel.get(), TemplateBean.class);
        }
        UserInfoBean userInfoBean = JSON.parseObject(AppConfig.userInfo.get(), UserInfoBean.class);
        if (null != userInfoBean)
            companyName = userInfoBean.getCompany();
    }

    public void toJumpAct(View view) {
        Intent intent = new Intent(this, PrintModelActivity.class);
        startActivityForResult(intent, 1104);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == 10019) {
            printMode = (TemplateBean) data.getSerializableExtra("prnitMode");
            showPrintModel(printMode);
            AppConfig.printModel.put(JSON.toJSONString(printMode));
        }
        if (resultCode == RESULT_OK_CODE) {
            selectAssetsBean = (SelectAssetsBean) data.getSerializableExtra("selectData");
            mPresenter.notifyDataChange(selectAssetsBean.getSelectBean());
        }
    }

    @Override
    public void showRv(AssetsListCallBackAdapter adapter) {
        binding.printOperateRv.setLayoutManager(new LinearLayoutManager(this));
        binding.printOperateRv.setAdapter(adapter);
    }

    public void forScanResult(String code) {
        super.forScanResult(code);
        Observable.create(new ObservableOnSubscribe<AssetBean>() {
            @Override
            public void subscribe(ObservableEmitter<AssetBean> emitter) throws Exception {
                emitter.onNext(XinMaDatabase.getInstance().assetBeanDao().getAssetBeanByNo(code));
            }
        }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<AssetBean>() {
                    @Override
                    public void accept(AssetBean assetBean) throws Exception {
                        initSelectAssetsBean();
                        selectAssetsBean.getSelectBean().add(assetBean);
                        mPresenter.notifyDataChange(selectAssetsBean.getSelectBean());
                    }
                });
    }

    @Override
    public void toSelectAssetsAct() {
        Intent intent = new Intent(this, AssetSelectActivity.class);
        intent.putExtra("from", PRINT_REQUEST_CODE);
        if (null != selectAssetsBean) {
            intent.putExtra("selectedData", selectAssetsBean);
        }
        startActivityForResult(intent, PRINT_REQUEST_CODE);
    }
}

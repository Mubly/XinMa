package com.mubly.xinma.presenter;

import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.mubly.xinma.R;
import com.mubly.xinma.activity.AssetsListActivity;
import com.mubly.xinma.adapter.SmartAdapter;
import com.mubly.xinma.base.BasePresenter;
import com.mubly.xinma.common.weight.RotateTextView;
import com.mubly.xinma.db.XinMaDatabase;
import com.mubly.xinma.iview.IAssetSelectView;
import com.mubly.xinma.model.AssetBean;
import com.mubly.xinma.model.FilterBean;
import com.mubly.xinma.model.SelectAssetsBean;
import com.mubly.xinma.utils.CommUtil;
import com.mubly.xinma.utils.CornerTransform;
import com.mubly.xinma.utils.ImageUtils;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class AssetSelectPresenter extends BasePresenter<IAssetSelectView> {
    private SelectAssetsBean localSelectBean;
    SmartAdapter<AssetBean> adapter = null;
    List<AssetBean> showDataList = new ArrayList<>();
    ImageUrlPersenter imageUrlPersenter = null;
    //    private List<AssetBean> allDataList = new ArrayList<>();
    private String[] status = null;

    public AssetSelectPresenter() {
        imageUrlPersenter = new ImageUrlPersenter();
    }

    public void init(final SelectAssetsBean selecBean, String[] status) {
        this.localSelectBean = selecBean;
        this.status = status;
        adapter = new SmartAdapter<AssetBean>(showDataList) {
            @Override
            public int getLayout(int viewType) {
                return R.layout.item_assets_select_layout;
            }

            @Override
            public void dealView(VH holder, final AssetBean data, int position) {
                final ImageView selectModel = (ImageView) holder.getChildView(R.id.asset_select_icon);
                ImageView iconImg = (ImageView) holder.getChildView(R.id.right_img_icon);
                if (null != localSelectBean && localSelectBean.getSelectBean().size() > 0) {
                    for (AssetBean assetBean : localSelectBean.getSelectBean()) {
                        if (assetBean.getAssetID().equals(data.getAssetID()))
                            selectModel.setSelected(true);
                    }

                } else {
                    selectModel.setSelected(false);
                }

                holder.setNetImage(selectModel.getContext(), R.id.assets_img, imageUrlPersenter.getAssetListUrl(data.getHeadimg()), R.mipmap.img_defaut);
                if (imageUrlPersenter.getAssetIcon(data.getStatus()) != -1) {
                    CornerTransform cornerTransform = new CornerTransform(iconImg.getContext(), CommUtil.dipTopx(iconImg.getContext(), 10));
                    cornerTransform.setExceptCorner(true, false, true, true);
                    Glide.with(iconImg.getContext()).
                            load(imageUrlPersenter.getAssetIcon(data.getStatus()))
                            .apply(RequestOptions.bitmapTransform(cornerTransform))
                            .into(iconImg);
//                    holder.setLocalImg(R.id.right_img_icon, imageUrlPersenter.getAssetIcon(data.getStatus()));
                }
                RotateTextView flagTv = (RotateTextView) holder.getChildView(R.id.flag_tv);
                flagTv.setText(data.getStatusName());
                holder.setText(R.id.assets_name, data.getAssetName());
                holder.setText(R.id.assets_size_value, data.getAssetModel());

                holder.setText(R.id.department_staff_value, data.getStaff());

                holder.setText(R.id.store_address_label, data.getSeatLabel());
                holder.setText(R.id.store_address_value, data.getSeat());


                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (selectModel.isSelected()) {
                            selectModel.setSelected(false);
                            int selectIndex = 0;
                            for (int i = 0; i < localSelectBean.getSelectBean().size(); i++) {
                                if (localSelectBean.getSelectBean().get(i).getAssetID().equals(data.getAssetID()))
                                    selectIndex = i;
                            }
                            localSelectBean.getSelectBean().remove(selectIndex);
                        } else {
                            if (null == localSelectBean) {
                                localSelectBean = new SelectAssetsBean();
                                List<AssetBean> seletData = new ArrayList<>();
                                localSelectBean.setSelectBean(seletData);
                            }
                            selectModel.setSelected(true);
                            localSelectBean.getSelectBean().add(data);
                        }
                    }
                });
            }
        };
        getMvpView().showRv(adapter);
        searchData(status);
    }

    //根据状态查询 默认
    public void searchData(final String[] status) {
        Observable.create(new ObservableOnSubscribe<List<AssetBean>>() {
            @Override
            public void subscribe(ObservableEmitter<List<AssetBean>> emitter) throws Exception {
                emitter.onNext(XinMaDatabase.getInstance().assetBeanDao().getAllByInStatus(status));
            }
        })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<List<AssetBean>>() {
                    @Override
                    public void accept(List<AssetBean> assetBeanList) throws Exception {
                        if (null != assetBeanList) {
//                            allDataList.addAll(assetBeanList);//保存一份此状态下的所有数据
                            showDataList.addAll(assetBeanList);
                        }
                        adapter.notifyDataSetChanged();
                    }
                });
    }

    //多条件查询
    public void filterData(FilterBean filterBean) {
        showDataList.clear();
        Observable.create(new ObservableOnSubscribe<List<AssetBean>>() {
            @Override
            public void subscribe(ObservableEmitter<List<AssetBean>> emitter) throws Exception {
                emitter.onNext(XinMaDatabase.getInstance().assetBeanDao().getSelectFilterAssets(status, filterBean.getCategoryID(), filterBean.getDepartID(), filterBean.getDepart(),
                        filterBean.getStaffID(), filterBean.getStaff(), filterBean.getPurchaseDate(), filterBean.getExpireDate(), filterBean.getRemainder()));
            }
        }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<List<AssetBean>>() {
                    @Override
                    public void accept(List<AssetBean> assetBeanList) throws Exception {
                        showDataList.addAll(assetBeanList);
                        adapter.notifyDataSetChanged();
                        getMvpView().isEmpty(assetBeanList.size() == 0);
                    }
                });
    }

    public SelectAssetsBean getLocalSelectBean() {
        if (null == localSelectBean || localSelectBean.getSelectBean().size() < 1) {
            return null;
        } else {
            return localSelectBean;
        }
    }

    //搜索框搜索
    public void searchDataEt(String searchKey) {
        showDataList.clear();
        Observable.create(new ObservableOnSubscribe<List<AssetBean>>() {
            @Override
            public void subscribe(ObservableEmitter<List<AssetBean>> emitter) throws Exception {
                emitter.onNext(XinMaDatabase.getInstance().assetBeanDao().getStaAssetBySeachKey(status, searchKey));
            }
        }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<List<AssetBean>>() {
                    @Override
                    public void accept(List<AssetBean> assetBeanList) throws Exception {
                        showDataList.addAll(assetBeanList);
                        adapter.notifyDataSetChanged();
                        getMvpView().isEmpty(assetBeanList.size() == 0);
                    }
                });

    }

}

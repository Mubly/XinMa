package com.mubly.xinma.fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mubly.xinma.R;
import com.mubly.xinma.activity.AssetSelectActivity;
import com.mubly.xinma.activity.AssetsDetialActivity;
import com.mubly.xinma.activity.AssetsListActivity;
import com.mubly.xinma.activity.OperateLogListActivity;
import com.mubly.xinma.adapter.AssetsListAdapter;
import com.mubly.xinma.adapter.SmartAdapter;
import com.mubly.xinma.databinding.FragmentAssetsListBinding;
import com.mubly.xinma.db.DataBaseUtils;
import com.mubly.xinma.db.XinMaDatabase;
import com.mubly.xinma.model.AssetBean;
import com.mubly.xinma.model.FilterBean;
import com.mubly.xinma.utils.LiveDataBus;
import com.scwang.smart.refresh.layout.api.RefreshLayout;
import com.scwang.smart.refresh.layout.listener.OnLoadMoreListener;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class AssetsListFragment extends Fragment {
    private String type;
    private int index;
    FragmentAssetsListBinding binding = null;
    List<AssetBean> dataList = new ArrayList<>();
    AssetsListAdapter adapter = null;
    private int pageIndex = 0, pageSize = 40;

    public static AssetsListFragment getInstance(String type, int index) {
        AssetsListFragment fragment = new AssetsListFragment();
        Bundle bundle = new Bundle();
        bundle.putString("type", type);
        bundle.putInt("index", index);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_assets_list, container, false);
        binding = DataBindingUtil.bind(view);
        type = getArguments().getString("type");
        index = getArguments().getInt("index");
        dataList.clear();
        init();
        return view;
    }

    private void init() {
        adapter = new AssetsListAdapter(dataList);
        adapter.setOnItemClickListener(new AssetsListAdapter.OnItemClickListener() {
            @Override
            public void itemClick(AssetBean data, boolean isLog, int index) {
                if (isLog)
                    toAssestLog(data);
                else
                    toAssestDetial(data);
            }
        });
        binding.assetsRv.setLayoutManager(new LinearLayoutManager(getContext()));
        binding.assetsRv.setAdapter(adapter);
        initData();
        initEvent();
    }

    private void toAssestLog(AssetBean data) {
        Intent intent=new Intent(getContext(), OperateLogListActivity.class);
        intent.putExtra("assetBean",data);
        startActivity(intent);
        ((AssetsListActivity)getActivity()).startPage();
    }

    private void initEvent() {
        binding.assetLoadMore.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                pageIndex = pageIndex + pageSize;
                initData();
            }
        });

        LiveDataBus.get().with("searchAsset", Integer.class).observe(getViewLifecycleOwner(), new Observer<Integer>() {
            @Override
            public void onChanged(Integer integer) {
                if (index == integer)
                    searchData(integer);
            }
        });
        LiveDataBus.get().with("filterAsset", FilterBean.class).observe(getViewLifecycleOwner(), new Observer<FilterBean>() {
            @Override
            public void onChanged(FilterBean filterBean) {
                if (index == filterBean.getIndex())
                    filterData(filterBean);
            }
        });
    }

    private void searchData(Integer integer) {
        String status = getStatus(integer);
        String searchKey = ((AssetsListActivity) getActivity()).getSearchKey();
        dataList.clear();
        Observable.create(new ObservableOnSubscribe<List<AssetBean>>() {
            @Override
            public void subscribe(ObservableEmitter<List<AssetBean>> emitter) throws Exception {
                if (null == status) {
                    emitter.onNext(XinMaDatabase.getInstance().assetBeanDao().getAssetAllBySeachKey(searchKey));
                } else {
                    emitter.onNext(XinMaDatabase.getInstance().assetBeanDao().getStatusAssetBySeachKey(status, searchKey));
                }

            }
        }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<List<AssetBean>>() {
                    @Override
                    public void accept(List<AssetBean> assetBeanList) throws Exception {
                        binding.assetLoadMore.setEnableLoadMore(false);
                        dataList.addAll(assetBeanList);
                        adapter.notifyDataSetChanged();
                        if (binding.assetLoadMore.getState().isFooter) {
                            binding.assetLoadMore.finishLoadMore();
                        }
                        if (assetBeanList.size() == 0) {
                            binding.assetLoadMore.setNoMoreData(true);
                        }
                        isEmpty(assetBeanList.size() == 0);
                        ((AssetsListActivity) getActivity()).refreshTab(index, getTabStr(index, assetBeanList.size()));
                    }
                });

    }

    private void filterData(FilterBean filterBean) {
        dataList.clear();
        Observable.create(new ObservableOnSubscribe<List<AssetBean>>() {
            @Override
            public void subscribe(ObservableEmitter<List<AssetBean>> emitter) throws Exception {
                emitter.onNext(XinMaDatabase.getInstance().assetBeanDao().getFilterAssets(getStatus(filterBean.getIndex()), filterBean.getCategoryID(), filterBean.getDepartID(), filterBean.getDepart(),
                        filterBean.getStaffID(), filterBean.getStaff(), filterBean.getPurchaseDate(), filterBean.getExpireDate(), filterBean.getRemainder()));
            }
        }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<List<AssetBean>>() {
                    @Override
                    public void accept(List<AssetBean> assetBeanList) throws Exception {
                        binding.assetLoadMore.setEnableLoadMore(false);
                        dataList.addAll(assetBeanList);
                        adapter.notifyDataSetChanged();
                        if (binding.assetLoadMore.getState().isFooter) {
                            binding.assetLoadMore.finishLoadMore();
                        }
                        if (assetBeanList.size() == 0) {
                            binding.assetLoadMore.setNoMoreData(true);
                        }
                        isEmpty(assetBeanList.size() == 0);
                        ((AssetsListActivity) getActivity()).refreshTab(index, getTabStr(index, assetBeanList.size()));
                    }
                });
    }


    private void toAssestDetial(AssetBean data) {
        Intent intent = new Intent(getContext(), AssetsDetialActivity.class);
        intent.putExtra("assetBean", data);
        intent.putExtra("from", "assetsList");
        startActivity(intent);
    }

    private void initData() {
        Observable.create(new ObservableOnSubscribe<List<AssetBean>>() {
            @Override
            public void subscribe(ObservableEmitter<List<AssetBean>> emitter) throws Exception {
                if (type.equals("0")) {
                    emitter.onNext(XinMaDatabase.getInstance().assetBeanDao().getAllByPage(pageIndex, pageSize));
                } else {
                    emitter.onNext(XinMaDatabase.getInstance().assetBeanDao().getAllByStatusPage(pageIndex, pageSize, type));
                }

            }
        }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<List<AssetBean>>() {
                    @Override
                    public void accept(List<AssetBean> assetBeanList) throws Exception {
                        dataList.addAll(assetBeanList);
                        adapter.notifyDataSetChanged();
                        if (binding.assetLoadMore.getState().isFooter) {
                            binding.assetLoadMore.finishLoadMore();
                        }
                        if (assetBeanList.size() == 0) {
                            binding.assetLoadMore.setNoMoreData(true);
                        }
                        isEmpty(dataList.size() == 0);
                    }
                });
    }


    private String getStatus(int currentIndex) {
        String status = null;
        switch (currentIndex) {
            case 0:
                status = null;
                break;
            case 1:
                status = "1";
                break;
            case 2:
                status = "3";
                break;
            case 3:
                status = "5";
                break;
            case 4:
                status = "6";
                break;
            case 5:
                status = "8";
                break;
        }
        return status;
    }


    private String getTabStr(int index, int searchCount) {
        if (index == 0)
            return "全部(" + searchCount + ")";
        else if (index == 1)
            return "闲置(" + searchCount + ")";
        else if (index == 2)
            return "在用(" + searchCount + ")";
        else if (index == 3)
            return "借用(" + searchCount + ")";
        else if (index == 4)
            return "维修(" + searchCount + ")";
        else if (index == 5)
            return "处置(" + searchCount + ")";
        else
            return "全部(" + searchCount + ")";
    }

    private void isEmpty(boolean isVisiable) {
        binding.assetListEmpty.setVisibility(isVisiable ? View.VISIBLE : View.GONE);
    }
}

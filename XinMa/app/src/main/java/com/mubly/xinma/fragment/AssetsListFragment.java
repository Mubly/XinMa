package com.mubly.xinma.fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mubly.xinma.R;
import com.mubly.xinma.activity.AssetSelectActivity;
import com.mubly.xinma.activity.AssetsDetialActivity;
import com.mubly.xinma.activity.AssetsListActivity;
import com.mubly.xinma.adapter.AssetsListAdapter;
import com.mubly.xinma.adapter.SmartAdapter;
import com.mubly.xinma.databinding.FragmentAssetsListBinding;
import com.mubly.xinma.db.DataBaseUtils;
import com.mubly.xinma.db.XinMaDatabase;
import com.mubly.xinma.model.AssetBean;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class AssetsListFragment extends Fragment {
    private int type;
    FragmentAssetsListBinding binding = null;
    List<AssetBean> assetBeanList = new ArrayList<>();
    AssetsListAdapter adapter = null;

    public static AssetsListFragment getInstance(int type) {
        AssetsListFragment fragment = new AssetsListFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("type", type);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_assets_list, container, false);
        binding = DataBindingUtil.bind(view);
        type = getArguments().getInt("type");
        init();
        return view;
    }

    private void init() {
        adapter = new AssetsListAdapter(assetBeanList);
        adapter.setOnItemClickListener(new AssetsListAdapter.OnItemClickListener() {
            @Override
            public void itemClick(AssetBean data, int index) {
                toAssestDetial(data);
            }
        });
        binding.assetsRv.setLayoutManager(new LinearLayoutManager(getContext()));
        binding.assetsRv.setAdapter(adapter);
        initData();
    }

    private void toAssestDetial(AssetBean data) {
        Intent intent = new Intent(getContext(), AssetsDetialActivity.class);
        intent.putExtra("assetBean",data);
        intent.putExtra("from","assetsList");
        startActivity(intent);
    }

    private void initData() {
        assetBeanList.clear();
        new Thread(new Runnable() {
            @Override
            public void run() {
                if (null == getStatus()) {
//                    assetBeanList.addAll(DataBaseUtils.getInstance().getAssetBeanList());
                    assetBeanList.addAll(((AssetsListActivity) getActivity()).getAllAssetBeanList());
                } else {
                    for (AssetBean assetBean : ((AssetsListActivity) getActivity()).getAllAssetBeanList()) {
                        if (assetBean.getStatus().equals(getStatus())) {
                            assetBeanList.add(assetBean);
                        }
                    }
//                    assetBeanList.addAll(XinMaDatabase.getInstance().assetBeanDao().getAllByStatus(getStatus()));
                }
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        adapter.notifyDataSetChanged();
                    }
                });
            }
        }).start();
    }

    private String getStatus() {
        String status = null;
        switch (type) {
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

}

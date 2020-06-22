package com.mubly.xinma.fragment;

import android.os.Bundle;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mubly.xinma.R;
import com.mubly.xinma.adapter.AssetsListAdapter;
import com.mubly.xinma.adapter.SmartAdapter;
import com.mubly.xinma.databinding.FragmentAssetsListBinding;
import com.mubly.xinma.db.DataBaseUtils;
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
        init();
        return view;
    }

    private void init() {
        adapter = new AssetsListAdapter(assetBeanList);
        binding.assetsRv.setLayoutManager(new LinearLayoutManager(getContext()));
        binding.assetsRv.setAdapter(adapter);
        initData();
    }

    private void initData() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                assetBeanList.addAll(DataBaseUtils.getInstance().getAssetBeanList());
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        adapter.notifyDataSetChanged();
                    }
                });
            }
        }).start();
    }
}

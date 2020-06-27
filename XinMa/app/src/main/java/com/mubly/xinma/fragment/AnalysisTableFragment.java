package com.mubly.xinma.fragment;

import android.os.Bundle;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.mikephil.charting.data.PieEntry;
import com.mubly.xinma.R;
import com.mubly.xinma.databinding.FragmentAnalysisTableBinding;
import com.mubly.xinma.iview.IAnalysisTableView;
import com.mubly.xinma.iview.IAnaysisView;
import com.mubly.xinma.presenter.AnalysisTablePresenter;
import com.mubly.xinma.utils.PieChartManager;

import java.util.List;

/**
 * 分析报表
 */
public class AnalysisTableFragment extends Fragment implements IAnalysisTableView {
    private FragmentAnalysisTableBinding binding = null;
    private AnalysisTablePresenter presenter = null;
    private int type;

    public static AnalysisTableFragment getInstance(int type) {
        AnalysisTableFragment fragment = new AnalysisTableFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("type", type);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_analysis_table, container, false);
        binding = DataBindingUtil.bind(view);
        type = getArguments().getInt("type");
        presenter = new AnalysisTablePresenter(type, this);
        binding.setVm(presenter);
        binding.setLifecycleOwner(this);
        return view;
    }

    @Override
    public void showPie(List<PieEntry> data,String totalValue) {
        PieChartManager pieChartManager = new PieChartManager(binding.pieChart);
        pieChartManager.showPieChart(data,totalValue);
    }
}

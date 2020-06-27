package com.mubly.xinma.iview;

import com.github.mikephil.charting.data.PieEntry;

import java.util.List;

public interface IAnalysisTableView {
    void showPie(List<PieEntry> data,String totalValue);
}

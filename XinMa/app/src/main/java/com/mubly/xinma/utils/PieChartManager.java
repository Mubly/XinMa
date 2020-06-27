package com.mubly.xinma.utils;

import android.graphics.Color;

import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.PercentFormatter;

import java.util.ArrayList;
import java.util.List;

public class PieChartManager {
    private int[] color = {Color.parseColor("#FF7372"), Color.parseColor("#FFB55D")
            , Color.parseColor("#6493FF"), Color.parseColor("#8EF0FF"),
            Color.parseColor("#D194F3"), Color.parseColor("#FFEF5D"),
            Color.parseColor("#354CA7"), Color.parseColor("#A4E45D")};
    private PieChart pieChart;

    public PieChartManager(PieChart pieChart) {
        this.pieChart = pieChart;
        pieChart.setNoDataText("暂无数据");

    }

    private void showPieChartData(List<Float> value,String centerStr) {
        List<PieEntry> data = new ArrayList<>();
        for (float v : value) {
            data.add(new PieEntry(v));
        }
        showPieChart(data,centerStr);
    }


    public void showPieChart(List<PieEntry> pieList, String totalValue) {
        PieDataSet dataSet = new PieDataSet(pieList, "");

        // 设置颜色list，让不同的块显示不同颜色，下面是我觉得不错的颜色集合，比较亮
        ArrayList<Integer> colors = new ArrayList<Integer>();
        int[] MATERIAL_COLORS = {
                Color.rgb(200, 172, 255)
        };
        for (int c : color) {
            colors.add(c);
        }
//        for (int c : ColorTemplate.VORDIPLOM_COLORS) {
//            colors.add(c);
//        }
        dataSet.setColors(colors);
        PieData pieData = new PieData(dataSet);
        // 设置描述，我设置了不显示，因为不好看，你也可以试试让它显示，真的不好看
        Description description = new Description();
        description.setEnabled(false);
        pieChart.setExtraTopOffset(10f);
        pieChart.setExtraBottomOffset(10f);
        pieChart.setDescription(description);
        //设置半透明圆环的半径, 0为透明
        pieChart.setTransparentCircleRadius(0f);

        //设置初始旋转角度
        pieChart.setRotationAngle(-15);
        //内部圆环
        pieChart.setDrawHoleEnabled(true);              //是否显示PieChart内部圆环(true:下面属性才有意义)
        pieChart.setCenterText(totalValue);
        //数据连接线距图形片内部边界的距离，为百分数
        dataSet.setValueLinePart1OffsetPercentage(80f);

        //设置连接线的颜色
        dataSet.setValueLineColor(Color.LTGRAY);
        // 连接线在饼状图外面
        dataSet.setYValuePosition(PieDataSet.ValuePosition.OUTSIDE_SLICE);

        // 设置饼块之间的间隔
//        dataSet.setSliceSpace(1f);
        dataSet.setHighlightEnabled(true);
        // 不显示图例
        Legend legend = pieChart.getLegend();
        legend.setEnabled(true);
        legend.setOrientation(Legend.LegendOrientation.VERTICAL);
        legend.setHorizontalAlignment(Legend.LegendHorizontalAlignment.RIGHT);

        // 和四周相隔一段距离,显示数据
//        pieChart.setExtraOffsets(26, 5, 26, 5);

        // 设置pieChart图表是否可以手动旋转
        pieChart.setRotationEnabled(false);
        // 设置piecahrt图表点击Item高亮是否可用
        pieChart.setHighlightPerTapEnabled(true);
        // 设置pieChart图表展示动画效果，动画运行1.4秒结束
        pieChart.animateY(1400, Easing.EaseInOutQuad);
        //设置pieChart是否只显示饼图上百分比不显示文字
        pieChart.setDrawEntryLabels(false);
        //是否绘制PieChart内部中心文本
        pieChart.setDrawCenterText(true);
        //是否显示所占+%
        pieChart.setUsePercentValues(true);
        // 绘制内容value，设置字体颜色大小
        pieData.setDrawValues(true);
        pieData.setValueFormatter(new PercentFormatter(pieChart));
        pieData.setValueTextSize(8f);
        pieData.setValueTextColor(Color.DKGRAY);


        pieChart.setData(pieData);
        // 更新 piechart 视图
        pieChart.postInvalidate();
    }
}

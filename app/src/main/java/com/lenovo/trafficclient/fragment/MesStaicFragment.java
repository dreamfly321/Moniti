package com.lenovo.trafficclient.fragment;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.formatter.PercentFormatter;
import com.lenovo.trafficclient.db.DBHelper;
import com.lenovo.trafficclient.db.Notify;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/*********************************************************************************
 Created by Android Studio.
 *Author:          Jack Fu
 *Version:         1.0
 *Date;            17-6-5 下午5:44
 *Description:     
 **********************************************************************************/
public class MesStaicFragment extends BaseFragment {
    private PieChart mChart;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mChart = new PieChart(getContext());

        mChart.setUsePercentValues(true);
        mChart.setDescription("");
        mChart.setExtraOffsets(5, 10, 5, 5);

        mChart.setDragDecelerationFrictionCoef(0.95f);

        mChart.setDrawHoleEnabled(true);
        mChart.setHoleColorTransparent(true);

        mChart.setTransparentCircleColor(Color.WHITE);
        mChart.setTransparentCircleAlpha(110);

        mChart.setHoleRadius(58f);
        mChart.setTransparentCircleRadius(61f);

        mChart.setDrawCenterText(true);

        mChart.setRotationAngle(0);
        // enable rotation of the chart by touch
        mChart.setRotationEnabled(true);

        // mChart.setUnit(" €");
        // mChart.setDrawUnitsInChart(true);



        mChart.animateY(1400, Easing.EasingOption.EaseInOutQuad);
        // mChart.spin(2000, 0, 360);
        mChart.getLegend().setPosition(Legend.LegendPosition.BELOW_CHART_CENTER);

        setData();

        return mChart;
    }


    private void setData() {
        List<Notify> notifies = new ArrayList<Notify>();
        try {
            notifies = DBHelper.getInstance(getActivity()).getNotifyIntegerDao().queryForAll();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        int light = 0;
        int road = 0;
        int account = 0;
        for (Notify notify : notifies) {
            if(notify.getType().equals("light")){
                light++;
            }else if(notify.getType().equals("road")){
                road++;
            }else {
                account++;
            }
        }

        ArrayList<Entry> yVals1 = new ArrayList<Entry>();
        yVals1.add(new Entry(light, 0));
        yVals1.add(new Entry(road, 0));
        yVals1.add(new Entry(account, 0));

        ArrayList<String> xVals = new ArrayList<String>();
        xVals.add("光照阈值");
        xVals.add("路况");
        xVals.add("账户阈值");

        PieDataSet dataSet = new PieDataSet(yVals1, "");
        dataSet.setSliceSpace(2f);
        dataSet.setSelectionShift(5f);

        // add a lot of colors

        int colors[] = {
                Color.RED,Color.BLUE,Color.GREEN
        };

        dataSet.setColors(colors);
        //dataSet.setSelectionShift(0f);

        PieData data = new PieData(xVals, dataSet);
        data.setValueFormatter(new PercentFormatter());
        data.setValueTextSize(11f);
        data.setValueTextColor(Color.WHITE);
        mChart.setData(data);

        // undo all highlights
        mChart.highlightValues(null);

        mChart.invalidate();
    }
}

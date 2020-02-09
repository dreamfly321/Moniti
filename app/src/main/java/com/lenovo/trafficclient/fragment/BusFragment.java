package com.lenovo.trafficclient.fragment;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.volley.VolleyError;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.lenovo.trafficclient.bean.Event;
import com.lenovo.trafficclient.util.MyVolley;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/*********************************************************************************
 Created by Android Studio.
 *Author:          Jack Fu
 *Version:         1.0
 *Date;            17-6-5 下午4:02
 *Description:     
 **********************************************************************************/
public class BusFragment extends BaseFragment{
    private BarChart mChart;
    ArrayList<BarEntry> station1 = new ArrayList<BarEntry>();
    ArrayList<BarEntry> station2 = new ArrayList<BarEntry>();

    public BusFragment() {
        station1.add(new BarEntry(0,0));
        station1.add(new BarEntry(0,1));
        station2.add(new BarEntry(0,0));
        station2.add(new BarEntry(0,1));
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void e(Event event){
        switch (event.getType()){
            case MIAO3:
                MyVolley.GetBusStationInfo(1, new MyVolley.Listener() {
                    @Override
                    public void success(JSONObject j) {
                        try {
                            if(j.getString("RESULT").equals("S")){
                                JSONArray array = j.getJSONArray("DATA");
                                for (int i = 0; i < array.length(); i++) {
                                    JSONObject jsonObject = array.getJSONObject(i);
                                    station1.set(jsonObject.getInt("BusId")-1,new BarEntry(jsonObject.getInt("Distance"),jsonObject.getInt("BusId")-1));
                                }
                                setData();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void error(VolleyError e) {
                        e.printStackTrace();
                    }
                });

                MyVolley.GetBusStationInfo(2, new MyVolley.Listener() {
                    @Override
                    public void success(JSONObject j) {
                        try {
                            if(j.getString("RESULT").equals("S")){
                                JSONArray array = j.getJSONArray("DATA");
                                for (int i = 0; i < array.length(); i++) {
                                    JSONObject jsonObject = array.getJSONObject(i);
                                    station2.set(jsonObject.getInt("BusId")-1,new BarEntry(jsonObject.getInt("Distance"),jsonObject.getInt("BusId")-1));
                                }
                                setData();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void error(VolleyError e) {
                        e.printStackTrace();
                    }
                });
                break;
        }
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mChart = new BarChart(getContext());
        mChart.setDescription("");

//        mChart.setDrawBorders(true);

        // scaling can now only be done on x- and y-axis separately
        mChart.setPinchZoom(false);

        mChart.setDrawBarShadow(false);

        mChart.setDrawGridBackground(false);

        // create a custom MarkerView (extend MarkerView) and specify the layout

        mChart.getLegend().setEnabled(false);

        XAxis xl = mChart.getXAxis();
        xl.setPosition(XAxis.XAxisPosition.BOTTOM);

        YAxis leftAxis = mChart.getAxisLeft();
        leftAxis.setDrawGridLines(false);
        leftAxis.setSpaceTop(30f);

        mChart.getAxisRight().setEnabled(false);
        return mChart;
    }

    public void setData(){
        int colors1[] = new int[2];
        int colors2[] = new int[2];
        ArrayList<String> xVals = new ArrayList<String>();
        xVals.add("1号公交车");
        xVals.add("2号公交车");

        for (int i = 0; i < station1.size(); i++) {
           if(station1.get(i).getVal() > station2.get(i).getVal()){
               colors1[i] = Color.YELLOW;
               colors2[i] = Color.GREEN;
           }else{
               colors2[i] = Color.YELLOW;
               colors1[i] = Color.GREEN;
           }
        }

        // create 3 datasets with different types
        BarDataSet set1 = new BarDataSet(station1, "Company A");
        // set1.setColors(ColorTemplate.createColors(getApplicationContext(),
        // ColorTemplate.FRESH_COLORS));
        set1.setColors(colors1);
        BarDataSet set2 = new BarDataSet(station2, "Company B");
        set2.setColors(colors2);

        ArrayList<BarDataSet> dataSets = new ArrayList<BarDataSet>();
        dataSets.add(set1);
        dataSets.add(set2);

        BarData data = new BarData(xVals, dataSets);
//        data.setValueFormatter(new LargeValueFormatter());

        // add space between the dataset groups in percent of bar-width
        data.setGroupSpace(80f);

        mChart.setData(data);
        mChart.invalidate();
    }


}

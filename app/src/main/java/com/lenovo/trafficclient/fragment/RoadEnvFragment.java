package com.lenovo.trafficclient.fragment;

import android.app.Notification;
import android.app.NotificationManager;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android.volley.VolleyError;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.formatter.ValueFormatter;
import com.github.mikephil.charting.utils.ViewPortHandler;
import com.google.gson.Gson;
import com.lenovo.trafficclient.MyApp;
import com.lenovo.trafficclient.R;
import com.lenovo.trafficclient.bean.Event;
import com.lenovo.trafficclient.db.DBHelper;
import com.lenovo.trafficclient.db.Notify;
import com.lenovo.trafficclient.db.Sense;
import com.lenovo.trafficclient.util.MyVolley;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.json.JSONException;
import org.json.JSONObject;

import java.sql.SQLException;
import java.util.ArrayList;

/*********************************************************************************
 Created by Android Studio.
 *Author:          Jack Fu
 *Version:         1.0
 *Date;            17-6-5 下午5:11
 *Description:     
 **********************************************************************************/
public class RoadEnvFragment extends BaseFragment {

    private TextView tv_cur;
    private TextView tv_max;
    private TextView tv_min;
    private TextView tv_tip;
    private BarChart mChart;
    private NotificationManager nm;
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void e(Event event){
        switch (event.getType()){
            case MIAO3:
                MyVolley.GetAllSense("admin", new MyVolley.Listener() {
                    @Override
                    public void success(JSONObject j) {
                        try {
                            if (j.getString("RESULT").equals("S")) {
                                Sense sense = new Gson().fromJson(j.getJSONObject("DATA").toString(), Sense.class);
                                tv_cur.setText("光照强度当前值:"+sense.getLight());
                                if(sense.getLight() < MyApp.getMinLight()){
                                    String s= "光照较弱，出行请开灯";
                                    tv_tip.setText("光照较弱，出行请开灯");
                                    Notify notify = new Notify();
                                    notify.setCreateAt(System.currentTimeMillis());
                                    notify.setMessage(s);
                                    notify.setType("light");
                                    Notification n= notify.buildNotification("提示",getActivity());
                                    DBHelper.getInstance(getActivity()).getNotifyIntegerDao().create(notify);
                                    nm.notify(1001,n);
                                }else if(sense.getLight() > MyApp.getMaxLight()){
                                    String s= "光照较强，出行请戴墨镜";
                                    tv_tip.setText(s);
                                    tv_tip.setText("光照较弱，出行请开灯");
                                    Notify notify = new Notify();
                                    notify.setCreateAt(System.currentTimeMillis());
                                    notify.setMessage(s);
                                    notify.setType("light");
                                    Notification n= notify.buildNotification("提示",getActivity());
                                    DBHelper.getInstance(getActivity()).getNotifyIntegerDao().create(notify);
                                    nm.notify(1001,n);
                                }else{
                                    tv_tip.setText("友情提示:适合出行");
                                    nm.cancel(1001);
                                }
                                setData(MyApp.getMinLight(),MyApp.getMaxLight(),sense.getLight());
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void error(VolleyError e) {

                    }
                });
                break;
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_road_env, null);
        initView(view);
        nm = (NotificationManager) getActivity().getSystemService(Context.NOTIFICATION_SERVICE);
        return view;
    }


    private void initView(View view) {
        tv_cur = (TextView) view.findViewById(R.id.tv_cur);
        tv_max = (TextView) view.findViewById(R.id.tv_max);
        tv_min = (TextView) view.findViewById(R.id.tv_min);
        tv_tip = (TextView) view.findViewById(R.id.tv_tip);
        mChart = (BarChart) view.findViewById(R.id.chart);

        tv_max.setText("光照阀值上限值:"+MyApp.getMaxLight());
        tv_min.setText("光照阀值下限值:"+MyApp.getMinLight());

        tv_tip.setText("友情提示:");

        mChart.setDescription("");

        // if more than 60 entries are displayed in the mChart, no values will be
        // drawn

        // scaling can now only be done on x- and y-axis separately
        mChart.setPinchZoom(false);

        mChart.setDrawGridBackground(false);
        mChart.setDrawBarShadow(false);

        mChart.setDrawValueAboveBar(false);

        // change the position of the y-labels
         mChart.getAxisLeft().setEnabled(false);
         mChart.getAxisRight().setEnabled(false);

        mChart.getXAxis().setEnabled(false);

        // mChart.setDrawXLabels(false);
        // mChart.setDrawYLabels(false);

        // setting data

        mChart.getLegend().setEnabled(false);
        mChart.setRotation(90);
        setData(MyApp.getMinLight(),MyApp.getMaxLight(),0);
        // mChart.setDrawLegend(false);
    }

    private void setData(final float min ,final float max,final float value){
        int colors[] = {
                Color.RED,Color.RED,Color.GREEN,Color.RED,Color.WHITE,Color.BLUE,Color.WHITE
        };
        ArrayList<String> xVals = new ArrayList<String>();
        xVals.add("");
        xVals.add("");

        float start = 0;
        final float end = max + 500;
        final float diff1 = max - min;
        final float diff2 = end - max;
        final float diff3 = end - value;



        ArrayList<BarEntry> yVals1 = new ArrayList<BarEntry>();

        yVals1.add(new BarEntry(new float[]{start,min,diff1,diff2},0));
        yVals1.add(new BarEntry(new float[]{start,value,diff3},1));
        BarDataSet set1 = new BarDataSet(yVals1, "");
        set1.setColors(colors);

        ArrayList<BarDataSet> dataSets = new ArrayList<BarDataSet>();
        dataSets.add(set1);

        BarData data = new BarData(xVals, dataSets);
        data.setValueFormatter(new ValueFormatter() {
            @Override
            public String getFormattedValue(float v, Entry entry, int i, ViewPortHandler viewPortHandler) {
                if(v == diff1){
                    return max+"";
                }else if(v == diff2){
                    return end+"";
                }else if(v == diff3){
                    return end+"";
                }
                return v+"";
            }
        });

        mChart.setData(data);
        mChart.invalidate();
    }
}

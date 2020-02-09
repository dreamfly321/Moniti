package com.lenovo.trafficclient.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.volley.VolleyError;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.google.gson.Gson;
import com.lenovo.trafficclient.R;
import com.lenovo.trafficclient.bean.Event;
import com.lenovo.trafficclient.db.Sense;
import com.lenovo.trafficclient.util.MyVolley;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/*********************************************************************************
 Created by Android Studio.
 *Author:          Jack Fu
 *Version:         1.0
 *Date;            17-6-5 下午4:02
 *Description:     
 **********************************************************************************/
public class BusEnvFragment extends BaseFragment {

    private ViewPager pager;
    private LinearLayout indi;
    private List<Sense> senseList = new ArrayList<Sense>();
    private TextView tv_chart_title;

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void e(Event event) {
        switch (event.getType()) {
            case MIAO3:
                MyVolley.GetAllSense("admin", new MyVolley.Listener() {
                    @Override
                    public void success(JSONObject j) {
                        try {
                            if (j.getString("RESULT").equals("S")) {
                                Sense sense = new Gson().fromJson(j.getJSONObject("DATA").toString(), Sense.class);
                                sense.setCreateAt(System.currentTimeMillis());
                                senseList.add(sense);

                            }
                            update();
                        } catch (JSONException e) {
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

    private void update() {
        int i = pager.getCurrentItem();
        pager.setAdapter(new CA());
        pager.setCurrentItem(i);
    }

    private void updateI(int p) {
        indi.removeAllViews();
        for (int i = 0; i < 3; i++) {
            ImageView imageView = new ImageView(getContext());
            if (i == p) {
                imageView.setImageResource(R.drawable.page_indicator_focused);
            } else {
                imageView.setImageResource(R.drawable.page_indicator_unfocused);
            }
            LinearLayout.LayoutParams l = new LinearLayout.LayoutParams(20, 20);
            l.leftMargin = 10;
            indi.addView(imageView, l);
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        senseList.clear();
        View view = inflater.inflate(R.layout.fragment_bus_env, null);
        initView(view);
        update();
        updateI(0);
        tv_chart_title.setText(titles[0]);
        return view;
    }

    private void initView(View view) {
        pager = (ViewPager) view.findViewById(R.id.pager);
        indi = (LinearLayout) view.findViewById(R.id.indi);
        pager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                updateI(position);
                tv_chart_title.setText(titles[position]);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        pager.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                pager.getParent().requestDisallowInterceptTouchEvent(true);
                return false;
            }
        });
        tv_chart_title = (TextView) view.findViewById(R.id.tv_chart_title);
    }


    private String[] titles = {
            "二氧化碳浓度", "空气温度", "空气湿度"
    };

    private class CA extends PagerAdapter {

        @Override
        public int getCount() {
            return 3;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            LineChart mChart = new LineChart(getContext());
            mChart.setDrawGridBackground(false);

            // no description text
            mChart.setDescription("");

            // enable value highlighting
            mChart.setHighlightEnabled(true);

            // enable touch gestures
            mChart.setTouchEnabled(true);

            // enable scaling and dragging
            mChart.setDragEnabled(true);
            mChart.setScaleEnabled(true);

            // if disabled, scaling can be done on x- and y-axis separately
            mChart.setPinchZoom(true);

            // set an alternative background color
            // mChart.setBackgroundColor(Color.GRAY);

            // create a custom MarkerView (extend MarkerView) and specify the layout
            // to use for it

            // enable/disable highlight indicators (the lines that indicate the
            // highlighted Entry)
            mChart.setHighlightEnabled(false);

            XAxis xl = mChart.getXAxis();
            xl.setPosition(XAxis.XAxisPosition.BOTTOM);
            xl.setDrawAxisLine(true);
            xl.setAxisLineWidth(2);
            xl.setAvoidFirstLastClipping(true);

            YAxis leftAxis = mChart.getAxisLeft();
            leftAxis.setInverted(false);
            leftAxis.setAxisLineWidth(2);

            YAxis rightAxis = mChart.getAxisRight();
            rightAxis.setEnabled(false);

            // add data
            setData(position,mChart);

            // // restrain the maximum scale-out factor
            // mChart.setScaleMinima(3f, 3f);
            //
            // // center the view to a specific position inside the chart
            // mChart.centerViewPort(10, 50);

            // get the legend (only possible after setting data)
            mChart.getLegend().setEnabled(false);

            // modify the legend ...

            // dont forget to refresh the drawing
            mChart.invalidate();
            container.addView(mChart);
            return mChart;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }


    }

    private SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
    private void setData(int p,LineChart mChart) {

        ArrayList<String> xVals = new ArrayList<String>();
        ArrayList<Entry> yVals = new ArrayList<Entry>();
        for (int i = 0; i < senseList.size(); i++) {
            Sense s = senseList.get(i);
            xVals.add(sdf.format(new Date(s.getCreateAt())));
            switch (p){
                case 0:
                    yVals.add(new Entry(s.getCO2(), i));
                    break;
                case 1:
                    yVals.add(new Entry(s.getTemperature(), i));
                    break;
                case 2:
                    yVals.add(new Entry(s.getHumidity(), i));
                    break;
            }

        }

        // create a dataset and give it a type
        LineDataSet set1 = new LineDataSet(yVals, "DataSet 1");

        set1.setLineWidth(1.5f);
        set1.setCircleSize(4f);

        // create a data object with the datasets
        LineData data = new LineData(xVals, set1);

        // set data
        mChart.setData(data);
    }
}

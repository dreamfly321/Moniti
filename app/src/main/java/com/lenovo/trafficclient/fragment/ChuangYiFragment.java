package com.lenovo.trafficclient.fragment;

import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.lenovo.trafficclient.R;
import com.lenovo.trafficclient.util.SPUtil;

import java.text.SimpleDateFormat;
import java.util.TimeZone;

/**
 * Created by Asus on 2017/6/5.
 */

public class ChuangYiFragment extends BaseFragment implements View.OnClickListener{

    private TextView tvTime;
    private TextView tvCost;
    private Button btnStart;
    private int rate = 5;
    private boolean isStart = false;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_chuangyi, null);
    }


    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        btnStart = (Button) view.findViewById(R.id.btn_start);
        tvTime = (TextView) view.findViewById(R.id.tv_time);
        tvCost = (TextView) view.findViewById(R.id.tv_cost);
        btnStart.setOnClickListener(this);
    }

    @Override
    public void onResume() {
        super.onResume();
        isStart = SPUtil.sp.getBoolean("isStart",false);
        if (isStart){
            handler.post(runnable);
        }
    }

    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm:ss");

    Handler handler = new Handler();
    Runnable runnable = new Runnable() {
        @Override
        public void run() {
            simpleDateFormat.setTimeZone(TimeZone.getTimeZone("GMT"));
            long diff = System.currentTimeMillis() - SPUtil.sp.getLong("isTime",System.currentTimeMillis());
            tvTime.setText(simpleDateFormat.format(diff));
            double cost = Math.ceil(diff / 360000f) * rate;
            tvCost.setText("费用：" + cost + "元");
            btnStart.setText("结束");
            handler.postDelayed(runnable,1000);
        }
    };

    @Override
    public void onDestroy() {
        super.onDestroy();
        handler.removeCallbacks(runnable);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_start:
                //TODO implement
                if (isStart){
                    btnStart.setText("开始");
                    SPUtil.spe.putBoolean("isStart",false).commit();
                    handler.removeCallbacks(runnable);
                    isStart = false;
                }else {
                    isStart = true;
                    SPUtil.spe.putBoolean("isStart",true)
                            .putLong("isTime",System.currentTimeMillis())
                            .commit();
                    handler.post(runnable);

                }
                break;
        }
    }
}

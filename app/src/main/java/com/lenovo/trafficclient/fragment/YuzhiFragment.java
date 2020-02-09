package com.lenovo.trafficclient.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.lenovo.trafficclient.MyApp;
import com.lenovo.trafficclient.R;
import com.lenovo.trafficclient.util.SPUtil;

/*********************************************************************************
 Created by Android Studio.
 *Author:          Jack Fu
 *Version:         1.0
 *Date;            17-6-5 下午3:41
 *Description:     
 **********************************************************************************/
public class YuzhiFragment extends BaseFragment implements View.OnClickListener {


    private Spinner sp_carid;
    private TextView tv_yuzhi;
    private Button btn_acountTd_query;
    private EditText edt_min;
    private EditText edt_max;
    private Button btn_acountTd_setting;
    private TextView tv_yuzhi_light;
    private Button btn_lightTd_query;
    private EditText edt_lightmin;
    private EditText edt_lightmax;
    private Button btn_lightTd_setting;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_fazhi, null);
        initView(view);
        return view;
    }


    @Override
    public void onClick(View view) {
        try {
            int min;
            int max;
            switch (view.getId()) {
                case R.id.btn_acountTd_query:
                    min = SPUtil.sp.getInt("minAccount" + (sp_carid.getSelectedItemPosition() + 1), 10);
                    max = SPUtil.sp.getInt("maxAccount" + (sp_carid.getSelectedItemPosition() + 1), 5000);
                    tv_yuzhi.setText("最低" + min + "元-最高" + max + "元");
                    break;
                case R.id.btn_acountTd_setting:
                    min = Integer.parseInt(edt_min.getText().toString());
                    max = Integer.parseInt(edt_max.getText().toString());
                    if(min >= max){
                        Toast.makeText(getContext(), "最小值不能大于或等于最大值", Toast.LENGTH_SHORT).show();
                        return;
                    }

                    switch (sp_carid.getSelectedItemPosition() ){
                        case 0:
                            MyApp.setMaxAccount1(max);
                            MyApp.setMinAccount1(min);
                            break;
                        case 1:
                            MyApp.setMaxAccount2(max);
                            MyApp.setMinAccount2(min);
                            break;
                        case 2:
                            MyApp.setMaxAccount3(max);
                            MyApp.setMinAccount3(min);
                            break;
                        case 3:
                            MyApp.setMaxAccount4(max);
                            MyApp.setMinAccount4(min);
                            break;
                    }
                    break;
                case R.id.btn_lightTd_query:
                    min = MyApp.getMinLight();
                    max = MyApp.getMaxLight();
                    tv_yuzhi_light.setText("最低" + min + "-最高" + max + "");
                    break;
                case R.id.btn_lightTd_setting:
                    min = Integer.parseInt(edt_lightmin.getText().toString());
                    max = Integer.parseInt(edt_lightmax.getText().toString());
                    if(min >= max){
                        Toast.makeText(getContext(), "最小值不能大于或等于最大值", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    MyApp.setMinLight(min);
                    MyApp.setMaxLight(max);
                    break;
            }
        }catch (Exception e){
            Toast.makeText(getContext(), "设置失败", Toast.LENGTH_SHORT).show();
        }
    }

    private void initView(View view) {
        sp_carid = (Spinner) view.findViewById(R.id.sp_carid);
        tv_yuzhi = (TextView) view.findViewById(R.id.tv_yuzhi);
        btn_acountTd_query = (Button) view.findViewById(R.id.btn_acountTd_query);
        edt_min = (EditText) view.findViewById(R.id.edt_min);
        edt_max = (EditText) view.findViewById(R.id.edt_max);
        btn_acountTd_setting = (Button) view.findViewById(R.id.btn_acountTd_setting);
        tv_yuzhi_light = (TextView) view.findViewById(R.id.tv_yuzhi_light);
        btn_lightTd_query = (Button) view.findViewById(R.id.btn_lightTd_query);
        edt_lightmin = (EditText) view.findViewById(R.id.edt_lightmin);
        edt_lightmax = (EditText) view.findViewById(R.id.edt_lightmax);
        btn_lightTd_setting = (Button) view.findViewById(R.id.btn_lightTd_setting);

        btn_acountTd_query.setOnClickListener(this);
        btn_acountTd_setting.setOnClickListener(this);
        btn_lightTd_query.setOnClickListener(this);
        btn_lightTd_setting.setOnClickListener(this);
    }


}

package com.lenovo.trafficclient.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import com.lenovo.trafficclient.R;

/*********************************************************************************
 Created by Android Studio.
 *Author:          Jack Fu
 *Version:         1.0
 *Date;            17-6-5 下午6:13
 *Description:     
 **********************************************************************************/
public class MsgFragment extends BaseFragment {

    private Spinner sp_news;
    private TextView textView;
    private ListView list_recharge;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_news, null);

        initView(view);
        return view;
    }

    private void initView(View view) {
        sp_news = (Spinner) view.findViewById(R.id.sp_news);
        textView = (TextView) view.findViewById(R.id.textView);
        list_recharge = (ListView) view.findViewById(R.id.list_recharge);
    }
}

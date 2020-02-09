package com.lenovo.trafficclient.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;

import com.lenovo.trafficclient.R;
import com.lenovo.trafficclient.adapter.BillAdapter;
import com.lenovo.trafficclient.db.Bill;
import com.lenovo.trafficclient.db.DBHelper;

import java.sql.SQLException;
import java.util.List;

/*********************************************************************************
 Created by Android Studio.
 *Author:          Jack Fu
 *Version:         1.0
 *Date;            17-6-5 下午4:58
 *Description:     
 **********************************************************************************/
public class BillFragment extends BaseFragment implements View.OnClickListener {

    private Spinner sp_time;
    private Button btn_recharge_query;
    private ListView list_recharge;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_recharge, null);

        initView(view);
        return view;
    }

    private void initView(View view) {
        sp_time = (Spinner) view.findViewById(R.id.sp_time);
        btn_recharge_query = (Button) view.findViewById(R.id.btn_recharge_query);
        list_recharge = (ListView) view.findViewById(R.id.list_recharge);
        btn_recharge_query.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_recharge_query:
                boolean order = sp_time.getSelectedItemPosition() == 0 ? true: false;
                try {
                    List<Bill> list = DBHelper.getInstance(getActivity()).getBillIntegerDao().queryBuilder().orderBy("createAt",order).query();
                    list_recharge.setAdapter(new BillAdapter(getContext(),list));
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                break;
        }
    }
}

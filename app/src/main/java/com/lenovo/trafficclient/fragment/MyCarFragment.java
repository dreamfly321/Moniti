package com.lenovo.trafficclient.fragment;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.Toast;

import com.android.volley.VolleyError;
import com.google.gson.Gson;
import com.lenovo.trafficclient.R;
import com.lenovo.trafficclient.adapter.MyCarAdapter;
import com.lenovo.trafficclient.bean.Event;
import com.lenovo.trafficclient.db.Balance;
import com.lenovo.trafficclient.db.Bill;
import com.lenovo.trafficclient.db.DBHelper;
import com.lenovo.trafficclient.util.MyVolley;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.json.JSONException;
import org.json.JSONObject;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/6/5 0005.
 */

public class MyCarFragment extends BaseFragment implements  AdapterView.OnItemClickListener {

    private GridView gvMybalance;
    private List<Balance> list;
    private MyCarAdapter myCarAdapter;
    private AlertDialog alertDialog;

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void e(Event event){
        switch (event.getType()){
            case MIAO5:
                MyVolley.GetCarAccountBalance(1, new MyVolley.Listener() {
                    @Override
                    public void success(JSONObject j) {
                        try {
                            if (j.getString("RESULT").equals("S")){
                                JSONObject jsonObject = j.getJSONObject("DATA");
                                int money = jsonObject.getInt("money");
                                Balance balance=new Balance();
                                balance.setBalance(money);
                                list.set(0,balance);
                                myCarAdapter.notifyDataSetChanged();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void error(VolleyError e) {
                        System.out.println(e);
                    }
                });
                MyVolley.GetCarAccountBalance(2, new MyVolley.Listener() {
                    @Override
                    public void success(JSONObject j) {
                        try {
                            if (j.getString("RESULT").equals("S")){
                                JSONObject jsonObject = j.getJSONObject("DATA");
                                int money = jsonObject.getInt("money");
                                Balance balance=new Balance();
                                balance.setBalance(money);
                                list.set(1,balance);
                                myCarAdapter.notifyDataSetChanged();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void error(VolleyError e) {
                        System.out.println(e);
                    }
                });
                MyVolley.GetCarAccountBalance(3, new MyVolley.Listener() {
                    @Override
                    public void success(JSONObject j) {
                        try {
                            if (j.getString("RESULT").equals("S")){
                                JSONObject jsonObject = j.getJSONObject("DATA");
                                int money = jsonObject.getInt("money");
                                Balance balance=new Balance();
                                balance.setBalance(money);
                                list.set(2,balance);
                                myCarAdapter.notifyDataSetChanged();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void error(VolleyError e) {
                        System.out.println(e);
                    }
                });
                MyVolley.GetCarAccountBalance(4, new MyVolley.Listener() {
                    @Override
                    public void success(JSONObject j) {
                        try {
                            if (j.getString("RESULT").equals("S")){
                                JSONObject jsonObject = j.getJSONObject("DATA");
                                int money = jsonObject.getInt("money");
                                Balance balance=new Balance();
                                balance.setBalance(money);
                                list.set(3,balance);
                                myCarAdapter.notifyDataSetChanged();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void error(VolleyError e) {
                        System.out.println(e);
                    }
                });
                break;
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_balance, null);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        list=new ArrayList<Balance>();
        list.add(new Balance());
        list.add(new Balance());
        list.add(new Balance());
        list.add(new Balance());
        myCarAdapter=new MyCarAdapter(getContext(),list);
        gvMybalance = (GridView) view.findViewById(R.id.gv_mybalance);
        gvMybalance.setAdapter(myCarAdapter);
        gvMybalance.setOnItemClickListener(this);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        switch (position){
            case 0:
                final int index=position+1;
                set(index);
                break;
            case 1:
                final int index1=position+1;
                set(index1);
                break;
            case 2:
                final int index2=position+1;
                set(index2);
                break;
            case 3:
                final int index3=position+1;
                set(index3);
                break;
        }
    }

    private void set(final int index) {
        View layout = LayoutInflater.from(getContext()).inflate(R.layout.dialog_recharge, null);
        final EditText et_money = (EditText) layout.findViewById(R.id.edt_recharge_value);
        alertDialog = new AlertDialog.Builder(getContext())
                .setView(layout)
                .setTitle("我的充值")
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        final int Money = Integer.parseInt(et_money.getText().toString());
                        MyVolley.SetCarAccountRecharge(index, Money, new MyVolley.Listener() {
                            @Override
                            public void success(JSONObject j) {
                                try {
                                    if (j.getString("RESULT").equals("S")) {
                                        if (Money > 500) {
                                            View view1 = LayoutInflater.from(getContext()).inflate(R.layout.dialog_pay, null);
                                            final EditText et_pay = (EditText) view1.findViewById(R.id.edt_pay);
                                            alertDialog = new AlertDialog.Builder(getContext())
                                                    .setView(view1)
                                                    .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                                                        @Override
                                                        public void onClick(DialogInterface dialog, int which) {
                                                            if (!et_pay.equals("123456")) {
                                                                Toast.makeText(getContext(), index + "号小车充值" + Money + "元失败", Toast.LENGTH_SHORT).show();
                                                            }
                                                        }
                                                    }).show();
                                            return;
                                        }else {
                                            Toast.makeText(getContext(), index + "号小车充值" + Money + "元成功", Toast.LENGTH_SHORT).show();
                                        }
                                        Bill bill=new Bill();
                                        bill.setCarId(index);
                                        bill.setMoney(Money);
                                        bill.setUser("admin");
                                        bill.setCreateAt(System.currentTimeMillis());
                                        try {
                                            DBHelper.getInstance(getContext()).getBillIntegerDao().create(bill);
                                        } catch (SQLException e) {
                                            e.printStackTrace();
                                        }
                                        Toast.makeText(getContext(), index + "号小车充值" + Money + "元成功", Toast.LENGTH_SHORT).show();
                                    } else {
                                        Toast.makeText(getContext(), index + "号小车充值" + Money + "元失败", Toast.LENGTH_SHORT).show();
                                    }
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }

                            @Override
                            public void error(VolleyError e) {
                                System.out.println(e);
                            }
                        });
                    }
                })
                .setNegativeButton("取消", null).show();
    }
}

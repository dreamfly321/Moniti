package com.lenovo.trafficclient.adapter;

import android.app.NotificationManager;
import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.lenovo.trafficclient.MyApp;
import com.lenovo.trafficclient.R;
import com.lenovo.trafficclient.db.Balance;
import com.lenovo.trafficclient.db.DBHelper;
import com.lenovo.trafficclient.db.Notify;
import com.lenovo.trafficclient.util.SPUtil;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/6/5 0005.
 */

public class MyCarAdapter<T extends Balance> extends BaseAdapter {

    private List<T> objects = new ArrayList<T>();

    private Context context;
    private LayoutInflater layoutInflater;
    private NotificationManager nm;
    public MyCarAdapter(Context context,List<T> objects) {
        this.objects=objects;
        this.context = context;
        this.layoutInflater = LayoutInflater.from(context);
        nm = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
    }

    @Override
    public int getCount() {
        return objects.size();
    }

    @Override
    public T getItem(int position) {
        return objects.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = layoutInflater.inflate(R.layout.item_balance, null);
            convertView.setTag(new ViewHolder(convertView));
        }
        initializeViews((T)getItem(position),position, (ViewHolder) convertView.getTag());
        return convertView;
    }

    private void initializeViews(T object,int position, ViewHolder holder) {
        int index=position+1;
        //TODO implement
        holder.tvBalanceCarid.setText(index+"");
        holder.tvBalanceValue.setText(object.getBalance()+"");
        Notify notify = new Notify();
        switch (position){
            case 0:
                if(object.getBalance() < MyApp.getMinAccount1()){
                    notify.setMessage("警告：小车号1当前余额:xx元  账户最低阈值:"+ MyApp.getMinAccount1()+"元");
                    notify.setType("account");
                    notify.setCreateAt(System.currentTimeMillis());
                    try {
                        DBHelper.getInstance(context).getNotifyIntegerDao().create(notify);
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                    nm.notify(1,notify.buildNotification("帐户余额",context));
                }else{
                    nm.cancel(1);
                }

                getstatus(object, holder);
                break;
            case 1:
                if(object.getBalance() < MyApp.getMinAccount2()){
                    notify.setMessage("警告：小车号2当前余额:" + object.getBalance() + "元 账户最低阈值:"+ MyApp.getMinAccount2()+"元");
                    notify.setType("account");
                    notify.setCreateAt(System.currentTimeMillis());
                    try {
                        DBHelper.getInstance(context).getNotifyIntegerDao().create(notify);
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                    nm.notify(2,notify.buildNotification("帐户余额",context));
                }else{
                    nm.cancel(2);
                }
                getstatus(object, holder);
                break;
            case 2:
                if(object.getBalance() < MyApp.getMinAccount3()){
                    notify.setMessage("警告：小车号3当前余额:"+object.getBalance()+"元  账户最低阈值:"+ MyApp.getMinAccount3()+"元");
                    notify.setType("account");
                    notify.setCreateAt(System.currentTimeMillis());
                    try {
                        DBHelper.getInstance(context).getNotifyIntegerDao().create(notify);
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                    nm.notify(3,notify.buildNotification("帐户余额",context));
                }else{
                    nm.cancel(3);
                }
                getstatus(object, holder);
                break;
            case 3:
                if(object.getBalance() < MyApp.getMinAccount4()){
                    notify.setMessage("警告：小车号4当前余额:"+object.getBalance()+"元  账户最低阈值:"+ MyApp.getMinAccount4()+"元");
                    notify.setType("account");
                    notify.setCreateAt(System.currentTimeMillis());
                    try {
                        DBHelper.getInstance(context).getNotifyIntegerDao().create(notify);
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                    nm.notify(4,notify.buildNotification("帐户余额",context));
                }else{
                    nm.cancel(4);
                }
                getstatus(object, holder);
                break;
        }
    }

    private void getstatus(T object, ViewHolder holder) {
        if (object.getBalance()<100){
            holder.rl.setBackgroundColor(Color.RED);
            holder.tvBalanceState.setText("警报");
        }else {
            holder.rl.setBackgroundColor(Color.GREEN);
            holder.tvBalanceState.setText("正常");
        }
    }

    protected class ViewHolder {
        private RelativeLayout rl;
        private TextView tvBalanceCarid;
        private TextView tvBalanceState;
        private TextView tvBalanceValue;

        public ViewHolder(View view) {
            rl = (RelativeLayout) view.findViewById(R.id.rl);
            tvBalanceCarid = (TextView) view.findViewById(R.id.tv_balance_carid);
            tvBalanceState = (TextView) view.findViewById(R.id.tv_balance_state);
            tvBalanceValue = (TextView) view.findViewById(R.id.tv_balance_value);
        }
    }

}

package com.lenovo.trafficclient.adapter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.lenovo.trafficclient.R;
import com.lenovo.trafficclient.db.Bill;

public class BillAdapter<T extends Bill> extends BaseAdapter {

    private List<T> objects;

    private Context context;
    private LayoutInflater layoutInflater;

    public BillAdapter(Context context,List<T> objects) {
        this.context = context;
        this.layoutInflater = LayoutInflater.from(context);
        this.objects = objects;
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
            convertView = layoutInflater.inflate(R.layout.item_recharge, null);
            convertView.setTag(new ViewHolder(convertView));
        }
        initializeViews((T)getItem(position),position, (ViewHolder) convertView.getTag());
        return convertView;
    }

    private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
    private void initializeViews(T object,int p, ViewHolder holder) {
        //TODO implement
        holder.tvRechargeNum.setText((p+1)+"");
        holder.tvRechargeCarId.setText(object.getCarId()+"");
        holder.tvRechargeMoney.setText(object.getMoney()+"");
        holder.tvRechargeUser.setText(object.getUser()+"");
        holder.tvRechargeTime.setText(sdf.format(new Date(object.getCreateAt())));
    }

    protected class ViewHolder {
        private TextView tvRechargeNum;
        private TextView tvRechargeCarId;
        private TextView tvRechargeMoney;
        private TextView tvRechargeUser;
        private TextView tvRechargeTime;

        public ViewHolder(View view) {
            tvRechargeNum = (TextView) view.findViewById(R.id.tv_recharge_num);
            tvRechargeCarId = (TextView) view.findViewById(R.id.tv_recharge_carId);
            tvRechargeMoney = (TextView) view.findViewById(R.id.tv_recharge_money);
            tvRechargeUser = (TextView) view.findViewById(R.id.tv_recharge_user);
            tvRechargeTime = (TextView) view.findViewById(R.id.tv_recharge_time);
        }
    }
}

package com.lenovo.trafficclient.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.lenovo.trafficclient.R;
import com.lenovo.trafficclient.db.News;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/6/5 0005.
 */

public class NewsAdapter<T extends News> extends BaseAdapter {

    private List<T> objects = new ArrayList<T>();

    private Context context;
    private LayoutInflater layoutInflater;

    public NewsAdapter(Context context) {
        this.context = context;
        this.layoutInflater = LayoutInflater.from(context);
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
            convertView = layoutInflater.inflate(R.layout.item_news, null);
            convertView.setTag(new ViewHolder(convertView));
        }
        initializeViews((T)getItem(position), (ViewHolder) convertView.getTag());
        return convertView;
    }

    private void initializeViews(T object, ViewHolder holder) {
        //TODO implement
    }

    protected class ViewHolder {
        private TextView tvNewsNum;
        private TextView tvNewsTitle;
        private TextView tvNewsText;
        private TextView tvNewsTime;

        public ViewHolder(View view) {
            tvNewsNum = (TextView) view.findViewById(R.id.tv_news_num);
            tvNewsTitle = (TextView) view.findViewById(R.id.tv_news_title);
            tvNewsText = (TextView) view.findViewById(R.id.tv_news_text);
            tvNewsTime = (TextView) view.findViewById(R.id.tv_news_time);
        }
    }

}

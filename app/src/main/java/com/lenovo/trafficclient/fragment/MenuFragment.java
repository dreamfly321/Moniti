package com.lenovo.trafficclient.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import com.lenovo.trafficclient.LoginActivity;
import com.lenovo.trafficclient.MainActivity;
import com.lenovo.trafficclient.R;
import com.lenovo.trafficclient.bean.TabBean;
import com.lenovo.trafficclient.util.SPUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/*********************************************************************************
 Created by Android Studio.
 *Author:          Jack Fu
 *Version:         1.0
 *Date;            17-6-5 下午3:11
 *Description:     
 **********************************************************************************/
public class MenuFragment extends Fragment{
    private ListView listMenu;
    private String[] titles = {
        "我的驾座",
        "我的交通",
        "阈值设置",
        "公交查询",
        "我的消息",
        "创意题",
        "退出登录",
    };
    private List<Map<String,String>> list = new ArrayList<Map<String, String>>();
    public MenuFragment() {
        for (String title : titles) {
            Map map = new HashMap();
            map.put("title",title);
            list.add(map);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_menu, null);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        listMenu = (ListView) view.findViewById(R.id.list_menu);
        SimpleAdapter sa = new SimpleAdapter(getContext(),list,R.layout.item_menu,new String[]{"title"},new int[]{R.id.tv_title});
        listMenu.setAdapter(sa);
        listMenu.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                MainActivity activity = (MainActivity) getActivity();
                activity.setTitle(titles[position]);
                List<TabBean> tabs = new ArrayList<TabBean>();
                switch (position){
                    case 0:
                        tabs.add(new TabBean("我的余额",new MyCarFragment()));
                        tabs.add(new TabBean("充值记录",new BillFragment()));
                        activity.setTabs(tabs);
                        break;
                    case 1:
                        tabs.add(new TabBean("我的路况",new RoadEnvFragment()));
                        tabs.add(new TabBean("道路环境",new RoadEnvFragment()));
                        activity.setTabs(tabs);
                        break;
                    case 2:
                        tabs.add(new TabBean("阈值设置",new YuzhiFragment()));
                        activity.setTabs(tabs);
                        break;
                    case 3:
                        tabs.add(new TabBean("站台信息",new BusFragment()));
                        tabs.add(new TabBean("候车环境",new BusEnvFragment()));
                        activity.setTabs(tabs);
                        break;
                    case 4:
                        tabs.add(new TabBean("消息查询",new MsgFragment()));
                        tabs.add(new TabBean("消息统计",new MesStaicFragment()));
                        activity.setTabs(tabs);
                        break;
                    case 5:
                        tabs.add(new TabBean("创意题",new ChuangYiFragment()));
                        activity.setTabs(tabs);
                        break;
                    case 6:
                        SPUtil.spe.putBoolean("login",false).commit();
                        startActivity(new Intent(activity,LoginActivity.class));
                        activity.finish();
                        break;

                }
            }
        });
    }

}

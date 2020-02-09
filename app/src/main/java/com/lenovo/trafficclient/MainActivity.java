package com.lenovo.trafficclient;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.IdRes;
import android.support.v4.app.FragmentActivity;
import android.support.v4.widget.SlidingPaneLayout;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.lenovo.trafficclient.bean.Event;
import com.lenovo.trafficclient.bean.TabBean;
import com.lenovo.trafficclient.fragment.BaseFragment;
import com.lenovo.trafficclient.util.SPUtil;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends FragmentActivity {

    private ImageView left;
    private TextView title;
    private TextView username;
    private FrameLayout container;
    private RadioGroup tab_group;
    private SlidingPaneLayout sliding;
    private List<TabBean> tabs = new ArrayList<TabBean>();
    private AlertDialog alertDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();

        EventBus.getDefault().register(this);
        View layout= LayoutInflater.from(this).inflate(R.layout.dialog_net,null);
        alertDialog=new AlertDialog.Builder(this)
                .setTitle("网络信息提示")
                .setView(layout)
                .setPositiveButton("设置", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent=new Intent(Settings.ACTION_WIFI_SETTINGS);
                        startActivity(intent);
                    }
                })
                .setNegativeButton("退出", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
                    }
                }).create();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void e(Event event){
        switch (event.getType()){
            case NO_NETWORK:

               if(!alertDialog.isShowing())alertDialog.show();
                break;
        }
    }

    private void initView() {
        left = (ImageView) findViewById(R.id.left);
        title = (TextView) findViewById(R.id.title);
        username = (TextView) findViewById(R.id.username);
        container = (FrameLayout) findViewById(R.id.container);
        tab_group = (RadioGroup) findViewById(R.id.tab_group);
        sliding = (SlidingPaneLayout) findViewById(R.id.sliding);
        left.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(sliding.isOpen()){
                    sliding.closePane();
                }else{
                    sliding.openPane();
                }
            }
        });

        tab_group.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
                replaceFragment(tabs.get(checkedId).fragment);
                for (int i = 0; i < group.getChildCount(); i++) {
                    RadioButton radioButton = (RadioButton) group.getChildAt(i);
                    if(radioButton.getId() == checkedId){
                        radioButton.setTextSize(22);
                    }else{
                        radioButton.setTextSize(18);
                    }
                }
            }
        });

        username.setText(SPUtil.sp.getString("UserName","admin"));
    }

    private void updateTab(){

    }


    public void replaceFragment(BaseFragment b){
        getSupportFragmentManager().beginTransaction().replace(R.id.container,b).commit();
    }

    public void setTitle(String title){
        this.title.setText(title);
    }


    public void setTabs(List<TabBean> tabs){
        this.tabs = tabs;
        tab_group.removeAllViews();
        if(tabs.size() <2){
            tab_group.setVisibility(View.GONE);
        }else{
            tab_group.setVisibility(View.VISIBLE);
        }

        for (int i = 0; i < tabs.size(); i++) {
            TabBean tabBean = tabs.get(i);
            RadioButton radioButton = new RadioButton(this);
            radioButton.setButtonDrawable(new ColorDrawable(Color.TRANSPARENT));
            radioButton.setGravity(Gravity.CENTER);
            radioButton.setTextColor(Color.WHITE);
            if(i == 0){
                radioButton.setTextSize(22);
            }else{
                radioButton.setTextSize(18);
            }
            radioButton.setId(i);
            radioButton.setText(tabBean.title);
            radioButton.setLayoutParams(new RadioGroup.LayoutParams(-1,-1,1));
            tab_group.addView(radioButton);
        }

        replaceFragment(tabs.get(0).fragment);
    }

    long time=0;
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode==event.KEYCODE_BACK){
            if (Math.abs(System.currentTimeMillis()-time)>2000){
                time=System.currentTimeMillis();
                Toast.makeText(this, "再按一次退出", Toast.LENGTH_SHORT).show();
                return false;
            }
            return super.onKeyDown(keyCode, event);
        }
        return super.onKeyDown(keyCode, event);
    }
}

package com.lenovo.trafficclient;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import java.util.Locale;

/**
 * Created by Administrator on 2017/5/31.
 */

public class LanguageActivity extends Activity implements View.OnClickListener {

    private ImageView img_setting;
    private TextView tv_set;
    private RadioButton china;
    private RadioButton english;
    private RadioGroup group;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_language);
        initView();
    }

    private void initView() {
        img_setting = (ImageView) findViewById(R.id.img_setting);
        img_setting.setOnClickListener(this);
        tv_set = (TextView) findViewById(R.id.tv_set);
        tv_set.setOnClickListener(this);
        china = (RadioButton) findViewById(R.id.china);
        english = (RadioButton) findViewById(R.id.english);
        group = (RadioGroup) findViewById(R.id.group);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.img_setting:
                Intent login=new Intent(this,LoginActivity.class);
                startActivity(login);
                finish();
                break;
            case R.id.tv_set:
                Resources resources=getResources();
                Configuration configuration=resources.getConfiguration();
                switch (group.getCheckedRadioButtonId()){
                    case R.id.china:
                        configuration.locale= Locale.CHINA;
                        break;
                    case R.id.english:
                        configuration.locale=Locale.ENGLISH;
                        break;
                }
                resources.updateConfiguration(configuration,resources.getDisplayMetrics());
                Intent intent=new Intent(this,LoginActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                break;
        }
    }
}

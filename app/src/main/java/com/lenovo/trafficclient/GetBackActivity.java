package com.lenovo.trafficclient;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.lenovo.trafficclient.util.SPUtil;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Administrator on 2017/5/31.
 */

public class GetBackActivity extends Activity implements View.OnClickListener {

    private ImageView img_setting;
    private EditText et_username;
    private EditText et_email;
    private Button btn_get_back;
    private String str="[\\w\\d]+@[\\w\\s]+.[\\w\\d]+";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_back);
        initView();
    }


    private void initView() {
        img_setting = (ImageView) findViewById(R.id.img_setting);
        img_setting.setOnClickListener(this);
        et_username = (EditText) findViewById(R.id.et_username);
        et_email = (EditText) findViewById(R.id.et_email);
        btn_get_back = (Button) findViewById(R.id.btn_get_back);

        btn_get_back.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_get_back:
                submit();
                break;
            case R.id.img_setting:
                Intent login=new Intent(this,LoginActivity.class);
                startActivity(login);
                finish();
                break;
        }
    }

    private void submit() {
        // validate
        String username = et_username.getText().toString().trim();
        if (TextUtils.isEmpty(username)) {
            Toast.makeText(this, "请输入用户名", Toast.LENGTH_SHORT).show();
            return;
        }

        String email = et_email.getText().toString().trim();
        if (TextUtils.isEmpty(email)) {
            Toast.makeText(this, "请输入邮箱", Toast.LENGTH_SHORT).show();
            return;
        }

        // TODO validate success, do something
        Pattern pattern=Pattern.compile(str);
        Matcher matcher=pattern.matcher(email);
        if (!matcher.matches()){
            Toast.makeText(this, "邮箱输入不合法", Toast.LENGTH_SHORT).show();
            return;
        }

        if (username.equals(SPUtil.sp.getString("Username",""))&&email.equals(SPUtil.sp.getString("Email",""))){
            Toast.makeText(this, SPUtil.sp.getString("Password",""), Toast.LENGTH_SHORT).show();
            return;
        }

    }
}

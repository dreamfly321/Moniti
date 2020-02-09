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
 *///

public class RegisterActivity extends Activity implements View.OnClickListener {

    private ImageView img_setting;
    private EditText et_username;
    private EditText et_email;
    private EditText et_password1;
    private EditText et_password2;
    private Button bt_register;
    private String str="[\\w\\d]+@[\\w\\s]+.[\\w\\d]+";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        initView();
    }

    private void initView() {
        img_setting = (ImageView) findViewById(R.id.img_setting);
        img_setting.setOnClickListener(this);
        et_username = (EditText) findViewById(R.id.et_username);
        et_email = (EditText) findViewById(R.id.et_email);
        et_password1 = (EditText) findViewById(R.id.et_password1);
        et_password2 = (EditText) findViewById(R.id.et_password2);
        bt_register = (Button) findViewById(R.id.bt_register);

        bt_register.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bt_register:
                submit();
                break;
            case R.id.img_setting:
                Intent set=new Intent(this,LoginActivity.class);
                startActivity(set);
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

        String password1 = et_password1.getText().toString().trim();
        if (TextUtils.isEmpty(password1)) {
            Toast.makeText(this, "请输入密码", Toast.LENGTH_SHORT).show();
            return;
        }

        String password2 = et_password2.getText().toString().trim();
        if (TextUtils.isEmpty(password2)) {
            Toast.makeText(this, "请验证密码", Toast.LENGTH_SHORT).show();
            return;
        }

        // TODO validate success, do something
        if (username.length()<4){
            Toast.makeText(this, "输入的用户名要大于4位", Toast.LENGTH_SHORT).show();
            return;
        }

        if (password1.length()<6){
            Toast.makeText(this, "输入的密码要大于6位", Toast.LENGTH_SHORT).show();
            return;
        }

        if (email.length()<6){
            Toast.makeText(this, "输入的邮箱要大于6位", Toast.LENGTH_SHORT).show();
            return;
        }

        if (!password1.equals(password2)){
            Toast.makeText(this, "输入的验证密码不匹配", Toast.LENGTH_SHORT).show();
            return;
        }

        Pattern pattern=Pattern.compile(str);
        Matcher matcher=pattern.matcher(email);
        if (!matcher.matches()){
            Toast.makeText(this, "邮箱输入不合法", Toast.LENGTH_SHORT).show();
            return;
        }

        SPUtil.spe.putString("Username",username)
                .putString("Password",password1)
                .putString("Email",email).commit();

        Toast.makeText(this, "注册成功", Toast.LENGTH_SHORT).show();

        Intent intent=new Intent(this,LoginActivity.class);
        startActivity(intent);

    }
}

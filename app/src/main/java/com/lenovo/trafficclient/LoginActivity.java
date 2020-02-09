package com.lenovo.trafficclient;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.lenovo.trafficclient.util.SPUtil;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Administrator on 2017/5/31.
 */

public class LoginActivity extends Activity implements View.OnClickListener {


    private ImageView img_setting;
    private TextView tv_language;
    private EditText et_username;
    private EditText et_password;
    private TextView tv_register;
    private TextView tv_get_back;
    private Button btn_login;
    private AlertDialog alert;
    private String str="([1-9]|[1-9]\\d|1\\d\\d|2[0-4]\\d|25[0-5])(.+(\\d|[1-9]\\d|1\\d\\d|2[0-4]\\d|25[0-5])){3}";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initView();
        if (SPUtil.sp.getBoolean("login",false)){
            Intent login=new Intent(this,MainActivity.class);
            startActivity(login);
            finish();
        }
    }

    private void initView() {
        img_setting = (ImageView) findViewById(R.id.img_setting);
        img_setting.setOnClickListener(this);
        tv_language = (TextView) findViewById(R.id.tv_language);
        tv_language.setOnClickListener(this);
        et_username = (EditText) findViewById(R.id.et_username);
        et_password = (EditText) findViewById(R.id.et_password);
        tv_register = (TextView) findViewById(R.id.tv_register);
        tv_register.setOnClickListener(this);
        tv_get_back = (TextView) findViewById(R.id.tv_get_back);
        tv_get_back.setOnClickListener(this);
        btn_login = (Button) findViewById(R.id.btn_login);

        btn_login.setOnClickListener(this);
    }

    @RequiresApi(api = Build.VERSION_CODES.HONEYCOMB)
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_login:
                submit();
                break;
            case R.id.img_setting:
                View layout= LayoutInflater.from(this).inflate(R.layout.dialog_ip,null);
                final EditText et_ip1= (EditText) layout.findViewById(R.id.ip1);
                final EditText et_ip2= (EditText) layout.findViewById(R.id.ip2);
                final EditText et_ip3= (EditText) layout.findViewById(R.id.ip3);
                final EditText et_ip4= (EditText) layout.findViewById(R.id.ip4);
                alert=new AlertDialog.Builder(this,AlertDialog.THEME_DEVICE_DEFAULT_LIGHT)
                        .setTitle("服务器地址设置")
                        .setView(layout)
                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                String ip=et_ip1.getText().toString()
                                        +et_ip2.getText().toString()
                                        +et_ip3.getText().toString()
                                        +et_ip4.getText().toString();
                                Pattern pattern=Pattern.compile(str);
                                Matcher matcher=pattern.matcher(ip);
                                if (!matcher.matches()){
                                    Toast.makeText(LoginActivity.this, "ip输入不合法", Toast.LENGTH_SHORT).show();
                                    return;
                                }else {
                                    Toast.makeText(LoginActivity.this, "ip输入合法", Toast.LENGTH_SHORT).show();
                                    return;
                                }
                            }
                        })
                        .setNegativeButton("取消",null).create();
                Window window=alert.getWindow();
                WindowManager.LayoutParams layoutParams=new WindowManager.LayoutParams();
                layoutParams.alpha=0.7f;
                window.setAttributes(layoutParams);
                alert.show();
                break;
            case R.id.tv_language:
                Intent language=new Intent(this,LanguageActivity.class);
                startActivity(language);
                break;
            case R.id.tv_register:
                Intent register=new Intent(this,RegisterActivity.class);
                startActivity(register);
                break;
            case R.id.tv_get_back:
                Intent get_back=new Intent(this,GetBackActivity.class);
                startActivity(get_back);
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

        String password = et_password.getText().toString().trim();
        if (TextUtils.isEmpty(password)) {
            Toast.makeText(this, "请输入用户名", Toast.LENGTH_SHORT).show();
            return;
        }

        // TODO validate success, do something
        if (username.equals("admin")&&password.equals("123456")){
            SPUtil.spe.putBoolean("login",true).commit();
            SPUtil.spe.putString("UserName",username).commit();
            Intent login=new Intent(this,MainActivity.class);
            startActivity(login);
            finish();
            Toast.makeText(this, "登录成功", Toast.LENGTH_SHORT).show();
        }else if(username.equals(SPUtil.sp.getString("UserName","admin"))&&password.equals(SPUtil.sp.getString("Password","123456"))){
            SPUtil.spe.putBoolean("login",true).putString("UserName",username).commit();
            Intent login=new Intent(this,MainActivity.class);
            startActivity(login);
            finish();
            Toast.makeText(this, "登录成功", Toast.LENGTH_SHORT).show();
        }else {
            Toast.makeText(this, "用户名或密码出错", Toast.LENGTH_SHORT).show();
        }

    }
}

package com.lenovo.trafficclient;

import android.app.Application;
import android.content.Intent;

import com.lenovo.trafficclient.util.MyVolley;
import com.lenovo.trafficclient.util.SPUtil;

/*********************************************************************************
 Created by Android Studio.
 *Author:          Jack Fu
 *Version:         1.0
 *Date;            17-6-5 下午3:00
 *Description:     
 **********************************************************************************/
public class MyApp extends Application {
    private static int minLight = 100;
    private static int maxLight = 1000;
    private static int minAccount1 = 100;
    private static int maxAccount1 = 5000;
    private static int minAccount2 = 100;
    private static int maxAccount2 = 5000;
    private static int minAccount3 = 100;
    private static int maxAccount3 = 5000;
    private static int minAccount4 = 100;
    private static int maxAccount4 = 5000;
    private static int minAccount5 = 100;
    private static int maxAccount5 = 5000;



    @Override
    public void onCreate() {
        super.onCreate();
        SPUtil.init(this);
        MyVolley.init(this);
        startService(new Intent(this,MyService.class));

        minAccount1 = SPUtil.sp.getInt("minAccount1",10);
        maxAccount1 = SPUtil.sp.getInt("maxAccount1",5000);

        minAccount2 = SPUtil.sp.getInt("minAccount2",10);
        maxAccount2 = SPUtil.sp.getInt("maxAccount2",5000);

        minAccount3 = SPUtil.sp.getInt("minAccount3",10);
        maxAccount3 = SPUtil.sp.getInt("maxAccount3",5000);

        minAccount4 = SPUtil.sp.getInt("minAccount4",10);
        maxAccount4 = SPUtil.sp.getInt("maxAccount4",5000);

        minAccount5 = SPUtil.sp.getInt("minAccount5",10);
        maxAccount5 = SPUtil.sp.getInt("maxAccount5",5000);

        minLight = SPUtil.sp.getInt("minLight",10);
        maxLight = SPUtil.sp.getInt("maxLight",1000);
    }

    public static int getMinLight() {
        return minLight;
    }

    public static void setMinLight(int minLight) {
        MyApp.minLight = minLight;
        SPUtil.spe.putInt("minLight",minLight).commit();
    }

    public static int getMaxLight() {
        return maxLight;
    }

    public static void setMaxLight(int maxLight) {
        MyApp.maxLight = maxLight;
        SPUtil.spe.putInt("maxLight",maxLight).commit();
    }

    public static int getMinAccount1() {
        return minAccount1;
    }

    public static void setMinAccount1(int minAccount1) {
        MyApp.minAccount1 = minAccount1;
        SPUtil.spe.putInt("minAccount1",minAccount1).commit();
    }

    public static int getMaxAccount1() {
        return maxAccount1;
    }

    public static void setMaxAccount1(int maxAccount1) {
        MyApp.maxAccount1 = maxAccount1;
        SPUtil.spe.putInt("maxAccount1",maxAccount1).commit();
    }

    public static int getMinAccount2() {
        return minAccount2;
    }

    public static void setMinAccount2(int minAccount2) {
        MyApp.minAccount2 = minAccount2;
        SPUtil.spe.putInt("minAccount2",minAccount2).commit();
    }

    public static int getMaxAccount2() {
        return maxAccount2;
    }

    public static void setMaxAccount2(int maxAccount2) {
        MyApp.maxAccount2 = maxAccount2;
        SPUtil.spe.putInt("maxAccount2",maxAccount2).commit();
    }

    public static int getMinAccount3() {
        return minAccount3;
    }

    public static void setMinAccount3(int minAccount3) {
        MyApp.minAccount3 = minAccount3;
        SPUtil.spe.putInt("minAccount3",minAccount3).commit();
    }

    public static int getMaxAccount3() {
        return maxAccount3;
    }

    public static void setMaxAccount3(int maxAccount3) {
        MyApp.maxAccount3 = maxAccount3;
        SPUtil.spe.putInt("maxAccount3",maxAccount3).commit();
    }

    public static int getMinAccount4() {
        return minAccount4;
    }

    public static void setMinAccount4(int minAccount4) {
        MyApp.minAccount4 = minAccount4;
        SPUtil.spe.putInt("minAccount4",minAccount4).commit();
    }

    public static int getMaxAccount4() {
        return maxAccount4;
    }

    public static void setMaxAccount4(int maxAccount4) {
        MyApp.maxAccount4 = maxAccount4;
        SPUtil.spe.putInt("maxAccount4",maxAccount4).commit();
    }

    public static int getMinAccount5() {
        return minAccount5;
    }

    public static void setMinAccount5(int minAccount5) {
        MyApp.minAccount5 = minAccount5;
        SPUtil.spe.putInt("minAccount5",minAccount5).commit();

    }

    public static int getMaxAccount5() {
        return maxAccount5;
    }

    public static void setMaxAccount5(int maxAccount5) {
        MyApp.maxAccount5 = maxAccount5;
        SPUtil.spe.putInt("maxAccount5",maxAccount5).commit();

    }
}

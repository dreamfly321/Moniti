package com.lenovo.trafficclient.util;

import android.content.Context;
import android.content.SharedPreferences;

/*********************************************************************************
 Created by Android Studio.
 *Author:          Jack Fu
 *Version:         1.0
 *Date;            17-6-5 下午3:02
 *Description:     
 **********************************************************************************/
public class SPUtil  {
    public static SharedPreferences sp;
    public static SharedPreferences.Editor spe;

    public static void init(Context c){
        sp = c.getSharedPreferences("app",0);
        spe = sp.edit();
    }

}

package com.lenovo.trafficclient;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.os.Handler;
import android.os.IBinder;

import com.lenovo.trafficclient.bean.Event;

import org.greenrobot.eventbus.EventBus;

public class MyService extends Service {
    public MyService() {
    }

    @Override
    public void onCreate() {
        super.onCreate();
        handler.post(r);
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    long count = 0;

    Handler handler = new Handler();
    Runnable r = new Runnable() {
        @Override
        public void run() {
            if(count % 3 == 0){
                EventBus.getDefault().post(new Event("", Event.Type.MIAO3));
            }
            if (count%5==0){
                EventBus.getDefault().post(new Event("", Event.Type.MIAO5));
            }

            if (!getNet(MyService.this)){
                EventBus.getDefault().post(new Event("", Event.Type.NO_NETWORK));
            }

            EventBus.getDefault().post(new Event("", Event.Type.MIAO));
            count++;
            handler.postDelayed(this,1000);
        }
    };

    @Override
    public void onDestroy() {
        super.onDestroy();
        handler.removeCallbacks(r);
    }

    public boolean getNet(Context context){
        boolean isNet=false;
        ConnectivityManager connectivityManager= (ConnectivityManager) getSystemService(CONNECTIVITY_SERVICE);
        try {
            isNet=connectivityManager.getActiveNetworkInfo()!=null;
        }catch (Exception e){
            e.printStackTrace();
        }
        return isNet;
    }

}

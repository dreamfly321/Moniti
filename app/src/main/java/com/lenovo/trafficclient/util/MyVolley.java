package com.lenovo.trafficclient.util;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/*********************************************************************************
 Created by Android Studio.
 *Author:          Jack Fu
 *Version:         1.0
 *Date;            17-6-5 下午3:03
 *Description:     
 **********************************************************************************/
public class MyVolley  {
    public static final String URL = "http://120.24.40.200:88/transportservice/action/";
    private static RequestQueue queue;
    public static void init(Context c){
        queue = Volley.newRequestQueue(c);
        queue.start();
    }


    public static void post(String url, JSONObject p,final Listener l){
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, url, p, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject jsonObject) {
                if(l != null) l.success(jsonObject);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                if(l != null) l.error(volleyError);
            }
        });

        queue.add(jsonObjectRequest);
    }


    public static void GetBusStationInfo(int id,Listener l){
        Map map = new HashMap();
        map.put("BusStationId",id);
        JSONObject jsonObject = new JSONObject(map);
        post(URL + "GetBusStationInfo.do",jsonObject,l);
    }

    public static void GetAllSense(String name,Listener l){
        Map map = new HashMap();
        map.put("UserName",name);
        JSONObject jsonObject = new JSONObject(map);
        post(URL + "GetAllSense.do",jsonObject,l);
    }

    public interface Listener{
        void success(JSONObject j);
        void error(VolleyError e);
    }

    public static void GetCarAccountBalance(int CarId,Listener listener){
        Map map=new HashMap();
        map.put("CarId",CarId);
        map.put("UserName","admin");
        JSONObject jsonObject=new JSONObject(map);
        post(URL+"GetCarAccountBalance.do",jsonObject,listener);
    }

    public static void SetCarAccountRecharge(int CarId,int Money,Listener listener){
        Map map=new HashMap();
        map.put("Money",Money);
        map.put("CarId",CarId);
        map.put("UserName","admin");
        JSONObject jsonObject=new JSONObject(map);
        post(URL+"SetCarAccountRecharge.do",jsonObject,listener);
    }

}

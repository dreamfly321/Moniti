package com.lenovo.trafficclient.bean;

import com.lenovo.trafficclient.fragment.BaseFragment;

/*********************************************************************************
 Created by Android Studio.
 *Author:          Jack Fu
 *Version:         1.0
 *Date;            17-6-5 下午3:22
 *Description:     
 **********************************************************************************/
public class TabBean {
    public String title;
    public BaseFragment fragment;

    public TabBean(String title, BaseFragment fragment) {
        this.title = title;
        this.fragment = fragment;
    }

}

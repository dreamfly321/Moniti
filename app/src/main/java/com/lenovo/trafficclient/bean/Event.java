package com.lenovo.trafficclient.bean;

/*********************************************************************************
 Created by Android Studio.
 *Author:          Jack Fu
 *Version:         1.0
 *Date;            17-6-5 下午3:07
 *Description:     
 **********************************************************************************/
public class Event {
    public enum Type{
        MIAO3,NO_NETWORK,MIAO,MIAO5,
    }
    private Object tag;
    private Type type;

    public Event(Object tag, Type type) {
        this.tag = tag;
        this.type = type;
    }

    public Object getTag() {
        return tag;
    }

    public void setTag(Object tag) {
        this.tag = tag;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }
}

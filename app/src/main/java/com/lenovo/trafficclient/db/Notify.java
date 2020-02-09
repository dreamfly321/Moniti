package com.lenovo.trafficclient.db;

import android.app.Notification;
import android.content.Context;
import android.support.v4.app.NotificationCompat;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;
import com.lenovo.trafficclient.R;

/*********************************************************************************
 Created by Android Studio.
 *Author:          Jack Fu
 *Version:         1.0
 *Date;            17-6-5 下午3:33
 *Description:     
 **********************************************************************************/
@DatabaseTable
public class Notify {
    @DatabaseField
    private String type;
    @DatabaseField
    private String message;
    @DatabaseField
    private long createAt;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public long getCreateAt() {
        return createAt;
    }

    public void setCreateAt(long createAt) {
        this.createAt = createAt;
    }

    public Notification buildNotification(String title, Context c){
        Notification notification = new NotificationCompat.Builder(c)
                .setContentText(message)
                .setContentTitle(title)
                .setSmallIcon(R.mipmap.ic_launcher).build();
        return notification;
    }
}

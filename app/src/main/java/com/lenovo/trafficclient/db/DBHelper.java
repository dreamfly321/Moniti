package com.lenovo.trafficclient.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.j256.ormlite.android.apptools.OpenHelperManager;
import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

import java.sql.SQLException;

/*********************************************************************************
 Created by Android Studio.
 *Author:          Jack Fu
 *Version:         1.0
 *Date;            17-6-5 下午3:32
 *Description:     
 **********************************************************************************/
public class DBHelper extends OrmLiteSqliteOpenHelper {

    private static DBHelper instance;

    public static DBHelper getInstance(Context c) {
        if(instance == null) instance = OpenHelperManager.getHelper(c,DBHelper.class);
        return instance;
    }

    public DBHelper(Context context) {
        super(context, "app", null, 1);
    }



    @Override
    public void onCreate(SQLiteDatabase database, ConnectionSource connectionSource) {
        try {
            TableUtils.createTable(connectionSource,Notify.class);
            TableUtils.createTable(connectionSource,Bill.class);
        }catch (Exception e){
        }

    }

    @Override
    public void onUpgrade(SQLiteDatabase database, ConnectionSource connectionSource, int oldVersion, int newVersion) {

    }

    private Dao<Notify,Integer> notifyIntegerDao;
    private Dao<Bill,Integer> billIntegerDao;
    private Dao<News,Integer> NewsIntegerDao;

    public Dao<Bill, Integer> getBillIntegerDao() {
        if(billIntegerDao == null){
            try {
                billIntegerDao = getDao(Bill.class);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return billIntegerDao;
    }

    public Dao<Notify, Integer> getNotifyIntegerDao() {
        if(notifyIntegerDao == null){
            try {
                notifyIntegerDao = getDao(Notify.class);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return notifyIntegerDao;
    }

    public Dao<News, Integer> getNewsIntegerDao() {
        if(NewsIntegerDao == null){
            try {
                NewsIntegerDao = getDao(News.class);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return NewsIntegerDao;
    }

}

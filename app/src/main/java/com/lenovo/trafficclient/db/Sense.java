package com.lenovo.trafficclient.db;

/*********************************************************************************
 Created by Android Studio.
 *Author:          Jack Fu
 *Version:         1.0
 *Date;            17-6-5 下午4:30
 *Description:     
 **********************************************************************************/
public class Sense {


    /**
     * CO2 : 731
     * Temperature : 386
     * Humidity : 257
     * Light : 974
     * PM2 : 473
     */

    private int CO2;
    private int Temperature;
    private int Humidity;
    private int Light;
    private int PM2;
    private long createAt;

    public int getCO2() {
        return CO2;
    }

    public void setCO2(int CO2) {
        this.CO2 = CO2;
    }

    public int getTemperature() {
        return Temperature;
    }

    public void setTemperature(int Temperature) {
        this.Temperature = Temperature;
    }

    public int getHumidity() {
        return Humidity;
    }

    public void setHumidity(int Humidity) {
        this.Humidity = Humidity;
    }

    public int getLight() {
        return Light;
    }

    public void setLight(int Light) {
        this.Light = Light;
    }

    public int getPM2() {
        return PM2;
    }

    public void setPM2(int PM2) {
        this.PM2 = PM2;
    }

    public long getCreateAt() {
        return createAt;
    }

    public void setCreateAt(long createAt) {
        this.createAt = createAt;
    }
}

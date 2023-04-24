package edu.hebut.dundun.entity;


import org.litepal.crud.LitePalSupport;

public class WaterIntake extends LitePalSupport {
    private int id;
    /**
     * 日期
     */
    private String date;
    /**
     * 饮水量
     */
    private float value;
    /**
     * 喝水次数
     */
    private int drinkTimes;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public float getValue() {
        return value;
    }

    public void setValue(float value) {
        this.value = value;
    }

    public int getDrinkTimes() {
        return drinkTimes;
    }

    public void setDrinkTimes(int drinkTimes) {
        this.drinkTimes = drinkTimes;
    }

    public String getSimpleDate() {
        String[] temp;
        temp = date.split("-");
        String simpleDate = temp[2] + "日";
        return simpleDate;
    }
}

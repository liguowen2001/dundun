package edu.hebut.dundun.entity;


import org.litepal.crud.LitePalSupport;

public class WaterIntake extends LitePalSupport {
    private int id;
    private String date;
    private Double value;

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

    public Double getValue() {
        return value;
    }

    public void setValue(Double value) {
        this.value = value;
    }
}

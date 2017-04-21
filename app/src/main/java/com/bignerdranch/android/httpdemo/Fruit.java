package com.bignerdranch.android.httpdemo;

/**
 * Created by acer-1 on 2017/4/18.
 */

public class Fruit {

    private String name;
    private String neirong;
    private String pinglun;

    public Fruit(String name,String neirong,String pinglun) {
        this.name = name;
        this.neirong=neirong;
        this.pinglun=pinglun;
    }

    public String getName() {
        return name;
    }

    public String getNeirong() {
        return neirong;
    }

    public String getPinglun() {
        return pinglun;
    }
}

package com.tenji.entity;

import java.io.Serializable;

/**
 * Created by tenji
 * Time: 2020/04/13 17:56
 */

public class Employee implements Serializable{

    private String usercd;
    private String username;
    private String sex;
    public Employee(){
    }

    public String getusercd() {
        return usercd;
    }

    public void setusercd(String strusercd) {
        this.usercd = strusercd;
    }

    public String getusername() {
        return username;
    }

    public void setusername(String strusername) {
        this.username = strusername;
    }

    public String getsex() {
        return sex;
    }

    public void setsex(String strsex) {
        this.sex = strsex;
    }

    @Override
    public String toString() {
        return "Employee{" +
                "usercd=" + usercd +
                ", username='" + username + '\'' +
                ", sex='" + sex + '\'' +
                '}';
    }
}

package com.tenji.entity;

/**
 * Created by tenji
 * Time: 2020/04/13 17:56
 */
public class MUser {

    private String usercd;
    private String password;
    private String level;

    public MUser(){
    }

    public String getusercd() {
        return usercd;
    }

    public void setusercd(String strusercd) {
        this.usercd = strusercd;
    }

    public String getpassword() {
        return password;
    }

    public void setpassword(String strpassword) {
        this.password = strpassword;
    }

    public String getlevel() {
        return level;
    }

    public void setlevel(String strlevel) {
        this.level = strlevel;
    }
}

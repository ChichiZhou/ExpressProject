package com.hezho.bean;

import org.springframework.stereotype.Component;

@Component
public class BootstrapTableCourier {
    private int id;
    private String courierName;
    private String password;
    private String courierPhone;
    private String regTime;
    private String loginTime;
    private String courierID;
    private int workLoad;

    public BootstrapTableCourier(){}

    public BootstrapTableCourier(int id, String courierName, String password, String courierPhone, String regTime, String loginTime, String courierID, int workLoad) {
        this.id = id;
        this.courierName = courierName;
        this.password = password;
        this.courierPhone = courierPhone;
        this.regTime = regTime;
        this.loginTime = loginTime;
        this.courierID = courierID;
        this.workLoad = workLoad;
    }

    public int getId(){return id;}

    public void setId(int id){this.id = id;}

    public String getCourierName(){return courierName;}

    public void setCourierName(String courierName){this.courierName = courierName;}

    public String getPassword(){return password;}

    public void setPassword(String password){this.password = password;}

    public String getCourierPhone(){return courierPhone;}

    public void setCourierPhone(String userPhone){this.courierPhone = userPhone;}

    public String getRegTime(){return regTime;}

    public void setRegTime(String regTime){this.regTime = regTime;}

    public String getLoginTime(){return loginTime;}

    public void setLoginTime(String loginTime){this.loginTime = loginTime;}

    public void setCourierID(String courierID){this.courierID = courierID;}

    public String getCourierID(){return this.courierID;}

    public void setWorkLoad(int workLoad){
        this.workLoad = workLoad;
    }

    public int getWorkLoad(){
        return this.workLoad;
    }
}

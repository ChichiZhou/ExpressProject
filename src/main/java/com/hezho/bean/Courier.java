package com.hezho.bean;

import org.springframework.stereotype.Component;

import java.sql.Timestamp;
import java.util.Objects;

@Component
public class Courier {
    private int id;
    private String courierName;
    private String password;
    private String courierPhone;
    private Timestamp regTime;
    private Timestamp loginTime;
    private String courierID;
    private int workLoad;

    public Courier(){}

    public Courier(int id, String courierName, String password, String courierPhone, Timestamp regTime, Timestamp loginTime, String courierID, int workLoad) {
        this.id = id;
        this.courierName = courierName;
        this.password = password;
        this.courierPhone = courierPhone;
        this.regTime = regTime;
        this.loginTime = loginTime;
        this.courierID = courierID;
        this.workLoad = workLoad;

    }

    public Courier(String courierName, String password, String courierPhone, String courierID, int workLoad){
        this.courierName = courierName;
        this.password = password;
        this.courierPhone = courierPhone;
        this.courierID = courierID;
        this.workLoad = workLoad;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, courierName, password, courierPhone, regTime, loginTime, courierID, workLoad);
    }

    @Override
    public String toString() {
        return "Customer{" +
                "id=" + id +
                ", courierName='" + courierName + '\'' +
                ", password='" + password + '\'' +
                ", courierPhone='" + courierPhone + '\'' +
                ", regTime='" + regTime + '\'' +
                ", loginTime='" + loginTime + '\'' +
                ", courierID='" + courierID + '\'' +
                ", workLoad='" + workLoad + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Courier courier = (Courier)o;
        return id == courier.id &&
                Objects.equals(courierName, courier.courierName) &&
                Objects.equals(password, courier.password) &&
                Objects.equals(courierPhone, courier.courierPhone) &&
                Objects.equals(regTime, courier.regTime) &&
                Objects.equals(loginTime, courier.loginTime) &&
                Objects.equals(courierID, courier.courierID) &&
                Objects.equals(workLoad, courier.workLoad);
    }

    public int getId(){return id;}

    public void setId(int id){this.id = id;}

    public String getCourierName(){return courierName;}

    public void setCourierName(String courierName){this.courierName = courierName;}

    public String getPassword(){return password;}

    public void setPassword(String password){this.password = password;}

    public String getCourierPhone(){return courierPhone;}

    public void setCourierPhone(String userPhone){this.courierPhone = userPhone;}

    public Timestamp getRegTime(){return regTime;}

    public void setRegTime(Timestamp regTime){this.regTime = regTime;}

    public Timestamp getLoginTime(){return loginTime;}

    public void setLoginTime(Timestamp loginTime){this.loginTime = loginTime;}

    public void setCourierID(String courierID){this.courierID = courierID;}

    public String getCourierID(){return this.courierID;}

    public void setWorkLoad(int workLoad){
        this.workLoad = workLoad;
    }

    public int getWorkLoad(){
        return this.workLoad;
    }
}

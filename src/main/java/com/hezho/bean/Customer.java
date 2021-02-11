package com.hezho.bean;

import org.springframework.stereotype.Component;

import java.sql.Timestamp;
import java.util.Objects;

@Component
public class Customer {
    private int id;
    private String userName;
    private String password;
    private String userPhone;
    private Timestamp regTime;
    private Timestamp loginTime;
    private String customerID;

    public Customer(){}

    public Customer(int id, String userName, String password, String userPhone, Timestamp regTime, Timestamp loginTime, String customerID) {
        this.id = id;
        this.userName = userName;
        this.password = password;
        this.userPhone = userPhone;
        this.regTime = regTime;
        this.loginTime = loginTime;
        this.customerID = customerID;

    }

    public Customer(String userName, String password, String userPhone, String customerID){
        this.userName = userName;
        this.password = password;
        this.userPhone = userPhone;
        this.customerID = customerID;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, userName, password, userPhone, regTime, loginTime, customerID);
    }

    @Override
    public String toString() {
        return "Customer{" +
                "id=" + id +
                ", userName='" + userName + '\'' +
                ", password='" + password + '\'' +
                ", userPhone='" + userPhone + '\'' +
                ", regTime='" + regTime + '\'' +
                ", loginTime='" + loginTime + '\'' +
                ", customerID='" + customerID + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Customer customer = (Customer)o;
        return id == customer.id &&
                Objects.equals(userName, customer.userName) &&
                Objects.equals(password, customer.password) &&
                Objects.equals(userPhone, customer.userPhone) &&
                Objects.equals(regTime, customer.regTime) &&
                Objects.equals(loginTime, customer.loginTime) &&
                Objects.equals(customerID, customer.customerID);
    }

    public int getId(){return id;}

    public void setId(int id){this.id = id;}

    public String getUserName(){return userName;}

    public void setUserName(String userName){this.userName = userName;}

    public String getPassword(){return password;}

    public void setPassword(String password){this.password = password;}

    public String getUserPhone(){return userPhone;}

    public void setUserPhone(String userPhone){this.userPhone = userPhone;}

    public Timestamp getRegTime(){return regTime;}

    public void setRegTime(Timestamp regTime){this.regTime = regTime;}

    public Timestamp getLoginTime(){return loginTime;}

    public void setLoginTime(Timestamp loginTime){this.loginTime = loginTime;}

    public void setCustomerID(String customerID){this.customerID = customerID;}

    public String getCustomerID(){return this.customerID;}


}

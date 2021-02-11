package com.hezho.bean;

import org.springframework.stereotype.Component;

@Component
public class BootStrapTableCustomer {
    private int id;
    private String userName;
    private String password;
    private String userPhone;
    private String regTime;
    private String loginTime;
    private String customerID;

    public BootStrapTableCustomer(){}

    public BootStrapTableCustomer(int id, String userName, String password, String userPhone, String regTime, String loginTime, String customerID) {
        this.id = id;
        this.userName = userName;
        this.password = password;
        this.userPhone = userPhone;
        this.regTime = regTime;
        this.loginTime = loginTime;
        this.customerID = customerID;
    }

    public int getId(){return id;}

    public void setId(int id){this.id = id;}

    public String getUserName(){return userName;}

    public void setUserName(String userName){this.userName = userName;}

    public String getPassword(){return password;}

    public void setPassword(String password){this.password = password;}

    public String getUserPhone(){return userPhone;}

    public void setUserPhone(String userPhone){this.userPhone = userPhone;}

    public String getRegTime(){return regTime;}

    public void setRegTime(String regTime){this.regTime = regTime;}

    public String getLoginTime(){return loginTime;}

    public void setLoginTime(String loginTime){this.loginTime = loginTime;}

    public String getCustomerID(){return customerID;}

    public void setCustomerID(){this.customerID = customerID;}

}

package com.hezho.bean;

import lombok.Data;

import java.sql.Date;

@Data
public class Admin {
    private int id;
    private String userName;
    private String passWord;
    private String loginIP;
    private Date loginTime;
    private Date createTime;

}

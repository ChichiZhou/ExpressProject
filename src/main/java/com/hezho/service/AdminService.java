package com.hezho.service;

import com.hezho.dao.BaseAdminDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.Date;

@Service
public class AdminService {
    @Autowired
    private BaseAdminDao adminDao;

    public void updateLoginTimeAndIP(String username, Date date, String ip) throws SQLException {
        adminDao.updateLoginTime(username, date, ip);
    }

    public  boolean login(String username, String password){
        return adminDao.login(username, password);
    }
}

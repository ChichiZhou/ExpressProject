package com.hezho.dao;

import com.hezho.bean.Admin;

import java.sql.SQLException;
import java.util.Date;
import java.util.Map;

public interface BaseAdminDao {
    void updateLoginTime(Map map) throws SQLException;

    Admin login(Map map);
}

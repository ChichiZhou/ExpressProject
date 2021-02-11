package com.hezho.dao;

import java.sql.SQLException;
import java.util.Date;

public interface BaseAdminDao {
    void updateLoginTime(String username, Date date, String ip) throws SQLException;

    boolean login(String username, String password);
}

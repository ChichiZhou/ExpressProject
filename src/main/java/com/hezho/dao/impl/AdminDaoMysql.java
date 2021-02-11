//package com.hezho.dao.impl;
//
//import com.hezho.dao.BaseAdminDao;
//import com.hezho.util.DruidUtil;
//import org.springframework.stereotype.Repository;
//import java.sql.Connection;
//import java.sql.PreparedStatement;
//import java.sql.ResultSet;
//import java.sql.SQLException;
//import java.util.Date;
//
//@Repository
//public class AdminDaoMysql implements BaseAdminDao {
//    private static final String SQL_UPDATE_LOGIN_TIME = "UPDATE EADMIN SET LOGINTIME=?,LOGINIP=? WHERE USERNAME=?";
//    private static final String SQL_LOGIN = "SELECT ID FROM EADMIN WHERE USERNAME=? AND PASSWORD=?";
//    @Override
//    public void updateLoginTime(String username, Date date, String ip){
//        Connection connection = DruidUtil.getConnection();
//        PreparedStatement state = null;
//        ResultSet result = null;
//        try {
//            state = connection.prepareStatement(SQL_UPDATE_LOGIN_TIME);
//            state.setDate(1, new java.sql.Date(date.getTime()));
//            state.setString(2, ip);
//            state.setString(3, username);
//            state.executeUpdate();
//        } catch (SQLException e) {
//            e.printStackTrace();
//        } finally {
//            try {
//                state.close();           // 为了解决 nullpointer 的错误
//                connection.close();
//            } catch (SQLException e) {
//                e.printStackTrace();
//            }
//        }
//    }
//
//    @Override
//    public boolean login(String username, String password) {
//        Connection connection = DruidUtil.getConnection();
//        PreparedStatement state = null;
//        ResultSet result = null;
//        try {
//            state = connection.prepareStatement(SQL_LOGIN);
//            state.setString(1, username);
//            state.setString(2, password);
//            result = state.executeQuery();
//            return result.next();
//        } catch (SQLException e) {
//            e.printStackTrace();
//        } finally {
//            DruidUtil.close(connection, state, result);
//        }
//
//        return false;
//    }
//}

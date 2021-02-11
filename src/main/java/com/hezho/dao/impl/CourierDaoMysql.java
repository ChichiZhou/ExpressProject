package com.hezho.dao.impl;

import com.hezho.bean.Courier;
import com.hezho.dao.BaseCourierDao;
import com.hezho.exception.DuplicateCodeException;
import com.hezho.util.DruidUtil;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class CourierDaoMysql implements BaseCourierDao {
    public static final String SQL_CONSOLE = "SELECT " +
            "(SELECT COUNT(ID)) AS data1_size, \n" +
            "(SELECT COUNT(TO_DAYS(REGTIME)=TO_DAYS(NOW()) OR NULL)) AS data1_day \n" +
            " FROM eCourier";

    public static final String SQL_FIND_ALL = "SELECT * FROM eCourier";

    public static final String SQL_FIND_LIMIT = "SELECT * FROM eCourier LIMIT ?,?";

    public static final String SQL_FIND_BY_COURIERID = "SELECT * FROM eCourier WHERE COURIERID=?";

    public static final String SQL_INSERT = "INSERT INTO eCourier (COURIERNAME,PASSWORD,COURIERPHONE,REGTIME,LOGINTIME,COURIERID,WORKLOAD) VALUES(?,?,?,NOW(),NULL,?,?)";

    public static final String SQL_UPDATE = "UPDATE eCourier SET COURIERNAME=?,PASSWORD=?,COURIERPHONE=?,WORKLOAD=? WHERE COURIERID=?";

    public static final String SQL_DELETE = "DELETE FROM eCourier WHERE COURIERID=?";

    @Override
    public Map<String, Integer> console() {
        Map<String, Integer> data = new HashMap<>();
        //1.    获取数据库的连接
        Connection conn = DruidUtil.getConnection();
        PreparedStatement state = null;
        ResultSet result = null;
        //2.    预编译SQL语句
        try {
            state = conn.prepareStatement(SQL_CONSOLE);
            //3.    填充参数(可选)
            //4.    执行SQL语句
            result = state.executeQuery();
            //5.    获取执行的结果
            if(result.next()){
                int data1_size = result.getInt("data1_size");
                int data1_day = result.getInt("data1_day");

                data.put("data1_size",data1_size);
                data.put("data1_day",data1_day);
                System.out.println("The data1_size is " + data1_size);
                System.out.println("The data1_day is " + data1_day);
            }
            //6.    资源的释放
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }finally {
            DruidUtil.close(conn,state,result);
        }
        return data;
    }

    @Override
    public List<Courier> findAll(boolean limit, int offset, int pageNumber) {
        ArrayList<Courier> data = new ArrayList<>();
        //1.    获取数据库的连接
        Connection conn = DruidUtil.getConnection();
        PreparedStatement state = null;
        ResultSet result = null;
        //2.    预编译SQL语句
        try {
            if(limit) {
                state = conn.prepareStatement(SQL_FIND_LIMIT);
                //3.    填充参数(可选)
                // offset 表示从哪个偏移量开始
                // pageNumber 表示扩展多少
                state.setInt(1,offset);
                state.setInt(2,pageNumber);
            }else {
                state = conn.prepareStatement(SQL_FIND_ALL);
            }

            //4.    执行SQL语句
            result = state.executeQuery();
            //5.    获取执行的结果
            while(result.next()){
                int id = result.getInt("id");
                String courierName = result.getString("courierName");
                String password = result.getString("password");
                String courierPhone = result.getString("courierPhone");
                String courierID = result.getString("courierID");
                Timestamp regTime = result.getTimestamp("regtime");
                Timestamp loginTime = result.getTimestamp("logintime");
                int workLoad = result.getInt("workLoad");
                System.out.println("workLoad is " + workLoad);
                Courier courier = new Courier(id, courierName, password, courierPhone, regTime, loginTime, courierID, workLoad);
                data.add(courier);
            }
            //6.    资源的释放
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }finally {
            DruidUtil.close(conn,state,result);
        }
        return data;
    }

    @Override
    public Courier findByCourierID(String userID) {
        //1.    获取数据库的连接
        Connection conn = DruidUtil.getConnection();
        PreparedStatement state = null;
        ResultSet result = null;
        //2.    预编译SQL语句
        try {
            state = conn.prepareStatement(SQL_FIND_BY_COURIERID);
            //3.    填充参数(可选)
            state.setString(1,userID);
            //4.    执行SQL语句
            result = state.executeQuery();
            //5.    获取执行的结果
            while(result.next()){
                int id = result.getInt("id");
                String courierName = result.getString("courierName");
                String password = result.getString("password");
                String courierPhone = result.getString("courierPhone");
                String courierID = result.getString("courierID");
                Timestamp regTime = result.getTimestamp("regtime");
                Timestamp loginTime = result.getTimestamp("logintime");
                int workLoad = result.getInt("workLoad");
                Courier courier = new Courier(id, courierName, password, courierPhone, regTime, loginTime, courierID, workLoad);
                return courier;
            }
            //6.    资源的释放
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }finally {
            DruidUtil.close(conn,state,result);
        }
        return null;
    }

    @Override
    public boolean insert(Courier courier) throws DuplicateCodeException {
        Connection conn = DruidUtil.getConnection();
        //2.    预编译SQL语句
        PreparedStatement state = null;
        try {
            state = conn.prepareStatement(SQL_INSERT);
            //3.    填充参数
            state.setString(1,courier.getCourierName());
            state.setString(2,courier.getPassword());
            state.setString(3,courier.getCourierPhone());
            state.setString(4,courier.getCourierID());
            state.setInt(5, courier.getWorkLoad());

            //4.    执行SQL语句,并获取执行结果
            return state.executeUpdate()>0?true:false;
        } catch (SQLException e1) {
            /*throwables.printStackTrace();*/
            if(e1.getMessage().endsWith("for key 'code'")){
                //是因为取件码重复,而出现了异常
                DuplicateCodeException e2 = new DuplicateCodeException(e1.getMessage());
                throw e2;
            }else{
                e1.printStackTrace();
            }
        }finally {
            //5.    释放资源
            DruidUtil.close(conn,state,null);
        }
        return false;
    }

    @Override
    public boolean update(String courierID, Courier courier) {
        Connection conn = DruidUtil.getConnection();
        PreparedStatement state = null;
        //2.    预编译SQL语句
        try {
            state = conn.prepareStatement(SQL_UPDATE);
            state.setString(1,courier.getCourierName());
            state.setString(2,courier.getPassword());
            state.setString(3,courier.getCourierPhone());
            state.setInt(4, courier.getWorkLoad());
            state.setString(5,courierID);
            return state.executeUpdate()>0?true:false;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }finally {
            DruidUtil.close(conn,state,null);
        }
        return false;
    }

    @Override
    public boolean delete(String courierID) {
        Connection conn = DruidUtil.getConnection();
        PreparedStatement state = null;
        //2.    预编译SQL语句
        try {
            state = conn.prepareStatement(SQL_DELETE);
            state.setString(1,courierID);
            return state.executeUpdate()>0?true:false;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }finally {
            DruidUtil.close(conn,state,null);
        }
        return false;
    }
}

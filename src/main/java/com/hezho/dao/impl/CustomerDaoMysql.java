//package com.hezho.dao.impl;
//
//import com.hezho.bean.Customer;
//import com.hezho.dao.BaseCustomerDao;
//import com.hezho.exception.DuplicateCodeException;
//import com.hezho.util.DruidUtil;
//import org.springframework.stereotype.Repository;
//
//import java.sql.*;
//import java.util.ArrayList;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//
//@Repository
//public class CustomerDaoMysql implements BaseCustomerDao {
//    // 查出来新增用户和总用户
//    public static final String SQL_CONSOLE = "SELECT " +
//            "(SELECT COUNT(ID)) AS data1_size, \n" +
//            "(SELECT COUNT(TO_DAYS(REGTIME)=TO_DAYS(NOW()) OR NULL)) AS data1_day \n" +
//            " FROM eCustomer";
//
//    //用于查询数据库中的所有快递信息
//    public static final String SQL_FIND_ALL = "SELECT * FROM eCustomer";
//
//    public static final String SQL_FIND_LIMIT = "SELECT * FROM eCustomer LIMIT ?,?";
//
//    public static final String SQL_FIND_BY_CUSTOMERID = "SELECT * FROM eCustomer WHERE CUSTOMERID=?";
//
//    public static final String SQL_INSERT = "INSERT INTO eCustomer (USERNAME,PASSWORD,USERPHONE,REGTIME,LOGINTIME, CUSTOMERID) VALUES(?,?,?,NOW(),NULL,?)";
//
//    public static final String SQL_UPDATE = "UPDATE eCustomer SET USERNAME=?,PASSWORD=?,USERPHONE=? WHERE CUSTOMERID=?";
//
//    public static final String SQL_DELETE = "DELETE FROM eCustomer WHERE CUSTOMERID=?";
//
//    public static final String SQL_FIND_BY_CUSTOMERPHONE = "SELECT * FROM eCustomer WHERE USERPHONE=?";
//
//    @Override
//    public Map<String, Integer> console() {
//        Map<String, Integer> data = new HashMap<>();
//        //1.    获取数据库的连接
//        Connection conn = DruidUtil.getConnection();
//        PreparedStatement state = null;
//        ResultSet result = null;
//        //2.    预编译SQL语句
//        try {
//            state = conn.prepareStatement(SQL_CONSOLE);
//            //3.    填充参数(可选)
//            //4.    执行SQL语句
//            result = state.executeQuery();
//            //5.    获取执行的结果
//            if(result.next()){
//                int data1_size = result.getInt("data1_size");
//                int data1_day = result.getInt("data1_day");
//
//                data.put("data1_size",data1_size);
//                data.put("data1_day",data1_day);
//            }
//            //6.    资源的释放
//        } catch (SQLException throwables) {
//            throwables.printStackTrace();
//        }finally {
//            DruidUtil.close(conn,state,result);
//        }
//        return data;
//    }
//
//    @Override
//    public List<Customer> findAll(boolean limit, int offset, int pageNumber) {
//        ArrayList<Customer> data = new ArrayList<>();
//        //1.    获取数据库的连接
//        Connection conn = DruidUtil.getConnection();
//        PreparedStatement state = null;
//        ResultSet result = null;
//        //2.    预编译SQL语句
//        try {
//            if(limit) {
//                state = conn.prepareStatement(SQL_FIND_LIMIT);
//                //3.    填充参数(可选)
//                // offset 表示从哪个偏移量开始
//                // pageNumber 表示扩展多少
//                state.setInt(1,offset);
//                state.setInt(2,pageNumber);
//            }else {
//                state = conn.prepareStatement(SQL_FIND_ALL);
//            }
//
//            //4.    执行SQL语句
//            result = state.executeQuery();
//            //5.    获取执行的结果
//            while(result.next()){
//                int id = result.getInt("id");
//                String username = result.getString("username");
//                String password = result.getString("password");
//                String userPhone = result.getString("userphone");
//                String customerID = result.getString("customerID");
//                Timestamp regTime = result.getTimestamp("regtime");
//                Timestamp loginTime = result.getTimestamp("logintime");
//                Customer e = new Customer(id, username, password, userPhone, regTime, loginTime, customerID);
//                data.add(e);
//            }
//            //6.    资源的释放
//        } catch (SQLException throwables) {
//            throwables.printStackTrace();
//        }finally {
//            DruidUtil.close(conn,state,result);
//        }
//        return data;
//    }
//
//    @Override
//    public Customer findByCustomerID(String userID) {
//        //1.    获取数据库的连接
//        Connection conn = DruidUtil.getConnection();
//        PreparedStatement state = null;
//        ResultSet result = null;
//        //2.    预编译SQL语句
//        try {
//            state = conn.prepareStatement(SQL_FIND_BY_CUSTOMERID);
//            //3.    填充参数(可选)
//            state.setString(1,userID);
//            //4.    执行SQL语句
//            result = state.executeQuery();
//            //5.    获取执行的结果
//            while(result.next()){
//                int id = result.getInt("id");
//                String username = result.getString("username");
//                String password = result.getString("password");
//                String userPhone = result.getString("userphone");
//                String customerID = result.getString("customerID");
//                Timestamp regTime = result.getTimestamp("regtime");
//                Timestamp loginTime = result.getTimestamp("logintime");
//                Customer e = new Customer(id, username, password, userPhone, regTime, loginTime, customerID);
//                return e;
//            }
//            //6.    资源的释放
//        } catch (SQLException throwables) {
//            throwables.printStackTrace();
//        }finally {
//            DruidUtil.close(conn,state,result);
//        }
//        return null;
//    }
//
//    public boolean insert(Customer customer) throws DuplicateCodeException {
//        //1.    连接的获取
//        Connection conn = DruidUtil.getConnection();
//        //2.    预编译SQL语句
//        PreparedStatement state = null;
//        try {
//            state = conn.prepareStatement(SQL_INSERT);
//            //3.    填充参数
//            state.setString(1,customer.getUserName());
//            state.setString(2,customer.getPassword());
//            state.setString(3,customer.getUserPhone());
//            state.setString(4,customer.getCustomerID());
//
//            //4.    执行SQL语句,并获取执行结果
//            return state.executeUpdate()>0?true:false;
//        } catch (SQLException e1) {
//            /*throwables.printStackTrace();*/
//            if(e1.getMessage().endsWith("for key 'code'")){
//                //是因为取件码重复,而出现了异常
//                DuplicateCodeException e2 = new DuplicateCodeException(e1.getMessage());
//                throw e2;
//            }else{
//                e1.printStackTrace();
//            }
//        }finally {
//            //5.    释放资源
//            DruidUtil.close(conn,state,null);
//        }
//        return false;
//    }
//
//    @Override
//    public boolean update(String customerID, Customer customer) {
//        //1.    连接的获取
//        Connection conn = DruidUtil.getConnection();
//        PreparedStatement state = null;
//        //2.    预编译SQL语句
//        try {
//            state = conn.prepareStatement(SQL_UPDATE);
//            state.setString(1,customer.getUserName());
//            state.setString(2,customer.getPassword());
//            state.setString(3,customer.getUserPhone());
//            state.setString(4,customerID);
//            return state.executeUpdate()>0?true:false;
//        } catch (SQLException throwables) {
//            throwables.printStackTrace();
//        }finally {
//            DruidUtil.close(conn,state,null);
//        }
//        return false;
//    }
//
//    @Override
//    public boolean delete(String customerID) {
//        //1.    连接的获取
//        Connection conn = DruidUtil.getConnection();
//        PreparedStatement state = null;
//        //2.    预编译SQL语句
//        try {
//            state = conn.prepareStatement(SQL_DELETE);
//            state.setString(1,customerID);
//            return state.executeUpdate()>0?true:false;
//        } catch (SQLException throwables) {
//            throwables.printStackTrace();
//        }finally {
//            DruidUtil.close(conn,state,null);
//        }
//        return false;
//    }
//
//    @Override
//    public Customer findByCustomerPhone(String customerPhone){
//        //1.    获取数据库的连接
//        Connection conn = DruidUtil.getConnection();
//        PreparedStatement state = null;
//        ResultSet result = null;
//        //2.    预编译SQL语句
//        try {
//            state = conn.prepareStatement(SQL_FIND_BY_CUSTOMERPHONE);
//            //3.    填充参数(可选)
//            state.setString(1,customerPhone);
//            //4.    执行SQL语句
//            result = state.executeQuery();
//            //5.    获取执行的结果
//            while(result.next()){
//                int id = result.getInt("id");
//                String username = result.getString("username");
//                String password = result.getString("password");
//                String userPhone = result.getString("userphone");
//                String customerID = result.getString("customerID");
//                Timestamp regTime = result.getTimestamp("regtime");
//                Timestamp loginTime = result.getTimestamp("logintime");
//                Customer e = new Customer(id, username, password, userPhone, regTime, loginTime, customerID);
//                return e;
//            }
//            //6.    资源的释放
//        } catch (SQLException throwables) {
//            throwables.printStackTrace();
//        }finally {
//            DruidUtil.close(conn,state,result);
//        }
//        return null;
//    }
//
//
//}

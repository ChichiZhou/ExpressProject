package com.hezho.service;

import com.hezho.bean.Customer;
import com.hezho.dao.BaseCourierDao;
import com.hezho.dao.BaseCustomerDao;
//import com.hezho.dao.impl.CustomerDaoMysql;
import com.hezho.exception.DuplicateCodeException;
import com.hezho.util.SqlSessionUtil;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.Reader;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class CustomerService {
//    @Autowired
//    private  BaseCustomerDao dao;
    Reader reader;

    {
        try {
            reader = Resources.getResourceAsReader("mybatis.xml");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // 得到sqlSessionFactoryBuilder
    SqlSessionFactoryBuilder builder = new SqlSessionFactoryBuilder();
    SqlSessionFactory build = builder.build(reader);
    // 得到 session
    SqlSession session = build.openSession();
    BaseCustomerDao baseCustomerDao = session.getMapper(BaseCustomerDao.class);

    public Map<String, Integer> console() {
        Map<String, Integer> resultMap = baseCustomerDao.console();
        SqlSessionUtil.closeSession();
        return resultMap;
    }

    public List<Customer> findAll(boolean limit, int offset, int pageNumber) {
        Map map = new HashMap();
        map.put("offset", offset);
        map.put("pageNumber", pageNumber);
        List<Customer> customerList = baseCustomerDao.findAll(map);
        SqlSessionUtil.closeSession();
        return customerList;
    }

    public Customer findByCustomerID(String userPhone) {
        Customer customer = baseCustomerDao.findByCustomerID(userPhone);
        SqlSessionUtil.closeSession();

        return customer;
    }

    public int insert(Customer customer){
        //1.    生成了取件码
        try {
            int flag = baseCustomerDao.insert(customer);
            session.commit();
            if(flag==1){
                //录入成功
                //SMSUtil.send(e.getUserPhone(),e.getCode());
            }
            return flag;
        } catch (DuplicateCodeException duplicateCodeException) {
            return insert(customer);      // 这里用了递归
        } finally {
            SqlSessionUtil.closeSession();
        }
    }

    // 这里只能修改手机号和密码，姓名和身份证无法修改
    public int update(String customerID, Customer customer) {
        Map map = new HashMap();
        map.put("customerID", customerID);
        map.put("username", customer.getUserName());
        map.put("password", customer.getPassword());
        map.put("userphone", customer.getUserPhone());

        int update = baseCustomerDao.update(map);
        session.commit();
        SqlSessionUtil.closeSession();
        return update;
    }

    public int delete(String customerID) {
        int flag = baseCustomerDao.delete(customerID);
        session.commit();
        SqlSessionUtil.closeSession();
        return flag;
    }

    public Customer findByCustomerPhone(String customerPhone){
        Customer customer = baseCustomerDao.findByCustomerPhone(customerPhone);
        SqlSessionUtil.closeSession();
        return customer;
    }
}

package com.hezho.service;

import com.hezho.bean.Customer;
import com.hezho.dao.BaseCustomerDao;
import com.hezho.dao.impl.CustomerDaoMysql;
import com.hezho.exception.DuplicateCodeException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class CustomerService {
    @Autowired
    private  BaseCustomerDao dao;

    public Map<String, Integer> console() {
        return dao.console();
    }

    public List<Customer> findAll(boolean limit, int offset, int pageNumber) {
        return dao.findAll(limit,offset,pageNumber);
    }

    public Customer findByCustomerID(String userPhone) {
        return dao.findByCustomerID(userPhone);
    }

    public boolean insert(Customer customer){
        //1.    生成了取件码
        try {
            boolean flag = dao.insert(customer);
            if(flag){
                //录入成功
                //SMSUtil.send(e.getUserPhone(),e.getCode());
            }
            return flag;
        } catch (DuplicateCodeException duplicateCodeException) {
            return insert(customer);      // 这里用了递归
        }
    }

    // 这里只能修改手机号和密码，姓名和身份证无法修改
    public boolean update(String customerID, Customer customer) {
        boolean update = dao.update(customerID, customer);
        return update;
    }

    public boolean delete(String customerID) {
        return dao.delete(customerID);
    }

    public Customer findByCustomerPhone(String customerPhone){
        return dao.findByCustomerPhone(customerPhone);
    }
}

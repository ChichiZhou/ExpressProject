package com.hezho.service;

import com.hezho.bean.Courier;
import com.hezho.dao.BaseCourierDao;
import com.hezho.dao.impl.CourierDaoMysql;
import com.hezho.exception.DuplicateCodeException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class CourierService {

    @Autowired
    private  BaseCourierDao dao;

    public  Map<String, Integer> console() {
        return dao.console();
    }

    public List<Courier> findAll(boolean limit, int offset, int pageNumber) {
        return dao.findAll(limit,offset,pageNumber);
    }

    public Courier findByCourierID(String courierID) {
        return dao.findByCourierID(courierID);
    }

    public boolean insert(Courier courier){
        //1.    生成了取件码
        try {
            boolean flag = dao.insert(courier);
            if(flag){
                //录入成功
                //SMSUtil.send(e.getUserPhone(),e.getCode());
            }
            return flag;
        } catch (DuplicateCodeException duplicateCodeException) {
            return insert(courier);      // 这里用了递归
        }
    }

    // 这里只能修改手机号和密码，姓名和身份证无法修改
    public boolean update(String customerID, Courier courier) {
        boolean update = dao.update(customerID, courier);
        return update;
    }

    public boolean delete(String courierID) {
        return dao.delete(courierID);
    }
}

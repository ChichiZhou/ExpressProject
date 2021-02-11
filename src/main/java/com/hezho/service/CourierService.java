package com.hezho.service;

import com.hezho.bean.Courier;
import com.hezho.dao.BaseAdminDao;
import com.hezho.dao.BaseCourierDao;
//import com.hezho.dao.impl.CourierDaoMysql;
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
public class CourierService {

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
    BaseCourierDao baseCourierDao = session.getMapper(BaseCourierDao.class);

    public  Map<String, Integer> console() {
        Map<String, Integer> resultMap = baseCourierDao.console();
        SqlSessionUtil.closeSession();
        return resultMap;
    }

    public List<Courier> findAll(boolean limit, int offset, int pageNumber) {
        Map map = new HashMap();
        map.put("offset", offset);
        map.put("pageNumber", pageNumber);
        List<Courier> courierList = baseCourierDao.findAll(map);
        SqlSessionUtil.closeSession();
        return courierList;
    }

    public Courier findByCourierID(String courierID) {
        Courier courier = baseCourierDao.findByCourierID(courierID);
        SqlSessionUtil.closeSession();
        return courier;
    }

    public int insert(Courier courier){
        //1.    生成了取件码
        try {
            int flag = baseCourierDao.insert(courier);
            session.commit();
            if(flag==1){
                //录入成功
                //SMSUtil.send(e.getUserPhone(),e.getCode());
            }
            return flag;
        } catch (DuplicateCodeException duplicateCodeException) {
            return insert(courier);      // 这里用了递归
        } finally {
            SqlSessionUtil.closeSession();
        }

    }

    // 这里只能修改手机号和密码，姓名和身份证无法修改
    public int update(String customerID, Courier courier) {
        Map map = new HashMap();
        map.put("customerID", customerID);
        map.put("courierName", courier.getCourierName());
        map.put("password", courier.getPassword());
        map.put("courierPhone", courier.getCourierPhone());
        map.put("workLoad", courier.getWorkLoad());

        int update = baseCourierDao.update(map);
        session.commit();
        SqlSessionUtil.closeSession();
        return update;
    }

    public int delete(String courierID) {
        int flag = baseCourierDao.delete(courierID);
        session.commit();
        SqlSessionUtil.closeSession();
        return flag;
    }
}

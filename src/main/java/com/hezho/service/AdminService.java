package com.hezho.service;

import com.hezho.bean.Admin;
import com.hezho.dao.BaseAdminDao;
import com.hezho.util.SqlSessionUtil;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.Reader;
import java.sql.SQLException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
public class AdminService {

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
    BaseAdminDao baseAdminDao = session.getMapper(BaseAdminDao.class);

    public void updateLoginTimeAndIP(String username, Date date, String ip) throws SQLException {
        Map map = new HashMap();
        map.put("userName", username);
        map.put("loginTime", date);
        map.put("loginIP", ip);
        baseAdminDao.updateLoginTime(map);
    }

    public  boolean login(String username, String password){
        Map map = new HashMap();
        map.put("userName", username);
        map.put("passWord", password);

        Admin admin;
        try {
            admin = baseAdminDao.login(map);
            return true;
        } catch(NullPointerException nullPointerException){
            return false;
        } finally {
            SqlSessionUtil.closeSession();
        }
    }
}

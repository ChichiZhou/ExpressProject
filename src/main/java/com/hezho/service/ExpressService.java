package com.hezho.service;

import com.hezho.bean.Express;
import com.hezho.dao.BaseCourierDao;
import com.hezho.dao.BaseExpressDao;
//import com.hezho.dao.impl.ExpressDaoMysql;
import com.hezho.exception.DuplicateCodeException;
import com.hezho.util.RandomUtil;
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
public class ExpressService{
//    @Autowired
//    private  BaseExpressDao dao;
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
    BaseExpressDao baseExpressDao = session.getMapper(BaseExpressDao.class);

    /**
     * 用于查询数据库中的全部快递（总数+新增），待取件快递（总数+新增）
     *
     * @return [{size:总数,day:新增},{size:总数,day:新增}]
     */
    public List<Map<String, Integer>> console() {
        List<Map<String, Integer>> resultMap = baseExpressDao.console();
        System.out.println(resultMap);
        SqlSessionUtil.closeSession();
        return resultMap;
    }

    /**
     * 用于查询所有快递
     *
     * @param limit      是否分页的标记，true表示分页。false表示查询所有快递
     * @param offset     SQL语句的起始索引
     * @param pageNumber 页查询的数量
     * @return 快递的集合
     */
    public List<Express> findAll(boolean limit, int offset, int pageNumber) {
        Map map = new HashMap();
        map.put("offset", offset);
        map.put("pageNumber", pageNumber);
        List<Express> expresses = baseExpressDao.findAll(map);
        for (Express item:expresses){
            System.out.println(item);
        }
        SqlSessionUtil.closeSession();
        return expresses;

    }

    /**
     * 根据快递单号，查询快递信息
     *
     * @param number 单号
     * @return 查询的快递信息，单号不存在时返回null
     */
    public Express findByNumber(String number) {
        Express express = baseExpressDao.findByNumber(number);
        SqlSessionUtil.closeSession();
        return express;
    }

    /**
     * 根据快递取件码，查询快递信息
     *
     * @param code 取件码
     * @return 查询的快递信息，取件码不存在时返回null
     */
    public Express findByCode(String code) {
        Express express = baseExpressDao.findByNumber(code);
        SqlSessionUtil.closeSession();
        return express;
    }

    /**
     * 根据用户手机号码，查询他所有的快递信息
     *
     * @param userPhone 手机号码
     * @return 查询的快递信息列表
     */
    public List<Express> findByUserPhone(String userPhone) {
        List<Express> expresses = baseExpressDao.findByUserPhone(userPhone);
        SqlSessionUtil.closeSession();
        return expresses;
    }

    /**
     * 根据录入人手机号码，查询录入的所有记录
     *
     * @param sysPhone 手机号码
     * @return 查询的快递信息列表
     */
    public List<Express> findBySysPhone(String sysPhone) {
        List<Express> expresses = baseExpressDao.findByUserPhone(sysPhone);
        SqlSessionUtil.closeSession();
        return expresses;
    }

    /**
     * 快递的录入
     *
     * @param e 要录入的快递对象
     * @return 录入的结果，true表示成功，false表示失败
     */
    public int insert(Express e){
        //1.    生成了取件码
        e.setCode(RandomUtil.getCode()+"");
        try {
            int flag = baseExpressDao.insert(e);
            session.commit();
            if(flag == 1){
                //录入成功
                //SMSUtil.send(e.getUserPhone(),e.getCode());
            }
            return flag;
        } catch (DuplicateCodeException duplicateCodeException) {
            return insert(e);      // 这里用了递归
        } finally {
            SqlSessionUtil.closeSession();
        }
    }

    /**
     * 快递的修改
     *
     * @param id         要修改的快递id
     * @param newExpress 新的快递对象（number，company,username,userPhone）
     * @return 修改的结果，true表示成功，false表示失败
     *
     *
     *  逻辑 BUG ,
     */
    public int update(int id, Express newExpress) {
        Map map = new HashMap();
        map.put("id", id);
        map.put("number", newExpress.getNumber());
        map.put("username", newExpress.getUsername());
        map.put("company", newExpress.getCompany());
        map.put("code", newExpress.getCode());
        map.put("sysphone", newExpress.getSysPhone());

        int update = baseExpressDao.update(map);
        session.commit();
        SqlSessionUtil.closeSession();
        return update;

    }

    /**
     * 更改快递的状态为1，表示取件完成
     *
     * @param code 要修改的快递取件码
     * @return 修改的结果，true表示成功，false表示失败
     */
    public int updateStatus(String code) {
        int updateStatus = baseExpressDao.updateStatus(code);
        session.commit();
        SqlSessionUtil.closeSession();
        return updateStatus;
    }

    /**
     * 根据id，删除单个快递信息
     *
     * @param id 要删除的快递id
     * @return 删除的结果，true表示成功，false表示失败
     */
    public int delete(int id) {
        int flag = baseExpressDao.delete(id);
        session.commit();
        SqlSessionUtil.closeSession();
        return flag;
    }

    /**
     * 根据用户手机号码，查询他所有的快递信息
     *
     * @param userPhone 手机号码
     * @param status 状态码
     * @return 查询的快递信息列表
     */
    public List<Express> findByUserPhoneAndStatus(String userPhone, int status) {
        Map map = new HashMap();
        map.put("userphone", userPhone);
        map.put("status", status);
        List<Express> expressList = baseExpressDao.findByUserPhoneAndStatus(map);
        SqlSessionUtil.closeSession();
        return expressList;
    }
}

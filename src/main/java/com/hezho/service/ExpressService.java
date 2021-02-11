package com.hezho.service;

import com.hezho.bean.Express;
import com.hezho.dao.BaseExpressDao;
import com.hezho.dao.impl.ExpressDaoMysql;
import com.hezho.exception.DuplicateCodeException;
import com.hezho.util.RandomUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class ExpressService{
    @Autowired
    private  BaseExpressDao dao;

    /**
     * 用于查询数据库中的全部快递（总数+新增），待取件快递（总数+新增）
     *
     * @return [{size:总数,day:新增},{size:总数,day:新增}]
     */
    public List<Map<String, Integer>> console() {
        return dao.console();
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
        return dao.findAll(limit,offset,pageNumber);
    }

    /**
     * 根据快递单号，查询快递信息
     *
     * @param number 单号
     * @return 查询的快递信息，单号不存在时返回null
     */
    public Express findByNumber(String number) {
        return dao.findByNumber(number);
    }

    /**
     * 根据快递取件码，查询快递信息
     *
     * @param code 取件码
     * @return 查询的快递信息，取件码不存在时返回null
     */
    public Express findByCode(String code) {
        return dao.findByCode(code);
    }

    /**
     * 根据用户手机号码，查询他所有的快递信息
     *
     * @param userPhone 手机号码
     * @return 查询的快递信息列表
     */
    public List<Express> findByUserPhone(String userPhone) {
        return dao.findByUserPhone(userPhone);
    }

    /**
     * 根据录入人手机号码，查询录入的所有记录
     *
     * @param sysPhone 手机号码
     * @return 查询的快递信息列表
     */
    public List<Express> findBySysPhone(String sysPhone) {
        return dao.findBySysPhone(sysPhone);
    }

    /**
     * 快递的录入
     *
     * @param e 要录入的快递对象
     * @return 录入的结果，true表示成功，false表示失败
     */
    public boolean insert(Express e){
        //1.    生成了取件码
        e.setCode(RandomUtil.getCode()+"");
        try {
            boolean flag = dao.insert(e);
            if(flag){
                //录入成功
                //SMSUtil.send(e.getUserPhone(),e.getCode());
            }
            return flag;
        } catch (DuplicateCodeException duplicateCodeException) {
            return insert(e);      // 这里用了递归
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
    public boolean update(int id, Express newExpress) {
        if(newExpress.getUserPhone()!=null){
            dao.delete(id);
            return insert(newExpress);
        }else{
            boolean update = dao.update(id, newExpress);
            Express e = dao.findByNumber(newExpress.getNumber());
            if(newExpress.getStatus() == 1){
                updateStatus(e.getCode());
            }
            return update;
        }
    }

    /**
     * 更改快递的状态为1，表示取件完成
     *
     * @param code 要修改的快递取件码
     * @return 修改的结果，true表示成功，false表示失败
     */
    public boolean updateStatus(String code) {
        return dao.updateStatus(code);
    }

    /**
     * 根据id，删除单个快递信息
     *
     * @param id 要删除的快递id
     * @return 删除的结果，true表示成功，false表示失败
     */
    public boolean delete(int id) {
        return dao.delete(id);
    }

    /**
     * 根据用户手机号码，查询他所有的快递信息
     *
     * @param userPhone 手机号码
     * @param status 状态码
     * @return 查询的快递信息列表
     */
    public List<Express> findByUserPhoneAndStatus(String userPhone, int status) {
        return dao.findByUserPhoneAndStatus(userPhone,status);
    }
}

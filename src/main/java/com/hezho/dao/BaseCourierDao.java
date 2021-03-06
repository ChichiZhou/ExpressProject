package com.hezho.dao;

import com.hezho.bean.Courier;
import com.hezho.exception.DuplicateCodeException;

import java.util.List;
import java.util.Map;

public interface BaseCourierDao {
    /**
     * 用于查询数据库中的全部顾客
     * @return
     */
    Map<String, Integer> console();

    /**
     * 用于查询所有快递
     * @param limit 是否分页的标记，true表示分页。false表示查询所有快递
     * @param offset SQL语句的起始索引
     * @param pageNumber 页查询的数量
     * @return 快递的集合
     */
    List<Courier> findAll(boolean limit, int offset, int pageNumber);

    /**
     * 根据用户手机号码，查询他所有的快递信息
     * @param courierID 手机号码
     * @return 查询的快递信息列表
     */
    Courier findByCourierID(String courierID);

    /**
     * 快递的录入
     * @param customer 要录入的快递对象
     * @return 录入的结果，true表示成功，false表示失败
     */
    boolean insert(Courier customer) throws DuplicateCodeException;

    /**
     * 快递的修改
     * @param customerID 要修改的快递id
     * @param newExpress 新的快递对象（number，company,username,userPhone）
     * @return 修改的结果，true表示成功，false表示失败
     */
    boolean update(String customerID, Courier newExpress);

    /**
     * 根据id，删除单个快递信息
     * @param customerID 要删除的快递id
     * @return 删除的结果，true表示成功，false表示失败
     */
    boolean delete(String customerID);
}

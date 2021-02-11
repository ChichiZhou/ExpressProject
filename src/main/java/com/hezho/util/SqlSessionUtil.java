package com.hezho.util;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import java.io.IOException;
import java.io.Reader;

public class SqlSessionUtil {
    private static ThreadLocal<SqlSession> threadLocal = new ThreadLocal<SqlSession>();
    private static SqlSessionFactory sqlSessionFactory;

    static {
        try{
            Reader reader = Resources.getResourceAsReader("mybatis.xml");
            sqlSessionFactory = new SqlSessionFactoryBuilder().build(reader);
        } catch (IOException e){
            e.printStackTrace();
        }
    }

    // 这里实际上是一个单例模式
    public static SqlSession getSession(){
        SqlSession session = threadLocal.get();
        if (session == null) {
            session = sqlSessionFactory.openSession();
            threadLocal.set(session);
        }
        return session;
    }

    public static void closeSession(){
        SqlSession session = threadLocal.get();
        if (session != null){
            session.close();
            threadLocal.remove();
        }
    }

}

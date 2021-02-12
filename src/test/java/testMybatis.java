import com.hezho.bean.Admin;
import com.hezho.bean.Express;
import com.hezho.dao.BaseAdminDao;
import com.hezho.dao.BaseCourierDao;
import com.hezho.dao.BaseExpressDao;
import com.hezho.util.SqlSessionUtil;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.Test;
import org.springframework.web.method.support.HandlerMethodReturnValueHandler;

import java.io.IOException;
import java.io.Reader;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class testMybatis {
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
    // 操作 sql，传入的参数是sql语句的完整路径，返回值和接口中方法的返回值相同
    @Test
    public void testAdminLogin(){
        BaseAdminDao baseAdminDao = session.getMapper(BaseAdminDao.class);
        Map map = new HashMap();
        map.put("userName", "admin");
        map.put("passWord", "12");
        Admin admin;
        try {
           admin = baseAdminDao.login(map);
            System.out.println(admin.getUserName());
        } catch(NullPointerException nullPointerException){
            System.out.println("账号密码错误");
        } finally {
            SqlSessionUtil.closeSession();
        }
    }

    @Test
    public void testCourier(){
        BaseCourierDao baseCourierDao = session.getMapper(BaseCourierDao.class);
        Map<String, Integer> map2 = new HashMap();
        map2 = baseCourierDao.console();
        for (Map.Entry entry : map2.entrySet()){
            System.out.println(entry.getValue());
        }
        SqlSessionUtil.closeSession();
    }

    @Test
    public void testExpressConsole(){
        BaseExpressDao baseExpressDao = session.getMapper(BaseExpressDao.class);
        List<Map<String, Integer>> resultMap = baseExpressDao.console();
        System.out.println(resultMap);

        SqlSessionUtil.closeSession();
    }

    @Test
    public void testFindAll(){
        BaseExpressDao baseExpressDao = session.getMapper(BaseExpressDao.class);
        Map map = new HashMap();
        map.put("offset", 0);
        map.put("pageNumber", 5);
        List<Express> expresses = baseExpressDao.findAll(map);
        for (Express item:expresses){
            System.out.println(item);
        }
        SqlSessionUtil.closeSession();
    }
}

package com.hezho.util;

import com.hezho.bean.User;
import com.hezho.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpSession;

// 获取当前用户信息

@Component
public class UserUtil {
    @Autowired
    CustomerService customerService;

    public static String getUserName(HttpSession session){
        return (String) session.getAttribute("adminUserName");
    }
    public static void setUserPhone(HttpSession session, String userPhone){
        session.setAttribute("userPhone", userPhone);
    }
    public static String getUserPhone(HttpSession session){
        return (String)session.getAttribute("userPhone");
    }
    public static String getLoginSms(HttpSession session, String userPhone){
        return (String) session.getAttribute(userPhone);
    }
    public static void setLoginSms(HttpSession session, String userPhone, String code){
        session.setAttribute(userPhone,code);
    }
    public static void setWxUser(HttpSession session, User user){
        session.setAttribute("wxUser",user);
    }
    public static User getWxUser(HttpSession session){
        return (User) session.getAttribute("wxUser");
    }
    public static String getAdmin(HttpSession session){
        return (String) session.getAttribute("admin");
    }
    public static void setAdmin(HttpSession session, String admin){
        session.setAttribute("admin", "admin");
    }

    // 验证手机号是用户还是快递员

    public boolean isUser(String userphone){
        // TODO 查找 Express 列表还是 Customer 列表
        if (customerService.findByCustomerPhone(userphone) != null){
            return true;
        }
        return false;
    }
}

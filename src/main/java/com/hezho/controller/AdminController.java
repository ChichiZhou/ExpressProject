package com.hezho.controller;

import com.hezho.bean.Message;
import com.hezho.service.AdminService;
import com.hezho.util.JSONUtil;
import com.hezho.util.UserUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.SQLException;
import java.util.Date;

@Controller
public class AdminController {
    @Autowired
    private AdminService adminService;

    @Autowired
    UserUtil userUtil;

    @RequestMapping("/admin/login.do")
    public @ResponseBody Message login(HttpServletRequest req) throws SQLException {
        String username = req.getParameter("username");
        String password = req.getParameter("password");

        boolean result = adminService.login(username, password);
        Message message = null;
        if (result){
            message = new Message(0, "登录成功");
            Date date = new Date();
            String ip = req.getRemoteAddr();
            adminService.updateLoginTimeAndIP(username, date, ip);
        } else {
            message = new Message(-1, "登录失败");
        }

        userUtil.setAdmin(req.getSession(), "admin");
//        String json = JSONUtil.toJSON(message);
//        System.out.println(json);

        return message;
    }

    @RequestMapping("/logout.do")
    @ResponseBody         // 这个很重要！！！！！不然 页面无法接受到返回值
    public String logout(HttpServletRequest request){
        request.getSession().invalidate();
        System.out.println("logout被调用");
        Message message = null;
        message = new Message(0, "登出");
        String json = JSONUtil.toJSON(message);

        return json;

    }


}

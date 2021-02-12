package com.hezho.controller;

import com.hezho.bean.BootstrapTableCourier;
import com.hezho.bean.Courier;
import com.hezho.bean.Message;
import com.hezho.bean.ResultData;
import com.hezho.service.CourierService;
import com.hezho.util.DateFormatUtil;
import com.hezho.util.JSONUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Controller
public class CourierController {
//    CourierService courierService = (CourierService) ToolSpring.getBean("courierService");
    @Autowired
    CourierService courierService;

    @RequestMapping("/courier/console.do")
    @ResponseBody
    public String console(HttpServletRequest request, HttpServletResponse response){
        Map<String, Integer> data = courierService.console();
        Message msg = new Message();
        if(data.size() == 0){
            msg.setStatus(-1);
        }else {
            msg.setStatus(0);
        }
        msg.setData(data);
        String json = JSONUtil.toJSON(msg);
        System.out.println(json);
        return json;
    }

    @RequestMapping("/courier/list.do")
    @ResponseBody
    public String list(HttpServletRequest request, HttpServletResponse response){
        //1.    获取查询数据的起始索引值
        int offset = Integer.parseInt(request.getParameter("offset"));
        //2.    获取当前页要查询的数据量
        int pageNumber = Integer.parseInt(request.getParameter("pageNumber"));
        //3.    进行查询
        System.out.println("jjjjjjjjjjj");
        System.out.println("The offset is " + offset);
        System.out.println("The pageNumber is " + pageNumber);

        List<Courier> list = courierService.findAll(true, offset, pageNumber);
        List<BootstrapTableCourier> list2 = new ArrayList<>();
        String loginTime = null;
        for(Courier e:list){
            String courierName = e.getCourierName();
            String regTime = DateFormatUtil.format(e.getRegTime());
            if (e.getLoginTime() != null){
                loginTime = DateFormatUtil.format(e.getLoginTime());
            }
            String courierPhone = e.getCourierPhone();
            String password = e.getPassword();
            String customerID = e.getCourierID();
            int workLoad = e.getWorkLoad();
            BootstrapTableCourier courier2 = new BootstrapTableCourier(e.getId(), courierName, password, courierPhone, regTime, loginTime, customerID, workLoad);
            list2.add(courier2);
        }
        Map<String, Integer> console = courierService.console();
        Integer total = console.get("data1_size");
        //4.    将集合封装为 bootstrap-table识别的格式
        ResultData<BootstrapTableCourier> data = new ResultData<>();
        data.setRows(list2);
        data.setTotal(total);
        String json = JSONUtil.toJSON(data);
        System.out.println("The json is " + json);
        return json;
    }

    @RequestMapping("/courier/find.do")
    @ResponseBody
    public String find(HttpServletRequest request, HttpServletResponse response){
        String courierID = request.getParameter("courierID");
        Courier customer = courierService.findByCourierID(courierID);
        Message msg = new Message();
        if(customer == null){
            msg.setStatus(-1);
            msg.setResult("单号不存在");
        }else{
            msg.setStatus(0);
            msg.setResult("查询成功");
            msg.setData(customer);
        }
        String json = JSONUtil.toJSON(msg);
        System.out.println(json);
        return json;
    }

    @RequestMapping("/courier/insert.do")
    @ResponseBody
    public String insert(HttpServletRequest request, HttpServletResponse response){
        String courierName = request.getParameter("courierName");
        String password = request.getParameter("password");
        String courierPhone = request.getParameter("courierPhone");
        String courierID = request.getParameter("courierID");
        int workLoad = Integer.parseInt(request.getParameter("workLoad"));

        System.out.println("Run Customer Insert");
        Courier courier = new Courier(courierName,password,courierPhone, courierID, workLoad);
        int flag = courierService.insert(courier);
        Message msg = new Message();
        if(flag != 0){
            //录入成功
            msg.setStatus(0);
            msg.setResult("用户录入成功!");
        }else{
            //录入失败
            msg.setStatus(-1);
            msg.setResult("用户录入失败!");
        }
        String json = JSONUtil.toJSON(msg);
        return json;
    }


    @RequestMapping("/courier/update.do")
    @ResponseBody
    public String update(HttpServletRequest request, HttpServletResponse response){
        String courierID = request.getParameter("courierID");
        String password = request.getParameter("password");
        String courierPhone = request.getParameter("courierPhone");
        String courierName = request.getParameter("courierName");
        int workLoad = Integer.parseInt(request.getParameter("workLoad"));

        System.out.println("CustomerID is " + courierID);
        System.out.println("password is " + password);
        System.out.println("userName is " + courierName);
        System.out.println("userPhone is " + courierPhone);
        //int status = Integer.parseInt(request.getParameter("status"));
        Courier courier = new Courier();
        courier.setPassword(password);
        courier.setCourierPhone(courierPhone);
        courier.setCourierName(courierName);
        courier.setCourierID(courierID);
        courier.setWorkLoad(workLoad);

        int flag = courierService.update(courierID, courier);
        Message msg = new Message();
        if(flag != 0){
            msg.setStatus(0);
            msg.setResult("修改成功");
        }else{
            msg.setStatus(-1);
            msg.setResult("修改失败");
        }
        String json = JSONUtil.toJSON(msg);

        return json;
    }

    @RequestMapping("/courier/delete.do")
    @ResponseBody
    public String delete(HttpServletRequest request, HttpServletResponse response){
        String courierID = request.getParameter("courierID");
        int flag = courierService.delete(courierID);
        Message msg = new Message();
        if(flag != 0){
            msg.setStatus(0);
            msg.setResult("删除成功");
        }else{
            msg.setStatus(-1);
            msg.setResult("删除失败");
        }
        String json = JSONUtil.toJSON(msg);
        return json;
    }
}

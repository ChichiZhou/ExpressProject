package com.hezho.controller;

import com.hezho.bean.BootStrapTableCustomer;
import com.hezho.bean.Customer;
import com.hezho.bean.Message;
import com.hezho.bean.ResultData;
import com.hezho.service.CustomerService;
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
public class CustomerController {
//    CustomerService customerService = (CustomerService) ToolSpring.getBean("adminService");
    @Autowired
    CustomerService customerService;

    @RequestMapping("/user/console.do")
    @ResponseBody
    public String console(){
        Map<String, Integer> data = customerService.console();
        System.out.println("The key of Map is " + data.keySet());
        System.out.println("The value of Map is " + data.values());
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

    @RequestMapping("/user/list.do")
    @ResponseBody
    public String list(HttpServletRequest request){
        //1.    获取查询数据的起始索引值
        int offset = Integer.parseInt(request.getParameter("offset"));
        //2.    获取当前页要查询的数据量
        int pageNumber = Integer.parseInt(request.getParameter("pageNumber"));
        //3.    进行查询
        System.out.println("jjjjjjjjjjj");
        System.out.println("The offset is " + offset);
        System.out.println("The pageNumber is " + pageNumber);

        List<Customer> list = customerService.findAll(true, offset, pageNumber);
        List<BootStrapTableCustomer> list2 = new ArrayList<>();
        String loginTime = null;
        for(Customer e:list){
            String username = e.getUserName();
            String regTime = DateFormatUtil.format(e.getRegTime());
            if (e.getLoginTime() != null){
                loginTime = DateFormatUtil.format(e.getLoginTime());
            }
            String userPhone = e.getUserPhone();
            String password = e.getPassword();
            String customerID = e.getCustomerID();
            BootStrapTableCustomer e2 = new BootStrapTableCustomer(e.getId(), username, password, userPhone, regTime, loginTime, customerID);
            list2.add(e2);
        }
        Map<String, Integer> console = customerService.console();
        Integer total = console.get("data1_size");
        //4.    将集合封装为 bootstrap-table识别的格式
        ResultData<BootStrapTableCustomer> data = new ResultData<>();
        data.setRows(list2);
        data.setTotal(total);
        String json = JSONUtil.toJSON(data);
        System.out.println("The json is " + json);
        return json;
    }

    @RequestMapping("/user/find.do")
    @ResponseBody
    public String find(HttpServletRequest request){
        String number = request.getParameter("customerID");
        Customer customer = customerService.findByCustomerID(number);
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

    @RequestMapping("/user/insert.do")
    @ResponseBody
    public String insert(HttpServletRequest request){
        String userName = request.getParameter("userName");
        String password = request.getParameter("password");
        String userPhone = request.getParameter("userPhone");
        String customerID = request.getParameter("customerID");

        System.out.println("Run Customer Insert");
        Customer e = new Customer(userName,password,userPhone, customerID);
        boolean flag = customerService.insert(e);
        Message msg = new Message();
        if(flag){
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


    @RequestMapping("/user/update.do")
    @ResponseBody
    public String update(HttpServletRequest request){
        String customerID = request.getParameter("customerID");
        String password = request.getParameter("password");
        String userPhone = request.getParameter("userPhone");
        String userName = request.getParameter("userName");

        System.out.println("CustomerID is " + customerID);
        System.out.println("password is " + password);
        System.out.println("userName is " + userName);
        System.out.println("userPhone is " + userPhone);
        //int status = Integer.parseInt(request.getParameter("status"));
        Customer customer = new Customer();
        customer.setPassword(password);
        customer.setUserPhone(userPhone);
        customer.setUserName(userName);
        customer.setCustomerID(customerID);

        boolean flag = customerService.update(customerID, customer);
        Message msg = new Message();
        if(flag){
            msg.setStatus(0);
            msg.setResult("修改成功");
        }else{
            msg.setStatus(-1);
            msg.setResult("修改失败");
        }
        String json = JSONUtil.toJSON(msg);

        return json;
    }

    @RequestMapping("/user/delete.do")
    @ResponseBody
    public String delete(HttpServletRequest request){
        String customerID = request.getParameter("customerID");
        boolean flag = customerService.delete(customerID);
        Message msg = new Message();
        if(flag){
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

package com.hezho.controller;

import com.hezho.bean.BootStrapTableExpress;
import com.hezho.bean.Express;
import com.hezho.bean.Message;
import com.hezho.bean.ResultData;
import com.hezho.service.ExpressService;
import com.hezho.util.DateFormatUtil;
import com.hezho.util.JSONUtil;
import com.hezho.util.UserUtil;
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
public class ExpressController {
//    ExpressService expressService = (ExpressService) ToolSpring.getBean("expressService");
    @Autowired
    ExpressService expressService;

    @RequestMapping("/express/console.do")
    @ResponseBody
    public String console(HttpServletRequest request, HttpServletResponse response){
        List<Map<String, Integer>> data = expressService.console();
        Message msg = new Message();
        if(data.size() == 0){
            msg.setStatus(-1);
        }else {
            msg.setStatus(0);
        }
        msg.setData(data);
        String json = JSONUtil.toJSON(msg);
        return json;
    }


    @RequestMapping("/express/list.do")
    @ResponseBody
    public String list(HttpServletRequest request, HttpServletResponse response){
        //1.    获取查询数据的起始索引值
        int offset = Integer.parseInt(request.getParameter("offset"));
        //2.    获取当前页要查询的数据量
        int pageNumber = Integer.parseInt(request.getParameter("pageNumber"));
        //3.    进行查询
        System.out.println("List All Items");
        List<Express> list = expressService.findAll(true, offset, pageNumber);
        for (Express item:list){
            System.out.println(item);
        }
        List<BootStrapTableExpress> list2 = new ArrayList<>();
        for(Express e:list){
            System.out.println(e.getUsername());
            String inTime = DateFormatUtil.format(e.getInTime());
            String outTime = e.getOutTime()==null?"未出库": DateFormatUtil.format(e.getOutTime());
            String status = e.getStatus()==0?"待取件":"已取件";
            String code = e.getCode()==null?"已取件":e.getCode();
            BootStrapTableExpress e2 = new BootStrapTableExpress(e.getId(),e.getNumber(),e.getUsername(),e.getUserPhone(),e.getCompany(),code,inTime,outTime,status,e.getSysPhone());
            list2.add(e2);
        }
        List<Map<String, Integer>> console = expressService.console();
        Integer total = console.get(0).get("data1_size");
        //4.    将集合封装为 bootstrap-table识别的格式
        ResultData<BootStrapTableExpress> data = new ResultData<>();
        data.setRows(list2);
        data.setTotal(total);
        String json = JSONUtil.toJSON(data);
        return json;
    }

    @RequestMapping("/express/insert.do")
    @ResponseBody
    public String insert(HttpServletRequest request, HttpServletResponse response){
        Express e = null;
        String number = request.getParameter("number");
        String company = request.getParameter("company");
        String username = request.getParameter("username");
        String userPhone = request.getParameter("userPhone");
        String sysPhone = request.getParameter("sysPhone");
        //Express e = new Express(number,company,username,userPhone, UserUtil.getUserPhone(request.getSession()));
        if (sysPhone == null){
            e = new Express(number,username,userPhone,company, UserUtil.getUserPhone(request.getSession()));
        } else {
            e = new Express(number,username,userPhone,company, sysPhone);
        }
        int flag = expressService.insert(e);
        Message msg = new Message();
        if(flag != 0){
            //录入成功
            msg.setStatus(0);
            msg.setResult("快递录入成功!");
        }else{
            //录入失败
            msg.setStatus(-1);
            msg.setResult("快递录入失败!");
        }
        String json = JSONUtil.toJSON(msg);
        return json;
    }

    @RequestMapping("/express/find.do")
    @ResponseBody
    public String find(HttpServletRequest request, HttpServletResponse response){
        String number = request.getParameter("number");
        Express e = expressService.findByNumber(number);
        Message msg = new Message();
        if(e == null){
            msg.setStatus(-1);
            msg.setResult("单号不存在");
        }else{
            msg.setStatus(0);
            msg.setResult("查询成功");
            msg.setData(e);
        }
        String json = JSONUtil.toJSON(msg);
        return json;
    }

    @RequestMapping("/express/update.do")
    @ResponseBody
    public String update(HttpServletRequest request, HttpServletResponse response){
        int id = Integer.parseInt(request.getParameter("id"));
        String number = request.getParameter("number");
        String company = request.getParameter("company");
        String username = request.getParameter("username");
        String userPhone = request.getParameter("userPhone");
        int status = Integer.parseInt(request.getParameter("status"));
        Express newExpress = new Express();
        newExpress.setNumber(number);
        newExpress.setCompany(company);
        newExpress.setUsername(username);
        newExpress.setUserPhone(userPhone);
        newExpress.setStatus(status);
        int flag = expressService.update(id, newExpress);
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

    @RequestMapping("/express/delete.do")
    @ResponseBody
    public String delete(HttpServletRequest request, HttpServletResponse response){
        int id = Integer.parseInt(request.getParameter("id"));
        int flag = expressService.delete(id);
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

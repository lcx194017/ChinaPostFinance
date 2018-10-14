package controller;

import domain.Employee;
import domain.SalaryDetail;
import exception.RecordInvalidException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import service.QueryDataHandleService;
import service.QueryService;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;

import static controller.ManualControl.dateTransform;

/**
 * @author lcx :liu.changxin@qq.com
 * @data 2018/10/14 9:27
 */
@Controller
@RequestMapping(value = "/statistics")
public class StatisticControl {
    private QueryService queryService;
    private QueryDataHandleService queryDataHandleService;

    @Autowired
    public void setQueryService(QueryService queryService) {
        this.queryService = queryService;
    }

    @Autowired
    public void setQueryDataHandleService(QueryDataHandleService queryDataHandleService) {
        this.queryDataHandleService = queryDataHandleService;
    }

    @RequestMapping(value = "/get_link_data", method = RequestMethod.POST)
    public void get_link_data(@RequestParam("startTime") String startTime,
                                     @RequestParam("endTime") String endTime,
                                     @RequestParam("flat") List<String> flat,
                                     @RequestParam("air") List<String> air,
                                     @RequestParam("ground") List<String> ground,
                                     HttpServletResponse response) {
        HashMap<String, Object> params = new HashMap<>();
        //输入String日期转换成Date格式(ManualControl中的静态方法)
        dateTransform(params, startTime, endTime);
        if (flat != null && flat.size() != 0)
            params.put("flat", flat);
        if (air != null && air.size() != 0)
            params.put("air", air);
        if (ground != null && ground.size() != 0)
            params.put("ground", ground);
        String result = null;
        Map<String, Object> map = null;
        try {
            map = queryService.productionLinkDynamicQuery(params);
        } catch (RecordInvalidException e) {
            e.printStackTrace();
            result = "{\"error\": -111}";
        }
        if (map != null) {
            List<Employee> employeeList = (List<Employee>) map.get("employeeList");
            List<SalaryDetail> salaryDetailList = (List<SalaryDetail>) map.get("salaryDetailList");
            result = queryDataHandleService.generateOperationLinkJsonData(employeeList, salaryDetailList, flat, air, ground);
        }
        response.setContentType("text/html;charset=UTF-8");
        //设置跨域请求
        response.setHeader("Access-Control-Allow-Origin", "*");

        try {
            response.getWriter().println(result);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @RequestMapping(value = "/get_link_detail_data", method = RequestMethod.POST)
    public void get_link_detail_data(@RequestParam("startTime") String startTime,
                                       @RequestParam("endTime") String endTime,
                                       @RequestParam("flat") List<String> flat,
                                       @RequestParam("air") List<String> air,
                                       @RequestParam("ground") List<String> ground,
                                       HttpServletResponse response) {
        HashMap<String, Object> params = new HashMap<>();
        //输入String日期转换成Date格式
        dateTransform(params, startTime, endTime);
        if (flat != null && flat.size() != 0)
            params.put("flat", flat);
        if (air != null && air.size() != 0)
            params.put("air", air);
        if (ground != null && ground.size() != 0)
            params.put("ground", ground);
        String result = null;
        Map<String, Object> map = null;
        try {
            map = queryService.productionLinkDynamicQuery(params);
        } catch (RecordInvalidException e) {
            e.printStackTrace();
            result = "{\"error\": -112}";
        }
        if (map != null) {
            List<Employee> employeeList = (List<Employee>) map.get("employeeList");
            List<SalaryDetail> salaryDetailList = (List<SalaryDetail>) map.get("salaryDetailList");
            result = queryDataHandleService.generateOperationLinkDetailJsonData(employeeList,
                    salaryDetailList);
        }
        response.setContentType("text/html;charset=UTF-8");
        //设置跨域请求
        response.setHeader("Access-Control-Allow-Origin", "*");

        try {
            response.getWriter().println(result);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    @RequestMapping(value = "/get_cost_detail_data", method = RequestMethod.POST)
    public void get_cost_detail_data(@RequestParam("time") String time,
                                       HttpServletResponse response) {
        Date date = FileUploadControl.dateTransform(time);
        Map<String, Object> map = null;
        String result = null;

        try {
            map = queryService.dateRangeQuery(date, date);
        } catch (RecordInvalidException e) {
            e.printStackTrace();
            result = "{\"error\": -112}";
        }

        if (map != null) {
            List<Employee> employeeList = (List<Employee>) map.get("employeeList");
            List<SalaryDetail> salaryDetailList = (List<SalaryDetail>) map.get("salaryDetailList");
            result = queryDataHandleService.generateStatisticCostDetailJsonData(employeeList,
                    salaryDetailList);
        }
        response.setContentType("text/html;charset=UTF-8");
        //设置跨域请求
        response.setHeader("Access-Control-Allow-Origin", "*");

        try {
            response.getWriter().println(result);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

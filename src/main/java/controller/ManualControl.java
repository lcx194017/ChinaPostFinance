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

/**
 * @author lcx :liu.changxin@qq.com
 * @data 2018/10/9 8:33
 */
@Controller
@RequestMapping(value = "/manual")
public class ManualControl {

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

    @RequestMapping(value = "/department_three/base", method = RequestMethod.GET)
    public void departmentThree_base(HttpServletResponse response) {
        Map<String, Object> map = null;
        String result = null;
        try {
            map = queryService.operationAreaDynamicQuery(null);
        } catch (RecordInvalidException e) {
            e.printStackTrace();
            result = "{\"error\": -102}";
        }
        if (map != null) {
            List<Employee> employeeList = (List<Employee>) map.get("employeeList");
            List<SalaryDetail> salaryDetailList = (List<SalaryDetail>) map.get("salaryDetailList");
            result = queryDataHandleService.generateOperationAreaJsonData(employeeList, salaryDetailList);
        }

        response.setContentType("text/html;charset=UTF-8");
        response.setHeader("Access-Control-Allow-Origin", "*");
        try {
            response.getWriter().println(result);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    @RequestMapping(value = "/department_three/base/get_base_group_data", method = RequestMethod.POST)
    public void departmentThree_base_getBaseGroupData(@RequestParam("startTime") String startTime,
                                                      @RequestParam("endTime") String endTime,
                                                      @RequestParam("department") List<String> department,
                                                      @RequestParam("group") List<String> group,
                                                      HttpServletResponse response) {
        HashMap<String, Object> params = new HashMap<>();
        //输入String日期转换成Date格式
        dateTransform(params, startTime, endTime);
        params.put("department", department);
        params.put("group", group);
        String result = null;
        Map<String, Object> map = null;
        try {
            map = queryService.operationAreaDynamicQuery(params);
        } catch (RecordInvalidException e) {
            e.printStackTrace();
            result = "{\"error\": -102}";
        }
        if (map != null) {
            List<Employee> employeeList = (List<Employee>) map.get("employeeList");
            List<SalaryDetail> salaryDetailList = (List<SalaryDetail>) map.get("salaryDetailList");
            result = queryDataHandleService.generateOperationAreaJsonData(employeeList, salaryDetailList);
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

    @RequestMapping(value = "/department_three/link", method = RequestMethod.POST)
    public void departmentThree_link(@RequestParam("startTime") String startTime,
                                     @RequestParam("endTime") String endTime,
                                     @RequestParam("link") List<String> link,
                                     HttpServletResponse response) {
        HashMap<String, Object> params = new HashMap<>();
        //输入String日期转换成Date格式
        dateTransform(params, startTime, endTime);
        params.put("production_link", link);
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
            result = queryDataHandleService.generateOperationLinkJsonData(employeeList, salaryDetailList, link);
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

    @RequestMapping(value = "/department_three/detail", method = RequestMethod.POST)
    public void departmentThree_detail(@RequestParam("startTime") String startTime,
                                     @RequestParam("endTime") String endTime,
                                     @RequestParam("link") List<String> link,
                                     HttpServletResponse response) {
        HashMap<String, Object> params = new HashMap<>();
        //输入String日期转换成Date格式
        dateTransform(params, startTime, endTime);
        params.put("production_link", link);
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
                    salaryDetailList, link.get(0));
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

    private static void dateTransform(Map<String, Object> map, String startTime, String endTime) {
        if (!startTime.equals("")) {
            String[] times = startTime.split("-");
            int year = Integer.parseInt(times[0]);
            int month = Integer.parseInt(times[1]);
            Date start = new Date(year - 1900, month - 1, 1);
            map.put("startTime", start);
        } else {
            //设定一个最早日期，防止截至日期选了，但开始日期没有选的清空
            Date start = new Date(1991 - 1900, 1, 1);
            map.put("startTime", start);
        }
        if (!endTime.equals("")) {
            String[] times = startTime.split("-");
            int year = Integer.parseInt(times[0]);
            int month = Integer.parseInt(times[1]);
            Date end = new Date(year - 1900, month - 1, 1);
            map.put("endTime", end);
        }
    }
}


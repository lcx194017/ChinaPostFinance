package controller;

import domain.Employee;
import domain.SalaryDetail;

import exception.RecordInvalidException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import others.ResCenterCollection;
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

//    @RequestMapping(value = "base", method = RequestMethod.POST)
//    public void departmentThree_base( @RequestParam("department") String department,
//                                     HttpServletResponse response) {
//        if (department.equals("空侧责任中心")) {}
//        else if (department.equals("陆侧责任中心")){}
//        else if (department.equals("")) {}
//        else if (department.equals("")) {}
//        else if (department.equals("")) {}
//        else  if (department.equals("")) {}
//        else if (de)
//
//        HashMap<String, Object> params = new HashMap<>();
//        if (department != null && department.size() != 0)
//            params.put("department", department);
//        Map<String, Object> map = null;
//        String result = null;
//        try {
//            map = queryService.operationAreaDynamicQuery(params);
//        } catch (RecordInvalidException e) {
//            e.printStackTrace();
//            result = "{\"error\": -101}";
//        }
//        if (map != null) {
//            List<Employee> employeeList = (List<Employee>) map.get("employeeList");
//            List<SalaryDetail> salaryDetailList = (List<SalaryDetail>) map.get("salaryDetailList");
//            result = queryDataHandleService.generateOperationAreaJsonData(employeeList, salaryDetailList);
//        }
//
//        response.setContentType("text/html;charset=UTF-8");
//        response.setHeader("Access-Control-Allow-Origin", "*");
//        try {
//            response.getWriter().println(result);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }


    @RequestMapping(value = "/base/get_base_group_data", method = RequestMethod.POST)
    public void departmentThree_base_getBaseGroupData(@RequestParam("startTime") String startTime,
                                                      @RequestParam("endTime") String endTime,
                                                      @RequestParam("department") List<String> department,
                                                      @RequestParam("group") List<String> group,
                                                      HttpServletResponse response) {
        HashMap<String, Object> params = new HashMap<>();
        List<String> department_transform = null;
        //输入String日期转换成Date格式
        dateTransform(params, startTime, endTime);
        for (String dep : department) {
            String[] centers = ResCenterCollection.getRESPONSIBILITY_CENTERS();
            LinkedList<String>[] linkedLists = ResCenterCollection.getLINKED_LISTS();
            for (int i = 0; i < centers.length; i++) {
                if (dep.equals(centers[i])) {
                    department_transform = linkedLists[i];
                    break;
                }
            }
        }
        if (department_transform != null)
            params.put("department", department_transform);
        if ((!department.contains("综合责任中心")) && group != null && group.size() != 0)
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

    /**
     * @Description: 根据姓名查询信息
     * @Param: [name, response]
     * @return: void
     * @Author: lcx
     * @Date: 2018/10/11
     */
    @RequestMapping(value = "/department_three/base/get_base_name_data", method = RequestMethod.POST)
    public void departmentThree_getBaseNameData(@RequestParam("name") String name,
                                                HttpServletResponse response) {
        Map<String, Object> map = null;
        String result = null;
        try {
            map = queryService.operationAreaPersonQuery(name);
        } catch (RecordInvalidException e) {
            e.printStackTrace();
            result = "{\"error\": -103}";
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

    @RequestMapping(value = "/link/get_link_data", method = RequestMethod.POST)
    public void departmentThree_link(@RequestParam("startTime") String startTime,
                                     @RequestParam("endTime") String endTime,
                                     @RequestParam("department") String department,
                                     @RequestParam("link") List<String> link,
                                     HttpServletResponse response) {
        HashMap<String, Object> params = new HashMap<>();
        //输入String日期转换成Date格式
        dateTransform(params, startTime, endTime);

        List<String> flat = null;
        List<String> air = null;
        List<String> ground = null;

        if (link != null && link.size() != 0) {
            if (department.equals("扁平件责任中心")) {
                flat = link;
                params.put("flat", flat);
            } else if (department.equals("空侧责任中心")) {
                air = link;
                params.put("air", air);
            } else if (department.equals("陆侧责任中心")) {
                ground = link;
                params.put("ground", ground);
            }
        }

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

    @RequestMapping(value = "/link/get_link_detail_data", method = RequestMethod.POST)
    public void departmentThree_detail(@RequestParam("startTime") String startTime,
                                       @RequestParam("endTime") String endTime,
                                       @RequestParam("department") String department,
                                       @RequestParam("link") List<String> link,
                                       HttpServletResponse response) {
        HashMap<String, Object> params = new HashMap<>();
        //输入String日期转换成Date格式
        dateTransform(params, startTime, endTime);

        if (link != null && link.size() != 0) {
            if (department.equals("扁平件责任中心")) {
                params.put("flat", link);
            } else if (department.equals("空侧责任中心")) {
                params.put("air", link);
            } else if (department.equals("陆侧责任中心")) {
                params.put("ground", link);
            }
        }

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

//    @RequestMapping(value = "/department_three/link/get_link_data", method = RequestMethod.POST)
//    public void departmentThree_link(@RequestParam("startTime") String startTime,
//                                     @RequestParam("endTime") String endTime,
//                                     @RequestParam("flat") List<String> flat,
//                                     @RequestParam("air") List<String> air,
//                                     @RequestParam("ground") List<String> ground,
//                                     HttpServletResponse response) {
//        HashMap<String, Object> params = new HashMap<>();
//        //输入String日期转换成Date格式
//        dateTransform(params, startTime, endTime);
//        if (flat != null && flat.size() != 0)
//            params.put("flat", flat);
//        if (air != null && air.size() != 0)
//            params.put("air", air);
//        if (ground != null && ground.size() != 0)
//            params.put("ground", ground);
//        String result = null;
//        Map<String, Object> map = null;
//        try {
//            map = queryService.productionLinkDynamicQuery(params);
//        } catch (RecordInvalidException e) {
//            e.printStackTrace();
//            result = "{\"error\": -111}";
//        }
//        if (map != null) {
//            List<Employee> employeeList = (List<Employee>) map.get("employeeList");
//            List<SalaryDetail> salaryDetailList = (List<SalaryDetail>) map.get("salaryDetailList");
//            result = queryDataHandleService.generateOperationLinkJsonData(employeeList, salaryDetailList, flat, air, ground);
//        }
//        response.setContentType("text/html;charset=UTF-8");
//        //设置跨域请求
//        response.setHeader("Access-Control-Allow-Origin", "*");
//
//        try {
//            response.getWriter().println(result);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }

//    @RequestMapping(value = "/department_three/link/get_link_detail_data", method = RequestMethod.POST)
//    public void departmentThree_detail(@RequestParam("startTime") String startTime,
//                                       @RequestParam("endTime") String endTime,
//                                       @RequestParam("flat") List<String> flat,
//                                       @RequestParam("air") List<String> air,
//                                       @RequestParam("ground") List<String> ground,
//                                       HttpServletResponse response) {
//        HashMap<String, Object> params = new HashMap<>();
//        //输入String日期转换成Date格式
//        dateTransform(params, startTime, endTime);
//        if (flat != null && flat.size() != 0)
//            params.put("flat", flat);
//        if (air != null && air.size() != 0)
//            params.put("air", air);
//        if (ground != null && ground.size() != 0)
//            params.put("ground", ground);
//        String result = null;
//        Map<String, Object> map = null;
//        try {
//            map = queryService.productionLinkDynamicQuery(params);
//        } catch (RecordInvalidException e) {
//            e.printStackTrace();
//            result = "{\"error\": -112}";
//        }
//        if (map != null) {
//            List<Employee> employeeList = (List<Employee>) map.get("employeeList");
//            List<SalaryDetail> salaryDetailList = (List<SalaryDetail>) map.get("salaryDetailList");
//            result = queryDataHandleService.generateOperationLinkDetailJsonData(employeeList,
//                    salaryDetailList);
//        }
//        response.setContentType("text/html;charset=UTF-8");
//        //设置跨域请求
//        response.setHeader("Access-Control-Allow-Origin", "*");
//
//        try {
//            response.getWriter().println(result);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }

    /**
     * 测试匹配id
     */
    @RequestMapping(value = "/test/{id}", method = RequestMethod.GET)
    public void test() {
        System.out.println("test success!");
    }

    static void dateTransform(Map<String, Object> map, String startTime, String endTime) {
        if (!startTime.equals("")) {
            String[] times = startTime.split("-");
            int year = Integer.parseInt(times[0]);
            int month = Integer.parseInt(times[1]);
            Date start = new Date(year - 1900, month - 1, 1);
            map.put("startTime", start);
        }
        if (!endTime.equals("")) {
            if (startTime.equals("")) {
                //设定一个最早日期，防止截至日期选了，但开始日期没有选的清空
                Date start = new Date(1991 - 1900, 1, 1);
                map.put("startTime", start);
            }
            String[] times = startTime.split("-");
            int year = Integer.parseInt(times[0]);
            int month = Integer.parseInt(times[1]);
            Date end = new Date(year - 1900, month - 1, 1);
            map.put("endTime", end);
        }
    }
}


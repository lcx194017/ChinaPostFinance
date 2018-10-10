package service;

import domain.Employee;
import domain.SalaryDetail;
import exception.RecordInvalidException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.io.*;
import java.util.*;

/**
 * @author lcx :liu.changxin@qq.com
 * @data 2018/10/7 20:45
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath*:/smart-context.xml")
public class QueryServiceTest {

    @Autowired
    QueryService queryService;

    @Autowired
    QueryDataHandleService queryDataHandleService;

    @Test
    public void operationAreaDynamicQuery() {
        Date startTime = new Date(2018 - 1900, 9,1);
        Date endTime = new Date(2018 - 1900, 9,1);
        List<String> department = new LinkedList<>();
        department.add("空侧邮件作业区");
        department.add("陆侧邮件作业区");
        List<String> group = new LinkedList<>();
        group.add("综合班");
        group.add("白班");
        HashMap<String, Object> params = new HashMap<>();
        params.put("startTime", startTime);
        params.put("endTime", endTime);
        params.put("department",department);
        params.put("group",group);
        Map<String, Object> map = null;
        try {
            map =  queryService.operationAreaDynamicQuery(params);
        } catch (RecordInvalidException e) {
            e.printStackTrace();
        }
        if (map != null) {
            List<Employee> employeeList = (List<Employee>) map.get("employeeList");
            List<SalaryDetail> salaryDetailList = (List<SalaryDetail>) map.get("salaryDetailList");


            String result = queryDataHandleService.generateOperationAreaJsonData(employeeList, salaryDetailList);
            File file = new File("G:/EmployeeJson.json");
            try {
                BufferedWriter writer = new BufferedWriter(new FileWriter(file));
                writer.write(result);
                writer.flush();
                writer.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Test
    public void productionLinkDynamicQuery() {
        Date startTime = new Date(2018 - 1900, 9,1);
        Date endTime = new Date(2018 - 1900, 9,1);
        List<String> production_link = new LinkedList<>();
        production_link.add("综合");
        production_link.add("运保");
        production_link.add("白班");

        HashMap<String, Object> params = new HashMap<>();
        params.put("startTime", startTime);
        params.put("endTime", endTime);
        params.put("production_link",production_link);

        Map<String, Object> map = null;
        try {
            map =  queryService.productionLinkDynamicQuery(params);
        } catch (RecordInvalidException e) {
            e.printStackTrace();
        }
        if (map != null) {
            List<Employee> employeeList = (List<Employee>) map.get("employeeList");
            List<SalaryDetail> salaryDetailList = (List<SalaryDetail>) map.get("salaryDetailList");


            String result = queryDataHandleService.generateOperationLinkJsonData(employeeList, salaryDetailList, production_link);
            File file = new File("G:/Production_link.json");
            try {
                BufferedWriter writer = new BufferedWriter(new FileWriter(file));
                writer.write(result);
                writer.flush();
                writer.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Test
    public void generateOperationLinkDetailJsonData() {
        Date startTime = new Date(2018 - 1900, 9,1);
        Date endTime = new Date(2018 - 1900, 9,1);
        List<String> production_link = new LinkedList<>();
        production_link.add("综合");
        HashMap<String, Object> params = new HashMap<>();
        params.put("startTime", startTime);
        params.put("endTime", endTime);
        params.put("production_link",production_link);

        Map<String, Object> map = null;
        try {
            map =  queryService.productionLinkDynamicQuery(params);
        } catch (RecordInvalidException e) {
            e.printStackTrace();
        }
        if (map != null) {
            List<Employee> employeeList = (List<Employee>) map.get("employeeList");
            List<SalaryDetail> salaryDetailList = (List<SalaryDetail>) map.get("salaryDetailList");


            String result = queryDataHandleService.generateOperationLinkDetailJsonData(employeeList, salaryDetailList, ((LinkedList<String>) production_link).getFirst());
            File file = new File("G:/LinkDetail.json");
            try {
                BufferedWriter writer = new BufferedWriter(new FileWriter(file));
                writer.write(result);
                writer.flush();
                writer.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
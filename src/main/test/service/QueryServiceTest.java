package service;

import controller.QueryDataHandle;
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

import static org.junit.Assert.*;

/**
 * @author lcx :liu.changxin@qq.com
 * @data 2018/10/7 20:45
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath*:/smart-context.xml")
public class QueryServiceTest {

    @Autowired
    QueryService queryService;

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
            QueryDataHandle.getEmployeeValidData(employeeList);
            QueryDataHandle.getSalaryDetailValidData(salaryDetailList);
            String result = QueryDataHandle.jsonDataGenerate(employeeList, salaryDetailList);
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
}
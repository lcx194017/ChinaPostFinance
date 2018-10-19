package dao;

import domain.Employee;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.*;

import static org.junit.Assert.*;

/**
 * @author lcx :liu.changxin@qq.com
 * @date 2018/10/3 13:42
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath*:/smart-context.xml")
public class EmployeeDaoTest {

    @Autowired
    private EmployeeDao employeeDao;

    @Test
    public void insertEmployee() {
        Employee employee = new Employee();
        employee.setId("321082197809213257");
        employee.setName("陈凯（陆）");
        employee.setLevel("操作岗（新B）");
        employee.setDepartment("陆侧邮件作业区");
        employee.setGroup("封发");
        employee.setPost("质检");
        employee.setProduction_link("质检巡查");
        employee.setExpiration_date(new Date(2018 - 1900, 9, 1));

        Employee employee1 = new Employee();
        employee1.setId("321082197809213258");
        employee1.setName("陈");
        employee1.setLevel("操作岗（新B）");
        employee1.setDepartment("陆侧邮件作业区");
        employee1.setGroup("封发");
        employee1.setPost("质检");
        employee1.setProduction_link("质检巡查");
        employee1.setExpiration_date(new Date(2018 - 1900, 10, 1));

        List<Employee> list = new LinkedList<>();
        list.add(employee);
        list.add(employee1);
        employeeDao.insertEmployeeBatch(list);
        System.out.println("插入数据完毕");
    }

    @Test
    public void getEmployeeByDateRange() {
        Date date1 = new Date(2018 - 1900, 9, 1);
        Date date2 = new Date(2018 - 1900, 9, 1);
        List<Employee> list = employeeDao.getEmployeeByDateRange(date1, date2);
        System.out.println(list.size());
    }

    @Test
    public void getEmployeeByLink() {
        String link = "人工处理";
        List<Employee> list = employeeDao.getEmployeeByLink(link);
        System.out.println(list.size());
    }

    @Test
    public void getEmployeeByLinkAndDateRange() {
        String link = "人工处理";
        Date date1 = new Date(2018 - 1900, 9, 1);
        Date date2 = new Date(2018 - 1900, 10, 1);
        List<Employee> list = employeeDao.getEmployeeByLinkAndDateRange(link, date1, date2);
        System.out.println(list.size());
    }

    @Test
    public void getEmployeeByDynamicCondition() {
        Date startTime = new Date(2018 - 1900, 9, 1);
        Date endTime = new Date(2018 - 1900, 9, 1);
        List<String> department = new LinkedList<>();
        department.add("空侧邮件作业区");
        department.add("陆侧邮件作业区");
        List<String> group = new LinkedList<>();
        group.add("综合班");
        group.add("白班");
        HashMap<String, Object> params = new HashMap<>();
        params.put("startTime", startTime);
        params.put("endTime", endTime);
        params.put("department", department);
        params.put("group", group);
        List<Employee> list = employeeDao.getEmployeeByDynamicCondition(params);
        System.out.println(list.size());
    }

    @Test
    public void getProductionLinkByDynamicCondition() {
        Date startTime = new Date(2018 - 1900, 9, 1);
        Date endTime = new Date(2018 - 1900, 9, 1);
        List<String> production_link = new LinkedList<>();
        production_link.add("邮件过检");
        production_link.add("邮件装车发运");

        HashMap<String, Object> params = new HashMap<>();
        params.put("startTime", startTime);
        params.put("endTime", endTime);
        params.put("production_link", production_link);

        List<Employee> list = employeeDao.getProductionLinkByDynamicCondition(params);
        System.out.println(list.size());
    }

    @Test
    public void updateEmployeeGroup() {
        int counts = employeeDao.updateEmployeeGroup("人工", "人工处理班");
        System.out.println("人工 -> 人工处理班：" + counts);

        counts = employeeDao.updateEmployeeGroup("封发", "封发班");
        System.out.println("封发 -> 封发班：" + counts);

        counts = employeeDao.updateEmployeeGroup("综合", "综合班");
        System.out.println("综合 -> 综合班" + counts);

        counts = employeeDao.updateEmployeeGroup("查验", "查验班组");
        System.out.println("查验 -> 查验班组" + counts);

        counts = employeeDao.updateEmployeeGroup("客服", "客服班组");
        System.out.println("客服 -> 客服班组" + counts);

    }
}
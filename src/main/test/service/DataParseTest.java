package service;

import dao.EmployeeDao;
import dao.SalaryDetailDao;
import domain.Employee;
import domain.SalaryDetail;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.*;

import static org.junit.Assert.*;

/**
 * @author lcx :liu.changxin@qq.com
 * @data 2018/10/3 14:59
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath*:/smart-context.xml")
public class DataParseTest {
    @Autowired
    private EmployeeDao employeeDao;
    @Autowired
    private SalaryDetailDao salaryDetailDao;

    /**解析excel中的数据到domain并写入数据库测试*/
    @Test
    public void phrase_inset() {
        Map<String, Employee> employeeMap = new HashMap<String, Employee>();
        Map<String, SalaryDetail> salaryDetailMap = new HashMap<String, SalaryDetail>();
        String file_path = "H:\\工程项目\\南京集散中心损益结算\\第一期\\空侧_生产环节sy.xlsx";
        int res = DataParse.ReadExcelData(file_path, employeeMap, salaryDetailMap);
        assertEquals(res, 0);
        Set<String> keySet = employeeMap.keySet();
        List<Employee> list = new LinkedList<>();
        for (String key : keySet) {
            Employee employee = employeeMap.get(key);
            employee.setExpiration_date(new Date(2018 - 1900, 9,1));
            list.add(employee);
        }
        employeeDao.insertEmployeeBatch(list);
        System.out.println("员工数据插入完毕");

        keySet = salaryDetailMap.keySet();
        List<SalaryDetail>list1 = new LinkedList<>();
        for (String key : keySet) {
            SalaryDetail salaryDetail = salaryDetailMap.get(key);
            salaryDetail.setPay_date(new Date(2018 - 1900, 9,1));
            list1.add(salaryDetail);
        }
        salaryDetailDao.insertSalaryDetailBatch(list1);
        System.out.println("工资数据插入完毕");
    }

    @Test
    public void data_query_handle() {

    }
}
package service;

import dao.EmployeeDao;
import dao.SalaryDetailDao;
import domain.Employee;
import domain.SalaryDetail;
import exception.RecordInvalidException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

/**
 * @author lcx :liu.changxin@qq.com
 * @date 2018/10/6 19:54
 * 功能：将解析Excel得到的数据入库
 */
@Service
public class SalaryService {

    EmployeeDao employeeDao;

    SalaryDetailDao salaryDetailDao;

    @Autowired
    public void setEmployeeDao(EmployeeDao employeeDao) {
        this.employeeDao = employeeDao;
    }

    @Autowired
    public void setSalaryDetailDao(SalaryDetailDao salaryDetailDao) {
        this.salaryDetailDao = salaryDetailDao;
    }

    /**
     * 将员工数据和工资信息录入数据库，如果员工信息和工资信息已经存在，新的数据覆盖旧的数据
     */
    @Transactional
    public void insertSalaryData(Map<String, Employee> employeeMap,
                                 Map<String, SalaryDetail> salaryDetailMap, Date date)
            throws RecordInvalidException {
        Set<String> employeeKeys = employeeMap.keySet();
        for (String key : employeeKeys) {
            Employee employee = employeeMap.get(key);
            employee.setExpiration_date(date);
            SalaryDetail salaryDetail = salaryDetailMap.get(key);
            salaryDetail.setPay_date(date);
            if (employee == null && salaryDetail == null)
                continue;

            /**信息不对等，无效，抛出异常*/
            if (employee == null || salaryDetail == null)
                throw new RecordInvalidException("存在无效的插入数据");

            int employeeExist = employeeDao.employeeIsExist(employee);
            int salaryDetailExist = salaryDetailDao.getSalaryDetailExist(salaryDetail);
            if (employeeExist == 0)
                employeeDao.insertEmployee(employee);
            else
                employeeDao.updateEmployee(employee);
            if (salaryDetailExist == 0)
                salaryDetailDao.insertSalaryDetail(salaryDetail);
            else
                salaryDetailDao.updateSalaryDetail(salaryDetail);
        }
    }

    /**
     * @Description: 如果数据库中没有对应记录，批量插入，如果有则调用 insertSalaryData逐条更新或插入
     * @Param: [employeeMap, salaryDetailMap]
     * @return: int
     * @Author: lcx
     * @Date: 2018/10/11
     */
    @Transactional
    public int insertOrUpdateDate(Map<String, Employee> employeeMap,
                                  Map<String, SalaryDetail> salaryDetailMap, Date date)
            throws RecordInvalidException {
        if (employeeMap.size() == 0 || salaryDetailMap.size() == 0)
            return -1;

        if (employeeMap.size() != salaryDetailMap.size())
            throw new RecordInvalidException("数据信息不对等");

        Set<String> set = employeeMap.keySet();
        String random_key = null;
        for (String s : set) {
            random_key = s;
            break;
        }
        Employee employee = employeeMap.get(random_key);
        String department = employee.getDepartment();

        //通过employee表就可以知道该部门本月的数据是不是已经录入
        int count = employeeDao.depAndDateExist(department, date).size();
        if (count == 0) {   //该部门本月数据没有录入，可以批量插入
            Set<String> keySet = employeeMap.keySet();
            List<Employee> emp_list = new LinkedList<>();
            for (String key : keySet) {
                Employee emp= employeeMap.get(key);
                employee.setExpiration_date(date);
                emp_list.add(emp);
            }
            employeeDao.insertEmployeeBatch(emp_list);

            keySet = salaryDetailMap.keySet();
            List<SalaryDetail>sal_list = new LinkedList<>();
            for (String key : keySet) {
                SalaryDetail salaryDetail = salaryDetailMap.get(key);
                salaryDetail.setPay_date(date);
                sal_list.add(salaryDetail);
            }
            salaryDetailDao.insertSalaryDetailBatch(sal_list);
        } else {
            insertSalaryData(employeeMap, salaryDetailMap, date);
        }
        return 0;
    }

    @Transactional
    public void deleteOldData(String department, Date date) {
        List<Employee> employeeList = employeeDao.depAndDateExist(department, date);
        salaryDetailDao.deleteSalaryRecord(employeeList);
        employeeDao.deleteByDepartmentDate(department, date);
    }

    /**
     * @Description: 目前数据还不稳定，某些班组字段不统一，在此统一修改
     * @Param: []
     * @return: void
     * @Author: lcx
     * @Date: 2018/10/19
     */
    @Transactional
    public void updateGroupName() {
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

package service;

import dao.EmployeeDao;
import dao.SalaryDetailDao;
import domain.Employee;
import domain.SalaryDetail;
import exception.RecordInvalidException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;
import java.util.Set;

/**
 * @author lcx :liu.changxin@qq.com
 * @data 2018/10/6 19:54
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
                                 Map<String, SalaryDetail> salaryDetailMap)
            throws RecordInvalidException {
        Set<String> employeeKeys = employeeMap.keySet();
        for (String key : employeeKeys) {
            Employee employee = employeeMap.get(key);
            SalaryDetail salaryDetail = salaryDetailMap.get(key);
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
}

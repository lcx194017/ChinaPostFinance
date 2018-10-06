package dao;

import domain.Employee;

import java.util.List;

/**
 * @author lcx :liu.changxin@qq.com
 * @data 2018/10/3 13:24
 */
public interface EmployeeDao {
    void insertEmployee(Employee employee);

    void insertEmployeeBatch(List<Employee> list);
}

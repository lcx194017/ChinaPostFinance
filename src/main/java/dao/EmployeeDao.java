package dao;

import domain.Employee;

import java.util.Date;
import java.util.HashMap;
import java.util.List;

/**
 * @author lcx :liu.changxin@qq.com
 * @data 2018/10/3 13:24
 */
public interface EmployeeDao {

    void insertEmployee(Employee employee);

    void insertEmployeeBatch(List<Employee> list);

    /**查询某个员工某月的数据是不是已经存在*/
    int employeeIsExist(Employee employee);

    /**对已经存在的员工某月数据进行更新*/
    void updateEmployee(Employee employee);

    /**通过日期范围获取指定时间段的员工信息*/
    List<Employee> getEmployeeByDateRange(Date date1, Date date2);

    /**根据员工姓名进行查询*/
    List<Employee> getEmployeeByName(String name);

    /**通过生产环节获取员工信息*/
    List<Employee> getEmployeeByLink(String link);

    /**通过生产环节和日期范围获取员工信息*/
    List<Employee> getEmployeeByLinkAndDateRange(String link, Date date1, Date date2);

    /**动态查询，根据时间段、作业区、班组查询数据
     * params:可能但不完全包括 startTime, endTime, department, group
     * department, group 的类型是list
     * */
    List<Employee> getEmployeeByDynamicCondition(HashMap<String, Object> params);

    /**动态查询，根据时间段、生产环节查询数据，查询到的数据用于环节成本分摊
     * params:可能但不完全包括 startTime, endTime , 生产环节描述*/
    List<Employee> getProductionLinkByDynamicCondition(HashMap<String, Object> params);

}

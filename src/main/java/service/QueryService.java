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
 * @date 2018/10/6 19:51
 */
@Service
public class QueryService {

    private EmployeeDao employeeDao;

    private SalaryDetailDao salaryDetailDao;

    @Autowired
    public void setEmployeeDao(EmployeeDao employeeDao) {
        this.employeeDao = employeeDao;
    }

    @Autowired
    public void setSalaryDetailDao(SalaryDetailDao salaryDetailDao) {
        this.salaryDetailDao = salaryDetailDao;
    }

    /**
     * @Description: 判断某个部门某个月的数据是否已经入库
     * @Param: [department, date]
     * @return: int
     * @Author: lcx
     * @Date: 2018/10/10
     */
    @Transactional(readOnly = true)
    public int departmentAtDateExist(String department, Date date) {
        return employeeDao.depAndDateExist(department, date).size();
    }

    /**
     * @Description: 查询特定员工的所有数据
     * @Param: [name] 
     * @return: java.util.Map<java.lang.String,java.lang.Object> 
     * @Author: lcx
     * @Date: 2018/10/10 
     */ 
    @Transactional(readOnly = true)
    public Map<String, Object> operationAreaPersonQuery(String name) throws RecordInvalidException{
        List<Employee> employeeList = employeeDao.getEmployeeByName(name);
        List<SalaryDetail> salaryDetailList = getSalaryDetailByEmployee(employeeList);
        Map<String, Object> resultMap = new HashMap<>();
        resultMap.put("employeeList", employeeList);
        resultMap.put("salaryDetailList", salaryDetailList);
        return resultMap;
    }

    /**
     * @Description: 查询某个时间范围内的所有数据
     * @Param: [date1, date2]
     * @return: java.util.Map<java.lang.String,java.lang.Object>
     * @Author: lcx
     * @Date: 2018/10/14
     */
    @Transactional(readOnly = true)
    public Map<String, Object> dateRangeQuery(Date date1, Date date2) throws RecordInvalidException{
        List<Employee> employeeList = employeeDao.getEmployeeByDateRange(date1, date2);
        List<SalaryDetail> salaryDetailList = getSalaryDetailByEmployee(employeeList);
        Map<String, Object> resultMap = new HashMap<>();
        resultMap.put("employeeList", employeeList);
        resultMap.put("salaryDetailList", salaryDetailList);
        return resultMap;
    }
    
    /**
     * @Description: 依据前端选择条件，动态查询，params为HashMap结构 params:可能但不完全包括
     * startTime, endTime, department, group  department, group 的类型是list 把查询结果放进Map
     * 中作为返回值，做下一步的处理
     * @Param: [params]
     * @return: java.util.Map<java.lang.String , java.lang.Object>
     * @Author: lcx
     * @Date: 2018/10/8
     */
    @Transactional(readOnly = true)
    public Map<String, Object> operationAreaDynamicQuery(HashMap<String, Object> params)
            throws RecordInvalidException {
        List<Employee> employeeList = employeeDao.getEmployeeByDynamicCondition(params);
        List<SalaryDetail> salaryDetailList = getSalaryDetailByEmployee(employeeList);
        Map<String, Object> resultMap = new HashMap<>();
        resultMap.put("employeeList", employeeList);
        resultMap.put("salaryDetailList", salaryDetailList);
        return resultMap;
    }

    /**
     * @Description: 依据前端选择条件，对生产环节统计需要的数据进行动态查询,params为HashMap结构
     * params:可能但不完全包括 startTime, endTime, flat, air, ground
     * 其中 flat, air, ground 的类型是list，然后把查询结果放进Map中作为返回值，做下一步的处理
     * @Param: [params]
     * @return: java.util.Map<java.lang.String       ,       java.lang.Object>
     * @Author: lcx
     * @Date: 2018/10/8
     */
    @Transactional(readOnly = true)
    public Map<String, Object> productionLinkDynamicQuery(HashMap<String, Object> params)
            throws RecordInvalidException {
        List<Employee> employeeList = employeeDao.getProductionLinkByDynamicCondition(params);
        List<SalaryDetail> salaryDetailList = getSalaryDetailByEmployee(employeeList);
        Map<String, Object> resultMap = new HashMap<>();
        resultMap.put("employeeList", employeeList);
        resultMap.put("salaryDetailList", salaryDetailList);
        return resultMap;
    }

    private List<SalaryDetail> getSalaryDetailByEmployee(List<Employee> employeeList)
            throws RecordInvalidException {
        List<SalaryDetail> salaryDetailList = new LinkedList<>();
        for (Employee employee : employeeList) {
            String id = employee.getId();
            Date date = employee.getExpiration_date();
            List<SalaryDetail> list = salaryDetailDao.getSalaryDetailByIDAndDate(id, date);
            if (list.size() != 1)
                throw new RecordInvalidException("employee 和 salary_detail数据表信息不对等");
            salaryDetailList.addAll(list);
        }
        return salaryDetailList;
    }
}

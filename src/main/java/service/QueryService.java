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
 * @data 2018/10/6 19:51
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
     *
     */
    /**
     * @Description: 依据前端选择条件，动态查询，params为HashMap结构 params:可能但不完全包括
     * startTime, endTime, department, group  department, group 的类型是list 把查询结果放进Map
     * 中作为返回值，做下一步的处理
     * @Param: [params]
     * @return: java.util.Map<java.lang.String,java.lang.Object>
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
     * params:可能但不完全包括 startTime, endTime, production_link, 其中 production_link 的类型是
     * list，然后把查询结果放进Map中作为返回值，做下一步的处理
     * @Param: [params]
     * @return: java.util.Map<java.lang.String   ,   java.lang.Object>
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

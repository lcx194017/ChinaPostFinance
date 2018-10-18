package dao;

import domain.SalaryDetail;

import java.util.Date;
import java.util.List;

/**
 * @author lcx :liu.changxin@qq.com
 * @date 2018/10/3 14:12
 */
public interface SalaryDetailDao {

    void insertSalaryDetail(SalaryDetail salaryDetail);

    void insertSalaryDetailBatch(List<SalaryDetail> list);

    /**判断是否已经导入了员工该月份的数据*/
    int getSalaryDetailExist(SalaryDetail salaryDetail);

    /**对某员工某月的数据进行更新*/
    void updateSalaryDetail(SalaryDetail salaryDetail);

    /**根据员工ID和日期进行查询*/
    List<SalaryDetail> getSalaryDetailByIDAndDate(String id, Date date);

    /**根据日期删除已经存在的月份数据*/
    int deleteSalaryRecordExist(Date date);

    /**获取起止日期之间的工资数据*/
    List<SalaryDetail> getSalaryByDateRange(Date start, Date end);

}

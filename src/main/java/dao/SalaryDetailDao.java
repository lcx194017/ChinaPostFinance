package dao;

import domain.SalaryDetail;

import java.util.List;

/**
 * @author lcx :liu.changxin@qq.com
 * @data 2018/10/3 14:12
 */
public interface SalaryDetailDao {
    void insertSalaryDetail(SalaryDetail salaryDetail);

    void insertSalaryDetailBatch(List<SalaryDetail> list);
}

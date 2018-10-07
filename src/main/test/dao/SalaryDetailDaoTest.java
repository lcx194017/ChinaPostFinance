package dao;

import domain.SalaryDetail;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Date;
import java.util.List;

import static org.junit.Assert.*;

/**
 * @author lcx :liu.changxin@qq.com
 * @data 2018/10/6 15:02
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath*:/smart-context.xml")

public class SalaryDetailDaoTest {

    @Autowired
    private SalaryDetailDao salaryDetailDao;

    @Test
    public void insertSalaryDetail() {
    }

    @Test
    public void insertSalaryDetailBatch() {
    }

    @Test
    public void deleteSalaryRecordExist() {
    }

    @Test
    public void getSalaryDetailByIDAndDate() {
        String id = "321082197809213142";
        Date date = new Date(2018 - 1900, 9,1);
        List<SalaryDetail> list = salaryDetailDao.getSalaryDetailByIDAndDate(id, date);
        System.out.println(list.size());
    }

    @Test
    public void getSalaryByDateRange() {
        Date date1 = new Date(2018 - 1900, 9,1);
        Date date2 = new Date(2018 - 1900, 9,1);
        List<SalaryDetail>list = salaryDetailDao.getSalaryByDateRange(date1, date2);
        System.out.println(list.size());
    }
}
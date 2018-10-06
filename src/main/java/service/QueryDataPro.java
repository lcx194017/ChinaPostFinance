package service;

/**
 * @author lcx :liu.changxin@qq.com
 * @data 2018/10/3 16:48
 */

import domain.Employee;
import domain.SalaryDetail;

import java.util.List;

/**
 * 对查询的数据集进行处理
 */
public class QueryDataPro {
    private final static String[] EMPLOYEE_TABLE_ITEM = {"员工身份证", "姓名", "职务级别",
            "部门", "班组", "岗位", "环节", "日期"};

    private final static String[] SALARYDETAIL_TABLE_ITEM = {
            "员工身份证", "工资日期", "基本工资", "月绩效", "职业资格等级津贴", "专业技术职务津贴",
            "专家津贴", "班组长津贴", "综合补贴", "加班费", "夜班费", "通信补贴", "交通补贴",
            "车辆公里数补贴", "餐费", "房补", "补发补退", "过节费", "劳动竞赛", "业务发展奖",
            "质效奖励", "季奖", "中心奖励", "高温费", "稳岗补贴", "绩效兑现", "职工教育经费",
            "福利费", "其他", "应发合计", "企业承担住房公积金", "企业承担各类保险", "工会经费",
            "外包人员管理费", "外包人员税费", "人工成本"
    };

    private static boolean[] employee_item_mark = new boolean[EMPLOYEE_TABLE_ITEM.length];
    private static boolean[] salaryDetail_item_mark = new boolean[SALARYDETAIL_TABLE_ITEM.length];

    /**
     * Employee表中只有岗位数据可能为空
     */
    private static void getEmployeeValidData(List<Employee> list) {
        for (int i = 0; i < employee_item_mark.length; i++)
            employee_item_mark[i] = true;
        int count = 0;

        for (Employee employee : list) {
            if (employee.getPost() != null)
                break;
            else
                count++;
        }
        if (count == list.size())
            employee_item_mark[5] = false;
    }

    private static void getSalaryDetailValidData(List<SalaryDetail> list) {
        int[] item_count = new int[SALARYDETAIL_TABLE_ITEM.length];
        for (SalaryDetail salaryDetail : list) {
            if (salaryDetail.getId() == null)
                item_count[0]++;
            if (salaryDetail.getPay_date() == null)
                item_count[1]++;
            if (salaryDetail.getBase_pay() == 0)
                item_count[2]++;
            if (salaryDetail.getMonthly_performance() == 0)
                item_count[3]++;
            if (salaryDetail.getProfessional_qualification_allowance() == 0)
                item_count[4]++;
            if (salaryDetail.getTechnical_post_allowance() == 0)
                item_count[5]++;
            if (salaryDetail.getExpert_allowance() == 0)
                item_count[6]++;
            if (salaryDetail.getClass_leader_allowance() == 0)
                item_count[7]++;
            if (salaryDetail.getComprehensive_subsidy() == 0)
                item_count[8]++;
            if (salaryDetail.getOvertime_pay() == 0)
                item_count[9]++;
            if (salaryDetail.getNight_shift_fee() == 0)
                item_count[10]++;
            if (salaryDetail.getCommunication_subsidy() == 0)
                item_count[11]++;
            if (salaryDetail.getTraffic_allowance() == 0)
                item_count[12]++;
            if (salaryDetail.getVehicle_kilometres_subsidy() == 0)
                item_count[13]++;
            if (salaryDetail.getMeal_fee() == 0)
                item_count[14]++;
            if (salaryDetail.getHousing_supplement() == 0)
                item_count[15]++;
            if (salaryDetail.getRecharge_supplement() == 0)
                item_count[16]++;
            if (salaryDetail.getFestival_fee() == 0)
                item_count[17]++;
            if (salaryDetail.getLabor_competition() == 0)
                item_count[18]++;
            if (salaryDetail.getBusiness_development_award() == 0)
                item_count[19]++;
            if (salaryDetail.getQuality_reward() == 0)
                item_count[20]++;
            if (salaryDetail.getSeason_award() == 0)
                item_count[21]++;
            if (salaryDetail.getCentral_reward() == 0)
                item_count[22]++;
            if (salaryDetail.getHigh_temperature_fee() == 0)
                item_count[23]++;
            if (salaryDetail.getSteady_post_subsidy() == 0)
                item_count[24]++;
            if (salaryDetail.getPerformance_cashing() == 0)
                item_count[25]++;
            if (salaryDetail.getStaff_training_expense() == 0)
                item_count[26]++;
            if (salaryDetail.getWelfare_funds() == 0)
                item_count[27]++;
            if (salaryDetail.getOther() == 0)
                item_count[28]++;
            if (salaryDetail.getJoint_plan() == 0)
                item_count[29]++;
            if (salaryDetail.getEnterprises_undertake_housing_provident() == 0)
                item_count[30]++;
            if (salaryDetail.getEnterprises_undertake_various_insurance() == 0)
                item_count[31]++;
            if (salaryDetail.getTrade_union_funds() == 0)
                item_count[32]++;
            if (salaryDetail.getOutsourced_personnel_management_fee() == 0)
                item_count[33]++;
            if (salaryDetail.getOutsourced_personnel_taxes_fee() == 0)
                item_count[34]++;
            if (salaryDetail.getLabor_cost() == 0)
                item_count[35]++;

            for (int i = 0; i < item_count.length; i++)
                if (item_count[i] == list.size())
                    salaryDetail_item_mark[i] = false;
                else
                    salaryDetail_item_mark[i] = true;
        }
    }

    /**功能说明：根据mark将Employee的list数据集合剔除共同无用项，转化为Json格式*/

    /**功能说明：根据mark将SalaryDetail的list数据集合剔除共同无用项，转化为Json格式*/
}

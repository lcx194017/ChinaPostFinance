package controller;

/**
 * @author lcx :liu.changxin@qq.com
 * @data 2018/10/3 16:48
 */

import domain.Employee;
import domain.SalaryDetail;

import java.util.List;

/**
 * 对查询的数据集进行处理，并生成Json格式的数据
 */
public class QueryDataHandle {
    private final static String[] EMPLOYEE_TABLE_ITEM = {"身份证号码", "姓名", "职务",
            "部门", "班组", "岗位", "生产环节", "日期"};

    private final static String[] SALARY_DETAIL_TABLE_ITEM = {
            "员工身份证", "工资日期", "基本工资", "月绩效", "职业资格等级津贴", "专业技术职务津贴",
            "专家津贴", "班组长津贴", "综合补贴", "加班费", "夜班费", "通信补贴", "交通补贴",
            "车辆公里数补贴", "餐费", "房补", "补发补退", "过节费", "劳动竞赛", "业务发展奖",
            "质效奖励", "季奖", "中心奖励", "高温费", "稳岗补贴", "绩效兑现", "职工教育经费",
            "福利费", "其他", "应发合计", "企业承担住房公积金", "企业承担各类保险", "工会经费",
            "外包人员管理费", "外包人员税费", "人工成本"
    };

    private static boolean[] employee_item_mark = new boolean[EMPLOYEE_TABLE_ITEM.length];
    private static boolean[] salaryDetail_item_mark = new boolean[SALARY_DETAIL_TABLE_ITEM.length];

    /**
     * Employee表中只有岗位数据可能为空
     */
    public static void getEmployeeValidData(List<Employee> list) {
        for (int i = 0; i < employee_item_mark.length; i++)
            employee_item_mark[i] = true;

        int count = 0;

        /**employee表中只有职务(level)可能为空*/
        for (Employee employee : list) {
            if (employee.getLevel() != null)
                break;
            else
                count++;
        }
        if (count == list.size())
            employee_item_mark[2] = false;
    }

    public static void getSalaryDetailValidData(List<SalaryDetail> list) {
        int[] item_count = new int[SALARY_DETAIL_TABLE_ITEM.length];
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

    /**
     * 功能说明：根据mark将Employee和SalaryDetail的list数据集合
     * 的共同无用项剔除，并转化为Json格式
     */
    public static String jsonDataGenerate(List<Employee> employeeList,
                                          List<SalaryDetail> salaryDetailList) {

        if (employeeList == null || salaryDetailList == null)
            return "";
        if (employeeList.size() != salaryDetailList.size())
            return "";

        StringBuilder json_data = new StringBuilder();
        /**Json数据头部*/
        json_data.append("{\"records\": [");

        /**JSON数据体，由有效数据动态生成*/
        int length = employeeList.size();
        for (int i = 0; i < length; i++) {
            json_data.append("{");

            Employee employee = employeeList.get(i);
            SalaryDetail salaryDetail = salaryDetailList.get(i);

            for (int j = 0; j < employee_item_mark.length; j++) {
                if (employee_item_mark[j]) {
                    json_data.append("\"").append(EMPLOYEE_TABLE_ITEM[j]).append("\":");
                    switch (j) {
                        case 0:
                            if (null != employee.getId())
                                json_data.append("\"").append(employee.getId()).append("\",");
                            else
                                json_data.append(employee.getId()).append(",");
                            break;
                        case 1:
                            if (null != employee.getName())
                                json_data.append("\"").append(employee.getName()).append("\",");
                            else
                                json_data.append(employee.getName()).append(",");
                            break;
                        case 2:
                            if (null != employee.getLevel())
                                json_data.append("\"").append(employee.getLevel()).append("\",");
                            else
                                json_data.append(employee.getLevel()).append(",");
                            break;
                        case 3:
                            if (null != employee.getDepartment())
                                json_data.append("\"").append(employee.getDepartment()).append("\",");
                            else
                                json_data.append(employee.getDepartment()).append(",");
                            break;
                        case 4:
                            if (null != employee.getGroup())
                                json_data.append("\"").append(employee.getGroup()).append("\",");
                            else
                                json_data.append(employee.getGroup()).append(",");
                            break;
                        case 5:
                            if (null != employee.getPost())
                                json_data.append("\"").append(employee.getPost()).append("\",");
                            else
                                json_data.append(employee.getPost()).append(",");
                            break;
                        case 6:
                            if (null != employee.getProduction_link())
                                json_data.append("\"").append(employee.getProduction_link()).append("\",");
                            else
                                json_data.append(employee.getProduction_link()).append(",");
                            break;
                        case 7:
                            if (null != employee.getExpiration_date())
                                json_data.append("\"").append(employee.getExpiration_date()).append("\",");
                            else
                                json_data.append(employee.getExpiration_date()).append(",");
                            break;
                    }
                }
            }

            for (int j = 2; j < salaryDetail_item_mark.length; j++) {
                if (salaryDetail_item_mark[j]) {
                    json_data.append("\"").append(SALARY_DETAIL_TABLE_ITEM[j]).append("\":");
                    switch (j) {
                        case 2:
                            json_data.append(salaryDetail.getBase_pay()).append(",");
                            break;
                        case 3:
                            json_data.append(salaryDetail.getMonthly_performance()).append(",");
                            break;
                        case 4:
                            json_data.append(salaryDetail.getProfessional_qualification_allowance()).append(",");
                            break;
                        case 5:
                            json_data.append(salaryDetail.getTechnical_post_allowance()).append(",");
                            break;
                        case 6:
                            json_data.append(salaryDetail.getExpert_allowance()).append(",");
                            break;
                        case 7:
                            json_data.append(salaryDetail.getClass_leader_allowance()).append(",");
                            break;
                        case 8:
                            json_data.append(salaryDetail.getComprehensive_subsidy()).append(",");
                            break;
                        case 9:
                            json_data.append(salaryDetail.getOvertime_pay()).append(",");
                            break;
                        case 10:
                            json_data.append(salaryDetail.getNight_shift_fee()).append(",");
                            break;
                        case 11:
                            json_data.append(salaryDetail.getCommunication_subsidy()).append(",");
                            break;
                        case 12:
                            json_data.append(salaryDetail.getTraffic_allowance()).append(",");
                            break;
                        case 13:
                            json_data.append(salaryDetail.getVehicle_kilometres_subsidy()).append(",");
                            break;
                        case 14:
                            json_data.append(salaryDetail.getMeal_fee()).append(",");
                            break;
                        case 15:
                            json_data.append(salaryDetail.getHousing_supplement()).append(",");
                            break;
                        case 16:
                            json_data.append(salaryDetail.getRecharge_supplement()).append(",");
                            break;
                        case 17:
                            json_data.append(salaryDetail.getFestival_fee()).append(",");
                            break;
                        case 18:
                            json_data.append(salaryDetail.getLabor_competition()).append(",");
                            break;
                        case 19:
                            json_data.append(salaryDetail.getBusiness_development_award()).append(",");
                            break;
                        case 20:
                            json_data.append(salaryDetail.getQuality_reward()).append(",");
                            break;
                        case 21:
                            json_data.append(salaryDetail.getSeason_award()).append(",");
                            break;
                        case 22:
                            json_data.append(salaryDetail.getCentral_reward()).append(",");
                            break;
                        case 23:
                            json_data.append(salaryDetail.getHigh_temperature_fee()).append(",");
                            break;
                        case 24:
                            json_data.append(salaryDetail.getSteady_post_subsidy()).append(",");
                            break;
                        case 25:
                            json_data.append(salaryDetail.getPerformance_cashing()).append(",");
                            break;
                        case 26:
                            json_data.append(salaryDetail.getStaff_training_expense()).append(",");
                            break;
                        case 27:
                            json_data.append(salaryDetail.getWelfare_funds()).append(",");
                            break;
                        case 28:
                            json_data.append(salaryDetail.getOther()).append(",");
                            break;
                        case 29:
                            json_data.append(salaryDetail.getJoint_plan()).append(",");
                            break;
                        case 30:
                            json_data.append(salaryDetail.getEnterprises_undertake_housing_provident()).append(",");
                            break;
                        case 31:
                            json_data.append(salaryDetail.getEnterprises_undertake_various_insurance()).append(",");
                            break;
                        case 32:
                            json_data.append(salaryDetail.getTrade_union_funds()).append(",");
                            break;
                        case 33:
                            json_data.append(salaryDetail.getOutsourced_personnel_management_fee()).append(",");
                            break;
                        case 34:
                            json_data.append(salaryDetail.getOutsourced_personnel_taxes_fee()).append(",");
                            break;
                        case 35:
                            json_data.append(salaryDetail.getLabor_cost()).append(",");
                            break;
                    }
                }
            }
            json_data.deleteCharAt(json_data.length() - 1);

            json_data.append("},");
        }
        /**JSON数据尾部*/
        json_data.deleteCharAt(json_data.length() - 1);
        json_data.append("]}");
        return json_data.toString();
    }

}

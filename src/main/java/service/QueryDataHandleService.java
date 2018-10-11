package service;

/**
 * @author lcx :liu.changxin@qq.com
 * @data 2018/10/3 16:48
 */

import domain.Employee;
import others.LinkCostDetailTable;
import others.LinkCostStatistics;
import domain.SalaryDetail;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * 对查询的数据集进行处理，并生成指定格式的数据(Json)
 */
@Service
public class QueryDataHandleService {

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

    private boolean[] employee_item_mark = new boolean[EMPLOYEE_TABLE_ITEM.length];
    private boolean[] salaryDetail_item_mark = new boolean[SALARY_DETAIL_TABLE_ITEM.length];

    private SalaryDetail total_statistics;
    private Set<String> person_statistics = new HashSet<>();  //用于人数统计，计算平均工资用

    private void init_params() {
        Arrays.fill(employee_item_mark, false);
        Arrays.fill(salaryDetail_item_mark, false);
        total_statistics = new SalaryDetail();
        person_statistics.clear();
    }


    private void getEmployeeValidData(List<Employee> list) {
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

    private void getSalaryDetailValidData(List<SalaryDetail> list) {
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

    private void calculate_statistical_info(List<SalaryDetail> salaryDetailList) {
        for (SalaryDetail salaryDetail : salaryDetailList) {
            total_statistics.setBase_pay(total_statistics.getBase_pay() + salaryDetail.getBase_pay());
            total_statistics.setMonthly_performance(total_statistics.getMonthly_performance()
                    + salaryDetail.getMonthly_performance());
            total_statistics.setProfessional_qualification_allowance(
                    total_statistics.getProfessional_qualification_allowance()
                            + salaryDetail.getProfessional_qualification_allowance());
            total_statistics.setTechnical_post_allowance(total_statistics.getTechnical_post_allowance()
                    + salaryDetail.getTechnical_post_allowance());
            total_statistics.setExpert_allowance(total_statistics.getExpert_allowance()
                    + salaryDetail.getExpert_allowance());
            total_statistics.setClass_leader_allowance(total_statistics.getClass_leader_allowance()
                    + salaryDetail.getClass_leader_allowance());
            total_statistics.setComprehensive_subsidy(total_statistics.getComprehensive_subsidy()
                    + salaryDetail.getComprehensive_subsidy());
            total_statistics.setOvertime_pay(total_statistics.getOvertime_pay()
                    + salaryDetail.getOvertime_pay());
            total_statistics.setNight_shift_fee(total_statistics.getNight_shift_fee()
                    + salaryDetail.getNight_shift_fee());
            total_statistics.setCommunication_subsidy(total_statistics.getCommunication_subsidy()
                    + salaryDetail.getCommunication_subsidy());
            total_statistics.setTraffic_allowance(total_statistics.getTraffic_allowance()
                    + salaryDetail.getTraffic_allowance());
            total_statistics.setVehicle_kilometres_subsidy(total_statistics.getVehicle_kilometres_subsidy()
                    + salaryDetail.getVehicle_kilometres_subsidy());
            total_statistics.setMeal_fee(total_statistics.getMeal_fee()
                    + salaryDetail.getMeal_fee());
            total_statistics.setHousing_supplement(total_statistics.getHousing_supplement()
                    + salaryDetail.getHousing_supplement());
            total_statistics.setRecharge_supplement(total_statistics.getRecharge_supplement()
                    + salaryDetail.getRecharge_supplement());
            total_statistics.setFestival_fee(total_statistics.getFestival_fee()
                    + salaryDetail.getFestival_fee());
            total_statistics.setLabor_competition(total_statistics.getLabor_competition()
                    + salaryDetail.getLabor_competition());
            total_statistics.setBusiness_development_award(total_statistics.getBusiness_development_award()
                    + salaryDetail.getBusiness_development_award());
            total_statistics.setQuality_reward(total_statistics.getQuality_reward()
                    + salaryDetail.getQuality_reward());
            total_statistics.setSeason_award(total_statistics.getSeason_award()
                    + salaryDetail.getSeason_award());
            total_statistics.setCentral_reward(total_statistics.getCentral_reward()
                    + salaryDetail.getCentral_reward());
            total_statistics.setHigh_temperature_fee(total_statistics.getHigh_temperature_fee()
                    + salaryDetail.getHigh_temperature_fee());
            total_statistics.setSteady_post_subsidy(total_statistics.getSteady_post_subsidy()
                    + salaryDetail.getSteady_post_subsidy());
            total_statistics.setPerformance_cashing(total_statistics.getPerformance_cashing()
                    + salaryDetail.getPerformance_cashing());
            total_statistics.setStaff_training_expense(total_statistics.getStaff_training_expense()
                    + salaryDetail.getStaff_training_expense());
            total_statistics.setWelfare_funds(total_statistics.getWelfare_funds()
                    + salaryDetail.getWelfare_funds());
            total_statistics.setOther(total_statistics.getOther() + salaryDetail.getOther());
            total_statistics.setJoint_plan(total_statistics.getJoint_plan() + salaryDetail.getJoint_plan());
            total_statistics.setEnterprises_undertake_housing_provident(
                    total_statistics.getEnterprises_undertake_housing_provident()
                            + salaryDetail.getEnterprises_undertake_housing_provident());
            total_statistics.setEnterprises_undertake_various_insurance(
                    total_statistics.getEnterprises_undertake_various_insurance()
                            + salaryDetail.getEnterprises_undertake_various_insurance());
            total_statistics.setTrade_union_funds(total_statistics.getTrade_union_funds()
                    + salaryDetail.getTrade_union_funds());
            total_statistics.setOutsourced_personnel_management_fee(
                    total_statistics.getOutsourced_personnel_management_fee()
                            + salaryDetail.getOutsourced_personnel_management_fee());
            total_statistics.setOutsourced_personnel_taxes_fee(
                    total_statistics.getOutsourced_personnel_taxes_fee()
                            + salaryDetail.getOutsourced_personnel_taxes_fee());
            total_statistics.setLabor_cost(total_statistics.getLabor_cost()
                    + salaryDetail.getLabor_cost());

        }
    }

    private StringBuilder generateHeaderJsonData() {
        StringBuilder header_json_data = new StringBuilder();
        header_json_data.append("\"header\": [");
        for (int j = 0; j < employee_item_mark.length; j++)
            if (employee_item_mark[j])
                header_json_data.append("\"").append(EMPLOYEE_TABLE_ITEM[j]).append("\",");
        for (int j = 2; j < salaryDetail_item_mark.length; j++)
            if (salaryDetail_item_mark[j])
                header_json_data.append("\"").append(SALARY_DETAIL_TABLE_ITEM[j]).append("\",");
        header_json_data.deleteCharAt(header_json_data.length() - 1);
        header_json_data.append("],");
        return header_json_data;
    }

    private StringBuilder generateEmployeeJsonData(Employee employee) {
        StringBuilder single_employee_json_data = new StringBuilder();

        for (int j = 0; j < employee_item_mark.length; j++) {
            if (employee_item_mark[j]) {
                single_employee_json_data.append("\"").append(EMPLOYEE_TABLE_ITEM[j]).append("\":");
                switch (j) {
                    case 0:
                        if (null != employee.getId()) {
                            single_employee_json_data.append("\"").append(employee.getId()).append("\",");
                            //统计一组查询记录中包含人数(注意不是记录数)
                            person_statistics.add(employee.getId());
                        } else
                            single_employee_json_data.append(employee.getId()).append(",");
                        break;
                    case 1:
                        if (null != employee.getName())
                            single_employee_json_data.append("\"").append(employee.getName()).append("\",");
                        else
                            single_employee_json_data.append(employee.getName()).append(",");
                        break;
                    case 2:
                        if (null != employee.getLevel())
                            single_employee_json_data.append("\"").append(employee.getLevel()).append("\",");
                        else
                            single_employee_json_data.append(employee.getLevel()).append(",");
                        break;
                    case 3:
                        if (null != employee.getDepartment())
                            single_employee_json_data.append("\"").append(employee.getDepartment()).append("\",");
                        else
                            single_employee_json_data.append(employee.getDepartment()).append(",");
                        break;
                    case 4:
                        if (null != employee.getGroup())
                            single_employee_json_data.append("\"").append(employee.getGroup()).append("\",");
                        else
                            single_employee_json_data.append(employee.getGroup()).append(",");
                        break;
                    case 5:
                        if (null != employee.getPost())
                            single_employee_json_data.append("\"").append(employee.getPost()).append("\",");
                        else
                            single_employee_json_data.append(employee.getPost()).append(",");
                        break;
                    case 6:
                        if (null != employee.getProduction_link())
                            single_employee_json_data.append("\"").append(employee.getProduction_link()).append("\",");
                        else
                            single_employee_json_data.append(employee.getProduction_link()).append(",");
                        break;
                    case 7:
                        if (null != employee.getExpiration_date())
                            single_employee_json_data.append("\"").append(employee.getExpiration_date()).append("\",");
                        else
                            single_employee_json_data.append(employee.getExpiration_date()).append(",");
                        break;
                }
            }
        }
        return single_employee_json_data;
    }

    private StringBuilder generateSalaryDetailJsonData(SalaryDetail salaryDetail, int counts) {

        StringBuilder single_salary_detail_json_data = new StringBuilder();

        for (int j = 2; j < salaryDetail_item_mark.length; j++) {
            if (salaryDetail_item_mark[j]) {
                single_salary_detail_json_data.append("\"").append(SALARY_DETAIL_TABLE_ITEM[j]).append("\":");
                switch (j) {
                    case 2:
                        single_salary_detail_json_data.append(salaryDetail.getBase_pay() / counts).append(",");
                        break;
                    case 3:
                        single_salary_detail_json_data.append(salaryDetail.getMonthly_performance() / counts).append(",");
                        break;
                    case 4:
                        single_salary_detail_json_data.append(salaryDetail.getProfessional_qualification_allowance() / counts).append(",");
                        break;
                    case 5:
                        single_salary_detail_json_data.append(salaryDetail.getTechnical_post_allowance() / counts).append(",");
                        break;
                    case 6:
                        single_salary_detail_json_data.append(salaryDetail.getExpert_allowance() / counts).append(",");
                        break;
                    case 7:
                        single_salary_detail_json_data.append(salaryDetail.getClass_leader_allowance() / counts).append(",");
                        break;
                    case 8:
                        single_salary_detail_json_data.append(salaryDetail.getComprehensive_subsidy() / counts).append(",");
                        break;
                    case 9:
                        single_salary_detail_json_data.append(salaryDetail.getOvertime_pay() / counts).append(",");
                        break;
                    case 10:
                        single_salary_detail_json_data.append(salaryDetail.getNight_shift_fee() / counts).append(",");
                        break;
                    case 11:
                        single_salary_detail_json_data.append(salaryDetail.getCommunication_subsidy() / counts).append(",");
                        break;
                    case 12:
                        single_salary_detail_json_data.append(salaryDetail.getTraffic_allowance() / counts).append(",");
                        break;
                    case 13:
                        single_salary_detail_json_data.append(salaryDetail.getVehicle_kilometres_subsidy() / counts).append(",");
                        break;
                    case 14:
                        single_salary_detail_json_data.append(salaryDetail.getMeal_fee() / counts).append(",");
                        break;
                    case 15:
                        single_salary_detail_json_data.append(salaryDetail.getHousing_supplement() / counts).append(",");
                        break;
                    case 16:
                        single_salary_detail_json_data.append(salaryDetail.getRecharge_supplement() / counts).append(",");
                        break;
                    case 17:
                        single_salary_detail_json_data.append(salaryDetail.getFestival_fee() / counts).append(",");
                        break;
                    case 18:
                        single_salary_detail_json_data.append(salaryDetail.getLabor_competition() / counts).append(",");
                        break;
                    case 19:
                        single_salary_detail_json_data.append(salaryDetail.getBusiness_development_award() / counts).append(",");
                        break;
                    case 20:
                        single_salary_detail_json_data.append(salaryDetail.getQuality_reward() / counts).append(",");
                        break;
                    case 21:
                        single_salary_detail_json_data.append(salaryDetail.getSeason_award() / counts).append(",");
                        break;
                    case 22:
                        single_salary_detail_json_data.append(salaryDetail.getCentral_reward() / counts).append(",");
                        break;
                    case 23:
                        single_salary_detail_json_data.append(salaryDetail.getHigh_temperature_fee() / counts).append(",");
                        break;
                    case 24:
                        single_salary_detail_json_data.append(salaryDetail.getSteady_post_subsidy() / counts).append(",");
                        break;
                    case 25:
                        single_salary_detail_json_data.append(salaryDetail.getPerformance_cashing() / counts).append(",");
                        break;
                    case 26:
                        single_salary_detail_json_data.append(salaryDetail.getStaff_training_expense() / counts).append(",");
                        break;
                    case 27:
                        single_salary_detail_json_data.append(salaryDetail.getWelfare_funds() / counts).append(",");
                        break;
                    case 28:
                        single_salary_detail_json_data.append(salaryDetail.getOther() / counts).append(",");
                        break;
                    case 29:
                        single_salary_detail_json_data.append(salaryDetail.getJoint_plan() / counts).append(",");
                        break;
                    case 30:
                        single_salary_detail_json_data.append(salaryDetail.getEnterprises_undertake_housing_provident() / counts).append(",");
                        break;
                    case 31:
                        single_salary_detail_json_data.append(salaryDetail.getEnterprises_undertake_various_insurance() / counts).append(",");
                        break;
                    case 32:
                        single_salary_detail_json_data.append(salaryDetail.getTrade_union_funds() / counts).append(",");
                        break;
                    case 33:
                        single_salary_detail_json_data.append(salaryDetail.getOutsourced_personnel_management_fee() / counts).append(",");
                        break;
                    case 34:
                        single_salary_detail_json_data.append(salaryDetail.getOutsourced_personnel_taxes_fee() / counts).append(",");
                        break;
                    case 35:
                        single_salary_detail_json_data.append(salaryDetail.getLabor_cost() / counts).append(",");
                        break;
                }
            }
        }
        return single_salary_detail_json_data;
    }

    /**
     * @Description: 根据mark将Employee和SalaryDetail的list数据集合的共同无用项剔除，并转化为Json格式
     * @Param: [employeeList, salaryDetailList]
     * @return: java.lang.String
     * @Author: lcx
     * @Date: 2018/10/8
     */
    public String generateOperationAreaJsonData(List<Employee> employeeList, List<SalaryDetail> salaryDetailList) {

        if (employeeList == null || salaryDetailList == null)
            return "";
        if (employeeList.size() != salaryDetailList.size())
            return "";

        init_params();     //参数初始化

        getEmployeeValidData(employeeList);

        getSalaryDetailValidData(salaryDetailList);

        //计算工资的统计信息
        calculate_statistical_info(salaryDetailList);

        StringBuilder json_data = new StringBuilder();

        //Json数据开始
        json_data.append("{");

        //Json数据表头
        json_data.append(generateHeaderJsonData());

        /**JSON数据体，由有效数据动态生成*/
        json_data.append("\"records\": [");

        int length = employeeList.size();

        for (int i = 0; i < length; i++) {
            json_data.append("{");

            Employee employee = employeeList.get(i);
            SalaryDetail salaryDetail = salaryDetailList.get(i);

            /**将一个employee中的有效数据转化成json*/
            json_data.append(generateEmployeeJsonData(employee));

            /**将一个salaryDetail中的有效数组转化为json*/
            json_data.append(generateSalaryDetailJsonData(salaryDetail, 1));

            json_data.deleteCharAt(json_data.length() - 1);

            json_data.append("},");
        }

        /**JSON数组数据尾部*/
        if (length != 0)
            json_data.deleteCharAt(json_data.length() - 1);
        json_data.append("],");

        //统计总计的Json数据
        json_data.append("\"statistics\":{ ");
        json_data.append(generateEmployeeJsonData(new Employee()));
        json_data.append(generateSalaryDetailJsonData(total_statistics, 1));
        json_data.deleteCharAt(json_data.length() - 1);
        json_data.append("},");

        //统计平均的Json数据
        json_data.append("\"average\":{");
        json_data.append(generateEmployeeJsonData(new Employee()));

        //如果人数少于1人，不计算平均数据
        if (person_statistics.size() > 0) {
            json_data.append(generateSalaryDetailJsonData(total_statistics, person_statistics.size()));
            json_data.deleteCharAt(json_data.length() - 1);
        }

        json_data.append("}");

        /**Json整体数据尾部*/
        json_data.append("}");

        return json_data.toString();
    }

    /**
     * @Description: 按生产环节对成本进行均摊，并返回
     * @Param: [employeeList, salaryDetailList, operation_link_list]
     * @return: java.lang.String
     * @Author: lcx
     * @Date: 2018/10/8
     */
    public String generateOperationLinkJsonData(List<Employee> employeeList,
                                                List<SalaryDetail> salaryDetailList,
                                                List<String> flat,
                                                List<String> air,
                                                List<String> ground) {
        if (employeeList == null || salaryDetailList == null)
            return "";
        if (employeeList.size() != salaryDetailList.size())
            return "";

        HashMap<String, LinkCostStatistics> linkCostStatisticsHashMap = new HashMap<>();
        for (String link : flat) {
            LinkCostStatistics linkCostStatistics = new LinkCostStatistics();
            linkCostStatistics.setDepartment("扁平件邮件作业区");
            linkCostStatistics.setProduction_link(link);
            linkCostStatisticsHashMap.put("扁平件邮件作业区" + link, linkCostStatistics);
        }
        for (String link : air) {
            LinkCostStatistics linkCostStatistics = new LinkCostStatistics();
            linkCostStatistics.setDepartment("空侧邮件作业区");
            linkCostStatistics.setProduction_link(link);
            linkCostStatisticsHashMap.put("空侧邮件作业区" + link, linkCostStatistics);
        }
        for (String link : ground) {
            LinkCostStatistics linkCostStatistics = new LinkCostStatistics();
            linkCostStatistics.setDepartment("陆侧邮件作业区");
            linkCostStatistics.setProduction_link(link);
            linkCostStatisticsHashMap.put("陆侧邮件作业区" + link, linkCostStatistics);
        }

        //对查询的每条数据进行人工成本分摊
        for (int i = 0; i < employeeList.size(); i++) {
            Employee employee = employeeList.get(i);
            SalaryDetail salaryDetail = salaryDetailList.get(i);

            String department = employee.getDepartment();
            String production_link = employee.getProduction_link();
            int links_count = production_link.split("、").length;  //单个员工涉及的环节数
            double labor_cost = salaryDetail.getLabor_cost();              //单个员工的人工成本

            if (department.equals("扁平件邮件作业区"))
                for (String link : flat) {
                    if (production_link.contains(link)) {
                        LinkCostStatistics linkCostStatistics = linkCostStatisticsHashMap.get("扁平件邮件作业区" + link);
                        linkCostStatistics.setDepartment(department);
                        linkCostStatistics.setCount(linkCostStatistics.getCount() + 1);
                        linkCostStatistics.setTotal_cost(linkCostStatistics.getTotal_cost() + labor_cost / links_count);
                    }
                }
            else if (department.equals("空侧邮件作业区"))
                for (String link : air) {
                    if (production_link.contains(link)) {
                        LinkCostStatistics linkCostStatistics = linkCostStatisticsHashMap.get("空侧邮件作业区" + link);
                        linkCostStatistics.setDepartment(department);
                        linkCostStatistics.setCount(linkCostStatistics.getCount() + 1);
                        linkCostStatistics.setTotal_cost(linkCostStatistics.getTotal_cost() + labor_cost / links_count);
                    }
                }
            else if (department.equals("陆侧邮件作业区"))
                for (String link : ground) {
                    if (production_link.contains(link)) {
                        LinkCostStatistics linkCostStatistics = linkCostStatisticsHashMap.get("陆侧邮件作业区" + link);
                        linkCostStatistics.setDepartment(department);
                        linkCostStatistics.setCount(linkCostStatistics.getCount() + 1);
                        linkCostStatistics.setTotal_cost(linkCostStatistics.getTotal_cost() + labor_cost / links_count);
                    }
                }
        }

        StringBuilder json_data = new StringBuilder();
        json_data.append("{\"statistics\":[");

        Set<String> key_set = linkCostStatisticsHashMap.keySet();

        for (String link : key_set) {
            LinkCostStatistics single = linkCostStatisticsHashMap.get(link);
            json_data.append("{");

            json_data.append("\"责任中心\":");
            if (null != single.getDepartment())
                json_data.append("\"").append(single.getDepartment()).append("\",");
            else
                json_data.append(single.getDepartment()).append(",");

            json_data.append("\"生产环节\":");
            if (null != single.getProduction_link())
                json_data.append("\"").append(single.getProduction_link()).append("\",");
            else
                json_data.append(single.getProduction_link()).append(",");

            json_data.append("\"人数\":");
            json_data.append(single.getCount()).append(",");

            json_data.append("\"总成本\":");
            json_data.append(single.getTotal_cost()).append("},");
        }

        json_data.deleteCharAt(json_data.length() - 1);

        json_data.append("]}");

        return json_data.toString();
    }

    /**
     * @Description: 生成生产环节的详细数据
     * @Param: [employeeList, salaryDetailList]
     * @return: java.lang.String
     * @Author: lcx
     * @Date: 2018/10/9
     */
    public String generateOperationLinkDetailJsonData(List<Employee> employeeList,
                                                      List<SalaryDetail> salaryDetailList) {
        if (employeeList == null || salaryDetailList == null)
            return "";
        if (employeeList.size() != salaryDetailList.size())
            return "";

        Map<String, LinkCostDetailTable> map = new HashMap<>();
        //对查询的每条数据进行人工成本分摊, 并用map进行统计(一个员工多个月的数据要统计成一条)
        for (int i = 0; i < employeeList.size(); i++) {
            Employee employee = employeeList.get(i);
            String id = employee.getId();
            String name = employee.getName();
            String real_production_link = employee.getProduction_link();        //获取员工实际的生产环节

            SalaryDetail salaryDetail = salaryDetailList.get(i);
            int links_count = real_production_link.split("、").length;   //单个员工涉及的环节数
            double labor_cost = salaryDetail.getLabor_cost();                   //单个员工的人工成本
            double cost_sharing = labor_cost / links_count;                     //单个员工的分摊成本

            if (map.containsKey(id)) {
                LinkCostDetailTable item = map.get(id);
                item.setCost_sharing(item.getCost_sharing() + cost_sharing);
                item.setTotal_cost(item.getTotal_cost() + labor_cost);
            } else {
                LinkCostDetailTable item = new LinkCostDetailTable();
                item.setId(id);
                item.setName(name);
                item.setCost_sharing(cost_sharing);
                item.setTotal_cost(labor_cost);
                map.put(id, item);
            }
        }

        StringBuilder json_data = new StringBuilder();
        json_data.append("{\"detail\":[");

        Set<String> keySet = map.keySet();
        for (String id : keySet) {
            json_data.append("{");
            json_data.append("\"身份证号码\":");
            json_data.append("\"").append(id).append("\",");
            json_data.append("\"姓名\":");
            json_data.append("\"").append(map.get(id).getName()).append("\",");
            json_data.append("\"本环节分摊成本\":");
            json_data.append("\"").append(map.get(id).getCost_sharing()).append("\",");
            json_data.append("\"总成本\":");
            json_data.append("\"").append(map.get(id).getTotal_cost()).append("\"},");
        }
        json_data.deleteCharAt(json_data.length() - 1);
        json_data.append("]}");
        return json_data.toString();
    }
}

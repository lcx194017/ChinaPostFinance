package service;

/**
 * @author lcx :liu.changxin@qq.com
 * @date 2018/10/3 16:48
 */

import domain.Employee;
import others.ExpenseDetail;
import others.LinkCostDetailTable;
import others.LinkCostStatistics;
import domain.SalaryDetail;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 对查询的数据集进行处理，并生成指定格式的数据(Json)
 */
@Service
public class QueryDataHandleService {

    private final static SimpleDateFormat format = new SimpleDateFormat("yyyy年MM月");

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

    //班组映射表，通过表找到数据库中信息到费用明细表的对应关系
    private final static Map<String, Integer> GROUP_MAP;
    private final static String[] GROUPS_NAME = {"接发班(空侧)", "人工处理班(空侧)", "封发班(空侧)",
            "运行安保组(空侧)", "白班(空侧)", "综合班(空侧)", "借调(空侧)", "转运班(陆侧)",
            "人工处理班(陆侧)", "封发班(陆侧)", "综合班(陆侧)", "借调(陆侧)", "分拣班(扁平)",
            "塑封班(扁平)", "人工处理班(扁平)", "综合班(扁平)", "借调(扁平)", "包分机班组(设备)",
            "胶带机班组(设备)", "扁平件班组(设备)", "信息班组(设备)", "库房班组(设备)", "综合班(设备)",
            "查验班组(运输)", "客服班组(运输)", "综合班(运输)", "综合办公室", "计划财务部",
            "人力资源部", "安全保卫部", "工会", "邮航", "战略责任中心"
    };

    private final static Map<String, Integer> DEPARTMENT_MAP;
    private final static String[] DEPARTMENTS_NAME = {
            "空侧邮件作业区", "陆侧邮件作业区", "扁平件邮件作业区", "技术保障部", "生产指挥调度中心",
            "综合办公室", "计划财务部", "人力资源部", "安全保卫部", "工会", "邮航", "战略"};

    private final static Map<String, Integer> LINK_MAP;
    private final static String[] LINKS_NAME = {
            "车辆引导", "邮件卸车", "邮件驳运", "邮件过检", "总包开拆", "邮件扫描封发",
            "二次供件", "本地信息处理", "质检巡查", "邮件装车发运", "人工处理", "分流",
            "综合", "网运信息处理", "调箱", "交接门洞接收集装箱", "掏箱", "安检机后分类传输",
            "空侧供件", "拒识邮件处理", "空侧封发装箱称重", "交邮", "收容处理", "人工处理",
            "信息处理", "运保", "综合", "白班", "信盒传输线巡视", "非标件收集", "供件",
            "辅助分拣", "分拣机巡视", "塑封操作", "空盒收集", "塑封机错包处理", "拒识邮件处理",
            "收容邮件处理", "分拣封发专项邮件处理", "接发", "牵引车驾驶", "质检", "白班", "综合"
    };

    static {
        GROUP_MAP = new HashMap<>();
        GROUP_MAP.put("空侧邮件作业区接发连班", 0);
        GROUP_MAP.put("空侧邮件作业区人工处理班", 1);
        GROUP_MAP.put("空侧邮件作业区封发连班", 2);
        GROUP_MAP.put("空侧邮件作业区运行安保组", 3);
        GROUP_MAP.put("空侧邮件作业区白班", 4);
        GROUP_MAP.put("空侧邮件作业区综合班", 5);
        GROUP_MAP.put("空侧邮件作业区借调", 6);

        GROUP_MAP.put("陆侧邮件作业区转运班", 7);
        GROUP_MAP.put("陆侧邮件作业区人工处理班", 8);
        GROUP_MAP.put("陆侧邮件作业区封发班", 9);
        GROUP_MAP.put("陆侧邮件作业区综合班", 10);
        GROUP_MAP.put("陆侧邮件作业区借调", 11);

        GROUP_MAP.put("扁平件侧邮件作业区分拣班", 12);
        GROUP_MAP.put("扁平件邮件作业区塑封班", 13);
        GROUP_MAP.put("扁平件邮件作业区人工处理班", 14);
        GROUP_MAP.put("扁平件邮件作业区综合班", 15);
        GROUP_MAP.put("扁平件邮件作业区借调", 16);

        GROUP_MAP.put("技术保障部包分机班组", 17);
        GROUP_MAP.put("技术保障部胶带机班组", 18);
        GROUP_MAP.put("技术保障部扁平件班组", 19);
        GROUP_MAP.put("技术保障部信息班组", 20);
        GROUP_MAP.put("技术保障部库房班组", 21);
        GROUP_MAP.put("技术保障部综合班", 22);

        GROUP_MAP.put("生产指挥调度中心查验班组", 23);
        GROUP_MAP.put("生产指挥调度中心客服班组", 24);
        GROUP_MAP.put("生产指挥调度中心综合班", 25);

        GROUP_MAP.put("综合办公室", 26);

        GROUP_MAP.put("计划财务部", 27);
        GROUP_MAP.put("人力资源部", 28);
        GROUP_MAP.put("安全保卫部", 29);
        GROUP_MAP.put("工会", 30);

        GROUP_MAP.put("邮航", 31);
        GROUP_MAP.put("战略责任中心", 32);

        DEPARTMENT_MAP = new HashMap<>();
        DEPARTMENT_MAP.put("空侧邮件作业区", 0);
        DEPARTMENT_MAP.put("陆侧邮件作业区", 1);
        DEPARTMENT_MAP.put("扁平件邮件作业区", 2);
        DEPARTMENT_MAP.put("技术保障部", 3);
        DEPARTMENT_MAP.put("生产指挥调度中心", 4);
        DEPARTMENT_MAP.put("综合办公室", 5);
        DEPARTMENT_MAP.put("计划财务部", 6);
        DEPARTMENT_MAP.put("人力资源部", 7);
        DEPARTMENT_MAP.put("安全保卫部", 8);
        DEPARTMENT_MAP.put("工会", 9);
        DEPARTMENT_MAP.put("邮航", 10);
        DEPARTMENT_MAP.put("战略", 11);

        LINK_MAP = new HashMap<>();
        LINK_MAP.put("陆侧邮件作业区车辆引导", 0);
        LINK_MAP.put("陆侧邮件作业区邮件卸车", 1);
        LINK_MAP.put("陆侧邮件作业区邮件驳运", 2);
        LINK_MAP.put("陆侧邮件作业区邮件过检", 3);
        LINK_MAP.put("陆侧邮件作业区总包开拆", 4);
        LINK_MAP.put("陆侧邮件作业区邮件扫描封发", 5);
        LINK_MAP.put("陆侧邮件作业区二次供件", 6);
        LINK_MAP.put("陆侧邮件作业区本地信息处理", 7);
        LINK_MAP.put("陆侧邮件作业区质检巡查", 8);
        LINK_MAP.put("陆侧邮件作业区邮件装车发运", 9);
        LINK_MAP.put("陆侧邮件作业区人工处理", 10);
        LINK_MAP.put("陆侧邮件作业区分流", 11);
        LINK_MAP.put("陆侧邮件作业区综合", 12);
        LINK_MAP.put("陆侧邮件作业区网运信息处理", 13);
        LINK_MAP.put("空侧邮件作业区调箱", 14);
        LINK_MAP.put("空侧邮件作业区交接门洞接收集装箱", 15);
        LINK_MAP.put("空侧邮件作业区掏箱", 16);
        LINK_MAP.put("空侧邮件作业区安检机后分类传输", 17);
        LINK_MAP.put("空侧邮件作业区空侧供件", 18);
        LINK_MAP.put("空侧邮件作业区拒识邮件处理", 19);
        LINK_MAP.put("空侧邮件作业区空侧封发装箱称重", 20);
        LINK_MAP.put("空侧邮件作业区交邮", 21);
        LINK_MAP.put("空侧邮件作业区收容处理", 22);
        LINK_MAP.put("空侧邮件作业区人工处理", 23);
        LINK_MAP.put("空侧邮件作业区信息处理", 24);
        LINK_MAP.put("空侧邮件作业区运保", 25);
        LINK_MAP.put("空侧邮件作业区综合", 26);
        LINK_MAP.put("空侧邮件作业区白班", 27);
        LINK_MAP.put("扁平件邮件作业区信盒传输线巡视", 28);
        LINK_MAP.put("扁平件邮件作业区非标件收集", 29);
        LINK_MAP.put("扁平件邮件作业区供件", 30);
        LINK_MAP.put("扁平件邮件作业区辅助分拣", 31);
        LINK_MAP.put("扁平件邮件作业区分拣机巡视", 32);
        LINK_MAP.put("扁平件邮件作业区塑封操作", 33);
        LINK_MAP.put("扁平件邮件作业区空盒收集", 34);
        LINK_MAP.put("扁平件邮件作业区塑封机错包处理", 35);
        LINK_MAP.put("扁平件邮件作业区拒识邮件处理", 36);
        LINK_MAP.put("扁平件邮件作业区收容邮件处理", 37);
        LINK_MAP.put("扁平件邮件作业区分拣封发专项邮件处理", 38);
        LINK_MAP.put("扁平件邮件作业区接发", 39);
        LINK_MAP.put("扁平件邮件作业区牵引车驾驶", 40);
        LINK_MAP.put("扁平件邮件作业区质检", 41);
        LINK_MAP.put("扁平件邮件作业区白班", 42);
        LINK_MAP.put("扁平件邮件作业区综合", 43);
    }

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
        int[] item_count = new int[EMPLOYEE_TABLE_ITEM.length];
        if (list.size() != 0) {
            for (int i = 0; i < employee_item_mark.length; i++)
                employee_item_mark[i] = true;

            //ID和日期为主键，所以不为空，略过检查
            for (Employee employee : list) {
                if (employee.getName() == null)
                    item_count[1]++;
                if (employee.getLevel() == null)
                    item_count[2]++;
                if (employee.getDepartment() == null)
                    item_count[3]++;
                if (employee.getGroup() == null)
                    item_count[4]++;
                if (employee.getPost() == null)
                    item_count[5]++;
                if (employee.getProduction_link() == null)
                    item_count[6]++;
            }

            for (int i = 0; i < item_count.length; i++)
                if (item_count[i] == list.size())
                    employee_item_mark[i] = false;
                else
                    employee_item_mark[i] = true;
        }
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
        }

        for (int i = 0; i < item_count.length; i++)
            if (item_count[i] == list.size())
                salaryDetail_item_mark[i] = false;
            else
                salaryDetail_item_mark[i] = true;

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
        int count = 0;
        for (int j = 0; j < employee_item_mark.length; j++)
            if (employee_item_mark[j]) {
                header_json_data.append("\"").append(EMPLOYEE_TABLE_ITEM[j]).append("\",");
                count++;
            }
        for (int j = 2; j < salaryDetail_item_mark.length; j++)
            if (salaryDetail_item_mark[j]) {
                header_json_data.append("\"").append(SALARY_DETAIL_TABLE_ITEM[j]).append("\",");
                count++;
            }
        if (count != 0)
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
                            //single_employee_json_data.append("\"").append(employee.getExpiration_date()).append("\",");
                            single_employee_json_data.append("\"").append(format.format(employee.getExpiration_date())).append("\",");
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
        if (flat != null)
            for (String link : flat) {
                LinkCostStatistics linkCostStatistics = new LinkCostStatistics();
                linkCostStatistics.setDepartment("扁平件邮件作业区");
                linkCostStatistics.setProduction_link(link);
                linkCostStatisticsHashMap.put("扁平件邮件作业区" + link, linkCostStatistics);
            }
        if (air != null)
            for (String link : air) {
                LinkCostStatistics linkCostStatistics = new LinkCostStatistics();
                linkCostStatistics.setDepartment("空侧邮件作业区");
                linkCostStatistics.setProduction_link(link);
                linkCostStatisticsHashMap.put("空侧邮件作业区" + link, linkCostStatistics);
            }
        if (ground != null)
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

            if (department.equals("扁平件邮件作业区")) {
                if (flat != null)
                    for (String link : flat) {
                        if (production_link.contains(link)) {
                            LinkCostStatistics linkCostStatistics = linkCostStatisticsHashMap.get("扁平件邮件作业区" + link);
                            linkCostStatistics.setDepartment(department);
                            linkCostStatistics.setCount(linkCostStatistics.getCount() + 1);
                            linkCostStatistics.setTotal_cost(linkCostStatistics.getTotal_cost() + labor_cost / links_count);
                        }
                    }
            } else if (department.equals("空侧邮件作业区")) {
                if (air != null)
                    for (String link : air) {
                        if (production_link.contains(link)) {
                            LinkCostStatistics linkCostStatistics = linkCostStatisticsHashMap.get("空侧邮件作业区" + link);
                            linkCostStatistics.setDepartment(department);
                            linkCostStatistics.setCount(linkCostStatistics.getCount() + 1);
                            linkCostStatistics.setTotal_cost(linkCostStatistics.getTotal_cost() + labor_cost / links_count);
                        }
                    }
            } else if (department.equals("陆侧邮件作业区")) {
                if (ground != null)
                    for (String link : ground) {
                        if (production_link.contains(link)) {
                            LinkCostStatistics linkCostStatistics = linkCostStatisticsHashMap.get("陆侧邮件作业区" + link);
                            linkCostStatistics.setDepartment(department);
                            linkCostStatistics.setCount(linkCostStatistics.getCount() + 1);
                            linkCostStatistics.setTotal_cost(linkCostStatistics.getTotal_cost() + labor_cost / links_count);
                        }
                    }
            }
        }

        StringBuilder json_data = new StringBuilder();
        json_data.append("{\"statistics\":[");

        Set<String> key_set = linkCostStatisticsHashMap.keySet();
        List<String> key_list = new LinkedList<>(key_set);
        Collections.sort(key_list);
        for (String link : key_list) {
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

        if (linkCostStatisticsHashMap.size() != 0)
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
        if (map.size() != 0)
            json_data.deleteCharAt(json_data.length() - 1);
        json_data.append("]}");
        return json_data.toString();
    }

    /**
     * @Description: 工资=应发合计
     * 公积金=企业承担住房公积金
     * 社保=企业承担各类保险
     * 补充医疗保险=补充医疗保险
     * 职工教育经费=职工教育经费
     * 职工福利费=职工福利费
     * 企业年金=企业年金
     * 工会经费=工会经费
     * 外包费用=外包人员应发合计+外包人员管理费+外包人员税差+小时工费用
     * @Param: [employeeList, salaryDetailList]
     * @return: java.lang.String
     * @Author: lcx
     * @Date: 2018/10/15
     */
    public String generateStatisticCostDetailJsonDataByGroup(List<Employee> employeeList,
                                                             List<SalaryDetail> salaryDetailList) {
        if (employeeList == null || salaryDetailList == null)
            return "";
        if (employeeList.size() != salaryDetailList.size())
            return "";

        //对应成本费用明细中的每一列
        ExpenseDetail[] expenseDetails = new ExpenseDetail[GROUP_MAP.size()];
        for (int i = 0; i < expenseDetails.length; i++)
            expenseDetails[i] = new ExpenseDetail();

        //对查询的日期范围内的每一条数据进行分类统计
        for (int i = 0; i < employeeList.size(); i++) {
            Employee employee = employeeList.get(i);
            SalaryDetail salaryDetail = salaryDetailList.get(i);
            String department = employee.getDepartment();
            String group = employee.getGroup();
            Integer index; //该员工属于明细表中的哪一列
            if (department == null) continue;
            if (group == null)
                index = GROUP_MAP.get(department);
            else
                index = GROUP_MAP.get(department + group);
            if (index != null) {
                generateRowStatistics(expenseDetails, index, 1, employee, salaryDetail);
            }
        }

        String result = generateStatisticalData(expenseDetails, GROUPS_NAME);
        return result;
    }

    /**
     * @Description: 将查询的数据按照部门的维度进行统计
     * @Param: [employeeList, salaryDetailList]
     * @return: java.lang.String
     * @Author: lcx
     * @Date: 2018/10/15
     */
    public String generateStatisticCostDetailJsonDataByDepartment(List<Employee> employeeList,
                                                                  List<SalaryDetail> salaryDetailList) {
        if (employeeList == null || salaryDetailList == null)
            return "";
        if (employeeList.size() != salaryDetailList.size())
            return "";

        //对应成本费用明细中的每一列
        ExpenseDetail[] expenseDetails = new ExpenseDetail[DEPARTMENT_MAP.size()];
        for (int i = 0; i < expenseDetails.length; i++)
            expenseDetails[i] = new ExpenseDetail();

        //对查询的日期范围内的每一条数据进行分类统计
        for (int i = 0; i < employeeList.size(); i++) {
            Employee employee = employeeList.get(i);
            SalaryDetail salaryDetail = salaryDetailList.get(i);
            String department = employee.getDepartment();
            Integer index;
            if (department == null) continue;

            index = DEPARTMENT_MAP.get(department);    //通过map获取是部门报表中的哪一列数据

            if (index != null) {
                generateRowStatistics(expenseDetails, index, 1, employee, salaryDetail);
            }
        }

        String result = generateStatisticalData(expenseDetails, DEPARTMENTS_NAME);
        return result;
    }

    /**
     * @Description: 将查询的数据按照环节进行统计
     * @Param: [employeeList, salaryDetailList]
     * @return: java.lang.String
     * @Author: lcx
     * @Date: 2018/10/15
     */
    public String generateStatisticCostDetailJsonDataByLink(List<Employee> employeeList,
                                                            List<SalaryDetail> salaryDetailList) {
        if (employeeList == null || salaryDetailList == null)
            return "";
        if (employeeList.size() != salaryDetailList.size())
            return "";

        //对应成本费用明细中的每一列
        ExpenseDetail[] expenseDetails = new ExpenseDetail[LINK_MAP.size()];
        for (int i = 0; i < expenseDetails.length; i++)
            expenseDetails[i] = new ExpenseDetail();

        //对查询的日期范围内的每一条数据进行分类统计
        for (int i = 0; i < employeeList.size(); i++) {
            Employee employee = employeeList.get(i);
            SalaryDetail salaryDetail = salaryDetailList.get(i);

            String department = employee.getDepartment();
            if (department.equals("扁平件邮件作业区") || department.equals("空侧邮件作业区") ||
                    department.equals("陆侧邮件作业区")) {
                String links = employee.getProduction_link();    //获取员工的生产环节
                String[] single_links = links.split("、");
                for (String li : single_links) {
                    Integer index = LINK_MAP.get(department + li);
                    if (index != null) {
                        generateRowStatistics(expenseDetails, index, single_links.length,
                                employee, salaryDetail);
                    }
                }
            }
        }

        ExpenseDetail[] expenseDetails_copy;
        String[] names_copy;
        String temp;

        StringBuilder json_data = new StringBuilder();
        json_data.append("{");
        json_data.append("\"空侧邮件作业区\":{");

        expenseDetails_copy = new ExpenseDetail[14];
        names_copy = new String[14];
        System.arraycopy(expenseDetails, 0, expenseDetails_copy, 0, 14);
        System.arraycopy(LINKS_NAME, 0,names_copy,0,14);
        temp = generateStatisticalData(expenseDetails_copy, names_copy);
        temp = temp.replace("{", "");
        temp = temp.replace("}", "");
        json_data.append(temp);

        json_data.append("},");
        json_data.append("\"陆侧邮件作业区\":{");

        expenseDetails_copy = new ExpenseDetail[14];
        names_copy = new String[14];
        System.arraycopy(expenseDetails, 14, expenseDetails_copy, 0, 14);
        System.arraycopy(LINKS_NAME, 14,names_copy,0,14);
        temp = generateStatisticalData(expenseDetails_copy, names_copy);
        temp = temp.replace("{", "");
        temp = temp.replace("}", "");
        json_data.append(temp);

        json_data.append("},");
        json_data.append("\"扁平件邮件作业区\":{");

        expenseDetails_copy = new ExpenseDetail[16];
        names_copy = new String[16];
        System.arraycopy(expenseDetails, 28, expenseDetails_copy, 0, 16);
        System.arraycopy(LINKS_NAME, 28,names_copy,0,16);
        temp = generateStatisticalData(expenseDetails_copy, names_copy);
        temp = temp.replace("{", "");
        temp = temp.replace("}", "");
        json_data.append(temp);

        json_data.append("}");
        json_data.append("}");

       return json_data.toString();
    }

    /**
     * @Description: 生成统计报表的一列数据，proportion是为了环节分摊添加的，
     * 其余场景下该值为1
     * @Param: [expenseDetails, index, proportion, employee, salaryDetail]
     * @return: void
     * @Author: lcx
     * @Date: 2018/10/15
     */
    private static void generateRowStatistics(ExpenseDetail[] expenseDetails,
                                              int index,
                                              int proportion,
                                              Employee employee,
                                              SalaryDetail salaryDetail) {
        double temp;
        ExpenseDetail expenseDetail = expenseDetails[index];

        temp = salaryDetail.getLabor_cost() / proportion;
        expenseDetail.setManual_labor_cost(expenseDetail.getManual_labor_cost() + temp);

        temp = salaryDetail.getBase_pay() / proportion;
        expenseDetail.setManual_wages(expenseDetail.getManual_wages() + temp);

        expenseDetail.setManual_wage_related_expenses(expenseDetail.getManual_wage_related_expenses() +
                100);

        //职工福利费
        temp = salaryDetail.getWelfare_funds() / proportion;
        expenseDetail.setManual_employee_benefits(expenseDetail.getManual_employee_benefits() +
                temp);

        //职工教育经费
        temp = salaryDetail.getStaff_training_expense() / proportion;
        expenseDetail.setManual_staff_training_expense(expenseDetail.getManual_staff_training_expense() +
                temp);

        //公积金
        temp = salaryDetail.getEnterprises_undertake_housing_provident() / proportion;
        expenseDetail.setManual_accumulation_fund(expenseDetail.getManual_accumulation_fund() +
                temp);

        //社保
        temp = salaryDetail.getEnterprises_undertake_various_insurance() / proportion;
        expenseDetail.setManual_social_security(expenseDetail.getManual_social_security() +
                temp);

        //外包费用(还没加小时工)
        if (employee.getLevel() == null) {
            temp = salaryDetail.getJoint_plan() +
                    salaryDetail.getOutsourced_personnel_management_fee() +
                    salaryDetail.getOutsourced_personnel_taxes_fee();
            expenseDetail.setManual_outsourced_cost(expenseDetail.getManual_outsourced_cost() +
                    temp);
        }
    }

    private static String generateStatisticalData(ExpenseDetail[] expenseDetails, String[] HEADS) {

        StringBuilder json_data = new StringBuilder();
        json_data.append("{");

        for (int i = 0; i < expenseDetails.length; i++) {
            json_data.append("\"").append(HEADS[i]).append("\":[");
            json_data.append(expenseDetails[i].toString());
            json_data.append("],");
        }
        json_data.deleteCharAt(json_data.length() - 1);
        json_data.append("}");
        return json_data.toString();
    }
}

package domain;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @author lcx :liu.changxin@qq.com
 * @data 2018/9/30 17:45
 */
public class SalaryDetail {
    public static final Map<String, Integer> contents = new HashMap<String, Integer>();
    private String[] contents_value = new String[35];

    static {
        contents.put("身份证号码", 0);
        contents.put("基本工资", 1);
        contents.put("月绩效", 2);
        contents.put("职业资格等级津贴", 3);
        contents.put("专业技术职务津贴", 4);
        contents.put("专家津贴", 5);
        contents.put("班组长津贴", 6);
        contents.put("综合补贴", 7);
        contents.put("加班费", 8);
        contents.put("夜班费", 9);
        contents.put("通信补贴", 10);
        contents.put("交通补贴", 11);
        contents.put("车辆公里数补贴", 12);
        contents.put("餐费", 13);
        contents.put("房补", 14);
        contents.put("补发补退", 15);
        contents.put("过节费", 16);
        contents.put("劳动竞赛", 17);
        contents.put("业务发展奖", 18);
        contents.put("质效奖励", 19);
        contents.put("季奖", 20);
        contents.put("中心奖励", 21);
        contents.put("高温费", 22);
        contents.put("稳岗补贴", 23);
        contents.put("绩效兑现", 24);
        contents.put("职工教育经费", 25);
        contents.put("福利费", 26);
        contents.put("其他", 27);
        contents.put("应发合计", 28);
        contents.put("企业承担住房公积金", 29);
        contents.put("企业承担各类保险", 30);
        contents.put("工会经费", 31);
        contents.put("外包人员管理费", 32);
        contents.put("外包人员税费", 33);
        contents.put("人工成本", 34);
    }

    private String id;
    private Date pay_date;
    private double base_pay;
    private double monthly_performance;
    private double professional_qualification_allowance;
    private double technical_post_allowance;
    private double expert_allowance;
    private double class_leader_allowance;
    private double comprehensive_subsidy;
    private double overtime_pay;
    private double night_shift_fee;
    private double communication_subsidy;
    private double traffic_allowance;
    private double vehicle_kilometres_subsidy;
    private double meal_fee;
    private double housing_supplement;
    private double recharge_supplement;
    private double festival_fee;
    private double labor_competition;
    private double business_development_award;
    private double quality_reward;
    private double season_award;
    private double central_reward;
    private double high_temperature_fee;
    private double steady_post_subsidy;
    private double performance_cashing;
    private double staff_training_expense;
    private double welfare_funds;
    private double other;
    private double joint_plan;
    private double enterprises_undertake_housing_provident;
    private double enterprises_undertake_various_insurance;
    private double trade_union_funds;
    private double outsourced_personnel_management_fee;
    private double outsourced_personnel_taxes_fee;
    private double labor_cost;

    public void setContents_value(int i, String values) {
        if (i < contents_value.length)
            contents_value[i] = values;
    }

    public String getId() {
        return id;
    }

    public Date getPay_date() {
        return pay_date;
    }

    public double getBase_pay() {
        return base_pay;
    }

    public double getMonthly_performance() {
        return monthly_performance;
    }

    public double getProfessional_qualification_allowance() {
        return professional_qualification_allowance;
    }

    public double getTechnical_post_allowance() {
        return technical_post_allowance;
    }

    public double getExpert_allowance() {
        return expert_allowance;
    }

    public double getClass_leader_allowance() {
        return class_leader_allowance;
    }

    public double getComprehensive_subsidy() {
        return comprehensive_subsidy;
    }

    public double getOvertime_pay() {
        return overtime_pay;
    }

    public double getNight_shift_fee() {
        return night_shift_fee;
    }

    public double getCommunication_subsidy() {
        return communication_subsidy;
    }

    public double getTraffic_allowance() {
        return traffic_allowance;
    }

    public double getVehicle_kilometres_subsidy() {
        return vehicle_kilometres_subsidy;
    }

    public double getMeal_fee() {
        return meal_fee;
    }

    public double getHousing_supplement() {
        return housing_supplement;
    }

    public double getRecharge_supplement() {
        return recharge_supplement;
    }

    public double getFestival_fee() {
        return festival_fee;
    }

    public double getLabor_competition() {
        return labor_competition;
    }

    public double getBusiness_development_award() {
        return business_development_award;
    }

    public double getQuality_reward() {
        return quality_reward;
    }

    public double getSeason_award() {
        return season_award;
    }

    public double getCentral_reward() {
        return central_reward;
    }

    public double getHigh_temperature_fee() {
        return high_temperature_fee;
    }

    public double getSteady_post_subsidy() {
        return steady_post_subsidy;
    }

    public double getPerformance_cashing() {
        return performance_cashing;
    }

    public double getStaff_training_expense() {
        return staff_training_expense;
    }

    public double getWelfare_funds() {
        return welfare_funds;
    }

    public double getOther() {
        return other;
    }

    public double getJoint_plan() {
        return joint_plan;
    }

    public double getEnterprises_undertake_housing_provident() {
        return enterprises_undertake_housing_provident;
    }

    public double getEnterprises_undertake_various_insurance() {
        return enterprises_undertake_various_insurance;
    }

    public double getTrade_union_funds() {
        return trade_union_funds;
    }

    public double getOutsourced_personnel_management_fee() {
        return outsourced_personnel_management_fee;
    }

    public double getOutsourced_personnel_taxes_fee() {
        return outsourced_personnel_taxes_fee;
    }

    public double getLabor_cost() {
        return labor_cost;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setPay_date(Date pay_date) {
        this.pay_date = pay_date;
    }

    public void setBase_pay(double base_pay) {
        this.base_pay = base_pay;
    }

    public void setMonthly_performance(double monthly_performance) {
        this.monthly_performance = monthly_performance;
    }

    public void setProfessional_qualification_allowance(double professional_qualification_allowance) {
        this.professional_qualification_allowance = professional_qualification_allowance;
    }

    public void setTechnical_post_allowance(double technical_post_allowance) {
        this.technical_post_allowance = technical_post_allowance;
    }

    public void setExpert_allowance(double expert_allowance) {
        this.expert_allowance = expert_allowance;
    }

    public void setClass_leader_allowance(double class_leader_allowance) {
        this.class_leader_allowance = class_leader_allowance;
    }

    public void setComprehensive_subsidy(double comprehensive_subsidy) {
        this.comprehensive_subsidy = comprehensive_subsidy;
    }

    public void setOvertime_pay(double overtime_pay) {
        this.overtime_pay = overtime_pay;
    }

    public void setNight_shift_fee(double night_shift_fee) {
        this.night_shift_fee = night_shift_fee;
    }

    public void setCommunication_subsidy(double communication_subsidy) {
        this.communication_subsidy = communication_subsidy;
    }

    public void setTraffic_allowance(double traffic_allowance) {
        this.traffic_allowance = traffic_allowance;
    }

    public void setVehicle_kilometres_subsidy(double vehicle_kilometres_subsidy) {
        this.vehicle_kilometres_subsidy = vehicle_kilometres_subsidy;
    }

    public void setMeal_fee(double meal_fee) {
        this.meal_fee = meal_fee;
    }

    public void setHousing_supplement(double housing_supplement) {
        this.housing_supplement = housing_supplement;
    }

    public void setRecharge_supplement(double recharge_supplement) {
        this.recharge_supplement = recharge_supplement;
    }

    public void setFestival_fee(double festival_fee) {
        this.festival_fee = festival_fee;
    }

    public void setLabor_competition(double labor_competition) {
        this.labor_competition = labor_competition;
    }

    public void setBusiness_development_award(double business_development_award) {
        this.business_development_award = business_development_award;
    }

    public void setQuality_reward(double quality_reward) {
        this.quality_reward = quality_reward;
    }

    public void setSeason_award(double season_award) {
        this.season_award = season_award;
    }

    public void setCentral_reward(double central_reward) {
        this.central_reward = central_reward;
    }

    public void setHigh_temperature_fee(double high_temperature_fee) {
        this.high_temperature_fee = high_temperature_fee;
    }

    public void setSteady_post_subsidy(double steady_post_subsidy) {
        this.steady_post_subsidy = steady_post_subsidy;
    }

    public void setPerformance_cashing(double performance_cashing) {
        this.performance_cashing = performance_cashing;
    }

    public void setStaff_training_expense(double staff_training_expense) {
        this.staff_training_expense = staff_training_expense;
    }

    public void setWelfare_funds(double welfare_funds) {
        this.welfare_funds = welfare_funds;
    }

    public void setOther(double other) {
        this.other = other;
    }

    public void setJoint_plan(double joint_plan) {
        this.joint_plan = joint_plan;
    }

    public void setEnterprises_undertake_housing_provident(double enterprises_undertake_housing_provident) {
        this.enterprises_undertake_housing_provident = enterprises_undertake_housing_provident;
    }

    public void setEnterprises_undertake_various_insurance(double enterprises_undertake_various_insurance) {
        this.enterprises_undertake_various_insurance = enterprises_undertake_various_insurance;
    }

    public void setTrade_union_funds(double trade_union_funds) {
        this.trade_union_funds = trade_union_funds;
    }

    public void setOutsourced_personnel_management_fee(double outsourced_personnel_management_fee) {
        this.outsourced_personnel_management_fee = outsourced_personnel_management_fee;
    }

    public void setOutsourced_personnel_taxes_fee(double outsourced_personnel_taxes_fee) {
        this.outsourced_personnel_taxes_fee = outsourced_personnel_taxes_fee;
    }

    public void setLabor_cost(double labor_cost) {
        this.labor_cost = labor_cost;
    }

    public void contents_valueToObject() {
        if (contents_value[0] != null)
            id = contents_value[0];
        if (contents_value[1] != null)
            base_pay = Double.parseDouble(contents_value[1]);
        if (contents_value[2] != null)
            monthly_performance = Double.parseDouble(contents_value[2]);
        if (contents_value[3] != null)
            professional_qualification_allowance = Double.parseDouble(contents_value[3]);
        if (contents_value[4] != null)
            technical_post_allowance = Double.parseDouble(contents_value[4]);
        if (contents_value[5] != null)
            expert_allowance = Double.parseDouble(contents_value[5]);
        if (contents_value[6] != null)
            class_leader_allowance = Double.parseDouble(contents_value[6]);
        if (contents_value[7] != null)
            comprehensive_subsidy = Double.parseDouble(contents_value[7]);
        if (contents_value[8] != null)
            overtime_pay = Double.parseDouble(contents_value[8]);
        if (contents_value[9] != null)
            night_shift_fee = Double.parseDouble(contents_value[9]);
        if (contents_value[10] != null)
            communication_subsidy = Double.parseDouble(contents_value[10]);
        if (contents_value[11] != null)
            traffic_allowance = Double.parseDouble(contents_value[11]);
        if (contents_value[12] != null)
            vehicle_kilometres_subsidy = Double.parseDouble(contents_value[12]);
        if (contents_value[13] != null)
            meal_fee = Double.parseDouble(contents_value[13]);
        if (contents_value[14] != null)
            housing_supplement = Double.parseDouble(contents_value[14]);
        if (contents_value[15] != null)
            recharge_supplement = Double.parseDouble(contents_value[15]);
        if (contents_value[16] != null)
            festival_fee = Double.parseDouble(contents_value[16]);
        if (contents_value[17] != null)
            labor_competition = Double.parseDouble(contents_value[17]);
        if (contents_value[18] != null)
            business_development_award = Double.parseDouble(contents_value[18]);
        if (contents_value[19] != null)
            quality_reward = Double.parseDouble(contents_value[19]);
        if (contents_value[20] != null)
            season_award = Double.parseDouble(contents_value[20]);
        if (contents_value[21] != null)
            central_reward = Double.parseDouble(contents_value[21]);
        if (contents_value[22] != null)
            high_temperature_fee = Double.parseDouble(contents_value[22]);
        if (contents_value[23] != null)
            steady_post_subsidy = Double.parseDouble(contents_value[23]);
        if (contents_value[24] != null)
            performance_cashing = Double.parseDouble(contents_value[24]);
        if (contents_value[25] != null)
            staff_training_expense = Double.parseDouble(contents_value[25]);
        if (contents_value[26] != null)
            welfare_funds = Double.parseDouble(contents_value[26]);
        if (contents_value[27] != null)
            other = Double.parseDouble(contents_value[27]);
        if (contents_value[28] != null)
            joint_plan = Double.parseDouble(contents_value[28]);
        if (contents_value[29] != null)
            enterprises_undertake_housing_provident = Double.parseDouble(contents_value[29]);
        if (contents_value[30] != null)
            enterprises_undertake_various_insurance = Double.parseDouble(contents_value[30]);
        if (contents_value[31] != null)
            trade_union_funds = Double.parseDouble(contents_value[31]);
        if (contents_value[32] != null)
            outsourced_personnel_management_fee = Double.parseDouble(contents_value[32]);
        if (contents_value[33] != null)
            outsourced_personnel_taxes_fee = Double.parseDouble(contents_value[33]);
        if (contents_value[34] != null)
            labor_cost = Double.parseDouble(contents_value[34]);
    }
}

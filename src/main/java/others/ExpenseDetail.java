package others;

/**
 * @author lcx :liu.changxin@qq.com
 * @date 2018/10/14 10:17
 * 费用明细，用于生成总的费用报表，该类对应报表完成一列
 */
public class ExpenseDetail {

    //人工
    private double manual_labor_cost;
    private double manual_wages;
    private double manual_wage_related_expenses;
    private double manual_employee_benefits;
    private double manual_staff_training_expense;
    private double manual_accumulation_fund;
    private double manual_social_security;
    private double manual_outsourced_cost;

    //折旧、摊销
    private double depreciation_cost;
    private double depreciation_expense;
    private double depreciation_amortize;

    //运输
    private double transport_cost;
    private double transport_self_handling;
    private double transport_toll;
    private double transport_fuel_costs;
    private double transport_vehicle_insurance;
    private double transport_maintenance;
    private double transport_annual_parking;
    private double transport_freight_charges;
    private double transport_highway;
    private double transport_aviation;

    //修理
    private double repair_cost;
    private double repair_buildings_structures;
    private double repair_mechanical_equipment;
    private double repair_computer;
    private double repair_other;

    //低值易耗品
    private double goods_cost;
    private double goods_expense;
    private double goods_administration;

    //业务费
    private double operation_cost;
    private double operation_labor_protection;
    private double operation_book_fee_consumables;
    private double operation_business_losses;
    private double operation_leasehold;
    private double operation_printing;
    private double operation_water_heating;
    private double operation_property_management;
    private double operation_security;
    private double operation_other;

    //各项税费
    private double taxes_cost;
    private double taxes_business_tax;
    private double taxes_property_tax;
    private double taxes_vehicle_vessel;
    private double taxes_stamp;
    private double taxes_land;
    private double taxes_employment_security;

    //纯管理费
    private double management_cost;
    private double management_office_expenses;
    private double management_communication_cost;
    private double management_conference_fee;
    private double management_travel_expenses;
    private double management_business_entertainment;
    private double management_foreign_affairs;
    private double management_business_promotion;
    private double management_agency_fee;
    private double management_accident_insurance;
    private double management_financial_cost;
    private double management_business_loss;
    private double management_other;
    private double management_non_operating;


    public double getManual_labor_cost() {
        return manual_labor_cost;
    }

    public double getManual_wages() {
        return manual_wages;
    }

    public double getManual_wage_related_expenses() {
        return manual_wage_related_expenses;
    }

    public double getManual_employee_benefits() {
        return manual_employee_benefits;
    }

    public double getManual_staff_training_expense() {
        return manual_staff_training_expense;
    }

    public double getManual_accumulation_fund() {
        return manual_accumulation_fund;
    }

    public double getManual_social_security() {
        return manual_social_security;
    }

    public double getManual_outsourced_cost() {
        return manual_outsourced_cost;
    }

    public double getDepreciation_cost() {
        return depreciation_cost;
    }

    public double getDepreciation_expense() {
        return depreciation_expense;
    }

    public double getDepreciation_amortize() {
        return depreciation_amortize;
    }

    public double getTransport_cost() {
        return transport_cost;
    }

    public double getTransport_self_handling() {
        return transport_self_handling;
    }

    public double getTransport_toll() {
        return transport_toll;
    }

    public double getTransport_fuel_costs() {
        return transport_fuel_costs;
    }

    public double getTransport_vehicle_insurance() {
        return transport_vehicle_insurance;
    }

    public double getTransport_maintenance() {
        return transport_maintenance;
    }

    public double getTransport_annual_parking() {
        return transport_annual_parking;
    }

    public double getTransport_freight_charges() {
        return transport_freight_charges;
    }

    public double getTransport_highway() {
        return transport_highway;
    }

    public double getTransport_aviation() {
        return transport_aviation;
    }

    public double getRepair_cost() {
        return repair_cost;
    }

    public double getRepair_buildings_structures() {
        return repair_buildings_structures;
    }

    public double getRepair_mechanical_equipment() {
        return repair_mechanical_equipment;
    }

    public double getRepair_computer() {
        return repair_computer;
    }

    public double getRepair_other() {
        return repair_other;
    }

    public double getGoods_cost() {
        return goods_cost;
    }

    public double getGoods_expense() {
        return goods_expense;
    }

    public double getGoods_administration() {
        return goods_administration;
    }

    public double getOperation_cost() {
        return operation_cost;
    }

    public double getOperation_labor_protection() {
        return operation_labor_protection;
    }

    public double getOperation_book_fee_consumables() {
        return operation_book_fee_consumables;
    }

    public double getOperation_business_losses() {
        return operation_business_losses;
    }

    public double getOperation_leasehold() {
        return operation_leasehold;
    }

    public double getOperation_printing() {
        return operation_printing;
    }

    public double getOperation_water_heating() {
        return operation_water_heating;
    }

    public double getOperation_property_management() {
        return operation_property_management;
    }

    public double getOperation_security() {
        return operation_security;
    }

    public double getOperation_other() {
        return operation_other;
    }

    public double getTaxes_cost() {
        return taxes_cost;
    }

    public double getTaxes_business_tax() {
        return taxes_business_tax;
    }

    public double getTaxes_property_tax() {
        return taxes_property_tax;
    }

    public double getTaxes_vehicle_vessel() {
        return taxes_vehicle_vessel;
    }

    public double getTaxes_stamp() {
        return taxes_stamp;
    }

    public double getTaxes_land() {
        return taxes_land;
    }

    public double getTaxes_employment_security() {
        return taxes_employment_security;
    }

    public double getManagement_cost() {
        return management_cost;
    }

    public double getManagement_office_expenses() {
        return management_office_expenses;
    }

    public double getManagement_communication_cost() {
        return management_communication_cost;
    }

    public double getManagement_conference_fee() {
        return management_conference_fee;
    }

    public double getManagement_travel_expenses() {
        return management_travel_expenses;
    }

    public double getManagement_business_entertainment() {
        return management_business_entertainment;
    }

    public double getManagement_foreign_affairs() {
        return management_foreign_affairs;
    }

    public double getManagement_business_promotion() {
        return management_business_promotion;
    }

    public double getManagement_agency_fee() {
        return management_agency_fee;
    }

    public double getManagement_accident_insurance() {
        return management_accident_insurance;
    }

    public double getManagement_financial_cost() {
        return management_financial_cost;
    }

    public double getManagement_business_loss() {
        return management_business_loss;
    }

    public double getManagement_other() {
        return management_other;
    }

    public double getManagement_non_operating() {
        return management_non_operating;
    }

    public void setManual_labor_cost(double manual_labor_cost) {
        this.manual_labor_cost = manual_labor_cost;
    }

    public void setManual_wages(double manual_wages) {
        this.manual_wages = manual_wages;
    }

    public void setManual_wage_related_expenses(double manual_wage_related_expenses) {
        this.manual_wage_related_expenses = manual_wage_related_expenses;
    }

    public void setManual_employee_benefits(double manual_employee_benefits) {
        this.manual_employee_benefits = manual_employee_benefits;
    }

    public void setManual_staff_training_expense(double manual_staff_training_expense) {
        this.manual_staff_training_expense = manual_staff_training_expense;
    }

    public void setManual_accumulation_fund(double manual_accumulation_fund) {
        this.manual_accumulation_fund = manual_accumulation_fund;
    }

    public void setManual_social_security(double manual_social_security) {
        this.manual_social_security = manual_social_security;
    }

    public void setManual_outsourced_cost(double manual_outsourced_cost) {
        this.manual_outsourced_cost = manual_outsourced_cost;
    }

    public void setDepreciation_cost(double depreciation_cost) {
        this.depreciation_cost = depreciation_cost;
    }

    public void setDepreciation_expense(double depreciation_expense) {
        this.depreciation_expense = depreciation_expense;
    }

    public void setDepreciation_amortize(double depreciation_amortize) {
        this.depreciation_amortize = depreciation_amortize;
    }

    public void setTransport_cost(double transport_cost) {
        this.transport_cost = transport_cost;
    }

    public void setTransport_self_handling(double transport_self_handling) {
        this.transport_self_handling = transport_self_handling;
    }

    public void setTransport_toll(double transport_toll) {
        this.transport_toll = transport_toll;
    }

    public void setTransport_fuel_costs(double transport_fuel_costs) {
        this.transport_fuel_costs = transport_fuel_costs;
    }

    public void setTransport_vehicle_insurance(double transport_vehicle_insurance) {
        this.transport_vehicle_insurance = transport_vehicle_insurance;
    }

    public void setTransport_maintenance(double transport_maintenance) {
        this.transport_maintenance = transport_maintenance;
    }

    public void setTransport_annual_parking(double transport_annual_parking) {
        this.transport_annual_parking = transport_annual_parking;
    }

    public void setTransport_freight_charges(double transport_freight_charges) {
        this.transport_freight_charges = transport_freight_charges;
    }

    public void setTransport_highway(double transport_highway) {
        this.transport_highway = transport_highway;
    }

    public void setTransport_aviation(double transport_aviation) {
        this.transport_aviation = transport_aviation;
    }

    public void setRepair_cost(double repair_cost) {
        this.repair_cost = repair_cost;
    }

    public void setRepair_buildings_structures(double repair_budings_structures) {
        this.repair_buildings_structures = repair_budings_structures;
    }

    public void setRepair_mechanical_equipment(double repair_mechanical_equipment) {
        this.repair_mechanical_equipment = repair_mechanical_equipment;
    }

    public void setRepair_computer(double repair_computer) {
        this.repair_computer = repair_computer;
    }

    public void setRepair_other(double repair_other) {
        this.repair_other = repair_other;
    }

    public void setGoods_cost(double goods_cost) {
        this.goods_cost = goods_cost;
    }

    public void setGoods_expense(double goods_expense) {
        this.goods_expense = goods_expense;
    }

    public void setGoods_administration(double goods_administration) {
        this.goods_administration = goods_administration;
    }

    public void setOperation_cost(double operation_cost) {
        this.operation_cost = operation_cost;
    }

    public void setOperation_labor_protection(double operation_labor_protection) {
        this.operation_labor_protection = operation_labor_protection;
    }

    public void setOperation_book_fee_consumables(double operation_book_fee_consumables) {
        this.operation_book_fee_consumables = operation_book_fee_consumables;
    }

    public void setOperation_business_losses(double operation_business_losses) {
        this.operation_business_losses = operation_business_losses;
    }

    public void setOperation_leasehold(double operation_leasehold) {
        this.operation_leasehold = operation_leasehold;
    }

    public void setOperation_printing(double operation_printing) {
        this.operation_printing = operation_printing;
    }

    public void setOperation_water_heating(double operation_water_heating) {
        this.operation_water_heating = operation_water_heating;
    }

    public void setOperation_property_management(double operation_property_management) {
        this.operation_property_management = operation_property_management;
    }

    public void setOperation_security(double operation_security) {
        this.operation_security = operation_security;
    }

    public void setOperation_other(double operation_other) {
        this.operation_other = operation_other;
    }

    public void setTaxes_cost(double taxes_cost) {
        this.taxes_cost = taxes_cost;
    }

    public void setTaxes_business_tax(double taxes_business_tax) {
        this.taxes_business_tax = taxes_business_tax;
    }

    public void setTaxes_property_tax(double taxes_property_tax) {
        this.taxes_property_tax = taxes_property_tax;
    }

    public void setTaxes_vehicle_vessel(double taxes_vehicle_vessel) {
        this.taxes_vehicle_vessel = taxes_vehicle_vessel;
    }

    public void setTaxes_stamp(double taxes_stamp) {
        this.taxes_stamp = taxes_stamp;
    }

    public void setTaxes_land(double taxes_land) {
        this.taxes_land = taxes_land;
    }

    public void setTaxes_employment_security(double taxes_employment_security) {
        this.taxes_employment_security = taxes_employment_security;
    }

    public void setManagement_cost(double management_cost) {
        this.management_cost = management_cost;
    }

    public void setManagement_office_expenses(double management_office_expenses) {
        this.management_office_expenses = management_office_expenses;
    }

    public void setManagement_communication_cost(double management_communication_cost) {
        this.management_communication_cost = management_communication_cost;
    }

    public void setManagement_conference_fee(double management_conference_fee) {
        this.management_conference_fee = management_conference_fee;
    }

    public void setManagement_travel_expenses(double management_travel_expenses) {
        this.management_travel_expenses = management_travel_expenses;
    }

    public void setManagement_business_entertainment(double management_business_entertainment) {
        this.management_business_entertainment = management_business_entertainment;
    }

    public void setManagement_foreign_affairs(double management_foreign_affairs) {
        this.management_foreign_affairs = management_foreign_affairs;
    }

    public void setManagement_business_promotion(double management_business_promotion) {
        this.management_business_promotion = management_business_promotion;
    }

    public void setManagement_agency_fee(double management_agency_fee) {
        this.management_agency_fee = management_agency_fee;
    }

    public void setManagement_accident_insurance(double management_accident_insurance) {
        this.management_accident_insurance = management_accident_insurance;
    }

    public void setManagement_financial_cost(double management_financial_cost) {
        this.management_financial_cost = management_financial_cost;
    }

    public void setManagement_business_loss(double management_business_loss) {
        this.management_business_loss = management_business_loss;
    }

    public void setManagement_other(double management_other) {
        this.management_other = management_other;
    }

    public void setManagement_non_operating(double management_non_operating) {
        this.management_non_operating = management_non_operating;
    }

    @Override
    public String toString() {
        return
                manual_labor_cost +
                "," + manual_wages +
                "," + manual_wage_related_expenses +
                "," + manual_employee_benefits +
                "," + manual_staff_training_expense +
                "," + manual_accumulation_fund +
                "," + manual_social_security +
                "," + manual_outsourced_cost +
                "," + depreciation_cost +
                "," + depreciation_expense +
                "," + depreciation_amortize +
                "," + transport_cost +
                "," + transport_self_handling +
                "," + transport_toll +
                "," + transport_fuel_costs +
                "," + transport_vehicle_insurance +
                "," + transport_maintenance +
                "," + transport_annual_parking +
                "," + transport_freight_charges +
                "," + transport_highway +
                "," + transport_aviation +
                "," + repair_cost +
                "," + repair_buildings_structures +
                "," + repair_mechanical_equipment +
                "," + repair_computer +
                "," + repair_other +
                "," + goods_cost +
                "," + goods_expense +
                "," + goods_administration +
                "," + operation_cost +
                "," + operation_labor_protection +
                "," + operation_book_fee_consumables +
                "," + operation_business_losses +
                "," + operation_leasehold +
                "," + operation_printing +
                "," + operation_water_heating +
                "," + operation_property_management +
                "," + operation_security +
                "," + operation_other +
                "," + taxes_cost +
                "," + taxes_business_tax +
                "," + taxes_property_tax +
                "," + taxes_vehicle_vessel +
                "," + taxes_stamp +
                "," + taxes_land +
                "," + taxes_employment_security +
                "," + management_cost +
                "," + management_office_expenses +
                "," + management_communication_cost +
                "," + management_conference_fee +
                "," + management_travel_expenses +
                "," + management_business_entertainment +
                "," + management_foreign_affairs +
                "," + management_business_promotion +
                "," + management_agency_fee +
                "," + management_accident_insurance +
                "," + management_financial_cost +
                "," + management_business_loss +
                "," + management_other +
                "," + management_non_operating;
    }
}

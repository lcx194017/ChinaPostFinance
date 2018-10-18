package others;

/**
 * @author lcx :liu.changxin@qq.com
 * @date 2018/10/8 21:00
 * 环节成本均摊统计类
 */
public class LinkCostStatistics {
    private String department;
    private String production_link;
    private int count;
    private double total_cost;

    public LinkCostStatistics() {
    }

    public String getDepartment() {
        return department;
    }

    public String getProduction_link() {
        return production_link;
    }

    public int getCount() {
        return count;
    }

    public double getTotal_cost() {
        return total_cost;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public void setProduction_link(String production_link) {
        this.production_link = production_link;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public void setTotal_cost(double total_cost) {
        this.total_cost = total_cost;
    }
}

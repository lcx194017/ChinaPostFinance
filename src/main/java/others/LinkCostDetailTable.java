package others;

/**
 * @author lcx :liu.changxin@qq.com
 * @date 2018/10/10 9:12
 */
public class LinkCostDetailTable {
    private String id;
    private String name;
    private double cost_sharing;
    private double total_cost;

    public LinkCostDetailTable() {
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public double getCost_sharing() {
        return cost_sharing;
    }

    public double getTotal_cost() {
        return total_cost;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setCost_sharing(double cost_sharing) {
        this.cost_sharing = cost_sharing;
    }

    public void setTotal_cost(double total_cost) {
        this.total_cost = total_cost;
    }
}

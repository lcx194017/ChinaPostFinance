package domain;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @author lcx :liu.changxin@qq.com
 * @date 2018/9/30 16:57
 */
public class Employee{
    public static final Map<String, Integer> contents = new HashMap<String, Integer>();
    private String[] contents_value = new String[7];

    static {
        contents.put("身份证号码", 0);
        contents.put("姓名", 1);
        contents.put("职务", 2);
        contents.put("部门", 3);
        contents.put("班组", 4);
        contents.put("岗位", 5);
        contents.put("生产环节", 6);
    }

    private String id;
    private String name;
    private String level;
    private String department;
    private String group;
    private String post;
    private String production_link;
    private Date expiration_date;

    public void setContents_value(int i, String values) {
        if (i < contents_value.length)
            contents_value[i] = values;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getLevel() {
        return level;
    }

    public String getDepartment() {
        return department;
    }

    public String getGroup() {
        return group;
    }

    public String getPost() {
        return post;
    }

    public String getProduction_link() {
        return production_link;
    }

    public Date getExpiration_date() {
        return expiration_date;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public void setGroup(String group) {
        this.group = group;
    }

    public void setPost(String post) {
        this.post = post;
    }

    public void setProduction_link(String production_link) {
        this.production_link = production_link;
    }

    public void setExpiration_date(Date expiration_date) {
        this.expiration_date = expiration_date;
    }

    public void contents_valueToObject() {
        id = contents_value[0];
        name = contents_value[1];
        level = contents_value[2];
        department = contents_value[3];
        group = contents_value[4];
        post = contents_value[5];
        production_link = contents_value[6];
    }
}

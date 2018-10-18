package others;

import java.util.LinkedList;

/**
 * @author lcx :liu.changxin@qq.com
 * @date 2018/10/13 19:49
 * 责任中心集合类
 */
public class ResCenterCollection {
    private static final String[] RESPONSIBILITY_CENTERS = {"扁平件责任中心", "空侧责任中心", "陆侧责任中心",
            "设备责任中心", "运输责任中心", "综合责任中心", "邮航", "战略责任中心"};
    private static final LinkedList<String>[] LINKED_LISTS = new LinkedList[RESPONSIBILITY_CENTERS.length];

    static {
        for (int i = 0; i < LINKED_LISTS.length; i++)
            LINKED_LISTS[i] = new LinkedList();
        LINKED_LISTS[0].add("扁平件邮件作业区");
        LINKED_LISTS[1].add("空侧邮件作业区");
        LINKED_LISTS[2].add("陆侧邮件作业区");
        LINKED_LISTS[3].add("技术保障部");
        LINKED_LISTS[4].add("生产指挥调度中心");
        LINKED_LISTS[5].add("综合办公室");
        LINKED_LISTS[5].add("人力资源部");
        LINKED_LISTS[5].add("工会");
        LINKED_LISTS[5].add("安全保卫部");
    }

    public static String[] getRESPONSIBILITY_CENTERS() {
        return RESPONSIBILITY_CENTERS;
    }

    public static LinkedList<String>[] getLINKED_LISTS() {
        return LINKED_LISTS;
    }

}

package cn.jinronga.pojo;

/**
 * Created with IntelliJ IDEA.
 * User: 郭金荣
 * Date: 2020/4/5 0005
 * Time: 11:38
 * E-mail:1460595002@qq.com
 * 类说明:详情实体类
 */
public class Property {
    private int id;
    //详情名称
    private String name;
    //详情信息跟分类是多对一的关系  比如：手机分类 下的型号、品牌、屏幕、颜色等
    private Category category;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }
}

package cn.jinronga.pojo;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: 郭金荣
 * Date: 2020/4/5 0005
 * Time: 11:19
 * E-mail:1460595002@qq.com
 * 类说明:分类实体类
 */
public class Category {

    private int id;
    //分类名称
    private  String name;
    //分类下的产品
    private List<Product> products;

    //导航栏下通过分类查找产品 分类有下的集合多大 每个集合对应多少产品
    private List<List<Product>>productsByRow;

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

    public List<Product> getProducts() {
        return products;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }

    public List<List<Product>> getProductsByRow() {
        return productsByRow;
    }

    public void setProductsByRow(List<List<Product>> productsByRow) {
        this.productsByRow = productsByRow;
    }
}

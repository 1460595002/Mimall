package cn.jinronga.pojo;

/**
 * Created with IntelliJ IDEA.
 * User: 郭金荣
 * Date: 2020/4/5 0005
 * Time: 12:15
 * E-mail:1460595002@qq.com
 * 类说明:
 */
public class ProductImage {
    private int id;
    //产品  跟产品是多对一关系
    private Product product;

    //图片名称
    private  String  type;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}

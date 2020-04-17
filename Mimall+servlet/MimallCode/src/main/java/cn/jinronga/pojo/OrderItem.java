package cn.jinronga.pojo;

/**
 * Created with IntelliJ IDEA.
 * User: 郭金荣
 * Date: 2020/4/5 0005
 * Time: 14:45
 * E-mail:1460595002@qq.com
 * 类说明:订单项（购物车）
 */
public class OrderItem {

    //数量
    private int number;
    //产品
    private Product product;
    //订单
    private Order order;
   //用户
    private User user;

    private int id;

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}

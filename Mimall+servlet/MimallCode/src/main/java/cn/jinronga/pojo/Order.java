package cn.jinronga.pojo;

import cn.jinronga.Dao.OrderDao;

import java.util.Date;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: 郭金荣
 * Date: 2020/4/5 0005
 * Time: 14:24
 * E-mail:1460595002@qq.com
 * 类说明:订单
 */
public class Order {

    private int id;
    //订单编号
    private String orderCode;
    //地址
    private String addreass;
    //邮编
    private  String post;
    //收货人
    private String receiver;
    //电话号码
    private  String mobile;
    //用户信息
    private  String userMessage;

    //下单时间
    private Date crateDate;

    //支付时间
    private Date payDate;
    //发货时间
    private  Date deliverDate;
    //确认收货时间
    private Date confirmDate;

    //用户
    private  User user;
    //订单项（购物车） 一个订单对多个购物车
    private List<OrderItem> orderItems;
    //订单总金额
    private float total;
    //订单总数
    private  int totalNumber;

    //订单转态
    private String status;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getOrderCode() {
        return orderCode;
    }

    public void setOrderCode(String orderCode) {
        this.orderCode = orderCode;
    }

    public String getAddreass() {
        return addreass;
    }

    public void setAddreass(String addreass) {
        this.addreass = addreass;
    }

    public String getPost() {
        return post;
    }

    public void setPost(String post) {
        this.post = post;
    }

    public String getReceiver() {
        return receiver;
    }

    public void setReceiver(String receiver) {
        this.receiver = receiver;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getUserMessage() {
        return userMessage;
    }

    public void setUserMessage(String userMessage) {
        this.userMessage = userMessage;
    }

    public Date getCrateDate() {
        return crateDate;
    }

    public void setCrateDate(Date crateDate) {
        this.crateDate = crateDate;
    }

    public Date getPayDate() {
        return payDate;
    }

    public void setPayDate(Date payDate) {
        this.payDate = payDate;
    }

    public Date getDeliverDate() {
        return deliverDate;
    }

    public void setDeliverDate(Date deliverDate) {
        this.deliverDate = deliverDate;
    }

    public Date getConfirmDate() {
        return confirmDate;
    }

    public void setConfirmDate(Date confirmDate) {
        this.confirmDate = confirmDate;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public List<OrderItem> getOrderItems() {
        return orderItems;
    }

    public void setOrderItems(List<OrderItem> orderItems) {
        this.orderItems = orderItems;
    }

    public float getTotal() {
        return total;
    }

    public void setTotal(float total) {
        this.total = total;
    }

    public int getTotalNumber() {
        return totalNumber;
    }

    public void setTotalNumber(int totalNumber) {
        this.totalNumber = totalNumber;
    }

    public String getStatus() {
        String desc="未知";

            switch(status){
          case OrderDao.waitPay:
              desc="待付款";
              break;
          case OrderDao.waitDelivery:
              desc="待发货";
              break;
          case OrderDao.waitConfirm:
              desc="待收货";
              break;
          case OrderDao.waitReview:
              desc="等评价";
              break;
          case OrderDao.finish:
              desc="完成";
              break;
          case OrderDao.delete:
              desc="刪除";
              break;
          default:
              desc="未知";
            }


        return desc;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}

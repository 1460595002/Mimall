package cn.jinronga.Dao;

import cn.jinronga.pojo.Order;
import cn.jinronga.pojo.User;
import cn.jinronga.util.DBUtil;
import cn.jinronga.util.DateUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: 郭金荣
 * Date: 2020/4/7 0007
 * Time: 15:43
 * E-mail:1460595002@qq.com
 * 类说明:订单 Dao
 */
public class OrderDao {
    public static final String waitPay = "waitPay";//等待付款
    public static final String waitDelivery = "waitDelivery";//等待发货
    public static final String waitConfirm = "waitConfirm";//等待确认收获
    public static final String waitReview = "waitReview";//等待评价
    public static final String finish = "finish";//完成
    public static final String delete = "delete";//删除

    //注：订单有个状态叫做delete，表示该订单处于被删除状态。 因为订单是非常重要的商业信息，里面有支付金额，用户信息，产品相关信息等资料，通常来讲，是不会被删除的，而是做一个标记。

    //总数
    public int getTotal() {
        int total = 0;
        try (Connection connection = DBUtil.getConnection(); Statement statement = connection.createStatement();
        ) {
            String sql = "select count(*) from order_";
            ResultSet resultSet = statement.executeQuery(sql);

            while (resultSet.next()) {

                total = resultSet.getInt(1);
            }


        } catch (SQLException e) {
            e.printStackTrace();
        }

        return total;

    }

    //添加
    public void add(Order order) {

        String sql = "insert into order_ values(null,?,?,?,?,?,?,?,?,?,?,?,?)";

        try (Connection connection = DBUtil.getConnection(); PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            //设置sql参数
            preparedStatement.setString(1, order.getOrderCode());
            preparedStatement.setString(2, order.getAddreass());
            preparedStatement.setString(3, order.getPost());
            preparedStatement.setString(4, order.getReceiver());
            preparedStatement.setString(5, order.getMobile());
            preparedStatement.setString(6, order.getUserMessage());
            preparedStatement.setTimestamp(7, DateUtil.date1(order.getCrateDate()));
            preparedStatement.setTimestamp(8, DateUtil.date1(order.getPayDate()));
            preparedStatement.setTimestamp(9, DateUtil.date1(order.getDeliverDate()));
            preparedStatement.setTimestamp(10, DateUtil.date1(order.getConfirmDate()));
            preparedStatement.setInt(11, order.getUser().getId());//获取用户的id
            preparedStatement.setString(12, order.getStatus());

            preparedStatement.executeUpdate();


            ResultSet generatedKeys = preparedStatement.getGeneratedKeys();

            if (generatedKeys.next()) {
                int id = generatedKeys.getInt(1);

                order.setId(id);


            }


        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public void update(Order order) {
        String sql = "update order_ set address= ?, post=?, receiver=?,mobile=?,userMessage=? ,createDate = ? , payDate =? , deliveryDate =?, confirmDate = ? , orderCode =?, uid=?, status=? where id = ?";

        try (Connection connection = DBUtil.getConnection(); PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, order.getOrderCode());
            preparedStatement.setString(2, order.getAddreass());
            preparedStatement.setString(3, order.getPost());
            preparedStatement.setString(4, order.getReceiver());
            preparedStatement.setString(5, order.getMobile());
            preparedStatement.setString(6, order.getUserMessage());
            preparedStatement.setTimestamp(7, DateUtil.date1(order.getCrateDate()));
            preparedStatement.setTimestamp(8, DateUtil.date1(order.getPayDate()));
            preparedStatement.setTimestamp(9, DateUtil.date1(order.getDeliverDate()));
            preparedStatement.setTimestamp(10, DateUtil.date1(order.getConfirmDate()));
            preparedStatement.setInt(11, order.getUser().getId());//获取用户的id
            preparedStatement.setString(12, order.getStatus());
            preparedStatement.setInt(13, order.getId());//获取订单id


            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public void delete(int id) {
        String sql = "delete from order_ where id=" + id;
        try (Connection connection = DBUtil.getConnection(); PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    //根据id获取
    public Order getId(int id) {
        Order order = new Order();
        String sql = "select * from Order_ where id = " + id;
        try (Connection connection = DBUtil.getConnection(); PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                String orderCode = resultSet.getString("orderCode");
                String address = resultSet.getString("address");
                String post = resultSet.getString("post");
                String receiver = resultSet.getString("receiver");
                String userMessage = resultSet.getString("userMessage");
                String mobile = resultSet.getString("mobile");
                Timestamp craeteDate = DateUtil.date1(resultSet.getTimestamp("craeteDate"));
                Timestamp payDate = DateUtil.date1(resultSet.getTimestamp("payDate"));
                Timestamp deliverDate = DateUtil.date1(resultSet.getTimestamp("deliverDate"));
                Timestamp confirmDate = DateUtil.date1(resultSet.getTimestamp("confirmDate"));
                int uid = resultSet.getInt("uid");
                User user = new UserDao().getId(uid);
                String status = resultSet.getString("status");


                order.setId(id);
                order.setAddreass(address);
                order.setPost(post);
                order.setReceiver(receiver);
                order.setMobile(mobile);
                order.setUserMessage(userMessage);
                order.setCrateDate(craeteDate);
                order.setPayDate(payDate);
                order.setDeliverDate(deliverDate);
                order.setConfirmDate(confirmDate);
                order.setUser(user);
                order.setStatus(status);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return order;
    }

    public List<Order> list() {
        return list(0, Short.MAX_VALUE);
    }

    public List<Order> list(int start, int count) {
        List<Order> orders = new ArrayList<>();

        String sql = "select * from order_ order by id desc limit ?,?";

        try (Connection connection = DBUtil.getConnection(); PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setInt(1, start);
            preparedStatement.setInt(2, count);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                Order order = new Order();
                int id = resultSet.getInt(1);
                String orderCode = resultSet.getString("orderCode");
                String address = resultSet.getString("address");
                String post = resultSet.getString("post");
                String receiver = resultSet.getString("receiver");
                String userMessage = resultSet.getString("userMessage");
                String mobile = resultSet.getString("mobile");
                Timestamp craeteDate = DateUtil.date1(resultSet.getTimestamp("craeteDate"));
                Timestamp payDate = DateUtil.date1(resultSet.getTimestamp("payDate"));
                Timestamp deliverDate = DateUtil.date1(resultSet.getTimestamp("deliverDate"));
                Timestamp confirmDate = DateUtil.date1(resultSet.getTimestamp("confirmDate"));
                int uid = resultSet.getInt("uid");
                User user = new UserDao().getId(uid);
                String status = resultSet.getString("status");


                order.setId(id);
                order.setAddreass(address);
                order.setPost(post);
                order.setReceiver(receiver);
                order.setMobile(mobile);
                order.setUserMessage(userMessage);
                order.setCrateDate(craeteDate);
                order.setPayDate(payDate);
                order.setDeliverDate(deliverDate);
                order.setConfirmDate(confirmDate);
                order.setUser(user);
                order.setStatus(status);


                orders.add(order);

            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return orders;
    }

    //查询用户所有的订单，不包括已经delete的
    public List<Order> list(int uid, String excludedStatus) {
        return list(uid, excludedStatus, 0, Short.MAX_VALUE);
    }

    public  List<Order> list(int uid, String excludedStatus, int start, int count) {
        List<Order> orders = new ArrayList<>();
        String sql = "select * from order_ where uid=? and status !=? order by id desc limit ?,?";

        try (Connection connection = DBUtil.getConnection(); PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setInt(1, uid);
            preparedStatement.setString(2, excludedStatus);
            preparedStatement.setInt(3, start);
            preparedStatement.setInt(4, count);

            ResultSet resultSet = preparedStatement.executeQuery();


            while (resultSet.next()) {
                Order order = new Order();
                int id = resultSet.getInt(1);
                String orderCode = resultSet.getString("orderCode");
                String address = resultSet.getString("address");
                String post = resultSet.getString("post");
                String receiver = resultSet.getString("receiver");
                String userMessage = resultSet.getString("userMessage");
                String mobile = resultSet.getString("mobile");
                Timestamp craeteDate = DateUtil.date1(resultSet.getTimestamp("craeteDate"));
                Timestamp payDate = DateUtil.date1(resultSet.getTimestamp("payDate"));
                Timestamp deliverDate = DateUtil.date1(resultSet.getTimestamp("deliverDate"));
                Timestamp confirmDate = DateUtil.date1(resultSet.getTimestamp("confirmDate"));
                User user = new UserDao().getId(uid);
                String status = resultSet.getString("status");


                order.setId(id);
                order.setAddreass(address);
                order.setPost(post);
                order.setReceiver(receiver);
                order.setMobile(mobile);
                order.setUserMessage(userMessage);
                order.setCrateDate(craeteDate);
                order.setPayDate(payDate);
                order.setDeliverDate(deliverDate);
                order.setConfirmDate(confirmDate);
                order.setUser(user);
                order.setStatus(status);


                orders.add(order);
            }


        } catch (SQLException e) {
            e.printStackTrace();
        }
        return orders;
    }


}
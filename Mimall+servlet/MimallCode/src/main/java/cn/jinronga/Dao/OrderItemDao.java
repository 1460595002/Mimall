package cn.jinronga.Dao;

import cn.jinronga.pojo.Order;
import cn.jinronga.pojo.OrderItem;
import cn.jinronga.pojo.Product;
import cn.jinronga.pojo.User;
import cn.jinronga.util.DBUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: 郭金荣
 * Date: 2020/4/7 0007
 * Time: 21:40
 * E-mail:1460595002@qq.com
 * 类说明:购物车（订单项）
 */
public class OrderItemDao {
    //总数
    public  int getTotal(){
        int total=0;
        String sql="select count(*) from orderitem";

        try (Connection connection= DBUtil.getConnection();
             PreparedStatement preparedStatement=connection.prepareStatement(sql);
        ){
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()){
               total = resultSet.getInt(1);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return total;
    }
  public void add(OrderItem orderItem){
        String sql="insert into orderitem values (null ,?,?,?,?)";

      try (Connection connection= DBUtil.getConnection();
           PreparedStatement preparedStatement=connection.prepareStatement(sql);) {

          preparedStatement.setInt(1,orderItem.getProduct().getId());
          preparedStatement.setInt(2,orderItem.getOrder().getId());
          preparedStatement.setInt(3,orderItem.getUser().getId());
          preparedStatement.executeUpdate();

          //获取主键
          ResultSet generatedKeys = preparedStatement.getGeneratedKeys();
          if (generatedKeys.next()){

              int id = generatedKeys.getInt(1);
              orderItem.setId(id);

          }


      } catch (SQLException e) {
          e.printStackTrace();
      }


  }
  public void update(OrderItem orderItem){
        String sql="update orderItem set pid=?,oid=?,uid=?,`number`=? where id=?";
      try (Connection connection= DBUtil.getConnection();
           PreparedStatement preparedStatement=connection.prepareStatement(sql);) {
          preparedStatement.setInt(1,orderItem.getProduct().getId());
           //如果用户不存在就返回 -1
           if (orderItem.getOrder()==null){
               preparedStatement.setInt(2,-1);
           }else {
               //存在执行
               preparedStatement.setInt(2,orderItem.getOrder().getId());
           }

           preparedStatement.setInt(3,orderItem.getUser().getId());
           preparedStatement.setInt(4,orderItem.getNumber());
          preparedStatement.setInt(5,orderItem.getId());

           preparedStatement.executeUpdate();



      } catch (SQLException e) {
          e.printStackTrace();
      }


  }
  //删除
    public void delete(int id){

        String sql="delete from orderitem where id="+id;
        try (Connection connection= DBUtil.getConnection();
             PreparedStatement preparedStatement=connection.prepareStatement(sql);) {

            preparedStatement.executeUpdate();


        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    //获取某个购物车根据id
    public OrderItem getId(int id){
   OrderItem orderItem=null;
        String sql="select * from orderitem where id="+id;
        try (Connection connection= DBUtil.getConnection();
             PreparedStatement preparedStatement=connection.prepareStatement(sql);) {
            ResultSet resultSet = preparedStatement.executeQuery();


            while (resultSet.next()){
                orderItem=new OrderItem();
                int pid = resultSet.getInt("pid");
                int oid = resultSet.getInt("oid");
                int uid = resultSet.getInt("uid");
                int number = resultSet.getInt("number");

                Product product = new ProductDao().getId(pid);

                //如果订单不等于-1 就获取订单数据辅助给orderitem
                if(oid !=-1){
                    Order order= new OrderDao().getId(oid);
                    orderItem.setOrder(order);
                }

                User user = new UserDao().getId(uid);

                orderItem.setId(id);
                orderItem.setProduct(product);
                orderItem.setUser(user);
                orderItem.setNumber(number);



            }


        } catch (SQLException e) {
            e.printStackTrace();
        }
return orderItem;

    }

    public List<OrderItem> listByUser(int uid) {
        return listByUser(uid, 0, Short.MAX_VALUE);
    }

    public List<OrderItem> listByUser(int uid, int start, int count) {
        List<OrderItem> beans = new ArrayList<OrderItem>();

        String sql = "select * from OrderItem where uid = ? and oid=-1 order by id desc limit ?,? ";

        try (Connection c = DBUtil.getConnection(); PreparedStatement ps = c.prepareStatement(sql);) {

            ps.setInt(1, uid);
            ps.setInt(2, start);
            ps.setInt(3, count);

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                OrderItem bean = new OrderItem();
                int id = rs.getInt(1);

                int pid = rs.getInt("pid");
                int oid = rs.getInt("oid");
                int number = rs.getInt("number");

                Product product = new ProductDao().getId(pid);
                if(-1!=oid){
                    Order order= new OrderDao().getId(oid);
                    bean.setOrder(order);
                }

                User user = new UserDao().getId(uid);
                bean.setProduct(product);

                bean.setUser(user);
                bean.setNumber(number);
                bean.setId(id);
                beans.add(bean);
            }
        } catch (SQLException e) {

            e.printStackTrace();
        }
        return beans;
    }
    public List<OrderItem> listByOrder(int oid) {
        return listByOrder(oid, 0, Short.MAX_VALUE);
    }

    public List<OrderItem> listByOrder(int oid, int start, int count) {
        List<OrderItem> beans = new ArrayList<OrderItem>();

        String sql = "select * from OrderItem where oid = ? order by id desc limit ?,? ";

        try (Connection c = DBUtil.getConnection(); PreparedStatement ps = c.prepareStatement(sql);) {

            ps.setInt(1, oid);
            ps.setInt(2, start);
            ps.setInt(3, count);

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                OrderItem bean = new OrderItem();
                int id = rs.getInt(1);

                int pid = rs.getInt("pid");
                int uid = rs.getInt("uid");
                int number = rs.getInt("number");

                Product product = new ProductDao().getId(pid);
                if(-1!=oid){
                    Order order= new OrderDao().getId(oid);
                    bean.setOrder(order);
                }

                User user = new UserDao().getId(uid);
                bean.setProduct(product);

                bean.setUser(user);
                bean.setNumber(number);
                bean.setId(id);
                beans.add(bean);
            }
        } catch (SQLException e) {

            e.printStackTrace();
        }
        return beans;
    }

    public void fill(List<Order> os) {
        for (Order o : os) {
            List<OrderItem> ois=listByOrder(o.getId());
            float total = 0;
            int totalNumber = 0;
            for (OrderItem oi : ois) {
                total+=oi.getNumber()*oi.getProduct().getPromotePrice();
                totalNumber+=oi.getNumber();
            }
            o.setTotal(total);
            o.setOrderItems(ois);
            o.setTotalNumber(totalNumber);
        }

    }

    public void fill(Order o) {
        List<OrderItem> ois=listByOrder(o.getId());
        float total = 0;
        for (OrderItem oi : ois) {
            total+=oi.getNumber()*oi.getProduct().getPromotePrice();
        }
        o.setTotal(total);
        o.setOrderItems(ois);
    }

    public List<OrderItem> listByProduct(int pid) {
        return listByProduct(pid, 0, Short.MAX_VALUE);
    }

    public List<OrderItem> listByProduct(int pid, int start, int count) {
        List<OrderItem> beans = new ArrayList<OrderItem>();

        String sql = "select * from OrderItem where pid = ? order by id desc limit ?,? ";

        try (Connection c = DBUtil.getConnection(); PreparedStatement ps = c.prepareStatement(sql);) {

            ps.setInt(1, pid);
            ps.setInt(2, start);
            ps.setInt(3, count);

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                OrderItem bean = new OrderItem();
                int id = rs.getInt(1);

                int uid = rs.getInt("uid");
                int oid = rs.getInt("oid");
                int number = rs.getInt("number");

                Product product = new ProductDao().getId(pid);
                if(-1!=oid){
                    Order order= new OrderDao().getId(oid);
                    bean.setOrder(order);
                }

                User user = new UserDao().getId(uid);
                bean.setProduct(product);

                bean.setUser(user);
                bean.setNumber(number);
                bean.setId(id);
                beans.add(bean);
            }
        } catch (SQLException e) {

            e.printStackTrace();
        }
        return beans;
    }

    public int getSaleCount(int pid) {
        int total = 0;
        try (Connection c = DBUtil.getConnection(); Statement s = c.createStatement();) {

            String sql = "select sum(number) from OrderItem where pid = " + pid;

            ResultSet rs = s.executeQuery(sql);
            while (rs.next()) {
                total = rs.getInt(1);
            }
        } catch (SQLException e) {

            e.printStackTrace();
        }
        return total;
    }

}

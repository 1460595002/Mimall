package cn.jinronga.Dao;

import cn.jinronga.pojo.Product;
import cn.jinronga.pojo.Review;
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
 * Time: 11:12
 * E-mail:1460595002@qq.com
 * 类说明:评价Dao
 */
public class ReviewDAO {

    public int getTotal(){
        int total=0;
        try (Connection connection = DBUtil.getConnection(); Statement statement = connection.createStatement();
        ){
            String sql="select count(*) from Review";
            ResultSet resultSet = statement.executeQuery(sql);

            while (resultSet.next()){

                total = resultSet.getInt(1);
            }




        } catch (SQLException e) {
            e.printStackTrace();
        }

        return total;

    }
    //根据产品id查看产品下总评价
    public int getTotal(int pid){
        String sql="select * from review where pid="+pid;
        int total=0;
        try (Connection connection=DBUtil.getConnection(); PreparedStatement preparedStatement=connection.prepareStatement(sql);
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

    //添加评价
    public  void   add(Review review){

        String sql="insert into review value (null ,?,?,?,?)";
        try (Connection connection=DBUtil.getConnection();
             PreparedStatement preparedStatement=connection.prepareStatement(sql);
        ){
            //设置sql参数
            preparedStatement.setString(1,review.getContent());
            preparedStatement.setInt(2,review.getUser().getId());//用户id
            preparedStatement.setInt(3,review.getProduct().getId());//产品id
            preparedStatement.setTimestamp(4, DateUtil.date1(review.getCreateDate()));//时间工具类创建时间

            preparedStatement.executeUpdate();

            //获取数据库中的主键
            ResultSet generatedKeys = preparedStatement.getGeneratedKeys();

            if (generatedKeys.next()){

                int id = generatedKeys.getInt(1);
                //将数据库的id赋值给review
                review.setId(id);

            }


        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

     //更新
    public void update(Review review){
        String sql="update review set content=?, uid=?,pid=?,createDate=? where id=?";

        try (Connection connection=DBUtil.getConnection();
          PreparedStatement preparedStatement=connection.prepareStatement(sql);
        ){
            //设置sql参数
            preparedStatement.setString(1,review.getContent());
            preparedStatement.setInt(2,review.getUser().getId());//用户id
            preparedStatement.setInt(3,review.getProduct().getId());//产品id
            preparedStatement.setTimestamp(4,DateUtil.date1(review.getCreateDate()));
            preparedStatement.setInt(5,review.getId());

            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }


    }
    //删除
    public void delete(int id){
       String sql="delete from review where id="+id;

       try (Connection connection=DBUtil.getConnection();
         PreparedStatement preparedStatement=connection.prepareStatement(sql);
       ){
           preparedStatement.executeUpdate();

       } catch (SQLException e) {
           e.printStackTrace();
       }

    }

    //通过id获取
    public Review getId(int id){
        String sql="select * from review where id="+id;
         Review review=new Review();
        try (Connection connection=DBUtil.getConnection();
         PreparedStatement preparedStatement=connection.prepareStatement(sql);
        ){
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()){
                String content = resultSet.getString("content");
                int uid = resultSet.getInt("uid");//用户id
                int pid = resultSet.getInt("pid");
                Timestamp createDate = DateUtil.date1(resultSet.getTimestamp("createDate"));


                User user = new UserDao().getId(uid);//获取user对象的id
                Product product = new ProductDao().getId(pid);

                //将数据给review对象
                review.setId(id);
                review.setContent(content);
                review.setUser(user);
                review.setProduct(product);
                review.setCreateDate(createDate);
            }


        } catch (SQLException e) {
            e.printStackTrace();
        }
        return review;
    }
    //查询指定产品的全部评价
    public List<Review> list(int pid){
        return list(pid,0,Short.MAX_VALUE);
    }

    //分页查询指定产品的评价
    public List<Review> list(int pid, int start, int count) {

        List<Review>reviews=new ArrayList<>();
        String sql="select * from Review where pid = ? order by id desc limit ?,? ";

        try (Connection connection=DBUtil.getConnection();
           PreparedStatement preparedStatement=connection.prepareStatement(sql);
        ){
            //设置sql的参数
            preparedStatement.setInt(1,pid);
            preparedStatement.setInt(2,start);
            preparedStatement.setInt(3,count);

            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()){
                Review review=new Review();
                int id = resultSet.getInt(1);
                String content = resultSet.getString("content");
                int uid = resultSet.getInt("uid");//用户id
                Timestamp createDate = DateUtil.date1(resultSet.getTimestamp("createDate"));
                User user = new UserDao().getId(uid);//获取user对象的id
                Product product = new ProductDao().getId(pid);

               //将数据赋值给reviewduixiang
                review.setId(id);
                review.setContent(content);
                review.setUser(user);
                review.setProduct(product);
                review.setCreateDate(createDate);

                reviews.add(review);//把review添加到reviews集合中

            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
      return reviews;

    }
    //查询某个产品的评价总数
    public int getCount(int pid) {
        String sql = "select count(*) from Review where pid = ? ";

        try (Connection c = DBUtil.getConnection(); PreparedStatement ps = c.prepareStatement(sql);) {

            ps.setInt(1, pid);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                return rs.getInt(1);
            }
        } catch (SQLException e) {

            e.printStackTrace();
        }
        return 0;
    }

    //判断某个产品是否有评价
    public boolean isExist(String content, int pid) {

        String sql = "select * from Review where content = ? and pid = ?";

        try (Connection c = DBUtil.getConnection(); PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setString(1, content);
            ps.setInt(2, pid);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return true;
            }

        } catch (SQLException e) {

            e.printStackTrace();
        }

        return false;
    }

}

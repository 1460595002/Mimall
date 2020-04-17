package cn.jinronga.Dao;

import cn.jinronga.pojo.Category;
import cn.jinronga.pojo.User;
import cn.jinronga.util.DBUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: 郭金荣
 * Date: 2020/4/5 0005
 * Time: 22:13
 * E-mail:1460595002@qq.com
 * 类说明:用户Dao
 */
public class UserDao {


    //获取总数
    public int getTotal(){
        int total=0;
        String sql="select count(*) from user";
        try (Connection connection= DBUtil.getConnection();
             PreparedStatement preparedStatement=connection.prepareStatement(sql);
        ){
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()){

                total = resultSet.getInt(1);

            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return total;
    }

    /**
     *思路：
     * 把User对象插入到数据库分为两个部分：
        1.把数据insert到数据库，获取数据库的主键
        2.再把数据库数据赋值给 user对象
     * @param user
     */
    //增加
    public void add(User user){

         String sql="insert into user values(null ,? ,?)";

        try (Connection connection=DBUtil.getConnection();
             PreparedStatement preparedStatement=connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
        ){

              //将数据辅助给user对象
              preparedStatement.setString(1,user.getName());
              preparedStatement.setString(2,user.getPassword());

              preparedStatement.executeUpdate();
              //获取数据库主键
            ResultSet generatedKeys = preparedStatement.getGeneratedKeys();

            if (generatedKeys.next()){
                int id = generatedKeys.getInt(1);
                //将id赋值给user的id
                user.setId(id);
            }


        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    //更新
    public void update(User user){

           String sql="update user set name=?,password=? where id=?";
        try (Connection connection=DBUtil.getConnection();
          PreparedStatement preparedStatement=connection.prepareStatement(sql);
        ){

            preparedStatement.setString(1,user.getName());
            preparedStatement.setString(2,user.getPassword());
            preparedStatement.setInt(3,user.getId());

             preparedStatement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    //删除根据id进行删除
    public void delete(int id){

        String sql="delete from user where id="+id;

        try(Connection connection =DBUtil.getConnection();
          PreparedStatement preparedStatement=connection.prepareStatement(sql);
        ) {
            preparedStatement.executeUpdate();


        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
    //根据id获取用户
    public User getId(int id){
        //获取用户信息用于后续查询结果返回
        User user =null;
        String sql="select * from user where id"+id;
        try (Connection connection =DBUtil.getConnection();
          PreparedStatement preparedStatement=connection.prepareStatement(sql);
        ){

            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()){
                user=new User();
                String name =  resultSet.getString("name");
                String password = resultSet.getString("password");
                //将数据赋值给user对象
                user.setId(id);
                user.setName(name);
                 user.setPassword(password);

            }


        } catch (SQLException e) {
            e.printStackTrace();
        }
return user;

    }
    public List<User>list(){
        return list(0,Short.MAX_VALUE);
    }

   public List<User> list(int start, int count) {

        //用于装User对象
        List<User>  users=new ArrayList<User>();
        String sql="select * from user order by id desc limit ?,? ";

        try (Connection connection=DBUtil.getConnection();
          PreparedStatement preparedStatement=connection.prepareStatement(sql);
        ){
            //设置预编译sql参数
            preparedStatement.setInt(1,start);
            preparedStatement.setInt(2,count);

            ResultSet resultSet = preparedStatement.executeQuery();

           while (resultSet.next()){
                User user=new User();
                //数据库中的数据
               int id = resultSet.getInt("id");
               String name = resultSet.getString("name");
               String password = resultSet.getString("password");

               //将数据给user对象
               user.setId(id);
               user.setName(name);
               user.setPassword(password);
               //将user对象添加到users集合
               users.add(user);


           }


        } catch (SQLException e) {
            e.printStackTrace();
        }

        return users;//返回集合对象

    }

     //判断用户名存在
     public boolean isExist(String name){

       User user= get(name);

       return user!=null;
     }

    private User get(String name) {
        String sql="select * from user where name=?";
        User user=null;
        try(Connection connection=DBUtil.getConnection();
         PreparedStatement preparedStatement=connection.prepareStatement(sql);
        ){
            preparedStatement.setString(1,name);
            ResultSet resultSet = preparedStatement.executeQuery();

            if(resultSet.next()){
                 user=new User();
                 //数据库中的数据
                int id = resultSet.getInt("id");
                String password = resultSet.getString("password");
                //将数据赋值给user对象
                user.setId(id);
                user.setName(name);
                user.setPassword(password);


            }


        } catch (SQLException e) {
            e.printStackTrace();
        }
        return user;
    }

    //根据账号密码获取对象，用户判断账号密码是否存在，直接跟数据库对比较而不是把全部用户查出来在内存中对比
    public  User getUserAndPassword(String name,String password){

         User user=null;
        String sql ="select * from user where name=? and password=?";
         try (Connection connection=DBUtil.getConnection();
          PreparedStatement preparedStatement=connection.prepareStatement(sql);
         ){
             //设置预编译sql参数
             preparedStatement.setString(1,name);
             preparedStatement.setString(2,password);

             ResultSet resultSet = preparedStatement.executeQuery();
             if (resultSet.next()){
                        user=new User();
                 int id = resultSet.getInt("id");

                 //将数据给user对象赋值
                     user.setId(id);
                     user.setName(name);
                     user.setPassword(password);

             }




         } catch (SQLException e) {
             e.printStackTrace();
         }

return user;
    }
}

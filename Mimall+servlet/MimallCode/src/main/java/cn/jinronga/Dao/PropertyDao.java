package cn.jinronga.Dao;

import cn.jinronga.pojo.Category;
import cn.jinronga.pojo.Property;
import cn.jinronga.util.DBUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: 郭金荣
 * Date: 2020/4/6 0006
 * Time: 13:05
 * E-mail:1460595002@qq.com
 * 类说明:详情dao
 */
public class PropertyDao {


    //查询分类总数
    public int getTotal(int cid){
        int total=0;
        String sql="select * from property where cid="+cid;
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


    //增加
    public  void add(Property property){

        String sql="insert  into property values (null ,?,?)";
        try (Connection connection=DBUtil.getConnection();
             PreparedStatement preparedStatement=connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
        ){
            //设置sql参数
            preparedStatement.setInt(1,property.getCategory().getId());//外键category的id
             preparedStatement.setString(2,property.getName());
             //执行sql
            preparedStatement.executeUpdate();

            //获取数据库的主键
            ResultSet generatedKeys = preparedStatement.getGeneratedKeys();


            if(generatedKeys.next()){
                int id = generatedKeys.getInt(1);
                //将数据库的id赋值给property对象中的id
                property.setId(id);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
     //更新
    public void update(Property property){
        String sql="update property set cid=?,name=? where id=? ";
        try(Connection connection=DBUtil.getConnection();
          PreparedStatement preparedStatement=connection.prepareStatement(sql);
        ) {
            //设置的sql的参数
            preparedStatement.setInt(1,property.getCategory().getId());//获取外键的id
            preparedStatement.setString(2,property.getName());
            preparedStatement.setInt(3,property.getId());

            preparedStatement.executeUpdate();



        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    //删除
   public void delete(int id){
        String sql="delete  from property where id="+id;
        try(Connection connection=DBUtil.getConnection();
         PreparedStatement preparedStatement=connection.prepareStatement(sql);
        ) {
            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
   }

   //根据id获取详情信息对象
    public Property getId( int id){
           Property property=null;
        String sql="select  * from property where id="+id;
        try (Connection connection=DBUtil.getConnection();
          PreparedStatement preparedStatement=connection.prepareStatement(sql);
        ){
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()){
                property=new Property();

                int cid = resultSet.getInt("cid");//cid获取分类的信息
                String name = resultSet.getString("name");
                property.setId(id);
                //因为要获取分类 通过Category的get方法获取分类 根据外键cid获取
                Category category=new CategoryDao().get(cid);
                //把获取数据赋值给property对象
                 property.setCategory(category);
                 property.setName(name);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return property;
    }


    //通过外键cid查询全部的详情信息并分页
    public List<Property> list(int cid){

       return  list(cid,0,Short.MAX_VALUE);

    }

   public List<Property> list(int cid, int start, int count) {
        List<Property> propertys=new ArrayList<Property>();

        String sql="select * from property where cid = ? order by id desc limit ?,?";

        try (Connection connection=DBUtil.getConnection();
          PreparedStatement preparedStatement=connection.prepareStatement(sql);
        ){
            //设置sql的参数
            preparedStatement.setInt(1,cid);
            preparedStatement.setInt(2,start);
            preparedStatement.setInt(3,count);

            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()){
                Property property=new Property();
                int id = resultSet.getInt(1);
                String name = resultSet.getString("name");

                //获取分类的信息
                 Category category=new CategoryDao().get(cid);

                //将数据赋值给property对象
                property.setId(id);
                property.setName(name);
                property.setCategory(category);

                //将property放到propertys集合
                propertys.add(property);

            }


        } catch (SQLException e) {
            e.printStackTrace();
        }
return propertys;
    }


}

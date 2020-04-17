package cn.jinronga.Dao;

import cn.jinronga.pojo.Product;
import cn.jinronga.pojo.Property;
import cn.jinronga.pojo.PropertyValue;
import cn.jinronga.util.DBUtil;


import java.sql.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: 郭金荣
 * Date: 2020/4/6 0006
 * Time: 21:58
 * E-mail:1460595002@qq.com
 * 类说明:详情信息值
 */
public class PropertyValueDao {

    //总数
    public int getTotal(){
        int total=0;
        try (Connection connection = DBUtil.getConnection(); Statement statement = connection.createStatement();
        ){
            String sql="select count(*) from propertyvalue ";
            ResultSet resultSet = statement.executeQuery(sql);

            while (resultSet.next()){

                total = resultSet.getInt(1);
            }




        } catch (SQLException e) {
            e.printStackTrace();
        }

        return total;

    }

    //添加
    public void add(PropertyValue propertyValue){
        String sql="insert into propertyvalue values (null,?,?,?)";

        try (Connection connection=DBUtil.getConnection();
             PreparedStatement preparedStatement=connection.prepareStatement(sql);
        ){
             //设置sql参数
            preparedStatement.setInt(1,propertyValue.getId());
            preparedStatement.setInt(2,propertyValue.getId());
            preparedStatement.setString(3,propertyValue.getValue());
            //执行sql
            preparedStatement.executeUpdate();

            /*获取数据库的主键
            赋值给propertyValue对象*/
            ResultSet generatedKeys = preparedStatement.getGeneratedKeys();

            if (generatedKeys.next()){
                //获取数据库的主键
                int id = generatedKeys.getInt(1);
                propertyValue.setId(id);

            }


        } catch (SQLException e) {
            e.printStackTrace();
        }


    }

    public void update(PropertyValue propertyValue) {
        String sql = "update  propertyvalue set pid=? ,ptid=?,`value`=? where id=?";
        try (Connection connection = DBUtil.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql);
        ) {
            //设置sql参数
            preparedStatement.setInt(1, propertyValue.getId());
            preparedStatement.setInt(2, propertyValue.getId());
            preparedStatement.setString(3, propertyValue.getValue());
            preparedStatement.setInt(4, propertyValue.getId());
            preparedStatement.executeUpdate();


        } catch (SQLException e) {
            e.printStackTrace();
        }


    }

     //删除
    public void delete(int id){
        String sql="delete from propertyvalue where id="+id;
        try(Connection connection=DBUtil.getConnection();
          PreparedStatement preparedStatement=connection.prepareStatement(sql);
        ) {
            preparedStatement.executeUpdate();


        } catch (SQLException e) {
            e.printStackTrace();
        }

    }


    //通过id获取详情信息值
    public PropertyValue getId(int id){
        PropertyValue propertyValue=new PropertyValue();

        String  sql="select * from propertyvalue where id="+id;
        try (Connection connection=DBUtil.getConnection();
            PreparedStatement preparedStatement=connection.prepareStatement(sql);
        ){


            ResultSet resultSet = preparedStatement.executeQuery();

            if(resultSet.next()){
                int pid = resultSet.getInt("pid");
                int ptid = resultSet.getInt("ptid");
                String value = resultSet.getString("value");
                Property property=new PropertyDao().getId(ptid);
                Product product =new ProductDao().getId(pid);


                //把数据添加到propertyValue对象
                propertyValue.setId(id);
                propertyValue.setProduct(product);
                propertyValue.setProperty(property);
                propertyValue.setValue(value);

            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

    return  propertyValue;
    }

    //根据详细信息id与产品id获取详情信息值
    public PropertyValue getId(int pid ,int ptid){
        PropertyValue propertyValue=null;
        String sql = "select * from PropertyValue where ptid = ?  and pid = ?" ;
       try (Connection connection=DBUtil.getConnection();
         PreparedStatement preparedStatement=connection.prepareStatement(sql);
       ){
           //设置sql参数
           preparedStatement.setInt(1,pid);
           preparedStatement.setInt(2,ptid);

           ResultSet resultSet = preparedStatement.executeQuery();

           if (resultSet.next()){
               propertyValue =new PropertyValue();
               int id = resultSet.getInt(1);
               String value = resultSet.getString("value");
               Product product=new ProductDao().getId(pid);
               Property property=new PropertyDao().getId(ptid);

               //将数据赋值给propertValue对象
               propertyValue.setId(id);
               propertyValue.setValue(value);
               propertyValue.setProperty(property);
               propertyValue.setProduct(product);



           }


       } catch (SQLException e) {
           e.printStackTrace();
       }

       return propertyValue;

    }

    //查询全部的详细信息值
    public List<PropertyValue> list(){

        return list(0,Short.MAX_VALUE);
    }
     //分页查询详情信息值
    private List<PropertyValue> list(int start,int count) {
           List<PropertyValue> propertyValues=new ArrayList<>();

           String sql="select * from PropertyValue order by id desc limit ?,?";
        try (Connection connection=DBUtil.getConnection();
             PreparedStatement preparedStatement=connection.prepareStatement(sql);
        ){
            preparedStatement.setInt(1,start);
            preparedStatement.setInt(2,count);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()){
                PropertyValue  propertyValue =new PropertyValue();
                int id = resultSet.getInt(1);
                int pid = resultSet.getInt("pid");
                int ptid = resultSet.getInt("ptid");
                Product product=new ProductDao().getId(pid);
                Property property = new PropertyDao().getId(ptid);
                String value = resultSet.getString("value");

                //将数据赋值给propertyValue对象
                propertyValue.setId(id);
                propertyValue.setValue(value);
                propertyValue.setProduct(product);
                propertyValue.setProperty(property);

                //propertyValue对象添加到propertyValues集合
                propertyValues.add(propertyValue);

            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
return propertyValues;
    }
    //初始化产品下的详细信息值
    public void init(Product p){
        /**
         * 思路：
         * 初始化逻辑：
         * 1.获取详细信息和分类信息
         * 2.遍历所有的详情信息
         *     根据详情信息和产品获取详情信息值
         *    如果详情信息值不存在，就创建一个对象
         */

       //通过分类类获取详情信息
        List<Property>propertyList=new PropertyDao().list(p.getCategory().getId());

        for (Property pt :propertyList){

            //获取详情信息值和产品
            PropertyValue propertyValue=getId(pt.getId(),p.getId());
            //如果没有详情详细信息值就就创建PropertyValue
            if(null==propertyValue){
                propertyValue=new PropertyValue();
                propertyValue.setProduct(p);
                propertyValue.setProperty(pt);
                this.add(propertyValue);
            }
        }
    }

    public List<PropertyValue> list(int pid) {
        List<PropertyValue> beans = new ArrayList<PropertyValue>();

        String sql = "select * from PropertyValue where pid = ? order by ptid desc";

        try (Connection c = DBUtil.getConnection(); PreparedStatement ps = c.prepareStatement(sql);) {

            ps.setInt(1, pid);

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                PropertyValue bean = new PropertyValue();
                int id = rs.getInt(1);

                int ptid = rs.getInt("ptid");
                String value = rs.getString("value");

                Product product = new ProductDao().getId(pid);
                Property property = new PropertyDao().getId(ptid);
                bean.setProduct(product);
                bean.setProperty(property);
                bean.setValue(value);
                bean.setId(id);
                beans.add(bean);
            }
        } catch (SQLException e) {

            e.printStackTrace();
        }
        return beans;
    }
}


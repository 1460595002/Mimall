package cn.jinronga.Dao;

import cn.jinronga.pojo.Category;
import cn.jinronga.util.DBUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: 郭金荣
 * Date: 2020/4/5 0005
 * Time: 16:06
 * E-mail:1460595002@qq.com
 * 类说明:分类DAO
 */
public class CategoryDao {

    //查询分类总数
    public int getTotal(){
        int total=0;
        /**
         * try()里面的资源出现异常或者程序执行完自动关闭
         */
        try(Connection connection = DBUtil.getConnection(); Statement statement =connection .createStatement();) {


                String sql="select count(*) from category";

                //Statement关闭了就会导致 ResultSet关闭
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

    public  void add(Category category) {

        String sql = "insert into category values(null,?)";

        try (Connection connection = DBUtil.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql,Statement.RETURN_GENERATED_KEYS);
        ) {
            //设置预编译sql的值
            preparedStatement.setString(1, category.getName());

             preparedStatement.executeUpdate();
            /**
             * 获取自增长主键，再把数据赋值给对象相应的属性
             */
            //获取数据库的主键
            ResultSet generatedKeys = preparedStatement.getGeneratedKeys();

            if (generatedKeys.next()) {
                int anInt = generatedKeys.getInt(1);
                //设置主键值
                category.setId(anInt);

            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }


     //更新
    public void update(Category category){
       String sql="update category set name=? where id=? ";

       try (Connection connection=DBUtil.getConnection();
            PreparedStatement preparedStatement=connection.prepareStatement(sql);
       ){
           preparedStatement.setString(1,category.getName());
           preparedStatement.setInt(2,category.getId());


           preparedStatement.executeUpdate();


       } catch (SQLException e) {
           e.printStackTrace();
       }



    }

    //删除 （通过id删除）
    public void delete(int id){

        String sql="delete from category  where id="+id; //要删除的id
        try (Connection connection=DBUtil.getConnection();
             PreparedStatement preparedStatement=connection.prepareStatement(sql);
        ){
            preparedStatement.executeUpdate();


        } catch (SQLException e) {
            e.printStackTrace();
        }


    }


    //根据id查询
    public Category get(int id){

        Category bean=null;
        String sql="select * from category where id="+id;

        try(Connection connection =DBUtil.getConnection();
          PreparedStatement preparedStatement=connection.prepareStatement(sql);
        ){
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()){
                bean=new Category();
                String name=resultSet.getString(2);
               //将查出来的数据赋值给Category对象
                bean.setId(id);
                bean.setName(name);
            }


        } catch (SQLException e) {
            e.printStackTrace();
        }

   return bean;
    }

   //查询所有
    public List<Category>list(){

        return list(0,Short.MAX_VALUE);
    }

   public List<Category> list(int start, int count) {

        List<Category> categorys= new ArrayList<Category>();

        String sql="select * from category order by id desc limit ?,?";

        try(Connection connection=DBUtil.getConnection();
          PreparedStatement preparedStatement=connection.prepareStatement(sql);
        ) {

            //设置编译的sql参数
            preparedStatement.setInt(1,start);
            preparedStatement.setInt(2,count);

            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()){
               Category category=new Category();
                //获取数据库的资源
                int id = resultSet.getInt(1);
                String name = resultSet.getString(2);
                //将数据库的数组赋值给Category对象
                category.setId(id);
                category.setName(name);

                categorys.add(category);//把对象放在集合中

            }




        } catch (SQLException e) {
            e.printStackTrace();
        }
        return categorys;
    }


    }


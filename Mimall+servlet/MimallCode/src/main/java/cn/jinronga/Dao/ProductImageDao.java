package cn.jinronga.Dao;

import cn.jinronga.pojo.Product;
import cn.jinronga.pojo.ProductImage;
import cn.jinronga.util.DBUtil;


import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: 郭金荣
 * Date: 2020/4/6 0006
 * Time: 15:57
 * E-mail:1460595002@qq.com
 * 类说明：产品图片Dao
 */
public class ProductImageDao {
    public static final String type_single = "type_single";//单个图片
    public static final String type_detail = "type_detail";//详情图片

    //总数
    public int getTotal() {
        int total = 0;
        /**
         * try()里面的资源出现异常或者程序执行完自动关闭
         */
        try (Connection connection = DBUtil.getConnection(); Statement statement = connection.createStatement();) {


            String sql = "select count(*) from productimage";

            //Statement关闭了就会导致 ResultSet关闭
            ResultSet resultSet = statement.executeQuery(sql);
            while (resultSet.next()) {
                total = resultSet.getInt(1);
            }


        } catch (SQLException e) {
            e.printStackTrace();
        }

        return total;


    }

    public void add(ProductImage productImage) {
        String sql = "insert into productimage values (null ,?,?)";


        try (Connection connection = DBUtil.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql,Statement.RETURN_GENERATED_KEYS)) {

            //设置sql参数
            preparedStatement.setInt(1, productImage.getId());
            preparedStatement.setString(2, productImage.getType());

            preparedStatement.executeUpdate();

            //获取数据库主键
            ResultSet generatedKeys = preparedStatement.getGeneratedKeys();
            if (generatedKeys.next()) {
                //将主键数据赋值给productImage对象中的id
                int id = generatedKeys.getInt(1);
                productImage.setId(id);
            }


        } catch (SQLException e) {
            e.printStackTrace();
        }


    }

    //修改图片
    public void update(ProductImage productImage) {


    }


    //删除
    public void delete(int id) {

        String sql = "delete from productimage where id=" + id;

        try (Connection connection = DBUtil.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql);
        ) {

            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }


    }

   //根据id查询
    public ProductImage getId(int id){

           ProductImage productImage=null;
        String sql="select * from productimage where id="+id;

        try (Connection connection=DBUtil.getConnection();
         PreparedStatement preparedStatement=connection.prepareStatement(sql);
        ){
            ResultSet resultSet = preparedStatement.executeQuery();


            if(resultSet.next()){
                productImage =new ProductImage();
                int pid = resultSet.getInt("pid");
                String type = resultSet.getString("type");

                productImage.setId(id);

                //获取产品信息
                Product product=new ProductDao().getId(pid);
                //将产品信息赋值给productImage对象
                productImage.setProduct(product);

                productImage.setType(type);

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
return productImage;

    }
   //查询某种类型所有图片
   public List<ProductImage> list(Product product,String type){
        return list(product,type,0,Short.MAX_VALUE);
   }
    //某种类型图片分页查询
    private List<ProductImage> list(Product product, String type, int start, int count) {
           List<ProductImage> productImages=new ArrayList<>();
             String sql="select * from productimage where pid=? and type =? order by id desc limit ?,?";

           try (Connection connection=DBUtil.getConnection();
            PreparedStatement preparedStatement=connection.prepareStatement(sql);
           ){

               preparedStatement.setInt(1,product.getId());
               preparedStatement.setString(2,type);
               preparedStatement.setInt(3,start);
               preparedStatement.setInt(4,count);

               //执行sql
               ResultSet resultSet = preparedStatement.executeQuery();

               while(resultSet.next()){
                   ProductImage productImage=new ProductImage();
                   int id = resultSet.getInt(1);//获取id
                   //将数据赋值给productImage对象
                   productImage.setId(id);
                   productImage.setType(type);
                   productImage.setProduct(product);

                   //将productImage对象放进productImage集合
                   productImages.add(productImage);
               }


           } catch (SQLException e) {
               e.printStackTrace();
           }
return productImages;
    }




}

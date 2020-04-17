package cn.jinronga.servlet;

import cn.jinronga.filter.BaseBackServlet;
import cn.jinronga.pojo.Category;
import cn.jinronga.util.ImageUtil;
import cn.jinronga.util.Page;


import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * Created with IntelliJ IDEA.
 * User: 郭金荣
 * Date: 2020/4/8 0008
 * Time: 23:51
 * E-mail:1460595002@qq.com
 * 类说明:
 */
public class CategoryServlet extends BaseBackServlet {
    @Override
    public String add(HttpServletRequest request, HttpServletResponse response, Page page) {
        HashMap<String, String> params = new HashMap<>();
        //上传文件的输入流
        InputStream inputStream = super.parseUpload(request, params);

        //获取名称
        String name=params.get("name");
        Category category=new Category();
        category.setName(name);//设置分类名称
        //名字添加到数据库
       categoryDAO.add(category);
                    //取出相对路径用于文件上传
        File imageFolder= new File(request.getSession().getServletContext().getRealPath("img/category"));
         //以id名命名存放图片
        File file=new File(imageFolder,category.getId()+".jpg");
            //获取文件输出流
        try(FileOutputStream fos = new FileOutputStream(file)) {
            //文件上传流inputStream不为空与字节不为0
            if(inputStream !=null &&inputStream.available()!=0){

                byte b[] = new byte[1024 * 1024];
                int length = 0;
                while (-1 != (length = inputStream.read(b))) {
                    fos.write(b, 0, length);
                }
                fos.flush();
                //通过如下代码，把文件保存为jpg格式
                BufferedImage img = ImageUtil.change2jpg(file);
                ImageIO.write(img, "jpg", file);
            }


        } catch (IOException e) {
            e.printStackTrace();
        }
        //@表示客户端跳转
        return "@admin_category_list";
    }

    @Override
    public String delete(HttpServletRequest request, HttpServletResponse response, Page page) {
            //获取id
        int id = Integer.parseInt(request.getParameter("id"));
        categoryDAO.delete(id);
       //@表示客户端跳转
        return "@admin_category_list";
    }

    @Override
    public String edit(HttpServletRequest request, HttpServletResponse response, Page page) {
        int id = Integer.parseInt(request.getParameter("id"));
        Category c = categoryDAO.get(id);
        request.setAttribute("c",c);


        return "admin/editCategory.jsp";
    }

    @Override
    public String update(HttpServletRequest request, HttpServletResponse response, Page page) {
             Map<String,String> params=new HashMap<>();
             InputStream is=parseUpload(request,params);
        System.out.println(params);

        int id = Integer.parseInt(params.get("id"));
        String name = params.get("name");
              Category c=new Category();
                c.setId(id);
                c.setName(name);

           categoryDAO.update(c);
        File  imageFolder= new File(request.getSession().getServletContext().getRealPath("img/category"));
        File file = new File(imageFolder,c.getId()+".jpg");
        file.getParentFile().mkdirs();

        try {
            if(null!=is && 0!=is.available()){
                try(FileOutputStream fos = new FileOutputStream(file)){
                    byte b[] = new byte[1024 * 1024];
                    int length = 0;
                    while (-1 != (length = is.read(b))) {
                        fos.write(b, 0, length);
                    }
                    fos.flush();
                    //通过如下代码，把文件保存为jpg格式
                    BufferedImage img = ImageUtil.change2jpg(file);
                    ImageIO.write(img, "jpg", file);
                }
                catch(Exception e){
                    e.printStackTrace();
                }
            }
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return "@admin_category_list";
    }

    @Override
    public String list(HttpServletRequest request, HttpServletResponse response, Page page) {

        //分页查询分类信息
        List<Category> cs = categoryDAO.list(page.getStart(), page.getCount());

        //获取分类的总数用于显示总共有多少页
        int total = categoryDAO.getTotal();
        //设置页面的总数
        page.setTotal(total);

        // 通过request.setAttribute 放在 “thecs" 这个key中，为后续服务端跳转到jsp之后使用
        request.setAttribute("thecs",cs);
        request.setAttribute("page",page);

           //return "admin/listCategory.jsp"; 服务端跳转到视图listCategory.jsp页面
        return "admin/listCategory.jsp";
    }
}

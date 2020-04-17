package cn.jinronga.servlet;

import cn.jinronga.filter.BaseBackServlet;
import cn.jinronga.pojo.Category;
import cn.jinronga.pojo.Property;
import cn.jinronga.util.Page;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: 郭金荣
 * Date: 2020/4/10 0010
 * Time: 16:36
 * E-mail:1460595002@qq.com
 * 类说明:详情信息servlet
 *
 */
public class PropertyServlet  extends BaseBackServlet {


    @Override
    public String add(HttpServletRequest request, HttpServletResponse response, Page page) throws UnsupportedEncodingException {
            //获取cid
        int cid = Integer.parseInt(request.getParameter("cid"));

        //获取分类
        Category category = categoryDAO.get(cid);
        //获取名称
        String name=request.getParameter("name");
        String str = new String(name.getBytes("ISO-8859-1"),"utf-8");
        Property p=new Property();
        //在详细信息中设置分类
        p.setCategory(category);
        //在详细信息中设置名字
        p.setName(str);
        //将信息添加到数据库
        propertyDAO.add(p);
        System.out.println("这里"+str);


        return "@admin_property_list?cid="+cid;
    }

    @Override
    public String delete(HttpServletRequest request, HttpServletResponse response, Page page) {
        int id = Integer.parseInt(request.getParameter("id"));
        Property p = propertyDAO.getId(id);
        propertyDAO.delete(id);

        return "@admin_property_list?cid="+p.getCategory().getId();
    }

    @Override
    public String edit(HttpServletRequest request, HttpServletResponse response, Page page) {
        int id = Integer.parseInt(request.getParameter("id"));
        //根据id查询所有属性
        Property p = propertyDAO.getId(id);
        request.setAttribute("p",p);


        return "admin/editProperty.jsp";
    }

    @Override
    public String update(HttpServletRequest request, HttpServletResponse response, Page page) throws UnsupportedEncodingException {

        int cid = Integer.parseInt(request.getParameter("cid"));
        Category category = categoryDAO.get(cid);

        int id = Integer.parseInt(request.getParameter("id"));
        String name = request.getParameter("name");
        //防止乱码
        String str = new String(name.getBytes("ISO-8859-1"),"utf-8");
        Property p=new Property();
        //将数据赋值给p对象
        p.setId(id);
        p.setName(str );
        p.setCategory(category);
        //将数据更新到数据库
        propertyDAO.update(p);
        return "@admin_property_list?cid="+p.getCategory().getId();
    }

    @Override
    public String list(HttpServletRequest request, HttpServletResponse response, Page page) {
          //获取id
        int cid = Integer.parseInt(request.getParameter("cid"));
        //根据cid获取分类
        Category category = categoryDAO.get(cid);
        //获取分页详细信息
        List<Property> ps = propertyDAO.list(cid, page.getStart(), page.getCount());
        //获取详细信息的总数
        int total = propertyDAO.getTotal(cid);
        //告诉分页总共有多少条数据，好显示多少页
        page.setTotal(total);
        //因为属性分页都是基于当前分类下的分页，所以分页的时候需要传递这个cid
        page.setParam("&cid="+category.getId());

        //集合反正服务端中，为跳转到前端使用
        request.setAttribute("ps",ps);
        request.setAttribute("c",category);
        request.setAttribute("page", page);

        return "admin/listProperty.jsp";
    }
}

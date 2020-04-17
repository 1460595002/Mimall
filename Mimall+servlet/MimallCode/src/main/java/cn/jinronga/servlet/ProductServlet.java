package cn.jinronga.servlet;

import cn.jinronga.filter.BaseBackServlet;
import cn.jinronga.pojo.Category;
import cn.jinronga.pojo.Product;
import cn.jinronga.pojo.Property;
import cn.jinronga.pojo.PropertyValue;
import cn.jinronga.util.Page;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: 郭金荣
 * Date: 2020/4/11 0011
 * Time: 11:00
 * E-mail:1460595002@qq.com
 * 类说明:产品Servlet
 */
public class ProductServlet  extends BaseBackServlet {


    @Override
    public String add(HttpServletRequest request, HttpServletResponse response, Page page) throws UnsupportedEncodingException {
     //获取cid
        int cid = Integer.parseInt(request.getParameter("cid"));
         //获取分类
        Category category = categoryDAO.get(cid);
        //获取产品名称
        String name = request.getParameter("name");
        //获取产品标题
        String subTitle = request.getParameter("subTitle");
        //原始价格
        float orignalPrice = Float.parseFloat(request.getParameter("orignalPrice"));
         //有货的价格
        float promotePrice = Float.parseFloat(request.getParameter("promotePrice"));
        //库存
        int stock = Integer.parseInt(request.getParameter("stock"));

        //给Product赋值
        Product p=new Product();
        p.setId(cid);
        p.setName(name);
        p.setCategory(category);
        p.setCreateDate(new Date());
        p.setPromotePrice(promotePrice);
        p.setOrignalPrice(orignalPrice);
        p.setStock(stock);
        p.setSubTitle(subTitle);
        //添加到数据库中去
        productDAO.add(p);
        return "@admin_product_list?cid="+cid;
    }

    @Override
    public String delete(HttpServletRequest request, HttpServletResponse response, Page page) {

    //获取id
        int id = Integer.parseInt(request.getParameter("id"));
        Product p = productDAO.getId(id);
         productDAO.delete(id);
        return "@admin_product_list?cid="+p.getCategory().getId();
    }

    //编辑
    @Override
    public String edit(HttpServletRequest request, HttpServletResponse response, Page page) {
      //获取id
        int id = Integer.parseInt(request.getParameter("id"));
        Product p = productDAO.getId(id);
        //赋值给服务端跳转到前端
        request.setAttribute("p",p);
        return "admin/editProduct.jsp";
    }
    public String editPropertyValue(HttpServletRequest request, HttpServletResponse response, Page page) {
        int id = Integer.parseInt(request.getParameter("id"));
        Product p = productDAO.getId(id);
        request.setAttribute("p",p);
        List<Property> pts= propertyDAO.list(p.getCategory().getId());
        propertyValueDAO.init(p);

        List<PropertyValue> pvs = propertyValueDAO.list(p.getId());
        return "admin/editProductValue.jsp";
    }
    public String updatePropertyValue(HttpServletRequest request, HttpServletResponse response, Page page) {
        int pvid = Integer.parseInt(request.getParameter("pvid"));
        String value = request.getParameter("value");

        PropertyValue pv =propertyValueDAO.getId(pvid);
        pv.setValue(value);
        propertyValueDAO.update(pv);
        return "%success";
    }
    @Override
    public String update(HttpServletRequest request, HttpServletResponse response, Page page) throws UnsupportedEncodingException {
        int cid = Integer.parseInt(request.getParameter("cid"));
        Category c = categoryDAO.get(cid);

        int id = Integer.parseInt(request.getParameter("id"));
        int stock = Integer.parseInt(request.getParameter("stock"));
        float orignalPrice = Float.parseFloat(request.getParameter("orignalPrice"));
        float promotePrice = Float.parseFloat(request.getParameter("promotePrice"));
        String subTitle= request.getParameter("subTitle");
        String name= request.getParameter("name");

        Product p = new Product();

        p.setName(name);
        p.setSubTitle(subTitle);
        p.setOrignalPrice(orignalPrice);
        p.setPromotePrice(promotePrice);
        p.setStock(stock);
        p.setId(id);
        p.setCategory(c);

        productDAO.update(p);
        return "@admin_product_list?cid="+p.getCategory().getId();
    }

    @Override
    public String list(HttpServletRequest request, HttpServletResponse response, Page page) {
        int cid = Integer.parseInt(request.getParameter("cid"));
        //获取分类
        Category category = categoryDAO.get(cid);
        //产品集合
        List<Product> ps= productDAO.list(cid,page.getStart(),page.getCount());
           //产品总数
        int total = productDAO.getTotal(cid);
        page.setTotal(total);
        page.setParam("$cid="+category.getId());

        //作为服务端跳转页面
        request.setAttribute("ps",ps);
         request.setAttribute("page",page);
           request.setAttribute("c",category);
        return "admin/listProduct.jsp";
    }
}

package cn.jinronga.servlet;

import cn.jinronga.Dao.*;
import cn.jinronga.util.Page;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;

/**
 * Created with IntelliJ IDEA.
 * User: 郭金荣
 * Date: 2020/4/12 0012
 * Time: 10:23
 * E-mail:1460595002@qq.com
 * 类说明:
 */
public class BaseForeServlet extends HttpServlet {
    protected CategoryDao categoryDAO = new CategoryDao ();
    protected OrderDao orderDAO = new OrderDao ();
    protected OrderItemDao orderItemDAO = new OrderItemDao ();
    protected ProductDao productDAO = new ProductDao ();
    protected ProductImageDao  productImageDAO = new ProductImageDao ();
    protected PropertyDao  propertyDAO = new PropertyDao ();
    protected PropertyValueDao propertyValueDAO = new PropertyValueDao ();
    protected ReviewDAO reviewDAO = new ReviewDAO();
    protected UserDao  userDAO = new UserDao ();

    public void service(HttpServletRequest request, HttpServletResponse response) {
        try {

            int start= 0;
            int count = 10;
            try {
                start = Integer.parseInt(request.getParameter("page.start"));
            } catch (Exception e) {

            }

            try {
                count = Integer.parseInt(request.getParameter("page.count"));
            } catch (Exception e) {
            }

            Page page = new Page(start,count);

            String method = (String) request.getAttribute("method");

            Method m = this.getClass().getMethod(method, javax.servlet.http.HttpServletRequest.class,
                    javax.servlet.http.HttpServletResponse.class,Page.class);

            String redirect = m.invoke(this,request, response,page).toString();

            if(redirect.startsWith("@"))
                response.sendRedirect(redirect.substring(1));
            else if(redirect.startsWith("%"))
                response.getWriter().print(redirect.substring(1));
            else
                request.getRequestDispatcher(redirect).forward(request, response);

        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }
}

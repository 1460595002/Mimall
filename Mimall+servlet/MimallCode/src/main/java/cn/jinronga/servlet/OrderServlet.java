package cn.jinronga.servlet;

import cn.jinronga.Dao.OrderDao;
import cn.jinronga.filter.BaseBackServlet;
import cn.jinronga.pojo.Order;
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
 * Time: 22:37
 * E-mail:1460595002@qq.com
 * 类说明:
 */
public class  OrderServlet extends BaseBackServlet {
    public String add(HttpServletRequest request, HttpServletResponse response, Page page) {
        return null;
    }

    public String delete(HttpServletRequest request, HttpServletResponse response, Page page) {
        return null;
    }
    public String delivery(HttpServletRequest request, HttpServletResponse response, Page page) {
        int id = Integer.parseInt(request.getParameter("id"));
        Order o = orderDAO.getId(id);
        o.setDeliverDate(new Date());
        o.setStatus(OrderDao.waitConfirm);
        orderDAO.update(o);
        return "@admin_order_list";
    }

    public String edit(HttpServletRequest request, HttpServletResponse response, Page page) {
        return null;
    }

    public String update(HttpServletRequest request, HttpServletResponse response, Page page) {
        return null;
    }

    public String list(HttpServletRequest request, HttpServletResponse response, Page page) {
        List<Order> os = orderDAO.list(page.getStart(),page.getCount());
        orderItemDAO.fill(os);
        int total = orderDAO.getTotal();
        page.setTotal(total);

        request.setAttribute("os", os);
        request.setAttribute("page", page);

        return "admin/listOrder.jsp";
    }
}
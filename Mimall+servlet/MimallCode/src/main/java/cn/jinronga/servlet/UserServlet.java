package cn.jinronga.servlet;

import cn.jinronga.filter.BaseBackServlet;
import cn.jinronga.pojo.User;
import cn.jinronga.util.Page;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: 郭金荣
 * Date: 2020/4/11 0011
 * Time: 22:13
 * E-mail:1460595002@qq.com
 * 类说明:
 */
public class UserServlet   extends BaseBackServlet {
    @Override
    public String add(HttpServletRequest request, HttpServletResponse response, Page page) throws UnsupportedEncodingException {
        return null;
    }

    @Override
    public String delete(HttpServletRequest request, HttpServletResponse response, Page page) {
        return null;
    }

    @Override
    public String edit(HttpServletRequest request, HttpServletResponse response, Page page) {
        return null;
    }

    @Override
    public String update(HttpServletRequest request, HttpServletResponse response, Page page) throws UnsupportedEncodingException {
        return null;
    }

    @Override
    public String list(HttpServletRequest request, HttpServletResponse response, Page page) {
        List<User> us = userDAO.list(page.getStart(),page.getCount());
        int total = userDAO.getTotal();
        page.setTotal(total);

        request.setAttribute("us", us);
        request.setAttribute("page", page);

        return "admin/listUser.jsp";
    }
}

package cn.jinronga.filter;

import cn.jinronga.Dao.CategoryDao;
import cn.jinronga.Dao.OrderItemDao;
import cn.jinronga.pojo.Category;
import cn.jinronga.pojo.Order;
import cn.jinronga.pojo.OrderItem;
import cn.jinronga.pojo.User;
import cn.jinronga.util.Page;
import org.apache.commons.lang3.StringUtils;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: 郭金荣
 * Date: 2020/4/12 0012
 * Time: 10:15
 * E-mail:1460595002@qq.com
 * 类说明:
 */
public class ForeServletFilter implements Filter{

    @Override
    public void destroy() {

    }

    @Override
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain)
            throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) res;
        String contextPath=request.getServletContext().getContextPath();
        request.getServletContext().setAttribute("contextPath", contextPath);

        User user =(User) request.getSession().getAttribute("user");
        int cartTotalItemNumber= 0;
        if(null!=user){
            List<OrderItem> ois = new OrderItemDao().listByUser(user.getId());
            for (OrderItem oi : ois) {
                cartTotalItemNumber+=oi.getNumber();
            }
        }
        request.setAttribute("cartTotalItemNumber", cartTotalItemNumber);

        List<Category> cs=(List<Category>) request.getAttribute("cs");
        if(null==cs){
            cs=new CategoryDao().list();
            request.setAttribute("cs", cs);
        }

        String uri = request.getRequestURI();
        uri = StringUtils.remove(uri, contextPath);
        if(uri.startsWith("/fore")&&!uri.startsWith("/foreServlet")){
            String method = StringUtils.substringAfterLast(uri,"/fore" );
            request.setAttribute("method", method);
            req.getRequestDispatcher("/foreServlet").forward(request, response);
            return;
        }

        chain.doFilter(request, response);
    }

    @Override
    public void init(FilterConfig arg0) throws ServletException {

    }


}

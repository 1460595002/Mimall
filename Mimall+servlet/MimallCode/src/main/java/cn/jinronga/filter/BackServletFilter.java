package cn.jinronga.filter;

import org.apache.commons.lang3.StringUtils;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created with IntelliJ IDEA.
 * User: 郭金荣
 * Date: 2020/4/8 0008
 * Time: 21:30
 * E-mail:1460595002@qq.com
 * 类说明:
 */
public class  BackServletFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request=(HttpServletRequest) servletRequest;
        HttpServletResponse response=(HttpServletResponse) servletResponse;

        //获取上下文路径，也就是项目路径
        String contextPath=request.getServletContext().getContextPath();
        //获取uti
        String uri = request.getRequestURI();
        //将uri中的上下文路径也就是项目路径去掉
         uri = StringUtils.remove(uri, contextPath);
         //判断uil是否以admin_开头
        if(uri.startsWith("/admin_")){
            // 如果是，那么就取出两个_之间的字符串，假设category，并且拼接成/categoryServlet，通过服务端跳转到/categoryServlet

            String servletPath = StringUtils.substringBetween(uri,"_", "_") + "Servlet";
            String method = StringUtils.substringAfterLast(uri,"_" );
            //在跳转之前，还取出了list字符串，然后通过request.setAttribute的方式，借助服务端跳转，传递到categoryServlet里去
            request.setAttribute("method", method);
             servletRequest.getRequestDispatcher("/" + servletPath).forward(request, response);

            return;
        }

        //如果不是admin_开头放行
        filterChain.doFilter(request, response);
    }

    @Override
    public void destroy() {

    }
}

package cn.jinronga.filter;

import cn.jinronga.Dao.*;
import cn.jinronga.util.Page;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Method;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: 郭金荣
 * Date: 2020/4/8 0008
 * Time: 23:44
 * E-mail:1460595002@qq.com
 * 类说明:
 */
public abstract class BaseBackServlet extends HttpServlet {

    public abstract String add(HttpServletRequest request, HttpServletResponse response, Page page) throws UnsupportedEncodingException;
    public abstract String delete(HttpServletRequest request, HttpServletResponse response, Page page) ;
    public abstract String edit(HttpServletRequest request, HttpServletResponse response, Page page) ;
    public abstract String update(HttpServletRequest request, HttpServletResponse response, Page page) throws UnsupportedEncodingException;
    public abstract String list(HttpServletRequest request, HttpServletResponse response, Page page) ;

    protected CategoryDao categoryDAO = new CategoryDao();
    protected OrderDao orderDAO = new OrderDao();
    protected OrderItemDao orderItemDAO = new OrderItemDao();
    protected ProductDao productDAO = new ProductDao();
    protected ProductImageDao productImageDAO = new ProductImageDao();
    protected PropertyDao propertyDAO = new PropertyDao();
    protected PropertyValueDao propertyValueDAO = new PropertyValueDao();
    protected ReviewDAO reviewDAO = new ReviewDAO();
    protected UserDao userDAO = new UserDao();

    public void service(HttpServletRequest request, HttpServletResponse response) {
        try {

            /*获取分页信息*/
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

            /*借助反射，调用对应的方法*/
            String method = (String) request.getAttribute("method");
            Method m = this.getClass().getMethod(method, javax.servlet.http.HttpServletRequest.class,
                    javax.servlet.http.HttpServletResponse.class,Page.class);
            String redirect = m.invoke(this,request, response,page).toString();

            /*根据方法的返回值，进行相应的客户端跳转，服务端跳转，或者仅仅是输出字符串*/

            //如果是以@开头的字符串，那么进行客户端跳转
            if(redirect.startsWith("@"))
                response.sendRedirect(redirect.substring(1));
//            如果redirect是以%开头的字符串，那么就直接输出字符串
            else if(redirect.startsWith("%"))
                response.getWriter().print(redirect.substring(1));
            else
                //实现服务端 页面跳转
                request.getRequestDispatcher(redirect).forward(request, response);

        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }
    //上传文件
    public InputStream parseUpload(HttpServletRequest request, Map<String, String> params) {
        InputStream is =null;
        try {
            //DiskFileItemFactory创建对象工厂，
            DiskFileItemFactory factory = new DiskFileItemFactory();
            //实现文件上传
            ServletFileUpload upload = new ServletFileUpload(factory);
            // 设置上传文件的大小限制为10M
            factory.setSizeThreshold(1024 * 10240);
             //解析请求正文内容
            List items = upload.parseRequest(request);
            Iterator iter = items.iterator();
            while (iter.hasNext()) {
                FileItem item = (FileItem) iter.next();
                if (!item.isFormField()) {    //isFormField()：判断是否是普通字段 如果不是就获取文件输入流
                    // item.getInputStream() 获取上传文件的输入流
                    is = item.getInputStream();
                } else {
                    //获取普通字段的字段名
                    String paramName = item.getFieldName();
                    //获取普通字段的值
                    String paramValue = item.getString();
                    //解决乱码问题
                    paramValue = new String(paramValue.getBytes("ISO-8859-1"), "UTF-8");
                    //放在Map中因为有上传的文件流，还有文件字段名称
                    params.put(paramName, paramValue);
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return is;
    }
}
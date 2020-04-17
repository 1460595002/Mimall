package cn.jinronga.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Created with IntelliJ IDEA.
 * User: 郭金荣
 * Date: 2020/4/5 0005
 * Time: 15:21
 * E-mail:1460595002@qq.com
 * 类说明:数据库驱动连接类
 */
public class DBUtil {

        static String ip = "localhost";//连接地址
        static int port = 3306; //端口
        static String database = "mimall";//连接的数据
        static String encoding = "UTF-8"; //字节码
        static String loginName = "root";//账户
        static String password = "1460595002"; //密码

        static {
            try {
                Class.forName("com.mysql.jdbc.Driver");
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        }

        public static Connection getConnection() throws SQLException {
            String url = String.format("jdbc:mysql://%s:%d/%s?characterEncoding=%s", ip, port, database, encoding);
            return DriverManager.getConnection(url, loginName, password);
        }

        public static void main(String[] args) throws SQLException {
            System.out.println(getConnection());

        }

    }

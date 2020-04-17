package cn.jinronga.util;

/**
 * Created with IntelliJ IDEA.
 * User: 郭金荣
 * Date: 2020/4/5 0005
 * Time: 15:29
 * E-mail:1460595002@qq.com
 * 类说明:时间类相互转换
 */
public class DateUtil {

    public static java.util.Date  date(java.sql.Timestamp timestamp){

        if (timestamp==null){
            return null;
        }

        return new java.util.Date(timestamp.getTime());
    }

    public  static  java.sql.Timestamp date1(java.util.Date  d){
        if (d==null){
            return null;
        }
        return new java.sql.Timestamp(d.getTime());

    }
}

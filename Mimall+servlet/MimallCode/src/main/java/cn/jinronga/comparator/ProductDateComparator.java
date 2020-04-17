package cn.jinronga.comparator;

import cn.jinronga.pojo.Product;

import java.util.Comparator;

/**
 * Created with IntelliJ IDEA.
 * User: 郭金荣
 * Date: 2020/4/13 0013
 * Time: 21:59
 * E-mail:1460595002@qq.com
 * 类说明:
 */
public class ProductDateComparator implements Comparator<Product> {
    @Override
    public int compare(Product o1, Product o2) {
        return o1.getCreateDate().compareTo(o2.getCreateDate());
    }
}

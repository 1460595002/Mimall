package cn.jinronga.comparator;

import cn.jinronga.pojo.Product;

import java.util.Comparator;

/**
 * Created with IntelliJ IDEA.
 * User: 郭金荣
 * Date: 2020/4/13 0013
 * Time: 21:45
 * E-mail:1460595002@qq.com
 * 类说明:
 */
public class ProductAllComparator  implements Comparator<Product> {
    @Override
    public int compare(Product p1, Product p2) {
        return p2.getReviewCount()*p2.getSaleCount()-p1.getReviewCount()*p1.getSaleCount();
    }
}

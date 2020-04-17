package cn.jinronga.util;

/**
 * Created with IntelliJ IDEA.
 * User: 郭金荣
 * Date: 2020/4/8 0008
 * Time: 23:49
 * E-mail:1460595002@qq.com
 * 类说明:分页工具类
 */
public class Page {

    //开始位置
    int start;
    //每页显示多少
    int count;
    //数据总数
    int total;
//    因为属性分页都是基于当前分类下的分页，所以分页的时候需要传递这个cid
    String param;
    public int getStart() {
        return start;
    }
    public void setStart(int start) {
        this.start = start;
    }
    public int getCount() {
        return count;
    }
    public void setCount(int count) {
        this.count = count;
    }
    public Page(int start, int count) {
        super();
        this.start = start;
        this.count = count;
    }

    public boolean isHasPreviouse(){
        if(start==0)
            return false;
        return true;

    }
    public boolean isHasNext(){
        if(start==getLast())
            return false;
        return true;
    }

    //总共有多少页
    public int getTotalPage(){
        int totalPage;
        // 假设总数是50，是能够被5整除的，那么就有10页
        if (0 == total % count)
            totalPage = total /count;
            // 假设总数是51，不能够被5整除的，那么就有11页
        else
            totalPage = total / count + 1;
        //如果页面没有数据页面就是1
        if(0==totalPage)
            totalPage = 1;
        return totalPage;

    }


    //最后一页
    public int getLast(){
        int last;
        // 假设总数是50，是能够被5整除的，那么最后一页的开始就是45
        if (0 == total % count)
            last = total - count;
            // 假设总数是51，不能够被5整除的，那么最后一页的开始就是50
        else
            last = total - total % count;

        last = last<0?0:last;
        return last;
    }

    public int getTotal() {
        return total;
    }
    public void setTotal(int total) {
        this.total = total;
    }
    public String getParam() {
        return param;
    }
    public void setParam(String param) {
        this.param = param;
    }
}

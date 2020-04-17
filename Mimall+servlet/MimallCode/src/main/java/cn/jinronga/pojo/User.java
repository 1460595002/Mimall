package cn.jinronga.pojo;

/**
 * Created with IntelliJ IDEA.
 * User: 郭金荣
 * Date: 2020/4/5 0005
 * Time: 10:52
 * E-mail:1460595002@qq.com
 * 类说明:用户实体类
 */
public class User {

    //用户id
    private int id;
    //用户名称
    private String name;
    //密码
    private  String password;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    //用于产品评价时显示匿名信息
    public String getAnonymousName(String name){

        if(name==null){

            return null;
        }

        //如果用户名以为直接就是*
        if (name.length()==1){
            return "*";
        }

        //如果用户名为两位后面的位*
        if (name.length()<=2){

         return    name.substring(0,1)+"*";//这样就变成了两位第二位就变成了*


        }

        //如果长度大于2，除了前后两个字显示中间全部变成*
               //把名称变为字符数组
        char[] chars = name.toCharArray();

        //除了最前面最后面全部变成*
        for (int i = 1; i <name.length()-1 ; i++) {

               chars[i]='*';

        }
        return new String(chars);



    }
}

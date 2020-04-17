### jsp包含关系：

 adminHeader.jsp ：

1. 表示本页面会使用html5的技术 

```html
<!DOCTYPE html>
```

2.jsp指令:

```html
 
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" isELIgnored="false"%>
```

 contentType="text/html; charset=UTF-8" 告诉浏览器使用UTF-8进行中文编码识别
pageEncoding="UTF-8" 本jsp上的中文文字，使用UTF-8进行编码
isELIgnored="false" 本jsp上会使用EL表达式 

3. 引入JSTL 

```html
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix='fmt' %> 
```

使用c和fmt两种标准标签库

4. 引入js和css 

```html
 
<script src="js/jquery/2.0.0/jquery.min.js"></script>
<link href="css/bootstrap/3.3.6/bootstrap.min.css" rel="stylesheet">
<script src="js/bootstrap/3.3.6/bootstrap.min.js"></script>
<link href="css/back/style.css" rel="stylesheet">
```

5. 预先定义一些判断输入框的函数，方便后面使用 

   **注意看注释：**

   判断名称是否为空

```javascript
//判断名称是否为空
 function checkEmpty(id, name){
            //获取id选择器的值
            var value = $("#"+id).val();
            if(value.length==0){
                alert(name+ "不能为空");
                $("#"+id)[0].focus();  //获取焦点，表示光标自动放在组件上无需操作
                return false;
            }
            return true;
        }
```

判断名称为空的 ，同时判断是否为数字

```javascript
//判断名称为空的 ，同时判断是否为数字
function checkNumber(id, name){
            //获取id选择器的值
            var value = $("#"+id).val();
            if(value.length==0){
                alert(name+ "不能为空");
                $("#"+id)[0].focus();
                return false;
            }
            //如果value不为数字 false
            if(isNaN(value)){
                //获取id选择器的值
                alert(name+ "必须是数字");
                $("#"+id)[0].focus();
                return false;
            }

            return true;
        }
```



判断名称为空的 ，同时判断是否为整数

```javascript
//判断名称为空的 ，同时判断是否为整数
        function checkInt(id, name){
            //获取id选择器的值
            var value = $("#"+id).val();
            if(value.length==0){
                alert(name+ "不能为空");
                $("#"+id)[0].focus();
                return false;
            }
            //如果value不为整数  false
            if(parseInt(value)!=value){
                alert(name+ "必须是整数");
                $("#"+id)[0].focus();
                return false;
            }

            return true;
        }
```

给删除超链时加提示信息

```javascript
  //给删除超链时加提示信息
        $(function(){

            $("a").click(function(){
                   //attr()：设置或返回被选元素的属性值
                var deleteLink = $(this).attr("deleteLink");
                console.log(deleteLink);
                //"true"==deleteLink 提示：确认要删除
                if("true"==deleteLink){
                     //confirm()显示一个带有指定消息和 OK 及取消按钮的对话框
                    var confirmDelete = confirm("确认要删除");
                    if(confirmDelete)
                        return true;
                    return false;

                }
            });
        })
```

####  listCategory.jsp 

工作原理：

 作为视图，担当的角色是显示数据。所以关键就是从第44行开始，借助**JSTL**的c:forEach标签遍历从CategoryServlet的list() 的request.setAttribute("**thecs**", cs); 传递过来的集合。 

```jsp
<c:forEach items="${thecs}" var="c">
```

```jsp
<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" import="java.util.*"%>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@include file="../include/admin/adminHeader.jsp"%>
<%@include file="../include/admin/adminNavigator.jsp"%>

<script>
    $(function(){
          //取出id为addForm的选择器
        $("#addForm").submit(function(){
            if(!checkEmpty("name","分类名称"))
                return false;
            if(!checkEmpty("categoryPic","分类图片"))
                return false;
            return true;
        });
    });

</script>

<title>分类管理</title>

<div class="workingArea">
    <h1 class="label label-info" >分类管理</h1>
    <br>
    <br>

    <div class="listDataTableDiv">
        <table class="table table-striped table-bordered table-hover  table-condensed">
            <thead>
            <tr class="active">
                <th>ID</th>
                <th>图片</th>
                <th>分类名称</th>
                <!--                     <th>详情管理</th> -->
                <!--                     <th>产品管理</th> -->
                <th>编辑</th>
                <th>删除</th>
            </tr>
            </thead>
            <tbody>
            <c:forEach items="${thecs}" var="c">

                <tr>
<%--                    显示分类id--%>
                    <td>${c.id}</td>
<%--    显示分类的图片--%>
                    <td><img height="40px" src="img/category/${c.id}.jpg"></td>
<%--    显示分类名称--%>
                    <td>${c.name}</td>

                        <%--                     <td><a href="admin_property_list?cid=${c.id}"><span class="glyphicon glyphicon-th-list"></span></a></td>                     --%>
                        <%--                     <td><a href="admin_product_list?cid=${c.id}"><span class="glyphicon glyphicon-shopping-cart"></span></a></td>                    --%>
                                 <%--   编辑：                   --%>
                    <td><a href="admin_category_edit?id=${c.id}"><span class="glyphicon glyphicon-edit"></span></a></td>
                              <%--             删除： categoryDao中的delete会执行     并且传递当前id进去           --%>
                    <td><a deleteLink="true" href="admin_category_delete?id=${c.id}"><span class="   glyphicon glyphicon-trash"></span></a></td>

                </tr>
            </c:forEach>
            </tbody>
        </table>
    </div>

    <div class="pageDiv">
        <%@include file="../include/admin/adminPage.jsp" %>
    </div>

    <div class="panel panel-warning addDiv">
        <div class="panel-heading">新增分类</div>
        <div class="panel-body">
<%--           提交方式post 提交位置admin_category_add 就会调用categoryDao的add方法--%>
            <form method="post" id="addForm" action="admin_category_add" enctype="multipart/form-data">
                <table class="addTable">
                    <tr>
                        <td>分类名称</td>
                        <td><input  id="name" name="name" type="text" class="form-control"></td>
                    </tr>
                    <tr>
                        <td>分类图片</td>
                        <td>
                            <input id="categoryPic" accept="image/*" type="file" name="filepath" />
                        </td>
                    </tr>
                    <tr class="submitTR">
                        <td colspan="2" align="center">
                            <button type="submit" class="btn btn-success">提 交</button>
                        </td>
                    </tr>
                </table>
            </form>
        </div>
    </div>

</div>

<%@include file="../include/admin/adminFooter.jsp"%>
```

#### 分页：adminPage

```html
<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" isELIgnored="false"%>

<script>
    $(function(){
        $("ul.pagination li.disabled a").click(function(){
            return false;
        });
    });

</script>

<nav>
    <ul class="pagination">
        <li <c:if test="${!page.hasPreviouse}">class="disabled"</c:if>>
<%--            首页--%>
            <a  href="?page.start=0${page.param}" aria-label="Previous" >
                <span aria-hidden="true">«</span>
            </a>
        </li>

        <li <c:if test="${!page.hasPreviouse}">class="disabled"</c:if>>
<%--            上一页--%>
            <a  href="?page.start=${page.start-page.count}${page.param}" aria-label="Previous" >
                <span aria-hidden="true">‹</span>
            </a>
        </li>

        <c:forEach begin="0" end="${page.totalPage-1}" varStatus="status">

            <c:if test="${status.count*page.count-page.start<=20 && status.count*page.count-page.start>=-10}">
                <li <c:if test="${status.index*page.count==page.start}">class="disabled"</c:if>>

<%--                    中间页--%>
                    <a href="?page.start=${status.index*page.count}${page.param}"
                            <c:if test="${status.index*page.count==page.start}">class="current"</c:if>
                    >${status.count}</a>
                </li>
            </c:if>
        </c:forEach>

        <li <c:if test="${!page.hasNext}">class="disabled"</c:if>>
<%--            下一页--%>
            <a href="?page.start=${page.start+page.count}${page.param}" aria-label="Next">
                <span aria-hidden="true">›</span>
            </a>
        </li>
        <li <c:if test="${!page.hasNext}">class="disabled"</c:if>>
<%--            最后一页--%>
            <a href="?page.start=${page.last}${page.param}" aria-label="Next">
                <span aria-hidden="true">»</span>
            </a>
        </li>
    </ul>
</nav>
```

对边界做了一些处理当没有上一页，下一页的时候就不能点击，比如：

```html
  <li <c:if test="${!page.hasPreviouse}">class="disabled"</c:if>>
      <a  href="?page.start=0${page.param}" aria-label="Previous" >
        <span aria-hidden="true">&laquo;</span>
      </a>
    </li>
```

#### 文件上传：add


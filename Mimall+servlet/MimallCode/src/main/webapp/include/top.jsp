<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" isELIgnored="false"%>

<nav class="top ">
    <a href="${contextPath}">
        <span style="color:#C40000;margin:0px" class=" glyphicon glyphicon-home redColor"></span>
        小米商城
    </a>
    <a href="https://www.miui.com/"> <span>MIUI</span></a>
    <a href="https://i.mi.com/"> <span>金融</span></a>
    <a href="https://i.mi.com/"> <span>有品</span></a>
    <a href="https://i.mi.com/"> <span>小爱开放平台</span></a>
    <a href="https://i.mi.com/"> <span>企业团购</span></a>
    <a href="https://i.mi.com/"> <span>资质团购</span></a>
    <a href="https://i.mi.com/"> <span>协议规则</span></a>




    <c:if test="${!empty user}">
        <a href="login.jsp">${user.name}</a>
        <a href="forelogout">退出</a>
    </c:if>



    <span class="pull-right">
          <c:if test="${empty user}">
              <a href="login.jsp">请登录</a>
              <a href="register.jsp">免费注册</a>
          </c:if>
            <a href="forebought">我的订单</a>
            <a href="forecart">
            <span style="color:#C40000;margin:0px" class=" glyphicon glyphicon-shopping-cart redColor"></span>
            购物车<strong>${cartTotalItemNumber}</strong>件</a>
        </span>

</nav>
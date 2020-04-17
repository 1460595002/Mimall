<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" isELIgnored="false"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix='fmt' %>

<html>

<head>
    <script src="js/jquery/2.0.0/jquery.min.js"></script>
    <link href="css/bootstrap/3.3.6/bootstrap.min.css" rel="stylesheet">
    <script src="js/bootstrap/3.3.6/bootstrap.min.js"></script>
    <link href="css/back/style.css" rel="stylesheet">

    <script>

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
    </script>
</head>
<body>
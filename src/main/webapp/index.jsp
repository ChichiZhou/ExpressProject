<%--
  Created by IntelliJ IDEA.
  User: hezhou
  Date: 2021-01-03
  Time: 14:05
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>

<html lang="zh-CN">
<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1">
    <link rel="stylesheet" href="./admin/assets/css/layui.css">
    <link rel="stylesheet" href="admin/assets/css/login.css">
    <link rel="icon" href="/favicon.ico">
    <title>快递e栈管理后台</title>
</head>
<body class="login-wrap">
<div class="login-container">
    <h3>快递e栈后台管理</h3>
    <form class="login-form" action="admin/index.html">
        <div class="input-group">
            <input type="text" id="username" class="input-field">
            <label for="username" class="input-label">
                <span class="label-title">用户名</span>
            </label>
        </div>
        <div class="input-group">
            <input type="password" id="password" class="input-field">
            <label for="password" class="input-label">
                <span class="label-title">密码</span>
            </label>
        </div>
        <button type="button" class="login-button">登录<i class="ai ai-enter"></i></button>
    </form>
</div>
</body>
<script src="admin/assets/layui.js"></script>
<script src="admin/js/index.js" data-main="login"></script>
<script src="admin/js/login.js" data-main="login"></script>
<script src="qrcode/jquery2.1.4.js"></script>
<script src="layer/layer.js"></script>
<script>
    $(function(){
        $(".login-button").click(function(){
            var username = $("#username").val();
            var password = $("#password").val();
            //1.    先使用layer，弹出load（提示加载中...）
            var windowId = layer.load();
            //2.    ajax与服务器交互。发起 ajax
            $.post("./admin/login.do",{username:username,password:password},function(data){
                //3.    关闭load窗口
                layer.close(windowId);
                //4.    将服务器回复的结果进行显示
                layer.msg(data.result);
                if(data.status == 0){
                    // 跳转到主页
                    // 重定向
                    location.assign("admin/index.html");
                }
                //
            },"JSON");

        });
    });
</script>
</html>

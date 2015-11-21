<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
    <title>傲美订单管理系统登陆页面</title>
    <meta name="keywords" content="登录页面" />
    <meta name="description" content="傲美订单管理系统登陆页面" />
    <link href="../bootstrap/css/bootstrap.min.css" rel="stylesheet"/>
    <link href="../bootstrap/css/bootstrap-theme.min.css" rel="stylesheet"/>
    <style type="text/css">
        .form-signin {
            max-width: 330px;
            padding: 5px;
            margin: 0 auto;
        }
        .input-mb {
            margin-bottom: 5px;
        }
    </style>
</head>
<body>
<nav class="navbar navbar-default" role="navigation">
    <div class="navbar-header">
        <a class="navbar-brand" href="javascript:void(0)">傲美订单管理系统</a>
    </div>
    <div>
</nav>
<div class="container">
    <h2 class="page-header text-center">欢迎登录</h2>
    <form class="form-signin" role="form" name="login" action="goHome.htm" method="post">
        <div class="input-group input-mb">
             <span class="input-group-addon">
             <span class="glyphicon glyphicon-user"></span>
             </span>
            <input type="text" name="user.username" id="name"  class="form-control" placeholder="请输入用户名" required autofocus>
        </div>
        <div class="input-group input-mb">
             <span class="input-group-addon">
             <span class="glyphicon glyphicon-lock"></span>
             </span>
            <input type="password" name="user.pwd" id="password" class="form-control" placeholder="请输入登录密码" required autofocus>
        </div>
        <button class="btn btn-lg btn-primary btn-block" type="submit">登录</button>
    </form>
</div>
<div class="panel-footer navbar-fixed-bottom text-center">
    Copyright©2014-2015 上海市傲美服饰辅料有限公司 版权所有
</div>
</body>
</html>

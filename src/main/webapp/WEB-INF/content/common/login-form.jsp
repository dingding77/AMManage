<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    String contextPath = request.getContextPath().equals("/")?"":request.getContextPath();
%>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
    <title>傲美订单管理系统登陆页面</title>
    <meta name="generator" content="MShtml 8.00.6001.18783" />
    <meta name="copyright" content="Copyright 2014 - L-Sky.Cn" />
    <meta name="Author" content="悠然天空网络科技有限公司 - L-Sky.Cn" />
    <meta name="keywords" content="登录页面" />
    <meta name="description" content="傲美订单管理系统登陆页面" />
    <link rel="stylesheet" type="text/css" href="<%=contextPath %>/css/style.css" />
    <style type="text/css">
        .download{margin:20px 33px 10px;*margin-bottom:30px;padding:5px;border-radius:3px;-webkit-border-radius:3px;-moz-border-radius:3px;background:#e6e6e6;border:1px dashed #df0031;font-size:14px;font-family:Comic Sans MS;font-weight:bolder;color:#555}
        .download a{padding-left:5px;font-size:14px;font-weight:normal;color:#555;text-decoration:none;letter-spacing:1px}
        .download a:hover{text-decoration:underline;color:#36F}
        .download span{float:right}
        .am_header{margin:80px auto 0;color: white;width:330px;text-align: center; font-size: 25px; font-weight: bold;}
    </style>
</head>

<body>
<div class="main">
    <div class="am_header"> 傲美订单管理系统 Beta 1.0 </div>
    <div class="content" style="margin-top: 25px;">
        <div class="title hide">欢迎登录</div>
        <div style="color: red; width: 300px; margin: auto; text-align: center;">${requestScope.errorMsg}</div>
        <form name="login" action="goHome.htm" method="post">
            <fieldset>
                <div class="input">
                    <input class="input_all name" style="height: 50px; font-size: 15px; font-weight: bold;" name="user.username" id="name" placeholder="用户名" type="text" onFocus="this.className='input_all name_now';" onBlur="this.className='input_all name'" maxLength="24" />
                </div>
                <div class="input">
                    <input class="input_all password" style="height: 50px; font-size: 15px;" name="user.pwd" id="password" type="password" placeholder="密码" onFocus="this.className='input_all password_now';" onBlur="this.className='input_all password'" maxLength="24" />
                </div>
                <div class="enter">
                    <input class="button hide" name="submit" type="submit" value="登录" />
                </div>
            </fieldset>
        </form>
    </div>
</div>
<script type="text/javascript" src="<%=contextPath %>/js/jquery-1.7.2.min.js"></script>
<script type="text/javascript" src="<%=contextPath %>/js/placeholder.js"></script>
<!--[if IE 6]>
<script type="text/javascript" src="<%=contextPath %>/js/belatedpng.js" ></script>
<script type="text/javascript">
    DD_belatedPNG.fix("*");
</script>
<![endif]--></body>
</html>


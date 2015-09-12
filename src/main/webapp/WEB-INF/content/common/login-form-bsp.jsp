<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    String contextPath = request.getContextPath().equals("/")?"":request.getContextPath();
%>
<html>
<head lang="en">
    <meta name="viewport" content="width=device-width, inital-scale-1.0"  charset="UTF-8">
    <title>Login</title>
    <script type="text/javascript" src="<%=contextPath %>/js/jquery.min.js"></script>
    <script type="text/javascript" src="<%=contextPath %>/bootstrap-3.3.4/js/bootstrap.min.js"></script>
    <link type="text/css" href="<%=contextPath %>/bootstrap-3.3.4/css/bootstrap.min.css" rel="stylesheet"/>
    <link type="text/css" href="<%=contextPath %>/bootstrap-3.3.4/css/bootstrap-theme.min.css" rel="stylesheet"/>
    <!-- HTML5 shim and Respond.js IE8 support of HTML5 elements and media queries -->
    <!--[if lt IE 9]>
    <script src="https://oss.maxcdn.com/libs/html5shiv/3.7.0/html5shiv.js"></script>
    <script src="https://oss.maxcdn.com/libs/respond.js/1.3.0/respond.min.js"></script>
    <![endif]-->
    <style>
        .form-signin{
            max-width: 330px;
            padding: 15px;
            margin: 60px auto;
        }
        input{
            margin-bottom: 3px;
        }
        .box {
            filter:progid:DXImageTransform.Microsoft.gradient(startColorstr='#6699FF', endColorstr='#6699FF'); /*  IE */
            background-image:linear-gradient(bottom, #6699FF 0%, #6699FF 100%);
            background-image:-o-linear-gradient(bottom, #6699FF 0%, #6699FF 100%);
            background-image:-moz-linear-gradient(bottom, #6699FF 0%, #6699FF 100%);
            background-image:-webkit-linear-gradient(bottom, #6699FF 0%, #6699FF 100%);
            background-image:-ms-linear-gradient(bottom, #6699FF 0%, #6699FF 100%);

            margin: 0 auto;
            position: relative;
            width: 100%;
            height: 100%;
        }
    </style>
</head>
<body>
<div >
    <div class="container">
        <form action="login.htm" name="form" id="form"  method="post" class="form-signin" role="form">
            <h2 class="form-signin-header" align="center">欢迎登录</h2>
            <span class="label label-danger">${requestScope.errorMsg}</span>
            <input type="text" name="user.username" class="form-control" placeholder="USER NAME" required autofocus/>
            <input type="password" name="user.pwd" class="form-control" placeholder="PASSWORD" required>
            <button class="btn btn-lg btn-primary btn-block" type="submit">登录</button>
        </form>
    </div>
</div>
</body>
</html>
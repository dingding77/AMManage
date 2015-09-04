<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<%@include file="../../common/header.jspf"%>
    <link rel="stylesheet" type="text/css" href="<%=contextPath %>/css/newtable.css" />

    <style>
        th,td{ line-height: 30px; padding-left: 5px;}
        .customerTab{ margin: 60px auto;}
        .customerTab tr td input{border: 0; border-bottom: 1px solid gray; outline: none;font-weight: bold; color: #0000ff;}
        .firstTabInfo td{ text-align: center}
        .splitRow th{ border: 0;}
        .Wdate{width: 100px;}
        .combo>input[class*="combo-text validatebox-text"]{ border: 0;outline: none;height: auto}
    </style>
    <script>
        $(function(){
            $('input[type=text][required=true]').validatebox();
            $('input[type=text]').attr('disabled','disabled').css('background-color','white');
        });

    </script>

</head>
<body LEFTMARGIN=0 TOPMARGIN=0 MARGINWIDTH=0 MARGINHEIGHT=0
      scroll=yes>
<form  id="form1" method="post">
    <TABLE class="customerTab" WIDTH="1100px" BORDER="0" ALIGN="CENTER" CELLPADDING="0" CELLSPACING="0" BGCOLOR="#fff">
        <tr>
            <th>客户名称</th>
            <td colspan="3">
               <input type="hidden" value="${modelData.id}" name="customer.id">
               <input type="text" value="${modelData.name}" name="customer.name"/>
            </td>
        </tr>
        <tr>
            <th>客户代码</th>
            <td colspan="3">
                <input type="text" value="${modelData.code}" name="customer.code">
            </td>
        </tr>
        <tr>
            <th>地址</th>
            <td colspan="3">
                <input type="text" value="${modelData.address}" name="customer.address">
            </td>
        </tr>
        <tr>
            <th>联系人</th>
            <td colspan="3">
                <input type="text" value="${modelData.contacts}" name="customer.contacts">
            </td>
        </tr>
        <tr>
            <th>电话</th>
            <td colspan="3">
                <input type="text" value="${modelData.phone}" name="customer.phone">
            </td>
        </tr>
        <tr>
            <th>传真</th>
            <td colspan="3">
                <input type="text" value="${modelData.fax}" name="customer.fax">
            </td>
        </tr>
        <tr>
            <th>电子邮件</th>
            <td colspan="3">
                <input type="text" value="${modelData.email}" name="customer.email">
            </td>
        </tr>
    </TABLE>
</form>
</body>
</html>

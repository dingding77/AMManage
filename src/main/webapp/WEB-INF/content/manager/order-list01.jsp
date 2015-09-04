<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    String contextPath = request.getContextPath().equals("/")?"":request.getContextPath();
%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Basic CRUD Application - jQuery EasyUI CRUD Demo</title>
    <link rel="stylesheet" type="text/css" href="<%=contextPath %>/js/themes/default/easyui.css">
    <link rel="stylesheet" type="text/css" href="<%=contextPath %>/js/themes/icon.css">
    <link rel="stylesheet" type="text/css" href="<%=contextPath %>/js/themes/color.css">
    <link rel="stylesheet" type="text/css" href="<%=contextPath %>/js/themes/default/easyui.css" />
    <script type="text/javascript" src="<%=contextPath %>/js/jquery-1.7.2.min.js"></script>
    <script type="text/javascript" src="<%=contextPath %>/js/jquery.easyui.min.js"></script>
    <script language="javascript" type="text/javascript" src="<%=contextPath %>/My97DatePicker/WdatePicker.js"></script>
</head>
<body>
<div>
    <label>订单名称:</label><input type="text" name="order.orderName"/>
    <label>订单日期:</label><input class="Wdate" type="text" id="beginOrderDate" onFocus="WdatePicker()"/>到<input class="Wdate" type="text" id="endOrderDate" onFocus="WdatePicker()"/>
</div>
<table id="dg" title="订单列表" class="easyui-datagrid"
       url="getOrderListJson.htm"
       toolbar="#toolbar" pagination="true"
       rownumbers="true" fitColumns="true" singleSelect="true">
    <thead>
    <tr>
        <th field="orderId" width="50">ID</th>
        <th field="orderName" width="50">订单名称</th>
        <th field="orderType" width="50">订单类型</th>
        <th field="orderAmt" width="50">订单金额</th>
    </tr>
    </thead>
</table>
<div id="toolbar">
    <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-add" plain="true" onclick="newUser()">New User</a>
    <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-edit" plain="true" onclick="editUser()">Edit User</a>
    <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-remove" plain="true" onclick="destroyUser()">Remove User</a>
</div>

<div id="dlg" class="easyui-dialog" style="width:400px;height:280px;padding:10px 20px"
     closed="true" buttons="#dlg-buttons">
    <div class="ftitle">User Information</div>
    <form id="fm" method="post" novalidate>
        <div class="fitem">
            <label>First Name:</label>
            <input name="firstname" class="easyui-textbox" required="true">
        </div>
        <div class="fitem">
            <label>Last Name:</label>
            <input name="lastname" class="easyui-textbox" required="true">
        </div>
        <div class="fitem">
            <label>Phone:</label>
            <input name="phone" class="easyui-textbox">
        </div>
        <div class="fitem">
            <label>Email:</label>
            <input name="email" class="easyui-textbox" validType="email">
        </div>
    </form>
</div>
<div id="dlg-buttons">
    <a href="javascript:void(0)" class="easyui-linkbutton c6" iconCls="icon-ok" onclick="saveUser()" style="width:90px">Save</a>
    <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-cancel" onclick="javascript:$('#dlg').dialog('close')" style="width:90px">Cancel</a>
</div>
<script type="text/javascript">
    var url;
    function newUser(){
        $('#dlg').dialog('open').dialog('setTitle','New User');
        $('#fm').form('clear');
        url = 'save_user.php';
    }
    function editUser(){
        var row = $('#dg').datagrid('getSelected');
        if (row){
            $('#dlg').dialog('open').dialog('setTitle','Edit User');
            $('#fm').form('load',row);
            url = 'update_user.php?id='+row.id;
        }
    }
    function saveUser(){
        $('#fm').form('submit',{
            url: url,
            onSubmit: function(){
                return $(this).form('validate');
            },
            success: function(result){
                var result = eval('('+result+')');
                if (result.errorMsg){
                    $.messager.show({
                        title: 'Error',
                        msg: result.errorMsg
                    });
                } else {
                    $('#dlg').dialog('close');        // close the dialog
                    $('#dg').datagrid('reload');    // reload the user data
                }
            }
        });
    }
    function destroyUser(){
        var row = $('#dg').datagrid('getSelected');
        if (row){
            $.messager.confirm('Confirm','Are you sure you want to destroy this user?',function(r){
                if (r){
                    $.post('destroy_user.php',{id:row.id},function(result){
                        if (result.success){
                            $('#dg').datagrid('reload');    // reload the user data
                        } else {
                            $.messager.show({    // show error message
                                title: 'Error',
                                msg: result.errorMsg
                            });
                        }
                    },'json');
                }
            });
        }
    }
</script>
<style type="text/css">
    #fm{
        margin:0;
        padding:10px 30px;
    }
    .ftitle{
        font-size:14px;
        font-weight:bold;
        padding:5px 0;
        margin-bottom:10px;
        border-bottom:1px solid #ccc;
    }
    .fitem{
        margin-bottom:5px;
    }
    .fitem label{
        display:inline-block;
        width:80px;
    }
    .fitem input{
        width:160px;
    }
</style>
</body>
</html>

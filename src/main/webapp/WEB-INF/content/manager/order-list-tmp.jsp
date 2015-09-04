<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<%@include file="../common/header.jspf"%>
<body>
<div class="tabsearch">
    <label>订单名称:</label><input type="text" name="manufactureOrder.order.orderName"/>
    <label>订单日期:</label><input class="Wdate" type="text" id="beginOrderDate" style="cursor: pointer" onFocus="WdatePicker()"/>到<input class="Wdate" style="cursor: pointer" type="text" id="endOrderDate" onFocus="WdatePicker()"/>
    <a href="#" class="easyui-linkbutton"  data-options="iconCls:'icon-search'">查询</a>

</div>
<table id="dg" title="订单列表" class="easyui-datagrid"
       url="getManufactureOrderListJson.htm"
       toolbar="#toolbar" pagination="true"
       rownumbers="true" fitColumns="true" singleSelect="true">
    <thead>
    <tr>
        <th field="id" checkbox="true"   width="50">ID</th>
        <th field="cstmCode" width="50">客户信息</th>
        <th field="proNo" width="50">订单类型</th>
        <th field="orderDate" width="50">订单日期</th>
    </tr>
    </thead>
</table>
<div id="toolbar">
    <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-add" plain="true" onclick="newOrder()">新增订单</a>
    <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-edit" plain="true" onclick="toolsEdit('编辑订单','/manager/order-edit.htm','id')">编辑订单</a>
    <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-remove" plain="true" onclick="toolsShow('查看订单','/manager/order-show.htm','id')">查看订单</a>
    <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-remove" plain="true" onclick="destroyOrder()">删除订单</a>
</div>

<div id="dlg" class="easyui-dialog" style="width:400px;height:280px;padding:10px 20px"
     closed="true" buttons="#dlg-buttons">
    <div class="ftitle">User Information</div>
    <form id="fm" method="post" novalidate>
        <div class="fitem">
            <label>First Name:</label>
            <input name="manufactureOrder.firstname" class="easyui-textbox" required="true">
        </div>
        <div class="fitem">
            <label>Last Name:</label>
            <input name="manufactureOrder.lastname" class="easyui-textbox" required="true">
        </div>
        <div class="fitem">
            <label>Phone:</label>
            <input name="manufactureOrder.phone" class="easyui-textbox">
        </div>
        <div class="fitem">
            <label>Email:</label>
            <input name="manufactureOrder.email" class="easyui-textbox" validType="email">
        </div>
    </form>
</div>
<div id="dlg-buttons">
    <a href="javascript:void(0)" class="easyui-linkbutton c6" iconCls="icon-ok" onclick="saveUser()" style="width:90px">Save</a>
    <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-cancel" onclick="javascript:$('#dlg').dialog('close')" style="width:90px">Cancel</a>
</div>
<script type="text/javascript">
    var url;
    function newOrder(){
        var url="/manager/order-add.htm";
        window.parent.addTab("新增订单",url);
    }
    function editOrder(){
        var url="/manager/order-edit.htm";
        window.parent.addTab("修改订单",url);
    }
    function saveOrder(){
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
    function destroyOrder(){
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

</body>
</html>

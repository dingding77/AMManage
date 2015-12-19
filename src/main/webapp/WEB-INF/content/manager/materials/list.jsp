<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<%@include file="../../common/header.jspf"%>
<body>
<div class="tabsearch">
    <label>订单名称:</label><input type="text" name="manufactureOrder.order.orderName"/>
    <label>订单日期:</label><input class="Wdate" type="text" id="beginOrderDate" style="cursor: pointer" onFocus="WdatePicker()"/>到<input class="Wdate" style="cursor: pointer" type="text" id="endOrderDate" onFocus="WdatePicker()"/>
    <a href="#" class="easyui-linkbutton"  data-options="iconCls:'icon-search'">查询</a>

</div>
<table id="dg" title="订单列表" class="easyui-datagrid"
       url="getListJson.htm"
       toolbar="#toolbar" pagination="true"
       rownumbers="true" fitColumns="true" style="width: 100%" singleSelect="false">
    <thead>
    <tr>
        <th field="id" checkbox="true"   width="50">ID</th>
        <th field="code" width="50">客户信息</th>
        <th field="name" width="50">订单类型</th>
        <th field="phone" width="50">订单日期</th>
    </tr>
    </thead>
</table>
<div id="toolbar">
    <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-add" plain="true" onclick="toolsAdd('新增客户信息','/manager/customer/add.htm')">新增客户信息</a>
    <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-edit" plain="true" onclick="toolsEdit('编辑客户信息','/manager/customer/edit.htm','id')">编辑客户信息</a>
    <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-remove" plain="true" onclick="toolsShow('查看客户信息','/manager/customer/show.htm','id')">查看客户信息</a>
    <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-remove" plain="true" onclick="toolDestroy('delete.htm','id')">删除客户信息</a>
    <a href="#" class="easyui-splitbutton" data-options="menu:'#mm2',iconCls:'icon-redo'">导出</a>
</div>
<div id="mm2" style="width:100px;">
    <div data-options="" onclick="exportWord();">Excel</div>
    <div data-options="">Cancel</div>
</div>
<script type="text/javascript">
    function exportWord(){
        window.location.href='manufactureOrder-exportWord.htm';
    }

</script>

</body>
</html>

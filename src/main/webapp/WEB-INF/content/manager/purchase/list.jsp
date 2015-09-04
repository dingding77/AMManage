<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<%@include file="../../common/header.jspf"%>
<body>

<table id="dg" title="订单列表" class="easyui-datagrid"
       url="getListJson.htm"
       toolbar="#toolbar" pagination="true"
       rownumbers="true" fitColumns="true" singleSelect="false">
    <thead>
    <tr>
        <th field="id" checkbox="true"   width="50">ID</th>
        <th field="code" width="50">客户信息</th>
        <th field="name" width="50">订单类型</th>
        <th field="phone" width="50">订单日期</th>
    </tr>
    </thead>
</table>
<div id="toolbar" align="left" style="height: auto">
    <div id="showMenu"></div>
    <div style="line-height:1px; background:#ccc;width:100%;margin:0 auto 0 auto;">&nbsp;</div>
    <label>订单名称:</label><input type="text" name="manufactureOrder.order.orderName"/>
    <label>订单日期:</label><input class="Wdate" type="text" id="beginOrderDate" style="cursor: pointer" onFocus="WdatePicker()"/>到<input class="Wdate" style="cursor: pointer" type="text" id="endOrderDate" onFocus="WdatePicker()"/>
    <a href="#" class="easyui-linkbutton"  data-options="iconCls:'icon-search'">查询</a>
    <div>
    </div>
</div>
<%--<div id="toolbar">
    <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-add" plain="true" onclick="toolsAdd('新增采购单','/manager/purchase/add.htm')">新增采购单</a>
    <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-edit" plain="true" onclick="toolsEdit('编辑采购单','/manager/purchase/edit.htm','id')">编辑采购单</a>
    <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-remove" plain="true" onclick="toolsShow('查看采购单','/manager/purchase/show.htm','id')">查看采购单</a>
    <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-remove" plain="true" onclick="toolDestroy('delete.htm','id')">删除采购单</a>
    <a href="#" class="easyui-splitbutton" data-options="menu:'#mm2',iconCls:'icon-redo'">导出</a>
</div>--%>
<script>
    showMenu('15')
</script>
</body>
</html>

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
        <th field="supplierName" width="50">供应商单位</th>
        <th field="supplierContract" width="50">供应商联系人</th>
        <th field="supplierPhone" width="50">供应商联系电话</th>
    </tr>
    </thead>
</table>
<div id="toolbar" align="left" style="height: auto">
    <div id="showMenu"></div>
    <div style="line-height:1px; background:#ccc;width:100%;margin:0 auto 0 auto;">&nbsp;</div>
    <label>订单名称:</label><input type="text" class="easyui-textbox" name="manufactureOrder.order.orderName"/>
    <label>订单日期:</label><input class="Wdate" type="text" id="beginOrderDate" style="cursor: pointer" onFocus="WdatePicker()"/>到<input class="Wdate" style="cursor: pointer" type="text" id="endOrderDate" onFocus="WdatePicker()"/>
    <a href="#" class="easyui-linkbutton"  data-options="iconCls:'icon-search'">查询</a>
    <div>
    </div>
</div>

<script>
    showMenu('15');
    function add(){
        toolsAdd('新增采购单','/manager/purchase/add.htm');
    }
    function edit(){
        toolsEdit('编辑采购单','/manager/purchase/edit.htm','id');
    }
    function show(){
        toolsShow('查看采购单','/manager/purchase/show.htm','id');
    }
    function destory(){
        toolDestroy('delete.htm','id');
    }
</script>
</body>
</html>

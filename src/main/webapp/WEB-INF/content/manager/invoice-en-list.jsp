<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<%@include file="../common/header.jspf"%>
<body>
<div class="tabsearch">
    <label>订单名称:</label><input type="text" name="order.orderName"/>
    <label>订单日期:</label><input class="Wdate" value="2014-05-02" type="text" id="beginOrderDate" style="cursor: pointer" onFocus="WdatePicker()"/>到<input class="Wdate" style="cursor: pointer" type="text" id="endOrderDate" onFocus="WdatePicker()"/>
    <a href="#" class="easyui-linkbutton"  data-options="iconCls:'icon-search'">查询</a>

</div>
<table id="dg" title="商业发票列表" class="easyui-datagrid"
       url="getInvoiceEnListJson.htm"
       toolbar="#toolbar" pagination="true"
       rownumbers="true" fitColumns="true" singleSelect="false">
    <thead>
    <tr>
        <th field="id" checkbox="true"   width="50">ID</th>
        <th field="toCompany" width="50">客户名称</th>
        <th field="invoiceNumber" width="50">发票编号</th>
        <th field="docTrade"  formatter="formatterdate" width="50">制单日期</th>
    </tr>
    </thead>
</table>
<div id="toolbar">
    <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-add" plain="true" onclick="toolsAdd('开具EN发票','/manager/invoice-en-add.htm')">新增EN发票</a>
    <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-edit" plain="true" onclick="toolsEdit('编辑EN发票','/manager/invoice-en-edit.htm','id')">编辑发票</a>
    <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-remove" plain="true" onclick="toolsEdit('查看EN发票','/manager/invoice-en-show.htm','id')">查看发票</a>
    <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-remove" plain="true" onclick="toolDestroy('enInvoice-delete.htm','id')">删除订单</a>
</div>
</body>
</html>

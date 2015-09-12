<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<%@include file="../common/header.jspf"%>
<body>
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
<div id="toolbar" align="left" style="height: auto">
    <div id="showMenu"></div>
    <div style="line-height:1px; background:#ccc;width:100%;margin:0 auto 0 auto;">&nbsp;</div>
    <label>订单名称:</label><input type="text" class="easyui-textbox" name="order.orderName"/>
    <label>订单日期:</label><input class="Wdate" value="2014-05-02" type="text" id="beginOrderDate" style="cursor: pointer" onFocus="WdatePicker()"/>到<input class="Wdate" style="cursor: pointer" type="text" id="endOrderDate" onFocus="WdatePicker()"/>
    <a href="#" class="easyui-linkbutton"  data-options="iconCls:'icon-search'">查询</a>
    <div>
    </div>
</div>
</body>
<script type="text/javascript">
    showMenu('13')
    function exportWord(){
        window.location.href='manufactureOrder-exportWord.htm';
    }
    function add(){
        toolsAdd('开具EN发票','/manager/invoice-en-add.htm');
    }
    function edit(){
        toolsEdit('编辑EN发票','/manager/invoice-en-edit.htm','id');
    }
    function show(){
        toolsEdit('查看EN发票','/manager/invoice-en-show.htm','id')
    }
    function destroy(){
        toolDestroy('delete.htm','id');
    }
</script>
</html>

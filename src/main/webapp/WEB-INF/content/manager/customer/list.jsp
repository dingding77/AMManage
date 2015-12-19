<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<%@include file="../../common/header.jspf"%>
<body>
<table id="dg" title="客户信息列表" class="easyui-datagrid"
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

<div id="toolbar" align="left" style="height: auto">
    <div id="showMenu"></div>
    <!--
    <div style="line-height:1px; background:#ccc;width:100%;margin:0 auto 0 auto;">&nbsp;</div>
    <label>订单名称:</label><input type="text" class="easyui-textbox" name="manufactureOrder.order.orderName"/>
    <label>订单日期:</label><input class="Wdate" type="text" id="beginOrderDate" style="cursor: pointer" onFocus="WdatePicker()"/>到<input class="Wdate" style="cursor: pointer" type="text" id="endOrderDate" onFocus="WdatePicker()"/>
    <a href="#" class="easyui-linkbutton" data-options="iconCls:'icon-search'">查询</a>
    <div>
    </div>
    -->
</div>

<script type="text/javascript">
    showMenu('17')
    function exportWord(){
        window.location.href='manufactureOrder-exportWord.htm';
    }
    function add(){
        toolsAdd('新增客户信息','/manager/customer/add.htm');
    }
    function edit(){
        toolsEdit('编辑客户信息','/manager/customer/edit.htm','id');
    }
    function show(){
        toolsShow('查看客户信息','/manager/customer/show.htm','id');
    }
    function destroy(){
        toolDestroy('delete.htm','id');
    }
</script>

</body>
</html>

<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<%@include file="../common/header.jspf"%>
<body>
<table id="dg" title="订单列表" class="easyui-datagrid"
       toolbar="#toolbar" pagination="true"
       rownumbers="true" fitColumns="true" singleSelect="false">

</table>
<div id="toolbar" align="left" style="height: auto">
    <div id="showMenu"></div>
    <div style="line-height:1px; background:#ccc;width:100%;margin:0 auto 0 auto;">&nbsp;</div>
    <label>订单名称:</label><input type="text" class="easyui-textbox" name="manufactureOrder.order.orderName"/>
    <label>订单日期:</label><input class="Wdate" type="text"  id="beginOrderDate" style="cursor: pointer;border:1px solid #ccc" onFocus="WdatePicker()"/>到<input class="Wdate" style="cursor: pointer;border:1px solid #ccc" type="text" id="endOrderDate" onFocus="WdatePicker()"/>
    <a href="#" class="easyui-linkbutton" data-options="iconCls:'icon-search'">查询</a>
    <div>
    </div>
</div>
<script type="text/javascript">

    $('#dg').datagrid({
        url:'getManufactureOrderListJson.htm',
        pageNumber:1,//当前页码
        pageSize:8,
        pageList:[8,16,32],
        pagination:true,
        columns:[[
            {field:'id',checkbox:'true'},
            {field:'cstmCode',title:'客户信息',width:50},
            {field:'proNo',title:'订单编号',width:50},
            {field:'orderDate',title:'订单日期',width:50,hidden:true}
        ]]
    });
    showMenu('11')
    $.parser.parse();
    function add(){
        toolsAdd('新增订单','/manager/order-add.htm');
    }
    function editBtn(){
        toolsEdit('编辑订单','/manager/order-edit.htm','id')
    }
    function show(){
        toolsShow('查看订单','/manager/order-show.htm','id')
    }

    function destroy(){
        toolDestroy('order-delete.htm','id')
    }
</script>
</body>
</html>

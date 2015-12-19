<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<%@include file="../common/header.jspf"%>
<!--生产单统计-->
<table id="dg" title="生产订单列表" class="easyui-datagrid"
       toolbar="#toolbar" pagination="true"
       rownumbers="true" fitColumns="true" style="width: 100%" singleSelect="false">

</table>
<div id="toolbar" align="left" style="height: auto">
    <div id="showMenu" style="margin-left: 20px; padding: 3px;">
        <a href="javascript:void(0)"  onclick="exportReport()"  class="easyui-linkbutton" data-options="iconCls:'icon-large-smartart'">导出Excel报表</a>
        <a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-large-chart'">图形报表</a>
    </div>

    <div style="line-height:1px; background:#ccc;width:100%;margin:0 auto 0 auto;">&nbsp;</div>
    <form id="searchForm">
        <label>订单编号:</label><input type="text" class="easyui-textbox" name="manufactureOrder.proNo"/>
        <label>下单日期:</label><input class="Wdate WdateNormal" name="manufactureOrder.orderDate" type="text" style="cursor: pointer" onfocus="WdatePicker({isShowClear:true})">
        <label>交货日期:</label><input class="Wdate WdateNormal" type="text" name="manufactureOrder.deliveryDate" style="cursor: pointer" onfocus="WdatePicker({isShowClear:true})">
        <a href="javascript:void(0)" onclick="doSearch()" class="easyui-linkbutton" data-options="iconCls:'icon-search'">查询</a>
        <div>
        </div>
    </form>
</div>
<script type="text/javascript">
$(function(){
    $('#dg').datagrid({
        url:'getProductListJson.htm',
        pageNumber:1,//当前页码
        pageSize:8,
        pageList:[8,16,32],
        pagination:true,
        queryParams:$('form[id="searchForm"]').serializeJson(),
        columns:[[
            {field:'id',checkbox:'true'},
            {field:'proNo',title:'订单编号',width:50},
            {field:'cstmCode',title:'客户编号',width:50},
            {field:'orderDate',title:'订单日期',width:50,formatter:formatterdate}
        ]]
    });
});


//导出报表数据
function exportReport(){
    window.location.href='exportProductData.htm';
}
</script>


</body>
</html>


<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<%@include file="../common/header.jspf"%>
<!--送货单统计-->
<body>
<table id="dg" title="商业发票列表" class="easyui-datagrid"
       url="getDeliveryListJson.htm"
       toolbar="#toolbar" pagination="true"
       rownumbers="true" fitColumns="true" style="width: 100%" singleSelect="false">
    <thead>
    <tr>
        <th field="id" checkbox="true"   width="50">ID</th>
        <th field="toCompany" width="50">客户名称</th>
        <th field="invoiceNumber" width="50">发票编号</th>
        <th field="relationOrderType" formatter="formatterRelationOrderType" width="50">对应订单类型</th>
        <th field="docTrade"  formatter="formatterdate" width="50">制单日期</th>
    </tr>
    </thead>
</table>
<div id="toolbar" align="left" style="height: auto">
    <div id="showMenu" style="margin-left: 20px; padding: 3px;">
        <a href="javascript:void(0)" onclick="exportReport()"  class="easyui-linkbutton" data-options="iconCls:'icon-large-smartart'">导出Excel报表</a>
        <a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-large-chart'">图形报表</a>
    </div>
    <div style="line-height:1px; background:#ccc;width:100%;margin:0 auto 0 auto;">&nbsp;</div>
    <label>订单名称:</label><input type="text" class="easyui-textbox" name="order.orderName"/>
    <label>订单日期:</label><input class="Wdate WdateNormal" value="2014-05-02" type="text" id="beginOrderDate" style="cursor: pointer" onFocus="WdatePicker()"/>到<input class="Wdate WdateNormal" style="cursor: pointer" type="text" id="endOrderDate" onFocus="WdatePicker()"/>
    <a href="#" class="easyui-linkbutton"  data-options="iconCls:'icon-search'">查询</a>
    <div>
    </div>
</div>
</body>
<script type="text/javascript">
    function formatterRelationOrderType(val){
        if(val=='1'){
            return '生产单';
        }else if(val=='2'){
            return '采购单';
        }else{
            return '<font color="red">未知</font>';
        }
    }
    //导出报表数据
    function exportReport(){
        window.location.href='exportDeliveryData.htm';
    }
</script>
</html>


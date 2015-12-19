<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<%@include file="../common/header.jspf"%>
<!--采购单统计-->
<body>
<table id="dg" title="产品列表" class="easyui-datagrid"
       toolbar="#toolbar" pagination="true"
       rownumbers="true" fitColumns="true" style="width: 100%" singleSelect="false">

</table>
<div id="toolbar" align="left" style="height: auto">
    <div id="showMenu" style="margin-left: 20px; padding: 3px;">
        <a href="javascript:void(0)" onclick="exportReport()" class="easyui-linkbutton" data-options="iconCls:'icon-large-smartart'">导出Excel报表</a>
        <a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-large-chart'">图形报表</a>
    </div>
    <div style="line-height:1px; background:#ccc;width:100%;margin:0 auto 0 auto;">&nbsp;</div>
    <form id="searchForm">
        <label>类型:</label>
        <select class="easyui-combobox" id="product_type" url='../system/dictionary/getPstpList.htm?dataDictionary.type=proType'  name="product.type" data-options="valueField:'code',textField:'value'" style="width:100px;">
        </select>
        <label>名称</label>
        <input type="text" class="easyui-textbox" name="dataDictionary.value">
        <a href="javascript:void(0)" onclick="doSearch()" class="easyui-linkbutton" data-options="iconCls:'icon-search'">查询</a>
    </form>
    <div>
    </div>
</div>
<script type="text/javascript">
    $('#dg').datagrid({
        url:'getPurchaseListJson.htm',
        pageNumber:1,//当前页码
        pageSize:8,
        pageList:[8,16,32],
        queryParams:{'purchaseOrder.supplierName':$('#supplierName').val(),'purchaseOrder.supplierPhone':$('#supplierPhone').val(),'purchaseOrder.supplierContract':$('#supplierContract').val()},
        pagination:true,
        columns:[[
            {field:'id',checkbox:'true'},//
            {field:'purchaseNo',title:'订单号',width:50},
            {field:'supplierName',title:'供应商单位',width:50},
            {field:'supplierContract',title:'供应商联系人',width:50},
            {field:'supplierPhone',title:'供应商联系电话',width:50}
        ]]
    });
    function formatterType(val){
        if(val=='0'){
            return '吊牌';
        }else if(val=='1'){
            return '贴纸';
        }else if(val=='2'){
            return '织标';
        }else if(val=='3'){
            return '印标';
        }else if(val=='4'){
            return '绣花标';
        }else if(val=='5'){
            return '礼盒';
        }else if(val=='6'){
            return '贺卡';
        }else if(val=='7'){
            return '其它';
        }
    }
    function doSearch(){
        $('#dg').datagrid('load',$('form[id="searchForm"]').serializeJson());
    }
    //导出报表数据
    function exportReport(){
        window.location.href='exportPurchaseData.htm';
    }
</script>
</body>
</html>

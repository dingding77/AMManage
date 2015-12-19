<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<%@include file="../common/header.jspf"%>
<!--送货单统计-->
<table id="dg" title="送货单列表" class="easyui-datagrid"
       toolbar="#toolbar" pagination="true"
       rownumbers="true" fitColumns="true" style="width: 100%" singleSelect="false">
</table>
<div id="toolbar" align="left" style="height: auto">
    <div id="showMenu" style="margin-left: 20px; padding: 3px;">
        <a href="javascript:void(0)" onclick="exportReport()"  class="easyui-linkbutton" data-options="iconCls:'icon-large-smartart'">导出Excel报表</a>
        <a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-large-chart'">图形报表</a>
    </div>

    <form id="searchForm">
        <div style="line-height:1px; background:#ccc;width:100%;margin:0 auto 0 auto;">&nbsp;</div>
        <label>客户名称:</label><input type="text" class="easyui-textbox" id="customerName" name="deliveryNote.customerName"/>
        <label>送货单号:</label><input type="text" class="easyui-textbox" id="deliverNo" name="deliveryNote.deliverNo"/>
        <label>制单日期:</label><input type="text" class="easyui-textbox" id="beginDeliverDate" />
        <a href="#" class="easyui-linkbutton" onclick="doSearch()" data-options="iconCls:'icon-search'">查询</a>
    </form>
</div>
</body>

<script type="text/javascript">
    $('#dg').datagrid({
        url:'getInvoiceListJson.htm',
        pageNumber:1,//当前页码
        pageSize:8,
        pageList:[8,16,32],
        pagination:true,
        queryParams:{},
        columns:[[
            {field:'id',checkbox:'true'},
            {field:'customerName',title:'客户名称',width:"20%"},
            {field:'deliverNo',title:'送货单号',width:"20%"},
            {field:'relationOrderType',title:'对应订单类型',width:"20%",formatter:formatterRelationOrderType},
            {field:'deliverDate',title:'制单日期',width:"16%",formatter:formatterdate},
            {field:'deliverNo',title:'关联订单操作',width:"20%",formatter:relationOperation}
        ]]
    });
    function formatterRelationOrderType(val){
        if(val=='1'){
            return '生产单';
        }else if(val=='2'){
            return '采购单';
        }else{
            return '<font color="red">未知</font>';
        }
    }

    function relationOperation(id,row){
        var orderNo=row.orderNo;
        if(orderNo&&orderNo!=null&&orderNo!=''){
            return '<a>查看</a><a>修改</a>'
        }else{
            return '<a href="javascript:relationOrder('+row.id+')">关联</a>'
        }
    }
    //导出报表数据
    function exportReport(){
        window.location.href='exportInvoiceData.htm';
    }
</script>
</html>


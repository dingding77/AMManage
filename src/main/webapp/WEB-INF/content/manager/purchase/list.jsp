<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<%@include file="../../common/header.jspf"%>
<body>

<table id="dg" title="采购订单列表" class="easyui-datagrid"
       toolbar="#toolbar" pagination="true"
       rownumbers="true" fitColumns="true" style="width: 100%" singleSelect="false">

</table>
<div id="toolbar" align="left" style="height: auto">
    <s:if test="relation!='relation'">
        <div id="showMenu"></div>
    </s:if>
    <form id="searchForm">
    <div style="line-height:1px; background:#ccc;width:100%;margin:0 auto 0 auto;">&nbsp;</div>

    <label>供应方单位:</label><input type="text" class="easyui-textbox" id="supplierName" name="purchaseOrder.supplierName"/>
    <%--<label>供应方日期:</label><input class="Wdate WdateNormal" type="text" id="beginOrderDate"  onFocus="WdatePicker()"/>到<input class="Wdate WdateNormal"  type="text" id="endOrderDate" onFocus="WdatePicker()"/>--%>
    <label>供应方电话:</label><input type="text" class="easyui-textbox" id="supplierPhone" name="purchaseOrder.supplierPhone"/>
        <label>供应方联系人:</label><input type="text" class="easyui-textbox" id="supplierContract" name="purchaseOrder.supplierContract"/>

    <a href="#" class="easyui-linkbutton" onclick="doSearch()"  data-options="iconCls:'icon-search'">查询</a>
    </form>
    <div>
    </div>
</div>

<script>
    showMenu('15');
    $(function(){
        $('#dg').datagrid({
            url:'/AMManage/manager/purchase/getListJson.htm',
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
    });
    function add(){
        toolsAdd('新增采购单','/manager/purchase/add.htm');
    }
    function edit(){
        toolsEdit('编辑采购单','/manager/purchase/edit.htm','id');
    }
    function show(){
        toolsShow('查看采购单','/manager/purchase/show.htm','id');
    }
    function destroy(){
        toolDestroy('delete.htm','id');
    }
    function exportFile(){
        toolExport('purchaseOrder-exportWord.htm?purchaseOrder.id=','id');
    }
</script>
</body>
</html>

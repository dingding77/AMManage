<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<%@include file="../common/header.jspf"%>
<body>
<table id="dg" title="生产订单列表" class="easyui-datagrid"
       toolbar="#toolbar" pagination="true"
       rownumbers="true" fitColumns="true" style="width: 100%" singleSelect="false">

</table>
<div id="toolbar" align="left" style="height: auto">
    <s:if test="relation!='relation'">
        <div id="showMenu"></div>
    </s:if>

    <div style="line-height:1px; background:#ccc;width:100%;margin:0 auto 0 auto;">&nbsp;</div>
    <form id="searchForm">
    <label>订单编号:</label><input type="text" class="easyui-textbox" name="manufactureOrder.proNo"/>
    <label>订单日期:</label><input class="Wdate WdateNormal" type="text"  id="beginOrderDate" name="manufactureOrder.beginOrderDate"  onFocus="WdatePicker()"/>到<input class="Wdate WdateNormal" type="text" id="endOrderDate" name="manufactureOrder.endOrderDate" onFocus="WdatePicker()"/>
    <a href="javascript:void(0)" onclick="doSearch()" class="easyui-linkbutton" data-options="iconCls:'icon-search'">查询</a>
    <div>
    </div>
    </form>
</div>
<script type="text/javascript">

    $('#dg').datagrid({
        url:'getManufactureOrderListJson.htm',
        pageNumber:1,//当前页码
        pageSize:8,
        pageList:[8,16,32],
        pagination:true,
        sortName:'createTime',
        sortOrder:'desc',
        queryParams:{'manufactureOrder.beginOrderDate':$('#beginOrderDate').val(),'manufactureOrder.endOrderDate':$('#endOrderDate').val()},
        columns:[[
            {field:'id',checkbox:'true'},
            {field:'proNo',title:'订单编号',width:50},
            {field:'cstmCode',title:'客户编号',width:50},
            {field:'orderDate',title:'订单日期',width:50,sortable:true,formatter:formatterdate},
            {field:'isOk',title:'是否完结',width:"10%",formatter:formatterStatus},
            {field:'createTime',title:'创建时间',width:50,sortable:true,formatter:formatterdate}
        ]]
    });
    showMenu('11')
    $.parser.parse();
    function add(){
        toolsAdd('新增订单','/manager/order-add.htm');
    }

    function edit(){
        var rows= $('#dg').datagrid('getSelections');
        if(rows.length>1){
            toolsEdit('编辑订单','/manager/order-edit.htm','id')
        }else{
            var isOK= rows[0]['isOk'];
            if(isOK=='Y'){
                $.messager.alert("操作提示", "您选中的数据已完结不可修改！","info");
            }else{
                toolsEdit('编辑订单','/manager/order-edit.htm','id')
            }
        }
    }

    function show(){
        toolsShow('查看订单','/manager/order-show.htm','id')
    }
    function destroy(){
        var rows= $('#dg').datagrid('getSelections');
        if(rows.length==0){
            toolDestroy('order-delete.htm','id');
        }else{
            var flag=false;
            for(var i=0;i<rows.length;i++){
                if(flag){
                    break;
                }
                var isOK= rows[i]['isOk'];
                if(isOK=='Y'){
                    $.messager.alert("操作提示", "您选中的数据包含已完结信息不可删除！","info");
                    flag=true;
                }
            }
            if(!flag){
                toolDestroy('delete.htm','id');
            }

        }
    }
    function exportFile(){
        toolExport('manufactureOrder-exportWord.htm?manufactureOrder.id=','id');
    }
    function formatterStatus(val){
        if(val=='Y'){
            return '<font style="color:blue;">已完结</font>';
        }else{
            return '<font style="color:red;">未完结</font>';
        }
    }
</script>
</body>
</html>

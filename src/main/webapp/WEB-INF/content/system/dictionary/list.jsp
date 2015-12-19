<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<%@include file="../../common/header.jspf"%>
<body>
<table id="dg" title="数据字典列表" class="easyui-datagrid"
       toolbar="#toolbar" pagination="true"
       rownumbers="true" fitColumns="true" style="width: 100%" singleSelect="false">

</table>
<div id="toolbar" align="left" style="height: auto">
    <div id="showMenu"></div>
    <div style="line-height:1px; background:#ccc;width:100%;margin:0 auto 0 auto;">&nbsp;</div>
    <form id="searchForm">
    <label>类型:</label>
    <select name="dataDictionary.type" value="" class="easyui-combobox">
    <option value="">--请选择--</option>
    <option value="pstp">后工序</option>
    <option value="ms">机台</option>
    <option value="else">其它</option>
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
        url:'getListJson.htm',
        pageNumber:1,//当前页码
        pageSize:8,
        pageList:[8,16,32],
        pagination:true,
        columns:[[
            {field:'id',checkbox:'true'},
            {field:'type',title:'类型',width:50,formatter:formatterType},
            {field:'value',title:'名称',width:50},
            {field:'description',title:'描述',width:50,hidden:true}
        ]]
    });
    function formatterType(val){
        if(val=='pstp'){
            return '后工序';
        }else if(val=='ms'){
            return '机台';
        }
    }
    showMenu('52')
    $.parser.parse();
    function add(){
        $('#dlg_add_dictionary').dialog({title: "新增数据字典信息"});
    }
    function edit(){
        toolsEdit('编辑订单','/manager/order-edit.htm','id')
    }
    function show(){
        toolsShow('查看订单','/manager/order-show.htm','id')
    }

    function destroy(){
        toolDestroy('order-delete.htm','id')
    }
    function doSearch(){
        $('#dg').datagrid('load',$('form[id="searchForm"]').serializeJson());
    }
</script>
</body>
</html>

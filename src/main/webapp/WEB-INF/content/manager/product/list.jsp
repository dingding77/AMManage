<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<%@include file="../../common/header.jspf"%>
<body>
<table id="dg" title="产品列表" class="easyui-datagrid"
       toolbar="#toolbar" pagination="true"
       rownumbers="true" fitColumns="true" style="width: 100%" singleSelect="false">

</table>
<div id="toolbar" align="left" style="height: auto">
    <div id="showMenu"></div>
    <div style="line-height:1px; background:#ccc;width:100%;margin:0 auto 0 auto;">&nbsp;</div>
    <form id="searchForm">
    <label>类型:</label>
    <select class="easyui-combobox" id="product_type" url='../../system/dictionary/getPstpList.htm?dataDictionary.type=proType'  name="product.type" data-options="valueField:'code',textField:'value'" style="width:100px;">
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
            {field:'name',title:'产品名称',width:50},
            {field:'type',title:'类型',width:50,formatter:formatterType},
            {field:'code',title:'产品编码',width:50},
            {field:'description',title:'描述',width:50,hidden:true}
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
    showMenu('53')
    $.parser.parse();
    function add(){
        toolsAdd('新增产品信息','/manager/product/add.htm');
    }
    function edit(){
        toolsEdit('编辑产品信息','/manager/product/edit.htm','id')
    }
    function show(){
        toolsShow('查看产品信息','/manager/product/show.htm','id')
    }

    function destroy(){
        toolDestroy('/manager/delete.htm','id')
    }
    function doSearch(){
        var productType=$('#product_type').combobox('getValue');
        $('#dg').datagrid('load',$('form[id="searchForm"]').serializeJson());
    }
</script>
</body>
</html>

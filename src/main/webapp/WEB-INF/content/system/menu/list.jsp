<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<%@include file="../../common/header.jspf"%>
<style type="text/css">
    #menuInfo table tr th,td{ line-height: 30px;}
    #menuInfo table tr th{padding-right: 4px; text-align: right;}
</style>
<body>
<table id="dg" title="菜单列表" class="easyui-datagrid"
       toolbar="#toolbar" pagination="true"
       rownumbers="true" fitColumns="true" singleSelect="false">

</table>
<div id="toolbar" align="left" style="height: auto">
    <div id="showMenu"></div>
    <div style="line-height:1px; background:#ccc;width:100%;margin:0 auto 0 auto;">&nbsp;</div>
    <label>订单名称:</label><input type="text" name="manufactureOrder.order.orderName"/>
    <label>订单日期:</label><input class="Wdate" type="text"  id="beginOrderDate" style="cursor: pointer;border:1px solid #ccc" onFocus="WdatePicker()"/>到<input class="Wdate" style="cursor: pointer;border:1px solid #ccc" type="text" id="endOrderDate" onFocus="WdatePicker()"/>
    <a href="#" class="easyui-linkbutton" data-options="iconCls:'icon-search'">查询</a>
    <div>
    </div>
</div>

<div id="dlg" class="easyui-dialog" title="新增菜单" style="width:450px;height:350px;padding:20px;">
    <form id="menuInfo">
        <div style="margin-bottom:10px">
            <div>菜单名称:</div>
            <input class="easyui-textbox" name="menu.name" style="width:60%;height:25px">
        </div>
        <div style="margin-bottom:10px">
            <div>父级菜单:</div>
            <select name="menu.parentid"  class="easyui-combobox">
                <s:iterator value="#session['userAllMenuList']" var="menu">
                    <s:if test="#menu.menuType!=1">
                        <option value="<s:property value="#menu.parentid"/>"><s:property value="#menu.name"/></option>
                    </s:if>
                </s:iterator>
            </select>
        </div>
        <div style="margin-bottom:10px">
            <div>级别:</div>
            <select name="menu.level" class="easyui-combobox">
                <option value="">--请选择--</option>
                <option value="0">0</option>
                <option value="1">1</option>
                <option value="2">2</option>
                <option value="2">3</option>
            </select>
        </div>
        <div style="margin-bottom:10px">
            <div>菜单类型:</div>
            <select name="menu.menuType" class="easyui-combobox" id="menuType">
                <option value="">--请选择--</option>
                <option value="0">菜单</option>
                <option value="1">按钮</option>
            </select>
        </div>

        <div style="margin-bottom:10px" id="link_path">
            <div>菜单路径:</div>
            <input class="easyui-textbox" name="menu.resources" style="width:60%;height:25px">
        </div>

        <div style="margin-bottom:10px" id="relationBtn_methodName">
            <div>操作方法:</div>
            <select name="menu.methodName" class="easyui-combobox">
                <option value="">--请选择--</option>
                <option value="add()">新增</option>
                <option value="edit()">编辑</option>
                <option value="show()">查看</option>
                <option value="destroy()">删除</option>
            </select>
        </div>

        <div style="margin-bottom:10px" id="relationBtn_img">
            <div>按钮图标:</div>
            <select name="menu.iconImg" class="easyui-combobox">
                <option value="">--请选择--</option>
                <option value="icon-add">icon-add</option>
                <option value="icon-edit">icon-edit</option>
                <option value="icon-remove">icon-remove</option>
            </select>
        </div>
    </form>

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
            {field:'name',title:'名称',width:50},
            {field:'level',title:'级别',width:50},
            {field:'resources',title:'菜单地址',width:50},
            {field:'menuType',title:'菜单类型',width:50,formatter:formatterMenuType}
        ]]
    });
    $('#dlg').dialog({
        iconCls: 'icon-save',
        onClose:function(){
            cancelAdd();
        },
        closed:true,
        buttons: [{
            text:'Ok',
            iconCls:'icon-ok',
            handler:function(){
                addSave();
            }
        },{
            text:'Cancel',
            handler:function(){
                cancelAdd();
            }
        }]
    });
    function formatterMenuType(value,row,index){
        if(value=='0'){
            return '菜单';
        }else if(value=='1'){
            return '按钮';
        }
    }
    showMenu('7')

    function add(){
        showMask('');
        $('#dlg').dialog('open')
    }
    function cancelAdd(){
        destroyMask();
        $('#dlg').dialog('close');
    }
    function edit(){
        toolsEdit('编辑菜单','/system/menu/edit.htm','id')
    }
    function show(){
        toolsShow('查看菜单','/system/menu/show.htm','id')
    }
    function destroy(){
        toolDestroy('order-delete.htm','id')
    }
    function menuTypeChange(){
        var menuType=$('#menuType').combobox('getValue')
        if(menuType=='0'){
            $('div[id*="relationBtn"]').hide();
            $('#link_path').show();
        }else if(menuType=='1'){
            $('div[id*="relationBtn"]').show();
            $('#link_path').hide();
        }
    }
    function addSave(){
        $.ajax({
            url:'addSave.htm',// 跳转到 action
            data:$('form[id="menuInfo"]').serialize(),
            type:'post',
            cache:false,
            dataType:'json',
            success:function(data) {
                if(data.errorMsg){
                    $.messager.alert('Warning',data.errorMsg);
                }else{
                    $.messager.alert("操作提示", "保存成功","info");
                    cancelAdd();
                }
            },
            error : function() {
                $.messager.alert("操作提示", "程序异常","warning");
            }
        });
    }

    $("#menuType").combobox({
        onChange: function (n,o) {
            menuTypeChange();
       }});

</script>
</body>
</html>

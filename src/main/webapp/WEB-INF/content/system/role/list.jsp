<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<%@include file="../../common/header.jspf"%>
<body>
<table id="dg" title="角色列表" class="easyui-datagrid"
       toolbar="#toolbar" pagination="true"
       rownumbers="true" fitColumns="true" singleSelect="false">

</table>
<div id="toolbar" align="left" style="height: auto">
    <div id="showMenu"></div>
    <div style="line-height:1px; background:#ccc;width:100%;margin:0 auto 0 auto;">&nbsp;</div>
    <form id="searchForm">
        <label>角色名:</label><input type="text" class="easyui-textbox" name="roleQuery.roleName" id="username"/>
        <label>可用状态:</label>
        <select name="roleQuery.isEnable" class="easyui-combobox">
            <option value="0">不可用</option>
            <option value="1">可用</option>
        </select>
        <a href="javascript:void(0)" onclick="doSearch()" class="easyui-linkbutton" data-options="iconCls:'icon-search'">查询</a>
    </form>
    <div>
    </div>
</div>
<!--新增角色-->
<div id="dlg_add_role" class="easyui-dialog" title="新增角色" style="width:450px;height:350px;padding:20px;">
    <form name="add_role">
        <div style="margin-bottom:10px">
            <div>角色名称:</div>
            <input type="hidden" id="role_id" name="role.id"/>
            <input class="easyui-textbox" id="role_rolename" data-options="required:true" name="role.roleName" style="width:60%;height:25px">
        </div>
        <div style="margin-bottom:10px">
            <div>是否启用:</div>
            <select name="role.isEnable" id="role_isEnable"  class="easyui-combobox">
                <option value="1">启用</option>
                <option value="0">不启用</option>
            </select>
        </div>
        <div style="margin-bottom:10px">
            <div>描述:</div>
            <input name="role.description" id="role_desc" class="easyui-textbox" style="width:100%;height:28px">
        </div>
    </form>
</div>
<!--角色授权-->
<%--http://kaixinmao.iteye.com/blog/1447689--%>
<div id="w" class="easyui-window" title="角色菜单授权" data-options="iconCls:'icon-save',closed:true" style="width:500px;height:350px;padding:5px;">
    <div class="easyui-layout" data-options="fit:true">
        <div data-options="region:'center'" style="border: 0">
            <div class="easyui-panel" style="padding:5px; border: 0;">
                <ul id="menu_tree" class="easyui-tree"></ul>
            </div>

        </div>
        <div data-options="region:'south',border:false" style="text-align:right;padding:5px 0 0;">
            <a class="easyui-linkbutton" data-options="iconCls:'icon-ok'" href="javascript:void(0)" onclick="javascript:saveAuthorize()" style="width:80px">Ok</a>
            <a class="easyui-linkbutton" data-options="iconCls:'icon-cancel'" href="javascript:void(0)" onclick="javascript:$('#w').window('close')" style="width:80px">Cancel</a>
        </div>
    </div>
</div>
</body>

<script type="text/javascript">
    showMenu('5');
    $('#dg').datagrid({
        url:'getListJson.htm',
        pageNumber:1,//当前页码
        pageSize:8,
        pageList:[8,16,32],
        pagination:true,
        columns:[[
            {field:'id',checkbox:'true'},
            {field:'roleName',title:'角色名称',width:50},
            {field:'description',title:'描述',width:50},
            {field:'isEnable',title:'状态',width:50,formatter:formatterEnable}
        ]]
    });

    $('#dlg_add_role').dialog({
        closed:true,
        buttons: [{
            text:'Save',
            iconCls:'icon-ok',
            handler:function(){
                addSave();
            }
        },{
            text:'Cancel',
            handler:function(){
                $('#dlg_add_role').dialog('close');
            }
        }]
    });


    //新增
    function add(){
        //重置表单
        $('form[name="add_role"]')[0].reset();
        $('#dlg_add_role').dialog({title: "新增角色"});
        $('#dlg_add_role').dialog('open');
    }
    //编辑
    function edit(){
        //重置表单
        $('form[name="add_role"]')[0].reset();
        var rows= $('#dg').datagrid('getSelections');
        if(rows.length==0 || rows.length>1){
            $.messager.alert("操作提示", "请选择一项！","info");
            return;
        }
        $('#dlg_add_role').dialog({title: "编辑角色"});
        $('#role_id').val(rows[0]['id']);
        var isEnable=rows[0]['isEnable'];
        var text='可用';
        if(isEnable=='0'){
            text='不可用';
        }
        $('#role_isEnable').textbox('setValue',rows[0]['isEnable']).textbox('setText',text);
        $('#role_desc').textbox('setValue',rows[0]['description']);
        $('#role_rolename').textbox('setValue',rows[0]['roleName']);
        $('#dlg_add_role').dialog('open');

    }

    //删除
    function destory(){
        toolDestroy('delete.htm','id');
    }
    function addSave(){
        var _url='addSave.htm';
        if($('#role_id').val()!=''){
            _url='editSave.htm';
        }
        if(!$('form[name="add_role"]').form('validate')){
            return;
        }
        $.ajax({
            url:_url,// 跳转到 action
            data:$('form[name="add_role"]').serialize(),
            type:'post',
            cache:false,
            dataType:'json',
            success:function(data) {
                if(data.errorMsg){
                    $.messager.alert('操作提示',data.errorMsg,'error');
                }else{
                    $.messager.alert("操作提示", "保存成功","info");
                    $('#dlg_add_role').dialog('close');
                    $('#dg').datagrid('load');//重新加载数据
                }
            },
            error : function() {
                $.messager.alert("操作提示", "程序异常","warning");
            }
        });
    }
    function formatterEnable(value,row){
        if(value=='0'){
            return '不可用';
        }else if(value=='1'){
            return '可用';
        }
    }

    function doSearch(){
        $('#dg').datagrid('load',$('form[id="searchForm"]').serializeJson());
    }

    //角色授权
    function roleAuthorize(){
        var rows= $('#dg').datagrid('getSelections');
        if(rows.length==0 || rows.length>1){
            $.messager.alert("操作提示", "请选择一项！","info");
            return;
        }
        var roleId=rows[0]['id'];
        $('#w').window('open');
        $('#menu_tree').tree({
            method:'post',
            animate:true,
            checkbox: true,
            url: '../menu/menuTree.htm?query.roleid='+roleId
        });
    }

    function saveAuthorize(){
        var nodes = $('#menu_tree').tree('getChecked');
        var s = '';
        for(var i=0; i<nodes.length; i++){
            if (s != '') s += ',';
            s += nodes[i].id;
        }

        var nodes1 = $('#menu_tree').tree('getChecked','indeterminate');
        for(var i=0; i<nodes1.length; i++){
            if (s != '') s += ',';
            s += nodes1[i].id;
        }
        if(s==''){
            $.messager.alert('操作提示','请选择列表中的菜单项','info');
            return;
        }
        var rows= $('#dg').datagrid('getSelections');
        var roleId=rows[0]['id'];
        var url='saveAuthorize.htm';
        var data={'roleId':roleId,'menus':s};
        $.ajax({
            type: 'POST',
            url: url,
            data: data,
            dataType: 'json',
            success: function(data){
                if(data.errMsg){
                    $.messager.alert('操作提示','授权失败','error');
                }else{
                    $.messager.alert('操作提示','授权成功','info');
                    $('#w').window('close');
                }
            }
        });
    }

</script>
</html>

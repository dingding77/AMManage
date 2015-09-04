<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<%@include file="../../common/header.jspf"%>
<html>
<head>
    <title>系统用户列表</title>
</head>
<body>
<table id="dg" title="菜单列表" class="easyui-datagrid"
       toolbar="#toolbar" pagination="true"
       rownumbers="true" fitColumns="true" singleSelect="false">

</table>
<div id="toolbar" align="left" style="height: auto">
    <div id="showMenu"></div>
    <div style="line-height:1px; background:#ccc;width:100%;margin:0 auto 0 auto;">&nbsp;</div>
    <form id="searchForm">
        <label>用户名:</label><input type="text" class="easyui-textbox" name="userQuery.username" id="username"/>
        <label>电话:</label> <input type="text"  class="easyui-textbox" name="userQuery.phone"/>
        <label>可用状态:</label>
        <select name="userQuery.enable" class="easyui-combobox">
            <option value="0">不可用</option>
            <option value="1">可用</option>
        </select>
        <a href="javascript:void(0)" onclick="doSearch()" class="easyui-linkbutton" data-options="iconCls:'icon-search'">查询</a>
    </form>
    <div>
    </div>
</div>
<!--角色授权-->
<div id="w" class="easyui-window" title="角色授权" data-options="iconCls:'icon-save'" style="width:500px;height:350px;padding:5px;">
    <div class="easyui-layout" data-options="fit:true">
        <div data-options="region:'east',split:true,title:'已有角色'" style="width:120px; text-align: center;">
            <s:iterator value="#session['userRoleList']" var="role">

                <a href="javascript:void(0)" name="user_role" class="easyui-linkbutton curUserRoleId_<s:property value="#role.id"/>"><s:property value="#role.roleName"/></a>
            </s:iterator>
        </div>
        <div data-options="region:'center'" style="border: 0">
            <div id="right_panel" style="margin: auto">

            </div>
        </div>
        <div data-options="region:'south',border:false" style="text-align:right;padding:5px 0 0;">
            <a class="easyui-linkbutton" data-options="iconCls:'icon-ok'" href="javascript:void(0)" onclick="javascript:alert('ok')" style="width:80px">Ok</a>
            <a class="easyui-linkbutton" data-options="iconCls:'icon-cancel'" href="javascript:void(0)" onclick="javascript:$('#w').window('close')" style="width:80px">Cancel</a>
        </div>
    </div>
</div>
<!--新增角色-->
<div id="dlg_add_user" class="easyui-dialog" title="新增用户" style="width:450px;height:350px;padding:20px;">
    <form name="add_role">

    </form>
</div>

</body>

<script type="text/javascript">
    showMenu('3');
    $('#dg').datagrid({
        url:'getListJson.htm',
        pageNumber:1,//当前页码
        pageSize:8,
        pageList:[8,16,32],
        pagination:true,
        columns:[[
            {field:'id',checkbox:'true'},
            {field:'username',title:'用户名',width:50},
            {field:'phone',title:'手机号',width:50},
            {field:'email',title:'EMAIL',width:50},
            {field:'enable',title:'可用状态',width:50,formatter:formatterEnable}
        ]]
    });
    $('#right_panel').panel({
        width:'100%',
        height:'100%',
        title:'角色列表',
        dataType:'json',
        href:'<%=contextPath %>/system/role/getAllListJson.htm',
        loadingMessage:'正在玩命加载...',
        extractor:function(data){
            var allRoles=$.parseJSON(data)['allRoles'];
          if(allRoles){
              var _table='<table>';
              $.each(allRoles,function(i,item){
                  var _roleName=item.roleName;
                  var _roleId=item.id;
                  if($('a[name="user_role"][class*="curUserRoleId_'+_roleId+'"]')){
                      _table=_table+'<tr><td style="text-align: center">'+'<input class="datagrid-header-check" type="checkbox" checked="checked" value="'+_roleId+'" style="margin-top: 2px;"/></td>';
                  }else{
                      _table=_table+'<tr><td style="text-align: center">'+'<input class="datagrid-header-check" type="checkbox" value="'+_roleId+'" style="margin-top: 2px;"/></td>';
                  }

                  _table=_table+'<td>'+'<a href="javascript:void(0)" class="easyui-linkbutton">'+_roleName+'</a>'+'</td></tr>';
                  if(i==allRoles.length-1){
                      _table=_table+'</table>';
                  }
                  $('#right_panel').append(_table);
              });
          }
        },
        onLoad:function(){
            $('#right_panel div[class="panel-loading"]').hide();
        },
        tools:[{
            iconCls:'icon-reload',
            handler:function(){
                $('#right_panel').panel('refresh');
            }
        }]
    });

    $('#dlg_add_user').dialog({
        onClose:function(){

        },
        closed:true,
        buttons: [{
            text:'Ok',
            iconCls:'icon-ok',
            handler:function(){

            }
        },{
            text:'Cancel',
            handler:function(){

            }
        }]
    });
    function add(){
        $('#dlg_add_user').dialog('open');
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
    function authorize(){
        var rows= $('#dg').datagrid('getSelections');
        if(rows.length==0){
            $.messager.alert("操作提示", "请选择一项！","info");
            return;
        }
        $('#w').window('open');

    }

</script>
</html>

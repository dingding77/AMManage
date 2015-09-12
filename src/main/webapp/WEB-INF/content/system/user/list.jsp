<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<%@include file="../../common/header.jspf"%>
<html>
<head>
    <title>系统用户列表</title>
</head>
<body>
<table id="dg" title="用户列表" class="easyui-datagrid"
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
<div id="w" class="easyui-window" title="角色授权" data-options="iconCls:'icon-save',closed:true" style="width:500px;height:350px;padding:5px;">
    <div class="easyui-layout" data-options="fit:true">
        <div data-options="region:'east',split:true,title:'已有角色'" id="userRoles" style="width:120px; text-align: center;">
        </div>
        <div data-options="region:'center'" style="border: 0">
            <div id="right_panel" style="margin: auto">

            </div>
        </div>
        <div data-options="region:'south',border:false" style="text-align:right;padding:5px 0 0;">
            <a class="easyui-linkbutton" data-options="iconCls:'icon-ok'" href="javascript:void(0)" onclick="javascript:saveAuthorize()" style="width:80px">Ok</a>
            <a class="easyui-linkbutton" data-options="iconCls:'icon-cancel'" href="javascript:void(0)" onclick="javascript:$('#w').window('close')" style="width:80px">Cancel</a>
        </div>
    </div>
</div>
<!--新增角色-->
<div id="dlg_add_user" class="easyui-dialog" title="新增用户" style="width:450px;height:350px;padding:20px;">
    <form name="add_user_info">
        <div style="margin-bottom:10px">
            <div>用户名:</div>
            <input type="hidden"  id="user_id" name="user.id"/>
            <input class="easyui-textbox" id="user_username" name="user.username" style="width:60%;height:25px">
        </div>
        <div style="margin-bottom:10px">
            <div>是否可用:</div>
            <select name="user.enable" id="user_enable" class="easyui-combobox" id="menuType">
                <option value="">--请选择--</option>
                <option value="0">不可用</option>
                <option value="1">可用</option>
            </select>
        </div>
        <div style="margin-bottom:10px" id="div_password">
            <div>密码:</div>
            <input class="easyui-textbox" type="password" name="user.pwd" id="user_pwd" style="width:60%;height:25px">
        </div>
        <div style="margin-bottom:10px">
            <div>手机号:</div>
            <input class="easyui-textbox" name="user.phone" id="user_phone" style="width:60%;height:25px">
        </div>
        <div style="margin-bottom:10px">
            <div>Email:</div>
            <input class="easyui-textbox" name="user.email" id="user_email" data-options="validType:'email'" style="width:60%;height:25px">
        </div>
    </form>
</div>

<!--密码重置-->
<div id="dlg_reset_pwd" class="easyui-dialog" title="密码重置" style="width:400px;height:200px;padding:20px;">
    <div style="margin-bottom:10px">
        <div>新密码:</div>
        <input type="hidden" name="user.id" id="reset_user_id"/>
        <input class="easyui-textbox" type="password" name="user.pwd" id="newPwd" style="width:60%;height:25px">
    </div>
</div>

</body>

<script type="text/javascript">
var _userId='';
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
        closed:true,
        width:'100%',
        height:'100%',
        title:'角色列表',
        dataType:'json',
        loadingMessage:'正在玩命加载...',
        extractor:function(data){
            var allRoles=$.parseJSON(data)['allRoles'];
            var userRoles=$.parseJSON(data)['userRoles'];
            if(userRoles){
                $('#userRoles').html('');
                if(userRoles.length>0){
                    $.each(userRoles,function(_index,item){
                        var _a='<a href="javascript:void(0)" name="user_role" class="easyui-linkbutton curUserRoleId_'+item.id+'">'+item.roleName+'</a>';
                        $('#userRoles').append('<div>'+_a+'</div>');
                        alert($('#userRoles').html());
                    });
                    $.parser.parse('#userRoles');
                }
            }
          if(allRoles){
              var _table='<table>';
              $.each(allRoles,function(i,item){
                  var _roleName=item.roleName;
                  var _roleId=item.id;
                  if($('a[name="user_role"][class*="curUserRoleId_'+_roleId+'"]').length>0){
                      _table=_table+'<tr><td style="text-align: center">'+'<input  type="checkbox" checked="checked" value="'+_roleId+'" style="margin-top: 2px;"/></td>';
                  }else{
                      _table=_table+'<tr><td style="text-align: center">'+'<input  type="checkbox" value="'+_roleId+'" style="margin-top: 2px;"/></td>';
                  }

                  _table=_table+'<td>'+'<a href="javascript:void(0)" class="easyui-linkbutton">'+_roleName+'</a>'+'</td></tr>';
                  if(i==allRoles.length-1){
                      _table=_table+'</table>';
                  }
                  $('#right_panel').html('').append(_table);
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
    //添加用户dialog
    $('#dlg_add_user').dialog({
        closed:true,
        buttons: [{
            text:'Ok',
            iconCls:'icon-ok',
            handler:function(){
                addUserSave();
            }
        },{
            text:'Cancel',
            handler:function(){
                $('#dlg_add_user').dialog('close');
            }
        }]
    });
    //密码重置dialog
    $('#dlg_reset_pwd').dialog({
        closed:true,
        buttons: [{
            text:'确认重置',
            iconCls:'icon-ok',
            handler:function(){
                resetUserPwd();
            }
        },{
            text:'取消',
            handler:function(){
                $('#dlg_reset_pwd').dialog('close');
            }
        }]
    });

    function add(){
        $('#user_id').val('');
        $('#dlg_add_user').dialog({title: "新增用户信息"});
        $('#div_password').show();
        $('form[name="add_user_info"] input[name^="user."],select[name^="user."]').each(function(){
            var name=$(this).attr('name');
            if(name=='user.id'){
                return;
            }
            if(name=='user.enable'){
                $('#user_enable').textbox('setValue',0).textbox('setText','可用');;
            }else{
                $('#'+name.replace('user.','user_')).textbox('setValue','');
            }
        });
        $('#dlg_add_user').dialog('open');
    }
    function edit(){
        var rows= $('#dg').datagrid('getSelections');
        if(rows.length==0 || rows.length>1){
            $.messager.alert("操作提示", "请选择一项！","info");
            return;
        }
        $('#div_password').hide();
        var url='getUserById.htm';
        $.ajax({
            url:url,// 跳转到 action
            data:{'user.id':rows[0]['id']},
            type:'post',
            cache:false,
            dataType:'json',
            success:function(data) {
                if(data){
                    $('#user_id').val(data['id']);
                    $('#dlg_add_user').dialog({title: "编辑用户信息"});
                    $('#dlg_add_user').dialog('open');

                    $('form[name="add_user_info"] input[name^="user."],select[name^="user."]').each(function(){
                        var name=$(this).attr('name');
                        var _value=(data[name.replace('user.','')]);
                        if(name=='user.id'){
                            return;
                        }
                        if(name=='user.enable'){
                            var text='可用';
                            if(_value=='0'){
                                text='不可用';
                            }
                            $('#user_enable').textbox('setValue',_value).textbox('setText',text);;
                        }else{
                            $('#'+name.replace('user.','user_')).textbox('setValue',_value);
                        }

                    });

                }
            },
            error : function() {
                $.messager.alert("操作提示", "程序异常","error");
            }
        });
    }
    function saveAuthorize(){
        var roles='';
        var checkeds=$('#right_panel input[type="checkbox"]:checked');
        checkeds.each(function(_index,item){
            if(_index==checkeds.length-1){
                roles=roles+$(this).val()
            }else{
                roles=roles+$(this).val()+','
            }

        });
        alert(roles);
        alert('_userId:'+_userId);
        $.ajax({
            url:'saveAuthorize.htm',// 跳转到 action
            data:{'userRole.roleIds':roles,'userRole.userid':_userId},
            type:'post',
            cache:false,
            dataType:'json',
            success:function(data) {
                if(data.errorMsg){
                    $.messager.alert('Warning',data.errorMsg);
                }else{
                    $.messager.alert("操作提示", "角色授权成功","info");
                    $('#w').dialog('close');
                }
            },
            error : function() {
                $.messager.alert("操作提示", "程序异常","error");
            }
        });
    }
    function resetPwd(){
        var rows= $('#dg').datagrid('getSelections');
        if(rows.length==0){
            $.messager.alert("操作提示", "请选择一项！","info");
            return;
        }
        $('#reset_user_id').val(rows[0]['id']);
        $('#dlg_reset_pwd').dialog('open');
    }
    function resetUserPwd(){
        $.ajax({
            url:'editSave.htm',// 跳转到 action
            data:{'user.id':$('#reset_user_id').val(),'user.pwd':$('#newPwd').val()},
            type:'post',
            cache:false,
            dataType:'json',
            success:function(data) {
                if(data.errorMsg){
                    $.messager.alert('Warning',data.errorMsg);
                }else{
                    $.messager.alert("操作提示", "密码重置成功","info");
                    $('#dlg_reset_pwd').dialog('close');
                    $('#reset_user_id').val('');
                    $('#newPwd').val('');
                }
            },
            error : function() {
                $.messager.alert("操作提示", "程序异常","error");
            }
        });
    }
    function addUserSave(){
        var url='addSave.htm';
        var userId=$('#user_id').val();
        if(userId!=''){
            url='editSave.htm';
        }
        $.ajax({
            url:url,// 跳转到 action
            data:$('form[name="add_user_info"]').serialize(),
            type:'post',
            cache:false,
            dataType:'json',
            success:function(data) {
                if(data.errorMsg){
                    $.messager.alert('Warning',data.errorMsg);
                }else{
                    $.messager.alert("操作提示", "保存成功","info");
                    $('#w').window('close');
                    $('#dg').datagrid('load');
                    $('#dlg_add_user').dialog('close');
                }
            },
            error : function() {
                $.messager.alert("操作提示", "程序异常","error");
            }
        });
    }
    function editUserSave(){
        $.ajax({
            url:'addSave.htm',// 跳转到 action
            data:$('form[name="add_user_info"]').serialize(),
            type:'post',
            cache:false,
            dataType:'json',
            success:function(data) {
                if(data.errorMsg){
                    $.messager.alert('Warning',data.errorMsg);
                }else{
                    $.messager.alert("操作提示", "保存成功","info");
                    $('#w').window('close');
                    $('#dg').datagrid('load');
                    $('#dlg_add_user').dialog('close');
                }
            },
            error : function() {
                $.messager.alert("操作提示", "程序异常","error");
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
    function authorize(){
        var rows= $('#dg').datagrid('getSelections');
        if(rows.length==0){
            $.messager.alert("操作提示", "请选择一项！","info");
            return;
        }
        _userId=rows[0]['id'];
        var timestamp = (new Date()).valueOf();
        var _href='<%=contextPath %>/system/role/getAllListJson.htm?user.id='+_userId+'&timestamp='+timestamp;
        $('#right_panel').panel({'href':_href});
        $('#right_panel').panel('open');
        $('#w').window('open');

    }

</script>
</html>

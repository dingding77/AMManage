function toolsShow(title,url,key){
    var rows= $('#dg').datagrid('getSelections');
    if(rows.length==0){
        $.messager.alert("操作提示", "请选择一项！","info");
    }else if(rows.length>1){
        $.messager.alert("操作提示", "只能选择一项数据！","info");
    }else{
        var storeId=rows[0][key];
        if(url.indexOf('?')>-1){
            url=url+"&&"+key+"="+storeId;
        }else{
            url=url+"?"+key+"="+storeId;
        }
        window.parent.addTab(title, url);
    }
}
//编辑工具事件
function toolsEdit(title,url,key){
    var rows= $('#dg').datagrid('getSelections');
    if(rows.length==0){
        $.messager.alert("操作提示", "请选择一项！","info")
    }else if(rows.length>1){
        $.messager.alert("操作提示", "只能选择一项数据！","info");
    }else{
        var storeId=rows[0][key];
        if(url.indexOf('?')>-1){
            url=url+"&&"+key+"="+storeId;
        }else{
            url=url+"?"+key+"="+storeId;
        }
        window.parent.addTab(title, url);
    }
}
//添加选项卡
function toolsAdd(title,url){
    window.parent.addTab(title, url);
}

function toolDestroy(url,idKey) {
    var rows= $('#dg').datagrid('getSelections');
    if(rows.length==0){
        $.messager.alert("操作提示", "请选择一项！","info");
        return;
    }
    var ids =new Array();
    for (var i = 0; i < rows.length; i++) {
        ids.push(rows[i][idKey]);
    }
    if (rows){
        $.messager.confirm('Confirm','您确定要删除选择项么?',function(r) {
            if (r) {
                $.post(url+'?ids='+ids, function (result) {
                    if (result.success) {
                        $('#dg').datagrid('reload');    // reload the user data
                    } else {
                        $.messager.show({    // show error message
                            title: 'Error',
                            msg: result.errorMsg
                        });
                    }
                }, 'json')
            }
        });
    }
}

function toolExport(url,key){
    var rows= $('#dg').datagrid('getSelections');
    if(rows.length==0){
        $.messager.alert("操作提示", "请选择一项！","info");
    }else if(rows.length>1){
        $.messager.alert("操作提示", "只能选择一项数据！","info");
    }else{
        var storeId=rows[0][key];
        window.location.href=url+storeId;
    }
}
function showMask(showMsg){
    $("<div class=\"datagrid-mask\"></div>").css({display:"block",width:'100%',height:'100%'}).appendTo("body");
    if(showMsg){
        if(showMsg!=''){
            $("<div class=\"datagrid-mask-msg\"></div>").html(showMsg).appendTo("body")
                .css({display:"block",left:(document.body.clientWidth - 190) / 2,top:(document.body.clientHeight - 45) / 2});
        }
    }
}
function destroyMask(){
    $("body").find(".datagrid-mask-msg").remove();
    $("body").find(".datagrid-mask").remove();
}
(function($){
    $.fn.serializeJson=function(){
        var serializeObj={};
        var array=this.serializeArray();
        var str=this.serialize();
        $(array).each(function(){
            if(serializeObj[this.name]){
                if($.isArray(serializeObj[this.name])){
                    serializeObj[this.name].push(this.value);
                }else{
                    serializeObj[this.name]=[serializeObj[this.name],this.value];
                }
            }else{
                serializeObj[this.name]=this.value;
            }
        });
        return serializeObj;
    };
})(jQuery);
//-----------------------------------------
function showMenu(id){
    $.ajaxSetup ({
        cache: false //关闭AJAX相应的缓存
    });
    $.ajax({
        url: "/AMManage/system/menu/menu-show.htm",
        data:{"menuParentId":id},
        success: function(listMenu){
            if(listMenu){

                $.each(listMenu,function(index,menu){
                    var iconImg=menu.iconImg;
                    var methodName=menu.methodName;
                    var name=menu.name;
                    var link='<a href="#" class="easyui-linkbutton" iconCls="'+iconImg+'" plain="true" onclick="'+methodName+'">'+name+' </a>';
                    if(methodName=='exportFile(),printFile()'){
                        link='<a href="javascript:void(0)" id="sb" iconCls="icon-save" onclick="javascript:void(0)">保存</a>';
                        link=link+'<div id="mm" style="width:100px;">';
                        link=link+'<div iconCls="icon-undo" onclick="exportFile()">导出</div>';
                        link=link+'<div iconCls="icon-print" onclick="printFile()">打印</div>';
                        link=link+'</div>';
                    }
                    $('#showMenu').append(link);
                });
                $.parser.parse('#showMenu');
                $('#sb').splitbutton({
                    menu:'#mm'
                });

            }
        }
    });

}

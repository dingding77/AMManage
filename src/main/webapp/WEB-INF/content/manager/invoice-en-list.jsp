<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<%@include file="../common/header.jspf"%>
<body>
<table id="dg" title="商业发票列表" class="easyui-datagrid"
       url="getInvoiceEnListJson.htm"
       toolbar="#toolbar" pagination="true"
       rownumbers="true" fitColumns="true" style="width: 100%" singleSelect="false">
    <thead>
    <tr>
        <th field="id" checkbox="true"   width="50">ID</th>
        <th field="toCompany" width="50">客户名称</th>
        <th field="invoiceNumber" width="50">发票编号</th>
        <th field="relationOrderType" formatter="formatterRelationOrderType" width="50">对应订单类型</th>
        <th field="orderNo" width="50">关联订单号</th>
        <th field="docTrade"  formatter="formatterdate" width="50">制单日期</th>
        <th field="isOk"  formatter="formatterStatus" width="50">是否完结</th>
        <th field="createTime" sortable="true"  formatter="formatterdate" width="50">创建时间</th>
    </tr>
    </thead>
</table>
<div id="toolbar" align="left" style="height: auto">
    <div id="showMenu"></div>
    <div style="line-height:1px; background:#ccc;width:100%;margin:0 auto 0 auto;">&nbsp;</div>
    <label>订单名称:</label><input type="text" class="easyui-textbox" name="order.orderName"/>
    <label>订单日期:</label><input class="Wdate WdateNormal" value="2014-05-02" type="text" id="beginOrderDate" style="cursor: pointer" onFocus="WdatePicker()"/>到<input class="Wdate WdateNormal" style="cursor: pointer" type="text" id="endOrderDate" onFocus="WdatePicker()"/>
    <a href="#" class="easyui-linkbutton"  data-options="iconCls:'icon-search'">查询</a>
    <div>
    </div>
</div>
</body>
<script type="text/javascript">
    showMenu('13')
    function exportWord(){
        window.location.href='enInvoiceAction-exportWord.htm';
    }
    function add(){
        toolsAdd('开具EN发票','/manager/invoice-en-add.htm');
    }
    function edit(){
        var rows= $('#dg').datagrid('getSelections');
        if(rows.length>1){
            toolsEdit('编辑EN发票','/manager/invoice-en-edit.htm','id');
        }else{
            var isOK= rows[0]['isOk'];
            if(isOK=='Y'){
                $.messager.alert("操作提示", "您选中的数据已完结不可修改！","info");
            }else{
                toolsEdit('编辑EN发票','/manager/invoice-en-edit.htm','id');
            }
        }
    }
    function show(){
        toolsEdit('查看EN发票','/manager/invoice-en-show.htm','id')
    }
    function destroy(){
        var rows= $('#dg').datagrid('getSelections');
        if(rows.length==0){
            toolDestroy('enInvoice-delete.htm','id');
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
                toolDestroy('enInvoice-delete.htm','id');
            }

        }

    }
    function exportFile(){
        var rows= $('#dg').datagrid('getSelections');
        if(rows.length==0){
            $.messager.alert("操作提示", "请选择一项！","info");
        }else if(rows.length>1){
            $.messager.alert("操作提示", "只能选择一项数据！","info");
        }else{
            var orderNo=rows[0]['orderNo'];
            if(orderNo&&orderNo!=null&&orderNo!=''){
                toolExport('enInvoiceAction-exportWord.htm?id=','id');
            }else{
                $.messager.alert("操作提示", "您选中的发票信息未关联订单，暂不可导出！","info");
            }
        }

    }
    function doFinish(){
        var rows= $('#dg').datagrid('getSelections');
        if(rows.length==0){
            $.messager.alert("操作提示", "请选择一项！","info");
        }else if(rows.length>1){
            $.messager.alert("操作提示", "只能选择一项数据！","info");
        }else{
            var isOk=rows[0]['isOk'];
            if(isOk=='Y'){
                $.messager.alert("操作提示", "您选中的数据已完结！","info");
                return;
            }
            var idVal=rows[0]['id'];
            var orderNo=rows[0]['orderNo'];
            if(orderNo&&orderNo!=null&&orderNo!=''){
                $.messager.confirm("操作提示", "您确定要执行改操作吗？", function (data) {
                    if (data) {
                        var body=$('#dg').parent();
                        $.mask({loadMsg: '正在提交数据....', target: body});
                        var url='doFinish-en.htm';
                        $.post(url+'?id='+idVal, function (result) {
                            if (result.success) {
                                $.messager.alert("操作提示", "操作成功！","info",function(){
                                    $.unmask({target: body});
                                    $('#dg').datagrid('reload');    // reload the user data
                                });
                            } else {
                                $.messager.show({    // show error message
                                    title: 'Error',
                                    msg: '操作失败'
                                });
                                $.unmask({target: body});
                            }
                        }, 'json')
                    }
                });
            }else{
                $.messager.alert("操作提示", "您选中的发票信息未关联订单！","info");
            }
        }
    }
    function formatterRelationOrderType(val){
        if(val=='1'){
            return '生产单';
        }else if(val=='2'){
            return '采购单';
        }else{
            return '<font color="red">未知</font>';
        }
    }

    function formatterStatus(val){
         if(val=='Y'){
             return '<font style="color:blue;">已完结</font>';
         }else{
             return '<font style="color:red;">未完结</font>';
         }
    }
</script>
</html>

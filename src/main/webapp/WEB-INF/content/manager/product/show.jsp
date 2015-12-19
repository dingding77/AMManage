<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<%@include file="../../common/header.jspf"%>
<link rel="stylesheet" type="text/css" href="<%=contextPath %>/css/newtable.css" />

<style>
    th,td{ line-height: 30px; padding-left: 5px;}
    .customerTab{ margin: 60px auto;}
    .customerTab tr td input{border: 0; border-bottom: 1px solid gray; outline: none;font-weight: bold; color: #0000ff;}
    .firstTabInfo td{ text-align: center}
    .splitRow th{ border: 0;}
    .Wdate{width: 100px;}
    .combo>input[class*="combo-text validatebox-text"]{ border: 0;outline: none;height: auto}
</style>
<script>
    $(function(){
        $('input[type=text][required=true]').validatebox();
    });
    function saveCustomer(){
        var url='editSave.htm';
        $('#form1').form('submit',{
            url: url,
            onSubmit: function(){
                return $(this).form('validate');
            },
            success: function(result){
                var result = eval('('+result+')');
                if (result.errorMsg){
                    $.messager.show({
                        title: 'Error',
                        msg: result.errorMsg
                    });
                }else{
                    $.messager.alert('操作提示','修改成功','info',function(){
                        location.reload();
                    });
                }
            }
        });
    }
    function formReset(){
        $("div[class='tabs-wrap']>ul>li[class='tabs-selected']").trigger('click');
    }


    function initSelect(obj,url,key,value,param){
        $.ajax({
            type: "POST",   //访问WebService使用Post方式请求
            url: url, //调用WebService的地址和方法名称组合 ---- WsURL/方法名
            data: param,  //这里是要传递的参数，格式为 data: "{paraName:paraValue}",下面将会看到
            dataType: 'json',   //WebService 会返回Json类型

            success: function(result) {     //回调函数，result，返回值
                if(result.list){
                    for(var i=0;i<result.list.length;i++){
                        var dd=list[i];
                        var _value=dd[value];
                        var _key=dd[key];
                        obj.append("<option value='"+_key+"'>"+_value+"</option>");
                    }
                }
            }
        });
    }
    //根据客户编码获取客户信息
    function getCustomerInfo(customerCode){
        $.ajax({
            type: "POST",   //访问WebService使用Post方式请求
            url: "getCustomerInfoByCode.htm", //调用WebService的地址和方法名称组合 ---- WsURL/方法名
            data: {'customer.code':customerCode},  //这里是要传递的参数，格式为 data: "{paraName:paraValue}",下面将会看到
            dataType: 'json',   //WebService 会返回Json类型

            success: function(result) {     //回调函数，result，返回值
                $('#customer_address').val(result.address);
                $('#customer_phone').val(result.phone);
            }
        });
    }
    $(function(){
        $('#sel_customer,#sel_code').combobox('reload', 'showCustomer.htm');

        $('#sel_customer').combobox({
            onChange:function(value){
                $('#sel_code').combobox('setValue',value);
                $('#customerCode').val(value);
                $('#customerName').val($('#sel_customer').combobox('getText'));
                getCustomerInfo(value);
            }
        });

        $('#sel_code').combobox({
            onChange:function(value){
                $('#sel_customer').combobox('setValue',value);
                $('#customerName').val(value);
                $('#customerCode').val($('#sel_code').combobox('getText'));
            }
        });
    });
    $(function(){
        $('#product_type').combobox('select', ${modelData.type});
        $('#product_pstp').combobox({
            url:'../../system/dictionary/getPstpList.htm?dataDictionary.type=pstp',
            valueField:'code',
            textField:'value',
            multiple:true,
            onLoadSuccess:function(data){
                if(data){
                    var _value='${modelData.pstp}';
                    if(_value){
                        $('#product_pstp').combobox('setText',_value);
                        //此处分割符不是','而是 ', '
                        $('#product_pstp').combobox('setValues',_value.split(', '));
                    }
                }
            }
        });
        $('input').attr('readonly','readonly');
    });
</script>

</head>
<body LEFTMARGIN=0 TOPMARGIN=0 MARGINWIDTH=0 MARGINHEIGHT=0
      scroll=yes>
<form  id="form1" method="post">
    <TABLE class="customerTab" WIDTH="1100px" BORDER="0" ALIGN="CENTER" CELLPADDING="0" CELLSPACING="0" BGCOLOR="#fff">
        <tr>
            <th>产品名称</th>
            <td colspan="3">
                <input type="text" class="easyui-textbox" required="true" value="${modelData.name}" name="product.name"/>
                <input type="hidden" name="product.id" value="${modelData.id}">
            </td>
        </tr>
        <tr>
            <th>产品编号</th>
            <td colspan="3">
                <input type="text" class="easyui-textbox" required="true" value="${modelData.code}" name="product.code">
            </td>
        </tr>
        <tr>
            <th>产品分类</th>
            <td colspan="3">
                <select class="easyui-combobox" value="${modelData.type}" required="true" id="product_type" url='../../system/dictionary/getPstpList.htm?dataDictionary.type=proType'  name="product.type" data-options="valueField:'code',textField:'value'" style="width:100px;">

                </select>

            </td>
        </tr>
        <tr>
            <th>色号</th>
            <td colspan="3">
                <input type="text" class="easyui-textbox" value="${modelData.pantoneNo}" name="product.pantoneNo">
            </td>
        </tr>
        <tr>
            <th>款号</th>
            <td colspan="3">
                <input type="text" class="easyui-textbox" value="${modelData.styleNo}" name="product.styleNo">
            </td>
        </tr>
        <tr>
            <th>尺码</th>
            <td colspan="3">
                <input type="text" class="easyui-textbox" required="true" value="${modelData.size}" name="product.size">
            </td>
        </tr>
        <tr>
            <th>后工序</th>
            <td colspan="3">
                <select class="easyui-combobox" value="${modelData.pstp}" id="product_pstp" url='../dictionary/getPstpList.htm?dataDictionary.type=pstp'  name="product.pstp" data-options="valueField:'code',textField:'value',multiple:true" style="width:200px;height:50px">

                </select>
            </td>
        </tr>
        <tr>
            <th>单价</th>
            <td colspan="3">
                <input type="text" class="easyui-numberbox" value="${modelData.price}"  precision="4"   name="product.price">
            </td>
        </tr>
    </TABLE>
</form>
</body>
</html>

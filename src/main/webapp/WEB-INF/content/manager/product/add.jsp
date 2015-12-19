<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<%@include file="../../common/header.jspf"%>
    <link rel="stylesheet" type="text/css" href="<%=contextPath %>/css/newtable.css" />

    <style>
        th,td{ line-height: 30px; padding-left: 5px;}
        .productTab{ margin: 60px auto;}
        .productTab tr td input{border: 0; border-bottom: 1px solid gray; outline: none;font-weight: bold; color: #0000ff;}
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
            var url='addSave.htm';
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
                        $.messager.show({
                            title: '提示',
                            msg:'添加成功'
                        });
                    }
                    formReset();
                }
            });
        }
        function formReset(){
            $("input[name='res']").click();
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

        $(function(){
            $('#sel_product,#sel_code').combobox('reload', 'showCustomer.htm');

            $('#sel_product').combobox({
                onChange:function(value){
                    $('#sel_code').combobox('setValue',value);
                    $('#productCode').val(value);
                    $('#productName').val($('#sel_product').combobox('getText'));

                }
            });

            $('#sel_code').combobox({
                onChange:function(value){
                    $('#sel_product').combobox('setValue',value);
                    $('#productName').val(value);
                    $('#productCode').val($('#sel_code').combobox('getText'));
                }
            });
        });
    </script>

</head>
<body LEFTMARGIN=0 TOPMARGIN=0 MARGINWIDTH=0 MARGINHEIGHT=0
      scroll=yes>
<form  id="form1" method="post">
    <TABLE class="productTab" WIDTH="1100px" BORDER="0" ALIGN="CENTER" CELLPADDING="0" CELLSPACING="0" BGCOLOR="#fff">
        <tr>
            <th>产品名称</th>
            <td colspan="3">
               <input type="text" class="easyui-textbox" required="true" name="product.name"/>
            </td>
        </tr>
        <tr>
            <th>产品编号</th>
            <td colspan="3">
                <input type="text" class="easyui-textbox" required="true" name="product.code">
            </td>
        </tr>
        <tr>
            <th>产品分类</th>
            <td colspan="3">
                <select class="easyui-combobox" required="true" id="product_type" url='../../system/dictionary/getPstpList.htm?dataDictionary.type=proType'  name="product.type" data-options="valueField:'code',textField:'value'" style="width:100px;">

                </select>
            </td>
        </tr>
        <tr>
            <th>色号</th>
            <td colspan="3">
                <input type="text" class="easyui-textbox" name="product.pantoneNo">
            </td>
        </tr>
        </tr>
        <tr>
            <th>款号</th>
            <td colspan="3">
                <input type="text" class="easyui-textbox" name="product.styleNo">
            </td>
        </tr>
        <tr>
            <th>尺码</th>
            <td colspan="3">
                <input type="text" class="easyui-textbox" required="true" name="product.size">
            </td>
        </tr>
        <tr>
            <th>后工序</th>
            <td colspan="3">
                <select class="easyui-combobox" id="product_pstp" url='../../system/dictionary/getPstpList.htm?dataDictionary.type=pstp'  name="product.pstp" data-options="valueField:'code',textField:'value',multiple:true" style="width:200px;height:50px">

                </select>
            </td>
        </tr>
        <tr>
            <th>单价</th>
            <td colspan="3">
                <input type="text" class="easyui-numberbox"  precision="4"   name="product.price">
            </td>
        </tr>
    </TABLE>
    <div id="button" style="margin-top: 20px;">
        <a href="javascript:void(0)" onclick="saveCustomer()" class="easyui-linkbutton">提交</a> &nbsp;&nbsp;&nbsp;&nbsp;
        <a href="javascript:void(0)" class="easyui-linkbutton" onclick="formReset()">重置</a>
        <input id="res" name="res" type="reset" style="display:none;" />
    </div>
</form>
</body>
</html>

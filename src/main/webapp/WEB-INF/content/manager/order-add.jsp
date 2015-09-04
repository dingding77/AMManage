<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<%@include file="../common/header.jspf"%>
    <link rel="stylesheet" type="text/css" href="<%=contextPath %>/css/newtable.css" />

    <style>
        th,td{ line-height: 30px; padding-left: 5px;}
        .orderTab tr td input{border: 0; border-bottom: 1px solid gray; outline: none;font-weight: bold; color: #0000ff;}
        .firstTabInfo td{ text-align: center}
        .splitRow th{ border: 0;}
        .Wdate{width: 100px;}
        .combo>input[class*="combo-text validatebox-text"]{ border: 0;outline: none;height: auto}
    </style>
    <script>
        $(function(){
            $('input[type=text][required=true]').validatebox();
        });
        function saveOrder(){
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
        //根据客户编码获取客户信息
        function getCustomerInfo(customerCode){
            $.ajax({
                type: "POST",   //访问WebService使用Post方式请求
                url: "customer/getCustomerInfoByCode.htm", //调用WebService的地址和方法名称组合 ---- WsURL/方法名
                data: {'customer.code':customerCode},  //这里是要传递的参数，格式为 data: "{paraName:paraValue}",下面将会看到
                dataType: 'json',   //WebService 会返回Json类型

                success: function(result) {     //回调函数，result，返回值
                    $('#customer_address').val(result.address);
                    $('#customer_phone').val(result.phone);
                }
            });
        }
        $(function(){
            $('#sel_customer,#sel_code').combobox('reload', 'customer/showCustomer.htm');

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
    </script>

</head>
<body LEFTMARGIN=0 TOPMARGIN=0 MARGINWIDTH=0 MARGINHEIGHT=0
      scroll=yes>
<form  id="form1" method="post">
    <TABLE class="orderTab" WIDTH="1100px" BORDER="0" ALIGN="CENTER" CELLPADDING="0" CELLSPACING="0" BGCOLOR="#fff">
        <tr>
            <td colspan="8" style="font-size:20px; font-weight:bold; text-align:center; border-bottom: 0;">上海傲美实业有限公司</td>
        </tr>
        <tr>
            <td colspan="8" style="font-size:20px; font-weight:bold; text-align:center;border-top: 0;border-bottom: 0;">生产单</td>
        </tr>
        <tr>
            <td colspan="8" style="font-size:12px; text-align:center;border-top: 0;">上海市嘉定区南翔镇昌翔路575号2号门2楼  TEL:021-66306672   FAX:021-56065197</td>
        </tr>
        <tr class="firstTabInfo">
            <td>客户</td>
            <td colspan="3">
                <%--<input type="text" required="true" name="manufactureOrder.cstmCode">--%>
                <input type="hidden" name="name" id="customerName">
                <input class="easyui-combobox" id="sel_customer" name="sel_customer"
                       data-options="valueField:'code',textField:'name'">
            </td>
            <td>客户代码</td>
            <td colspan="3">
                <input type="hidden" name="code" id="customerCode">
                <input class="easyui-combobox" id="sel_code" name="sel_customer"
                       data-options="valueField:'name',textField:'code'">
            </td>
        </tr>
        <tr class="firstTabInfo">
            <td>下单日期</td>
            <td colspan="3"><input class="Wdate" name="manufactureOrder.orderDate" type="text" style="cursor: pointer" onFocus="WdatePicker({isShowClear:false})"/></td>
            <td>交货日期</td>
            <td colspan="3"><input class="Wdate" type="text" name="manufactureOrder.deliveryDate"></td>
        </tr>
        <tr class="firstTabInfo">
            <td>后道要求</td>
            <td colspan="3" style="text-align: left;">
                <select class="easyui-combobox" id="pstp" name="state" data-options="multiple:true,multiline:true" style="width:200px;height:50px">

                </select>
            </td>
            <td>机台</td>
            <td colspan="3">
                <input type="checkbox" name="manufactureOrder.board">
                <input type="checkbox" name="manufactureOrder.board">
                <input type="checkbox" name="manufactureOrder.board">
            </td>
        </tr>
        <tr style="border: 0" class="splitRow">
            <th height="30" style="border-left: 1px solid #99bbe8;"></th>
            <th></th>
            <th></th>
            <th></th>
            <th></th>
            <th></th>
            <th></th>
            <th style="border-right:1px solid #99bbe8;"></th>
        </tr>
        <tr>
            <td>数量(个)</td>
            <td colspan="4">产品描述</td>
            <td colspan="3">款号</td>
        </tr>
        <tr>
            <td><input type="text" name="manufactureOrder.proNum"></td>
            <td colspan="4">
                <textarea cols="50" name="manufactureOrder.proDesc" rows="5">
                </textarea>
            </td>
            <td colspan="3">
                <input type="text" name="manufactureOrder.styleNo">
            </td>
        </tr>
        <tr>
            <td colspan="8">材料记录:</td>
        </tr>
        <tr>
            <td colspan="2">领料日期:
                <input class="Wdate" type="text" name="manufactureOrder.callslipDate" style="cursor: pointer" onFocus="WdatePicker({isShowClear:false})"/>
            </td>
            <td colspan="2">材料编号:<input type="text" name="manufactureOrder.materialNo"></td>
            <td colspan="2">实需数量:<input type="text" name="manufactureOrder.needNum"></td>
            <td colspan="2">实领数量:<input type="text" name="manufactureOrder.realNum"></td>
        </tr>
        <tr>
            <td colspan="2">返料日期:
                <input class="Wdate" type="text" name="manufactureOrder.revertDate" style="cursor: pointer" onFocus="WdatePicker({isShowClear:false})"/>
            </td>
            <td colspan="6">数&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;量:
                <input type="text" name="manufactureOrder.revertNum">
            </td>
        </tr>
        <tr style="border: 0" class="splitRow">
            <th height="30" style="border-left: 1px solid #99bbe8;"></th>
            <th></th>
            <th></th>
            <th></th>
            <th></th>
            <th></th>
            <th></th>
            <th style="border-right:1px solid #99bbe8;"></th>
        </tr>
        <tr>
        <tr>
            <td colspan="2">翻单:<input type="text" name="manufactureOrder.repeatOrder"></td>
            <td colspan="6">新版:<input type="text" name="manufactureOrder.newEdition"></td>
        </tr>
        <tr>
            <td colspan="4">生产操作员:<input type="text" name="manufactureOrder.proOperator"></td>
            <td colspan="4">生产贴样处:<input type="text" name="manufactureOrder.proPasteLike"></td>
        </tr>
        <tr>
            <td colspan="8">耗&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;时:
                <input type="text" name="manufactureOrder.proTimeConsuming">
            </td>
        </tr>
        <tr>
            <td colspan="8">生&nbsp;产&nbsp;日&nbsp;期:
                <input class="Wdate" type="text" name="manufactureOrder.proDate" style="cursor: pointer" onFocus="WdatePicker({isShowClear:false})"/>
            </td>
        </tr>
        <tr style="border: 0" class="splitRow">
            <th height="30" style="border-left: 1px solid #99bbe8;"></th>
            <th></th>
            <th></th>
            <th></th>
            <th></th>
            <th></th>
            <th></th>
            <th style="border-right:1px solid #99bbe8;"></th>
        </tr>
        <tr>
            <td colspan="8">剪折操作员:<input type="text" name="manufactureOrder.fssOperator"></td>
        </tr>
        <tr>
            <td colspan="8">耗&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;时:
                <input type="text" name="manufactureOrder.fssTimeConsuming">
            </td>
        </tr>
        <tr>
            <td colspan="8">剪&nbsp;折&nbsp;日&nbsp;期:
                <input class="Wdate" type="text" name="manufactureOrder.fssDate" style="cursor: pointer" onFocus="WdatePicker({isShowClear:false})"/>
            </td>
        </tr>
        <tr style="border: 0" class="splitRow">
            <th height="30" style="border-left: 1px solid #99bbe8;"></th>
            <th></th>
            <th></th>
            <th></th>
            <th></th>
            <th></th>
            <th></th>
            <th style="border-right:1px solid #99bbe8;"></th>
        </tr>
        <tr>
            <td colspan="4">品检操作员:<input type="text" name="manufactureOrder.qcOperator"></td>
            <td colspan="4">货样贴样处:<input type="text" name="manufactureOrder.goodsPasteLike"></td>
        </tr>
        <tr>
            <td colspan="8">耗&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;时:
                <input type="text" name="manufactureOrder.qcTimeConsuming">
            </td>
        </tr>
        <tr>
            <td colspan="8">包装明细:<input type="text" name="manufactureOrder.packDetail"></td>
        </tr>
        <tr>
            <td colspan="8">品检日期:
                <input class="Wdate" type="text" name="manufactureOrder.qcDate" style="cursor: pointer" onFocus="WdatePicker({isShowClear:false})"/>
            </td>
        </tr>
        <tr>
            <td>备注</td>
            <td colspan="7">
                <textarea cols="50" rows="5" name="manufactureOrder.remark">
                </textarea>
            </td>
        </tr>
        <tr>
            <td colspan="3" style="text-decoration: underline; border-right: 0;">产品计划:
                <input type="text" name="manufactureOrder.proPlanning"/>
            </td>
            <td colspan="2" style="border-left: 0; border-right: 0;"></td>
            <td colspan="3" style="text-decoration: underline; border-left: 0;">产品跟单:
                <input type="text" name="manufactureOrder.proDocumentary"/>
            </td>
        </tr>
    </TABLE>
    <div id="button" style="margin-top: 20px;">
        <a href="javascript:void(0)" onclick="saveOrder()" class="easyui-linkbutton">提交</a> &nbsp;&nbsp;&nbsp;&nbsp;
        <a href="javascript:void(0)" class="easyui-linkbutton" onclick="formReset()">重置</a>
        <input id="res" name="res" type="reset" style="display:none;" />
    </div>
    </div>
</form>
</body>
</html>

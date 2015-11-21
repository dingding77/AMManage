<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<%@include file="../common/header.jspf"%>
<link rel="stylesheet" type="text/css" href="<%=contextPath %>/css/newtable.css" />
<link href="../js/jquery.imgareaselect-0.9.10/css/imgareaselect-default.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="../js/jquery.imgareaselect-0.9.10/scripts/jquery.imgareaselect.min.js"></script>
<script type="text/javascript" src="../js/jquery.imgareaselect-0.9.10/scripts/jquery.imgareaselect.pack.js"></script>
<script type="text/javascript" src="../js/upload.js"></script>
<script type="text/javascript">
 $(function(){
        $('#cutimg-window').window({
         title: '图片处理',
         width: 600,
         height: 300,
         top: ($(document.body).height() - 300) * 0.5,
         left: ($(window).width() - 600) * 0.5,
         shadow: true,
         modal: true,
         closed: true,
         minimizable: false,
         maximizable: false,
         collapsible: false,
         onClose:function(){
             $('#cutimg-window').window('resize',{
                 width: 600,
                 height: 300,
                 top: ($(document.body).height() - 300) * 0.5,
                 left: ($(window).width() - 600) * 0.5,
             });
         }
     });
 });
 function iFrameHeight() {
     var ifm= document.getElementById("cutimg-iframe");
     var subWeb = document.frames ? document.frames["cutimg-iframe"].document : ifm.contentDocument;
     if(ifm != null && subWeb != null) {
         ifm.height = subWeb.body.scrollHeight;
         ifm.width = subWeb.body.scrollWidth;
     }
 }
</script>
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
            var url='manufactureAddSave.htm';
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
                        $.messager.alert('操作提示','添加成功','info',function(){
                            location.reload();
                        });

                    }

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
                    <input class="easyui-combobox" id="sel_customer" required="true" name="sel_customer"
                           data-options="valueField:'code',textField:'name'">
            </td>
            <td>客户代码</td>
            <td colspan="3">
                <input type="hidden" name="manufactureOrder.cstmCode" id="customerCode">
                <input class="easyui-combobox" id="sel_code" required="true" name="sel_customer"
                       data-options="valueField:'name',textField:'code'">
            </td>
        </tr>
        <tr class="firstTabInfo">
            <td>下单日期</td>
            <td colspan="3"><input class="Wdate" name="manufactureOrder.orderDate" type="text" style="cursor: pointer" onFocus="WdatePicker({isShowClear:false})"/></td>
            <td>交货日期</td>
            <td colspan="3"><input class="Wdate" type="text" name="manufactureOrder.deliveryDate" type="text" style="cursor: pointer" onFocus="WdatePicker({isShowClear:false})"/></td>
        </tr>
        <tr class="firstTabInfo">
            <td>后道要求</td>
            <td colspan="3" style="text-align: left;">
                <select class="easyui-combobox" id="pstp" url='../system/dictionary/getPstpList.htm?dataDictionary.type=pstp'  name="manufactureOrder.houdaoRequests" data-options="valueField:'value',textField:'value',multiple:true" style="width:200px;height:50px">

                </select>
            </td>
            <td>机台</td>
            <td colspan="3">
                <select class="easyui-combobox" id="board"  url='../system/dictionary/getPstpList.htm?dataDictionary.type=ms'  name="manufactureOrder.board" data-options="valueField:'value',textField:'value',multiple:true" style="width:200px;height:50px">

                </select>
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
        <tr style="border:0;">
            <td colspan="8" style="border: 0; border-left: 1px solid #99bbe8;">
                <table class="subTable" style="width: 100%;border: 0;margin: 0;padding: 0" border="0">
                <tr>
                    <td style="border-left: 0;">数量</td>
                    <td>品名</td>
                    <td>尺码</td>
                    <td>款号</td>
                    <td>色号</td>
                    <td>货物编号</td>
                    <td>客户编号</td>
                </tr>
                <tr>
                    <td style="border-left: 0;"><input type="text"/></td>
                    <td><input type="text"/></td>
                    <td><input type="text"/></td>
                    <td><input type="text"/></td>
                    <td><input type="text"/></td>
                    <td><input type="text"/></td>
                    <td><input type="text"/></td>
                </tr>
            </table>
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
            <td colspan="4">生产贴样处:
                <div>
                    <img id="proPasteLike_show" style="width: 40px; height: 40px;">
                </div>
                <input type="file" onchange="uploadImage('proPasteLike','proPasteLike_file')" id="proPasteLike_file" name="file2" >

                <input type="hidden" id="proPasteLike" name="manufactureOrder.proPasteLike">
            </td>
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
            <td colspan="4">货样贴样处:
                <div>
                    <img id="goodsPasteLike_show"  style="width: 40px; height: 40px;">
                </div>
                <input type="file" onchange="uploadImage('goodsPasteLike','goodsPasteLike_file')" id="goodsPasteLike_file" name="file2" >
                <input type="hidden" id="goodsPasteLike" name="manufactureOrder.goodsPasteLike"></td>
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
        <a href="javascript:void(0)" onclick="saveOrder()" class="easyui-linkbutton" style="width: 100px;">提交</a> &nbsp;&nbsp;&nbsp;&nbsp;
        <a href="javascript:void(0)" class="easyui-linkbutton" onclick="formReset()" style="width: 100px;">重置</a>
        <input id="res" name="res" type="reset" style="display:none;" />
    </div>
    </div>
</form>
<div id="cutimg-window" class="easyui-window" data-options="iconCls:'icon-save',closed:true" title="图片处理">
    <iframe id="cutimg-iframe" frameborder="0" scrolling="no" marginheight="0" marginwidth="0" onLoad="iFrameHeight()"></iframe>
</div>

</body>
</html>

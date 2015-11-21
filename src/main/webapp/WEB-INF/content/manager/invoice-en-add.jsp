<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<%@include file="../common/header.jspf"%>
    <link rel="stylesheet" type="text/css" href="<%=contextPath %>/css/newtable.css" />
    <style type="text/css">
        .text-left{ text-align: left}
        .text-right{ text-align: right}
        .text-center{ text-align: center}
        .none_right_border{ border-right: 0; border-left: 0;}
        table tr{ line-height: 30px;}
        .title{
            text-align:center;font-size: 20px; font-weight: bold; color: #000000;
        }
        .invoiceTab tr td input{border: 0; border-bottom: 1px solid gray; outline: none;font-weight: bold; color: #0000ff;}
        .maininfo td{ text-align: center; height: 30px;}
        .maininfo td input{ text-align: center; }
    </style>
</head>
<script>
    var orderFlag='purchaseNo';
    $(function(){
        $('input[type=text][required=true]').validatebox();

        $('#dd').dialog({
            title: '关联操作',
            width: '80%',
            height: '50%',
            cache: false,
            modal: true,
            closed:true,
            buttons: [{
                text:'Ok',
                iconCls:'icon-ok',
                handler:function(){
                    chooseOneOrder();
                }
            },{
                text:'Cancel',
                handler:function(){
                    $('#dd').dialog('close');
                }
            }]
        });

        $('input[name="enCommercialInvoice.relationOrderType"]').change(function() {
            $('#orderNo').val('');
            $('#span_orderNo').text('');
        });
    });
    function saveEnInvoice(){
        var url='enInvoiceAddSave.htm';
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

    function chooseOrderInfo(){
        var orderType=$('input[name="enCommercialInvoice.relationOrderType"]:checked').val();
        if(orderType=='1'){//生产单
            orderFlag='proNo';
            $('#dd').dialog({href: 'order-list.htm?relation=relation'})
            $('#dd').dialog('open');
        }else if(orderType=='2'){//采购单
            orderFlag='purchaseNo';

            $('#dd').dialog({href: 'purchase/list.htm?relation=relation'})
            $('#dd').dialog('open');
        }else{
            $.messager.alert('操作提示','为匹配到订单类型','info');
        }
    }

    function chooseOneOrder(){
        var rows= $('#dg').datagrid('getSelections');
        if(rows.length==0){
            $.messager.alert("操作提示", "请选择一项！","info")
        }else if(rows.length>1){
            $.messager.alert("操作提示", "只能选择一项数据！","info");
        }else{

            $('#orderNo').val(rows[0][orderFlag]);
            $('#span_orderNo').text('单号:'+rows[0][orderFlag]);
            $('#dd').dialog('close');
        }
    }
</script>
<body LEFTMARGIN=0 TOPMARGIN=0 MARGINWIDTH=0 MARGINHEIGHT=0
      scroll=yes>
<form  id="form1" method="post" >
    <TABLE class="invoiceTab" WIDTH="80%" BORDER="0" ALIGN="CENTER" CELLPADDING="0" CELLSPACING="0" BGCOLOR="#fff">
        <tr>
            <td colspan="5" class="title" >上海傲美实业有限公司</td>
        </tr>
        <tr>
            <td colspan="5" class="text-center">
                2nd Floor, 2-575 Changxiang Road, Nanxiang Town, Jiading District, Shanghai 201802, China
            </td>
        </tr>
        <tr>
            <td colspan="5"  class="title">
                商业发票
                <input type="hidden" name="enCommercialInvoice.createUserid" value="${user.id}"/>
            </td>
        </tr>
        <tr>
            <td colspan="5"  class="title">
                COMMERCIAL INVOICE
            </td>
        </tr>
        <tr>
            <td colspan="3">
                对应订单类型&nbsp;&nbsp;<input name="enCommercialInvoice.relationOrderType" value="1" type="radio" checked="true" />生产单<input name="enCommercialInvoice.relationOrderType" value="2" type="radio"/>采购单
            </td>
            <td colspan="2">
                <a class="easyui-linkbutton" onclick="chooseOrderInfo()" style="margin-left: 5px;">立即关联订单</a>
                <input type="hidden" id="orderNo" name="enCommercialInvoice.orderNo"/>
                <label id="span_orderNo"></label>
            </td>
        </tr>
        <tr>
            <td colspan="3">TO:<input type="text" required="true" style="width: 300px;" name="enCommercialInvoice.toCompany"/></td>
            <td class="text-right">Invoice Number:</td>
            <td><input type="text" required="true" name="enCommercialInvoice.invoiceNumber"/></td>
        </tr>
        <tr>
            <td colspan="3"></td>
            <td class="text-right">Re:</td>
            <td><input type="text" name="enCommercialInvoice.reInfo"/></td>
        </tr>
        <tr>
            <td colspan="3">FROM: Shanghai Ao Mei Industrial Co., Ltd.<input name="enCommercialInvoice.fromCompany" value="Shanghai Ao Mei Industrial Co., Ltd." type="hidden"></td>
            <td class="text-right">制单日期:</td>
            <td>
                <input class="Wdate" type="text" name="enCommercialInvoice.docTrade"  style="cursor: pointer" onFocus="WdatePicker({isShowClear:false})"/>
            </td>
        </tr>
        <tr>
            <td colspan="3"></td>
            <td class="text-right">出货日期:</td>
            <td>
                <input class="Wdate" type="text" name="enCommercialInvoice.shipmentDate"  style="cursor: pointer" onFocus="WdatePicker({isShowClear:false})"/>
            </td>
        </tr>
        <tr>
            <td colspan="2">装船口岸 From: SHANGHAI CHINA
                <input name="enCommercialInvoice.seaportFrom" value="SHANGHAI CHINA" type="hidden">
            </td>
            <td colspan="3">目的地 To: <input type="text" required="true" name="enCommercialInvoice.destinationTo" style="width: 300px;"/></td>
        </tr>
        <tr>
            <td colspan="2">PAYMENT : <input type="text" required="true" name="enCommercialInvoice.payment" style="width: 30px;">%
                <select name="enCommercialInvoice.paymentType">
                    <option value="T/T">T/T</option>
                    <option value="D/P">D/P</option>
                    <option value="D/A">D/A</option>
                    <option value="M/T">M/T</option>
                </select>
            </td>
            <td colspan="3">开证银行 Issued by:<input type="text" required="true" name="enCommercialInvoice.issuedBy" style="width: 300px;"></td>
        </tr>
        <tr>
            <td class="text-center">订单编号</td>
            <td class="text-center" colspan="2">货物描述</td>
            <td class="text-center">单价</td>
            <td class="text-center">总价</td>
        </tr>
        <tr>
            <td class="text-center">PO</td>
            <td class="text-center">Name</td>
            <td class="text-center">PSC</td>
            <td class="text-center">Unit Price EXW OR CIF SHANGHAI (1000PCS)</td>
            <td class="text-center">Total Amount USD</td>
        </tr>
        <s:iterator begin="0" end="4" var="index">
            <tr class="maininfo">
                <td><input type="text" name="enCommercialInvoice.enciOrders[${index}].orderNo"/></td>
                <td><input type="text" name="enCommercialInvoice.enciOrders[${index}].goodsDesc"/></td>
                <td><input type="text" name="enCommercialInvoice.enciOrders[${index}].psc"/></td>
                <td><input type="text" name="enCommercialInvoice.enciOrders[${index}].price" style="width: 30%"/><select><option value="EXW">EXW</option><option value="CIF">CIF</option></select></td>
                <td><input type="text" name="enCommercialInvoice.enciOrders[${index}].totalAmount"/></td>
            </tr>
        </s:iterator>
        <tr>
            <td colspan="5">Followings is the bank information of Shanghai Ao Mei Industrial Co., Ltd. </td>
        </tr>
        <tr>
            <td style="border-right: 0">&nbsp;</td>
            <td colspan="4" style="border-left: 0">Company Name: Shanghai Ao Mei Industrial Co., Ltd.	</td>
        </tr>
        <tr>
            <td style="border-right: 0">&nbsp;</td>
            <td colspan="4" style="border-left: 0">Account: OSA11443631363633</td>
        </tr>
        <tr>
            <td style="border-right: 0">&nbsp;</td>
            <td colspan="4" style="border-left: 0">SWIFT： SPDBCNSHOSA</td>
        </tr>
        <tr>
            <td style="border-right: 0">&nbsp;</td>
            <td colspan="4" style="border-left: 0">Bank Name: SHANGHAI  PUDONG  DEVELOPMENT  BANK</td>
        </tr>
        <tr>
            <td style="border-right: 0">&nbsp;</td>
            <td colspan="4" style="border-left: 0">Bank Address: No.12  ZHONG  SHAN  DONG  YI  LU , SHANGHAI , CHINA</td>
        </tr>
    </TABLE>
    <div id="button" style="margin-top: 20px;">
        <a href="javascript:void(0)" onclick="saveEnInvoice()" class="easyui-linkbutton" style="width: 100px;">提交</a> &nbsp;&nbsp;&nbsp;&nbsp;
        <a href="javascript:void(0)" class="easyui-linkbutton" onclick="formReset()" style="width: 100px;">重置</a>
        <input id="res" name="res" type="reset" style="display:none;" />
    </div>
</form>
<div id="dd"></div>
</body>
</html>

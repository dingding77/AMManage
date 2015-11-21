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
        .invoiceTab tr td{ padding-left: 5px;}
        .invoiceTab tr td input{border: 0; border-bottom: 1px solid gray; outline: none;font-weight: bold; color: #0000ff;}
        .maininfo td{ text-align: center; height: 30px;}
        .maininfo td input{ text-align: center; width: 100px;}
        .sonTab{margin:0; margin-top: 20px;}
        .sonTab tr td{border: 0;}
        .tabTitle{ text-align: center; font-weight: bold; font-size: 23px; padding: 5px;}
        .combo>input[class*="combo-text validatebox-text"]{ border: 0;outline: none;}

    </style>
    <script type="text/javascript" src="<%=contextPath %>/js/calculate.js"></script>
    <script>
        $(function(){
            $('input[type=text][required=true]').validatebox();
            for(var i=0;i<0;i++){
                $('#gooosNum_'+i).numberbox({
                    onChange:function(){
                        var goodsNum=($(this).val());
                        var gooosPrice=($('#gooosPrice_'+i).numberbox('getValue'));

                        if(goodsNum!='' && gooosPrice !=''){
                            $('#goodsAmount_'+i).val(mul(goodsNum,gooosPrice));
                        }
                    }
                });
                $('#gooosPrice_'+i).numberbox({
                    onChange:function(){
                        var gooosPrice=($(this).val());
                        var goodsNum=($('#gooosNum_'+i).numberbox('getValue'));
                        if(goodsNum!='' && gooosPrice !=''){
                            $('#goodsAmount_'+i).val(mul(goodsNum,gooosPrice));
                        }else{
                            $('#goodsAmount_'+i).val('');
                        }
                    }
                });
            }
        });
        function saveZhInvoice(){
            var url='zhInvoiceAddSave.htm';
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
        function calcMoney(index){
            var gooosPrice=($('#gooosPrice_'+index).numberbox('getValue'));
            var goodsNum=($('#gooosNum_'+index).numberbox('getValue'));
            if(goodsNum!='' && gooosPrice !=''){
                $('#goodsAmount_'+index).val(mul(goodsNum,gooosPrice));
            }else{
                if(goodsNum==''&&gooosPrice!=''){
                    $('#goodsNum_'+index).validatebox({required: true});
                    $.parser.parse($("#goodsNum_"+index));
                }else if(goodsNum!=''&&gooosPrice==''){
                    $('#gooosPrice_'+index).validatebox({required: true});
                    $.parser.parse($("#gooosPrice_"+index));
                }
                $('#goodsAmount_'+index).val('');
            }
            var sumNum=0;
            $('input[id*="goodsAmount_"]').each(function(){
                var num=$(this).val();
                if(num&&num>0){
                    sumNum=add(sumNum,num);
                }
            });
            if(sumNum>0){
                $('#total_span').text(sumNum);
            }
        }
    </script>

</head>
<body LEFTMARGIN=0 TOPMARGIN=0 MARGINWIDTH=0 MARGINHEIGHT=0
      scroll=yes>
<form  id="form1" action="" method="post" >
    <TABLE class="invoiceTab" WIDTH="80%" BORDER="0" ALIGN="CENTER" CELLPADDING="0" CELLSPACING="0" BGCOLOR="#fff">
        <tr>
            <td colspan="8"  style="font-size:20px; font-weight:bold; text-align:center;">上海傲美实业有限公司</td>
        </tr>
        <tr>
            <td colspan="8" style="text-align:center">上海市嘉定区南翔镇昌翔路575号2号门2楼  TEL:021-66306672   FAX:021-56065197
            </td>
        </tr>
        <tr>
            <td colspan="8" style="font-size:20px; font-weight:bold; text-align:center">送  货  单</td>
        </tr>
        <tr>
            <td colspan="5">
                对应订单类型&nbsp;&nbsp;<input name="deliveryNote.relationOrderType" value="1" type="radio" checked="true" />生产单<input name="deliveryNote.relationOrderType" value="2" type="radio"/>采购单
            </td>
            <td colspan="3">
                <a class="easyui-linkbutton" onclick="chooseOrderInfo()" style="margin-left: 5px;">新增关联订单</a>
                <input type="hidden" id="orderNo" name="deliveryNote.orderNo" value="${deliveryNote.orderNo}"/>
                <label id="span_orderNo"></label>
            </td>
        </tr>
        <tr>
            <td colspan="5">客户名称：<input required="true" type="text"name="deliveryNote.customerName" /></td>
            <td colspan="3">送货单号：<input required="true" type="text" name="deliveryNote.deliverNo"/></td>
        </tr>
        <tr>
            <td colspan="5">付款方式：<input type="text" name="deliveryNote.paymentWay"/></td>
            <td colspan="3">送货方式：<input type="text" name="deliveryNote.deliverWay"/></td>
        </tr>
        <tr>
            <td colspan="5">制单日期：
                <input class="Wdate" name="deliveryNote.doctradeDate"  type="text"   onFocus="WdatePicker({isShowClear:false})"/>
            </td>
            <td colspan="3">送货日期：
                <input class="Wdate" name="deliveryNote.deliverDate"  type="text"   onFocus="WdatePicker({isShowClear:false})"/>
            </td>
        </tr>
        <tr class="title">
            <td>货物编号</td>
            <td>客户编号/合同号</td>
            <td>货物名称</td>
            <td>数 量</td>
            <td>单位</td>
            <td>单 价</td>
            <td>金 额</td>
            <td>实发数量</td>
        </tr>
        <s:iterator begin="0" end="4" var="index">
            <tr class="maininfo">
                <td><input type="text"  name="deliveryNote.deliveryGoodsList[${index}].goodsNo"></td>
                <td><input type="text"  name="deliveryNote.deliveryGoodsList[${index}].contractNo"></td>
                <td><input type="text"  name="deliveryNote.deliveryGoodsList[${index}].goodsName"></td>
                <td><input type="text"  id="gooosNum_${index}"  class="textbox-text easyui-numberbox" name="deliveryNote.deliveryGoodsList[${index}].goodsNum" data-options="min:1,onChange:function(){calcMoney(${index})}"></td>
                <td><input type="text"  name="deliveryNote.deliveryGoodsList[${index}].goodsUnit"></td>
                <td><input type="text" id="gooosPrice_${index}" class="textbox-text easyui-numberbox" data-options="precision:4,min:0,onChange:function(){calcMoney(${index})}"  name="deliveryNote.deliveryGoodsList[${index}].goodsPrice"></td>
                <td><input type="text" id="goodsAmount_${index}"  name="deliveryNote.deliveryGoodsList[${index}].goodsAmount"></td>
                <td><input type="text"  name="deliveryNote.deliveryGoodsList[${index}].realsendNum"></td>
            </tr>
        </s:iterator>
        <tr>
            <td>大写金额:</td>
            <td colspan="4">&nbsp;</td>
            <td>合计金额:</td>
            <td colspan="2"><span id="total_span"></span></td>
        </tr>
        <tr>
            <td colspan="8">
    <pre>
    1，上列货品请需方入库和使用本产品前，请严格检查，若有不符，请与7日内提出异议，逾期视为接受。
    2，本公司只对产品本身负责（退货，换货，补数），若因为产品带来的其他索赔，概不承担.
    3，染料，化学剂，高温烫气及其他造成产品褪色或污染，这非本公司产品瑕疵，需方使用前，请自行彻底测试。
    4，①存根（白），②客户（红），③回单（黄）， 打印有效，书写无效！
    </pre>
            </td>
        </tr>
        <tr>
            <td colspan="2">制单人:${user.username}<input type="hidden" value="${user.username}" name="deliveryNote.doctradeUser"/></td>
            <td colspan="3">业务员:<input type="text" value="${user.username}" name="deliveryNote.busiUser"></td>
            <td colspan="3">收货人签名:<input type="text" name="deliveryNote.receiver"></td>
        </tr>
    </TABLE>
    <div id="button" style="margin-top: 20px;">
        <a href="javascript:void(0)" class="easyui-linkbutton" onclick="saveZhInvoice()" style="width: 100px;">提交</a> &nbsp;&nbsp;&nbsp;&nbsp;
        <a href="javascript:void(0)" class="easyui-linkbutton"  onclick="formReset()" style="width: 100px;">重置</a>
        <input id="res" name="res" type="reset" style="display:none;" />
    </div>
</form>
<div id="dd"></div>
</body>
<script>
    var orderFlag='';
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
    function chooseOrderInfo(){
        var orderType=$('input[name="deliveryNote.relationOrderType"]:checked').val();
        if(orderType=='1'){//生产单
            orderFlag='proNo';
            $('#dd').dialog({href: 'order-list.htm?relation=relation'})
            $('#dd').dialog('open');
        }else if(orderType=='2'){//采购单
            orderFlag='purchaseNo';

            $('#dd').dialog({href: 'purchase/list.htm?relation=relation'})
            $('#dd').dialog('open');
        }else{
            $.messager.alert('操作提示','未匹配到订单类型','info');
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
</html>

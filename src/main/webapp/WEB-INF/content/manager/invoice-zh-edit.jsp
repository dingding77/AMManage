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
<script>
    $(function(){
        $('input[type=text][required=true]').validatebox();
    });
    function saveZhInvoice(){
        var url='zhInvoiceEditSave.htm';
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
                    $.messager.alert('提示','修改成功','info',function(){
                        window.parent.refreshTab(getCurTabUrl());
                    });

                }

            }
        });
    }
    function formReset(){
        $("input[name='res']").click();
    }

    function getCurTabUrl(){
        var currTab =  self.parent.$('#tabs').tabs('getSelected'); //获得当前tab
        var url = $(currTab.panel('options').content).attr('src');
        return url;
    }

</script>

</head>
<body LEFTMARGIN=0 TOPMARGIN=0 MARGINWIDTH=0 MARGINHEIGHT=0
      scroll=yes>
<form  id="form1" action="" method="post" >
    <TABLE class="invoiceTab" WIDTH="80%" BORDER="0" ALIGN="CENTER" CELLPADDING="0" CELLSPACING="0" BGCOLOR="#fff">
        <tr>
            <td colspan="8" style="font-size:20px; font-weight:bold; text-align:center;">上海傲美实业有限公司</td>
        </tr>
        <tr>
            <td colspan="8" style="text-align:center">上海市嘉定区南翔镇昌翔路575号2号门2楼  TEL:021-66306672   FAX:021-56065197
            </td>
        </tr>
        <tr>
            <td colspan="8" style="font-size:20px; font-weight:bold; text-align:center">
                送  货  单
                <input type="hidden" name="deliveryNote.id" value="${deliveryNote.id}" />
            </td>
        </tr>
        <tr>
            <td colspan="5">客户名称：<input required="true" type="text" name="deliveryNote.customerName"  value="${deliveryNote.customerName}"/></td>
            <td colspan="3">送货单号：<input required="true" type="text" name="deliveryNote.deliverNo" value="${deliveryNote.deliverNo}"/></td>
        </tr>
        <tr>
            <td colspan="5">付款方式：<input type="text" name="deliveryNote.paymentWay" value="${deliveryNote.paymentWay}"/></td>
            <td colspan="3">送货方式：<input type="text" name="deliveryNote.deliverWay" value="${deliveryNote.deliverWay}"/></td>
        </tr>
        <tr>
            <td colspan="5">制单日期：
                <input class="Wdate" name="deliveryNote.doctradeDate" value='<s:date name="deliveryNote.doctradeDate" format="yyyy-MM-dd"/>'  type="text"  style="cursor: pointer" onFocus="WdatePicker({isShowClear:false})"/>
            </td>
            <td colspan="3">送货日期：
                <input class="Wdate" name="deliveryNote.deliverDate" value='<s:date name="deliveryNote.deliverDate" format="yyyy-MM-dd"/>' type="text"  style="cursor: pointer" onFocus="WdatePicker({isShowClear:false})"/>
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
        <s:iterator value="deliveryNote.deliveryGoodsList" status="status" var="item">
            <tr class="maininfo">
                <td>
                    <input type="hidden"  name="deliveryNote.deliveryGoodsList[${status.index}].id" value="<s:property value='#item.id' />" />
                    <input type="text"  name="deliveryNote.deliveryGoodsList[${status.index}].goodsNo" value="<s:property value='#item.goodsNo' />" />
                </td>
                <td><input type="text"  name="deliveryNote.deliveryGoodsList[${status.index}].contractNo" value="<s:property value='#item.contractNo'/>"></td>
                <td><input type="text"  name="deliveryNote.deliveryGoodsList[${status.index}].goodsName" value="<s:property value='#item.goodsName'/>"></td>
                <td><input type="text"  name="deliveryNote.deliveryGoodsList[${status.index}].goodsNum" value="<s:property value='#item.goodsNum'/>"></td>
                <td><input type="text"  name="deliveryNote.deliveryGoodsList[${status.index}].goodsUnit" value="<s:property value='#item.goodsUnit'/>"></td>
                <td><input type="text"  name="deliveryNote.deliveryGoodsList[${status.index}].goodsPrice" value="<s:property value='#item.goodsPrice'/>"></td>
                <td><input type="text"  name="deliveryNote.deliveryGoodsList[${status.index}].goodsAmount" value="<s:property value='#item.goodsAmount'/>"></td>
                <td><input type="text"  name="deliveryNote.deliveryGoodsList[${status.index}].realsendNum" value="<s:property value='#item.realsendNum'/>"></td>
            </tr>
        </s:iterator>
        <tr>
            <td>大写金额:</td>
            <td colspan="4">&nbsp;</td>
            <td>合计金额:</td>
            <td colspan="2">&nbsp;</td>
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
            <td colspan="2">制单人:${user.userName}</td>
            <td colspan="3">业务员:<input type="text" value="${user.userName}"></td>
            <td colspan="3">收货人签名:<input type="text"></td>
        </tr>
    </TABLE>
    <div id="button" style="margin-top: 20px;">
        <a href="javascript:void(0)" class="easyui-linkbutton" onclick="saveZhInvoice()" style="width: 100px;">提交</a> &nbsp;&nbsp;&nbsp;&nbsp;
        <a href="javascript:void(0)" class="easyui-linkbutton"  onclick="formReset()" style="width: 100px;">重置</a>
        <input id="res" name="res" type="reset" style="display:none;" />
    </div>
</form>

</body>
</html>

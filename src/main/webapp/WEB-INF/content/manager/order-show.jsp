<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/content/common/header.jspf"%>
    <link rel="stylesheet" type="text/css" href="<%=contextPath %>/css/newtable.css" />
    <style>
        th,td{ line-height: 30px; padding-left: 5px;}
        .orderTab tr td input{border: 0; border-bottom: 1px solid gray; outline: none;}
        .firstTabInfo td{ text-align: center}
        .splitRow th{ border: 0;}
        .Wdate{width: 100px;}
    </style>
    <script>
        $(function(){
            $('input[type=text][required=true]').validatebox();
        });
        function saveOrder(){
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
                <input type="text" required="true" value="${manufactureOrder.cstmCode}" name="manufactureOrder.cstmCode">
                <input type="hidden" name="manufactureOrder.id" value="${manufactureOrder.id}">
            </td>
            <td>生产单编号</td>
            <td colspan="3"><input type="text" required="true" value="${manufactureOrder.proNo}" name="manufactureOrder.proNo"></td>
        </tr>
        <tr class="firstTabInfo">
            <td>下单日期</td>
            <td colspan="3"><input class="Wdate" value='<s:date name="manufactureOrder.orderDate" format="yyyy-MM-dd" />'  name="manufactureOrder.orderDate" type="text" style="cursor: pointer" onFocus="WdatePicker({isShowClear:false})"/></td>
            <td>交货日期</td>
            <td colspan="3"><input class="Wdate" type="text"  value='<s:date name="manufactureOrder.deliveryDate" format="yyyy-MM-dd" />' name="manufactureOrder.deliveryDate"></td>
        </tr>
        <tr class="firstTabInfo">
            <td>后道要求</td>
            <td colspan="3"><input type="text" name="manufactureOrder.houdaoRequests" value="${manufactureOrder.houdaoRequests}"></td>
            <td>机台</td>
            <td colspan="3"><input type="text" name="manufactureOrder.board" value="${manufactureOrder.board}"></td>
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
                        <td style="border-left: 0;">品名</td>
                        <td>数量</td>
                        <td>尺码</td>
                        <td>款号</td>
                        <td>色号</td>
                        <td>货物编号</td>
                        <td>客户编号</td>
                    </tr>
                    <tr>
                        <td>
                            <input id="cc" class="easyui-combobox purchaseOrderSelName" value="${manufactureOrder.detailList[0].name}" name="manufactureOrder.detailList[0].name" data-options="valueField:'name',textField:'name',url:'/AMManage/manager/product/getListJonsBuQuery.htm'">
                            <input name="manufactureOrder.detailList[0].id" value="${manufactureOrder.detailList[0].id}" type="hidden"/>
                        </td>
                        <td><input name="manufactureOrder.detailList[0].num" value="${manufactureOrder.detailList[0].num}" type="text"/></td>
                        <td><input name="manufactureOrder.detailList[0].size" value="${manufactureOrder.detailList[0].size}" type="text"/></td>
                        <td><input name="manufactureOrder.detailList[0].kh" value="${manufactureOrder.detailList[0].kh}" type="text"/></td>
                        <td><input name="manufactureOrder.detailList[0].colorSize" value="${manufactureOrder.detailList[0].colorSize}" type="text"/></td>
                        <td><input name="manufactureOrder.detailList[0].hwbh" value="${manufactureOrder.detailList[0].hwbh}" type="text"/></td>
                        <td><input name="manufactureOrder.detailList[0].kebh" value="${manufactureOrder.detailList[0].kebh}" type="text"/></td>
                    </tr>
                </table>
            </td>
        </tr>
        <tr>
            <td colspan="8">材料记录:</td>
        </tr>
        <tr>
            <td colspan="2">领料日期:
                <input class="Wdate" type="text" name="manufactureOrder.callslipDate" value='<s:date name="manufactureOrder.callslipDate" format="yyyy-MM-dd" />' style="cursor: pointer" onFocus="WdatePicker({isShowClear:false})"/>
            </td>
            <td colspan="2">材料编号:<input type="text" name="manufactureOrder.materialNo" value="${manufactureOrder.materialNo}"></td>
            <td colspan="2">实需数量:<input type="text" name="manufactureOrder.needNum" value="${manufactureOrder.needNum}"></td>
            <td colspan="2">实领数量:<input type="text" name="manufactureOrder.realNum"value="${manufactureOrder.realNum}"></td>
        </tr>
        <tr>
            <td colspan="2">返料日期:
                <input class="Wdate" type="text" name="manufactureOrder.revertDate" value='<s:date name="manufactureOrder.revertDate" format="yyyy-MM-dd" />' style="cursor: pointer" onFocus="WdatePicker({isShowClear:false})"/>
            </td>
            <td colspan="6">数&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;量:
                <input type="text" value="${manufactureOrder.revertNum}" name="manufactureOrder.revertNum">
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
            <td colspan="2">翻单:<input type="text" value="${manufactureOrder.repeatOrder}" name="manufactureOrder.repeatOrder"></td>
            <td colspan="6">新版:<input type="text" value="${manufactureOrder.newEdition}" name="manufactureOrder.newEdition"></td>
        </tr>
        <tr>
            <td colspan="4">生产操作员:<input type="text" name="manufactureOrder.proOperator" value="${manufactureOrder.proOperator}"></td>
            <td colspan="4">生产贴样处:<input type="text" name="manufactureOrder.proPasteLike" value="${manufactureOrder.proPasteLike}"></td>
        </tr>
        <tr>
            <td colspan="8">耗&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;时:
                <input type="text" name="manufactureOrder.proTimeConsuming" value="${manufactureOrder.proTimeConsuming}">
            </td>
        </tr>
        <tr>
            <td colspan="8">生&nbsp;产&nbsp;日&nbsp;期:
                <input class="Wdate" type="text" name="manufactureOrder.proDate" value='<s:date name="manufactureOrder.proDate" format="yyyy-MM-dd"/>' style="cursor: pointer" onFocus="WdatePicker({isShowClear:false})"/>
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
            <td colspan="8">剪折操作员:<input type="text" name="manufactureOrder.fssOperator" value="${manufactureOrder.fssOperator}"></td>
        </tr>
        <tr>
            <td colspan="8">耗&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;时:
                <input type="text" name="manufactureOrder.fssTimeConsuming" value="${manufactureOrder.fssTimeConsuming}">
            </td>
        </tr>
        <tr>
            <td colspan="8">剪&nbsp;折&nbsp;日&nbsp;期:
                <input class="Wdate" type="text" name="manufactureOrder.fssDate" value='<s:date name="manufactureOrder.fssDate" format="yyyy-MM-dd"/>' style="cursor: pointer" onFocus="WdatePicker({isShowClear:false})"/>
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
            <td colspan="4">品检操作员:<input type="text" name="manufactureOrder.qcOperator" value="${manufactureOrder.qcOperator}"></td>
            <td colspan="4">货样贴样处:<input type="text" name="manufactureOrder.goodsPasteLike" value="${manufactureOrder.goodsPasteLike}"></td>
        </tr>
        <tr>
            <td colspan="8">耗&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;时:
                <input type="text" name="manufactureOrder.qcTimeConsuming" value="${manufactureOrder.qcTimeConsuming}">
            </td>
        </tr>
        <tr>
            <td colspan="8">包装明细:<input type="text" name="manufactureOrder.packDetail" value="${manufactureOrder.packDetail}"></td>
        </tr>
        <tr>
            <td colspan="8">品检日期:
                <input class="Wdate" type="text" name="manufactureOrder.qcDate" value='<s:date name="manufactureOrder.qcDate" format="yyyy-MM-dd" />' style="cursor: pointer" onFocus="WdatePicker({isShowClear:false})"/>
            </td>
        </tr>
        <tr>
            <td>备注</td>
            <td colspan="7">
                <textarea cols="50" rows="5" name="manufactureOrder.remark">
                    ${manufactureOrder.remark}
                </textarea>
            </td>
        </tr>
        <tr>
            <td colspan="3" style="text-decoration: underline; border-right: 0;">产品计划:
                <input type="text" name="manufactureOrder.proPlanning" value="${manufactureOrder.proPlanning}"/>
            </td>
            <td colspan="2" style="border-left: 0; border-right: 0;"></td>
            <td colspan="3" style="text-decoration: underline; border-left: 0;">产品跟单:
                <input type="text" name="manufactureOrder.proDocumentary" value="${manufactureOrder.proDocumentary}"/>
            </td>
        </tr>
    </TABLE>
    </div>
</form>
</body>
</html>

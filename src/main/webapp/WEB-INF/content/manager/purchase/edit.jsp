<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<%@include file="../../common/header.jspf"%>
<link rel="stylesheet" type="text/css" href="<%=contextPath %>/css/newtable.css" />
<script type="text/javascript" src="<%=contextPath %>/js/order.js"></script>

<style>
    th,td{ line-height: 30px; padding-left: 5px;}
    .customerTab{ margin: 10px auto;}
    .customerTab tr td input{border: 0;  border-bottom: 1px solid gray; outline: none;font-weight: bold; color: #0000ff;}
    .detail tr td input{width:100px;}
    .firstTabInfo td{ text-align: center}
    .splitRow th{ border: 0;}
    .Wdate{width: 100px;}
    .combo>input[class*="combo-text validatebox-text"]{ border: 0;outline: none;height: auto}
</style>
<script>
    var _index=1;
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
    function addRowForDetail(){
        var json={'detailIndex':_index};
        //获取表格倒数第二行
        var targetRow=$('#PURCHASE_DETAIL tbody tr:eq(-2)');
        targetRow.after('<tr>'+$('#detailList').tpl(json).html()+'</tr>');
        $.parser.parse($('#PURCHASE_DETAIL tbody tr:eq(-2)'));
        _index=_index+1;
        addEventForInput();
        addComBoboxEvent();
    }
    function caclTotalAmt(){
        var productSubtotal=0;
        $('input[id*="totalAmt_"]').each(function(){
            var totalAmt=$(this).val();
            if(totalAmt!=null&&totalAmt!=''){
                productSubtotal=add(productSubtotal,totalAmt);
            }
        });
        $('#product-subtotal').text(productSubtotal);
    }
    function addEventForInput(i){
        $('#buyNum_'+i).numberbox({
            onChange:function(){
                var goodsNum=($(this).val());
                var gooosPrice=($('#price_'+i).numberbox('getValue'));

                if(goodsNum!='' && gooosPrice !=''){
                    $('#totalAmt_'+i).val(mul(goodsNum,gooosPrice));
                }else{
                    $('#totalAmt_'+i).val('');
                }
                caclTotalAmt();
            }
        });
        $('#price_'+i).numberbox({
            onChange:function(){
                var gooosPrice=($(this).val());
                var goodsNum=($('#buyNum_'+i).numberbox('getValue'));
                if(goodsNum!='' && gooosPrice !=''){
                    $('#totalAmt_'+i).val(mul(goodsNum,gooosPrice));
                }else{
                    $('#totalAmt_'+i).val('');
                }
                caclTotalAmt();
            }
        });
    }
    function deleteRowForDetail(obj){
        var _row=obj.parents('tr');
        _row.remove();
    }
</script>

</head>

<script type="text/javascript">
    (function($){
        $.fn.tpl = function(data){
            $.template('template', $(this).html().replace(/@/g,"$"));
            return $.tmpl('template', data);
        }
    })($);
    $(function(){
        addComBoboxEvent();
    });
    function addComBoboxEvent(){
        $("input[class*='purchaseOrderSelName']:last").combobox({
                    onChange: function (newVal,oldVal) {
                        var _this=$(this);
                        $.ajax({
                            url:'../product/getListJonsByCode.htm?query.code='+newVal,
                            type:'POST',
                            dataType:'json',
                            success:function(data){
                                var proInfo=eval(data);

                                var _styleNo=proInfo.styleNo;
                                var _pantoneNo=proInfo.pantoneNo;
                                var _size=proInfo.size;
                                var _price=proInfo.price;

                                var _row=_this.parent().parent();
                                var _tds=_row.children('td');
                                _tds.eq(1).children('input').val(_styleNo);
                                _tds.eq(2).children('input').val(_pantoneNo);
                                _tds.eq(3).children('input').val(_size);
                                var id=(_tds.eq(4).find('input:eq(0)').attr('id'));
                                $('#'+id).numberbox('setValue', _price);
                            }
                        });
                    }}
        );
    }
</script>
<script id="detailList" type="text/x-jquery-tmpl">
        <tr>
            <td>
                <input class="easyui-combobox purchaseOrderSelName"  required="true" name="purchaseOrder.detailList[0].name"
                       data-options="valueField:'code',textField:'name',url:'../product/getListJonsBuQuery.htm'">
            </td>
            <td>
                <input type="text" name="purchaseOrder.detailList[@{detailIndex}].styleNo"/>
            </td>
            <td>
                <input type="text" name="purchaseOrder.detailList[@{detailIndex}].colorNo"/>
            </td>
            <td>
                <input type="text" name="purchaseOrder.detailList[@{detailIndex}].size"/>
            </td>
            <td>
                <input type="text" id="price_@{detailIndex}"  name="purchaseOrder.detailList[@{detailIndex}].price"  class="easyui-numberbox"  precision="4" required="true"/>
            </td>
            <td>
                <input type="text" id="buyNum_@{detailIndex}" name="purchaseOrder.detailList[@{detailIndex}].buyNum" class="easyui-numberbox" required="true"/>
            </td>
            <td>
                <input type="text" id="totalAmt_@{detailIndex}" name="purchaseOrder.detailList[@{detailIndex}].totalAmt" style="background-color:white;"  />
            </td>
            <td>
                <input type="text"name="purchaseOrder.detailList[@{detailIndex}].remark"/>
            </td>
            <td style="width: 100px; text-align: center;">
                <a href="javascript:void(0)" class="easyui-linkbutton" onclick="deleteRowForDetail($(this))">删除</a>
            </td>
        </tr>
</script>
<body LEFTMARGIN=0 TOPMARGIN=0 MARGINWIDTH=0 MARGINHEIGHT=0
      scroll=yes>

<form  id="FORM" method="post">
    <TABLE id="PURCHASE_ORDER" class="customerTab" WIDTH="100%" BORDER="0" ALIGN="CENTER" CELLPADDING="0" CELLSPACING="0" BGCOLOR="#fff">
        <tr>
            <th colspan="2">采购方</th>
            <th colspan="2">供应方</th>
        </tr>
        <tr>
            <th>单位名称</th>
            <td>
                <input type="hidden" name="purchaseOrder.id" value="${purchaseOrder.id}">
                <input name="companyName"  value="${companyInfo.name}"/>
                <input type="hidden" name="purchaseOrder.extInfo" id="extInfo">
            </td>
            <th>单位名称</th>
            <td>
                <input name="supplierName"  class="easyui-textbox" value="${purchaseOrder.supplierName}" />
            </td>
        </tr>

        <tr>
            <th>联系人</th>
            <td>
                <input name="companyContract" value="${companyInfo.contract}"/>
            </td>
            <th>联系人</th>
            <td>
                <input name="supplierContract" value="${purchaseOrder.supplierContract}" class="easyui-textbox"/>
            </td>
        </tr>


        <tr>
            <th>电话</th>
            <td>
                <input name="companyPhone" value="${companyInfo.telephone}"/>
            </td>
            <th>日期</th>
            <td>
                <input name="supplierDate" value='${purchaseOrder.supplierDate}'   onfocus="WdatePicker()"   class="Wdate" style="cursor: pointer"/>
            </td>
        </tr>
        <tr>
            <th>日期</th>
            <td>
                <input name="companyDate" value='${purchaseOrder.companyDate}' onfocus="WdatePicker()"  class="Wdate" style="cursor: pointer"/>
            </td>
            <th>电话</th>
            <td>
                <input name="supplierPhone" value="${purchaseOrder.supplierPhone}"  class="easyui-textbox"/>
            </td>
        </tr>
        <tr>
            <th>备注</th>
            <td colspan="3">
                <textarea cols="100" name="companyRemark" value="${purchaseOrder.remark}"></textarea>
            </td>
        </tr>
        <tr>
            <th>收货人员</th>
            <td colspan="3">
                <input name="receiver" value="${purchaseOrder.receiver}" />
            </td>
        </tr>
        <tr>
            <th>付款方式</th>
            <td colspan="3">
                <select name="purchaseOrder.payment">
                    <option value="月结">月结</option>
                </select>
            </td>
        </tr>
        <tr>
            <th>我司交期</th>
            <td colspan="3">
                <input name="purchaseOrder.deliveryTime" value='<s:date name="purchaseOrder.deliveryTime"   format="yyyy-MM-dd"/>' onfocus="WdatePicker()"   class="Wdate" style="cursor: pointer"/>
            </td>
        </tr>
    </TABLE>
    <!--订单明细信息-->
    <TABLE ID="PURCHASE_DETAIL" class="customerTab detail" WIDTH="100%" BORDER="0" ALIGN="CENTER" CELLPADDING="0" CELLSPACING="0" BGCOLOR="#fff">
        <tr>
            <th>品名</th>
            <th>款号</th>
            <th>色号</th>
            <th>尺码</th>
            <th>单价</th>
            <th>数量</th>
            <th>金额</th>
            <th>备注</th>
            <th>操作</th>
        </tr>
        <s:iterator value="purchaseOrder.detailList" var="detail" status="status">
        <tr>
            <td>
                <input type="hidden" name="purchaseOrder.detailList[<s:property value='#status.index'/>].purchaseId" value="<s:property value='#detail.purchaseId'/>"/>
                <input type="hidden" name="purchaseOrder.detailList[<s:property value='#status.index'/>].id" value="<s:property value='#detail.id'/>"/>
                <input class="easyui-combobox purchaseOrderSelName" value="<s:property value='#detail.name'/>"  required="true" name="purchaseOrder.detailList[<s:property value='#status.index'/>].name"
                       data-options="valueField:'code',textField:'name',url:'../product/getListJonsBuQuery.htm'">
            </td>
            <td>
                <input type="text" value="<s:property value='#detail.styleNo'/>"  name="purchaseOrder.detailList[<s:property value='#status.index'/>].styleNo"/>
            </td>
            <td>
                <input type="text" value="<s:property value='#detail.colorNo'/>" name="purchaseOrder.detailList[<s:property value='#status.index'/>].colorNo"/>
            </td>
            <td>
                <input type="text" value="<s:property value='#detail.size'/>" name="purchaseOrder.detailList[<s:property value='#status.index'/>].size"/>
            </td>
            <td>
                <input type="text" id="price_0" value="<s:property value='#detail.price'/>"  name="purchaseOrder.detailList[<s:property value='#status.index'/>].price"  class="easyui-numberbox"  precision="4" required="true"/>
            </td>
            <td>
                <input type="text" id="buyNum_0" value="<s:property value='#detail.buyNum'/>"  name="purchaseOrder.detailList[<s:property value='#status.index'/>].buyNum" class="easyui-numberbox" required="true"/>
            </td>
            <td>
                <input type="text" id="totalAmt_0" value="<s:property value='#detail.totalAmt'/>"  name="purchaseOrder.detailList[<s:property value='#status.index'/>].totalAmt" style="background-color:white;"  />
            </td>
            <td>
                <input type="text" value="<s:property value='#detail.remark'/>"  name="purchaseOrder.detailList[<s:property value='#status.index'/>].remark"/>
            </td>
            <td style="width: 100px; text-align: center;">
                <a href="javascript:void(0)" class="easyui-linkbutton" onclick="addRowForDetail()">添加</a>
            </td>
        </tr>
        </s:iterator>
        <tr>
            <td colspan="9" style="text-align: right;">产品小计:
                <input type="text" class="total-box easyui-numberbox" id="product-subtotal" style="background-color: white; width: 200px;" >
            </td>
        </tr>
    </TABLE>
    <TABLE class="customerTab detail" WIDTH="100%" BORDER="0" ALIGN="CENTER" CELLPADDING="0" CELLSPACING="0" BGCOLOR="#fff">
        <tr>
            <td>
                审核:<input type="text" id="auditor" value="${purchaseOrder.auditor}" name="purchaseOrder.auditor"/>
            </td>
            <td>
                主管:<input type="text" id="director" value="${purchaseOrder.director}" name="purchaseOrder.director"/>
            </td>
            <td>
                部门:<input type="text" id="department" value="${purchaseOrder.department}" name="purchaseOrder.department"/>
            </td>
            <td>
                业务员:<input type="text" id="salesman" value="${purchaseOrder.salesman}" name="purchaseOrder.salesman"/>
            </td>
            <td>
                制单:<input type="text" id="touching" value="${purchaseOrder.touching}" name="purchaseOrder.touching"/>
            </td>
        </tr>
    </TABLE>
    <div id="button" style="margin-top: 20px;">
        <a href="javascript:void(0)" onclick="addSave()" class="easyui-linkbutton">提交</a> &nbsp;&nbsp;&nbsp;&nbsp;
        <a href="javascript:void(0)" class="easyui-linkbutton" onclick="formReset()">重置</a>
        <input id="res" name="res" type="reset" style="display:none;" />
    </div>
</form>
</body>
<script type="text/javascript">
    function calcProdSubTotal() {
        var prodSubTotal = 0;
        $("input[class='row-total-input']").each(function () {
            var valString = $(this).val() || 0;
            prodSubTotal=parseFloat(parseFloat(prodSubTotal)+parseFloat(valString)).toFixed(4);
        });
        $("#product-subtotal").parents('td').find('input:eq(1)').css('background-color','white').val(prodSubTotal);
    };
    function addSave(){
        var fields=$('form>table[id="PURCHASE_ORDER"] textarea,input[name!="purchaseOrder.extInfo"]').serializeArray();
        var data = "{";//构建的json数据
        $.each( fields, function( i, field ) {
            if(i!==fields.length-1){
                data=data+( field.name+':\"'+field.value + '\",' );
            }else{
                data=data+( field.name+':\"'+field.value + '\"' );
            }
        });
        data=data+'}';
        $('#extInfo').val((data));
        operationSave();
    }

    function operationSave(){
        var url='editSave.htm';
        $('#FORM').form('submit',{
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
        $("input[name='res']").click();
    }

</script>
</html>

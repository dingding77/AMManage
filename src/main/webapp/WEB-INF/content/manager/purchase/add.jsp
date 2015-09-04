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
            alert(url);
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
            var json={detailIndex:_index};
            var html = template($('#detailList').html(),json);
            html=html.replace('purchaseOrder.detailList[]','purchaseOrder.detailList['+json['detailIndex']+'].styleNo');

            $('#PURCHASE_DETAIL tbody tr:last').before(html);
            var objNew=$('#PURCHASE_DETAIL tbody tr:last');
            $.parser.parse();
            _index=_index+1;
            addEventForInput();
        }
        function deleteRowForDetail(obj){
            var _row=obj.parent().parent();
            var _lastRow=$('#PURCHASE_DETAIL tr:last');
            var _lastInput=_lastRow.children('td').children('input')
            _row.children('td').children('input').each(function(_index){
                var _curName=$(this).attr('name');
                var lastName=_lastInput[_index].getAttribute('name')

                _lastInput[_index].setAttribute('name',_curName);
            });
            obj.parent().parent().remove();
            _index=_index-1;

        }
    </script>

</head>
<script id="detailList" type="text/html">
    <tr>
    <td>
    <input type="text" name="purchaseOrder.detailList[${detailIndex}].name"/>
            </td>
    <td>
    <input type="text" name="purchaseOrder.detailList[${detailIndex}].styleNo"/>
            </td>
        <td>
            <input type="text" name="purchaseOrder.detailList[${detailIndex}].colorNo"/>
        </td>
    <td>
    <input type="text" name="purchaseOrder.detailList[${detailIndex}].size"/>
            </td>
        <td>
            <input type="text"name="purchaseOrder.detailList[${detailIndex}].price"  class="price-per-pallet easyui-numberbox"  max="20" precision="4" required="true"/>
        </td>
        <td>
            <input type="text" name="purchaseOrder.detailList[${detailIndex}].buyNum" class=" num-pallets-input easyui-numberbox"/>
        </td>
        <td>
            <input type="text" name="purchaseOrder.detailList[${detailIndex}].totalAmt" class="row-total-input easyui-numberbox"/>
        </td>
    <td>
    <input type="text"name="purchaseOrder.detailList[${detailIndex}].remark"/>
            </td>
        <td>
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
                <input name="companyName" value="${companyInfo.name}"/>
                <input type="hidden" name="purchaseOrder.extInfo" id="extInfo">
            </td>
            <th>单位名称</th>
            <td>
                <input name="supplierName"/>
            </td>
        </tr>

        <tr>
            <th>联系人</th>
            <td>
                <input name="companyContract" value="${companyInfo.contract}"/>
            </td>
            <th>联系人</th>
            <td>
                <input name="supplierContract"/>
            </td>
        </tr>


        <tr>
            <th>电话</th>
            <td>
                <input name="companyPhone" value="${companyInfo.telephone}"/>
            </td>
            <th>日期</th>
            <td>
                <input name="supplierDate"  class="Wdate" style="cursor: pointer"/>
            </td>
        </tr>
        <tr>
            <th>日期</th>
            <td>
                <input name="companyDate" class="Wdate" style="cursor: pointer"/>
            </td>
            <th>电话</th>
            <td>
                <input name="supplierDate"/>
            </td>
        </tr>
        <tr>
            <th>备注</th>
            <td colspan="3">
                <textarea cols="100" name="companyRemark" value="${companyInfo.remark}"></textarea>
            </td>
        </tr>
        <tr>
            <th>收货人员</th>
            <td colspan="3">
                <input name="receiver"/>
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
        <tr>
            <td>
                <input type="text" name="purchaseOrder.detailList[0].name"/>
            </td>
            <td>
                <input type="text" name="purchaseOrder.detailList[0].styleNo"/>
            </td>
            <td>
                <input type="text" name="purchaseOrder.detailList[0].colorNo"/>
            </td>
            <td>
                <input type="text" name="purchaseOrder.detailList[0].size"/>
            </td>
            <td>
                <input type="text"name="purchaseOrder.detailList[0].price"  class="price-per-pallet easyui-numberbox"  max="20" precision="4" required="true"/>
            </td>
            <td>
                <input type="text" name="purchaseOrder.detailList[0].buyNum" class=" num-pallets-input easyui-numberbox"/>
            </td>
            <td>
                <input type="text" name="purchaseOrder.detailList[0].totalAmt" class="row-total-input easyui-numberbox"/>
            </td>
            <td>
                <input type="text"name="purchaseOrder.detailList[0].remark"/>
            </td>
            <td>
                <a href="javascript:void(0)" class="easyui-linkbutton" onclick="addRowForDetail()">添加</a>
            </td>
        </tr>
        <tr>
            <td colspan="9" style="text-align: right;">产品小计:
                <input type="text" class="total-box easyui-numberbox" id="product-subtotal" disabled="disabled">
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
    function addSave(){
        var fields=$('form>table[id="PURCHASE_ORDER"] input').serializeArray();
        var data = "{";//构建的json数据
        $.each( fields, function( i, field ) {
            if(i!==fields.length-1){
                data=data+( field.name+':\"'+field.value + '\",' );
            }else{
                data=data+( field.name+':\"'+field.value + '\"' );
            }

        });
        data=data+'}';
        $('#extInfo').val(JSON.stringify(data));
        operationSave();
    }

    function operationSave(){
        var url='addSave.htm';
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
</html>

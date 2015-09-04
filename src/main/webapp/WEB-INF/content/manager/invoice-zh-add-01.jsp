<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<html>
<head>
    <title>开具中文发票</title>
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
        .maininfo td input{ text-align: center; }
        .sonTab{margin:0; margin-top: 20px;}
        .sonTab tr td{border: 0;}
        .tabTitle{ text-align: center; font-weight: bold; font-size: 23px; padding: 5px;}
        .combo>input[class*="combo-text validatebox-text"]{ border: 0;outline: none;}

    </style>
    <script>
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
                 // getCustomerInfo(value);
              }
             });

            $('#sel_code').combobox({
                onChange:function(value){
                    $('#sel_customer').combobox('setValue',value);
                    $('#customerName').val(value);
                    $('#customerCode').val($('#sel_code').combobox('getText'));
                }
            });


            $('#but_submit').click(function(){
                if(!$("#form1").form('validate')){
                    alert('bbs');
                    return false;
                }
/*                $('#form1')[0].submit(function(){

                })*/

            });
            $('#but_reset').click(function(){
                $('#form1')[0].reset()
            });
        });
    </script>

</head>
<body LEFTMARGIN=0 TOPMARGIN=0 MARGINWIDTH=0 MARGINHEIGHT=0
      scroll=yes>
<form  id="form1" action="" method="post" >
    <div class="tabTitle">
        送货单
    </div>
    <TABLE class="invoiceTab" WIDTH="100%" BORDER="0" ALIGN="CENTER" CELLPADDING="0" CELLSPACING="0" BGCOLOR="#fff">
        <tr>
            <td colspan="2" class="customer">客户:
                <input type="hidden" name="name" id="customerName">
                <input style="border: 0;" class="easyui-combobox" id="sel_customer" name="sel_customer"
                       data-options="valueField:'code',textField:'name'">
            </td>
            <td></td>
            <td colspan="2">客户代码:
                <input type="hidden" name="code" id="customerCode">
                <input class="easyui-combobox" id="sel_code" name="sel_customer"
                       data-options="valueField:'name',textField:'code'">
            </td>
            <td colspan="2">日期:<input class="Wdate" type="text" id="beginOrderDate" style="cursor: pointer" onFocus="WdatePicker()"/></td>
        </tr>
        <tr>
            <td colspan="2">电话:<input id="customer_phone" name="customer.phone"/></td>
            <td></td>
            <td colspan="4">地址:<input id="customer_address" name="customer.address"/></td>
        </tr>
        <tr class="title">
            <th>编号/版号</th>
            <th>品名规格号码</th>
            <th>单位</th>
            <th>单价</th>
            <th>数量</th>
            <th>金额</th>
            <th>摘要</th>
        </tr>
        <tr class="mainInfo">
            <td>
                <input type="text"/>
            </td>
            <td><input type="text"/></td>
            <td>
                <select>
                    <option>个</option>
                    <option>张</option>
                    <option>片</option>
                </select>
            </td>
            <td><input type="text" style="width: 50px;"/></td>
            <td><input type="text"/></td>
            <td><input type="text"/></td>
            <td><input type="text"/></td>
        </tr>

        <tr>
            <td colspan="5">
                共1笔 金额合计(大写)：RMB 壹仟玖佰元整
            </td>
            <td colspan="2">1900.00</td>
        </tr>
        <tr class="last_row">
            <td colspan="7" style="text-align: left;margin-left: 0;border: 0;">
                <table border="0" class="sonTab" cellpadding="0" cellspacing="0">
                    <tbody>
                    <tr>
                        <td>备注:</td>
                        <td>油渍，染剂，化学剂，05度以上高温及其它特殊处理会造成褪色，污染或收缩，不属于产品瑕疵。</td>
                    </tr>
                    <tr>
                        <td></td>
                        <td>我司产品经贵公司验收后，如有任何问题，请3日内联系，一经验收，我司不再承担后期任何连带责任</td>
                    </tr>
                    <tr>
                        <td></td>
                        <td>使用本产品请务必自行测试。处以上状况外，如有疑问请于10天内于本公司联络。谢谢合作!</td>
                    </tr>
                    <tr>
                        <td colspan="2">
                            送货人:<input/>
                            复合:<input/>
                            操作员:<input type="text" readonly="readonly" value="${user.userName}"/>
                            收货单位签章:<input/>
                        </td>
                    </tr>
                    </tbody>
                </table>
            </td>
        </tr>
    </TABLE>
    <div id="button" style="margin-top: 20px;">
        <a href="javascript:void(0)" class="easyui-linkbutton" id="but_submit" style="width: 100px;">提交</a> &nbsp;&nbsp;&nbsp;&nbsp;
        <a href="javascript:void(0)" class="easyui-linkbutton" id="but_reset" style="width: 100px;">重置</a>
    </div>
    </div>
</form>
<script type="text/javascript">

</script>
</body>
</html>

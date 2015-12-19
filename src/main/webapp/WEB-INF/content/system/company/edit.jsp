<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<%@include file="../../common/header.jspf"%>
<link rel="stylesheet" type="text/css" href="<%=contextPath %>/css/newtable.css" />

<style>
    th,td{ line-height: 30px; padding-left: 5px;}
    .customerTab{ margin: 60px auto;}
    .customerTab tr td input{border: 0; border-bottom: 1px solid gray; outline: none;font-weight: bold; color: #0000ff;}
    .firstTabInfo td{ text-align: center}
    .splitRow th{ border: 0;}
    .combo>input[class*="combo-text validatebox-text"]{ border: 0;outline: none;height: auto}
</style>
<script>
    $(function(){
        $('input[type=text][required=true]').validatebox();
    });

    function saveInfo(){
        var url='companyInfoEditSave.htm';
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
                    $.messager.alert('操作提示','修改成功','info',function(){
                        location.reload();
                    });

                }

            }
        });
    }
    function formReset(){
        location.reload();
    }
</script>

</head>
<body LEFTMARGIN=0 TOPMARGIN=0 MARGINWIDTH=0 MARGINHEIGHT=0
      scroll=yes>
<form  id="form1" method="post">
    <TABLE class="customerTab" WIDTH="1100px" BORDER="0" ALIGN="CENTER" CELLPADDING="0" CELLSPACING="0" BGCOLOR="#fff">
        <tr>
            <th>公司名称</th>
            <td colspan="3">
                <input type="hidden" name="companyInfo.id" value="${companyInfo.id}"/>
                <input type="text" required=true name="companyInfo.name" value="${companyInfo.name}"/>
            </td>
        </tr>
        <tr>
            <th>公司地址</th>
            <td colspan="3">
                <input type="text" required=true name="companyInfo.address" value="${companyInfo.address}">
            </td>
        </tr>
        <tr>
            <th>联系人</th>
            <td colspan="3">
                <input type="text" required=true name="companyInfo.contract" value="${companyInfo.contract}">
            </td>
        </tr>
        <tr>
            <th>联系电话</th>
            <td colspan="3">
                <input type="text" required=true name="companyInfo.telephone" value="${companyInfo.telephone}">
            </td>
        </tr>
        <tr>
            <th>传真</th>
            <td colspan="3">
                <input type="text" name="companyInfo.fax" value="${companyInfo.fax}">
            </td>
        </tr>

    </TABLE>
    <div id="button" style="margin-top: 20px;">
        <a href="javascript:void(0)" onclick="saveInfo()" class="easyui-linkbutton" style="width: 100px;">提交</a> &nbsp;&nbsp;&nbsp;&nbsp;
        <a href="javascript:void(0)" class="easyui-linkbutton" onclick="formReset()" style="width: 100px;">重置</a>
        <input id="res" name="res" type="reset" style="display:none;" />
    </div>
</form>
</body>
</html>

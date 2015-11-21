<%@page import="java.util.List"%>
<%@ page language="java"  import="java.util.*"  contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.0 Transitional//EN">
<%
    String contextPath = request.getContextPath().equals("/")?"":request.getContextPath();
%>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
    <title>傲美订单管理系统</title>
    <link href="../css/default.css" rel="stylesheet" type="text/css" />
    <link href="../css/myeasyui.css" rel="stylesheet" type="text/css" />
    <link rel="stylesheet" type="text/css" href="<%=contextPath %>/js/easyui/themes/default/easyui.css" />
    <link rel="stylesheet" type="text/css" href="<%=contextPath %>/js/easyui/themes/icon.css" />
    <script type="text/javascript" src="<%=contextPath %>/js/easyui/jquery.min.js"></script>
    <script type="text/javascript" src="<%=contextPath %>/js/easyui/jquery.easyui.min.js"></script>
    <script type="text/javascript" src='<%=contextPath %>/js/aomei.index.js'> </script>
    <style type="text/css">
        .accordion .accordion-header-selected {
            background: #8DADFF;
        }
    </style>
    <script type="text/javascript">

        var _menus={
            <%
            if(session.getAttribute("menus")!=null){
            %>
            <%=session.getAttribute("menus")%>
            <%
            }
            %>
        };


        //设置登录窗口
        function openPwd() {
            $('#w').window({
                title: '修改密码',
                width: 400,
                modal: true,
                shadow: true,
                closed: true,
                height: 260,
                resizable:false
            });
        }
        //关闭登录窗口
        function closePwd() {
            $('#w').window('close');
        }



        //修改密码
        function serverLogin() {
            var $newpass = $('#txtNewPass');
            var $rePass = $('#txtRePass');

            if ($newpass.val() == '') {
                msgShow('系统提示', '请输入密码！', 'warning');
                return false;
            }
            if ($rePass.val() == '') {
                msgShow('系统提示', '请在一次输入密码！', 'warning');
                return false;
            }

            if ($newpass.val() != $rePass.val()) {
                msgShow('系统提示', '两次密码不一至！请重新输入', 'warning');
                return false;
            }

            $.post('/ajax/editpassword.ashx?newpass=' + $newpass.val(), function(msg) {
                msgShow('系统提示', '恭喜，密码修改成功！<br>您的新密码为：' + msg, 'info');
                $newpass.val('');
                $rePass.val('');
                close();
            })

        }

        $(function() {

            openPwd();

            $('#editpass').click(function() {
                $('#w').window('open');
            });

            $('#btnEp').click(function() {
                serverLogin();
            })

            $('#btnCancel').click(function(){closePwd();})

            $('#loginOut').click(function() {
                $.messager.confirm('系统提示', '您确定要退出本次登录吗?', function(r) {

                    if (r) {
                        location.href = 'login-out.htm';
                    }
                });
            })
        });

        //打开链接
        var index = 0;
        function addTab(tab_name, href){
            href = '<%=contextPath%>' + href;
            if ($('#tabs').tabs('exists',tab_name)){
                //alert("aaaaaa" + href);
                $('#tabs').tabs('select', tab_name);
                refreshTab(href);
            } else {
                //alert("bbbbbbb"+href);
                $('#tabs').tabs('add',{
                    title: tab_name,
                    content:"<iframe id='tab"+index+"' class=\"tab\" style='width:100%;height:100%' scrolling=\"auto\" frameborder=\"0\" src= \""+href+"\"></iframe>",
                    closable:true,
                    tools:[{
                        iconCls:'icon-mini-refresh',
                        handler:function(){
                            refreshTab(href);
                        }
                    }]
                });
            }
        }

        function refreshTab(href){
            var pp = $('#tabs').tabs('getSelected');
            if(pp && pp.find('iframe').length > 0){
                var _refresh_ifram = pp.find('iframe')[0];
                //var refresh_url = _refresh_ifram.src;
                var refresh_url = href;
                //alert(_refresh_ifram.contentWindow.location.href+"----refresh_url====="+refresh_url);
                _refresh_ifram.contentWindow.location.href=refresh_url;
            }
        }
    </script>

</head>
<body class="easyui-layout" style="overflow-y: hidden"  fit="true"   scroll="no">
<noscript>
    <div style=" position:absolute; z-index:100000; height:2046px;top:0px;left:0px; width:100%; background:white; text-align:center;">
        <img src="../images/noscript.gif" alt='抱歉，请开启脚本支持！' />
    </div></noscript>

<div id="loading-mask" style="position:absolute;top:0px; left:0px; width:100%; height:100%; background:#D2E0F2; z-index:20000">
    <div id="pageloading" style="position:absolute; top:50%; left:50%; margin:-120px 0px 0px -120px; text-align:center;  border:2px solid #8DB2E3; width:200px; height:40px;  font-size:14px;padding:10px; font-weight:bold; background:#fff; color:#15428B;">
        <img src="../images/loading.gif" align="absmiddle" /> 正在加载中,请稍候...</div>
</div>

<div region="north" border="false" style="overflow: hidden; height: 40px;
        background: url(../images/layout-browser-hd-bg.gif) #7f99be repeat-x center 50%;
        line-height: 40px;color: #fff; font-family: Verdana,'微软雅黑,黑体'">
        <span style="float:right; padding-right:20px;" class="head">欢迎 ${user.username}
            <%--<a href="#" id="editpass">修改密码</a>--%>
            <a href="#" id="loginOut">安全退出</a></span>
        <span style="padding-left:10px; font-size: 16px; ">
            <img src="../images/blocks.gif" width="20" height="20" align="absmiddle" />
            傲美实业订单管理系统
        </span>
</div>

<div region="west" split="true"  title="导航菜单" style="width:180px;" id="west">
    <div id="nav">
        <!--  导航内容 -->

    </div>

</div>
<div id="mainPanle" region="center" style="background: #eee; overflow-y:hidden">
    <div id="tabs" class="easyui-tabs"  fit="true" border="false" >
        <div title="欢迎使用" style="padding:20px;overflow:hidden; color:blue;text-align: center">
            <div class="welcome" style="font-size: 25px; font-weight: bold; margin:120px auto;">
                <%
                    if(session.getAttribute("menus")==null){
                %>
                您还没有相关操作权限，请联系管理员
                <%
                }else{
                %>
                欢迎使用傲美订单管理系统
                <%
                    }
                %>

            </div>
        </div>
    </div>
</div>


<!--修改密码窗口-->
<div id="w" class="easyui-window" title="修改密码" collapsible="false" closed="true" minimizable="false"
     maximizable="false" icon="icon-save"  style="width: 400px;  padding: 5px;
        background: #fafafa;">
    <div class="easyui-layout" fit="true">
        <div region="center" border="false" style="padding: 10px; background: #fff; border: 1px solid #ccc;height: 150px">
            <table cellpadding=3>
                <tr>
                    <td>原始密码：</td>
                    <td><input id="txtOldPass" type="Password" class="easyui-textbox" /></td>
                </tr>
                <tr>
                    <td>新密码：</td>
                    <td><input id="txtNewPass" type="Password" class="easyui-validatebox easyui-textbox" data-options="required:true,validType:'length[6,10]'" /></td>
                </tr>
                <tr>
                    <td>确认密码：</td>
                    <td><input id="txtRePass" type="Password" class="easyui-textbox" /></td>
                </tr>
            </table>
        </div>
        <div region="south" border="false" style="text-align: right; height: 30px; line-height: 30px;">
            <a id="btnEp" class="easyui-linkbutton" icon="icon-ok" href="javascript:void(0)" >
                确定</a> <a id="btnCancel" class="easyui-linkbutton" icon="icon-cancel" href="javascript:void(0)">取消</a>
        </div>
    </div>
</div>
<div id="footer" region="south">
    <div id="footer-inner">Copyright©2014-2015 上海市傲美服饰辅料有限公司 版权所有</div>
</div>
<div id="mm" class="easyui-menu" style="width:150px;">
    <div id="tabupdate">刷新</div>
    <div class="menu-sep"></div>
    <div id="close">关闭</div>
    <div id="closeall">全部关闭</div>
    <div id="closeother">除此之外全部关闭</div>
    <div class="menu-sep"></div>
    <div id="closeright">当前页右侧全部关闭</div>
    <div id="closeleft">当前页左侧全部关闭</div>
    <div class="menu-sep"></div>
    <div id="exit">退出</div>
</div>

<script type="text/javascript">
    <%
        if (session.isNew()) { //判断是否为新用户
    %>
    jQuery.messager.show({
        title:'温馨提示:',
        msg:'你好,欢迎使用傲美订单系统!',
        timeout:5000,
        showType:'slide'
    });
    <%
        }
    %>
</script>
</body>
</html>
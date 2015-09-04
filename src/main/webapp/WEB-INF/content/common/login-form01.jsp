<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<link href="../css/main.css" rel="stylesheet" type="text/css">
<link href="../css/login.css" rel="stylesheet" type="text/css">

<script type="text/javascript">


function reloadcode(){
	document.getElementById('vcsImg').src = '../ValidateCodeServlet.html?' + Math.random();
	}
function val(){
	var username=document.getElementById("userName").value;
	//var pwd=document.getElementById("pwd").value;
	if(username==""||username.length==0){
		document.getElementById("userName").value=" 用户名";
	}
	//if(pwd==""||pwd.length==0){
//document.getElementById("pwd").value=" 密  码";
	//}
}


function changtxt()
{
 objtxt=document.getElementById('pwdtxt');
 objpwd=document.getElementById('pwd');
 objtxt.style.display='none';
 objpwd.style.display='';
 objpwd.focus();
}
 function changpwd()
{
 objtxt=document.getElementById('pwdtxt');
 objpwd=document.getElementById('pwd');
 if(objpwd.value=='')
 {
  objtxt.style.display='';
  objpwd.style.display='none';
  objtxt.value=' 密  码';
 }
}
</script>

<body LEFTMARGIN=0 TOPMARGIN=0 MARGINWIDTH=0 MARGINHEIGHT=0 scroll=no onload="val()">
<div id="box3">
<div id="loginarea">
  <div id="loginarea1"></div>
  <div id="loginarea2">
    <s:form action="login" name="form" id="form"  method="post">
      <table width="1002" border="0" cellspacing="0" cellpadding="0">
        <tr>
          <td width="643" height="255">&nbsp;</td>
          <td width="359" valign="top"><table border="0" cellspacing="0" cellpadding="0">
            <tr>
              <td width="15" background="../images/login_taba1.gif"></td>
              <td background="../images/login_taba2.gif"></td>
              <td width="15" height="15" background="../images/login_taba3.gif"></td>
            </tr>
            <tr>
              <td width="15" background="../images/login_tabb1.gif"></td>
              <td width="275" height="185" valign="top" bgcolor="#bed7e7"><table width="268" border="0" align="center" cellpadding="0" cellspacing="0">
                <tr>
                  <td width="217" height="40" id="loginuser"><table width="268" border="0" cellspacing="0" cellpadding="0">
                      <tr>
                        <td width="48" height="38">&nbsp;</td>
                        <td width="220">
                        <s:textfield name="user.userName" id="userName" cssClass="form-login1" onclick="if(this.value==' 用户名'){this.value='';}" onFocus="if(this.value==' 用户名'){this.value='';}" onblur="val()"></s:textfield>
                        </td>
                      </tr>
                  </table></td>
                </tr>
                <tr>
                  <td height="8"></td>
                </tr>
                <tr>
                  <td height="38" id="loginpass"><table width="268" border="0" cellspacing="0" cellpadding="0">
                      <tr>
                        <td width="48" height="38">&nbsp;</td>
                        <td width="220"> 
                            <%-- <s:password  id="pwd" name="user.password"  cssClass="form-login1"  onclick="if(this.value==' 密  码'){this.value='';}" onFocus="if(this.value==' 密  码'){this.value='';}" onblur="val()"/>  --%>
                             <s:password type="password" id="pwd" name="user.password"  cssClass="form-login1"  onmouseout="changpwd()" style="display:none;" />
                             <input  id="pwdtxt" type="text" value=" 密  码" class="form-login1" onclick="changtxt()" onfocus="changtxt()"/>  
                        </td>
                      </tr>
                  </table></td>
                </tr>
                <tr>
                  <td align="right"><span style="color: red; font-size: 14px; font-weight: bold;"><s:actionerror/></span></td>
                </tr>
                <tr>
                  <td height="38"><table width="268" border="0" cellspacing="0" cellpadding="0">
                      <tr>
                        <td width="86"><input name="validateCode" type="text" class="form-login2" onclick="if(this.value==' 验证码'){this.value='';}" onFocus='if(this.value==" 验证码"){this.value="";}' value=" 验证码" size="12" onblur='if(this.value==""){this.value=" 验证码";}'/></td>
                        <td width="86" align="center"><IMG id="vcsImg" SRC="../ValidateCodeServlet.html" WIDTH="75" HEIGHT="25" hspace="5" style="position:relative; top:0px; +top:0px;"></td>
                        <td width="96"><label><a href="javascript:reloadcode()">看不清换一张</a></label></td>
                      </tr>
                  </table></td>
                </tr>
                <tr>
                  <td height="50"><input type="submit" style="background:url(../images/btn_login1.gif);height: 42;width:268;border: 0" value=""></td>
                </tr>
              </table></td>
              <td width="15" background="../images/login_tabb3.gif"></td>
            </tr>
            <tr>
              <td width="15" background="../images/login_tabc1.gif"></td>
              <td background="../images/login_tabc2.gif"></td>
              <td width="15" height="15" background="../images/login_tabc3.gif"></td>
            </tr>
          </table></td>
        </tr>
      </table>
    </s:form>
    </div>
</div>
</div>
</body>
</html>



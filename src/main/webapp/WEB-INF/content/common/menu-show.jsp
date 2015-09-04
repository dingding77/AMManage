	<%@ taglib prefix="s" uri="/struts-tags" %>
	<s:iterator value="listMenu" var="menu">
		<a href="#" class="easyui-linkbutton" iconCls="<s:property value='#menu.iconImg' />" plain="true" onclick="<s:property value='#menu.methodName' />"><s:property	value="#menu.name" /> </a>
	</s:iterator>

<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<%@include file="../common/header.jspf"%>
<script>
    function exportWord(){
        window.location.href='exportWord.htm';
    }

</script>
<body>
    <input type="button" value="导出" onclick="exportWord()"/>
</body>
</html>

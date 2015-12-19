<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<%@include file="../common/header.jspf"%>
<link rel="stylesheet" type="text/css" href="<%=contextPath %>/css/newtable.css" />
<link href="../js/jquery.imgareaselect-0.9.10/css/imgareaselect-default.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="../js/jquery.imgareaselect-0.9.10/scripts/jquery.imgareaselect.min.js"></script>
<script type="text/javascript" src="../js/jquery.imgareaselect-0.9.10/scripts/jquery.imgareaselect.pack.js"></script>
<script type="text/javascript" src="../js/upload.js"></script>
<html>

<body style="padding: 0;margin: 0; text-align: center;">
    <div style="margin: 5px 0 0 20px;">
        <input type="hidden" name="x1" value="0" />
        <input type="hidden" name="y1" value="0" />
        <input type="hidden" name="x2" value="479" />
        <input type="hidden" name="y2" value="264" />
        <div id="facediv" style="display:none;z-index:100;">
            <img id="face" src="${requestScope.imgPath}" />
            <input type="hidden" id="inputIdVal" value="${requestScope.inputId}">
        </div>
        <script type="text/javascript">
                $("#facediv").css({"display":"block"});

                $('<button id="btnSubmit">提交</button>')
                        .click(function (){
                            cutImage($('#face').attr('src'),$('#inputIdVal').val());
                            window.parent.$('#cutimg-window').window('close');
                        }).insertAfter($('#facediv'));
                $('<button id="btnCancel" style="margin-left: 15px;">取消</button>')
                        .click(function (){
                            window.parent.$('#cutimg-window').window('close');
                        }).insertAfter($('#facediv'));
                $('#face').imgAreaSelect({
                    maxWidth: 479, maxHeight: 264,
                    minWidth: 479, minHeight:264,
                    x1:0,y1:0,x2:200,y2:100,
                    onSelectChange: function (img, selection) {
                        var scaleX = 100 / (selection.width || 1);
                        var scaleY = 100 / (selection.height || 1);

                        $('#face + div > img').css({
                            width: Math.round(scaleX * 400) + 'px',
                            height: Math.round(scaleY * 300) + 'px',
                            marginLeft: '-' + Math.round(scaleX * selection.x1) + 'px',
                            marginTop: '-' + Math.round(scaleY * selection.y1) + 'px'
                        });
                    },
                    onSelectEnd: function (img, selection) {
                        $('input[name="x1"]').val(selection.x1);
                        $('input[name="y1"]').val(selection.y1);
                        $('input[name="x2"]').val(selection.x2);
                        $('input[name="y2"]').val(selection.y2);
                    }
                });
        </script>
    </div>
</body>
</html>

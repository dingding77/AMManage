/**
 * Created by Administrator on 2015/10/9.
 */
var inputIdVal='';
function uploadImage(inputId,fileId,isCutImg) {
    inputIdVal=inputId;
    var allowExtention = ".jpg,.bmp,.gif,.png";
    var fileVal=$('#'+fileId).val();
    //获取后缀名称
    var extention = fileVal.substring(fileVal.lastIndexOf(".") + 1).toLowerCase();
    if (allowExtention.indexOf(extention) > -1) {
        $.ajaxFileUpload( {
            url : '../photo.do',// 需要链接到服务器地址
            fileElementId : fileId,// 文件选择框的id属性
            dataType : 'json',// 服务器返回的格式，可以是json
            data : {"operation":1},
            success : function(data) {
                if (data['result'] == 1) {
                    $("#"+inputIdVal+'_show').attr('src',data["path"]);
                    $("#"+inputIdVal).val(data["path"]);
                    if(isCutImg){
                        $('#cutimg-iframe').attr('src', '../common/cutImg.htm?inputId=' + inputId + "&imgPath=" + data['path']);
                        $('#cutimg-window').window('open');
                    }
                }
            },
            error : function(data) {
            }
        });
    }else{
        $.messager.alert("操作提示", "仅支持" + allowExtention + "为后缀名的文件!","info");
    }

}

function cutImage(path,inputIdVal) {
    $.ajax( {
        type : "POST",
        url:"../photo.do",
        dateType:"json",
        data:{"operation":2,"x1":$('input[name="x1"]').val(),
            "x2":$('input[name="x2"]').val(),
            "y1":$('input[name="y1"]').val(),
            "y2":$('input[name="y2"]').val(),
            "path":path},
        success : function(data) {
            $("#"+inputIdVal+'_show',window.parent.document).attr('src',data["path"]);
            $("#"+inputIdVal,window.parent.document).attr('src',data["path"]);
        },
        error:function(data) {

        }
    });
}
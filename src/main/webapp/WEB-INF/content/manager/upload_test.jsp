<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <title></title>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
    <link href="../js/jquery.imgareaselect-0.9.10/css/imgareaselect-default.css" rel="stylesheet" type="text/css" />
    <script type="text/javascript" src="../js/jquery-1.7.2.min.js"></script>
    <script type="text/javascript" src="../js/ajaxfileupload.js"></script>
    <script type="text/javascript" src="../js/jquery.imgareaselect-0.9.10/scripts/jquery.imgareaselect.min.js"></script>
    <script type="text/javascript" src="../js/jquery.imgareaselect-0.9.10/scripts/jquery.imgareaselect.pack.js"></script>
    <script type="text/javascript" src="../js/upload.js"></script>
</head>
<body>
<input type="hidden" name="x1" value="0" />
<input type="hidden" name="y1" value="0" />
<input type="hidden" name="x2" value="100" />
<input type="hidden" name="y2" value="100" />
<input id="fileToUpload" name="fileToUpload" type="file" onchange="uploadImage();"/>
<div id="facediv" style="display:none;z-index:100;">
    <img id="face" />
</div>
</body>
</html>
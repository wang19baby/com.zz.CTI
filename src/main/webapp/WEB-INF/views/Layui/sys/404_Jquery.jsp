<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/include/taglib.jsp"%>
<!DOCTYPE html>
<html>
<head>
	<meta charset="utf-8">
	<title>404--layui后台管理模板 2.0</title>
	<meta name="renderer" content="webkit">
	<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
	<meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1">
	<meta name="apple-mobile-web-app-status-bar-style" content="black">
	<meta name="apple-mobile-web-app-capable" content="yes">
	<meta name="format-detection" content="telephone=no">
    
    <link rel="stylesheet" href="${ctxStatic}/Layui/css/404/404.css">
    <script src="${ctxStatic}/Layui/js/Record/jquery-1.7.2.min.js"></script>
    <script src="${ctxStatic}/Layui/js/404/scriptalizer.js" type="text/javascript"></script>
    
    <script type="text/javascript">
        $(function(){
            $('#parallax').jparallax({});
        });
    </script>
</head>
<body>
    <div id="content" class="narrowcolumn">

<div id="parallax">
    <div class="error1">
        <img src="${ctxStatic}/Layui/images/404/wand.jpg" alt="Mauer" />
    </div>
    <div class="error2">
        <img src="${ctxStatic}/Layui/images/404/licht.png" alt="Licht" />
    </div>
    <div class="error3">
        <img src="${ctxStatic}/Layui/images/404/halo1.png" alt="Halo1" />
    </div>
    <div class="error4">
        <img src="${ctxStatic}/Layui/images/404/halo2.png" alt="Halo2" />
    </div>
    <div class="error5">
        <img src="${ctxStatic}/Layui/images/404/batman-404.png" alt="Batcave 404" />
    </div>
</div>
<div class="footer-banner" style="width:728px; margin:0 auto"></div>
</div>
 
</body>
</html>
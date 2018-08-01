<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/include/taglib.jsp"%>
<!DOCTYPE html>
<html>
<head>
	<meta charset="utf-8">
	<title>语音呼叫弹屏</title>
	<meta name="renderer" content="webkit">
	<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
	<meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1">
	<meta name="apple-mobile-web-app-status-bar-style" content="black">
	<meta name="apple-mobile-web-app-capable" content="yes">
	<meta name="format-detection" content="telephone=no">
	<link rel="stylesheet" href="${ctxStatic}/Layui/layui/css/layui.css" media="all" />
	<link rel="stylesheet" href="${ctxStatic}/Layui/css/public.css" media="all" />
	
	<style type="text/css">
	i.layui-anim{display:inline-block}
	.callLayer{
		padding: 50px;
		line-height: 22px;
		background-color: #393D49;
		color: #fff;
		font-weight: 300;
		text-align: center;
		font-size: 18px;
	}
	.layui-icon{
		font-size: 48px !important;
	}
	</style>
</head>
<body>
<form class="layui-form layui-row">
	<div class="layui-col-md6 layui-col-xs12">
	<div class="callLayer">
		<div class="layui-form-item">
			<span id="callEvent">未知操作</span>&nbsp<span id="callNum">未知号码</span>
		</div>
		<div class="layui-form-item">
			<span id="callIcon"><i class="layui-icon layui-anim layui-anim-rotate layui-anim-loop">&#xe63d;</i></span>
		</div>
	</div>
	</div>
</form>
	<script type="text/javascript" src="${ctxStatic}/Layui/layui/layui.js"></script>
	<script type="text/javascript" src="${ctxStatic}/Layui/js/voiceCall/callOutLayer.js"></script>
</body>
</html>
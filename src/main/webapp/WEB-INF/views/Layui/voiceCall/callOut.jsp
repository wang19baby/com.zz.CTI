<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/include/taglib.jsp"%>
<!DOCTYPE html>
<html>
<head>
	<meta charset="utf-8">
	<title>语音外呼</title>
	<meta name="renderer" content="webkit">
	<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
	<meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1">
	<meta name="apple-mobile-web-app-status-bar-style" content="black">
	<meta name="apple-mobile-web-app-capable" content="yes">
	<meta name="format-detection" content="telephone=no">
	<link rel="stylesheet" href="${ctxStatic}/Layui/layui/css/layui.css" media="all" />
	<link rel="stylesheet" href="${ctxStatic}/Layui/css/public.css" media="all" />
</head>
<body class="childrenBody">
<form class="layui-form">
	<blockquote class="layui-elem-quote quoteBox">
		<div class="layui-inline">
			<div class="layui-input-inline">
				<input type="text" class="layui-input callVal" placeholder="请输入呼叫号码"/>
			</div>
			<button class="layui-btn callOut_btn"><i class="layui-icon">&#xe63b;</i>呼叫</button>
			<button class="layui-btn recordPlay"><i class="layui-icon">&#xe63b;</i>放音</button>
		</div>
	</blockquote>
</form>
<script type="text/javascript" src="${ctxStatic}/Layui/layui/layui.js"></script>
<script type="text/javascript" src="${ctxStatic}/Layui/js/voiceCall/callOut.js"></script>
</body>
</html>
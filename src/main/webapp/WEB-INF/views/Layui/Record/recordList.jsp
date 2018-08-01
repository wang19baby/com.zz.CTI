<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/include/taglib.jsp"%>
<!DOCTYPE html>
<html>
<head>
	<meta charset="utf-8">
	<title>录音列表</title>
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
		<div class="demoTable">
		    <div class="layui-inline">
		      <label class="layui-form-label">日期：</label>
		      <div class="layui-input-inline">
		        <input id="startDate" name="startDate" type="text" class="layui-input" placeholder="起始日期">
		      </div>
		      --
		      <div class="layui-input-inline">
		        <input id="endDate" name="endDate" type="text" class="layui-input" placeholder="结束日期">
		      </div>
		    </div>
    
			<button class="layui-btn" lay-submit="" lay-filter="search">搜索</button>
		</div>
	</blockquote>
	<table id="recordList" lay-filter="recordList"></table>

	<!--操作-->
	<script type="text/html" id="recordListBar">
		<a class="layui-btn layui-btn-xs" lay-event="play">回放</a>
	</script>
</form>
	<script type="text/javascript" src="${ctxStatic}/Layui/layui/layui.js"></script>
	<script type="text/javascript" src="${ctxStatic}/Layui/js/Record/recordList.js"></script>
</body>
</html>

<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/include/taglib.jsp"%>
<!DOCTYPE html>
<html>
<head>
	<meta charset="utf-8">
	<title>在线转接坐席</title>
	<meta name="renderer" content="webkit">
	<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
	<meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1">
	<meta name="apple-mobile-web-app-status-bar-style" content="black">
	<meta name="apple-mobile-web-app-capable" content="yes">
	<meta name="format-detection" content="telephone=no">
	<link rel="stylesheet" href="${ctxStatic}/Layui/layui/css/layui.css" media="all" />
	<link rel="stylesheet" href="${ctxStatic}/Layui/css/public.css" media="all" />
	
	<style type="text/css">
	body{overflow-y: scroll;}
	</style>
</head>
<body class="childrenBody">
<form class="layui-form">
	<blockquote class="layui-elem-quote quoteBox">
		<div class="layui-input-inline">
			<select name="dept" lay-filter="selectDepts">
				<option value = "" selected>全部</option>
			 	<c:forEach items="${dict:getDataDictionary('depts')}" var = "dept">
					<option value = "${dept.deptId}">${dept.deptName}</option>
				</c:forEach>
			</select>
		</div>
	</blockquote>
	<table id="agentTransferByReceptionist" lay-filter="agentTransferByReceptionist"></table>
	
	<!--操作-->
	<script type="text/html" id="agentTransferByReceptionistBar">
		<a class="layui-btn layui-btn-xs layui-btn-primary" lay-event="transfer">转接</a>
		<a class="layui-btn layui-btn-xs" lay-event="cancle">取消</a>
	</script>
</form>
	<script type="text/javascript" src="${ctxStatic}/Layui/layui/layui.js"></script>
	<script type="text/javascript" src="${ctxStatic}/Layui/js/agentGroup/agentTransferByReceptionist.js"></script>
</body>
</html>
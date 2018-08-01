<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/include/taglib.jsp"%>
<!DOCTYPE html>
<html>
<head>
	<meta charset="utf-8">
	<title>增加坐席</title>
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
		      <label class="layui-form-label">员工号：</label>
		      <div class="layui-input-inline">
		        <input id="userNo" name="userNo" type="text" class="layui-input" placeholder="请输入搜索员工号">
		      </div>
		    </div>
		    
		    <div class="layui-inline">
		      <label class="layui-form-label">坐席工号：</label>
		      <div class="layui-input-inline">
		        <input id="workNo" name="workNo" type="text" class="layui-input" placeholder="请输入搜索坐席工号">
		      </div>
		    </div>
		    
		    <div class="layui-inline">
		      <label class="layui-form-label">姓名：</label>
		      <div class="layui-input-inline">
		        <input id="workName" name="workName" type="text" class="layui-input" placeholder="请输入搜索姓名">
		      </div>
		    </div>
		    
		    <div class="layui-inline">
		    	<label class="layui-form-label">部门：</label>
			    <div class="layui-input-inline" style="width: 57%;">
					<select name="dept">
						<option value = "" selected>全部</option>
					 	<c:forEach items="${dict:getDataDictionary('depts')}" var = "dept">
							<option value = "${dept.deptId}">${dept.deptName}</option>
						</c:forEach>
					</select>
				</div>
			</div>
		</div>
	</blockquote>
	<blockquote class="layui-elem-quote quoteBox">
		<div class="layui-inline">
	    	<button class="layui-btn" lay-submit="" lay-filter="search"><i class="layui-icon">&#xe615;</i></button>
	    </div>
	    
	    <div class="layui-inline">
			<a class="layui-btn layui-btn-danger layui-btn-normal delAll_btn"><i class="layui-icon">&#xe640;</i>批量删除</a>
		</div>
	</blockquote>
	
	<table id="agentList" lay-filter="agentList"></table>

	<!--操作-->
<!-- 	<a class="layui-btn layui-btn-xs" lay-event="look">查看</a> -->
	<script type="text/html" id="agentListBar">
		<a class="layui-btn layui-btn-xs layui-btn-primary" lay-event="edit">编辑</a>
		<a class="layui-btn layui-btn-xs layui-btn-danger" lay-event="delete">删除</a>
	</script>
</form>
	<script type="text/javascript" src="${ctxStatic}/Layui/layui/layui.js"></script>
	<script type="text/javascript" src="${ctxStatic}/Layui/js/agentMananger/agentList.js"></script>
</body>
</html>
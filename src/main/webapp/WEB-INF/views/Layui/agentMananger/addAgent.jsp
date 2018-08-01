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
	<div class="layui-form-item layui-row layui-col-xs12">
		<label class="layui-form-label">员工号</label>
		<div class="layui-input-block">
			<input type="text" name="userNo" class="layui-input" lay-verify="required" placeholder="请输入员工号">
		</div>
	</div>
	
	<div class="layui-form-item layui-row layui-col-xs12">
		<label class="layui-form-label">坐席名称</label>
		<div class="layui-input-block">
			<input type="text" name="workName" class="layui-input" lay-verify="required" placeholder="请输入坐席名称">
		</div>
	</div>

	<div class="layui-form-item layui-row layui-col-xs12">
		<label class="layui-form-label">坐席号</label>
		<div class="layui-input-block">
			<input type="text" name="workNo" class="layui-input" placeholder="请输入坐席号">
		</div>
	</div>
	
	<div class="layui-form-item layui-row layui-col-xs12">
		<label class="layui-form-label">坐席座机号</label>
		<div class="layui-input-block">
			<input type="text" name="phoneNumber" class="layui-input" lay-verify="required" placeholder="请输入坐席座机号">
		</div>
	</div>
	
	<div class="layui-form-item">
		<label class="layui-form-label">坐席部门</label>
		<div class="layui-input-block">
			<select name="deptId" class="deptId">
				 <c:forEach items="${dict:getDataDictionary('depts')}" var = "dept">
				 	<option value = "${dept.deptId}">${dept.deptName}</option>
				</c:forEach>
			</select>
		</div>
	</div>
	
	<div class="layui-form-item">
		<label class="layui-form-label">坐席角色</label>
		<div class="layui-input-block">
			<select name="roleId" class="roleId">
				 <c:forEach items="${dict:getDataDictionary('agentRoles')}" var = "agentRole">
				 	<option value = "${agentRole.roleId}">${agentRole.roleName}</option>
				</c:forEach>
			</select>
		</div>
	</div>
	
	<div class="layui-form-item">
		<label class="layui-form-label">坐席权限</label>
		<div class="layui-input-block">
			<c:forEach items="${dict:getDataDictionary('agentPermissions')}" var = "agentPermissions">
				<input type="checkbox" name="agentPermissions" value="${agentPermissions.permissionId}" 
				title="${agentPermissions.permissionName}">
			</c:forEach>
		</div>
	</div>
	
	<div class="layui-form-item layui-row layui-col-xs12">
		<label class="layui-form-label">坐席简介</label>
		<div class="layui-input-block">
			<textarea name="agentProfile" placeholder="请输入坐席简介" class="layui-textarea"></textarea>
		</div>
	</div>
	<div class="layui-form-item layui-row layui-col-xs12">
		<div class="layui-input-block">
			<button class="layui-btn layui-btn-sm" lay-submit="" lay-filter="addAgent">添加</button>
			<button class="layui-btn layui-btn-sm layui-btn-primary" type="reset">重置</button>
		</div>
	</div>
</form>
	<script type="text/javascript" src="${ctxStatic}/Layui/layui/layui.js"></script>
	<script type="text/javascript" src="${ctxStatic}/Layui/js/agentMananger/addAgent.js"></script>
</body>
</html>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/include/taglib.jsp"%>
<!DOCTYPE html>
<html>
<head>
	<meta charset="utf-8">
	<title>编辑用户</title>
	<meta name="renderer" content="webkit">
	<meta http-equiv="pragma" content="no-cache">  
    <meta http-equiv="cache-control" content="no-cache">  
    <meta http-equiv="expires" content="0">   
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
			<input name="userNo" type="text" class="layui-input layui-disabled" lay-verify="required" placeholder="请输入员工号" value="${agentUser.userNo}" disabled>
		</div>
	</div>
	
	<div class="layui-form-item layui-row layui-col-xs12">
		<label class="layui-form-label">坐席号</label>
		<div class="layui-input-block">
			<input name="workNo" type="text" class="layui-input" placeholder="请输入坐席号" value="${agentUser.workNo}">
		</div>
	</div>
	
	<div class="layui-form-item layui-row layui-col-xs12">
		<label class="layui-form-label">姓名</label>
		<div class="layui-input-block">
			<input name="workName" type="text" class="layui-input" placeholder="请输入姓名" value="${agentUser.workName}">
		</div>
	</div>
	
	<div class="layui-form-item layui-row layui-col-xs12">
		<label class="layui-form-label">座机号</label>
		<div class="layui-input-block">
			<input name="phoneNumber" type="text" class="layui-input" placeholder="请输入座机号" value="${agentUser.phoneNumber}">
		</div>
	</div>
	
	<div class="layui-form-item">
		<label class="layui-form-label">部门</label>
		<div class="layui-input-block">
			<select name="dept">
				 <c:forEach items="${dict:getDataDictionary('depts')}" var = "dept">
				 	<c:choose>
					   	<c:when test="${dept.deptId == agentUser.dept.deptId}">
					   		<option value = "${dept.deptId}" selected>${dept.deptName}</option>
					   	</c:when>
					   	<c:otherwise> 
					   		<option value = "${dept.deptId}">${dept.deptName}</option>
					   	</c:otherwise>
					</c:choose>
				</c:forEach>
			</select>
		</div>
	</div>
	
	<div class="layui-form-item">
		<label class="layui-form-label">用户角色</label>
		<div class="layui-input-block">
			<select name="agentRole">
				 <c:forEach items="${dict:getDataDictionary('agentRoles')}" var = "agentRole">
				 	<c:choose>
					   	<c:when test="${agentRole.roleId == agentUser.agentRole.roleId}">
					   		<option value = "${agentRole.roleId}" selected>${agentRole.roleName}</option>
					   	</c:when>
					   	<c:otherwise> 
					   		<option value = "${agentRole.roleId}">${agentRole.roleName}</option>
					   	</c:otherwise>
					</c:choose>
				</c:forEach>
			</select>
		</div>
	</div>
	
	<div class="layui-form-item">
    	<label class="layui-form-label">用户权限</label>
    	<div class="layui-input-block">
			<c:forEach items="${dict:getDataDictionary('agentPermissions')}" var = "agentPermissions">
				<c:choose>
					<c:when test="${fn:contains(agentUser.agentPermissions,agentPermissions)}">
						<input type="checkbox" name="agentPermissions" value="${agentPermissions.permissionId}" title="${agentPermissions.permissionName}" checked>
					</c:when>
					<c:otherwise>
						<input type="checkbox" name="agentPermissions" value="${agentPermissions.permissionId}" title="${agentPermissions.permissionName}">
					</c:otherwise>
				</c:choose>
			</c:forEach>
    	</div>
  	</div>
	
	<div class="layui-form-item layui-row layui-col-xs12">
		<label class="layui-form-label">用户简介</label>
		<div class="layui-input-block">
			<textarea name="agentProfile" placeholder="请输入用户简介" class="layui-textarea">${agentUser.agentProfile}</textarea>
		</div>
	</div>
	
	<div class="layui-form-item layui-row layui-col-xs12">
		<div class="layui-input-block">
			<button class="layui-btn layui-btn-sm" lay-submit="" lay-filter="editAgent">修改提交</button>
		</div>
	</div>
</form>
	<script type="text/javascript" src="${ctxStatic}/Layui/layui/layui.js"></script>
	<script type="text/javascript" src="${ctxStatic}/Layui/js/agentMananger/editUser.js"></script>
</body>
</html>
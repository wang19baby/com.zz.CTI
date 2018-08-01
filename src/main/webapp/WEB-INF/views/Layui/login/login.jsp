<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/include/taglib.jsp"%>
<!DOCTYPE html>
<html class="loginHtml">
<head>
	<meta charset="utf-8">
	<title>呼叫中心</title>
	<meta name="renderer" content="webkit">
	<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
	<meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1">
	<meta name="apple-mobile-web-app-status-bar-style" content="black">
	<meta name="apple-mobile-web-app-capable" content="yes">
	<meta name="format-detection" content="telephone=no">
	
	<link rel="icon" href="${ctxStatic}/Layui/favicon.ico">
	<link rel="stylesheet" href="${ctxStatic}/Layui/layui/css/layui.css" media="all" />
	<link rel="stylesheet" href="${ctxStatic}/Layui/css/public.css" media="all" />
	<!-- 粒子效果 -->
	<link rel="stylesheet" media="screen" href="${ctxStatic}/Layui/css/particlesStyle.css">
</head>
<body class="loginBody">
	<!-- 粒子效果 -->
	<div id="particles-js"></div>
	
	<form class="layui-form">
		<div class="login_face"><img src="${ctxStatic}/Layui/images/face.jpg" class="userAvatar"></div>
		
		<div class="layui-form-item input-item">
			<label for="userNo">员工号</label>
			<input type="text" placeholder="请填写员工号" id="userNo" name="userNo" class="layui-input" lay-verify="required">
		</div>
		<div class="layui-form-item input-item">
			<label for="phonenumber">座机号</label>
			<input type="text" placeholder="请填写座机号" id="phonenumber" name="phonenumber" class="layui-input" lay-verify="required">
		</div>
		<div class="layui-form-item">
			<button class="layui-btn layui-block" lay-filter="login" lay-submit>登录</button>
		</div>
		<div class="layui-form-item layui-row">
			<a href="javascript:;" class="seraph icon-qq layui-col-xs4 layui-col-sm4 layui-col-md4 layui-col-lg4"></a>
			<a href="javascript:;" class="seraph icon-wechat layui-col-xs4 layui-col-sm4 layui-col-md4 layui-col-lg4"></a>
			<a href="javascript:;" class="seraph icon-sina layui-col-xs4 layui-col-sm4 layui-col-md4 layui-col-lg4"></a>
		</div>
	</form>
	
	<script type="text/javascript" src="${ctxStatic}/Layui/layui/layui.js"></script>
	<script type="text/javascript" src="${ctxStatic}/Layui/js/login/login.js"></script>
	<script type="text/javascript" src="${ctxStatic}/Layui/js/cache.js"></script>
	<!-- 粒子效果 -->
	<script src="${ctxStatic}/Layui/js/Particles/particles.js"></script>
	<script src="${ctxStatic}/Layui/js/Particles/starryBackground.js"></script>
</body>
</html>
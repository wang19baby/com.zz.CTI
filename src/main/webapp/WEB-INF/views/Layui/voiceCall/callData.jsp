<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/include/taglib.jsp"%>
<!DOCTYPE html>
<html>
<head>
	<meta charset="utf-8">
	<title>来电用户信息</title>
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
	<div>
		<hr class="layui-bg-green">
		--- 来电呼叫信息
		<hr class="layui-bg-green">
	</div>
	
	<table class="layui-table">
		<thead>
			<tr>
				<th>主叫号码</th>
				<th>被叫号码</th>
				<th>呼叫描述</th>
				<th>原始被叫</th>
			</tr>
		</thead>
		<tbody>
			<tr>
				<td class="caller"></td>
				<td class="called"></td>
				<td class="callskill"></td>
				<td class="orgicallednum"></td>
			</tr>
		</tbody>
	</table>
	
	<div>
		<hr class="layui-bg-green">
		--- 客户列表
		<hr class="layui-bg-green">
	</div>
	
	<!--CTI平台状态-->
	<script type="text/html" id="CTIStatus">
		{{#  if(d.status == 0){ }}
		<span class="layui-black">签出状态</span>
		{{#  } else if(d.status == 1){ }}
		<span style="color: green" class="layui-green">空闲状态</span>
		{{#  } else if(d.status == 2){ }}
		<span style="color: orange" class="layui-orange">预占用状态</span>
		{{#  } else if(d.status == 3){ }}
		<span style="color: orange" class="layui-orange">占用状态</span>
		{{#  } else if(d.status == 4){ }}
		<span class="layui-blue">应答状态</span>
		{{#  } else if(d.status == 5){ }}
		<span class="layui-blue">通话状态</span>
		{{#  } else if(d.status == 6){ }}
		<span class="layui-red">工作状态</span>
		{{#  } else if(d.status == 7){ }}
		<span style="color: orange" class="layui-orange">示忙状态</span>
		{{#  } else if(d.status == 8){ }}
		<span class="layui-red">休息状态</span>
		{{#  } else { }}
		<span style="color: gray" class="layui-gray">未知</span>
		{{#  }}}
	</script>
	
	<!--AgentGateway状态-->
	<script type="text/html" id="AgentGatewayStatus">
		{{#  if(d.status == 0){ }}
		<span style="color: gray" class="layui-gray">未知</span>
		{{#  } else if(d.status == 1){ }}
		<span style="color: green" class="layui-green">空闲状态</span>
		{{#  } else if(d.status == 2){ }}
		<span class="layui-black">签出状态</span>
		{{#  } else if(d.status == 3){ }}
		<span style="color: orange" class="layui-orange">示忙状态</span>
		{{#  } else if(d.status == 4){ }}
		<span style="color: green" class="layui-green">空闲状态</span>
		{{#  } else if(d.status == 5){ }}
		<span class="layui-red">工作状态</span>
		{{#  } else if(d.status == 6){ }}
		<span style="color: green" class="layui-green">同空闲状态</span>
		{{#  } else if(d.status == 7){ }}
		<span class="layui-blue">通话状态</span>
		{{#  } else if(d.status == 8){ }}
		<span class="layui-red">休息状态</span>
		{{#  } else { }}
		<span style="color: gray" class="layui-gray">未知</span>
		{{#  }}}
	</script>

	<!--操作-->
	<script type="text/html" id="agentListBar">
		<a class="layui-btn layui-btn-xs layui-btn-primary" lay-event="look">查看</a>
		<a class="layui-btn layui-btn-xs" lay-event="call">呼叫</a>
	</script>
</form>
	<script type="text/javascript" src="${ctxStatic}/Layui/layui/layui.js"></script>
	<script type="text/javascript" src="${ctxStatic}/Layui/js/voiceCall/callData.js"></script>
</body>
</html>
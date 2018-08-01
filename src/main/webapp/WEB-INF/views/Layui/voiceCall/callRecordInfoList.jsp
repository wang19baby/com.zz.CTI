<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/include/taglib.jsp"%>
<!DOCTYPE html>
<html>
<head>
	<meta charset="utf-8">
	<title>通话记录</title>
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
		      <label class="layui-form-label">振铃号码：</label>
		      <div class="layui-input-inline">
		        <input name="otherPhone" type="text" class="layui-input" placeholder="请输入搜索的振铃号码"/>
		      </div>
		    </div>
		    
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
    
			<button class="layui-btn" lay-submit="" lay-filter="search"><i class="layui-icon">&#xe615;</i></button>
		</div>
	</blockquote>
	<table id="callRecordInfoList" lay-filter="callRecordInfoList"></table>
	
	<!-- 呼叫类型 -->
	<script type="text/html" id="callType">
		{{#  if(d.type == 1){ }}
		<span style="color: green">呼出</span>
		{{#  } else if(d.type == 2){ }}
		<span class="layui-blue">来电</span>
		{{#  } else { }}
		<span style="color: gray" class="layui-gray">未知</span>
		{{#  }}}
	</script>
	
	<!-- 呼叫结果 -->
	<script type="text/html" id="callResult">
		{{#  if(d.result){ }}
		<span class="layui-blue">已接</span>
		{{#  } else { }}
		<span class="layui-red">未接</span>
		{{#  }}}
	</script>

	<!--操作-->
	<script type="text/html" id="agentListBar">
		<a class="layui-btn layui-btn-xs" lay-event="call">重播</a>
		<a class="layui-btn layui-btn-xs layui-btn-primary" lay-event="look">回放</a>
	</script>
</form>
	<script type="text/javascript" src="${ctxStatic}/Layui/layui/layui.js"></script>
	<script type="text/javascript" src="${ctxStatic}/Layui/js/voiceCall/callRecordInfoList.js"></script>
</body>
</html>
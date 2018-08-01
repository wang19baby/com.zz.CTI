layui.define(['jquery','layer'],function(exports){
	var	$ = layui.$,
		layer = parent.layer === undefined ? layui.layer : top.layer;
	var obj = {
			// 强制签入
			forceLogin: function (user) {
				$.ajax({
					type : 'post',
					url : '/' + WEB_NAME + '/sys/forcelogin',
					contentType : 'application/json',
					dataType : 'json',
					data : user,
					success : function(result) {
						if (result.retcode == "0") {
							// 保存坐席信息
							window.sessionStorage.setItem("changeRefresh", "true"); // 切换窗口刷新当前页面
							window.sessionStorage.setItem("text", '示闲');
							window.sessionStorage.setItem("color", 'green');
							location.href = '/' + WEB_NAME + '/sys/login';
						} else {
							// 错误信息
							layer.msg(result.error_msg, {icon: 5,time: 2000,shift: 6}, function(){});
						}
					},
					error : function(result) {
						// 错误信息
						layer.msg('响应失败', {icon: 5,time: 2000,shift: 6}, function(){});
					}
				});
			},
			// 外呼
			callOut: function (data) {
				//清除呼叫和录音流水号
				window.sessionStorage.removeItem("callId");
				window.sessionStorage.removeItem("recordId");
				
				var callData = data;
				$.ajax({
					type : 'post',
					async : false,
					url : '/' + WEB_NAME + '/voicecall/callout',
					dataType : 'json',
					data : {called : callData},
					success : function(result) {
						if (result.retcode == "0") {
							// 外呼成功 更新外呼号码
							window.sessionStorage.setItem("status", 'CALL');
//							window.sessionStorage.setItem("called", callData);
							window.sessionStorage.setItem("callId", result.result);
							window.sessionStorage.setItem("callNum", callData);
						} else {
							layer.msg(result.error_msg, {icon: 5,time: 2000,shift: 6}, function(){});
						}
					},
					error : function(result) {
						layer.msg('响应失败！！！', {icon: 5,time: 2000,shift: 6}, function(){});
					}
				});
			},
			// 内呼
			callInner: function (data) {
				//清除呼叫和录音流水号
				window.sessionStorage.removeItem("callId");
				window.sessionStorage.removeItem("recordId");
				
				var callData = data;
				$.ajax({
					type : 'post',
					async : false,
					url : '/' + WEB_NAME + '/voicecall/callinner',
					dataType : 'json',
					data : {called : callData.workno},
					success : function(data) {
						if (data.retcode == "0") {
							// 内呼成功
							window.sessionStorage.setItem("status", 'CALL');
//							window.sessionStorage.setItem("called", callData.name);
							window.sessionStorage.setItem("callId", data.result);
							window.sessionStorage.setItem("callNum", callData.name);
						} else {
							 layer.msg(data.error_msg, {icon: 5,time: 2000,shift: 6}, function(){});
						}
					},
					error : function(data) {
						layer.msg('响应失败！！！', {icon: 5,time: 2000,shift: 6}, function(){});
					}
				});
			},
			// 呼叫应答
			callAnswer: function () {
				$.ajax({
					type : 'post',
					async : false,
					url : '/' + WEB_NAME + '/voicecall/callAnswer',
					dataType : 'json',
					success : function(data) {
						if (data.retcode == "0") {
							
						} else {
							 layer.msg(data.error_msg, {icon: 5,time: 2000,shift: 6}, function(){});
						}
					},
					error : function(data) {
						layer.msg('响应失败！！！', {icon: 5,time: 2000,shift: 6}, function(){});
					}
				});
			},
			// 挂断
			callEnd: function () {
				$.ajax({
					type : 'post',
					async : false,
					url : '/' + WEB_NAME + '/voicecall/callrelease',
					dataType : 'json',
					success : function(data) {
						if (data.retcode == "0") {
							
						} else {
							 layer.msg(data.error_msg, {icon: 5,time: 2000,shift: 6}, function(){});
						}
					},
					error : function(data) {
						layer.msg('响应失败！！！', {icon: 5,time: 2000,shift: 6}, function(){});
					}
				});
			},
			// 挂断话机
			phoneEnd: function () {
				$.ajax({
					type : 'post',
					async : false,
					url : '/' + WEB_NAME + '/voicecall/callreleasePhone',
					dataType : 'json',
					success : function(data) {
						if (data.retcode == "0") {
							
						} else {
							 layer.msg(data.error_msg, {icon: 5,time: 2000,shift: 6}, function(){});
						}
					},
					error : function(data) {
						layer.msg('响应失败！！！', {icon: 5,time: 2000,shift: 6}, function(){});
					}
				});
			},
			// 更新坐席状态请求
			changeStatus: function (status) {
				$.ajax({
					type : 'POST',
					url : '/' + WEB_NAME + '/onlineagent/OnlineAgentServlet',
					dataType : 'json',
					data : {type : status},
					success : function(result) {
						if (result.retcode == "0") {
							layer.msg('坐席状态变更成功', {icon: 6,time: 1000});
						} else {
							// 错误信息
							layer.msg(result.error_msg, {icon: 5,time: 2000,shift: 6}, function(){});
						}
					},
					error : function(result) {
						// 错误信息
						layer.msg('响应失败', {icon: 5,time: 2000,shift: 6}, function(){});
					}
				});
			},
			// 查询呼叫数据
			callData: function (type) {
				var resultMsg;
				$.ajax({
					type : 'POST',
					async : false,
					url : '/' + WEB_NAME + '/voicecall/calldata',
					dataType : 'json',
					data : {type : type},
					success : function(result) {
						if (result.retcode == "0") {
							resultMsg = result.result;
						} else {
							// 错误信息
							layer.msg(result.error_msg, {icon: 5,time: 2000,shift: 6}, function(){});
						}
					},
					error : function(result) {
						// 错误信息
						layer.msg('响应失败', {icon: 5,time: 2000,shift: 6}, function(){});
					}
				});
				return resultMsg;
			},
			// 呼叫转移
			voiceTransfer: function (data) {
				var transferResult = true;
				$.ajax({
					type : 'POST',
					async : false,
					url : '/' + WEB_NAME + '/voicecall/callTransfer',
					dataType : 'json',
					data : {
							devicetype : data.devicetype,
							address : data.address,
							callNum : data.callNum
						},
					success : function(result) {
						if (result.retcode == "0") {
							layer.msg('呼叫转移成功', {icon: 6,time: 2000});
						} else {
							// 错误信息
							transferResult = false;
							layer.msg(result.error_msg, {icon: 5,time: 2000,shift: 6}, function(){});
						}
					},
					error : function(result) {
						// 错误信息
						transferResult = false;
						layer.msg('响应失败', {icon: 5,time: 2000,shift: 6}, function(){});
					}
				});
				return transferResult;
			},
			// 坐席班组
			agentGroup: function (type) {
				var resultMsg;
				$.ajax({
					type : 'POST',
					async : false,
					url : '/' + WEB_NAME + '/get/Msg',
					dataType : 'json',
					data : {type : type},
					success : function(result) {
						if (result.retcode == "0") {
							resultMsg = result.result;
						} else {
							// 错误信息
							layer.msg(result.error_msg, {icon: 5,time: 2000,shift: 6}, function(){});
						}
					},
					error : function(result) {
						// 错误信息
						layer.msg('响应失败', {icon: 5,time: 2000,shift: 6}, function(){});
					}
				});
				return resultMsg;
			},
			// 录音相关
			record: function (data) {
				$.ajax({
					type : 'POST',
					async : false,
					url : '/' + WEB_NAME + '/record/controller',
					dataType : 'json',
					data: data,  
					success : function(result) {
						if (result.retcode == "0") {
							
						} else {
							// 错误信息
							layer.msg(result.error_msg, {icon: 5,time: 2000,shift: 6}, function(){});
						}
					},
					error : function(result) {
						// 错误信息
						layer.msg('响应失败', {icon: 5,time: 2000,shift: 6}, function(){});
					}
				});
			},
			// 增加用户
			addAgent: function (data) {
				var resultMsg;
				$.ajax({
					method: 'POST',
					async : false,
					url : '/' + WEB_NAME + '/agentManage/addAgent',
					data : JSON.stringify(data),
					contentType : 'application/json',
					dataType : 'json',
					beforeSend: function() {
						layer.load();
					},
					success : function(result) {
						layer.closeAll('loading');
						if (result.retcode == "0") {
							layer.msg('添加坐席成功', {icon: 6,time: 1000});
						} else {
							layer.msg(result.error_msg, {icon: 5,time: 2000,shift: 6}, function(){});
						}
					},
					error : function(result) {
						layer.closeAll('loading');
						layer.msg('响应失败', {icon: 5,time: 2000,shift: 6}, function(){});
					}
				});
				return resultMsg;
			},
			// 删除用户
			deleteAgent: function (data) {
				var resultMsg;
				$.ajax({
					type : 'POST',
					async : false,
					url : '/' + WEB_NAME + '/agentManage/deleteAgent',
					contentType : 'application/json',
					dataType : 'json',
					data: JSON.stringify(data),
					success : function(result) {
						resultMsg = result;
						if (result.retcode == "0") {
							layer.msg('删除用户成功', {icon: 6,time: 2000});
						} else {
							// 错误信息
							layer.msg(result.error_msg, {icon: 5,time: 2000,shift: 6}, function(){});
						}
					},
					error : function(result) {
						// 错误信息
						layer.msg('响应失败', {icon: 5,time: 2000,shift: 6}, function(){});
					}
				});
				return resultMsg;
			},
			// 批量删除用户
			deleteAgents: function (data) {
				var resultMsg;
				$.ajax({
					type : 'POST',
					async : false,
					url : '/' + WEB_NAME + '/agentManage/deleteAgents',
					contentType : 'application/json',
					dataType : 'json',
					data: JSON.stringify(data),
					success : function(result) {
						resultMsg = result;
						if (result.retcode == "0") {
							layer.msg('批量删除用户成功', {icon: 6,time: 2000});
						} else {
							// 错误信息
							layer.msg(result.error_msg, {icon: 5,time: 2000,shift: 6}, function(){});
						}
					},
					error : function(result) {
						// 错误信息
						layer.msg('响应失败', {icon: 5,time: 2000,shift: 6}, function(){});
					}
				});
				return resultMsg;
			},
			// 编辑用户
			editAgent: function (data) {
				var resultMsg;
				$.ajax({
					type : 'POST',
					async : false,
					url : '/' + WEB_NAME + '/agentManage/editAgent',
					contentType : 'application/json',
					dataType : 'json',
					data: JSON.stringify(data),
					success : function(result) {
						resultMsg = result;
						if (result.retcode == "0") {
							layer.msg('编辑用户成功', {icon: 6,time: 2000});
						} else {
							// 错误信息
							layer.msg(result.error_msg, {icon: 5,time: 2000,shift: 6}, function(){});
						}
					},
					error : function(result) {
						// 错误信息
						layer.msg('响应失败', {icon: 5,time: 2000,shift: 6}, function(){});
					}
				});
				return resultMsg;
			}
	};
	exports('request', obj);
})

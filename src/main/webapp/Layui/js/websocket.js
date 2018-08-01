layui.config({
	base : "/" + WEB_NAME + "/Layui/js/"
}).extend({
	"request" : "request",
	"ctiUtil" : "ctiUtil"
})
layui.use(['ctiUtil','request','jquery','layer'],function() {
	var $ = layui.jquery,
		request = layui.request,
		ctiUtil = layui.ctiUtil,
		layer = parent.layer === undefined ? layui.layer : top.layer;

	var websocket = null;  
	if ('WebSocket' in window) {
	    websocket = new WebSocket('ws://' + ctiUtil.getRootPath_dc() + '/websocket/socketServer.do');  
	}  
	else if ('MozWebSocket' in window) {
	    websocket = new MozWebSocket('ws://' + ctiUtil.getRootPath_dc() + '/websocket/socketServer.do');  
	}  
	else {
	    websocket = new SockJS('http://' + ctiUtil.getRootPath_dc() + '/sockjs/socketServer.do');  
	}  
	websocket.onopen = onOpen; 			// 连接打开事件的事件监听器
	websocket.onmessage = onMessage;  	// 消息事件的事件监听器
	websocket.onerror = onError;  		// 监听error事件
	websocket.onclose = onClose; 		// 监听连接关闭事件
	  
	function onOpen() {}
	function onError() {}
	function onClose() {}

	function onMessage(evt) {
		var event = $.parseJSON(evt.data);
		if ("100-006" == event.retcode) {
			// 签出
			window.sessionStorage.setItem("status", 'LOGOUT');
		}
		if (event.eventType == undefined || null == event.eventType
				|| event.eventType.length == 0) {
			return;
		}
		eventHandle(event);
	}
	  
	// 单发
	function doSendUser() {
		// readyState 连接的当前状态。取值是 Ready state constants之一。 只读
	    if (websocket.readyState == websocket.OPEN) {  
	        var msg = document.getElementById("inputMsg").value;  
	        websocket.send("#anyone#" + msg);//调用后台handleTextMessage方法 
	        consoel.log('单发信息成功!');
	    } else {
	    	consoel.log('连接失败!');
	    }  
	}  
	  
	// 群发
	function doSendUsers() {
	    if (websocket.readyState == websocket.OPEN) {  
	        var msg = document.getElementById("inputMsg").value;  
	        websocket.send("#everyone#" + msg);//调用后台handleTextMessage方法  
	        consoel.log('群发信息成功!');
	    } else {
	    	consoel.log('连接失败!');
	    }  
	}

	// eventHandle
	function eventHandle(event) {
		var eventType = event.eventType;
		switch (eventType) {
		case "AgentOther_InService": // 座席签入成功
			window.sessionStorage.setItem("status", 'READY');
			break;
		case "AgentOther_ShutdownService": // 座席签出成功
			break;

		case "AgentState_Ready": // 示闲
		case "AgentState_Force_Ready": // Force Idle
		case "AgentState_CancelNotReady_Success": // 取消示忙成功
		case "AgentState_CancelRest_Success": // 取消休息成功
		case "AgentState_CancelWork_Success": // 退出工作态
			window.sessionStorage.setItem("status", 'READY');
			window.sessionStorage.setItem("text", '示闲');
			window.sessionStorage.setItem("color", 'green');
			break;

		case "AgentState_Busy": // 示忙
		case "AgentState_SetNotReady_Success": // 示忙成功
		case "AgentState_Force_SetNotReady": // 强制示忙成功
			window.sessionStorage.setItem("status", 'BUSY');
			window.sessionStorage.setItem("text", '示忙');
			window.sessionStorage.setItem("color", 'orange');
			break;

		case "AgentState_SetWork_Success": // 进入工作态
		case "AgentState_Work": // 工作中
			window.sessionStorage.setItem("status", 'WORK');
			window.sessionStorage.setItem("text", '工作');
			window.sessionStorage.setItem("color", 'red');
			break;

		case "AgentChat_DataRecved": // 文字交谈消息获取事件
			break;

		case "AgentChat_Disconnected": // 文字交谈会话结束
			break;

		case "AgentChat_Connected": // 文字交谈会话连接建立
			window.sessionStorage.setItem("status", 'TALKING');
			window.sessionStorage.setItem("text", '通话');
			window.sessionStorage.setItem("color", 'blue');
			break;

		case "AgentChat_Ring": // 文字交谈会话振铃
			window.sessionStorage.setItem("status", 'RING');
			window.sessionStorage.setItem("text", '震铃');
			window.sessionStorage.setItem("color", 'blue');
			break;
			
		case "AgentOther_PhoneAlerting": //物理话机正在振铃
			if(window.sessionStorage.getItem("status") != 'CALL') {
				// 坐席来电未摘机
//				window.sessionStorage.setItem("status", 'ALERTING');
			}
			break;

		case "AgentEvent_Auto_Answer": // 座席自动应答
		case "AgentEvent_Ringing": // 座席来电提醒
			//清除呼叫和录音流水号
			window.sessionStorage.removeItem("callId");
			window.sessionStorage.removeItem("recordId");
			
			window.sessionStorage.setItem("status", 'RINGING');
			window.sessionStorage.setItem("text", '来电');
			window.sessionStorage.setItem("color", 'blue');
			window.sessionStorage.setItem("callNum", event.content.caller);
//			window.sessionStorage.setItem("caller", event.content.caller);
			window.sessionStorage.setItem("callId", event.content.callid);
			break;

		case "AgentEvent_Talking": // 座席进入Talking
			window.sessionStorage.setItem("status", 'TALKING');
			window.sessionStorage.setItem("text", '通话');
			window.sessionStorage.setItem("color", 'blue');
			break;
			
		case "AgentMediaEvent_Record": // 当前座席录音开始
			window.sessionStorage.setItem("recordId", event.content.recordID);
			break;
			
		case "AgentMediaEvent_StopRecordDone": // 当前座席停止录音成功
			window.sessionStorage.setItem("status", "STOPRECORD");
			// 更新录音
			var recordData = {};
			recordData.recordId = window.sessionStorage.getItem("recordId");
			recordData.callId = window.sessionStorage.getItem("callId");
			recordData.recordTitle = window.sessionStorage.getItem("recordId");
			recordData.recordEndDate = ctiUtil.getNowFormatDate();
			recordData.type = 'UPDATERECORD';
			request.record(recordData);
			break;
			
		case "AgentMediaEvent_Play_Succ": // 录音播放成功
			window.sessionStorage.setItem("status", "RECORDPLAY");
			break;
			
		case "AgentMediaEvent_StopPlayDone": // 停止录音播放成功
			window.sessionStorage.setItem("status", "RECORDDONE");
			break;
			
		case "AgentEvent_Hold": // 保持成功
			window.sessionStorage.setItem("status", 'HOLD');
			window.sessionStorage.setItem("text", '保持');
			window.sessionStorage.setItem("color", 'red');
			break;
		
		case "AgentEvent_Call_Out_Fail": 	// 席发起外呼呼叫失败	
		case "AgentEvent_Inside_Call_Fail": // 座席发起内部呼叫已经失败
		case "AgentEvent_Call_Release": 	// 座席退出呼叫
		case "AgentEvent_No_Answer":		// 座席久不应答
		case "AgentOther_PhoneRelease": 	// 座席物理话机挂机
			window.sessionStorage.setItem("status", 'CALLEND');
			break;
			
		case "AgentSession_Destroyed": 	// session失效
	    	window.sessionStorage.clear();
	    	window.localStorage.clear();
	    	
			  layer.open({
			      type: 1,
			      title: false, //不显示标题栏
			      closeBtn: false,
			      area: '300px;',
			      shade: 0.8,
			      id: 'LAY_SessionDestroyed', //设定一个id，防止重复弹出
			      btn: ['重新登录'],
			      btnAlign: 'c',
			      moveType: 1, //拖拽模式，0或者1
			      zIndex: 999,
			      content: '<div style="padding: 50px; line-height: 22px; background-color: #393D49; color: #fff; font-weight: 300; text-align: center;">当前坐席已超时！！！</div>',
			      success: function(layero){
			        var btn = layero.find('.layui-layer-btn');
			        btn.find('.layui-layer-btn0').attr({
			        	href : '/' + WEB_NAME + '/sys/login'
			        });
			      }
			    });
			break;
			
		case "AgentSession_ThirdLogin": 	// 第三方登录
	    	window.sessionStorage.clear();
	    	window.localStorage.clear();
	    	
			  layer.open({
			      type: 1,
			      title: false, 			//不显示标题栏
			      closeBtn: false,
			      area: '300px;',
			      shade: 0.8,
			      id: 'LAY_ThirdLogin', 	//设定一个id，防止重复弹出
			      btn: ['返回登录'],
			      btnAlign: 'c',
			      moveType: 1, //拖拽模式，0或者1
			      zIndex: 999,
			      content: '<div style="padding: 50px; line-height: 22px; background-color: #393D49; color: #fff; font-weight: 300; text-align: center;">当前坐席在其他地方登录！！！</div>',
			      success: function(layero){
			        var btn = layero.find('.layui-layer-btn');
			        btn.find('.layui-layer-btn0').attr({
			        	href : '/' + WEB_NAME + '/sys/login'
			        });
			      }
			    });
			break;
			
		default:
			break;
		}
	}
})

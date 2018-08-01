//获取系统时间
var newDate = '';
var $;
var layer;
var request;

var LAY_CALL,
	LAY_RINGING,
	LAY_calllAgent,
	LAY_CallData,
	LAY_RecordPlay;
getLangDate();

//监听坐席状态sessionStorage改变
window.addEventListener("storage",function onStorageChange(event) {
	if (event.key == 'status') {
		// 更新坐席状态
		document.getElementById("statusSpan").innerHTML = window.sessionStorage.getItem("text");
		document.getElementById("statusSpan").style.color = window.sessionStorage.getItem("color");
		var status = window.sessionStorage.getItem("status");
		
		switch (status) {
		case 'CALL':
			// 坐席发起呼叫请求
			LAY_CALL = layer.open({
		          type: 2,
		          title: false, 			//不显示标题栏
		          closeBtn: false,
		          area: ['390px', '180px'],
		          shade: 0.8, 				// 遮罩
		          shadeClose: false, 		// 是否点击遮罩关闭
		          anim: 0, 					// 弹出动画
		          isOutAnim: true, 			// 关闭动画
		          scrollbar: false, 		// 是否允许浏览器出现滚动条
		          id: 'LAY_CALL', 			// 用于控制弹层唯一标识
		          btn: ['挂断','坐席'],
		          btnAlign: 'c',
		          moveType: 1,
		          content: ['/' + WEB_NAME + '/page/callOutLayer', 'no'],
		          success: function(layero){ // 层弹出后的成功回调方法
		              var btn = layero.find('.layui-layer-btn');
		              btn.css('position', 'absolute');
		              btn.css('bottom', '0');
		              btn.css('margin-left', '28%');
		          },
		          yes: function(index, layero){
		      	   	  //如果设定了yes回调，需进行手工关闭
		        	  layero.find('.layui-layer-btn0').text("挂断中...").attr("disabled","disabled").addClass("layui-disabled");
		        	  request.callEnd(); // 挂断
		          },
		          btn2: function(index, layero){
		      	   	  //按钮【按钮二】的回调
		        	  // 弹出转接坐席inframe窗口
		        	  openAgent();
		      	   	  // 开启该代码可禁止点击该按钮关闭
		      	   	  return false; 
		          }
		   });
			break;
			
		case 'RINGING':
			// 坐席来电提醒
			LAY_RINGING = layer.open({
		          type: 2,
		          title: false, 			//不显示标题栏
		          closeBtn: false,
		          area: ['390px', '180px'],
		          shade: 0.8, 				// 遮罩
		          shadeClose: false, 		// 是否点击遮罩关闭
		          anim: 0, 					// 弹出动画
		          isOutAnim: true, 			// 关闭动画
		          scrollbar: false, 		// 是否允许浏览器出现滚动条
		          id: 'LAY_RINGING', 		// 用于控制弹层唯一标识
		          btn: ['应答','挂断','查询','坐席'],
		          btnAlign: 'c',
		          moveType: 1,
		          content: ['/' + WEB_NAME + '/page/callOutLayer', 'no'],
		          success: function(layero){ // 层弹出后的成功回调方法
		              var btn = layero.find('.layui-layer-btn');
		              btn.css('position', 'absolute');
		              btn.css('bottom', '0');
		              btn.css('margin-left', '10%');
		          },
		          yes: function(index, layero){
		      	   	  //如果设定了yes回调，需进行手工关闭
		        	  request.callAnswer();
		          },
		          btn2: function(index, layero){
		        	  layero.find('.layui-layer-btn1').text("挂断中...").attr("disabled","disabled").addClass("layui-disabled");
		        	  request.callEnd(); // 挂断
		      	   	  // 开启该代码可禁止点击该按钮关闭
		      	   	  return false; 
		          },
		          btn3: function(index, layero){
		        	  openCallData();
		      	   	  // 开启该代码可禁止点击该按钮关闭
		      	   	  return false; 
		          },
		          btn4: function(index, layero){
		      	   	  // 开启该代码可禁止点击该按钮关闭
		        	  openAgent();
		      	   	  return false; 
		          }
		   });
			break;
			
		case 'CALLEND':
			// 呼叫结束
			layer.close(LAY_CALL);
			layer.close(LAY_RINGING);
			layer.close(LAY_calllAgent);
			layer.close(LAY_CallData);
			break;
			
		case 'STOPRECORD':
			// 停止录音
			layer.prompt({
				  formType: 0,
				  value: window.sessionStorage.getItem("recordId"),
				  id: 'LAY_' + window.sessionStorage.getItem("recordId"), // 用于控制弹层唯一标识
				  btn: ['确定'],
		          btnAlign: 'c',
				  closeBtn : 0,
				  move: false, // 禁止拖拽
				  moveType: 1,
				  maxlength: 100, //可输入文本的最大长度，默认500
				  title: '请输入录音标题',
				}, function(value, index, elem){
					var data = {};
					data.recordId = window.sessionStorage.getItem("recordId");
					data.callId = window.sessionStorage.getItem("callId");
					data.recordTitle = value;
					data.type = 'UPDATERECORD';
					request.record(data);
					layer.close(index);
				});
			break;
			
		case 'RECORDPLAY':
			// 录音播放
			LAY_RecordPlay = layer.open({
                type: 2,
                title: '录音回放', 		// 不显示标题栏
                closeBtn: 1,			// 关闭按钮
                area: ['600px', '220px'],
                shade: 0, 				// 遮罩
                shadeClose: false, 		// 是否点击遮罩关闭
                offset: 'b',
                anim: 0, 				// 弹出动画
                isOutAnim: true, 		// 关闭动画
                scrollbar: false, 		// 是否允许浏览器出现滚动条
                maxmin: true, 			// 最大最小化
                id: 'LAY_RecordPlay', 	// 用于控制弹层唯一标识
                moveType: 1,
                content: ['/' + WEB_NAME + '/page/recordPlay', 'no'],
                cancel: function(index, layero) {
                	// 停止录音播放
                	var recordData = {};
                	recordData.type = 'STOPPLAY';
                	request.record(recordData);
                } 
        	});
			break;
			
		case 'RECORDDONE':
			// 录音播放停止
			layer.close(LAY_RecordPlay);
			break;
			
		default:
			break;
		}
	}
});

layui.config({
	base : "/" + WEB_NAME + "/Layui/js/"
}).extend({
	"request" : "request",
	"ctiUtil" : "ctiUtil"
})

layui.use(['ctiUtil','request','form','element','layer','jquery'],function(){
    var form = layui.form,
    	element = layui.element,
    	ctiUtil = layui.ctiUtil;
	layer = parent.layer === undefined ? layui.layer : top.layer;
	$ = layui.jquery;
	request = layui.request;
	
    layer.config({
  	  extend: 'myskin/myskin.css'
  	});
    
    // 初始渲染坐席状态
	document.getElementById("statusSpan").innerHTML = window.sessionStorage.getItem("text");
	document.getElementById("statusSpan").style.color = window.sessionStorage.getItem("color");
    
    //签入时间【此处应该从接口获取，实际使用中请自行更换】
    $(".loginTime").html(ctiUtil.getDateByLong(window.sessionStorage.getItem("logindate")));    
        
    //icon动画
    $(".panel a").hover(function(){
        $(this).find(".layui-anim").addClass("layui-anim-scaleSpring");
    },function(){
        $(this).find(".layui-anim").removeClass("layui-anim-scaleSpring");
    })
    $(".panel a").click(function(){
        parent.addTab($(this));
    })
    
    // 座席当前呼叫统计信息
    fillParameter(request.callData('STATISTICS'));
    
    //填充数据方法
    function fillParameter(data){
        //判断字段数据是否存在
        function nullData(data){
            if(data == "undefined"){
                return "未定义";
            }else{
                return data;
            }
        }
        $(".talktime").text(nullData(data.talktime));      			//当日总通时
        $(".answernums").text(nullData(data.answernums));        	//当日接听次数
        $(".noanswernums").text(nullData(data.noanswernums));    	//当日久不应答次数
        $(".calloutnums").text(nullData(data.calloutnums));        	//当日外呼次数
        $(".intercallnums").text(nullData(data.intercallnums));    	//当日内部呼叫次数
        $(".transfernums").text(nullData(data.transfernums));    	//当日转接次数
        $(".transferoutnums").text(nullData(data.transferoutnums));	//当日转出次数
        $(".keepnums").text(nullData(data.keepnums));				//当日保持次数
        $(".busynums").text(nullData(data.busynums));    			//当日示忙次数
        $(".busytime").text(nullData(data.busytime));				//当日示忙时长
        $(".restnums").text(nullData(data.restnums));    			//当日请假次数
        $(".resttime").text(nullData(data.resttime));				//当日请假时长
        $(".agentrelease").text(nullData(data.agentrelease));		//当日主动释放数
    }

    //外部图标
    $.get(iconUrl,function(data){
        $(".outIcons span").text(data.split(".icon-").length-1);
    })
})

function openAgent() {
	LAY_calllAgent = layer.tab({
		area: ['500px', '100%'],
		offset: 'rt', 			// 坐标
		shade: 0, 				// 遮罩
		shadeClose: false, 		// 是否点击遮罩关闭
		id: 'LAY_calllAgent', 	// 用于控制弹层唯一标识
		scrollbar: false, 		// 是否允许浏览器出现滚动条
        anim: 0, 				// 弹出动画
        isOutAnim: true, 		// 关闭动画
        tab: [{
        	title: '坐席列表',
        	content: '<iframe src="' + '/' + WEB_NAME + '/page/agentTransfer' + '"></iframe>'
        }, {
        	title: '公司列表',
        	content: '<iframe src="' + '/' + WEB_NAME + '/page/agentTransferByReceptionist' + '"></iframe>'
        }]
	});
}

function openCallData() {
	LAY_CallData = layer.open({
        type: 2,
        title: false, 			//不显示标题栏
        closeBtn: false,
        area: ['500px', '100%'],
        offset: 'lt', 			// 坐标
        shade: 0, 				// 遮罩
        shadeClose: false, 		// 是否点击遮罩关闭
        anim: 0, 				// 弹出动画
        isOutAnim: true, 		// 关闭动画
        scrollbar: false, 		// 是否允许浏览器出现滚动条
        id: 'LAY_CallData', 	// 用于控制弹层唯一标识
        btn: ['关闭'],
        btnAlign: 'c',
        moveType: 1,
        content: ['/' + WEB_NAME + '/page/callData', 'no'],
        success: function(layero){ // 层弹出后的成功回调方法
            var btn = layero.find('.layui-layer-btn');
            btn.css('position', 'absolute');
            btn.css('bottom', '0');
            btn.css('margin-left', '40%');
        }
	});
}

function getLangDate(){
    var dateObj = new Date(); 			//表示当前系统时间的Date对象
    var year = dateObj.getFullYear(); 	//当前系统时间的完整年份值
    var month = dateObj.getMonth()+1; 	//当前系统时间的月份值
    var date = dateObj.getDate(); 		//当前系统时间的月份中的日
    var day = dateObj.getDay(); 		//当前系统时间中的星期值
    var weeks = ["星期日","星期一","星期二","星期三","星期四","星期五","星期六"];
    var week = weeks[day]; 				//根据星期值，从数组中获取对应的星期字符串
    var hour = dateObj.getHours(); 		//当前系统时间的小时值
    var minute = dateObj.getMinutes(); 	//当前系统时间的分钟值
    var second = dateObj.getSeconds(); 	//当前系统时间的秒钟值
    var timeValue = "" +((hour >= 12) ? (hour >= 18) ? "晚上" : "下午" : "上午" ); //当前时间属于上午、晚上还是下午
    newDate = dateFilter(year) + "年" + dateFilter(month) + "月" + dateFilter(date) + "日 " + " " + dateFilter(hour) + ":" + dateFilter(minute) 
    	+ ":" + dateFilter(second);
    document.getElementById("nowTime").innerHTML = "亲爱的" + document.getElementById("nowTime").getAttribute ('data-name') + "，" + timeValue + "好！ 欢迎使用呼叫中心。当前时间为： "
    	+ newDate + "　" + week;
    setTimeout("getLangDate()",1000);
}

//值小于10时，在前面补0
function dateFilter(date) {
    if(date < 10) {
    	return "0" + date;
    }
    return date;
}

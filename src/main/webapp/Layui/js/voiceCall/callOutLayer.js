layui.use([ 'form', 'layer', 'laydate', 'table', 'laytpl' ], function() {
	var form = layui.form, 
		layer = parent.layer === undefined ? layui.layer : top.layer, 
		$ = layui.jquery;
	var hour = 0,
		minute = 0,
		second = 0;
	
	if(window.sessionStorage.getItem("status") == 'CALL') {
		$('#callEvent').text('正在呼叫');
		$('#callNum').text(window.sessionStorage.getItem("callNum"));
//		$('#callNum').text(window.sessionStorage.getItem("called"));
	} else {
		$('#callEvent').text('来电震铃');
		$('#callNum').text(window.sessionStorage.getItem("callNum"));
//		$('#callNum').text(window.sessionStorage.getItem("caller"));
	}
	

	// 监听坐席进入会话
	window.addEventListener("storage", function onStorageChange(event) {
		if (event.key == 'status' && window.sessionStorage.getItem("status") == 'TALKING') {
			setInterval(timer, 1000);
			$('#callEvent').text('通话中');
		}
	});

	function timer() {
		second = second + 1;
		if (second >= 60) {
			second = 0;
			minute = minute + 1;
		}
		
		if (minute >= 60) {
			minute = 0;
			hour = hour + 1;
		}
		$('#callIcon').text(dateFilter(hour) + ':' + dateFilter(minute) + ':' + dateFilter(second));
	}
	
	//值小于10时，在前面补0
	function dateFilter(date) {
	    if(date < 10) {
	    	return "0" + date;
	    }
	    return date;
	}
})
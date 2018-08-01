layui.config({
	base : "/" + WEB_NAME + "/Layui/js/"
}).extend({
	"request" : "request"
})

layui.use(['request','form','layer','jquery'],function(){
    var form = layui.form,
    	request = layui.request,
        layer = parent.layer === undefined ? layui.layer : top.layer
        $ = layui.jquery;
        
    
    layer.config({
        extend: ['myskin/myskin.css'] //加载您的扩展样式
    });

    $(".loginBody .seraph").click(function(){
        layer.msg("功能尚未完善",{
            time:5000
        });
    })

    //登录按钮
    form.on("submit(login)",function(data){
    	var btnSubmit = $(this);
    	var userJson = JSON.stringify(data.field);
		$.ajax({
			method: 'POST',
			url : '/' + WEB_NAME + '/sys/login',
			data : userJson,
			contentType : 'application/json',
			dataType : 'json',
			beforeSend: function(){
				btnSubmit.text("登录中...").attr("disabled","disabled").addClass("layui-disabled");
			},
			success : function(result) {
				if (result.retcode == "0") {
					// 保存坐席信息
//					window.sessionStorage.setItem("changeRefresh", "true"); // 切换窗口刷新当前页面
					window.sessionStorage.setItem("text", '示闲');
					window.sessionStorage.setItem("color", 'green');
					location.href = '/' + WEB_NAME + '/sys/login';
				} else if (result.retcode == "100-002") {
			    	// 座席已经登录 进行强制签入
			    	layer.confirm('此坐席已登录，是否强制登录？', {
			    		skin: 'layer-ext-black',
			    		anim: 6,
			    		btn: ['是','否'],
			    		btnAlign: 'c',
			    		closeBtn : 0,
			    		move: false
			    	}, function() {
			    		request.forceLogin(userJson);
			    	}, function() {
			    		btnSubmit.text("登录").attr("disabled",false).removeClass("layui-disabled");
			    	});
				} else {
					// 错误信息
					layer.msg(result.error_msg, {icon: 5,time: 2000,shift: 6}, function(){});
					btnSubmit.text("登录").attr("disabled",false).removeClass("layui-disabled");
				}
			},
			error : function(result) {
				// 错误信息
				layer.msg('响应失败', {icon: 5,time: 2000,shift: 6}, function(){});
				btnSubmit.text("登录").attr("disabled",false).removeClass("layui-disabled");
			}
		});
		// 阻止form表单submit
		return false;
    })

    //表单输入效果
    $(".loginBody .input-item").click(function(e){
        e.stopPropagation();
        $(this).addClass("layui-input-focus").find(".layui-input").focus();
    })
    $(".loginBody .input-item").change(function(e){
        e.stopPropagation();
        $(this).addClass("layui-input-focus").find(".layui-input").focus();
    })
    $(".loginBody .layui-form-item .layui-input").focus(function(){
        $(this).parent().addClass("layui-input-focus");
    })
    $(".loginBody .layui-form-item .layui-input").blur(function(){
        $(this).parent().removeClass("layui-input-focus");
        if($(this).val() != ''){
            $(this).parent().addClass("layui-input-active");
        }else{
            $(this).parent().removeClass("layui-input-active");
        }
    })
})

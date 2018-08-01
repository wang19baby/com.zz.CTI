layui.config({
	base : "/" + WEB_NAME + "/Layui/js/"
}).extend({
	"request" : "request"
})
layui.use(['request','form','layer','laydate','table','laytpl'],function(){
    var form = layui.form,
        layer = parent.layer === undefined ? layui.layer : top.layer,
        $ = layui.jquery,
        laydate = layui.laydate,
        laytpl = layui.laytpl,
        table = layui.table,
        request = layui.request;
    
    // 转接坐席列表导航按钮渲染
    var html = '<div class="layui-inline" style="margin:5px 5px 5px 5px;"><a class="layui-btn" data-id="0"><i class="layui-icon">&#xe613;</i>全部</a></div>';
    var arr = request.agentGroup('GROUPONVDN');
    for(var i = 0 ; i < arr.length; i++){
    	html += '<div class="layui-inline" style="margin:5px 5px 5px 5px;"><a class="layui-btn" data-id="' + arr[i].id + '"><i class="layui-icon">&#xe613;</i>' + arr[i].name + '</a></div>';
    }
    $('.quoteBox').append(html);
    
    //坐席列表
    var tableIns = table.render({
        elem: '#agentTransfer', //指定原始表格元素选择器（推荐id选择器）
        url : '/' + WEB_NAME + '/get/Msg', //数据接口
        where: {type : 'AGENTBYGROUP'}, //接口的其它参数
        cellMinWidth : 95,//（layui 2.2.1 新增）全局定义所有常规单元格的最小宽度（默认：60），一般用于列宽自动分配的情况。其优先级低于表头参数中的 minWidth
        page : false, //开启分页
        height : "full-125", //容器高度
        id : "agentTransfer", // 设定容器唯一ID
        text: { //  自定义文本
            none: '暂无相关数据' //默认：无数据。注：该属性为 layui 2.2.5 开始新增
        },
        response: { //定义后端 json 格式，详细参见官方文档
        	statusName: 'retcode', //数据状态的字段名称，默认：code
        	statusCode: 0, //成功的状态码，默认：0
        	msgName: 'error_msg', //状态信息的字段名称，默认：msg
        	countName: 'count', //数据总数的字段名称，默认：count
        	dataName: 'result' //数据列表的字段名称，默认：data
        },
        cols : [[ //表头
            {field: 'workno', title: '座席工号', align:"center"},
            {field: 'name', title: '座席名称', align:'center'},
            {field: 'status', title: '座席状态', align:'center', templet:"#AgentGatewayStatus"},
            {title: '操作', width:170,fixed:"right",align:"center",templet:'#agentListBar'}
        ]]
    });
    
    // 坐席班组查询
    $('.layui-btn').click(function() {
    	tableIns.reload({
        	where: {
        		type : 'ONLIGNEAGENTGROUP',
        		groupId : $(this).data('id')
        	}
        });
    })

    //列表操作
    table.on('tool(agentTransfer)', function(obj){  //注：tool是工具条事件名，newsList是table原始容器的属性 lay-filter="对应的值"
        var layEvent = obj.event,  //获得 lay-event 对应的值（也可以是表头的 event 参数对应的值）
            data = obj.data; //获得当前行数据
        if(layEvent === 'transfer') {
        	data.devicetype = 2;
        	data.address = data.workno;
        	data.callNum = window.sessionStorage.getItem("callNum");
        	
        	$(this).text("转移中...").attr("disabled","disabled").addClass("layui-disabled");
        	if(!request.voiceTransfer(data)) {
        		// 呼叫转移失败
        		$(this).text("转移").attr("disabled",false).removeClass("layui-disabled");
        	}
        } else if(layEvent === 'confjoin') { //三方通话
        	layer.msg('三方通话', {icon: 5,time: 2000,shift: 6}, function(){});
        }
    });
})
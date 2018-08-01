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
    
    //公司列表
    var tableIns = table.render({
        elem: '#agentTransferByReceptionist', 				//指定原始表格元素选择器（推荐id选择器）
        url : '/' + WEB_NAME + '/agentManage/agentList', 	//数据接口
        cellMinWidth : 95,									//（layui 2.2.1 新增）全局定义所有常规单元格的最小宽度（默认：60），一般用于列宽自动分配的情况。其优先级低于表头参数中的 minWidth
        page : true, 										//开启分页
        limit : 20, 										//每页显示的条数（默认：10）。值务必对应 limits 参数的选项。优先级低于 page 参数中的 limit 参数。
        limits : [10,20,30,40], 							// 每页条数的选择项
        height : "full-110", 								//容器高度
        id : "agentTransferByReceptionist", 				// 设定容器唯一ID
        text: { 											//  自定义文本
            none: '暂无相关数据' 								//默认：无数据。注：该属性为 layui 2.2.5 开始新增
        },
        response: { 										//定义后端 json 格式，详细参见官方文档
        	statusName: 'retcode', 							//数据状态的字段名称，默认：code
        	statusCode: 0, 									//成功的状态码，默认：0
        	msgName: 'error_msg', 							//状态信息的字段名称，默认：msg
        	countName: 'count', 							//数据总数的字段名称，默认：count
        	dataName: 'result' 								//数据列表的字段名称，默认：data
        },
        cols : [[ //表头
            {field: 'userNo', title: '员工号', align:"center", sort: true},
            {field: 'workName', title: '姓名',  align:'center'},
            {field: 'phoneNumber', title: '座机号', align:'center'},
            {title: '操作', width:170,fixed:"right",align:"center",templet:'#agentTransferByReceptionistBar'}
        ]]
    });
    
    //列表操作
    table.on('tool(agentTransferByReceptionist)', function(obj){  	//注：tool是工具条事件名，newsList是table原始容器的属性 lay-filter="对应的值"
        var layEvent = obj.event,  									//获得 lay-event 对应的值（也可以是表头的 event 参数对应的值）
            data = obj.data; 										//获得当前行数据
        if(layEvent === 'transfer') {
        	data.devicetype = 5;
        	data.address = data.phoneNumber;
        	data.callNum = window.sessionStorage.getItem("callNum");
        	
        	$(this).text("转移中...").attr("disabled","disabled").addClass("layui-disabled");
        	if(!request.voiceTransfer(data)) {
        		// 呼叫转移失败
        		$(this).text("转移").attr("disabled",false).removeClass("layui-disabled");
        	}
        } else if(layEvent === 'cancle') { //取消转移
        	layer.msg('取消转移', {icon: 5,time: 2000,shift: 6}, function(){});
        }
    });
    
    form.on('select(selectDepts)', function(data) {
    	tableIns.reload({
            page: {curr: 1},
        	where: {deptId : data.value}
        })
    });
})
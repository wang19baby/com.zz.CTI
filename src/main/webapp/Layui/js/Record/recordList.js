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
    
    //执行一个laydate实例
    laydate.render({
    	elem:'#startDate',		// 绑定元素
    	theme: '#393D49', 		// 自定义背景色主题
    	calendar: true,			// 显示公历节日
    	mark: {'0-0-10': '工资'}, //每个月10号
		done: function(value, date, endDate){
			$("#endDate").val(value);
		}
    });
    laydate.render({
    	elem:'#endDate',
    	theme: '#393D49',
    	calendar: true,
    	mark: {'0-0-10': '工资'},
		done: function(value, date, endDate){
			if($('#startDate').val() == '') {
				return;
			}
			// 判断结束日期是否大于开始日期
			if(new Date(Date.parse($('#startDate').val())) >= 
				new Date(Date.parse(value))) {
				$('#endDate').val($('#startDate').val());
			}
		}
    });
    
    //录音列表
    var tableIns = table.render({
        elem: '#recordList',
        url : '/' + WEB_NAME + '/record/controller',
        where: {type : 'RECORDLIST'},
        cellMinWidth : 95,			//（layui 2.2.1 新增）全局定义所有常规单元格的最小宽度（默认：60），一般用于列宽自动分配的情况。其优先级低于表头参数中的 minWidth
        loading : true, 			// 是否显示加载条
        page : true, 				//开启分页
        height : "full-125", 		//容器高度
        limit : 9, 					//每页显示的条数（默认：10）。值务必对应 limits 参数的选项。优先级低于 page 参数中的 limit 参数。
        limits : [9,18,27,36], 		// 每页条数的选择项
        id : "recordTable", 		// 设定容器唯一ID
        text: { 					//  自定义文本
            none: '暂无相关数据' 		//默认：无数据。注：该属性为 layui 2.2.5 开始新增
        },
        response: { 				//定义后端 json 格式，详细参见官方文档
        	statusName: 'retcode', 	//数据状态的字段名称，默认：code
        	statusCode: 0, 			//成功的状态码，默认：0
        	msgName: 'error_msg', 	//状态信息的字段名称，默认：msg
        	countName: 'count', 	//数据总数的字段名称，默认：count
        	dataName: 'result' 		//数据列表的字段名称，默认：data
        },
        cols : [[ //表头
            {field: 'recordId', title: '录音ID', align:"center"},
            {field: 'callId', title: '呼叫ID', align:'center'},
            {field: 'recordTitle', title: '录音标题',  align:'center'},
            {field: 'locationId', title: '中心节点', align:'center'},
            {field: 'recordStartDate', title: '录音开始时间', align:'center'},
            {field: 'recordEndDate', title: '录音结束时间', align:'center'},
            {title: '操作',fixed:"right",align:"center",templet:'#recordListBar'}
        ]]
    });
    
    //列表操作
    table.on('tool(recordList)', function(obj){
        var layEvent = obj.event,
            data = obj.data;
        if(layEvent === 'play'){ //录音回放
        	var recordData = {};
        	recordData.type = 'RECORDPLAY';
        	recordData.recordId = data.recordId;
        	request.record(recordData);
        	
        	var record = {};
        	record.title = data.recordTitle;
        	record.album = data.recordId + '.v3';
        	record.recordTime = (new Date(data.recordEndDate) - new Date(data.recordStartDate)) / 1000;
        	record.cover = '../Layui/images/Record/record.jpg';
        	window.sessionStorage.setItem("record", JSON.stringify(record));
        }
    });
    
    form.on("submit(search)",function(data){
    	tableIns.reload({
            page: {
                curr: 1 //重新从第 1 页开始
            },
        	where: {
        		type : 'RECORDLIST',
        		otherPhone : data.field.otherPhone,
        		startDate : data.field.startDate,
        		endDate : data.field.endDate
        	}, //接口的其它参数
        })
        return false; //阻止表单跳转。如果需要表单跳转，去掉这段即可。
    })

})
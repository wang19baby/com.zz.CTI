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
    
    //坐席列表
    var tableIns = table.render({
        elem: '#agentList', 				//指定原始表格元素选择器（推荐id选择器）
        url : '/' + WEB_NAME + '/get/Msg', 	//数据接口
        where: {type : 'AGENTINFO'}, 		//接口的其它参数
        cellMinWidth : 95,					//（layui 2.2.1 新增）全局定义所有常规单元格的最小宽度（默认：60），一般用于列宽自动分配的情况。其优先级低于表头参数中的 minWidth
        page : false, 						//开启分页
        height : "full-125", 				//容器高度
        limit : 20, 						//每页显示的条数（默认：10）。值务必对应 limits 参数的选项。优先级低于 page 参数中的 limit 参数。
        limits : [10,15,20,25], 			// 每页条数的选择项
        loading : true,
        id : "agentListTable", 				// 设定容器唯一ID
        text: { 							//  自定义文本
            none: '暂无相关数据' 				//默认：无数据。注：该属性为 layui 2.2.5 开始新增
        },
        response: { 						//定义后端 json 格式，详细参见官方文档
        	statusName: 'retcode', 			//数据状态的字段名称，默认：code
        	statusCode: 0, 					//成功的状态码，默认：0
        	msgName: 'error_msg', 			//状态信息的字段名称，默认：msg
        	countName: 'count', 			//数据总数的字段名称，默认：count
        	dataName: 'result' 				//数据列表的字段名称，默认：data
        },
        cols : [[ 							//表头
            {field: 'workno', title: '座席工号', align:"center"},
            {field: 'name', title: '座席名称', align:'center'},
            {field: 'status', title: '座席状态', align:'center', templet:"#CTIStatus"},
            {field: 'groupid', title: '座席班组ID',  align:'center'},
            {field: 'vdnid', title: '所属VDN ID', align:'center'},
            {field: 'mediatype', title: '座席媒体类型', align:'center'},
            {title: '操作',fixed:"right",align:"center",templet:'#agentListBar'}
        ]]
    });

    //搜索【此功能需要后台配合，所以暂时没有动态效果演示】
    $(".search_btn").on("click",function() {
        if ($(".searchVal").val() != '') {
        	var arrResult = new Array();
        	var arrTable = table.cache.agentListTable;
    		for(var i = 0; i < arrTable.length; i++){
    			//如果字符串中不包含目标字符会返回-1  字符模糊匹配
    			if(arrTable[i].name.match(new RegExp($(".searchVal").val()))){
    				//匹配成功
    				arrResult.push(arrTable[i].workno);
    			}
    		}
    		
    		if (arrResult === undefined || arrResult.length == 0) {
    		    // 搜索结果为空
    			layer.msg('查无此人', {icon: 5,time: 2000,shift: 6}, function(){});
    			return;
    		}
    		tableIns.reload({
            	where: {type : 'ONLINEAGENTONVDNBYKEYWORD', keyList : JSON.stringify(arrResult)}, //接口的其它参数
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
                    {field: 'status', title: '座席状态', align:'center', templet:"#CTIStatus"},
                    {field: 'groupid', title: '座席班组ID',  align:'center'},
                    {field: 'vdnid', title: '所属VDN ID', align:'center'},
                    {field: 'mediatype', title: '座席媒体类型', align:'center'},
                    {title: '操作',fixed:"right",align:"center",templet:'#agentListBar'}
                ]]
            })
        }else{
        	tableIns.reload({
            	where: {type : 'AGENTINFO'}, 	//接口的其它参数
                response: { 					//定义后端 json 格式，详细参见官方文档
                	statusName: 'retcode', 		//数据状态的字段名称，默认：code
                	statusCode: 0, 				//成功的状态码，默认：0
                	msgName: 'error_msg', 		//状态信息的字段名称，默认：msg
                	countName: 'count', 		//数据总数的字段名称，默认：count
                	dataName: 'result' 			//数据列表的字段名称，默认：data
                },
                cols : [[ //表头
                    {field: 'workno', title: '座席工号', align:"center"},
                    {field: 'name', title: '座席名称', align:'center'},
                    {field: 'status', title: '座席状态', align:'center', templet:"#CTIStatus"},
                    {field: 'groupid', title: '座席班组ID',  align:'center'},
                    {field: 'vdnid', title: '所属VDN ID', align:'center'},
                    {field: 'mediatype', title: '座席媒体类型', align:'center'},
                    {title: '操作',fixed:"right",align:"center",templet:'#agentListBar'}
                ]]
            })
        }
    });
    
    // 在线坐席
    $(".agent_btn").click(function(){
    	tableIns.reload({
        	where: {type : 'ONLIGNEAGENTONVDN'}, 	//接口的其它参数
            response: { 							//定义后端 json 格式，详细参见官方文档
            	statusName: 'retcode', 				//数据状态的字段名称，默认：code
            	statusCode: 0, 						//成功的状态码，默认：0
            	msgName: 'error_msg', 				//状态信息的字段名称，默认：msg
            	countName: 'count', 				//数据总数的字段名称，默认：count
            	dataName: 'result' 					//数据列表的字段名称，默认：data
            },
            cols : [[ //表头
                {field: 'workno', title: '座席工号', align:"center"},
                {field: 'name', title: '座席名称', align:'center'},
                {field: 'status', title: '座席状态', align:'center', templet:"#AgentGatewayStatus"},
                {field: 'groupid', title: '座席班组ID',  align:'center'},
                {field: 'vdnid', title: '所属VDN ID', align:'center'},
                {field: 'mediatype', title: '座席媒体类型', align:'center'},
                {title: '操作',fixed:"right",align:"center",templet:'#agentListBar'}
            ]]
        })
    })

    //列表操作
    table.on('tool(agentList)', function(obj){  //注：tool是工具条事件名，newsList是table原始容器的属性 lay-filter="对应的值"
        var layEvent = obj.event,  				//获得 lay-event 对应的值（也可以是表头的 event 参数对应的值）
            data = obj.data; 					//获得当前行数据
        if(layEvent === 'call') { 				//呼叫
        	request.callInner(data);
        } else if(layEvent === 'look') { 		//查看
        	console.log(tableIns.config.data);
            layer.open({
                type: 1,
                title: "详细信息",
                area: '300px',
                shade: 0.8, 					// 遮罩
                shadeClose: true, 				// 是否点击遮罩关闭
                anim: 4, 						// 弹出动画
                isOutAnim: true, 				// 关闭动画
                scrollbar: false, 				// 是否允许浏览器出现滚动条
                id: 'LAY_layuipro', 			// 用于控制弹层唯一标识
                btn: ['关闭'],
                moveType: 1,
                content: '<div style="padding:15px 20px; text-align:justify; line-height: 22px; text-indent:2em;border-bottom:1px solid #e2e2e2;">'
                	+'<p class="layui-red">测试</p></pclass>'
                	+'</div>',
                success: function(layero){ 		// 层弹出后的成功回调方法
                    var btn = layero.find('.layui-layer-btn');
                    btn.css('text-align', 'center');
                    btn.on("click",function(){
                    	
                    });
                },
                cancel: function(index, layero){
                	
                }
            });
        }
    });
})
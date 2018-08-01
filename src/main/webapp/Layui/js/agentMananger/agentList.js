layui.config({
	base : "/" + WEB_NAME + "/Layui/js/"
}).extend({
	"request" : "request"
})
layui.use(['request','form','layer','table','laytpl'],function(){
    var form = layui.form,
        layer = parent.layer === undefined ? layui.layer : top.layer,
        $ = layui.jquery,
        laytpl = layui.laytpl,
        table = layui.table,
    	request = layui.request;
    
    layer.config({
    	  extend: 'myskin/myskin.css', 	//加载新皮肤
    	});
    
    //用户列表
    var tableIns = table.render({
        elem: '#agentList',
        url : '/' + WEB_NAME + '/agentManage/agentList',
        cellMinWidth : 95,			//（layui 2.2.1 新增）全局定义所有常规单元格的最小宽度（默认：60），一般用于列宽自动分配的情况。其优先级低于表头参数中的 minWidth
        loading : true, 			// 是否显示加载条
        page : true, 				//开启分页
        height : "full-220", 		//容器高度
        limit : 15, 				//每页显示的条数（默认：10）。值务必对应 limits 参数的选项。优先级低于 page 参数中的 limit 参数。
        limits : [10,15,20,25], 	// 每页条数的选择项
        id : "agentList", 			// 设定容器唯一ID
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
        cols : [[ 					//表头
            {type: "checkbox", fixed:"left", width:50},
            {field: 'userNo', title: '员工号', align:"center", sort: true},
            {field: 'workNo', title: '坐席工号', align:'center'},
            {field: 'workName', title: '坐席名称',  align:'center'},
            {field: 'phoneNumber', title: '座机号', align:'center'},
            {field: 'agentProfile', title: '坐席简介', align:'center'},
            {title: '操作',fixed:"right",align:"center",templet:'#agentListBar'}
        ]]
    });
    
    //列表操作
    table.on('tool(agentList)', function(obj){
        var layEvent = obj.event,
            data = obj.data;
        
		switch (layEvent) {
		
		case "look":
			layer.open({
				skin: 'layui-layer-molv',
		        type: 2,
		        title: '用户详情', 		//不显示标题栏
		        maxmin: true,
		        btn: false,
		        closeBtn: 1,
		        area: ['500px', '500px'],
		        shade: 0, 				// 遮罩
		        shadeClose: false, 		// 是否点击遮罩关闭
		        anim: 0, 				// 弹出动画
		        isOutAnim: true, 		// 关闭动画
		        scrollbar: false, 		// 是否允许浏览器出现滚动条
		        id: 'LAY_AgentInfo', 	// 用于控制弹层唯一标识
		        moveType: 1,
		        content: ['/' + WEB_NAME + '/page/agentTransfer', 'no']
			});
			break;
			
		case "edit":
			// 编辑
			editUser(data);
			break;
			
		case "delete":
			// 删除
            layer.confirm('确定删除此用户？',{
            	skin: 'layer-ext-green',
            	icon: 3, 
            	title: '提示信息',
            	btnAlign: 'c',
            	closeBtn: 0
            	}, function(index) {
        			if(request.deleteAgent(data).retcode == "0") {
        				tableIns.reload();
        			}
        			layer.close(index);
            });
			break;
			
		default:
			break;
		}
    });
    
    form.on("submit(search)",function(data){
    	tableIns.reload({
            page: {
                curr: 1 //重新从第 1 页开始
            },
        	where: {
        		userNo : data.field.userNo,
        		workNo : data.field.workNo,
        		workName : data.field.workName,
        		deptId : data.field.dept
        	}, //接口的其它参数
        })
        return false; //阻止表单跳转。如果需要表单跳转，去掉这段即可。
    })
    
    //编辑用户
    function editUser(edit){
        var index = layui.layer.open({
            title : "编辑用户",
            type : 2,
            content : '/' + WEB_NAME + '/page/editUser?userNo=' + edit.userNo,
            success : function(layero, index){
                setTimeout(function(){
                    layui.layer.tips('点击此处返回用户列表', '.layui-layer-setwin .layui-layer-close', {
                        tips: 3
                    });
                },500)
            }
        })
        layui.layer.full(index);
        //改变窗口大小时，重置弹窗的宽高，防止超出可视区域（如F12调出debug的操作）
        $(window).on("resize",function(){
            layui.layer.full(index);
        })
    }
    
    //批量删除
    $(".delAll_btn").click(function(){
        var checkStatus = table.checkStatus('agentList'),
            data = checkStatus.data,
            agentUsers = [];
        if(data.length > 0) {
            for (var i in data) {
            	agentUsers.push(data[i]);
            }
            console.log(agentUsers);
            layer.confirm('确定删除选中的用户？',{
            	skin: 'layer-ext-black',
            	icon: 3, 
            	title: '提示信息',
            	btnAlign: 'c',
            	closeBtn: 0
            	}, function(index) {
        			if(request.deleteAgents(agentUsers).retcode == "0") {
        				tableIns.reload();
        			}
        			layer.close(index);
            });
        }else{
            layer.msg("请选择需要删除的用户");
        }
    })

})
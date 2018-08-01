layui.config({
	base : "/" + WEB_NAME + "/Layui/js/"
}).extend({
	"request" : "request"
})
layui.use(['request','form','layer'],function(){
    var form = layui.form
        layer = parent.layer === undefined ? layui.layer : top.layer,
        $ = layui.jquery,
        request = layui.request;
    
    form.on("submit(editAgent)",function(data) {
    	var agentPermissions = $("input:checkbox[name='agentPermissions']:checked").map(function (index, elem) {
    		return $(elem).val();
    	}).get();
    	
    	data.field.agentPermissions = new Array();
		for (var i = 0; i < agentPermissions.length; i++) {
			var agentPermission = new Object();
			agentPermission.permissionId = agentPermissions[i];
			data.field.agentPermissions.push(agentPermission);
		}
		var roleId = data.field.agentRole;
		var deptId = data.field.dept;
		data.field.dept = new Object();
    	data.field.agentRole = new Object();
    	data.field.agentRole.roleId = roleId;
    	data.field.dept.deptId = deptId;

    	if(request.editAgent(data.field).retcode == "0") {
    		var index = parent.layer.getFrameIndex(window.name);	//先得到当前iframe层的索引
    		parent.layer.close(index); 								//再执行关闭 
		}
    	
        return false;
    })
})
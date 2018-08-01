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

    form.on("submit(addAgent)",function(data) {
    	var agentPermissions = $("input:checkbox[name='agentPermissions']:checked").map(function (index, elem) {
    		return $(elem).val();
    	}).get();
    	
    	data.field.agentPermissions = new Array();
		for (var i = 0; i < agentPermissions.length; i++) {
			var agentPermission = new Object();
			agentPermission.permissionId = agentPermissions[i];
			data.field.agentPermissions.push(agentPermission);
		}
		data.field.dept = new Object();
		data.field.dept.deptId = data.field.deptId;
    	data.field.agentRole = new Object();
    	data.field.agentRole.roleId = data.field.roleId;
    	
    	if(request.addAgent(data.field).retcode == "0") {
    		$('.layui-form')[0].reset();
		}
        return false;
    })
})
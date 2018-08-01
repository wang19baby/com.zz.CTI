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
    
    // 座席当前呼叫统计信息
    fillParameter(request.callData('CALLINFOBEFOREANSWER'));
    
    //填充数据方法
    function fillParameter(data){
        //判断字段数据是否存在
        function nullData(data){
            if(!data){
                return "";
            }else{
                return data;
            }
        }
        $(".caller").text(nullData(data.caller));      				//主叫号码
        $(".called").text(nullData(data.called));        			//被叫号码
        $(".callskill").text(nullData(data.callskill));    			//呼叫技能描述
        $(".orgicallednum").text(nullData(data.orgicallednum));     //原始被叫
    }
})
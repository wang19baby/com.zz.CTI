layui.config({
	base : "/" + WEB_NAME + "/Layui/js/"
}).extend({
	"request" : "request"
});
layui.use(['request','form','layer','laydate','table','laytpl'],function(){
    var form = layui.form,
        layer = parent.layer === undefined ? layui.layer : top.layer,
        $ = layui.jquery,
        request = layui.request;
    
    //语音外呼
    $(".callOut_btn").on("click",function(){
        if($(".callVal").val() != ''){
        	request.callOut($(".callVal").val());
        }else{
			layer.msg('号码不能为空', {icon: 5,time: 2000,shift: 6}, function(){});
        }
    });
    
    $(".recordPlay").on("click",function(){
    	var data = {};
    	data.type = 'RECORDPLAY';
//    	request.record('RECORDPLAY');
//    	request.record('RECORDFILE');
//    	request.record(data);
//    	request.record('queryrecordscore');
//    	request.record('callRecordInfoList');
//    	request.record('batchrecordzipfileurl');
//    	request.record('queryselfcallrecordinfolist');
//    	request.record('recordfileurl');
//    	request.record('queryrecordfilebycallid');
//    	request.callData('CALLINFOBYCALLID');
//    	request.record('queryselfrecordfile');
    });
})
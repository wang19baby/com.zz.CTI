layui.define('jquery',function(exports){
	var	$ = layui.$;
	
	var obj = {
			// 毫秒数转时间
			getDateByLong : function (l) {
				var d= new Date();
				d.setTime(l);
				return d.format('yyyy-MM-dd HH:mm:ss');
			},
			// 获取当前的日期时间 格式“yyyy-MM-dd HH:MM:SS”
			getNowFormatDate : function () {
		        var date = new Date();
		        var seperator1 = "-";
		        var seperator2 = ":";
		        var month = date.getMonth() + 1;
		        var strDate = date.getDate();
		        var minutes = date.getMinutes();
		        var seconds = date.getSeconds();
		        if (month >= 1 && month <= 9) {
		            month = "0" + month;
		        }
		        if (strDate >= 0 && strDate <= 9) {
		            strDate = "0" + strDate;
		        }
		        if (minutes >=0 && minutes <=9) {
		        	minutes = "0" + minutes;
				}
		        if (seconds >=0 && seconds <=9) {
		        	seconds = "0" + seconds;
				}
		        var currentdate = date.getFullYear() + seperator1 + month + seperator1 + strDate
		                + " " + date.getHours() + seperator2 + minutes
		                + seperator2 + seconds;
		        return currentdate;
		    },
		    // 获取项目路径
		    getRootPath_dc : function () {
			    var pathName = window.location.pathname.substring(1);
			    var webName = pathName == '' ? '' : pathName.substring(0, pathName.indexOf('/'));
			    if (webName == "") {
			        return window.location.host;
			    }
			    else {
			        return window.location.host + '/' + webName;
			    }
			}
	};
	
	Date.prototype.format = function(fmt) {        
	    var o = {        
	    "M+" : this.getMonth()+1, //月份        
	    "d+" : this.getDate(), //日        
	    "h+" : this.getHours()%12 == 0 ? 12 : this.getHours()%12, //小时        
	    "H+" : this.getHours(), //小时        
	    "m+" : this.getMinutes(), //分        
	    "s+" : this.getSeconds(), //秒        
	    "q+" : Math.floor((this.getMonth()+3)/3), //季度        
	    "S" : this.getMilliseconds() //毫秒        
	    };        
	    var week = {        
	    "0" : "\u65e5",        
	    "1" : "\u4e00",        
	    "2" : "\u4e8c",        
	    "3" : "\u4e09",        
	    "4" : "\u56db",        
	    "5" : "\u4e94",        
	    "6" : "\u516d"       
	    };
	    
	    if(/(y+)/.test(fmt)){        
	        fmt=fmt.replace(RegExp.$1, (this.getFullYear()+"").substr(4 - RegExp.$1.length));        
	    }        
	    if(/(E+)/.test(fmt)){        
	        fmt=fmt.replace(RegExp.$1, ((RegExp.$1.length>1) ? (RegExp.$1.length>2 ? "\u661f\u671f" : "\u5468") : "")+week[this.getDay()+""]);        
	    }        
	    for(var k in o){        
	        if(new RegExp("("+ k +")").test(fmt)){        
	            fmt = fmt.replace(RegExp.$1, (RegExp.$1.length==1) ? (o[k]) : (("00"+ o[k]).substr((""+ o[k]).length)));        
	        }        
	    }        
	    return fmt;        
	}
	
	exports('ctiUtil', obj);
})

<%@ page language="java" pageEncoding="UTF-8"%>

<link rel="stylesheet" type="text/css" href="../style.css">
<script type="text/javascript" src="../js/jquery-1.6.2.min.js"></script>
<script type="text/javascript" src="../js/jquery.form.js"></script>
<script type="text/javascript" src="../js/jquery-jtemplates.js"></script>



<script type="text/javascript">
function toType(type){
	if(type == "info"){
		return "信息";
	} else if(type == "juice"){
		return "挤奶";
	} else if(type == "milk"){
		return "喝奶";
	} else if(type == "shit"){
		return "大便";
	} else if(type == "weight"){
		return "体重";
	} else if(type == "temp"){
		return "体温";
	} else if(type == "heart"){
		return "心率";
	} else if(type == "height"){
		return "身高";
	} else {
		return "未知";
	}
}

function toDatetime(time, fmt){
	//对Date的扩展，将 Date 转化为指定格式的String   
	//月(M)、日(d)、小时(h)、分(m)、秒(s)、季度(q) 可以用 1-2 个占位符，   
	//年(y)可以用 1-4 个占位符，毫秒(S)只能用 1 个占位符(是 1-3 位的数字)   
	//例子：   
	//(new Date()).Format("yyyy-MM-dd hh:mm:ss.S") ==> 2006-07-02 08:09:04.423   
	//(new Date()).Format("yyyy-M-d h:m:s.S")      ==> 2006-7-2 8:9:4.18   
	Date.prototype.format = function(fmt) { //author: meizz   
		var o = {   
		 "M+" : this.getMonth()+1,                 //月份   
		 "d+" : this.getDate(),                    //日   
		 "h+" : this.getHours(),                   //小时   
		 "m+" : this.getMinutes(),                 //分   
		 "s+" : this.getSeconds(),                 //秒   
		 "q+" : Math.floor((this.getMonth()+3)/3), //季度   
		 "S"  : this.getMilliseconds()             //毫秒   
		};   
		if(/(y+)/.test(fmt))   
		 fmt=fmt.replace(RegExp.$1, (this.getFullYear()+"").substr(4 - RegExp.$1.length));   
		for(var k in o)   
		 if(new RegExp("("+ k +")").test(fmt))   
		fmt = fmt.replace(RegExp.$1, (RegExp.$1.length==1) ? (o[k]) : (("00"+ o[k]).substr((""+ o[k]).length)));   
		return fmt;   
	}
	
	
	var date = new Date();
	date.setTime(time);
	if(!fmt)fmt = "yyyy-MM-dd hh:mm";
	return date.format(fmt);
}


function toAmount(type, amount){
	var forDight = function(Dight,How){  
        Dight = Math.round(Dight*Math.pow(10,How))/Math.pow(10,How);  
        return Dight;  
    }  
	if(type == "info"){
		return "";
	} else if(type == "juice"){
		return forDight(amount, 0) + "ML";
	} else if(type == "milk"){
		return forDight(amount, 0) + "ML";
	} else if(type == "shit"){
		if(amount == 1 || amount == "1"){
			return "极少";
		} else if(amount == 2 || amount == "2"){
			return "较少";
		} else if(amount == 3 || amount == "3"){
			return "适中";
		} else if(amount == 4 || amount == "4"){
			return "较多";
		} else if(amount == 5 || amount == "5"){
			return "极多";
		}
	} else if(type == "weight"){
		return forDight(amount, 2) + "KG";
	} else if(type == "temp"){
		return forDight(amount, 1) + "度";
	} else if(type == "heart"){
		return forDight(amount, 0) + "bpm";
	} else if(type == "height"){
		return forDight(amount, 0) + "CM";
	} else {
		return "";
	}
}
</script>
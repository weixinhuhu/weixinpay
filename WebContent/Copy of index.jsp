<%@ page language="java" import="java.io.*,java.util.*"
	pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<%@ page import="org.course.service.*"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<%
	String wecharNo = "";
%>
<%
	wecharNo = request.getParameter("openid");
%>
<head>
<base href="<%=basePath%>">
<title>用户水费预存</title>
<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
<meta http-equiv="expires" content="0">
<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
<meta http-equiv="description" content="This is my page">

<script language="javascript">
function amount(th){ 
    var regStrs = [ 
        ['^0(\\d+)$', '$1'], //禁止录入整数部分两位以上，但首位为0 
        ['[^\\d\\.]+$', ''], //禁止录入任何非数字和点 
        ['\\.(\\d?)\\.+', '.$1'], //禁止录入两个以上的点 
        ['^(\\d+\\.\\d{2}).+', '$1'] //禁止录入小数点后两位以上 
    ]; 
    for(i=0; i<regStrs.length; i++){ 
        var reg = new RegExp(regStrs[i][0]); 
        th.value = th.value.replace(reg, regStrs[i][1]); 
    } 
} 

function overFormat(th){ 
    var v = th.value; 
    if(v === ''){ 
        v = '0.00'; 
    }else if(v === '0'){ 
        v = '0.00'; 
    }else if(v === '0.'){ 
        v = '0.00'; 
    }else if(/^0+\d+\.?\d*.*$/.test(v)){ 
        v = v.replace(/^0+(\d+\.?\d*).*$/, '$1'); 
        v = inp.getRightPriceFormat(v).val; 
    }else if(/^0\.\d$/.test(v)){ 
        v = v + '0'; 
    }else if(!/^\d+\.\d{2}$/.test(v)){ 
        if(/^\d+\.\d{2}.+/.test(v)){ 
            v = v.replace(/^(\d+\.\d{2}).*$/, '$1'); 
        }else if(/^\d+$/.test(v)){ 
            v = v + '.00'; 
        }else if(/^\d+\.$/.test(v)){ 
            v = v + '00'; 
        }else if(/^\d+\.\d$/.test(v)){ 
            v = v + '0'; 
        }else if(/^[^\d]+\d+\.?\d*$/.test(v)){ 
            v = v.replace(/^[^\d]+(\d+\.?\d*)$/, '$1'); 
        }else if(/\d+/.test(v)){ 
            v = v.replace(/^[^\d]*(\d+\.?\d*).*$/, '$1'); 
            ty = false; 
        }else if(/^0+\d+\.?\d*$/.test(v)){ 
            v = v.replace(/^0+(\d+\.?\d*)$/, '$1'); 
            ty = false; 
        }else{ 
            v = '0.00'; 
        } 
    } 
    th.value = v    
}

function submit(){
	var	Money=document.getElementById("inputmoney").value
	 if (Money==0){
		 alert("请输入正确的购买金额！");
	 }else{
		var	 url= "http://weixinsdpt.applinzi.com/jsapi.jsp?openid=<%=wecharNo%>&totlemoney="+Money;
		window.location.href = url
	}
}


</script>
</head>
<div style="text-align: center; width: 100%; height: 100%; margin: 0px;">
	<br>请输入预存金额 <br> <input type="text"
		style="ime-mode: disabled; width: 70%; height: 5%; font-size: 2em"
		maxlength="6" size="20"  name="inputmoney"
		id="inputmoney" onKeyUp="amount(this)" onBlur="overFormat(this)" /> <br>
	<button
		style="font-size: 2em; width: 70%; height: 10%; background-color: green"
		class="  ub-ac bc-text-head  bc-btn " onclick="submit()">提交</button>
</div>
<body>
</body>
</html>

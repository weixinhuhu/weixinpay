<%@ page language="java" contentType="text/html; charset=GBK"
		 pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<%
	String wecharNo = "";
%>
<%
	wecharNo = request.getParameter("openid");
%>

<!DOCTYPE HTML>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>用户注册</title>
<link rel="stylesheet" href="css/weui.css" />
<link rel="stylesheet" href="css/example.css" />
<meta name="viewport" content="width=device-width, initial-scale=1" />
<meta name="apple-mobile-web-app-capable" content="yes" />
<meta name="apple-mobile-web-app-status-bar-style" content="black" />
<script type="text/javascript">
 	function UserReg(){
 		var meterInfo=document.getElementById("meterinfo").value;
 		var userPhone=document.getElementById("userphone").value;
		if (checkMeteinfo(meterInfo)==false){
		alert("请输入正确的用户编号");
		return;
		}
		if (checkPhone(userPhone)==false){
		alert("请输入正确的手机号");
		return;
		}
	 	var	 url= "http://weixinsdpt.applinzi.com/UserRegResult.jsp?wecharNo=<%=wecharNo%>&meterInfo="+meterInfo+"&userPhone="+userPhone+"&flag=0";
	 	window.location.href = url;
 	}
 	
 	function UserUnReg(){
 		var meterInfo=document.getElementById("meterinfo").value;
 		var userPhone=document.getElementById("userphone").value;
		if (checkMeteinfo(meterInfo)==false){
		alert("请输入正确的用户编号");
		return;
		}
		if (checkPhone(userPhone)==false){
		alert("请输入正确的手机号");
		return;
		}
 		var	 url= "http://weixinsdpt.applinzi.com/UserRegResult.jsp?wecharNo=<%=wecharNo%>&meterInfo="+meterInfo+"&userPhone="+userPhone+"&flag=1";
	 	window.location.href = url;
	}

	function checkMeteinfo(s) {
		if (s == "") {
			return false;
		} else
			return true;
	}

	function checkPhone(s) {
		var phoneReg = /^1[3-578]\d{9}$/;
		if (phoneReg.test(s)) {
			return true;
		} else {
			return false;
		}
	}
</script>
</head>
<body>
	<div class="page__hd">
		<h1 class="page__title">账户绑定</h1>
		<p class="page__desc"></p>
		<div class="weui-cells weui-cells_form">
			<div class="weui-cell">
				<div class="weui-cell__hd">
					<label class="weui-label">用户编号</label>
				</div>
				<div class="weui-cell__bd">
					<input id="meterinfo" class="weui-input" type="number"
						pattern="[0-9]*" placeholder="请输入用户编号" maxlength="10">
				</div>
			</div>
			<div class="weui-cell weui-cells_form">
				<div class="weui-cell__hd">
					<label class="weui-label">手机号</label>
				</div>
				<div class="weui-cell__bd">
					<input id="userphone" class="weui-input" type="tel"
						placeholder="请输入手机号" maxlength="11">
				</div>
			</div>
		</div>
	</div>
	<div class="weui-form-preview__ft"
		style="border-bottom: 1px solid #e5e5e5;">
		<a class="weui-form-preview__btn weui-form-preview__btn_default"
			onclick="UserUnReg()">解绑</a>
		<button type="submit"
			class="weui-form-preview__btn weui-form-preview__btn_primary"
			onclick="UserReg()">绑定</button>
	</div>
</body>
</html>
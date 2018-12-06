<%@ page language="java" import="java.io.*,java.util.*"
	pageEncoding="UTF-8"%>
<%@ page import="org.course.service.*"%>
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
<meta charset="utf-8" />
<meta name="viewport" content="width=device-width, initial-scale=1" />
<meta name="apple-mobile-web-app-capable" content="yes" />
<meta name="apple-mobile-web-app-status-bar-style" content="black" />

<!-- 控制浏览器缓存 -->
<meta http-equiv="Cache-Control" content="no-store" />
<title>用户预缴水费</title>
<style>
* {
	margin: 0;
	padding: 0;
	box-sizing: border-box;
}

html,body {
	height: 100%;
	overflow: hidden;
}

.clearfix:after {
	content: "\200B";
	display: block;
	height: 0;
	clear: both;
}

.clearfix {
	*zoom: 1;
}

.shuru div::-webkit-scrollbar-thumb {
	background-color: #a7afb4;
	background-clip: padding-box;
	min-height: 28px;
}

.shuru div::-webkit-scrollbar-thumb:hover {
	background-color: #525252;
	background-clip: padding-box;
	min-height: 28px;
}

.shuru div::-webkit-scrollbar-track-piece {
	background-color: #ccd0d2;
}

.wrap {
	position: relative;
	margin: auto;
	max-width: 640px;
	min-width: 320px;
	width: 100%;
	height: 100%;
	background: #F0EFF5;
	overflow: hidden;
}

.layer-content {
	position: absolute;
	left: 50%;
	bottom: -200px;
	width: 100%;
	max-width: 640px;
	height: auto;
	z-index: 12;
	-webkit-transform: translateX(-50%);
	transform: translateX(-50%);
}

/* 输入表单 */
.edit_cash {
	display: block;
	margin-top: 15px;
	padding: 15px;
	margin: 0 auto;
	width: 90%;
	border: 1px solid #CFCFCF;
	border-radius: 10px;
	background-color: #fff;
}

.edit_cash p {
	font-size: 14px;
	color: #8D8D8F;
}

.shuru {
	position: relative;
	margin-bottom: 10px;
}

.shuru div {
	border: none;
	width: 100%;
	height: 50px;
	font-size: 25px;
	line-height: 50px;
	border-bottom: 1px solid #CFCFCF;
	text-indent: 30px;
	outline: none;
	white-space: pre;
	overflow-x: scroll;
}

.shuru span {
	position: absolute;
	top: 5px;
	font-size: 25px;
}

.submit {
	display: block;
	margin: 20px auto 0;
	width: 90%;
	height: 40px;
	font-size: 16px;
	color:#fff ;
	background:green;
	border-radius: 5px;
}

/* 键盘 */
.form_edit {
	width: 100%;
	background: #D1D4DD;
}

.form_edit>div {
	margin-bottom: 2px;
	margin-right: 0.5%;
	float: left;
	width: 33%;
	height: 45px;
	text-align: center;
	color: #333;
	line-height: 45px;
	font-size: 18px;
	font-weight: 600;
	background-color: #fff;
	border-radius: 5px;
}

.form_edit>div:nth-child(3n) {
	margin-right: 0;
}

.form_edit>div:last-child {
	background-color: #DEE1E9;
}
</style>
</head>
<body>
	<div>
		<form action="" class="edit_cash">
			<p>预存金额</p>
			<div class="shuru">
				<span>&yen;</span>
				<div id="money"></div>
			</div>
		</form>
		<input type="submit" value="确认金额" class="submit" onclick="submit()" />
	</div>
	<div class="layer"></div>
	<div class="layer-content">
		<div class="form_edit clearfix">
			<div class="num">1</div>
			<div class="num">2</div>
			<div class="num">3</div>
			<div class="num">4</div>
			<div class="num">5</div>
			<div class="num">6</div>
			<div class="num">7</div>
			<div class="num">8</div>
			<div class="num">9</div>
			<div class="num">.</div>
			<div class="num">0</div>
			<div id="remove">删除</div>
		</div>
	</div>
	<script src="../js/jquery-2.0.3.js"></script>
	<script>	
		function submit(){
			var	Money=document.getElementById("money").innerHTML;	
			 console.log(Money);
			 if (isMoney(Money)==false){
				 alert("请输入正确的购买金额！");
			 }else{
				var	 url= "http://weixinsdpt.applinzi.com/jsapi.jsp?openid=<%=wecharNo%>&totlemoney="+ Money;
				window.location.href = url;
			}
		}
		function isMoney(s) {
			var regu =/^(([1-9][0-9]*)|(([0]\.\d{1,2}|[1-9][0-9]*\.\d{1,2})))$/;
			var re = new RegExp(regu);
			if (re.test(s)) {
				return true;
			} else {
				return false;
			}
		}
		$(function() {		
			$('.shuru').click(function(e) {
				$('.layer-content').animate({
					bottom : 0
				}, 200);
				e.stopPropagation();
			});
			 $('.wrap').click(function() {
				$('.layer-content').animate({
					bottom : '-200px'
				}, 200);
			}); 
			$('.form_edit .num').click(function() {
				var oDiv = document.getElementById("money");
				oDiv.innerHTML += this.innerHTML;
			});
			$('#remove').click(function() {
				var oDiv = document.getElementById("money");
				var oDivHtml = oDiv.innerHTML;
				oDiv.innerHTML = oDivHtml.substring(0, oDivHtml.length - 1);
			});
		});
	</script>
</body>
</html>

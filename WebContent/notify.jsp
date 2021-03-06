<%@ page language="java" contentType="text/html; charset=GBK"
		 pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<%@ page import="org.course.service.*"%>
<%@ page import="java.sql.SQLException" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<base href="<%=basePath%>">
<title>用户缴费结果通知</title>
<link rel="stylesheet" href="../css/weui.css" />
<link rel="stylesheet" href="../css/example.css" />
<meta name="viewport" content="width=device-width, initial-scale=1" />
<meta name="apple-mobile-web-app-capable" content="yes" />
<meta name="apple-mobile-web-app-status-bar-style" content="black" />
<style>
.page__hd {
	padding: 40px;
}

.weui-form-preview__hd .weui-form-preview__value {
	font-style: normal;
	font-size: 1.6em;
}

.weui-form-preview__value {
	display: block;
	overflow: hidden;
	word-break: normal;
	word-wrap: break-word;
}

.weui-form-preview__value {
	display: block;
	overflow: hidden;
	word-break: normal;
	word-wrap: break-word;
}

.weui-form-preview__label {
	float: left;
	margin-right: 1em;
	min-width: 4em;
	color: #808080;
	text-align: justify;
	text-align-last: justify;
}

.weui-form-preview__value {
	display: block;
	overflow: hidden;
	word-break: normal;
	word-wrap: break-word;
}

.weui-form-preview__label {
	float: left;
	margin-right: 1em;
	min-width: 4em;
	color: #808080;
	text-align: justify;
	text-align-last: justify;
}

.weui-form-preview__value {
	display: block;
	overflow: hidden;
	word-break: normal;
	word-wrap: break-word;
}

</style>

<script language="javascript">
	function closewindows() {
		window.opener = null;
		window.open('', '_self');
		window.close();
	};
</script>

</head>

<body>
	<%
		String openid = request.getParameter("openid");
	%>
	<%
		String orderId = request.getParameter("orderId");
	%>
	<%
		String totlemoney = request.getParameter("totlemoney");
	%>
	<%
		ManageService ManageService = new ManageService();
		try {
			ManageService.WechatDeposit(openid, Double.parseDouble(totlemoney),
					orderId);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	%>
	
	<div class="page__hd">
		<h1 class="page__title">缴费成功</h1>
	</div>
	<div class="weui-form-preview">
		<div class="weui-form-preview__hd">
			<div class="weui-form-preview__item">
				<label class="weui-form-preview__label">支付金额</label> <em
					class="weui-form-preview__value">￥<%=totlemoney%></em>
			</div>
		</div>
		<div class="weui-form-preview__bd">
			<div class="weui-form-preview__item">
				<label class="weui-form-preview__label">支付ID</label> <span
					class="weui-form-preview__value"><%=openid%></span>
			</div>
			<div class="weui-form-preview__item">
				<label class="weui-form-preview__label">订单号</label> <span
					class="weui-form-preview__value"><%=orderId%></span>
			</div>
		</div>
	</div>
</body>
</html>

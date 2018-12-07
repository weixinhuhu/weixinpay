<%@ page language="java" contentType="text/html; charset=GBK"
         pageEncoding="UTF-8" %>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://"
            + request.getServerName() + ":" + request.getServerPort()
            + path + "/";
%>
<%@ page import="org.course.service.*" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
    <base href="<%=basePath%>">
    <title>用户缴费结果通知</title>
    <meta http-equiv="pragma" content="no-cache">
    <meta http-equiv="cache-control" content="no-cache">
    <meta http-equiv="expires" content="0">
    <meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
    <meta http-equiv="description" content="This is my page">

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
    ManageService.WechatWriteoff(openid, Double.parseDouble(totlemoney), 0, orderId);
%>
<div
        style="text-align: center; width: 100%; height: 100%; margin: 0px;">
    <br> 支付ID：<%=openid%>
    <br> 订单号：<%=orderId%>
    <br> 支付金额：<%=totlemoney%>
    <br> 支付成功
</div>
<br>
</body>
</html>

<%@ page language="java" contentType="text/html; charset=GBK"
		 pageEncoding="UTF-8"%>
<%@ page import="org.course.service.*"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<link rel="stylesheet" href="css/weui.css" />
<link rel="stylesheet" href="css/example.css" />
<meta name="viewport" content="width=device-width, initial-scale=1" />
<meta name="apple-mobile-web-app-capable" content="yes" />
<meta name="apple-mobile-web-app-status-bar-style" content="black" />
<head>
<base href="<%=basePath%>">
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>账户绑定结果</title>

</head>
<%
	String wecharNo = request.getParameter("wecharNo");
%>
<%
	String meterInfo = request.getParameter("meterInfo");
%>
<%
	String userPhone = request.getParameter("userPhone");
%>
<%
	String flag = request.getParameter("flag");
%>
<%
	String res;
%>
<body>
	<%
		try {
			ManageService ManageService = new ManageService();
			res = ManageService.WechatRelation(Integer.parseInt(flag), Integer.parseInt(meterInfo), userPhone,
					wecharNo);
			if (res == "0") {
				out.print("恭喜，操作成功！");
			} else {
				out.print("抱歉，操作失败！  "+ "\n"+"错误原因:  " +res);
			}
		} catch (Exception e) {
			out.println(e.toString());
		}
	%>
</body>
</html>
<%@page import="javax.servlet.jsp.tagext.TryCatchFinally"%>
<%@ page language="java" contentType="text/html; charset=GBK"
	pageEncoding="GBK"%>
<%@ page import="org.weixin.pay.*"%>
<%@ page import="org.pay.utils.*"%>
<%@ page import="org.utils.http.*"%>
<%@ page import="org.course.service.*"%>
<html>
<%
	String wecharNo = "";
	String StrPay = "";
	String appId = "";
	String timeStamp = "";
	String nonceStr = "";
	String prepay_id = "";
	String paySign = "";
	String orderId = "";
	Integer flag = 0;
	//������1��
	String Money="0.00";  
%>
<%  wecharNo=request.getParameter("openid"); %>
<%  Money=request.getParameter("totlemoney");%>
<%
	try {
		WxFinaPackage WxFinaPackage = new WxFinaPackage();
		StrPay = WxFinaPackage.getFinaPackage(wecharNo,Money);
		String[] paylist = new String[5];
		paylist = StrPay.split(",");
		appId = paylist[0];
		timeStamp = paylist[1];
		nonceStr = paylist[2];
		prepay_id = paylist[3];
		paySign = paylist[4];
		orderId = paylist[5];
	} catch (Exception e) {
		out.println(e.toString());
	}
%>

<head>
<script language="javascript">
	function callpay() {
		WeixinJSBridge.invoke('getBrandWCPayRequest', {
			 "appId" : "<%=appId%>",
			 "timeStamp" : "<%=timeStamp%>", 
			 "nonceStr" : "<%=nonceStr%>", 
			 "package" : "<%=prepay_id%>",
			"signType" : "MD5", 
			"paySign" : "<%=paySign%>"
						},
						function(res) {
							if (res.err_msg == 'get_brand_wcpay_request:ok') {
								alert('��ϲ����֧���ɹ�!');							
								window.location.href = "http://weixinsdpt.applinzi.com/notify_1.jsp?openid=<%=wecharNo%>&orderId=<%=orderId%>&totlemoney=<%=Money%>";
							} else {
								alert('֧��ʧ��');
							}
						})
	}
</script>
<title>΢��֧��</title>
</head>
<body>
	<div
		style="text-align: center; width: 100%; height: 100%; margin: 0px;">
		<br> �����ţ�<%=orderId%>
		<br>�û�΢�źţ�<%=wecharNo%>
		<br>��ɷѽ�<%=Money%>	
		<br>
		<br>
		<button
			style="font-size: 2em; width: 70%; height: 10%; background-color: green"
			class="  ub-ac bc-text-head  bc-btn " onclick="callpay()">֧���˵�</button>
	</div>
</body>
</html>

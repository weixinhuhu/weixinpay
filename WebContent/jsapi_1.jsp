<%@ page language="java" contentType="text/html; charset=GBK"
         pageEncoding="UTF-8" %>
<%@ page import="org.weixin.pay.*" %>

<html>
<%
    String wecharNo;
    String StrPay;
    String appId = "";
    String timeStamp = "";
    String nonceStr = "";
    String prepay_id = "";
    String paySign = "";
    String orderId = "";
    String Money;
%>
<% wecharNo = request.getParameter("openid"); %>
<% Money = request.getParameter("totlemoney");%>
<%
    try {
        WxFinaPackage WxFinaPackage = new WxFinaPackage();
        StrPay = WxFinaPackage.getFinaPackage(wecharNo, Money);
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
                    "appId": "<%=appId%>",
                    "timeStamp": "<%=timeStamp%>",
                    "nonceStr": "<%=nonceStr%>",
                    "package": "<%=prepay_id%>",
                    "signType": "MD5",
                    "paySign": "<%=paySign%>"
                },
                function (res) {
                    if (res.err_msg == 'get_brand_wcpay_request:ok') {
                        alert('恭喜您，支付成功!');
                        window.location.href = "http://weixinsdpt.applinzi.com/notify_1.jsp?openid=<%=wecharNo%>&orderId=<%=orderId%>&totlemoney=<%=Money%>";
                    } else {
                        alert('支付失败');
                    }
                })
        }
    </script>
    <title>微信支付</title>
</head>
<body>
<div
        style="text-align: center; width: 100%; height: 100%; margin: 0px;">
    <br> 订单号：<%=orderId%>
    <br>用户微信号：<%=wecharNo%>
    <br>需缴费金额：<%=Money%>
    <br>
    <br>
    <button
            style="font-size: 2em; width: 70%; height: 10%; background-color: green"
            class="  ub-ac bc-text-head  bc-btn " onclick="callpay()">支付账单
    </button>
</div>
</body>
</html>

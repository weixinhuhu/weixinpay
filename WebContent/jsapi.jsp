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
<%
    wecharNo = request.getParameter("openid");
%>
<%
    Money = request.getParameter("totlemoney");
%>
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
    <title>微信支付</title>
    <link rel="stylesheet" href="../css/weui.css"/>
    <link rel="stylesheet" href="../css/example.css"/>
    <meta name="viewport" content="width=device-width, initial-scale=1"/>
    <meta name="apple-mobile-web-app-capable" content="yes"/>
    <meta name="apple-mobile-web-app-status-bar-style" content="black"/>
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

        .weui-form-preview__btn_primary {
            color: #09BB07;
        }

        .weui-form-preview__btn {
            position: relative;
            display: block;
            -webkit-box-flex: 1;
            -webkit-flex: 1;
            flex: 1;
            color: #09BB07;
            text-align: center;
            -webkit-tap-highlight-color: rgba(0, 0, 0, 0);
        }

        .weui-form-preview__ft {
            position: relative;
            line-height: 50px;
            display: -webkit-box;
            display: -webkit-flex;
            display: flex;
        }

    </style>
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
                        /* alert('恭喜您，支付成功!');		 */
                        window.location.href = "http://weixinsdpt.applinzi.com/notify.jsp?openid=<%=wecharNo%>&orderId=<%=orderId%>&totlemoney=<%=Money%>";
                    } else {
                        alert('支付失败');
                    }
                });
        }
    </script>

</head>
<body>
<div class="page__hd">
    <h1 class="page__title">确认缴费</h1>
</div>
<div class="weui-form-preview">
    <div class="weui-form-preview__hd">
        <div class="weui-form-preview__item">
            <label class="weui-form-preview__label">付款金额</label> <em
                class="weui-form-preview__value">￥<%=Money%>
        </em>
        </div>
    </div>
    <div class="weui-form-preview__bd">
        <div class="weui-form-preview__item">
            <label class="weui-form-preview__label">商品名称</label> <span
                class="weui-form-preview__value">预存水费</span>
        </div>
        <div class="weui-form-preview__item">
            <label class="weui-form-preview__label">订单号</label> <span
                class="weui-form-preview__value"><%=orderId%></span>
        </div>
        <div class="weui-form-preview__ft">
            <a class="weui-form-preview__btn weui-form-preview__btn_primary"
               onclick="callpay()">支付</a>
        </div>
    </div>
</div>
</body>
</html>

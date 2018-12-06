package org.weixin.pay;

import java.util.SortedMap;
import java.util.TreeMap;

import org.pay.utils.GetWxOrderno;
import org.pay.utils.RequestHandler;
import org.pay.utils.Sha1Util;
import org.pay.utils.TenpayUtil;

/**
 * @author ex_yangxiaoyi
 * 
 */
public class WxFinaPackage {
	// ΢��֧���̻���ͨ�� ΢�Ż��ṩappid��appsecret���̻���partner
	private static String appid = "wx31ac720aa5838480";
	private static String appsecret = "6820b4f14d6e3f8afafe094e99533a24";
	private static String partner = "1480061982";
	// �������partnerkey�����̻���̨���õ�һ��32λ��key,΢���̻�ƽ̨-�˻�����-��ȫ����-api��ȫ
	private static String partnerkey = "WXabik5OnJ8rRjvijSl1PQfkLXPPTG6E";
	// openId ��΢���û���Թ��ںŵı�ʶ����Ȩ�Ĳ������ﲻ����
	private static String openId = "o_-0ls1cQ41FoAMz_9xH-ZgV-LMI";
	// ΢��֧���ɹ���֪ͨ��ַ ����Ҫ��80�˿ڲ��ҵ�ַ���ܴ�����
	private static String notifyurl = "http://weixinsdpt.applinzi.com/"; // Key

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		//����
		System.out.println(getFinaPackage(openId,"0.01"));
	}

	public static  String getFinaPackage(String wechatNo,String Money) {
		// ΢��֧��jsApi
		WxPayDto tpWxPay = new WxPayDto();
		tpWxPay.setOpenId(wechatNo);
		tpWxPay.setBody("����ˮ��");
		tpWxPay.setOrderId(getNonceStr());
		tpWxPay.setSpbillCreateIp("127.0.0.1");
		tpWxPay.setTotalFee(Money);
		return getPackage(tpWxPay);
	}

	/**
	 * ��ȡ����Ԥ֧��id����
	 * 
	 * @return
	 */
	@SuppressWarnings("static-access")
	public static String getPackage(WxPayDto tpWxPayDto) {
		String openId = tpWxPayDto.getOpenId();
		// 1 ����
		// ������
		String orderId = tpWxPayDto.getOrderId();
		// �������� ԭ������
		String attach = "";
		// �ܽ���Է�Ϊ��λ������С����
		String totalFee = getMoney(tpWxPayDto.getTotalFee());
		// �������ɵĻ��� IP
		String spbill_create_ip = tpWxPayDto.getSpbillCreateIp();
		// ����notify_url�� ֧����ɺ�΢�ŷ�����������Ϣ�������жϻ�Ա�Ƿ�֧���ɹ����ı䶩��״̬�ȡ�
		String notify_url = notifyurl;
		String trade_type = "JSAPI";
		// ---�������
		// �̻���
		String mch_id = partner;
		// ����ַ���
		String nonce_str = getNonceStr();
		// ��Ʒ������������޸�
		String body = tpWxPayDto.getBody();
		// �̻�������
		String out_trade_no = orderId;
		SortedMap<String, String> packageParams = new TreeMap<String, String>();
		packageParams.put("appid", appid);
		packageParams.put("mch_id", mch_id);
		packageParams.put("nonce_str", nonce_str);
		packageParams.put("body", body);
		packageParams.put("attach", attach);
		packageParams.put("out_trade_no", out_trade_no);
		// ����д�Ľ��Ϊ1 �ֵ�ʱ�޸�
		packageParams.put("total_fee", totalFee);
		packageParams.put("spbill_create_ip", spbill_create_ip);
		packageParams.put("notify_url", notify_url);
		packageParams.put("trade_type", trade_type);
		packageParams.put("openid", openId);
		RequestHandler reqHandler = new RequestHandler(null, null);
		reqHandler.init(appid, appsecret, partnerkey);
		String sign = reqHandler.createSign(packageParams);
		String xml = "<xml>" + "<appid>" + appid + "</appid>" + "<mch_id>"
				+ mch_id + "</mch_id>" + "<nonce_str>" + nonce_str
				+ "</nonce_str>" + "<sign>" + sign + "</sign>"
				+ "<body><![CDATA[" + body + "]]></body>" + "<out_trade_no>"
				+ out_trade_no + "</out_trade_no>" + "<attach>" + attach
				+ "</attach>" + "<total_fee>" + totalFee + "</total_fee>"
				+ "<spbill_create_ip>" + spbill_create_ip
				+ "</spbill_create_ip>" + "<notify_url>" + notify_url
				+ "</notify_url>" + "<trade_type>" + trade_type
				+ "</trade_type>" + "<openid>" + openId + "</openid>"
				+ "</xml>";
		String prepay_id = "";
		String createOrderURL = "https://api.mch.weixin.qq.com/pay/unifiedorder";
		prepay_id = new GetWxOrderno().getPayNo(createOrderURL, xml);
		// ��ȡprepay_id��ƴ���������֧������Ҫ��package
		SortedMap<String, String> finalpackage = new TreeMap<String, String>();
		String timestamp = Sha1Util.getTimeStamp();
		String packages = "prepay_id=" + prepay_id;
		finalpackage.put("appId", appid);
		finalpackage.put("timeStamp", timestamp);
		finalpackage.put("nonceStr", nonce_str);
		finalpackage.put("package", packages);
		finalpackage.put("signType", "MD5");
		// Ҫǩ��
		String finalsign = reqHandler.createSign(finalpackage);			
		finalpackage.put("paySign", finalsign);
		
//		String finaPackage = "\"appId\":\"" + appid + "\",\"timeStamp\":\""
//			+ timestamp + "\",\"nonceStr\":\"" + nonce_str
//			+ "\",\"package\":\"" + packages + "\",\"signType\" : \"MD5"
//				+ "\",\"paySign\":\"" + finalsign + "\"";		
		
		String finaPackage = appid+","+timestamp+","+nonce_str+","+packages+","+finalsign+","+orderId+",";
		return finaPackage;
	}
	/**
	 * ��ȡ����ַ���
	 * 
	 * @return
	 */
	public static String getNonceStr() {
		// �����
		String currTime = TenpayUtil.getCurrTime();
		// 8λ����
		String strTime = currTime.substring(8, currTime.length());
		// ��λ�����
		String strRandom = TenpayUtil.buildRandom(4) + "";
		// 10λ���к�,�������е�����
		return strTime + strRandom;
	}

	/**
	 * Ԫת���ɷ�
	 * 
	 * @param money
	 * @return
	 */
	public static String getMoney(String amount) {
		if (amount == null) {
			return "";
		}
		// ���ת��Ϊ��Ϊ��λ
		String currency = amount.replaceAll("\\$|\\��|\\,", ""); // �������, ��
																// ����$�Ľ��
		int index = currency.indexOf(".");
		int length = currency.length();
		Long amLong = 0l;
		if (index == -1) {
			amLong = Long.valueOf(currency + "00");
		} else if (length - index >= 3) {
			amLong = Long.valueOf((currency.substring(0, index + 3)).replace(
					".", ""));
		} else if (length - index == 2) {
			amLong = Long.valueOf((currency.substring(0, index + 2)).replace(
					".", "") + 0);
		} else {
			amLong = Long.valueOf((currency.substring(0, index + 1)).replace(
					".", "") + "00");
		}
		return amLong.toString();
	}
}

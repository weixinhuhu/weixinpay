package org.pay.utils;




import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.SortedMap;
import java.util.TreeMap;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;



/*
 '微信支付服务器签名支付请求请求类
 '============================================================================
 'api说明�?
 'init(app_id, app_secret, partner_key, app_key);
 '初始化函数，默认给一些参数赋值，如cmdno,date等�?
 'setKey(key_)'设置商户密钥
 'getLasterrCode(),获取�?��错误�?
 'GetToken();获取Token
 'getTokenReal();Token过期后实时获取Token
 'createMd5Sign(signParams);生成Md5签名
 'genPackage(packageParams);获取package�?
 'createSHA1Sign(signParams);创建签名SHA1
 'sendPrepay(packageParams);提交预支�?
 'getDebugInfo(),获取debug信息
 '============================================================================
 '*/
public class RequestHandler {
	/** Token获取网关地址地址 */
	private String tokenUrl;
	/** 预支付网关url地址 */
	private String gateUrl;
	/** 查询支付通知网关URL */
	private String notifyUrl;
	/** 商户参数 */
	private String appid;
	private String appkey;
	private String partnerkey;
	private String appsecret;
	private String key;
	/** 请求的参�?*/
	private SortedMap parameters;
	/** Token */
	private String Token;
	private String charset;
	/** debug信息 */
	private String debugInfo;
	private String last_errcode;

	private HttpServletRequest request;

	private HttpServletResponse response;

	/**
	 * 初始构�?函数�?
	 * 
	 * @return
	 */
	public RequestHandler(HttpServletRequest request,
			HttpServletResponse response) {
		this.last_errcode = "0";
		this.request = request;
		this.response = response;
		//this.charset = "GBK";
		this.charset = "UTF-8";
		this.parameters = new TreeMap();
		// 验证notify支付订单网关
		notifyUrl = "https://gw.tenpay.com/gateway/simpleverifynotifyid.xml";
		
	}

	/**
	 * 初始化函数�?
	 */
	public void init(String app_id, String app_secret,	String partner_key) {
		this.last_errcode = "0";
		this.Token = "token_";
		this.debugInfo = "";
		this.appid = app_id;
		this.partnerkey = partner_key;
		this.appsecret = app_secret;
		this.key = partner_key;
	}

	public void init() {
	}

	/**
	 * 获取�?��错误�?
	 */
	public String getLasterrCode() {
		return last_errcode;
	}

	/**
	 *获取入口地址,不包含参数�?
	 */
	public String getGateUrl() {
		return gateUrl;
	}

	/**
	 * 获取参数�?
	 * 
	 * @param parameter
	 *            参数名称
	 * @return String
	 */
	public String getParameter(String parameter) {
		String s = (String) this.parameters.get(parameter);
		return (null == s) ? "" : s;
	}

	
	 //设置密钥
	
	public void setKey(String key) {
		this.partnerkey = key;
	}
	//设置微信密钥
	public void  setAppKey(String key){
		this.appkey = key;
	}
	
	// 特殊字符处理
	public String UrlEncode(String src) throws UnsupportedEncodingException {
		return URLEncoder.encode(src, this.charset).replace("+", "%20");
	}

	// 获取package的签名包
	public String genPackage(SortedMap<String, String> packageParams)
			throws UnsupportedEncodingException {
		String sign = createSign(packageParams);

		StringBuffer sb = new StringBuffer();
		Set es = packageParams.entrySet();
		Iterator it = es.iterator();
		while (it.hasNext()) {
			Map.Entry entry = (Map.Entry) it.next();
			String k = (String) entry.getKey();
			String v = (String) entry.getValue();
			sb.append(k + "=" + UrlEncode(v) + "&");
		}

		// 去掉�?���?��&
		String packageValue = sb.append("sign=" + sign).toString();
//		System.out.println("UrlEncode�?packageValue=" + packageValue);
		return packageValue;
	}

	/**
	 * 创建md5摘要,规则�?按参数名称a-z排序,遇到空�?的参数不参加签名�?
	 */
	public String createSign(SortedMap<String, String> packageParams) {
		StringBuffer sb = new StringBuffer();
		Set es = packageParams.entrySet();
		Iterator it = es.iterator();
		while (it.hasNext()) {
			Map.Entry entry = (Map.Entry) it.next();
			String k = (String) entry.getKey();
			String v = (String) entry.getValue();
			if (null != v && !"".equals(v) && !"sign".equals(k)
					&& !"key".equals(k)) {
				sb.append(k + "=" + v + "&");
			}
		}
		sb.append("key=" + this.getKey());
		String sign = MD5Util.MD5Encode(sb.toString(), this.charset)
				.toUpperCase();
		return sign;

	}
	/**
	 * 创建package签名
	 */
	public boolean createMd5Sign(String signParams) {
		StringBuffer sb = new StringBuffer();
		Set es = this.parameters.entrySet();
		Iterator it = es.iterator();
		while (it.hasNext()) {
			Map.Entry entry = (Map.Entry) it.next();
			String k = (String) entry.getKey();
			String v = (String) entry.getValue();
			if (!"sign".equals(k) && null != v && !"".equals(v)) {
				sb.append(k + "=" + v + "&");
			}
		}

		// 算出摘要
		String enc = TenpayUtil.getCharacterEncoding(this.request,
				this.response);
		String sign = MD5Util.MD5Encode(sb.toString(), enc).toLowerCase();

		String tenpaySign = this.getParameter("sign").toLowerCase();

		// debug信息
		this.setDebugInfo(sb.toString() + " => sign:" + sign + " tenpaySign:"
				+ tenpaySign);

		return tenpaySign.equals(sign);
	}

	

    //输出XML
	   public String parseXML() {
		   StringBuffer sb = new StringBuffer();
	       sb.append("<xml>");
	       Set es = this.parameters.entrySet();
	       Iterator it = es.iterator();
	       while(it.hasNext()) {
				Map.Entry entry = (Map.Entry)it.next();
				String k = (String)entry.getKey();
				String v = (String)entry.getValue();
				if(null != v && !"".equals(v) && !"appkey".equals(k)) {
					
					sb.append("<" + k +">" + getParameter(k) + "</" + k + ">\n");
				}
			}
	       sb.append("</xml>");
			return sb.toString();
		}

	/**
	 * 设置debug信息
	 */
	protected void setDebugInfo(String debugInfo) {
		this.debugInfo = debugInfo;
	}
	public void setPartnerkey(String partnerkey) {
		this.partnerkey = partnerkey;
	}
	public String getDebugInfo() {
		return debugInfo;
	}
	public String getKey() {
		return key;
	}

}

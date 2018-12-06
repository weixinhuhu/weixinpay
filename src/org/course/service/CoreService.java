package org.course.service;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import org.course.message.resp.Article;
import org.course.message.resp.NewsMessage;
import org.course.message.resp.TextMessage;
import org.course.util.MessageUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 核心服务类
 * 
 * 
 * @date 2013-10-17
 */
public class CoreService {
	private static Logger log = (Logger) LoggerFactory
			.getLogger(CoreService.class);
	public static String fromUserName;
	public static String toUserName;
	public static String respXml;
	public static String usernum = "";
	public static int num;

	/**
	 * 处理微信发来的请求
	 * 
	 * @param request
	 * @return xml
	 * @throws SQLException
	 */
	public static String processRequest(HttpServletRequest request) {
		// xml格式的消息数据
		respXml = null;
		String SendMsg = "";
		ManageService ms = new ManageService();
		try {
			// 调用parseXml方法解析请求消息
			Map<String, String> requestMap = MessageUtil.parseXml(request);
			// 发送方帐号
			fromUserName = requestMap.get("FromUserName");
			// 开发者微信号
			toUserName = requestMap.get("ToUserName");
			// 消息类型
			String msgType = requestMap.get("MsgType");
			TextMessage textMessage = new TextMessage();
			textMessage.setToUserName(fromUserName);
			textMessage.setFromUserName(toUserName);
			textMessage.setCreateTime(new Date().getTime());
			textMessage.setMsgType(MessageUtil.RESP_MESSAGE_TYPE_TEXT);

			// 欢迎提示
			textMessage.setContent("欢迎关注融通高科水电平台公众号" + "\n"
					+ "为方便您查询缴费，请先做水表档案与微信号的绑定操作！" + "\n"
					+ "具体操作流程请参照用水服务中的账户绑定菜单");
			respXml = MessageUtil.messageToXml(textMessage);

			if (msgType.equals(MessageUtil.REQ_MESSAGE_TYPE_EVENT)) {
				// 事件类型
				String eventType = requestMap.get("Event");
				// 自定义菜单点击事件
				if (eventType.equals(MessageUtil.EVENT_TYPE_CLICK)) {
					// 事件KEY值，与创建菜单时的key值对应
					String eventKey = requestMap.get("EventKey");
					// 根据key值判断用户点击的按钮
					switch (eventKey) {
					case "12":// 预付费缴费记录查询
						SendMsg = ms.WechatQryDepositFlow(fromUserName);
						newsMessage(SendMsg, "预付费缴费记录查询", "");
						break;
					case "13":// 余额查询
						SendMsg = ms.QureyUserMoney(fromUserName);
						newsMessage(SendMsg + "\n" + "\n" + "预存水费", "余额查询",
								"http://weixinsdpt.applinzi.com/index.jsp?openid="
										+ fromUserName);
						break;
					case "15":// 后付费缴费记录查询
						SendMsg = ms.WechatQryWriteoffFlow(fromUserName);
						newsMessage(SendMsg, "后付费缴费记录查询", "");
						break;
					case "21":// 账单查询
						SendMsg = ms.WechatQryBill(fromUserName);
						String totlemoney = ms.GetTotleMoney(fromUserName);
						String url = "";
						if (SendMsg.equals("账单未生成，请稍后再试")) {
							url = "";
						} else {
							url = "http://weixinsdpt.applinzi.com/jsapi_1.jsp?openid="
									+ fromUserName + "&totlemoney" + totlemoney;
							SendMsg = SendMsg + "\n" + "\n" + "去缴费";
						}
						newsMessage(SendMsg, "账单详情", url);
						break;
					case "31":// 账户绑定
						newsMessage("操作提示" + "\n" + "如需绑定用水档案请输入bd#手机号#用户编号"
								+ "\n" + "如需解绑用水档案请输入jb#手机号#用户编号", "账号绑定操作提示",
								"");
						break;
					case "32":// 客户档案查询
						SendMsg = ms.getUseInfo(fromUserName);
						newsMessage(SendMsg, "客户档案查询", "");
						break;
					case "33":// 水价查询
						SendMsg = ms.WechatQryPrice();
						newsMessage(SendMsg, "水价详情", "");
						break;
					}
				}
			} else {
				// 获取接收到的消息内容
				String resmessage = requestMap.get("Content").toUpperCase()
						.trim();
				String str[] = resmessage.split("#");
				if (str.length == 3) {
					if (str[0].equals("BD")) {
						SendMsg = ms.WechatRelation(0,
								Integer.parseInt(str[2]), str[1], fromUserName);
						textMessage.setContent(SendMsg);
					}
					if (str[0].equals("JB")) {
						SendMsg = ms.WechatRelation(1,
								Integer.parseInt(str[2]), str[1], fromUserName);
						textMessage.setContent(SendMsg);
					}
				}
				respXml = MessageUtil.messageToXml(textMessage);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return respXml;
	}

	/**
	 * newsMessage封装
	 * 
	 * @param sendMsg
	 */
	private static void newsMessage(String sendMsg, String StrTitle, String url) {
		Article article = new Article();
		article.setTitle(StrTitle);
		article.setDescription(sendMsg);
		article.setPicUrl("");
		article.setUrl(url);
		List<Article> articleList = new ArrayList<Article>();
		articleList.add(article);
		// 创建图文消息
		NewsMessage newsMessage = new NewsMessage();
		newsMessage.setToUserName(fromUserName);
		newsMessage.setFromUserName(toUserName);
		newsMessage.setCreateTime(new Date().getTime());
		newsMessage.setMsgType(MessageUtil.RESP_MESSAGE_TYPE_NEWS);
		newsMessage.setArticleCount(articleList.size());
		newsMessage.setArticles(articleList);
		respXml = MessageUtil.messageToXml(newsMessage);
	}
}
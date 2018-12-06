package org.weixin.main;

import org.course.menu.Button;
import org.course.menu.ClickButton;
import org.course.menu.ComplexButton;
import org.course.menu.Menu;
import org.course.menu.ViewButton;
import org.course.pojo.Token;
import org.course.util.CommonUtil;
import org.course.util.MenuUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 菜单管理器类
 * 
 * @date 2016-6-17
 */
public class MenuManager {
	private static Logger log = LoggerFactory.getLogger(MenuManager.class);

	/**
	 * 定义菜单结构
	 * 
	 * @return
	 */
	private static Menu getMenu() {
		
//		ClickButton btn11 = new ClickButton();
//		btn11.setName("预付费缴费");
//		btn11.setType("click");
//		btn11.setKey("11");
		
//		ViewButton btn11 = new ViewButton();
//		btn11.setName("预付费缴费");
//		btn11.setType("view");
//		btn11.setUrl("http://weixinsdpt.applinzi.com/jsapi.jsp");

		ClickButton btn12 = new ClickButton();
		btn12.setName("预付费缴费记录查询");
		btn12.setType("click");
		btn12.setKey("12");

		ClickButton btn13 = new ClickButton();
		btn13.setName("余额查询");
		btn13.setType("click");
		btn13.setKey("13");

//		ClickButton btn14 = new ClickButton();
//		btn14.setName("后付费缴费");
//		btn14.setType("click");
//		btn14.setKey("14");
		
//		ViewButton btn14 = new ViewButton();
//		btn14.setName("后付费缴费");
//		btn14.setType("view");
//		btn14.setUrl("http://weixinsdpt.applinzi.com/jsapi.jsp");

		ClickButton btn15 = new ClickButton();
		btn15.setName("后付费缴费记录查询");
		btn15.setType("click");
		btn15.setKey("15");

		ClickButton btn21 = new ClickButton();
		btn21.setName("账单查询");
		btn21.setType("click");
		btn21.setKey("21");

		ClickButton btn31 = new ClickButton();
		btn31.setName("账户绑定");
		btn31.setType("click");
		btn31.setKey("31");

		ClickButton btn32 = new ClickButton();
		btn32.setName("客户档案查询");
		btn32.setType("click");
		btn32.setKey("32");

		ClickButton btn33 = new ClickButton();
		btn33.setName("水价信息");
		btn33.setType("click");
		btn33.setKey("33");

		ComplexButton mainBtn1 = new ComplexButton();
		mainBtn1.setName("充值缴费");
		//mainBtn1.setSub_button(new Button[] { btn11, btn12, btn13, btn14, btn15 });
		mainBtn1.setSub_button(new Button[] {  btn12, btn13, btn15 });
		ComplexButton mainBtn2 = new ComplexButton();
		mainBtn2.setName("我的用水");
		mainBtn2.setSub_button(new Button[] { btn21 });

		ComplexButton mainBtn3 = new ComplexButton();
		mainBtn3.setName("用水服务");
		mainBtn3.setSub_button(new Button[] { btn31, btn32, btn33 });

		Menu menu = new Menu();
		menu.setButton(new Button[] { mainBtn1, mainBtn2, mainBtn3 });

		return menu;
	}

	public static void main(String[] args) {
		// // 第三方用户唯一凭证 融通高科公司账户
		// String appId = "wx31ac720aa5838480";
		
		// // 第三方用户唯一凭证密钥
		// String appSecret = "6820b4f14d6e3f8afafe094e99533a24";

		// // 第三方用户唯一凭证 个人账户 貌似不支持自定义菜单
		// String appId = "wxc8ee3e935b6bb515";
		
		// // 第三方用户唯一凭证密钥
		// String appSecret = "beddb90d51a6aa8627834af252322b25";

		// 第三方用户唯一凭证 融通高科公司账户
		String appId = "wx31ac720aa5838480";
		// 第三方用户唯一凭证密钥
		String appSecret = "6820b4f14d6e3f8afafe094e99533a24";

		// 调用接口获取凭证
		Token token = CommonUtil.getToken(appId, appSecret);

		if (null != token) {
			// 创建菜单
			boolean result = MenuUtil.createMenu(getMenu(),
					token.getAccessToken());

			// 判断菜单创建结果
			if (result)
				log.info("菜单创建成功！");
			else
				log.info("菜单创建失败！");
		}
	}
}

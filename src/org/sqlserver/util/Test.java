package org.sqlserver.util;

import java.sql.SQLException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.course.service.ManageService;

public class Test {
	private static Logger log = LoggerFactory.getLogger(Test.class);

	private static String wechatNo = "o_-0ls1cQ41FoAMz_9xH-ZgV-LMI";// 测试微信账户

	public static void main(String[] args) throws SQLException {
		String SendMsg = "";
		ManageService ms = new ManageService();
//		String str[] = "BD#13833333333#10220001".split("#");
//		if (str.length == 3) {
//			if (str[0].equals("BD")) {
//				SendMsg = ms.WechatRelation(0, Integer.parseInt(str[2]),
//						str[1], wechatNo);
//				log.info("BD");
//			}
//			if (str[0].equals("JB")) {
//				SendMsg = ms.WechatRelation(1, Integer.parseInt(str[2]),
//						str[1], wechatNo);
//				log.info("JB");
//			}
//		}
		
		//	SendMsg=ms.WechatRelation(	1, 10220001, "13833333333", wechatNo);
		// SendMsg=ms.WechatDeposit(wechatNo, 10010);
		 SendMsg=ms.WechatQryPrice();
		//SendMsg=ms.WechatWriteoff(wechatNo,4000,0,"1000000000");		
		//SendMsg=ms.WechatQryWriteoffFlow(wechatNo);
	//	SendMsg=ms.WechatDeposit(wechatNo, 0.01,"1223123311");
	//	SendMsg=ms.WechatRelation(0,18070802,"13812345678",wechatNo);
	//	SendMsg=ms.WechatQryDepositFlow(wechatNo);
		//SendMsg=ms.QureyUserMoney(wechatNo);		
	//	SendMsg=ms.GetTotleMoney(wechatNo);	
		
		if (SendMsg.length() == 0 || SendMsg == null) {
			log.info("-----"+SendMsg+"err");
		} else {
			log.info("-----"+SendMsg+"ok");
		}	
	}
}

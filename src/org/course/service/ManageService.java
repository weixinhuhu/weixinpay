package org.course.service;

import java.sql.ResultSet;
import java.sql.SQLException;
import org.sqlserver.util.SqlUtil;

public class ManageService {

	/**
	 * 通过用水户号 存储过程方式获取结果集
	 * 
	 * @param usernum
	 * @return
	 * @throws SQLException
	 */
	String getProResult(String usernum) throws SQLException {
		SqlUtil sqlutil = new SqlUtil();
		ResultSet rs = null;
		rs = sqlutil.getResult(usernum);
		StringBuffer msg = new StringBuffer();
		try {
			while (rs.next()) {
				msg.append("账户余额：" + rs.getString(3) + "元\n用水户号："
						+ rs.getString(2) + "\n用水户名：" + rs.getString(1)
						+ " \n用户地址：" + rs.getString(4) + "\n更新日期："
						+ rs.getString(5)
						+ "\n账户余额=充值金额-表端已用量*用水单价(用水量以每月账单为准)" + "\n\n\n立即缴费");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return msg.toString();
	}

	/**
	 * 1 绑定与解除
	 * 
	 * @param type
	 *            业务类型0：绑定 1：解绑
	 * @param CustNo
	 *            客户号
	 * @param MobileNo
	 *            手机号
	 * @param WeChatNo
	 *            微信号
	 * @return
	 * @throws SQLException
	 */
	public String WechatRelation(int type, int CustNo, String MobileNo,
			String WeChatNo) throws SQLException {
		SqlUtil sqlutil = new SqlUtil();
		String remsg = sqlutil.Pro_WechatRelation(type, CustNo, MobileNo,
				WeChatNo);
		StringBuffer msg = new StringBuffer();
		if (remsg.equals("0")) {
			msg.append("恭喜您，操作成功！");
		} else {
			msg.append("操作失败，请确认输入的信息是否正确！");
		}
		return msg.toString();
	}

	/**
	 * 2客户查询
	 * 
	 * @param WeChatNo
	 * @return
	 * @throws SQLException
	 */
	public String getUseInfo(String WeChatNo) throws SQLException {
		SqlUtil sqlutil = new SqlUtil();
		ResultSet rs = null;
		rs = sqlutil.Pro_getUserInfoResult(WeChatNo);
		StringBuffer msg = new StringBuffer();
		if (rs == null) {
			msg.append("此微信号码未绑定用户用水档案");
		} else {
			try {
				while (rs.next()) {
					msg.append("客户编号：" + rs.getString(1) + "\n" + "客户姓名："
							+ rs.getString(2) + "\n" + "客户地址："
							+ rs.getString(3) + "\n" + "账务模式："
							+ rs.getString(4));
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return msg.toString();
	}

	/**
	 * 3 水费账户余额查询
	 * 
	 * @param WeChatNo
	 * @return
	 * @throws SQLException
	 */
	public String QureyUserMoney(String WeChatNo) throws SQLException {
		SqlUtil sqlutil = new SqlUtil();
		ResultSet rs = null;
		rs = sqlutil.Por_QueryMoney(WeChatNo);
		StringBuffer msg = new StringBuffer();
		if (rs == null) {
			msg.append("抱歉！未查询到余额信息");
		} else {
			try {
				while (rs.next()) {
					msg.append("客户编号：" + rs.getString(1) + "\n" + "客户姓名："
							+ rs.getString(2) + "\n" + "用水地址："
							+ rs.getString(3) + "\n" + "账务余额："
							+ rs.getString(4) + "\n" + "截止日期："
							+ rs.getString(5));
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return msg.toString();
	}

	/**
	 * 4 预付费缴费
	 * 
	 * @param WeChatNo
	 *            微信号
	 * @param money
	 *            缴费金额
	 * @return
	 * @throws SQLException
	 */
	public String WechatDeposit(String WeChatNo, Double money, String orderId)
			throws SQLException {
		SqlUtil sqlutil = new SqlUtil();
		String remsg = sqlutil.Pro_WechatDeposit(WeChatNo, money, orderId);
		StringBuffer msg = new StringBuffer();
		msg.append(remsg);
		return msg.toString();
	}

	/**
	 * 5 预付费缴费记录查询
	 * 
	 * @param WeChatNo
	 * @return
	 * @throws SQLException
	 */
	public String WechatQryDepositFlow(String WeChatNo) throws SQLException {
		SqlUtil sqlutil = new SqlUtil();
		ResultSet rs = null;
		rs = sqlutil.Por_WechatQryDepositFlow(WeChatNo);
		StringBuffer msg = new StringBuffer();
		if (rs == null) {
			msg.append("抱歉！未查询到近六个月内预付费缴费记录");
		} else {
			try {
				while (rs.next()) {
					msg.append("\n" + "客户编号：" + rs.getString(1) + "\n"
							+ "客户	小区：" + rs.getString(2) + "\n" + "用户楼栋："
							+ rs.getString(3) + "\n" + "用户姓名："
							+ rs.getString(4) + "\n" + "用户地址："
							+ rs.getString(5) + "\n" + "预存金额："
							+ rs.getString(6) + "\n" + "充值时间："
							+ rs.getString(7) + "\n");
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return msg.toString();
	}

	/**
	 * 6 后付费欠费查询
	 * 
	 * @param WeChatNo
	 * @return
	 * @throws SQLException
	 */
	public String WechatQryBill(String WeChatNo) throws SQLException {
		SqlUtil sqlutil = new SqlUtil();
		ResultSet rs = null;
		rs = sqlutil.Por_WechatQryBill(WeChatNo);
		StringBuffer msg = new StringBuffer();
		if (rs == null || rs.getRow() == 0) {
			msg.append("账单未生成，请稍后再试");
		} else {
			try {
				while (rs.next()) {
					msg.append("费用编号：" + rs.getString(1) + "\n" + "费用日期："
							+ rs.getString(2) + "\n" + "计量水量："
							+ rs.getString(3) + "\n" + "欠费：" + rs.getString(4)
							+ "\n" + "违约金：" + rs.getString(5));
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return msg.toString();
	}

	public String GetTotleMoney(String WeChatNo) throws SQLException {
		float money = 0;
		SqlUtil sqlutil = new SqlUtil();
		ResultSet rs = null;
		rs = sqlutil.Por_WechatQryBill(WeChatNo);
		if (rs == null) {
			money = 0.00f;
		} else {
			try {
				while (rs.next()) {
					money = Float.parseFloat(rs.getString(4))
							+ Float.parseFloat(rs.getString(5));
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return money + "";
	}

	/**
	 * 7 后付费交费
	 * 
	 * @param WeChatNo
	 *            微信号
	 * @param money
	 *            缴费金额
	 * @param count
	 *            缴费笔数，0：全部，其它为多笔欠费的前iCount笔
	 * @return
	 * @throws SQLException
	 */
	public String WechatWriteoff(String WeChatNo, double money, int count,
			String orderNo) throws SQLException {
		SqlUtil sqlutil = new SqlUtil();
		String remsg = sqlutil.Pro_WechatWriteoff(WeChatNo, money, count,
				orderNo);
		StringBuffer msg = new StringBuffer();
		msg.append(remsg);
		return msg.toString();
	}

	/**
	 * 8 后付费交费记录查询
	 * 
	 * @param WeChatNo
	 * @return
	 * @throws SQLException
	 */
	public String WechatQryWriteoffFlow(String WeChatNo) throws SQLException {
		SqlUtil sqlutil = new SqlUtil();
		ResultSet rs = null;
		rs = sqlutil.Por_WechatQryoffFlow(WeChatNo);
		StringBuffer msg = new StringBuffer();
		if (rs == null) {
			msg.append("抱歉！未查询到近六个月后付费缴费记录");
			return msg.toString();
		} else {
			try {
				while (rs.next()) {
					msg.append("费用编号：" + rs.getString(1) + "\n" + "费用日期："
							+ rs.getString(2) + "\n" + "流水号：" + rs.getString(3)
							+ "\n" + "账务日期：" + rs.getString(4) + "\n" + "费用金额："
							+ rs.getString(5) + "\n" + "违约金：" + rs.getString(6)
							+ "\n" + "缴费时间：" + rs.getString(7));
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return msg.toString();
	}

	/**
	 * 9 价格查询
	 * 
	 * @return
	 * @throws SQLException
	 */
	public String WechatQryPrice() throws SQLException {
		SqlUtil sqlutil = new SqlUtil();
		StringBuffer msg = new StringBuffer();
		ResultSet rs = null;
		rs = sqlutil.pro_WechatQryPrice();
		try {
			while (rs.next()) {
				msg.append("\n" + rs.getString(1).trim());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return msg.toString();
	}
}

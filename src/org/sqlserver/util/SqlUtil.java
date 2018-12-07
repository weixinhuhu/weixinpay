package org.sqlserver.util;

import java.sql.*;
import java.util.Calendar;
import java.util.Date;

import org.course.service.CoreService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SqlUtil {
	private static Connection conn = null;
	private static Statement st = null;
	private static ResultSet rs = null;
	private static ResultSet rs1 = null;
	private static CallableStatement cs = null;
	public static String status = "";
	private int usenum;
	private String msg;
	private static Logger log = (Logger) LoggerFactory
			.getLogger(CoreService.class);

	/**
	 * 查询结果集函数
	 * 
	 * @param sql
	 * @return
	 * @throws SQLException
	 */
	public ResultSet getResult(String sql) throws SQLException {
		try {
			DbConn dbconn = new DbConn();
			conn = dbconn.getConntion();
			st = conn.createStatement();
			rs = st.executeQuery(sql);

		} catch (SQLException e) {
			conn.close();
			e.printStackTrace();
		}
		return rs;
	}

	/**
	 * 用户信息查询
	 * 
	 * @param wechatno
	 *            微信号码
	 * @return
	 * @throws SQLException
	 */
	public ResultSet Pro_getUserInfoResult(String wechatno) throws SQLException {
		try {
			DbConn dbconn = new DbConn();
			conn = dbconn.getConntion();
			cs = conn
					.prepareCall("{call  [dbo].[up_WechatQryCustomerByWeChatNo](?,?)}");
			cs.setString(1, wechatno);
			cs.registerOutParameter(2, Types.VARCHAR);
			cs.execute();
			status = cs.getString(2);
			if (status.equals("0")) {
				rs = cs.executeQuery();
				log.info("查询成功");
			} else {
				log.info("查询失败");
				log.info(status);
				log.info("-----------------------");
			}
		} catch (Exception e) {
			conn.close();
			log.info("查询异常！");
			log.info("-----------------------");
		}
		return rs;
	}

	/**
	 * 用水余额查询
	 * 
	 * @param wechatno
	 * @return
	 * @throws SQLException
	 */
	public ResultSet Por_QueryMoney(String wechatno) throws SQLException {
		usenum = Por_QueryUserNum(wechatno);
		if (usenum == 0) {
			return null;
		}
		// DbConn dbconn = new DbConn();
		// conn = dbconn.getConntion();
		cs = conn.prepareCall("{call  [dbo].[up_WechatQryRemainingSum](?,?)}");
		cs.setInt(1, usenum);
		cs.registerOutParameter(2, Types.VARCHAR);
		cs.execute();
		status = cs.getString(2);
		if (status.equals("0")) {
			rs = cs.executeQuery();
		} else {
			log.info("查询失败");
			log.info(status);
			log.info("-----------------------");
		}
		return rs;
	}

	/**
	 * 微信号码查询用户号
	 * 
	 * @param wechatno
	 * @return
	 * @throws SQLException
	 */
	public int Por_QueryUserNum(String wechatno) throws SQLException {
		try {
			DbConn dbconn = new DbConn();
			conn = dbconn.getConntion();
			cs = conn
					.prepareCall("{call  [dbo].[up_WechatQryCustomerByWeChatNo](?,?)}");
			cs.setString(1, wechatno);
			cs.registerOutParameter(2, Types.VARCHAR);
			cs.execute();
			status = cs.getString(2);
			if (status.equals("0")) {
				rs = cs.executeQuery();
				while (rs.next()) {
					usenum = Integer.parseInt(rs.getString(1));
					log.info("查询用户号成功，用户号 ：  " + usenum);
				}
			} else {
				usenum = 0;
				log.info("查询用户号失败");
				log.info(status);
				log.info("-----------------------");
			}
		} catch (Exception e) {
			usenum = 0;
			conn.close();
			log.info("查询用户号异常！");
			log.info("-----------------------");
		}

		return usenum;
	}

	public ResultSet Por_WechatQryDepositFlow(String wechatno)
			throws SQLException {
		usenum = Por_QueryUserNum(wechatno);
		if (usenum == 0) {
			return null;
		}
		// DbConn dbconn = new DbConn();
		// conn = dbconn.getConntion();
		Date dNow = new Date(); // 当前时间
		Date dBefore = new Date();
		Calendar calendar = Calendar.getInstance(); // 得到日历
		calendar.setTime(dNow);// 把当前时间赋给日历
		calendar.add(calendar.MONTH, -6); // 设置为前6月
		dBefore = calendar.getTime(); // 得到前6月的时间
		cs = conn
				.prepareCall("{call  [dbo].[up_WechatQryDepositFlow](?,?,?,?,?)}");
		cs.setInt(1, usenum);
		cs.setDate(2, new java.sql.Date(dBefore.getTime()));
		cs.setDate(3, new java.sql.Date(dNow.getTime()));
		cs.registerOutParameter(4, Types.VARCHAR);
		cs.registerOutParameter(5, Types.VARCHAR);
		cs.execute();
		cs.getMoreResults();
		rs = cs.getResultSet();
		return rs;
	}

	public ResultSet Por_WechatQryoffFlow(String wechatno) throws SQLException {
		usenum = Por_QueryUserNum(wechatno);
		if (usenum == 0) {
			return null;
		}
		Date dNow = new Date(); // 当前时间
		Date dBefore = new Date();
		Calendar calendar = Calendar.getInstance(); // 得到日历
		calendar.setTime(dNow);// 把当前时间赋给日历
		calendar.add(calendar.MONTH, -6); // 设置为前6月
		dBefore = calendar.getTime(); // 得到前6月的时间
		cs = conn
				.prepareCall("{call  [dbo].[up_WechatQryWriteoffFlow](?,?,?,?)}");
		cs.setInt(1, usenum);
		cs.setDate(2, new java.sql.Date(dBefore.getTime()));
		cs.setDate(3, new java.sql.Date(dNow.getTime()));
		cs.registerOutParameter(4, Types.VARCHAR);
		cs.execute();
		if (cs.getMoreResults()) {
			rs = cs.getResultSet();
		}else{
			rs = null;
		}		
		return rs;
	}

	public ResultSet Por_WechatQryBill(String wechatno) throws SQLException {
		usenum = Por_QueryUserNum(wechatno);
		if (usenum == 0) {
			return null;
		}
		cs = conn.prepareCall("{call  [dbo].[up_WechatQryBill](?,?)}");
		cs.setInt(1, usenum);
		cs.registerOutParameter(2, Types.VARCHAR);
		cs.execute();
		if (cs.getMoreResults()) {
			rs = cs.getResultSet();
		} else {
			rs = null;
		}

		return rs;
	}

	/**
	 * 价格查询
	 * 
	 * @return
	 * @throws SQLException
	 */
	public ResultSet pro_WechatQryPrice() throws SQLException {
		DbConn dbconn = new DbConn();
		conn = dbconn.getConntion();
		cs = conn.prepareCall("{call  [dbo].[up_WechatQryPrice](?)}");
		cs.registerOutParameter(1, Types.VARCHAR);
		cs.execute();
		status = cs.getString(1);
		if (status.equals("0")) {
			rs = cs.executeQuery();
		} else {
			log.info("查询失败");
			log.info(status);
			log.info("-----------------------");
		}
		return rs;
	}

	/**
	 * 绑定于解绑
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
	 * @throws SQLException sql异常
	 */
	public String Pro_WechatRelation(int type, int CustNo, String MobileNo,
			String WeChatNo) throws SQLException {
		DbConn dbconn = new DbConn();
		conn = dbconn.getConntion();
		cs = conn.prepareCall("{call  [dbo].[up_WechatRelation](?,?,?,?,?)}");
		cs.setInt(1, type);
		cs.setInt(2, CustNo);
		cs.setString(3, MobileNo);
		cs.setString(4, WeChatNo);
		cs.registerOutParameter(5, Types.VARCHAR);
		cs.execute();
		status = cs.getString(5);
		return status;
	}

	public String Pro_WechatDeposit(String WeChatNo, Double money, String orderId)
			throws SQLException {
		usenum = Por_QueryUserNum(WeChatNo);
		if (usenum == 0) {
			status = "请先绑定用水档案操作！";
			return status;
		}
		DbConn dbconn = new DbConn();
		conn = dbconn.getConntion();
		cs = conn.prepareCall("{call  [dbo].[up_WechatDeposit](?,?,?,?,?,?)}");
		cs.setInt(1, usenum);
		cs.setDouble(2, money);
		cs.setString(3, orderId);
		cs.registerOutParameter(4, Types.VARCHAR);
		cs.registerOutParameter(5, Types.VARCHAR);
		cs.registerOutParameter(6, Types.VARCHAR);
		cs.execute();
		status = cs.getString(6);
		if (status.equals("0")) {
			status = "\n" + "恭喜充值成功！ " + "\n" + "当前总额：" + cs.getString(4)
					+ " 元" + "\n" + "最后次充值时间：" + cs.getString(5);
			log.info(status);
		} else {
			status = "\n" + "抱歉充值失败！" + status;
			log.info(status);
		}
		return status;
	}

	public String Pro_WechatWriteoff(String WeChatNo, double money, int count,
			String orderNo) throws SQLException {
		usenum = Por_QueryUserNum(WeChatNo);
		if (usenum == 0) {
			status = "请先绑定用水档案操作！";
			return status;
		}
		DbConn dbconn = new DbConn();
		conn = dbconn.getConntion();
		cs = conn.prepareCall("{call  [dbo].[up_WechatWriteoff](?,?,?,?,?)}");
		cs.setInt(1, usenum);
		cs.setDouble(2, money );
		cs.setInt(3, count);
		cs.setString(4, orderNo);
		cs.registerOutParameter(5, Types.VARCHAR);
		cs.execute();
		status = cs.getString(5);
		if (status.equals("0")) {
			status = "\n" + "恭喜缴费成功！";
			log.info(status);
		} else {
			status = "\n" + "抱歉充值失败！" + status;
			log.info(status);
		}
		return status;
	}
}

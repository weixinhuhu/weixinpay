package org.course.service;

import java.sql.ResultSet;
import java.sql.SQLException;
import org.sqlserver.util.SqlUtil;

public class ManageService {

	/**
	 * 通过用水户号 存储过程方式获取结果集
	 * 
	 * @param usernum 用户号
	 * @return  用户信息
	 * @throws SQLException sql异常
	 */
	String getProResult(String usernum) throws SQLException {
		SqlUtil sqlutil = new SqlUtil();
		ResultSet rs ;
		rs = sqlutil.getResult(usernum);
		StringBuilder msg = new StringBuilder();
		try {
			while (rs.next()) {
				msg.append("账户余额：" + moneyFormat(rs.getString(3)) + "元\n用水户号："
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
	 * @return 操作结果
	 * @throws SQLException sql 异常
	 */
	public String WechatRelation(int type, int CustNo, String MobileNo,
			String WeChatNo) throws SQLException {
		SqlUtil sqlutil = new SqlUtil();
		String remsg = sqlutil.Pro_WechatRelation(type, CustNo, MobileNo,
				WeChatNo);
		if (remsg.equals("0")) {
			return "0";
		} else {
			return remsg;
		}
	}

	/**
	 * 2客户查询
	 * 
	 * @param WeChatNo 微信号
	 * @return 客户记录
	 * @throws SQLException sql 异常
	 */
	public String getUseInfo(String WeChatNo) throws SQLException {
		SqlUtil sqlutil = new SqlUtil();
		ResultSet rs;
		rs = sqlutil.Pro_getUserInfoResult(WeChatNo);
		StringBuilder msg = new StringBuilder();
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
	 * @param WeChatNo 微信号
	 * @return  账户余额
	 * @throws SQLException sql 异常
	 */
	public String QureyUserMoney(String WeChatNo) throws SQLException {
		SqlUtil sqlutil = new SqlUtil();
		ResultSet rs ;
		rs = sqlutil.Por_QueryMoney(WeChatNo);
		StringBuilder msg = new StringBuilder();
		if (rs == null) {
			msg.append("抱歉！未查询到余额信息");
		} else {
			try {
				while (rs.next()) {
					msg.append("客户编号：" + rs.getString(1) + "\n" + "客户姓名："
							+ rs.getString(2) + "\n" + "用水地址："
							+ rs.getString(3) + "\n" + "账务余额："
							+ moneyFormat(rs.getString(4)) + "\n" + "截止日期："
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
	 * @return  缴费结果
	 * @throws SQLException sql异常
	 */
	public String WechatDeposit(String WeChatNo, Double money, String orderId)
			throws SQLException {
		SqlUtil sqlutil = new SqlUtil();
		String remsg = sqlutil.Pro_WechatDeposit(WeChatNo, money, orderId);
		StringBuilder msg = new StringBuilder();
		msg.append(remsg);
		return msg.toString();
	}

	/**
	 * 5 预付费缴费记录查询
	 * 
	 * @param  WeChatNo 微信号
	 * @return 缴费记录
	 * @throws SQLException sql异常
	 */
	public String WechatQryDepositFlow(String WeChatNo) throws SQLException {
		SqlUtil sqlutil = new SqlUtil();
		ResultSet rs ;
		rs = sqlutil.Por_WechatQryDepositFlow(WeChatNo);
		StringBuilder msg = new StringBuilder();
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
							+ moneyFormat(rs.getString(6)) + "\n" + "充值时间："
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
	 * @param WeChatNo "微信号"
	 * @return 欠费记录
	 * @throws SQLException sql 异常
	 */
	public String WechatQryBill(String WeChatNo) throws SQLException {
		SqlUtil sqlutil = new SqlUtil();
		ResultSet rs ;
		rs = sqlutil.Por_WechatQryBill(WeChatNo);
		StringBuilder msg = new StringBuilder();
		if (rs == null || rs.getRow() == 0) {
			msg.append("账单未生成，请稍后再试");
		} else {
			try {
				while (rs.next()) {
					msg.append("费用编号：" + rs.getString(1) + "\n" + "费用日期："
							+ rs.getString(2) + "\n" + "计量水量："
							+ rs.getString(3) + "\n" + "欠费：" + moneyFormat(rs.getString(4))
							+ "\n" + "违约金：" +moneyFormat( rs.getString(5)));
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
		ResultSet rs ;
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
	 * @return  缴费结果
	 * @throws SQLException sql异常
	 */
	public String WechatWriteoff(String WeChatNo, double money, int count,
			String orderNo) throws SQLException {
		SqlUtil sqlutil = new SqlUtil();
		String remsg = sqlutil.Pro_WechatWriteoff(WeChatNo, money, count,
				orderNo);
		StringBuilder msg = new StringBuilder();
		msg.append(remsg);
		return msg.toString();
	}

	/**
	 * 8 后付费交费记录查询
	 * 
	 * @param WeChatNo 微信号
	 * @return 交费记录
	 * @throws SQLException sql异常
	 */
	public String WechatQryWriteoffFlow(String WeChatNo) throws SQLException {
		SqlUtil sqlutil = new SqlUtil();
		ResultSet rs ;
		rs = sqlutil.Por_WechatQryoffFlow(WeChatNo);
		StringBuilder msg = new StringBuilder();
		if (rs == null|| rs.getRow() == 0) {
			msg.append("抱歉！未查询到近六个月后付费缴费记录");
			return msg.toString();
		} else {
			try {
				while (rs.next()) {
					msg.append("费用编号：" + rs.getString(1) + "\n" + "费用日期："
							+ rs.getString(2) + "\n" + "流水号：" + rs.getString(3)
							+ "\n" + "账务日期：" + rs.getString(4) + "\n" + "费用金额："
							+moneyFormat( rs.getString(5)) + "\n" + "违约金：" + moneyFormat(rs.getString(6))
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
	 * @return 价格信息
	 * @throws SQLException sql异常
	 */
	public String WechatQryPrice() throws SQLException {
		SqlUtil sqlutil = new SqlUtil();
		StringBuilder msg = new StringBuilder();
		ResultSet rs ;
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
	
	  /**  
     * 对金额的格式调整到分  
     * @param money  金额
     * @return  格式化金额
     */    
    public static String moneyFormat(String money){//23->23.00    
		StringBuilder sb=new StringBuilder();
        if(money==null){    
            return "0.00";    
        }    
        int index=money.indexOf(".");    
        if(index==-1){    
            return money+".00";    
        }else{    
            String s0=money.substring(0,index);//整数部分    
            String s1=money.substring(index+1);//小数部分    
            if(s1.length()==1){//小数点后一位    
                s1=s1+"0";    
            }else if(s1.length()>2){//如果超过3位小数，截取2位就可以了    
                s1=s1.substring(0,2);    
            }    
            sb.append(s0);    
            sb.append(".");    
            sb.append(s1);    
        }    
        return sb.toString()+"元";    
    } 
}

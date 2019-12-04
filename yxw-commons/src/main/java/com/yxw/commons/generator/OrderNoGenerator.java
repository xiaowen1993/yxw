package com.yxw.commons.generator;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.atomic.AtomicLong;

import org.apache.commons.lang.StringUtils;

/**
 * 订单号生成类
 * @author YangXuewen
 *
 */
public class OrderNoGenerator {

	private static DateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");
	private static DateFormat simpleDf = new SimpleDateFormat("yyyyMMdd");

	/**
	 * 订单日期标志位  判断流水号是否需要从1开始
	 */
	private static long orderDay;
	private static AtomicLong serialNumGen = new AtomicLong(0);
	private static Byte lockObj = new Byte("0");

	private static final char PAD_CHAR_ZERO = '0';

	private static final int SERIAL_NUM_LENGTH = 6;

	/**
	 *   生成规则: 固定前缀（Y） + 平台类型 + 支付类型 + 日期(yyyyMMddHHmmss) + 交易类型 + 业务编号 + 服务器编号 + openId哈希值 + 定长流水号(从1开始)
	 *   
	 *   @platformType 平台类型(2位)<br>
	 *   	微信服务窗（01），支付宝服务窗（02），银联中嵌入app（03）
	 *   	更多对应关系见 SYS_PLATFORM 表的 TARGET_ID 和 NAME
	 *   @payType 支付类型(2位)<br>
	 *   	微信支付（01），支付宝支付（02），微信支付（健康易app）（03）
	 *      更多对应关系见 SYS_PAY_MODE 表的 TARGET_ID 和 NAME
	 *   @tradeType 交易类型(1位)<br>
	 *   	支付（1），退费（2），部分退费（3）
	 *   @bizType 业务编号(1位)<br>
	 *   	挂号（1），门诊（2），住院（3）
	 *   @serverNo 服务器编号(2位,不足2位的前补0,根据IP/MAC生成)
	 *   @openId 生成3位哈希值
	 */
	public static String genOrderNo(String platformType, String payType, int tradeType, int bizType, String serverNo, String openId) {
		StringBuffer orderNo = new StringBuffer();

		//代表医享网，无意义
		orderNo.append("Y");

		//平台类型
		orderNo.append(StringUtils.leftPad(platformType, 2, PAD_CHAR_ZERO));

		//支付类型
		orderNo.append(StringUtils.leftPad(payType, 2, PAD_CHAR_ZERO));

		//日期: yyyyMMddHHmmss
		orderNo.append(df.format(new Date()));

		//交易类型
		orderNo.append(tradeType);

		//业务编号
		orderNo.append(bizType);

		//服务器编号
		orderNo.append(StringUtils.leftPad(serverNo, 2, PAD_CHAR_ZERO));

		//订单所在表编号
		orderNo.append(String.format("%03d", ( ( openId.hashCode() * 2654435769L ) >> 28 ) & 0xff));

		//流水号
		long nowDay = Long.valueOf(simpleDf.format(new Date()));
		synchronized (lockObj) {
			if (nowDay > orderDay) {
				orderDay = nowDay;
				serialNumGen = new AtomicLong(0);
			}
		}

		String serialNum = getSerialNum();
		if (serialNum.length() > SERIAL_NUM_LENGTH) {
			serialNumGen = new AtomicLong(0);
			serialNum = getSerialNum();
		}

		orderNo.append(serialNum);

		return orderNo.toString();
	}

	private static String getSerialNum() {
		String serialNum = String.valueOf(serialNumGen.incrementAndGet());
		serialNum = StringUtils.leftPad(serialNum, SERIAL_NUM_LENGTH, PAD_CHAR_ZERO);
		return serialNum;
	}

	public static String getDateTimeString(String orderNo) {
		return orderNo.substring(5, 19);
	}

	public static Date getDateTime(String orderNo) throws ParseException {
		return df.parse(getDateTimeString(orderNo));
	}

	public static String getBizType(String orderNo) {
		return orderNo.substring(20, 21);
	}

	public static String getServerNo(String orderNo) {
		return orderNo.substring(21, 23);
	}

	public static String getHashVal(String orderNo) {
		return orderNo.substring(23, 26);
	}
}

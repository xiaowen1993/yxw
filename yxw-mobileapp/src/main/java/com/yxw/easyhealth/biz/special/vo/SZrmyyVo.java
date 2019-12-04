package com.yxw.easyhealth.biz.special.vo;

import java.io.Serializable;
import java.security.MessageDigest;

import com.yxw.easyhealth.biz.special.SZrmyyConstants;
import com.yxw.easyhealth.biz.special.utils.DES;
import com.yxw.mobileapp.biz.user.entity.EasyHealthUser;

/**
 * 深圳人民医院对象
 * 
 * @Package: com.yxw.easyhealth.biz.special.vo
 * @ClassName: SZrmyyVo
 * @Statement: <p>
 *             </p>
 * @JDK version used: 1.6
 * @Author: dfw
 * @Create Date: 2015-10-30
 * @modify By:
 * @modify Date:
 * @Why&What is modify:
 * @Version: 1.0
 */
public class SZrmyyVo implements Serializable {

	private static final long serialVersionUID = -3802659460466872082L;

	/**
	 * 用户所在系统ID,可使用身份证号码作为会员的唯一标识
	 */
	private String customerid;

	/**
	 * 姓名
	 */
	private String name;

	/**
	 * 性别,M表示男，F表示女
	 */
	private String sex;

	/**
	 * 证件号
	 */
	private String idno;

	/**
	 * 手机号码
	 */
	private String mobile;

	/**
	 * 请求时间，自1970-1-1 08:00起至今的毫秒数，初步约定1天以前的请求非法。
	 */
	private String time;

	/**
	 * from:数据来源 值固定为jky
	 */
	private String from;

	/**
	 * ”0/1" 1是否付费，0表示免费用户（缺省值），1表示付费用户
	 */
	private Integer isfee;

	/**
	 * 转入菜单编码
	 */
	private String menu;

	public String getCustomerid() {
		return customerid;
	}

	public void setCustomerid(String customerid) {
		this.customerid = customerid;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public String getIdno() {
		return idno;
	}

	public void setIdno(String idno) {
		this.idno = idno;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public String getFrom() {
		return from;
	}

	public void setFrom(String from) {
		this.from = from;
	}

	public Integer getIsfee() {
		return isfee;
	}

	public void setIsfee(Integer isfee) {
		this.isfee = isfee;
	}

	public String getMenu() {
		return menu;
	}

	public void setMenu(String menu) {
		this.menu = menu;
	}

	public String getJSON() {
		String json = "{";
		json += "\"CustomerId\":";
		json += "\"" + this.idno + "\",";

		json += "\"CustomerId\":";
		json += "\"" + this.idno + "\",";

		json += "\"Name\":";
		json += "\"" + this.name + "\",";

		json += "\"Sex\":";
		json += "\"" + this.sex + "\",";

		json += "\"IdNo\":";
		json += "\"" + this.idno + "\",";

		json += "\"Mobile\":";
		json += "\"" + this.mobile + "\",";

		json += "\"Time\":";
		json += "\"" + this.time + "\",";

		json += "\"Menu\":";
		json += "\"" + this.menu + "\",";

		json += "\"IsFee\":";
		json += "\"" + isfee + "\",";

		json += "\"From\":\"jky\"";

		json += "}";
		return json;
	}

	public static SZrmyyVo getVo(EasyHealthUser user, String toMenu, int isFee) {
		SZrmyyVo vo = new SZrmyyVo();

		vo.setName(user.getName());
		vo.setCustomerid(user.getCardNo());
		vo.setIdno(user.getCardNo());
		vo.setFrom(SZrmyyConstants.DATA_SOURCE_FROM);
		vo.setIsfee(SZrmyyConstants.IS_FEE_NO);
		vo.setMobile(user.getMobile());
		vo.setTime(String.valueOf(System.currentTimeMillis()));
		vo.setMenu(toMenu);
		vo.setIsfee(isFee);
		vo.setSex(user.getSex().intValue() == 1 ? SZrmyyConstants.SEX_MAN : SZrmyyConstants.SEX_WOMAN);

		return vo;
	}

	public String getNonce() {
		return this.time;
	}

	public String getSignature(String nonce, String encryptedData) throws Exception {
		String signature = SZrmyyConstants.MD5_KEY + nonce + encryptedData;
		MessageDigest md = MessageDigest.getInstance("MD5");
		byte[] md5 = md.digest(signature.getBytes());
		signature = DES.toHexString(md5);
		return DES.toHexString(md5);
	}

	public String getEncryptData(String data) throws Exception {
		return new DES(SZrmyyConstants.DES_KEY).encode(data);
	}

}

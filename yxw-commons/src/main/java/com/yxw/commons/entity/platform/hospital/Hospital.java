/**
 * <html>
 *   <body>
 *     <p>Copyright(C)版权所有 - 2015 广州医享网络科技发展有限公司.</p>
 *     <p>All rights reserved.</p>
 *     <p>Created on 2015年5月14日</p>
 *     <p>Created by homer.yang</p>
 *   </body>
 * </html>
 */
package com.yxw.commons.entity.platform.hospital;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Entity;

import com.yxw.base.entity.BaseEntity;

/**
 * 医院实体类
 * 
 * @author homer.yang
 * @version 1.0
 * @date 2015年5月14日
 */
@Entity(name = "hospital")
public class Hospital extends BaseEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5521008935330780498L;

	/**
	 * 医院名称
	 */
	private String name;

	/**
	 * 医院编码
	 */
	private String code;

	/**
	 * 联系人
	 */
	private String contactName;

	/**
	 * 联系电话
	 */
	private String contactTel;

	/**
	 * 状态:0禁用，1启用
	 */
	private int status;

	/**
	 * 分院列表
	 */
	private List<BranchHospital> branchHospitals = new ArrayList<BranchHospital>();

	/**
	 * 可选功能列表
	 */
	private List<Optional> options;

	/**
	 * 医院已经接入的平台配置信息
	 */
	private List<HospitalPlatformSettings> hospitalPlatformSettings = new ArrayList<HospitalPlatformSettings>();

	private List<PlatformPaySettings> platformPaySettings = new ArrayList<PlatformPaySettings>();

	/**
	 * 规则修改时间
	 */
	private Date ruleLastEditTime;

	/**
	 * 规则发布时间
	 */
	private Date rulePublishTime;

	private String lastHandlerId;

	/**
	 * 最后操作人
	 */
	private String lastHandler;

	/**
	 * 是否发布 0未发布 1已发布
	 */
	private Integer isPublishRule;

	/**
	 * 区域代码 BizConstant.areaCode key:ShenZheng value:深圳 BizConstant.areaCode
	 * key:GuangZhou value:广州
	 * 
	 * 2016-04-20 修改为国家统一编码 by YY
	 * 并添加areaName字段
	 */
	private String areaCode;
	private String areaName;

	private Integer sortIndex;

	private String guideURL;

	private String cloudURL;

	private String trafficURL;

	private String logo;

	public Hospital() {
		super();
	}

	/**
	 * @param name
	 *            医院名称
	 * @param code
	 *            医院编码
	 * @param contactName
	 *            联系人
	 * @param contactTel
	 *            联系电话
	 * @param status
	 *            状态:0禁用，1启用
	 */
	public Hospital(String name, String code, String areaCode, String contactName, String contactTel) {
		super();
		this.name = name;
		this.code = code;
		this.areaCode = areaCode;
		this.contactName = contactName;
		this.contactTel = contactTel;
	}

	public Hospital(String name, String code, String contactName, String contactTel, String areaCode, String guideURL, String cloudURL,
			String trafficURL, String logo) {
		super();
		this.name = name;
		this.code = code;
		this.contactName = contactName;
		this.contactTel = contactTel;
		this.areaCode = areaCode;
		this.guideURL = guideURL;
		this.cloudURL = cloudURL;
		this.trafficURL = trafficURL;
		this.logo = logo;
	}

	public Hospital(String name, String code, String contactName, String contactTel, String areaCode, String areaName, String guideURL,
			String cloudURL, String trafficURL, String logo) {
		super();
		this.name = name;
		this.code = code;
		this.contactName = contactName;
		this.contactTel = contactTel;
		this.areaCode = areaCode;
		this.areaName = areaName;
		this.guideURL = guideURL;
		this.cloudURL = cloudURL;
		this.trafficURL = trafficURL;
		this.logo = logo;
	}

	/**
	 * @return 医院名称
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name
	 *            医院名称
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return 医院编码
	 */
	public String getCode() {
		return code;
	}

	/**
	 * @param code
	 *            医院编码
	 */
	public void setCode(String code) {
		this.code = code;
	}

	/**
	 * @return 联系人
	 */
	public String getContactName() {
		return contactName;
	}

	/**
	 * @param contactName
	 *            联系人
	 */
	public void setContactName(String contactName) {
		this.contactName = contactName;
	}

	/**
	 * @return 联系电话
	 */
	public String getContactTel() {
		return contactTel;
	}

	/**
	 * @param contactTel
	 *            联系电话
	 */
	public void setContactTel(String contactTel) {
		this.contactTel = contactTel;
	}

	/**
	 * @return 状态:0禁用，1启用
	 */
	public int getStatus() {
		return status;
	}

	/**
	 * @param status
	 *            状态:0禁用，1启用
	 */
	public void setStatus(int status) {
		this.status = status;
	}

	/**
	 * @return the 中文状态:0禁用，1启用
	 */
	public String getCnStatus() {
		return this.status == 1 ? "启用" : "禁用";
	}

	/**
	 * @return 分院列表
	 */
	public List<BranchHospital> getBranchHospitals() {
		return branchHospitals;
	}

	/**
	 * @param branchHospitals
	 *            分院列表
	 */
	public void setBranchHospitals(List<BranchHospital> branchHospitals) {
		this.branchHospitals = branchHospitals;
	}

	/**
	 * @return 可选功能列表
	 */
	public List<Optional> getOptions() {
		return options;
	}

	/**
	 * @param options
	 *            可选功能列表
	 */
	public void setOptions(List<Optional> options) {
		this.options = options;
	}

	/**
	 * @return 医院已经接入的平台配置信息
	 */
	public List<HospitalPlatformSettings> getHospitalPlatformSettings() {
		return hospitalPlatformSettings;
	}

	/**
	 * @param hospitalPlatformSettings
	 *            医院已经接入的平台配置信息
	 */
	public void setHospitalPlatformSettings(List<HospitalPlatformSettings> hospitalPlatformSettings) {
		this.hospitalPlatformSettings = hospitalPlatformSettings;
	}

	public List<PlatformPaySettings> getPlatformPaySettings() {
		return platformPaySettings;
	}

	public void setPlatformPaySettings(List<PlatformPaySettings> platformPaySettings) {
		this.platformPaySettings = platformPaySettings;
	}

	public Date getRuleLastEditTime() {
		return ruleLastEditTime;
	}

	public void setRuleLastEditTime(Date ruleLastEditTime) {
		this.ruleLastEditTime = ruleLastEditTime;
	}

	public Date getRulePublishTime() {
		return rulePublishTime;
	}

	public void setRulePublishTime(Date rulePublishTime) {
		this.rulePublishTime = rulePublishTime;
	}

	public String getLastHandler() {
		return lastHandler;
	}

	public void setLastHandler(String lastHandler) {
		this.lastHandler = lastHandler;
	}

	public Integer getIsPublishRule() {
		return isPublishRule;
	}

	public void setIsPublishRule(Integer isPublishRule) {
		this.isPublishRule = isPublishRule;
	}

	public String getLastHandlerId() {
		return lastHandlerId;
	}

	public void setLastHandlerId(String lastHandlerId) {
		this.lastHandlerId = lastHandlerId;
	}

	public String getAreaCode() {
		return areaCode;
	}

	public void setAreaCode(String areaCode) {
		this.areaCode = areaCode;
	}

	public String getAreaName() {
		return areaName;
	}

	public void setAreaName(String areaName) {
		this.areaName = areaName;
	}

	public String getGuideURL() {
		return guideURL;
	}

	public void setGuideURL(String guideURL) {
		this.guideURL = guideURL;
	}

	public String getCloudURL() {
		return cloudURL;
	}

	public void setCloudURL(String cloudURL) {
		this.cloudURL = cloudURL;
	}

	public String getTrafficURL() {
		return trafficURL;
	}

	public void setTrafficURL(String trafficURL) {
		this.trafficURL = trafficURL;
	}

	public String getLogo() {
		return logo;
	}

	public void setLogo(String logo) {
		this.logo = logo;
	}

	public Integer getSortIndex() {
		return sortIndex;
	}

	public void setSortIndex(Integer sortIndex) {
		this.sortIndex = sortIndex;
	}

}

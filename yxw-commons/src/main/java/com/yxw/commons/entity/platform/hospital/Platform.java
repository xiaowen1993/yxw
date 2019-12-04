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

import javax.persistence.Entity;

import com.yxw.base.entity.BaseEntity;

/**
 * 接入平台实体类
 * 
 * @author homer.yang
 * @version 1.0
 * @date 2015年5月14日
 */
@Entity(name = "platform")
public class Platform extends BaseEntity {
    
    /**
     * 
     */
    private static final long serialVersionUID = -3493137838140941662L;
    
    /**
     * 平台名称
     */
    private String name;
    
    /**
     * 平台编码
     */
    private String code;
    
    /**
     * 平台状态：是否启用。0：禁用，1:启用。
     */
    private String state;
    
    private Integer targetId;
    
    // 不在后台获取了
    // private String mark;
    
    /**
     * @return the name
     */
    public String getName() {
        return name;
    }
    
    /**
     * @param name
     *            the name to set
     */
    public void setName(String name) {
        this.name = name;
    }
    
    /**
     * @return the code
     */
    public String getCode() {
        return code;
    }
    
    /**
     * @param code
     *            the code to set
     */
    public void setCode(String code) {
        this.code = code;
    }

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public Integer getTargetId() {
		return targetId;
	}

	public void setTargetId(Integer targetId) {
		this.targetId = targetId;
	}
    
    
    
}

/**
 * <html>
 *   <body>
 *     <p>Copyright(C)版权所有 - 2015 广州医享网络科技发展有限公司.</p>
 *     <p>All rights reserved.</p>
 *     <p>Created on 2015年12月21日</p>
 *     <p>Created by homer.yang</p>
 *   </body>
 * </html>
 */
package com.yxw.easyhealth.biz.bed.service;

import java.util.List;

import com.yxw.easyhealth.biz.bed.dto.BedResponse;

/**
 * @author homer.yang
 * @version 1.0
 * @date 2015年12月21日
 */
public interface BedService {
    
    /**
     * 调用接口返回产科床位数据（单个医院）
     */
    public abstract BedResponse callObstetricsBedWebservice(String hospitalName);
    
    /**
     * 调用接口返回产科床位数据（所有医院）
     */
    public abstract List<BedResponse> callObstetricsBedWebserviceToList();
    
}

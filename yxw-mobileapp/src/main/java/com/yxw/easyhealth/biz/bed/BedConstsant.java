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
package com.yxw.easyhealth.biz.bed;

import com.yxw.framework.config.SystemConfig;

/**
 * @author homer.yang
 * @version 1.0
 * @date 2015年12月21日
 */
public class BedConstsant {
    
    /**
     * 产科床位Webservice地址
     */
    public static String OBSTETRICS_BED_WEBSERVICE_URL = SystemConfig.getStringValue("obstetrics_bed_webservice_url");
    
    /**
     * 产科床位医院(逗号分开)
     */
    public static String OBSTETRICS_BED_HOSPITAL_NAMES = SystemConfig.getStringValue("obstetrics_bed_hospital_names");
    
    public static final String SERVICE_NAME = "MaternityBedInfo";
    public static final String SERVICE_NAME_PREFIX = "get";
    
}

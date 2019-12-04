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
package com.yxw.easyhealth.biz.bed.dto;

import java.io.Serializable;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.yxw.easyhealth.biz.bed.BedConstsant;

/**
 * @author homer.yang
 * @version 1.0
 * @date 2015年12月21日
 */
@XStreamAlias(BedConstsant.SERVICE_NAME)
public class BedResponse implements Serializable {
    private static final long serialVersionUID = -8365724596450059168L;
    
    /**
     * 医院名称
     */
    private String hospitalName;
    
    /**
     * kcs空床数
     */
    private String kcs = "-";
    
    /**
     * sjcw实际床位数
     */
    private String sjcw = "-";
    
    /**
     * zyrs现有病人数
     */
    private String zyrs = "-";
    
    public BedResponse(String hospitalName) {
        super();
        this.hospitalName = hospitalName;
    }
    
    public String getHospitalName() {
        return hospitalName;
    }
    
    public void setHospitalName(String hospitalName) {
        this.hospitalName = hospitalName;
    }
    
    public String getKcs() {
        return kcs;
    }
    
    public void setKcs(String kcs) {
        this.kcs = kcs;
    }
    
    public String getSjcw() {
        return sjcw;
    }
    
    public void setSjcw(String sjcw) {
        this.sjcw = sjcw;
    }
    
    public String getZyrs() {
        return zyrs;
    }
    
    public void setZyrs(String zyrs) {
        this.zyrs = zyrs;
    }
    
}

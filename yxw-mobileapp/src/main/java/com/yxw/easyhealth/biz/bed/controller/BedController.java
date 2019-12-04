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
package com.yxw.easyhealth.biz.bed.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.yxw.easyhealth.biz.bed.BedConstsant;
import com.yxw.easyhealth.biz.bed.dto.BedResponse;
import com.yxw.easyhealth.biz.bed.service.BedService;

/**
 * 床位查询
 * 
 * @author homer.yang
 * @version 1.0
 * @date 2015年12月21日
 */
@Controller
@RequestMapping("/easyhealth/bed")
public class BedController {
    //private static org.slf4j.Logger logger = org.slf4j.LoggerFactory.getLogger(BedController.class);
    
    @Autowired
    private BedService bedService;
    
    /**
     * 产科床位
     * 
     * @param request
     * @param response
     * @param modelMap
     * @return
     */
    @RequestMapping(value = "obstetrics")
    public ModelAndView obstetrics(HttpServletRequest request, HttpServletResponse response, ModelMap modelMap) {
        
        //List<BedResponse> beds = bedService.callObstetricsBedWebserviceToList();
        
        List<BedResponse> beds = new java.util.ArrayList<BedResponse>();
        
        String[] hospitals = BedConstsant.OBSTETRICS_BED_HOSPITAL_NAMES.split(",");
        for (String hospitalName : hospitals) {
            beds.add(new BedResponse(hospitalName));
        }
        
//        BedResponse bed = new BedResponse("测试医院");
//        bed.setKcs("1");
//        bed.setSjcw("1");
//        bed.setZyrs("1");
//        beds.add(bed);
        
        modelMap.put("obstetricsBeds", beds);
        
        return new ModelAndView("easyhealth/biz/bed/obstetrics", modelMap);
    }
    
    @ResponseBody
    @RequestMapping(value = "loadObstetricsBed")
    public Object loadObstetricsBed() {
        Map<String, Object> resMap = new HashMap<String, Object>();
        
        try {
            List<BedResponse> beds = bedService.callObstetricsBedWebserviceToList();
            
            resMap.put("status", "OK");
            resMap.put("message", "OK");
            resMap.put("obstetricsBeds", beds);
        } catch (Exception e) {
            resMap.put("status", "ERROR");
            resMap.put("message", "程序异常：" + e);
        }
        
        return resMap;
    }
    
}

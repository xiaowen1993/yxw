/**
 * <html>
 * <body>
 *  <P>  Copyright(C)版权所有 - 2014 广州医享网络科技发展有限公司.</p>
 *  <p>  All rights reserved.</p>
 *  <p> Created on 2015年3月23日</p>
 *  <p> Created by 申午武</p>
 *  </body>
 * </html>
 */
package com.yxw.framework.common.excel;

import org.apache.poi.hssf.usermodel.HSSFRow;

import com.yxw.framework.common.excel.hssf.HssfFillerInfo;

/**
 * 填充器接口
 * 
 * @author 申午武
 * @version 1.0
 * @since 2015年3月23日
 */
public interface Filler {
    
    public abstract void filler(HSSFRow row, int beginCellIndex, HssfFillerInfo hssfFillerInfo, Object value);
    
}

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
package com.yxw.framework.common.excel.hssf;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import org.apache.poi.hssf.usermodel.HSSFCell;

import com.yxw.framework.common.excel.Font;
import com.yxw.framework.common.excel.Style;
import com.yxw.framework.exception.SystemException;

/**
 * excel格式文件
 * 
 * @author 申午武
 * @version 1.0
 * @since 2015年3月23日
 */
public class HssfFillerInfo implements Serializable {
    
    private static final long serialVersionUID = -1206777017293555241L;
    /***
     * style 样式,默认为Style
     */
    private Style style = new Style();
    /***
     * font 字体,默认为Font
     */
    private Font font = new Font();
    /**
     * blankReplace 空值替换,默认为null
     */
    private String blankReplace = null;
    /***
     * encode 中文处理,一般情况下不需要修改此设置
     */
    private short encode = HSSFCell.ENCODING_UTF_16;
    /***
     * isMerge 是否启用合并单元格,默认为false
     */
    private boolean isMerge = false;
    
    /***
     * mergeRowNum 合并N行
     */
    private int mergeRowNum = 0;
    
    /***
     * mergeCellNum 合并N列
     */
    private int mergeCellNum = 0;
    
    /***
     * dateFormat 日期格式,默认yyyy-MM-dd HH:mm:ss,为null或者""不进行转换
     */
    private String dateFormat = "yyyy-MM-dd HH:mm:ss";
    /***
     * calendarFormat 日历格式,默认为yyyy-MM-dd,为null或者""不进行转换
     */
    private String calendarFormat = "yyyy-MM-dd";
    /***
     * Double格式 默认转换格式为"0.0",为null或者""不进行转换
     */
    private String doubleFormat = "0.00";
    /***
     * float 格式 默认转换格式为"0.00",为null或者""不进行转换
     */
    private String floatFormat = "0.00";
    /***
     * booleanConversion 是否启用boolean格式转换,默认true
     */
    private boolean booleanConversion = true;
    /***
     * boolean格式 为true时默认转换为"是"
     */
    private String trueConversion = "是";
    /***
     * boolean格式 为False时默认转换为"否"
     */
    private String falseConversion = "否";
    
    /***
     * intConversion 是否启用int格式转换,默认为false
     */
    private boolean intConversion = false;
    /***
     * int格式 接收一个map,map的key对应要转换的数值,value为转换值
     */
    private Map<Integer, String> intConversionMap = new HashMap<Integer, String>();
    
    public Style getStyle() {
        return style;
    }
    
    public void setStyle(Style style) {
        if (style != null) {
            this.style = style;
        }
    }
    
    public Font getFont() {
        return font;
    }
    
    public void setFont(Font font) {
        if (font != null) {
            this.font = font;
        }
    }
    
    public String getBlankReplace() {
        return blankReplace;
    }
    
    public void setBlankReplace(String blankReplace) {
        
    }
    
    public short getEncode() {
        return encode;
    }
    
    public void setEncode(short encode) {
        if (encode > 0) {
            this.encode = encode;
        }
    }
    
    public boolean isMerge() {
        return isMerge;
    }
    
    public void setMerge(boolean isMerge) {
        this.isMerge = isMerge;
    }
    
    public int getMergeRowNum() {
        return mergeRowNum;
    }
    
    public void setMergeRowNum(int mergeRowNum) {
        if (mergeRowNum >= 0) {
            this.mergeRowNum = mergeRowNum;
        }
    }
    
    public int getMergeCellNum() {
        return mergeCellNum;
    }
    
    public void setMergeCellNum(int mergeCellNum) {
        if (mergeCellNum >= 0) {
            this.mergeCellNum = mergeCellNum;
        }
    }
    
    public String getDateFormat() {
        return dateFormat;
    }
    
    public void setDateFormat(String dateFormat) {
        this.dateFormat = dateFormat;
    }
    
    public String getDoubleFormat() {
        return doubleFormat;
    }
    
    public void setDoubleFormat(String doubleFormat) {
        this.doubleFormat = doubleFormat;
    }
    
    public String getFloatFormat() {
        return floatFormat;
    }
    
    public void setFloatFormat(String floatFormat) {
        this.floatFormat = floatFormat;
    }
    
    public String getFalseConversion() {
        return falseConversion;
    }
    
    public void setFalseConversion(String falseConversion) {
        if (falseConversion != null && ("").equals(falseConversion.trim())) {
            this.falseConversion = falseConversion;
        } else {
            throw new IndexOutOfBoundsException("falseConversion不能为Null或者空字符串");
        }
    }
    
    public String getTrueConversion() {
        return trueConversion;
    }
    
    public void setTrueConversion(String trueConversion) {
        if (trueConversion != null && ("").equals(trueConversion.trim())) {
            this.trueConversion = trueConversion;
        } else {
            throw new IndexOutOfBoundsException("trueConversion不能为Null或者空字符串");
        }
    }
    
    public boolean isBooleanConversion() {
        return booleanConversion;
    }
    
    public void setBooleanConversion(boolean booleanConversion) {
        this.booleanConversion = booleanConversion;
    }
    
    public boolean isIntConversion() {
        return intConversion;
    }
    
    public void setIntConversion(boolean intConversion) {
        this.intConversion = intConversion;
    }
    
    public Map<Integer, String> getIntConversionMap() {
        return intConversionMap;
    }
    
    public void setIntConversionMap(Map<Integer, String> intConversionMap) {
        if (intConversionMap != null && intConversionMap.size() > 0) {
            this.intConversionMap = intConversionMap;
        } else {
            throw new SystemException("intConversionMap不能为null并且size不能小于等于0");
        }
        this.intConversionMap = intConversionMap;
    }
    
    public String getCalendarFormat() {
        return calendarFormat;
    }
    
    public void setCalendarFormat(String calendarFormat) {
        this.calendarFormat = calendarFormat;
    }
    
}

/**
 * <html>
 * <body>
 *  <P>  Copyright(C)版权所有 - 2014 广州医享网络科技发展有限公司.</p>
 *  <p>  All rights reserved.</p>
 *  <p> Created on 2015年1月28日</p>
 *  <p> Created by 谢家贵</p>
 *  </body>
 * </html>
 */
package com.yxw.utils;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Locale;

/**
 * 
 * 
 * <p>
 * Title:格式化数字
 * </p>
 * <p>
 * Description:格式化双精度数字, 格式化双精度数字,格式化单精度数字,双精度数字转字符串,双精度数字转金额.
 * </p>
 * <p>
 * Copyright: Copyright (c) 2014
 * </p>
 * <p>
 * Company:广州医享网络科技发展有限公司
 * </p>
 * 
 * @author 谢家贵 2015-1-28
 * @see
 * @since 1.0
 */
public class NumberUtils {
    
    /**
     * 格式化双精度数字
     * 
     * @param num
     * @return Double
     */
    public static Double FormatDouble(Double num) {
        if (num == null) {
            return null;
        }
        int p_num = num.toString().indexOf(".");
        if (((num.toString().length()) - p_num - 1) > 5) {
            if (Integer.parseInt(num.toString().substring(p_num + 6, p_num + 7)) >= 5) {
                num = num + 0.000001;
            }
        }
        DecimalFormat df = new DecimalFormat("####.00");
        return new Double(df.format(num));
    }
    
    /**
     * 格式化双精度数字
     * 
     * @param num
     * @return String
     */
    public static String FormatDoubleToString(Double num) {
        if (num == null) {
            return null;
        }
        int p_num = num.toString().indexOf(".");
        if (((num.toString().length()) - p_num - 1) > 5) {
            if (Integer.parseInt(num.toString().substring(p_num + 6, p_num + 7)) >= 5) {
                num = num + 0.000001;
            }
        }
        DecimalFormat df = new DecimalFormat("###,##0.00");
        return df.format(num);
    }
    
    /**
     * 格式化单精度数字
     * 
     * @param num
     * @return Float
     */
    public static Float FormatFolat(Float num) {
        if (num == null) {
            return null;
        }
        
        int p_num = num.toString().indexOf(".");
        if (((num.toString().length()) - p_num - 1) > 5) {
            if (Integer.parseInt(num.toString().substring(p_num + 6, p_num + 7)) >= 5) {
                num = num + new Float(0.000001);
            }
        }
        DecimalFormat df = new DecimalFormat("####.00000");
        return new Float(df.format(num));
    }
    
    /**
     * 双精度数字转字符串
     * 
     * @param num
     *            双精度数字
     * @param dec
     *            保留小数位数
     * @return String
     */
    public static String getCommaNumber(double num, int dec) {
        NumberFormat nf = NumberFormat.getNumberInstance(Locale.CHINA);
        nf.setMaximumFractionDigits(dec);
        return nf.format(num);
    }
    
    /**
     * 双精度数字转金额
     * 
     * @param num
     *            双精度数字
     * @param dec
     *            保留小数位数
     * @return String
     */
    public static String getCurrencyNationNumber(double num, int dec) {
        NumberFormat nf = NumberFormat.getCurrencyInstance(Locale.CHINA);
        nf.setMaximumFractionDigits(dec);
        return nf.format(num);
    }
}

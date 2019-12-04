/**
 * <html>
 * <body>
 *  <P>  Copyright(C)版权所有 - 2014 广州医享网络科技发展有限公司.</p>
 *  <p>  All rights reserved.</p>
 *  <p> Created on 2015年1月26日</p>
 *  <p> Created by 申午武</p>
 *  </body>
 * </html>
 */
package com.yxw.framework.common.validation;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;

/**
 * @author 申午武
 * 
 */
public class Validation {
    // wi =2(n-1)(mod 11);加权因子
    private final int[] wi = { 7, 9, 10, 5, 8, 4, 2, 1, 6, 3, 7, 9, 10, 5, 8, 4, 2, 1 };
    // 校验码
    private final char[] vi = { '1', '0', 'X', '9', '8', '7', '6', '5', '4', '3', '2' };
    
    /***
     * 验证只能为中文和字母的字符串
     * 
     * @param value
     * @return
     */
    public boolean validationOnlyChineseAndLetters(String value) {
        String pattern = "[\u4e00-\u9fa5A-Za-z]+";
        Pattern p = Pattern.compile(pattern);
        Matcher m = p.matcher(value);
        return m.matches();
        
    }
    
    /***
     * 验证只能为字母和数字的字符串
     * 
     * @param value
     * @return
     */
    public boolean validationOnlyLettersAndFigures(String value) {
        String pattern = "[A-Za-z0-9]+";
        Pattern p = Pattern.compile(pattern);
        Matcher m = p.matcher(value);
        return m.matches();
        
    }
    
    /***
     * 验证手机
     * 
     * @param value
     * @return
     */
    public boolean validationPhone(String value) {
        
        String pattern1 = "13\\d{9}";
        String pattern2 = "15[0-35-9]\\d{8}";
        String pattern3 = "18[05-9]\\d{8}";
        Pattern p1 = Pattern.compile(pattern1);
        Pattern p2 = Pattern.compile(pattern2);
        Pattern p3 = Pattern.compile(pattern3);
        Matcher m1 = p1.matcher(value);
        Matcher m2 = p2.matcher(value);
        Matcher m3 = p3.matcher(value);
        if (m1.matches() || m2.matches() || m3.matches()) {
            return true;
        } else {
            return false;
        }
        
    }
    
    /***
     * 验证电子邮箱
     * 
     * @param value
     * @return
     */
    public boolean validationEmail(String value) {
        
        String pattern = "([a-zA-Z0-9]+[_|\\_|\\.]?)*[a-zA-Z0-9]+@([a-zA-Z0-9]+[_|\\_|\\.]?)*[a-zA-Z0-9]+"
                + "\\.[a-zA-Z]{2,3}";
        Pattern p = Pattern.compile(pattern);
        Matcher m = p.matcher(value);
        return m.matches();
        
    }
    
    /***
     * 验证分数
     * 
     * @param value
     * @return/^[0-9]+([.]\d{1,2 )?$/
     */
    public boolean validationEovScore(String value) {
        String pattern = "[0-9]+([.]\\d{1,2})?";
        Pattern p = Pattern.compile(pattern);
        Matcher m = p.matcher(value);
        return m.matches();
        
    }
    
    /***
     * 验证日期格式为yyyy-MM-dd HH:mm:ss
     * 
     * @param value
     * @return
     */
    public static boolean validationDate1(String value) {
        
        String pattern = "(\\d{1,4})(-|\\/)(\\d{1,2})\\2(\\d{1,2}) (?:(?:[0-2][0-3])|(?:[0-1][0-9])):"
                + "[0-5][0-9]:[0-5][0-9]";
        Pattern p = Pattern.compile(pattern);
        Matcher m = p.matcher(value);
        return m.matches();
        
    }
    
    /***
     * 验证日期格式为yyyy-MM-dd HH:mm
     * 
     * @param value
     * @return
     */
    public static boolean validationDate2(String value) {
        
        String pattern = "(\\d{1,4})(-|\\/)(\\d{1,2})\\2(\\d{1,2}) (?:(?:[0-2][0-3])|(?:[0-1][0-9])):[0-5][0-9]";
        Pattern p = Pattern.compile(pattern);
        Matcher m = p.matcher(value);
        return m.matches();
        
    }
    
    /***
     * 验证日期格式为yyyy-MM-dd
     * 
     * @param value
     * @return
     */
    public static boolean validationDate3(String value) {
        
        String pattern = "(\\d{1,4})(-|\\/)(\\d{1,2})\\2(\\d{1,2})";
        Pattern p = Pattern.compile(pattern);
        Matcher m = p.matcher(value);
        return m.matches();
        
    }
    
    /***
     * 验证日期格式为yyyyMMdd
     * 
     * @param value
     * @return
     */
    public static boolean validationDate4(String value) {
        
        String pattern = "(\\d{4})(\\d{2})(\\d{2})";
        Pattern p = Pattern.compile(pattern);
        Matcher m = p.matcher(value);
        return m.matches();
        
    }
    
    /***
     * 验证日期格式为yyyy/MM/dd
     * 
     * @param value
     * @return
     */
    public static boolean validationDate5(String value) {
        
        String pattern = "(\\d{4})(\\/)(\\d{1,2})\\2(\\d{1,2})";
        Pattern p = Pattern.compile(pattern);
        Matcher m = p.matcher(value);
        return m.matches();
        
    }
    
    /***
     * 验证固话
     * 
     * @param value
     * @return
     */
    public boolean validationTel(String value) {
        
        String pattern = "(([0\\+]\\d{2,3}-)?(0\\d{2,3})-)?(\\d{7,8})(-(\\d{3,}))?";
        Pattern p = Pattern.compile(pattern);
        Matcher m = p.matcher(value);
        return m.matches();
        
    }
    
    /***
     * 验证身份证号码
     * 
     * @param value
     * @return
     */
    
    public boolean validationIdCard(String value) {
        if (value == null) {
            return false;
        }
        // 假如位数为15位，且都为数字则返回true
        if (value.length() == 15 && StringUtils.isNumeric(value)) {
            return true;
        }
        
        if (value.length() != 18) {
            return false;
        }
        // 获取最后一位校验码 进行验证
        char verify = value.charAt(17);
        // 前17位都不为数字返回false
        value = value.substring(0, 17);
        if (!StringUtils.isNumeric(value)) {
            return false;
        }
        
        if (verify == getVerify(value)) {
            return true;
        }
        return false;
    }
    
    /**
     * (身份证验证)计算最后一位校验码
     * 
     * @param eighteen
     * @return
     */
    private char getVerify(String eighteen) {
        int[] ai = new int[18];
        int remain = 0;
        if (eighteen.length() == 18) {
            eighteen = eighteen.substring(0, 17);
        }
        if (eighteen.length() == 17) {
            int sum = 0;
            for (int i = 0; i < 17; i++) {
                char k = eighteen.charAt(i);
                ai[i] = Character.getNumericValue(k);
            }
            for (int i = 0; i < 17; i++) {
                sum += wi[i] * ai[i];
            }
            remain = sum % 11;
        }
        return vi[remain];
    }
    
    /***
     * 验证URL
     * 
     * @param value
     * @return
     */
    public static boolean validationURL(String value) {
        String pattern = "^((https|http|ftp|rtsp|mms)?://)"
                + "?(([0-9a-z_!~*'().&=+$%-]+: )?[0-9a-z_!~*'().&=+$%-]+@)?" // ftp的user@
                + "(([0-9]{1,3}\\.){3}[0-9]{1,3}" // IP形式的URL- 199.194.52.184
                + "|" // 允许IP和DOMAIN（域名）
                + "([0-9a-z_!~*'()-]+\\.)*" // 域名- www.
                + "([0-9a-z][0-9a-z-]{0,61})?[0-9a-z]\\." // 二级域名
                + "[a-z]{2,6})" // first level domain- .com or .museum
                + "(:[0-9]{1,4})?" // 端口- :80
                + "((/?)|" // a slash isn't required if there is no file name
                + "(/[0-9a-z_!~*'().;?:@&=+$,%#-]+)+/?)$";
        Pattern p = Pattern.compile(pattern);
        Matcher m = p.matcher(value);
        return m.matches();
        
    }
    
    /**
     * 验证对象是否为空
     * 
     * @param s
     * @return 如果为null或者空字符串则返回true,否则返回false
     */
    public static boolean isEmpty(Object o) {
        if (o instanceof String) {
            String s = (String) o;
            if (s == null || "".equals(s.trim())) {
                return true;
            } else {
                return false;
            }
        } else {
            if (o == null) {
                return true;
            } else {
                return false;
            }
        }
    }
    
    /**
     * 判断List对象是否为空,要求list的size大于0;注意这里并没有要求list.get(0)!=null
     * 
     * @param list
     * @return 非空则返回true;空则返回false
     */
    public static boolean listIsNotNull(List list) {
        boolean result = false;
        if (list != null && list.size() > 0) {
            result = true;
        }
        return result;
    }
    
    /**
     * 判断String数组是否为空,要求数组的length大于0;注意这里并没有要求sa[0]!="";
     * 
     * @param sa
     * @return
     */
    public static boolean StringArrayIsNotNull(String[] sa) {
        boolean result = false;
        if (sa != null && sa.length > 0) {
            result = true;
        }
        return result;
    }
    
}

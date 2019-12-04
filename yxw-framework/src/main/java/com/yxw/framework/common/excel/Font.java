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

import java.io.Serializable;

import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.util.HSSFColor;

/**
 * excel字体设置
 * 
 * @author 申午武
 * @version 1.0
 * @since 2015年3月23日
 */
public class Font implements Serializable {
    
    private static final long serialVersionUID = 8616298427240123608L;
    private static Logger logger = Logger.getLogger(Font.class);
    /**
     * HSSFFont对象
     */
    private HSSFFont hssfFont;
    /**
     * fontHeightInPoints 字体高度,默认为10
     */
    private short fontHeightInPoints = 10;
    /**
     * fontName 字体名称,默认为FONT_ARIAL
     */
    private String fontName = HSSFFont.FONT_ARIAL;
    /**
     * fontColor 字体颜色
     */
    private short fontColor;
    /**
     * italic 是否使用斜体,默认为false
     */
    private boolean italic = false;
    /**
     * strikeout 是否使用下划线,默认为false
     */
    private boolean strikeout = false;
    /**
     * URLColor 超链接样式 ,默认为HSSFColor.BLUE.index,当strikeout为false时无效
     */
    private short urlColor = HSSFColor.BLUE.index;
    /**
     * boldWeight 字体加粗,默认为正常字体
     */
    private short boldWeight = HSSFFont.BOLDWEIGHT_NORMAL;
    
    public HSSFFont getCellFont() {
        if (fontHeightInPoints >= 0) {
            hssfFont.setFontHeightInPoints(fontHeightInPoints);
        }
        if (fontName != null && !("").equals(fontName.trim())) {
            hssfFont.setFontName(fontName);
        }
        if (fontColor < 0) {
            hssfFont.setColor(fontColor);
        }
        hssfFont.setBoldweight(boldWeight);
        hssfFont.setItalic(italic);
        hssfFont.setStrikeout(strikeout);
        if (strikeout) {
            hssfFont.setColor(this.urlColor);
        }
        return hssfFont;
    }
    
    public short getFontHeightInPoints() {
        return fontHeightInPoints;
    }
    
    public void setFontHeightInPoints(short fontHeightInPoints) {
        if (fontHeightInPoints >= 0) {
            this.fontHeightInPoints = fontHeightInPoints;
        } else {
            logger.warn("字体高度不能小于0,已自动设置为默认值");
        }
        
    }
    
    public String getFontName() {
        return fontName;
    }
    
    public void setFontName(String fontName) {
        if (fontName != null && !("").equals(fontName.trim())) {
            this.fontName = fontName;
        } else {
            logger.warn("字体名称不能为空,已自动设置为默认值");
        }
        
    }
    
    public short getFontColor() {
        return fontColor;
    }
    
    public void setFontColor(short fontColor) {
        if (fontColor < 0) {
            this.fontColor = fontColor;
        } else {
            logger.warn("字体颜色填充不正确,已自动设置为默认值");
        }
        
    }
    
    public boolean isItalic() {
        return italic;
    }
    
    public void setItalic(boolean italic) {
        this.italic = italic;
    }
    
    public boolean isStrikeout() {
        return strikeout;
    }
    
    public void setStrikeout(boolean strikeout) {
        this.strikeout = strikeout;
    }
    
    public HSSFFont getHssfFont() {
        return hssfFont;
    }
    
    public void setHssfFont(HSSFFont hssfFont) {
        this.hssfFont = hssfFont;
    }
    
    public short getBoldWeight() {
        return boldWeight;
    }
    
    public void setBoldWeight(short boldWeight) {
        this.boldWeight = boldWeight;
    }
    
    public short getUrlColor() {
        return urlColor;
    }
    
    public void setUrlColor(short urlColor) {
        this.urlColor = urlColor;
    }
    
}

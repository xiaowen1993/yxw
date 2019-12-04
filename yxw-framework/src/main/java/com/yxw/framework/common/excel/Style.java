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

import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.IndexedColors;

/**
 * excel样式
 * 
 * @author 申午武
 * @version 1.0
 * @since 2015年3月23日
 */
public class Style implements Serializable {
    private static final long serialVersionUID = 1775359077133274119L;
    /**
     * hssfCellStyle 样式对象
     */
    private HSSFCellStyle hssfCellStyle;
    /**
     * alignment 水平对齐方式,默认居中对齐
     */
    private short alignment = HSSFCellStyle.ALIGN_CENTER;
    /**
     * verticalAlignment 垂直对齐方式,默认居中对齐
     */
    private short verticalAlignment = HSSFCellStyle.VERTICAL_CENTER;
    /**
     * fillForegroundColor 前景色填充,默认为空
     */
    private short fillForegroundColor; // IndexedColors.BLUE.getIndex();
    /**
     * fillForegroundColor 背景色填充 ,默认为空
     */
    private short fillBackgroundColor; // IndexedColors.GREEN.getIndex();
    /**
     * borderBottom 下边框
     */
    private short borderBottom = HSSFCellStyle.BORDER_THIN;
    /**
     * borderBottomColor 下边框颜色
     */
    private short borderBottomColor = IndexedColors.BLACK.getIndex();
    /**
     * borderLeft 左边框
     */
    private short borderLeft = HSSFCellStyle.BORDER_THIN;
    /**
     * borderLeftColor 左边框颜色
     */
    private short borderLeftColor = IndexedColors.BLACK.getIndex();
    /**
     * borderTop 上边框
     */
    private short borderTop = HSSFCellStyle.BORDER_THIN;
    /**
     * borderTopColor 上边框颜色
     */
    private short borderTopColor = IndexedColors.BLACK.getIndex();
    /**
     * borderRight 右边框
     */
    private short borderRight = HSSFCellStyle.BORDER_THIN;
    /**
     * borderRightColor 右边框颜色
     */
    private short borderRightColor = IndexedColors.BLACK.getIndex();
    /**
     * wrapText 是否启用自动换行,默认为true
     */
    private boolean wrapText = true;
    
    public HSSFCellStyle getCellStyle() {
        if (hssfCellStyle != null) {
            // 水平对齐方式
            if (this.alignment > 0) {
                hssfCellStyle.setAlignment(this.alignment);
            }
            // 垂直对齐方式
            if (this.verticalAlignment > 0) {
                hssfCellStyle.setVerticalAlignment(this.verticalAlignment);
            }
            // 背景色填充
            if (this.fillBackgroundColor > 0) {
                hssfCellStyle.setFillBackgroundColor(this.fillBackgroundColor);
                hssfCellStyle.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
            }
            // 前景色填充
            if (this.fillForegroundColor > 0) {
                hssfCellStyle.setFillForegroundColor(this.fillForegroundColor);
                hssfCellStyle.setFillPattern(CellStyle.BIG_SPOTS);
            }
            // 设置边框
            if (this.borderBottom > 0) {
                hssfCellStyle.setBorderBottom(this.borderBottom); // 下边框
            }
            if (this.borderLeft > 0) {
                hssfCellStyle.setBorderLeft(this.borderLeft); // 左边框
            }
            if (this.borderTop > 0) {
                hssfCellStyle.setBorderTop(this.borderTop); // 上边框
            }
            if (this.borderRight > 0) {
                hssfCellStyle.setBorderRight(this.borderRight); // 右边框
            }
            if (this.borderBottomColor > 0) {
                hssfCellStyle.setBottomBorderColor(this.borderBottomColor);
            }
            if (this.borderLeftColor > 0) {
                hssfCellStyle.setLeftBorderColor(this.borderLeftColor);
            }
            if (this.borderRightColor > 0) {
                hssfCellStyle.setRightBorderColor(this.borderRightColor);
            }
            if (this.borderTopColor > 0) {
                hssfCellStyle.setTopBorderColor(this.borderTopColor);
            }
            // 设置自动换行
            hssfCellStyle.setWrapText(wrapText);
        }
        return hssfCellStyle;
    }
    
    public short getAlignment() {
        return alignment;
    }
    
    public void setAlignment(short alignment) {
        this.alignment = alignment;
    }
    
    public short getVerticalAlignment() {
        return verticalAlignment;
    }
    
    public void setVerticalAlignment(short verticalAlignment) {
        this.verticalAlignment = verticalAlignment;
    }
    
    public short getFillForegroundColor() {
        return fillForegroundColor;
    }
    
    public void setFillForegroundColor(short fillForegroundColor) {
        this.fillForegroundColor = fillForegroundColor;
    }
    
    public short getFillBackgroundColor() {
        return fillBackgroundColor;
    }
    
    public void setFillBackgroundColor(short fillBackgroundColor) {
        this.fillBackgroundColor = fillBackgroundColor;
    }
    
    public short getBorderBottom() {
        return borderBottom;
    }
    
    public void setBorderBottom(short borderBottom) {
        this.borderBottom = borderBottom;
    }
    
    public short getBorderLeft() {
        return borderLeft;
    }
    
    public void setBorderLeft(short borderLeft) {
        this.borderLeft = borderLeft;
    }
    
    public short getBorderTop() {
        return borderTop;
    }
    
    public void setBorderTop(short borderTop) {
        this.borderTop = borderTop;
    }
    
    public short getBorderRight() {
        return borderRight;
    }
    
    public void setBorderRight(short borderRight) {
        this.borderRight = borderRight;
    }
    
    public boolean isWrapText() {
        return wrapText;
    }
    
    public void setWrapText(boolean wrapText) {
        this.wrapText = wrapText;
    }
    
    public short getBorderBottomColor() {
        return borderBottomColor;
    }
    
    public void setBorderBottomColor(short borderBottomColor) {
        this.borderBottomColor = borderBottomColor;
    }
    
    public short getBorderLeftColor() {
        return borderLeftColor;
    }
    
    public void setBorderLeftColor(short borderLeftColor) {
        this.borderLeftColor = borderLeftColor;
    }
    
    public short getBorderTopColor() {
        return borderTopColor;
    }
    
    public void setBorderTopColor(short borderTopColor) {
        this.borderTopColor = borderTopColor;
    }
    
    public short getBorderRightColor() {
        return borderRightColor;
    }
    
    public void setBorderRightColor(short borderRightColor) {
        this.borderRightColor = borderRightColor;
    }
    
    public void setHssfCellStyle(HSSFCellStyle hssfCellStyle) {
        this.hssfCellStyle = hssfCellStyle;
    }
    
    public HSSFCellStyle getHssfCellStyle() {
        return hssfCellStyle;
    }
    
}

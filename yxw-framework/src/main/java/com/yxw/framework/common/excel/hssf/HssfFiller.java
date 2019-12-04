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

import java.util.Calendar;
import java.util.Date;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFDataFormat;
import org.apache.poi.hssf.usermodel.HSSFHyperlink;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.ss.util.CellRangeAddress;

import com.yxw.framework.common.excel.Filler;
import com.yxw.framework.common.validation.Validation;
import com.yxw.framework.exception.SystemException;

/**
 * <p>
 * 单元格填充.
 * </p>
 * <p>
 * 单元格填充. 支持基本数据类型,URL,合并单元格的填充,自动转换单元格的数据格式,自动替换空值,暂不支持图片和注释的填充
 * </p>
 * 
 * @author 申午武
 * @version 1.0
 * @since 2015年3月23日
 */
public class HssfFiller implements Filler {
    
    /***
     * 填充单元格
     * 
     * @param beginCellIndex
     *            开始单元格索引
     * @param hssfFillerInfo
     *            填充属性
     * @param value
     *            值
     * @return
     */
    @Override
    public void filler(HSSFRow row, int beginCellIndex, HssfFillerInfo hssfFillerInfo, Object value) {
        HSSFCell cell = row.createCell(beginCellIndex, hssfFillerInfo.getEncode());
        HSSFDataFormat hssfDataFormat = null;
        // 合并
        if (hssfFillerInfo.isMerge()) {
            if (row.getRowNum() + hssfFillerInfo.getMergeRowNum() > 65535) {
                throw new SystemException("合并行超过excel最大行数65535");
            }
            if (beginCellIndex + hssfFillerInfo.getMergeCellNum() > 255) {
                throw new SystemException("合并列超过excel最大列数255");
            }
            CellRangeAddress cellRangeAddress = new CellRangeAddress(row.getRowNum(), row.getRowNum()
                    + hssfFillerInfo.getMergeRowNum(), beginCellIndex, beginCellIndex
                    + hssfFillerInfo.getMergeCellNum());
            cell.getSheet().addMergedRegion(cellRangeAddress);
            mergeStyle(row.getRowNum(), hssfFillerInfo.getMergeRowNum(), beginCellIndex,
            // 设置合并单元格后的样式
                    hssfFillerInfo.getMergeCellNum(), row, hssfFillerInfo);
        }
        
        // 填充单元格
        if (value != null && !("").equals(value.toString().trim())) {
            if (value instanceof Integer) {
                String tempValue = String.valueOf(value);
                if (hssfFillerInfo.isIntConversion()) {
                    if (hssfFillerInfo.getIntConversionMap().containsKey(tempValue)) {
                        cell.setCellValue(hssfFillerInfo.getIntConversionMap().get(tempValue));
                    }
                } else {
                    cell.setCellValue(Integer.valueOf(tempValue));
                }
            } else if (value instanceof Double) {
                String tempValue = String.valueOf(value);
                cell.setCellValue(Double.valueOf(tempValue));
                if (hssfFillerInfo.getDoubleFormat() != null && !("").equals(hssfFillerInfo.getDoubleFormat().trim())) {
                    hssfDataFormat = cell.getRow().getSheet().getWorkbook().createDataFormat();
                    hssfFillerInfo.getStyle().getHssfCellStyle()
                            .setDataFormat(hssfDataFormat.getFormat(hssfFillerInfo.getDoubleFormat()));
                }
            } else if (value instanceof Float) {
                String tempValue = String.valueOf(value);
                cell.setCellValue(Float.valueOf(tempValue));
                if (hssfFillerInfo.getFloatFormat() != null && !("").equals(hssfFillerInfo.getFloatFormat().trim())) {
                    hssfDataFormat = cell.getRow().getSheet().getWorkbook().createDataFormat();
                    hssfFillerInfo.getStyle().getHssfCellStyle()
                            .setDataFormat(hssfDataFormat.getFormat(hssfFillerInfo.getFloatFormat()));
                }
            } else if (value instanceof Calendar) {
                cell.setCellValue((Calendar) (value));
                if (hssfFillerInfo.getCalendarFormat() != null
                        && !("").equals(hssfFillerInfo.getCalendarFormat().trim())) {
                    hssfDataFormat = cell.getRow().getSheet().getWorkbook().createDataFormat();
                    hssfFillerInfo.getStyle().getHssfCellStyle()
                            .setDataFormat(hssfDataFormat.getFormat(hssfFillerInfo.getCalendarFormat()));
                }
            } else if (value instanceof Boolean) {
                boolean tempValue = Boolean.valueOf(String.valueOf(value));
                if (hssfFillerInfo.isBooleanConversion()) {
                    if (tempValue) {
                        cell.setCellValue(hssfFillerInfo.getTrueConversion());
                    } else {
                        cell.setCellValue(hssfFillerInfo.getFalseConversion());
                    }
                } else {
                    cell.setCellValue(tempValue);
                }
            } else if (value instanceof Date) {
                cell.setCellValue(((Date) (value)));
                if (hssfFillerInfo.getDateFormat() != null && !("").equals(hssfFillerInfo.getDateFormat().trim())) {
                    hssfDataFormat = cell.getRow().getSheet().getWorkbook().createDataFormat();
                    hssfFillerInfo.getStyle().getHssfCellStyle()
                            .setDataFormat(hssfDataFormat.getFormat(hssfFillerInfo.getDateFormat()));
                }
            } else if (Validation.validationURL(value.toString())) {
                HSSFHyperlink hssfHyperlink = new HSSFHyperlink(HSSFHyperlink.LINK_URL);
                hssfHyperlink.setAddress(value.toString());
                hssfFillerInfo.getFont().setStrikeout(true);
                cell.setHyperlink(hssfHyperlink);
                cell.setCellValue(value.toString());
            } else {
                cell.setCellValue(String.valueOf(value));
            }
        } else {
            cell.setCellValue(hssfFillerInfo.getBlankReplace());
        }
        if (hssfFillerInfo.getFont() != null) {
            // 把字体属性装载到style
            hssfFillerInfo.getStyle().getHssfCellStyle().setFont(hssfFillerInfo.getFont().getCellFont());
        }
        if (!hssfFillerInfo.isMerge()) {
            // 装载sytle属性并填充到cell中
            cell.setCellStyle(hssfFillerInfo.getStyle().getCellStyle());
        }
        // cell.getRow().getSheet().autoSizeColumn(beginCellIndex);
        
    }
    
    private void mergeStyle(int beginRowNum, int mergeRowNum, int beginCellIndex, int mergeCellNum, HSSFRow row,
            HssfFillerInfo hssfFillerInfo) {
        // 设置合并的单元格样式
        for (int i = beginRowNum; i <= beginRowNum + mergeRowNum; i++) {
            HSSFRow row_temp = getRow(row.getSheet(), i);
            for (int j = beginCellIndex; j <= beginCellIndex + mergeCellNum; j++) {
                HSSFCell cell_temp = row_temp.getCell(j);
                if (cell_temp == null) {
                    cell_temp = row_temp.createCell(j);
                }
                cell_temp.setCellStyle(hssfFillerInfo.getStyle().getCellStyle());
            }
        }
        
    }
    
    private HSSFRow getRow(HSSFSheet sheet, int rowIndex) {
        HSSFRow row = sheet.getRow(rowIndex);
        if (row == null) {
            row = sheet.createRow(rowIndex);
        }
        return row;
    }
    
}

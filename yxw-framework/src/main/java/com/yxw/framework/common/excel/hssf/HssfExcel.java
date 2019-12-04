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

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.tools.zip.ZipEntry;
import org.apache.tools.zip.ZipOutputStream;

import com.yxw.framework.common.excel.Filler;
import com.yxw.framework.config.SystemConfig;
import com.yxw.framework.config.SystemConstants;

/**
 * 生成excel
 * 
 * @author 申午武
 * @version 1.0
 * @since 2015年3月23日
 */
public class HssfExcel {
    private static Logger logger = Logger.getLogger(HssfExcel.class);
    /**
     * 缓冲大小为1024*16
     */
    private static final int BUFFER_SIZE = 1024 * 16;
    /**
     * maxRowInSheet 一张表的最大行数,默认为65535
     */
    private static final int MAX_ROW_IN_SHEET = 65535;
    /**
     * maxColumnInSheet 一个工作薄的最大列数,默认为65535
     */
    private static final int MAX_COLUMN_IN_SHEET = 255;
    /**
     * customMaxRowInSheet 自定义一张表的最大行数,默认为10000,该值不能大于MAX_ROW_IN_SHEET
     */
    private int customMaxRowInSheet = 10000;
    /**
     * customMaxColumnInSheet 自定义一张表的最大列数,默认为50,该值不能大于MAX_COLUMN_IN_SHEET
     */
    private int customMaxColumnInSheet = 50;
    /**
     * isZip 是否打压缩包,默认为false,当有多个文件生成时,必须打压缩包
     */
    private boolean isZip = false;
    /**
     * isNewSheet 当数据量达到customMaxRowInSheet值时,是newSheet还是newWorkbook. true: newSheet;false: newWorkbook; 默认为true.
     * 
     */
    private boolean isNewSheet = true;
    /**
     * sheetName 表名称,默认为"Sheet"
     */
    private String sheetName = "Sheet";
    /**
     * 全局当前行索引
     */
    private int currentRowIndex = 0;
    /**
     * 全局sheet索引
     */
    private int sheetIndex = 1;
    /**
     * 填充器
     */
    private Filler filler = new HssfFiller();
    /**
     * hssfWorkbooks
     */
    private List<HSSFWorkbook> hssfWorkbooks = new ArrayList<HSSFWorkbook>();
    /**
     * 文件名
     */
    private String fileName = null;
    /**
     * 全局sheet
     */
    private HSSFSheet hssfSheet = null;
    /**
     * 全局样式
     */
    private HSSFCellStyle hssfCellStyle = null;
    /**
     * 全局字体
     */
    private HSSFFont hssfFont = null;
    
    public HssfExcel() {
        HSSFWorkbook hssfWorkbook = new HSSFWorkbook();
        this.hssfWorkbooks.add(hssfWorkbook);
        HSSFSheet sheet = hssfWorkbook.createSheet(this.sheetName + this.sheetIndex);
        this.hssfSheet = sheet;
    }
    
    public HssfExcel(String sheetName) {
        HSSFWorkbook hssfWorkbook = new HSSFWorkbook();
        this.hssfWorkbooks.add(hssfWorkbook);
        this.sheetName = sheetName;
        HSSFSheet sheet = hssfWorkbook.createSheet(this.sheetName + this.sheetIndex);
        this.hssfSheet = sheet;
    }
    
    /**
     * 下载
     * 
     * @param fileName
     * @param response
     * @return
     */
    public void generateExcel(String fileName, HttpServletResponse response) {
        OutputStream outputStream = null;
        ZipOutputStream zipOutputStream = null;
        try {
            response.reset();
            response.setContentType("applcation/octet-stream");
            if (isZip || hssfWorkbooks.size() > 1) {
                this.fileName = fileName + ".zip";
            } else {
                this.fileName = fileName + ".xls";
            }
            response.setHeader("Content-Disposition",
                    "attachment; filename=\"" + new String(this.fileName.getBytes("gb2312"), "iso8859-1") + "\"");
            outputStream = response.getOutputStream();
            BufferedOutputStream out = new BufferedOutputStream(outputStream, BUFFER_SIZE);
            // 是否打压缩包
            if (isZip || hssfWorkbooks.size() > 1) {
                zipOutputStream = new ZipOutputStream(out);
                zipOutputStream.setEncoding("utf-8");
                for (int i = 0; i < hssfWorkbooks.size(); i++) {
                    HSSFWorkbook hssfWorkbook = hssfWorkbooks.get(i);
                    zipOutputStream.putNextEntry(new ZipEntry(fileName + (i + 1) + ".xls"));
                    hssfWorkbook.write(zipOutputStream);
                    zipOutputStream.flush();
                }
            } else {
                HSSFWorkbook hssfWorkbook = hssfWorkbooks.get(0);
                hssfWorkbook.write(outputStream);
                outputStream.flush();
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (zipOutputStream != null) {
                    zipOutputStream.close();
                }
                if (outputStream != null) {
                    outputStream.close();
                }
                
            } catch (IOException e) {
                e.printStackTrace();
            }
            
        }
        
    }
    
    public void generateExcel(String fileName) {
        OutputStream outputStream = null;
        ZipOutputStream zipOutputStream = null;
        try {
            if (isZip || hssfWorkbooks.size() > 1) {
                this.fileName = fileName + ".zip";
            } else {
                this.fileName = fileName + ".xls";
            }
            File file = new File(SystemConfig.getStringValue("report_file_path") + SystemConstants.FILE_SEPARATOR
                    + this.fileName);
            if (!file.getParentFile().exists()) {
                file.getParentFile().mkdirs();
                file.createNewFile();
            } else {
                file.createNewFile();
            }
            outputStream = new BufferedOutputStream(new FileOutputStream(file), BUFFER_SIZE);
            // 是否打压缩包
            if (isZip || hssfWorkbooks.size() > 1) {
                zipOutputStream = new ZipOutputStream(outputStream);
                zipOutputStream.setEncoding("utf-8");
                for (int i = 0; i < hssfWorkbooks.size(); i++) {
                    HSSFWorkbook hssfWorkbook = hssfWorkbooks.get(i);
                    zipOutputStream.putNextEntry(new ZipEntry(fileName + (i + 1) + ".xls"));
                    hssfWorkbook.write(zipOutputStream);
                    zipOutputStream.flush();
                }
            } else {
                HSSFWorkbook hssfWorkbook = hssfWorkbooks.get(0);
                hssfWorkbook.write(outputStream);
                outputStream.flush();
            }
        } catch (IOException e1) {
            e1.printStackTrace();
        } finally {
            try {
                if (zipOutputStream != null) {
                    zipOutputStream.close();
                }
                if (outputStream != null) {
                    outputStream.close();
                }
                
            } catch (IOException e) {
                e.printStackTrace();
            }
            
        }
        
    }
    
    public HSSFCellStyle initHSSFCellStyle() {
        int size = hssfWorkbooks.size();
        return this.hssfWorkbooks.get(size - 1).createCellStyle();
    }
    
    public HSSFFont initHSSFFont() {
        int size = hssfWorkbooks.size();
        return this.hssfWorkbooks.get(size - 1).createFont();
    }
    
    public void cellFiller(int beginRowIndex, int beginCellIndex, HssfFillerInfo hssfFillerInfo, Object value) {
        HSSFWorkbook hssfWorkbook = null;
        hssfFillerInfo.getStyle().setHssfCellStyle(initHSSFCellStyle());
        hssfFillerInfo.getFont().setHssfFont(initHSSFFont());
        // 超出最大行数后的处理
        beginRowIndex = exceedMaxRowIndexProcess(hssfWorkbook, beginRowIndex, hssfFillerInfo);
        HSSFRow row = null;
        if (this.hssfSheet.getRow(beginRowIndex) != null) {
            row = this.hssfSheet.getRow(beginRowIndex);
        } else {
            row = this.hssfSheet.createRow(beginRowIndex);
        }
        // 超出最大列数之后的处理
        if (hssfFillerInfo.isMerge()) {
            exceedMaxColumnIndexProcess(beginCellIndex + hssfFillerInfo.getMergeCellNum());
        } else {
            exceedMaxColumnIndexProcess(beginCellIndex);
        }
        filler.filler(row, beginCellIndex, hssfFillerInfo, value);
    }
    
    public void rowFiller(int beginRowIndex, int beginCellIndex, HssfFillerInfo hssfFillerInfo, Object[] values) {
        HSSFWorkbook hssfWorkbook = null;
        hssfFillerInfo.getStyle().setHssfCellStyle(initHSSFCellStyle());
        hssfFillerInfo.getFont().setHssfFont(initHSSFFont());
        exceedMaxRowIndexProcess(hssfWorkbook, beginRowIndex, hssfFillerInfo);
        HSSFRow row = this.hssfSheet.createRow(beginRowIndex);
        // 超出最大列数之后的处理
        if (hssfFillerInfo.isMerge()) {
            exceedMaxColumnIndexProcess(beginCellIndex + values.length * hssfFillerInfo.getMergeCellNum());
        } else {
            exceedMaxColumnIndexProcess(values.length);
        }
        for (int j = 0; j < values.length; j++) {
            filler.filler(row, beginCellIndex, hssfFillerInfo, values[j]);
        }
        
    }
    
    /**
     * 列表填充
     * 
     * @param sheet
     * @param lineSpacingIndex
     *            行间隔
     * @param fillerInfo
     *            填充器属性
     * @param list
     *            值
     */
    public void listFiller(int beginRowIndex, int beginCellIndex, HssfFillerInfo hssfFillerInfo, List<Object[]> values) {
        // 当前单元格索引
        int currentCellIndex = beginCellIndex;
        HSSFWorkbook hssfWorkbook = null;
        hssfFillerInfo.getStyle().setHssfCellStyle(initHSSFCellStyle());
        hssfFillerInfo.getFont().setHssfFont(initHSSFFont());
        for (int i = 0; i < values.size(); i++) {
            exceedMaxRowIndexProcess(hssfWorkbook, beginRowIndex, hssfFillerInfo);
            HSSFRow row = this.hssfSheet.createRow(beginRowIndex);
            Object[] value = values.get(i);
            // 超出最大列数之后的处理
            if (hssfFillerInfo.isMerge()) {
                exceedMaxColumnIndexProcess(currentCellIndex + value.length * hssfFillerInfo.getMergeCellNum());
            } else {
                exceedMaxColumnIndexProcess(value.length);
            }
            for (int j = 0; j < value.length; j++) {
                // 合并单元格计算
                if (hssfFillerInfo.isMerge()) {
                    try {
                        filler.filler(row, currentCellIndex, hssfFillerInfo, value[j]);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    // 当前单元格索引=合并单元格数+当前单元格索引
                    currentCellIndex = hssfFillerInfo.getMergeCellNum() + currentCellIndex;
                } else {
                    try {
                        filler.filler(row, currentCellIndex, hssfFillerInfo, value[j]);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                // 一个单元格填充完后,当前单元格索引定位到下一个单元格
                currentCellIndex++;
            }
            // 一行填充完后,currentCellIndex初始化为benginCellIndex
            currentCellIndex = beginCellIndex;
            // 合并行计算
            if (hssfFillerInfo.isMerge()) {
                // 当前行索引=合并行数+当前行索引
                this.currentRowIndex = hssfFillerInfo.getMergeRowNum() + this.currentRowIndex;
            }
            // 一行填充完之后,当前行索引定位到下一行
            this.currentRowIndex++;
        }
        
    }
    
    /**
     * 超出最大行数时的处理
     * 
     * @param currentRowIndex
     *            当前行索引
     * @param sheetIndex
     *            sheet索引
     * @param hssfWorkbook
     * @param currentHssfSheet
     *            当前sheet
     * @return
     */
    private int exceedMaxRowIndexProcess(HSSFWorkbook hssfWorkbook, int currentRowIndex, HssfFillerInfo hssfFillerInfo) {
        // 当前行索引超出自定义的表的最大行数时,新建一张表或者工作薄.当前行索引清零
        if (hssfFillerInfo.isMerge()) {
            currentRowIndex = currentRowIndex + hssfFillerInfo.getMergeRowNum();
        }
        if (currentRowIndex >= this.customMaxRowInSheet) {
            if (isNewSheet) {
                this.sheetIndex++;
                this.hssfSheet = this.hssfSheet.getWorkbook().createSheet(this.sheetName + sheetIndex);
            } else {
                hssfWorkbook = new HSSFWorkbook();
                this.hssfWorkbooks.add(hssfWorkbook);
                this.hssfSheet = hssfWorkbook.createSheet(this.sheetName);
                this.hssfCellStyle = hssfWorkbook.createCellStyle();
                this.hssfFont = hssfWorkbook.createFont();
            }
            currentRowIndex = 0;
        }
        
        return currentRowIndex;
        
    }
    
    /**
     * 列索引是否超出最大列索引后的处理
     * 
     * @param columnIndex
     *            列索引
     */
    private void exceedMaxColumnIndexProcess(int columnIndex) throws IndexOutOfBoundsException {
        if (columnIndex >= this.customMaxColumnInSheet) {
            logger.error("列的长度索引超出了excel最大的列索引,excel生成失败");
            throw new IndexOutOfBoundsException("列的长度索引超出了excel最大的列索引,excel生成失败");
        }
    }
    
    public boolean isZip() {
        return isZip;
    }
    
    public void setZip(boolean isZip) {
        this.isZip = isZip;
    }
    
    public boolean isNewSheet() {
        return isNewSheet;
    }
    
    public void setNewSheet(boolean isNewSheet) {
        this.isNewSheet = isNewSheet;
    }
    
    public int getCustomMaxRowInSheet() {
        return customMaxRowInSheet;
    }
    
    public void setCustomMaxRowInSheet(int customMaxRowInSheet) {
        if (customMaxRowInSheet > 0 && customMaxRowInSheet <= MAX_ROW_IN_SHEET) {
            this.customMaxRowInSheet = customMaxRowInSheet;
        } else {
            this.customMaxRowInSheet = MAX_ROW_IN_SHEET;
            logger.info("customMaxRowInSheet小于0或者超出MAX_ROW_IN_SHEET,customMaxRowInSheet已被自动调整为MAX_ROW_IN_SHEET");
        }
        
    }
    
    public int getCustomMaxColumnInSheet() {
        return customMaxColumnInSheet;
    }
    
    public void setCustomMaxColumnInSheet(int customMaxColumnInSheet) {
        if (customMaxColumnInSheet > 0 && customMaxColumnInSheet <= MAX_ROW_IN_SHEET) {
            this.customMaxColumnInSheet = customMaxColumnInSheet;
        } else {
            this.customMaxColumnInSheet = MAX_COLUMN_IN_SHEET;
            logger.info("customMaxColumnInSheet小于0或者超出MAX_COLUMN_IN_SHEET,customMaxColumnInSheet已被自动调整为MAX_COLUMN_IN_SHEET");
        }
    }
    
}

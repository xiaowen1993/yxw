package com.yxw.utils;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFDataFormat;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.VerticalAlignment;
import org.apache.poi.ss.util.CellRangeAddress;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

/**
 * excel2003 使用HSSF
 * @author Administrator
 *
 */
public class ExcelExportUtils {
	/**
	 * 导出excel文件的byte[]
	 * @param data	页面的table
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static byte[] getExcelData(String data) {
		byte[] results = null;
		String sData = convertData(data);

		Document doc = null;
		HSSFWorkbook workbook = null;
		try {
			// 生成dom对象
			doc = DocumentHelper.parseText(sData);
			// 拿到标题栏
			List<Element> theads = doc.selectNodes("/table/thead/tr/th");

			// 处理thead -- tr->th
			// 创建表格
			workbook = new HSSFWorkbook();
			// 创建sheet
			HSSFSheet sheet = workbook.createSheet("demo1");
			// 宽度设置
			sheet.setDefaultColumnWidth(20);
			// 设置高度 -- 一般不设置，使用默认就好
			// sheet.setDefaultRowHeight((short) 350);
			// 设置单元格style
			HSSFCellStyle commonStyle = getDefaultCellStyle(workbook);
			HSSFCellStyle titleStyle = getDefaultTitleCellStyle(workbook);
			HSSFRow titleRow = sheet.createRow(0);
			// 处理标题行
			handleTitle(titleRow, theads, titleStyle);
			// 拿到每一行数据
			List<Element> tbodys = doc.selectNodes("/table/tbody/tr");
			// 处理主体
			handleBody(sheet, tbodys, commonStyle);
			
			ByteArrayOutputStream bos = new ByteArrayOutputStream();
			workbook.write(bos);
			results = bos.toByteArray();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (workbook != null) {
				try {
					workbook.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}

		return results;
	}

	/**
	 * 标题行，不允许跨行，只能跨列（有一定局限性，后面视情况改进）
	 * 出现合并单元格的，只需要设定第一个单元格的值，将作为合并后单元格的值，取值也是一样的
	 * @param row
	 * @param elements
	 * @param style
	 */
	private static void handleTitle(HSSFRow row, List<Element> elements, HSSFCellStyle style) {
		System.out.println("----------------------------- 处理title ------------------------------");
		long time = System.currentTimeMillis();
		int totalCols = 0; // 总列数
		for (Element element : elements) {
			// cell填充的第一个位置
			int lastCellNum = row.getLastCellNum();

			lastCellNum = lastCellNum == -1 ? 0 : lastCellNum;
			// System.err.println(lastCellNum);
			// 当前行号
			int rowNum = row.getRowNum();
			String colspanValue = element.attributeValue("colspan");
			if (StringUtils.isBlank(colspanValue)) {
				colspanValue = element.attributeValue("colSpan");
			}
			int colspan = NumberUtils.toInt(colspanValue, 1);

			CellRangeAddress address = null;
			if (colspan > 1) {
				address = new CellRangeAddress(rowNum, rowNum, totalCols, totalCols + colspan - 1);
			}

			if (address != null) {
				row.getSheet().addMergedRegion(address);
			}

			totalCols += colspan; // 新增列数

			HSSFCell cell = row.createCell(totalCols - colspan);
			// cell.setCellStyle(style);
			cell.setCellValue(element.getText());

			// System.out.println(row.getFirstCellNum() + " - " + row.getLastCellNum());
		}

		// 统一设置风格
		for (int index = 0; index <= row.getLastCellNum(); index++) {
			HSSFCell cell = row.getCell(index);
			if (cell != null) {
				cell.setCellStyle(style);
			}
		}

		System.out.println("----------------------------- 处理title完毕, 共花费 " + (System.currentTimeMillis() - time)
				+ " ms ------------------------------");
	}

	@SuppressWarnings("unchecked")
	private static void handleBody(HSSFSheet sheet, List<Element> elements, HSSFCellStyle style) {
		System.out.println("----------------------------- 处理body ------------------------------");
		long time = System.currentTimeMillis();

		for (Element rowElement : elements) {
			// 防止没有数据，如<tr />
			if (!rowElement.hasContent())
				continue;

			HSSFRow row = sheet.createRow(sheet.getLastRowNum() + 1);
			// printElements(elements);
			List<Element> tdElements = rowElement.selectNodes("./td");
			// printElements(tdElements);
			for (Element tdElement : tdElements) {
				int colNum = row.getLastCellNum();
				colNum = colNum == -1 ? 0 : colNum;

				/* ---------------------------------- 合并处理 ----------------------------------- */
				// 当前行号
				int rowNum = row.getRowNum();
				// 跨列
				String colspanValue = tdElement.attributeValue("colspan");
				if (StringUtils.isBlank(colspanValue)) {
					colspanValue = tdElement.attributeValue("colSpan");
				}
				int colspan = NumberUtils.toInt(colspanValue, 1);
				// 跨行
				String rowspanValue = tdElement.attributeValue("rowspan");
				if (StringUtils.isBlank(rowspanValue)) {
					rowspanValue = tdElement.attributeValue("rowSpan");
				}
				int rowspan = NumberUtils.toInt(rowspanValue, 1);
				// 合并对象
				CellRangeAddress address = null;
				if (colspan > 1 && rowspan > 1) { // 行列合并
					address = new CellRangeAddress(rowNum, rowNum + rowspan - 1, colNum, colNum + colspan - 1);
				} else if (colspan > 1) { // 列合并
					address = new CellRangeAddress(rowNum, rowNum, colNum, colNum + colspan - 1);
				} else if (rowspan > 1) { // 行合并
					address = new CellRangeAddress(rowNum, rowNum + rowspan - 1, colNum, colNum);
				} else {
					// 不进行任何处理
				}

				if (address != null) {
					sheet.addMergedRegion(address);
				}

				int tempCount = 0;
				while (true) {
					// System.out.println(tdElement.getStringValue() + " - " + tempCount);
					HSSFCell cell = row.createCell(colNum + tempCount);
					cell.setCellStyle(style);
					cell.setCellValue(tdElement.getStringValue());

					// 往下一个
					tempCount++;

					if (!isCombileCell(cell, sheet)) {
						break;
					}
				}
			}

			// 统一设置风格
			for (int index = 0; index <= row.getLastCellNum(); index++) {
				HSSFCell cell = row.getCell(index);
				if (cell != null) {
					cell.setCellStyle(style);
				}
			}
		}

		setAllRegionStyle(sheet, style);

		System.out.println("----------------------------- 处理body完毕, 共花费 " + (System.currentTimeMillis() - time)
				+ " ms ------------------------------");
	}

	private static void setAllRegionStyle(HSSFSheet sheet, HSSFCellStyle style) {
		List<CellRangeAddress> addrList = sheet.getMergedRegions();
		for (CellRangeAddress region : addrList) {
			for (int i = region.getFirstRow(); i <= region.getLastRow(); i++) {

				HSSFRow row = sheet.getRow(i);
				if (row == null)
					row = sheet.createRow(i);
				for (int j = region.getFirstColumn(); j <= region.getLastColumn(); j++) {
					HSSFCell cell = row.getCell(j);
					if (cell == null) {
						cell = row.createCell(j);
						cell.setCellValue("");
					}
					cell.setCellStyle(style);
				}
			}
		}
	}

	private static boolean isCombileCell(HSSFCell cell, HSSFSheet sheet) {
		boolean result = false;

		// 原始单元格
		int rowIndex = cell.getRowIndex();
		int colIndex = cell.getColumnIndex();

		// 遍历合并数据
		List<CellRangeAddress> addrList = sheet.getMergedRegions();
		for (CellRangeAddress address : addrList) {
			if (address.containsColumn(colIndex) && address.containsRow(rowIndex)) {
				result = !(address.getFirstColumn() == colIndex && address.getFirstRow() == rowIndex);
			}
		}

		return result;
	}

	private static HSSFCellStyle getDefaultCellStyle(HSSFWorkbook workbook) {
		HSSFCellStyle style = workbook.createCellStyle();
		// 水平居中
		style.setAlignment(HorizontalAlignment.CENTER);
		// 垂直居中
		style.setVerticalAlignment(VerticalAlignment.CENTER);
		// 字体
		HSSFFont font = workbook.createFont();
		font.setFontHeightInPoints((short) 11);
		// font.setBold(true);
		style.setFont(font);
		// 设置单元格格式 -- 文本格式
		HSSFDataFormat format = workbook.createDataFormat();
		style.setDataFormat(format.getFormat("@"));
		// 设置边框
		style.setBorderTop(BorderStyle.THIN);
		style.setBorderLeft(BorderStyle.THIN);
		style.setBorderRight(BorderStyle.THIN);
		style.setBorderBottom(BorderStyle.THIN);
		return style;
	}

	private static HSSFCellStyle getDefaultTitleCellStyle(HSSFWorkbook workbook) {
		HSSFCellStyle style = workbook.createCellStyle();
		// 水平居中
		style.setAlignment(HorizontalAlignment.CENTER);
		// 垂直居中
		style.setVerticalAlignment(VerticalAlignment.CENTER);
		// 字体
		HSSFFont font = workbook.createFont();
		font.setFontHeightInPoints((short) 13);
		font.setBold(true);
		style.setFont(font);
		// 设置单元格格式 -- 文本格式 -> 这样数据才不会变..
		HSSFDataFormat format = workbook.createDataFormat();
		style.setDataFormat(format.getFormat("@"));
		// 设置边框
		style.setBorderTop(BorderStyle.THIN);
		style.setBorderLeft(BorderStyle.THIN);
		style.setBorderRight(BorderStyle.THIN);
		style.setBorderBottom(BorderStyle.THIN);
		return style;
	}
	
	private static String convertData(String data) {
		String result = data;

		Pattern pattern = Pattern.compile("<T\\w \\w+pan=\\d+>");
		Matcher matcher = pattern.matcher(data);
		while (matcher.find()) {
			String temp = matcher.group();
			int index = temp.indexOf("pan=");
			String sReplace = temp.substring(0, index + 4).concat("\"").concat(temp.substring(index + 4, temp.length() - 1)).concat("\">");
			result = result.replaceAll(temp, sReplace);
		}
		
		// 标签内，全小写
		pattern = Pattern.compile("<(S*?)[^>]*>.*?|<.*? />");
		matcher = pattern.matcher(result);
		while (matcher.find()) {
			String temp = matcher.group();
			result = result.replaceAll(temp, temp.toLowerCase());
		}

		return result;
	}
}

/**
 * <html>
 * <body>
 *  <P>  Copyright(C)版权所有 - 2015 广州医享网络科技发展有限公司.</p>
 *  <p>  All rights reserved.</p>
 *  <p> Created on 2015年3月13日</p>
 *  <p> Created by caiwq</p>
 *  </body>
 * </html>
 */
package com.yxw.framework.common.attach;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.apache.commons.fileupload.FileItem;
import org.apache.log4j.Logger;
import org.springframework.web.multipart.MultipartFile;

import com.yxw.framework.config.SystemConfig;
import com.yxw.framework.config.SystemConstants;
import com.yxw.framework.exception.SystemException;
import com.yxw.utils.DateUtils;
import com.yxw.utils.StringUtils;

/**
 * @author caiwq
 * @version 1.0
 */
public class FileHelper {
	protected static Logger logger = Logger.getLogger(FileHelper.class);
	// private final static int BIG_BUFFER_SIZE = 1024 * 1024 * 10;// 10MB
	/**
	 * 缓冲大小为1024*16
	 */
	private final static int BUFFER_SIZE = 1024 * 16;

	public static String DISK = null;
	public static String SAVE_DIR = SystemConfig.getStringValue("save_dir");

	/**
	 * 
	 * @param
	 * @return
	 */
	public static List<FileInfo> saveFiles(List<FileItem> attachs) throws NullPointerException {
		if (attachs == null || attachs.size() <= 0) {
			return null;
		}
		int size = attachs.size();
		List<FileInfo> fileInfos = new ArrayList<FileInfo>(size);
		for (int i = 0; i < size; i++)
			fileInfos.add(saveFile(attachs.get(i)));
		return fileInfos;
	}

	/**
	 * 
	 * @param file
	 * @param appType
	 * @return
	 */
	public static FileInfo saveFile(FileItem file) {
		if (file == null) {
			logger.error("file is null");
			throw new SystemException("待保存的文件不能为空");
		}

		FileInfo fileInfo = new FileInfo();
		// 原始名称
		String originalName = file.getName();
		// 文件大小
		Long fileSize = Long.valueOf(file.getSize());
		// 文件后缀名
		String extName = getFileExt(originalName);
		// 真实名称(20位唯一标识符)
		String archiveName = StringUtils.randomString(20) + extName;
		String year = DateUtils.today().substring(0, 4);
		String month = DateUtils.today().substring(5, 7);
		String directory = "D:/upload" + SystemConstants.FILE_SEPARATOR + year + SystemConstants.FILE_SEPARATOR + month;
		File fileDirectory = new File(directory);
		// 取存放路径的根目录来获得剩余容量,是因为如果目标文件夹如果为空,则获取不到该分区的剩余容量
		if (new File(fileDirectory.getPath().substring(0, fileDirectory.getPath().indexOf("\\") + 1)).getFreeSpace() - fileSize < 0) {
			logger.error(fileDirectory.getPath() + ",该目录所在磁盘分区的剩余容量不足以存放" + originalName + "文件");
			fileInfo.setStatus(false);
			fileInfo.setMsg("服务器磁盘容量不足,上传失败");
			return fileInfo;
		}
		if (!fileDirectory.exists()) {
			fileDirectory.mkdirs();
		}
		InputStream in = null;
		BufferedOutputStream out = null;
		try {
			in = new BufferedInputStream(file.getInputStream(), BUFFER_SIZE);
			out = new BufferedOutputStream(new FileOutputStream(fileDirectory.getPath() + SystemConstants.FILE_SEPARATOR + archiveName), BUFFER_SIZE);
			byte[] buffer = new byte[BUFFER_SIZE];
			int len = 0;
			while ( ( len = in.read(buffer) ) > 0) {
				out.write(buffer, 0, len);
			}
			out.flush();
			fileInfo.setAbsolutePath(fileDirectory.getPath() + SystemConstants.FILE_SEPARATOR + archiveName);
			// 相对路径：/年份/月份/业务类型(菜单编码)/存档名称(存档名称为20位唯一标识符)+文件类型
			fileInfo.setRelativePath(SystemConstants.FILE_SEPARATOR + year + SystemConstants.FILE_SEPARATOR + month + SystemConstants.FILE_SEPARATOR
					+ archiveName);
			fileInfo.setOriginalName(originalName);
			fileInfo.setArchiveName(archiveName);
			fileInfo.setSize(fileSize);
			fileInfo.setType(extName);
			fileInfo.setStatus(true);
			fileInfo.setMsg("上传成功");
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (in != null) {
				try {
					in.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			if (out != null) {
				try {
					out.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		// 上传完后,清除临时文件
		file.delete();
		return fileInfo;
	}

	/**
	 * 
	 * @param path
	 * @param response
	 */
	public static void readFile(String path, OutputStream outputStream) {
		BufferedInputStream in = null;
		BufferedOutputStream out = null;
		try {
			in = new BufferedInputStream(new FileInputStream(path), BUFFER_SIZE);
			out = new BufferedOutputStream(outputStream, BUFFER_SIZE);
			byte[] buffer = new byte[BUFFER_SIZE];
			int len = 0;
			while ( ( len = in.read(buffer) ) > 0) {
				out.write(buffer, 0, len);
			}
			out.flush();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (in != null) {
				try {
					in.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			if (out != null) {
				try {
					out.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

	/**
	 * 
	 * @param path
	 */
	public static void deleteFile(String path) {
		File file = new File(path);
		if (file.exists()) {
			file.delete();
		}
	}

	/**
	 * 
	 * @param wfAttach
	 * @return
	 */
	// public String getFilePath(OfdAttach ofdAttach) {
	// Calendar calendar = Calendar.getInstance();
	// calendar.setTime(ofdAttach.getUploadDate());
	// int year = calendar.get(calendar.YEAR);
	// int month = calendar.get(calendar.MONTH) + 1;
	// String url = BPAFConfig.getProperty("ATTACH_PATH") + FILE_SEPARATOR +
	// year + FILE_SEPARATOR + month
	// + FILE_SEPARATOR + ofdAttach.getAppType() + FILE_SEPARATOR +
	// ofdAttach.getArchiveName();
	// return url;
	// }

	/**
	 * 获取文件扩展名
	 * 
	 * @return string
	 */
	public static String getFileExt(String fileName) {
		return fileName.substring(fileName.lastIndexOf("."));
	}

	/**
	 * 保存文件
	 * @param file
	 * @param appType
	 * @return
	 * @throws NullPointerException
	 */
	public FileInfo saveFile(MultipartFile file, String originalName) throws NullPointerException {
		FileInfo fileInfo = new FileInfo();
		// if (file==null||!file.exists()) {
		// //logger.error("file is null");
		// return fileInfo;
		// }
		Long fileSize = Long.valueOf(file.getSize());// 文件大小
		String fileType = getFileExt(originalName);// 文件类型
		String archiveName = "yxw_file_" + UUID.randomUUID();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		String date = sdf.format(new Date());
		StringBuffer directory = new StringBuffer();
		directory.append(FileHelper.DISK).append(File.separatorChar).append(FileHelper.SAVE_DIR).append(File.separatorChar).append(date);
		File fileDirectory = new File(directory.toString());
		// 取存放路径的根目录来获得剩余容量,是因为如果目标文件夹如果为空,则获取不到该分区的剩余容量
		//		if (new File(fileDirectory.getPath().substring(0, fileDirectory.getPath().indexOf("\\") + 1)).getFreeSpace() - fileSize < 0) {
		//			logger.error(fileDirectory.getPath() + ",该目录所在磁盘分区的剩余容量不足以存放" + originalName + "文件");
		//			fileInfo.setStatus(false);
		//			fileInfo.setMsg("服务器磁盘容量不足,上传失败");
		//			return fileInfo;
		//		}
		if (!fileDirectory.exists()) {
			fileDirectory.mkdirs();
		}
		InputStream in = null;
		BufferedOutputStream out = null;
		try {
			in = new BufferedInputStream(file.getInputStream(), BUFFER_SIZE);
			out =
					new BufferedOutputStream(new FileOutputStream(fileDirectory.getPath() + SystemConstants.FILE_SEPARATOR + archiveName + fileType),
							BUFFER_SIZE);
			byte[] buffer = new byte[BUFFER_SIZE];
			int len = 0;
			while ( ( len = in.read(buffer) ) > 0) {
				out.write(buffer, 0, len);
			}
			out.flush();
			// 绝对路径
			fileInfo.setAbsolutePath(fileDirectory.getPath() + SystemConstants.FILE_SEPARATOR + archiveName + fileType);
			// 相对路径
			fileInfo.setRelativePath(File.separatorChar + FileHelper.SAVE_DIR + File.separatorChar + date + File.separatorChar + archiveName
					+ fileType);
			fileInfo.setOriginalName(originalName);
			fileInfo.setArchiveName(archiveName);
			fileInfo.setSize(fileSize);
			fileInfo.setType(fileType);
			fileInfo.setStatus(true);
			fileInfo.setMsg("上传成功");
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (in != null) {
				try {
					in.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			if (out != null) {
				try {
					out.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		// 上传完后,清除临时文件
		// file.delete();
		return fileInfo;
	}
}

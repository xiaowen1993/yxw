package com.yxw.platform.message.controller;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Transparency;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import com.yxw.framework.config.SystemConfig;
import com.yxw.framework.mvc.controller.RespBody;
import com.yxw.framework.mvc.controller.RespBody.Status;

/**
 * 文件上传
 * 
 * @author luob
 * @date 2015-05-29
 * @version 1.0
 * */
@Controller
@RequestMapping("/uploading")
public class FileUploadController {

	private Image img;
	private final int MAX_WIDTH = 400;
	private final int MAX_HEIGHT = 250;
	private SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
	private static Logger logger = LoggerFactory.getLogger(FileUploadController.class);
	public static String DISK = null;
	public static String SAVE_DIR = SystemConfig.getStringValue("save_dir");
	public static String[] limitTypes = { "gif", "png", "jpg", "jpeg", "bmp" };
	static {
		String osName = System.getProperties().getProperty("os.name");
	    // logger.info("osName:" + osName);
		if (osName.toLowerCase().contains("linux")) {
			// /usr/local/
			DISK = SystemConfig.getStringValue("linux_disk");// 保存的目录名
		} else if (osName.toLowerCase().contains("win")) {
			// E:\\
			DISK = SystemConfig.getStringValue("win_disk");// 保存的目录名
		} else if (osName.toLowerCase().contains("mac")) {
			// /temp/
			DISK = SystemConfig.getStringValue("mac_disk");// 保存的目录名
			
		}
	}

	/**
	 * 上传图片
	 * 
	 * @param uploadFile
	 * @param request
	 * @return
	 * */
	@RequestMapping(params = "method=uploadMeterial")
	@ResponseBody
	public RespBody uploadMeterial(MultipartFile uploadFile, HttpServletRequest request) {
		String date = sdf.format(new Date());
		String path = DISK + File.separatorChar + SAVE_DIR + File.separatorChar + date;
		boolean isPic = false;
		File saveDir = new File(path);
		if (!saveDir.exists()) {
			saveDir.mkdirs();
		}
		String orignalName = uploadFile.getOriginalFilename();
		int pos = orignalName.indexOf(".");
		String fileName = "yxw_file_" + UUID.randomUUID();// filename
		String ext = orignalName.substring(pos);// jpg
		for (String type : limitTypes) {
			if (type.equals(ext.substring(1))) {
				isPic = true;
			}
		}
		if (!isPic) {
			return new RespBody(Status.ERROR, "文件格式不正确");
		}
		try {
			// 构造Image对象
			img = ImageIO.read(uploadFile.getInputStream());
			int width = img.getWidth(null);
			int height = img.getHeight(null);
			BufferedImage image = resizeFix(MAX_WIDTH, MAX_HEIGHT, width, height);
			File destFile = new File(path + "/" + fileName + ext);
			// 判断图片大小是否大于1MB
			/*
			 * if (destFile.length() > (1024 * 1024)) { return new
			 * RespBody(Status.ERROR, "图片大小不得大于1MB"); }
			 */
			
			FileOutputStream out = new FileOutputStream(destFile); // 输出到文件流
			
			//com.sun.image.codec.jpeg.JPEGImageEncoder encoder = com.sun.image.codec.jpeg.JPEGCodec.createJPEGEncoder(out);
			//encoder.encode(image); // JPEG编码
			//out.close();
			
			//图像处理JPEGCodec类已经从Jdk1.7移除 modify by homer.yang
			ImageIO.write(image,  "jpeg" , out); 
			out.close();
			
			// FileUtils.copyInputStreamToFile(uploadFile.getInputStream(), new
			// File(path + "/" + fileName + ext));
			return new RespBody(Status.OK, "/" + SAVE_DIR + "/" + date + "/" + fileName + ext);
		} catch (Exception e) {
			e.printStackTrace();
			return new RespBody(Status.ERROR, "上传文件失败");
		}
	}

	/**
	 * 按照宽度还是高度进行压缩
	 * 
	 * @param maxWidth
	 *            int 最大宽度
	 * @param maxHeight
	 *            int 最大高度
	 * @param width
	 *            int 原始宽度
	 * @param height
	 *            int 原始高度
	 */
	public BufferedImage resizeFix(int maxWidth, int maxHeight, int width, int height) throws IOException {
		if (width / height > maxWidth / maxHeight) {
			maxHeight = (int) ( height * maxWidth / width );
			return resize(maxWidth, maxHeight);
		} else {
			maxWidth = (int) ( width * maxHeight / height );
			return resize(maxWidth, maxHeight);
		}
	}

	/**
	 * 强制压缩/放大图片到固定的大小
	 * 
	 * @param maxWidth
	 *            int 新宽度
	 * @param maxHeight
	 *            int 新高度
	 */
	public BufferedImage resize(int maxWidth, int maxHeight) throws IOException {
		BufferedImage image = new BufferedImage(maxWidth, maxHeight, BufferedImage.TYPE_INT_RGB);
		image.getGraphics().drawImage(img, 0, 0, maxWidth, maxHeight, null); // 绘制缩小后的图
		return image;
	}

	/**
	 * 上传图片(保存透明背景的PNG图)
	 * 
	 * @param uploadFile
	 * @param request
	 * @return
	 * */
	@RequestMapping(params = "method=uploadLogo")
	@ResponseBody
	public RespBody uploadLogo(MultipartFile uploadFile, HttpServletRequest request) {
		String date = sdf.format(new Date());
		String path = DISK + File.separatorChar + SAVE_DIR + File.separatorChar + date;
		boolean isPic = false;
		File saveDir = new File(path);
		if (!saveDir.exists()) {
			saveDir.mkdirs();
		}
		String orignalName = uploadFile.getOriginalFilename();
		int pos = orignalName.indexOf(".");
		String fileName = "yxw_file_" + UUID.randomUUID();// filename
		String ext = orignalName.substring(pos);// jpg
		for (String type : limitTypes) {
			if (type.equals(ext.substring(1))) {
				isPic = true;
			}
		}
		if (!isPic) {
			return new RespBody(Status.ERROR, "文件格式不正确");
		}
		try {
			// 构造Image对象
			img = ImageIO.read(uploadFile.getInputStream());
			int width = img.getWidth(null);
			int height = img.getHeight(null);
			BufferedImage image = resizeFix(MAX_WIDTH, MAX_HEIGHT, width, height);
			Graphics2D g2d = image.createGraphics();
			image = g2d.getDeviceConfiguration().createCompatibleImage(width, height, Transparency.TRANSLUCENT);
			g2d.dispose();
			File destFile = new File(path + "/" + fileName + ext);
			FileUtils.copyInputStreamToFile(uploadFile.getInputStream(), destFile);
			return new RespBody(Status.OK, "/" + SAVE_DIR + "/" + date + "/" + fileName + ext);
		} catch (Exception e) {
			e.printStackTrace();
			return new RespBody(Status.ERROR, "上传文件失败");
		}
	}
}

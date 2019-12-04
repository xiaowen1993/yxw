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
package com.yxw.framework.common.attach.conterller;

import java.io.File;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.yxw.framework.common.PKGenerator;
import com.yxw.framework.common.attach.FileHelper;
import com.yxw.framework.common.attach.entity.Attach;
import com.yxw.framework.common.attach.service.AttachService;
import com.yxw.framework.config.SystemConfig;
import com.yxw.framework.config.SystemConstants;
import com.yxw.utils.DateUtils;
import com.yxw.utils.MD5Utils;

/**
 * @author caiwq
 * @version 1.0
 */
@Controller
@RequestMapping("/attach")
public class AttachController {

	protected Logger logger = LoggerFactory.getLogger(getClass());

	//保存路径
	private static final String FILE_PATH = SystemConfig.getStringValue("file_path");
	private static final String FILE_CONTEXT_PATH = SystemConfig.getStringValue("file_context_path");

	//密钥文件保存路径
	private static final String SECRET_KEY_PATH = SystemConfig.getStringValue("secret_key_path");

	@Autowired
	private AttachService attachService;

	@RequestMapping(value = "/uploadTest", method = RequestMethod.GET)
	public String uploadTest(HttpServletRequest request, HttpServletResponse response) {
		return "/attach/upload";
	}

	@RequestMapping(value = "/uploadFile")
	@ResponseBody
	public Map<String, Object> uploadFile(MultipartFile uploadFile, HttpServletRequest request) {
		logger.info("---------------------文件上传 start---------------------");
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			//表单ID
			String attachId = request.getParameter("attachId");
			//文件类型
			Integer attachType = StringUtils.isBlank(request.getParameter("attachType")) ? 0 : Integer.parseInt(request.getParameter("attachType"));
			logger.info("文件类型 0：图片 1：文档 2：密钥 3：其他 -->[" + attachType + "]");
			if (StringUtils.isBlank(attachId)) {
				attachId = PKGenerator.generateId();
			}

			String year = DateUtils.today().substring(0, 4);
			String month = DateUtils.today().substring(5, 7);
			String fileName = uploadFile.getOriginalFilename();
			logger.info("获取到文件，文件名：[" + fileName + "]");
			if (StringUtils.isNotEmpty(fileName)) {
				logger.info("文件 [" + fileName + "] 上传开始");
				//重命名文件
				//String renameFileName = MD5Utils.getMd5String32(UUID.randomUUID().toString());
				String renameFileName = String.valueOf(attachId);
				//文件拓展名
				String extName = FileHelper.getFileExt(fileName);
				// 文件大小
				Long fileSize = Long.valueOf(uploadFile.getSize());
				String directory = "";
				if (attachType == 2) {
					directory = SECRET_KEY_PATH;
				} else {
					directory =
							SystemConstants.FILE_SEPARATOR + FILE_PATH +
									SystemConstants.FILE_SEPARATOR + FILE_CONTEXT_PATH +
									SystemConstants.FILE_SEPARATOR + year + SystemConstants.FILE_SEPARATOR + month;
				}

				File fileDirectory = new File(directory);
				if (!fileDirectory.exists()) {
					fileDirectory.mkdirs();
				}

				// 取存放路径的根目录来获得剩余容量,是因为如果目标文件夹如果为空,则获取不到该分区的剩余容量
				/*if (new File(fileDirectory.getPath().substring(0, fileDirectory.getPath().indexOf("\\") + 1)).getFreeSpace() - fileSize < 0) {
					logger.error(fileDirectory.getPath() + ",该目录所在磁盘分区的剩余容量不足以存放" + fileName + "文件");
					map.put("status", false);
					map.put("message", "服务器磁盘容量不足,上传失败");
					return map;
				}*/
				String absolutePath = directory + SystemConstants.FILE_SEPARATOR + renameFileName + extName;
				File localFile = new File(absolutePath);
				uploadFile.transferTo(localFile);

				Attach attach = attachService.findByAttachId(attachId);
				boolean checkFlag = false;
				if (attach == null) {
					checkFlag = true;
					attach = new Attach();
				}
				attach.setAttachId(attachId);
				attach.setOriginalName(fileName);
				attach.setArchiveName(renameFileName);
				attach.setAbsolutePath(absolutePath);
				String relativePath = "";
				if (attachType == 2) {
					relativePath = SystemConstants.FILE_SEPARATOR + SECRET_KEY_PATH + SystemConstants.FILE_SEPARATOR + renameFileName + extName;
				} else {
					relativePath =
							SystemConstants.FILE_SEPARATOR + FILE_CONTEXT_PATH +
									SystemConstants.FILE_SEPARATOR + year + SystemConstants.FILE_SEPARATOR + month + SystemConstants.FILE_SEPARATOR
									+ renameFileName + extName;
				}
				attach.setRelativePath(relativePath);
				attach.setAttachSize(fileSize);
				attach.setExtName(extName);
				attach.setUploadDate(new Date());
				attach.setUploadPersonId("");
				if (checkFlag) {
					attachService.add(attach);
				} else {
					attachService.update(attach);
				}
				map.put("attach", attach);
				logger.info("文件 [" + fileName + "] 上传结束");
			}
			logger.info("---------------------文件上传 end---------------------");
			map.put("status", true);
			return map;
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("", e);
			map.put("status", false);
			map.put("message", e);
			return map;
		}
	}

	/**
	 * 上传文件
	 * @param request
	 * @param response
	 * @return
	 
	@ResponseBody
	@RequestMapping(value = "/uploadFile", method = RequestMethod.POST)
	public Map<String, Object> uploadFile(HttpServletRequest request, HttpServletResponse response) {
		logger.info("---------------------文件上传 start---------------------");
		Map<String, Object> map = new HashMap<String, Object>();
	    CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver(request.getSession().getServletContext());
	    try {
	    	String attachId = request.getParameter("attachId"); 
	    	if (StringUtils.isBlank(attachId)) {
	    		attachId = PKGenerator.generateId();
			}
	    	List<Attach> attachs = new ArrayList<Attach>();
	        if (multipartResolver.isMultipart(request)) {
	            MultipartHttpServletRequest multiRequest = (MultipartHttpServletRequest) request;
	            Iterator<String> iterator = multiRequest.getFileNames();
	            String year = DateUtils.today().substring(0, 4);
		        String month = DateUtils.today().substring(5, 7);
	            while (iterator.hasNext()) {
	            	MultipartFile file = multiRequest.getFile(iterator.next());  
	            	String fileName = file.getOriginalFilename();
	            	logger.info("获取到文件，文件名：[" + fileName +"]");
	            	if(StringUtils.isNotEmpty(fileName)){
	            		logger.info("文件 [" + fileName +"] 上传开始");
	            		Attach attach = new Attach();
	            		//重命名文件
						String renameFileName = MD5Utils.getMd5String32(UUID.randomUUID().toString());
						//文件拓展名
						String extName = FileHelper.getFileExt(fileName);
						 // 文件大小
				        Long fileSize = Long.valueOf(file.getSize());
						String directory = FILE_PATH + SystemConstants.FILE_SEPARATOR + year + SystemConstants.FILE_SEPARATOR + month;
						File fileDirectory = new File(directory);
				        // 取存放路径的根目录来获得剩余容量,是因为如果目标文件夹如果为空,则获取不到该分区的剩余容量
				        if (new File(fileDirectory.getPath().substring(0, fileDirectory.getPath().indexOf("\\") + 1)).getFreeSpace() - fileSize < 0) {
				            logger.error(fileDirectory.getPath() + ",该目录所在磁盘分区的剩余容量不足以存放" + fileName + "文件");
				            map.put("status", false);
				            map.put("message", "服务器磁盘容量不足,上传失败");
				            return map;
				        }
				        if (!fileDirectory.exists()) {
				            fileDirectory.mkdirs();
				        }
						String absolutePath = directory + SystemConstants.FILE_SEPARATOR  + renameFileName + extName;
	                    File localFile = new File(absolutePath);
						file.transferTo(localFile);
						
						attach.setAttachId(attachId);
						attach.setOriginalName(fileName);
						attach.setArchiveName(renameFileName);
						attach.setAbsolutePath(absolutePath);
						attach.setRelativePath(SystemConstants.FILE_SEPARATOR + year + SystemConstants.FILE_SEPARATOR + month + SystemConstants.FILE_SEPARATOR + renameFileName + extName);
						attach.setAttachSize(fileSize);
						attach.setExtName(extName);
						attach.setUploadDate(new Date());
						attach.setUploadPersonId("");
			            attachs.add(attach);
			            logger.info("文件 [" + fileName +"] 上传结束");
	            	}
	            }
	        }
	        if(attachs.size() > 0){
	        	for (Attach attach : attachs) {
	        		attachService.add(attach);
				}
	        }
	        map.put("status", true);
	        map.put("attachId", attachId);
	        logger.info("---------------------文件上传 end---------------------");
	    }catch (Exception e) {
	        logger.error("", e);
	        map.put("status", false);
	        map.put("message", e);
	    }
	    return map;
	}
	*/

	@ResponseBody
	@RequestMapping(value = "/getAttachId")
	public String getAttachId(HttpServletRequest request, HttpServletResponse response) {
		//    	Map<String, String> map = new HashMap<String, String>();
		//		map.put("attachId", PKGenerator.generateId());
		return PKGenerator.generateId();
	}
}

package com.yxw.platform.message.controller;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageInfo;
import com.yxw.commons.constants.biz.UserConstant;
import com.yxw.commons.entity.platform.hospital.Hospital;
import com.yxw.commons.entity.platform.message.Meterial;
import com.yxw.commons.entity.platform.privilege.User;
import com.yxw.framework.common.PKGenerator;
import com.yxw.framework.mvc.controller.BaseController;
import com.yxw.framework.mvc.controller.RespBody;
import com.yxw.framework.mvc.controller.RespBody.Status;
import com.yxw.framework.mvc.service.BaseService;
import com.yxw.platform.message.MessageConstant;
import com.yxw.platform.message.service.MeterialService;
import com.yxw.platform.vo.MsgHospitalVO;

/**
 * 单一图文控制类
 * */
@Controller
@RequestMapping("/message/meterial")
public class MeterialController extends BaseController<Meterial, String> {

	//private static Logger logger = LoggerFactory.getLogger(FileUploadController.class);
	private Image img;
	private final int MAX_WIDTH = 400;
	private final int MAX_HEIGHT = 250;
	private SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");

	@Autowired
	private MeterialService meterialService;

	@Override
	protected BaseService<Meterial, String> getService() {
		return meterialService;
	}

	/**
	 * 获取消息管理医院列表
	 * */
	@RequestMapping(params = "method=list")
	public ModelAndView list(@RequestParam(value = "pageNum",
			required = false,
			defaultValue = "1") Integer pageNum, @RequestParam(value = "pageSize",
			required = false,
			defaultValue = "5") Integer pageSize, String search, ModelMap modelMap, HttpServletRequest request) {

		@SuppressWarnings("unchecked")
		List<Hospital> hospitalList = (List<Hospital>) request.getSession().getAttribute(UserConstant.HOSPITAL_LIST);
		PageInfo<MsgHospitalVO> hospitals = null;
		if (hospitalList.size() > 0) {
			String[] hospitalIds = new String[hospitalList.size()];
			for (int i = 0; i < hospitalList.size(); i++) {
				hospitalIds[i] = hospitalList.get(i).getId();
			}

			Map<String, Object> params = new HashMap<String, Object>();
			// 设置搜索条件
			params.put("search", search);
			// 把session中保存的ID做查询条件
			params.put("hospitalIds", hospitalIds);
			hospitals = meterialService.findHospListByPage(params, new Page<MsgHospitalVO>(pageNum, pageSize));
		}
		modelMap.put("search", search);
		ModelAndView view = new ModelAndView("/sys/meterial/list", "hospitals", hospitals);
		return view;
	}

	/**
	 * 素材资源列表
	 * */
	@RequestMapping(params = "method=listOfPic")
	public String listOfPic(@RequestParam(required = false,
			defaultValue = "1") int pageNum, @RequestParam(required = false,
			defaultValue = "5") int pageSize, String hospitalId, ModelMap modelMap) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("hospitalId", hospitalId);
		params.put("type", "1");
		PageInfo<Meterial> meterials = meterialService.findListByPage(params, new Page<Meterial>(pageNum, pageSize));
		modelMap.put("meterials", meterials);
		modelMap.put("hospitalId", hospitalId);
		return "/sys/meterial/pic_list";
	}

	/**
	 * 上传图片素材并保存
	 * */
	@RequestMapping(params = "method=savePic")
	@ResponseBody
	public RespBody savePic(MultipartFile uploadFile, HttpServletRequest request, String hospitalId) {
		// 权限控制
		User loginUser = (User) request.getSession().getAttribute(MessageConstant.LOGINED_USER);
		String editPerson = null;
		if (loginUser != null) {
			editPerson = loginUser.getId();
		}
		String date = sdf.format(new Date());
		StringBuffer path = new StringBuffer();
		path.append(FileUploadController.DISK).append(File.separatorChar).append(FileUploadController.SAVE_DIR).append(File.separatorChar)
				.append(date);
		boolean isPic = false;
		File saveDir = new File(path.toString());
		if (!saveDir.exists()) {
			saveDir.mkdirs();
		}
		String orignalName = uploadFile.getOriginalFilename();
		int pos = orignalName.indexOf(".");
		String fileName = "yxw_file_" + UUID.randomUUID();// filename
		String ext = orignalName.substring(pos);// jpg
		for (String type : FileUploadController.limitTypes) {
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
			File destFile = new File(path.append(File.separatorChar).append(fileName).append(ext).toString());// 判断图片大小是否大于1MB
			/*
			 * if (destFile.length() > (1024 * 1024)) { return new
			 * RespBody(Status.ERROR, "图片大小不得大于1MB"); }
			 */
			FileOutputStream out = new FileOutputStream(destFile); // 输出到文件流
			
//			JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder(out);
//			encoder.encode(image); // JPEG编码
//			out.close();
			
			//图像处理JPEGCodec类已经从Jdk1.7移除 modify by homer.yang
			ImageIO.write(image,  "jpeg" , out); 
			out.close();
			
			StringBuffer coverPicPath = new StringBuffer();
			coverPicPath.append(File.separatorChar).append(FileUploadController.SAVE_DIR).append(File.separatorChar).append(date)
					.append(File.separatorChar).append(fileName).append(ext);
			Meterial meterial = new Meterial();
			String id = PKGenerator.generateId();
			meterial.setId(id);
			meterial.setName(orignalName);
			meterial.setCoverPicPath(coverPicPath.toString());
			meterial.setPath(coverPicPath.toString());
			meterial.setState(1);
			meterial.setType(1);
			BigDecimal size = new BigDecimal(destFile.length() / 1024.0);
			meterial.setSize(String.valueOf(size.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue()));// 单位K
			meterial.setCp(editPerson);
			meterial.setEp(editPerson);
			meterial.setHospitalId(hospitalId);
			meterialService.add(meterial);
			return new RespBody(Status.OK, meterial);
		} catch (Exception e) {
			e.printStackTrace();
			return new RespBody(Status.ERROR, "上传文件失败");
		}
	}

	/**
	 * 修改素材资源
	 * */
	@ResponseBody
	@RequestMapping(params = "method=edit")
	public RespBody edit(String name, String id, String meterialGroupId) {
		try {
			Meterial meterial = meterialService.findById(id);
			meterial.setName(name);
			meterial.setMeterialGroupId(meterialGroupId);
			meterialService.update(meterial);
			return new RespBody(Status.OK);
		} catch (Exception e) {
			e.printStackTrace();
			return new RespBody(Status.ERROR);
		}
	}

	/**
	 * 删除素材资源
	 * */
	@ResponseBody
	@RequestMapping(params = "method=delete")
	public RespBody delete(String id) {
		try {
			meterialService.deleteById(id);
			return new RespBody(Status.OK);
		} catch (Exception e) {
			e.printStackTrace();
			return new RespBody(Status.ERROR);
		}
	}

	/**
	 * 删除素材资源
	 * */
	@ResponseBody
	@RequestMapping(params = "method=deleteByIds")
	public RespBody deleteByIds(String ids) {
		try {
			if (ids != null) {
				for (String id : ids.split(",")) {
					meterialService.deleteById(id);
				}
				return new RespBody(Status.OK);
			} else {
				return new RespBody(Status.ERROR);
			}
		} catch (Exception e) {
			e.printStackTrace();
			return new RespBody(Status.ERROR);
		}
	}

	/**
	 * 选择素材列表
	 * */
	@RequestMapping(params = "method=choosePicList")
	public String choosePicList(@RequestParam(required = false,
			defaultValue = "1") int pageNum, @RequestParam(required = false,
			defaultValue = "10") int pageSize, String hospitalId, String metarialType, ModelMap modelMap) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("hospitalId", hospitalId);
		params.put("type", "1");
		PageInfo<Meterial> meterials = meterialService.findListByPage(params, new Page<Meterial>(pageNum, pageSize));
		modelMap.put("meterials", meterials);
		modelMap.put("hospitalId", hospitalId);
		modelMap.put("metarialType", metarialType);
		return "/sys/meterial/select_pic_list";
	}

	/**
	 * 添加语音素材
	 * */
	@ResponseBody
	@RequestMapping(params = "method=addSound")
	public RespBody addSound(String meterialGroupId, String name, String path, String size, String duration) {
		try {
			Meterial meterial = new Meterial();
			meterial.setMeterialGroupId(meterialGroupId);
			meterial.setName(name);
			meterial.setPath(path);
			meterial.setSize(size);
			meterial.setState(1);
			meterial.setType(2);
			meterial.setDuration(duration);
			meterialService.add(meterial);
			return new RespBody(Status.OK);
		} catch (Exception e) {
			e.printStackTrace();
			return new RespBody(Status.ERROR);
		}
	}

	/**
	 * 添加视频素材
	 * */
	@ResponseBody
	@RequestMapping(params = "method=addVideo")
	public RespBody addVideo(String meterialGroupId, String name, String path, String size, String duration, String coverPicPath) {
		try {
			Meterial meterial = new Meterial();
			meterial.setMeterialGroupId(meterialGroupId);
			meterial.setName(name);
			meterial.setPath(path);
			meterial.setSize(size);
			meterial.setState(1);
			meterial.setType(3);
			meterial.setDuration(duration);
			meterial.setCoverPicPath(coverPicPath);
			meterialService.add(meterial);
			return new RespBody(Status.OK);
		} catch (Exception e) {
			e.printStackTrace();
			return new RespBody(Status.ERROR);
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
			maxHeight = (int) (height * maxWidth / width);
			return resize(maxWidth, maxHeight);
		} else {
			maxWidth = (int) (width * maxHeight / height);
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
	 * 获取图片信息
	 * 
	 * @param id
	 * */
	@ResponseBody
	@RequestMapping(params = "method=getMetarialById")
	public RespBody getMetarialById(String id) {
		try {
			Meterial entity = meterialService.findById(id);
			return new RespBody(Status.OK, entity);
		} catch (Exception e) {
			e.printStackTrace();
			return new RespBody(Status.ERROR);
		}
	}
}

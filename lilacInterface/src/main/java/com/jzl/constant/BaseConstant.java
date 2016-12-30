package com.jzl.constant;

import java.io.File;

import com.jzl.conf.Config;

/**
 * 基础的常量
 * 
 * @Description
 * @author haojian 309444359@qq.com
 * @date 2015-9-21 下午5:19:28
 * 
 */
public class BaseConstant {

	/** 返回状态--失败 */
	public static final int STATUS_FAILURE = 0;
	/** 返回状态--成功 */
	public static final int STATUS_SUCCESS = 1;
	/** 返回状态--session过期 */
	public static final int STATUS_SESSION_INVALID = 2;

	/** 图片上传根路径 */
	public static final String BASE_IMAGE_ADDRESS = new File(BaseConstant.class
			.getResource("/").getPath()) + "/../../../img/";
	/** 图片服务器路径 */
	public static final String IMG_SERVER_URL = Config.IMG_URL;
	/** 图片路径--会员头像 */
	public static final String USER_AVATAR_IMAGE = "/user/avatar/";
	/** 图片路径--前台页面上会员临时头像，用于裁剪的原图 */
	public static final String TEMP_IMAGE = "/temp/";
	
	
	/**银行服务租户ID*/
	public static final Long TENANTID = 1L; // 采用默认为1 （0、通用，1、数金），目前已知只有ifp.config.api用 0 。
	/**银行服务APPID*/
	public static final Long APPID_FOR_TOKEN = 2L; //1466501212896288837L;
	/**银行服务APPID*/
	public static final Long APPID = 3L; //1466501212896288837L;
	/**商户类型*/
	public static final String MCCCODE="7278"; //7278 购物服务及会所（贸易、经纪服务）
	
}

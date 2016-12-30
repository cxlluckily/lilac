package com.jzl.conf;

import java.util.Properties;

import com.appcore.conf.AbstractPropertiesConfig;

/**
 *  配置加载
 * @author haojian
 * Apr 27, 2012 3:21:26 PM
 */
public class Config extends AbstractPropertiesConfig{
    
    private static final long serialVersionUID = 1L;
    
    private static Properties p = getProperties("config.properties");
    
    /**项目名*/
    public static final String SITE_NAME = p.getProperty("SITE_NAME");
    /**项目访问url*/
    public static final String BASE_URL = p.getProperty("BASE_URL");
    /**静态资源服务器地址，存放js库,css,网站原生图片 等*/
    public static final String STATIC_URL = p.getProperty("STATIC_URL");
    /**图片服务器地址，存放用户上传的图片、视频等*/
    public static final String IMG_URL = p.getProperty("IMG_URL");
    /**临时图片保存路径*/
    public static final String TEMP_IMG_URL = p.getProperty("TEMP_IMG_URL");
    
    
}

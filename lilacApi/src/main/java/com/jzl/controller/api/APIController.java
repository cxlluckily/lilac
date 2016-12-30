package com.jzl.controller.api;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.appcore.util.JsonUtil;
import com.jzl.to.api.CreateRequestTO;
import com.jzl.to.api.CreateResponseTO;
import com.jzl.controller.AbstractAPIController;
import com.jzl.to.FailResponseTO;

/**
 * 【UserController】控制器
 * 
 * @author AutoCode 1129290218@qq.com
 * @date 2016-12
 * 
 */
@Controller(value = "APIController")
@RequestMapping("api/")
public class APIController extends AbstractAPIController {

    private static final Logger LOGGER = LoggerFactory.getLogger(APIController.class);


	/**
	 * 注册
     * @param request
     * @param createRequestTO
     * @return Object
	 * @author AutoCode 1129290218@qq.com
     * @date 2016-12
	 */
    @RequestMapping(value = "create")
    @ResponseBody
    public Object create(HttpServletRequest request, CreateRequestTO requestTO) {
        
        LOGGER.debug("请求ip【{}】，请求信息【{}】", new Object[] { request.getRemoteHost(), requestTO });

        try{
            //处理业务
            
            CreateResponseTO responseTO = new CreateResponseTO();
            return responseTO;
        }catch(Exception e){
            e.printStackTrace();
            LOGGER.error("注册出现异常【{}】，请求ip【{}】，请求信息【{}】", new Object[] { e.getMessage(), request.getRemoteAddr(), requestTO });
            return FailResponseTO.newFailResponseTO();
        }
		
    }




}

package com.jzl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * 初始化缓存数据等
 * 
 * @Description
 * @author haojian 309444359@qq.com
 * @date 2015-12-15 下午3:42:16
 * 
 */
@Component(value = "serverInit")
public class ServerInit {

    private static final Logger LOGGER = LoggerFactory.getLogger(ServerInit.class);

    public boolean init() {
        
        //1、初始化扫描未支付订单任务
        
        
        return true;
    }

}

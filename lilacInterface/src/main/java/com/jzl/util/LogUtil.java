package com.jzl.util;

/**
 * 日志帮助类
 * @Description
 * @author haojian 309444359@qq.com
 * @date 2016-3-2 下午5:19:55 
 *
 */
public class LogUtil {/*
    
     * 异步记录用户行为日志
     * @author haojian
     * @date 2016-3-2 下午5:20:03 
     * @param request
     * @param source 用户来源：   1、后台   2、手机端    3、web端
     * @return void
     *//*
    public static void logUserAction(HttpServletRequest request, int logType){
        
        CasUser casUser = SessionManager.getAttribute(request, CasConstant.KEY_CAS_USER);
        //AdminUser adminUser = SessionManager.getAttribute(request, AdminConstant.KEY_ADMIN_USER);
        String ip = request.getRemoteHost();
        String servletPath = request.getServletPath();
        String param = JsonUtil.getJsonString(request.getParameterMap());
        
        //创建日志对象
        LogUserAction log = new LogUserAction();
        
        log.setLogType(logType);
        
        long userId = 0;
        if(casUser!=null){
            userId = casUser.getUserId();
        }
        
        log.setUserId(userId);
        log.setUrl(servletPath);
        log.setParam(param);
        log.setIp(ip);
        log.setCtime(new Date());
        
        LogUserActionService logUserActionService = AppContext.getBean("logUserActionService");
        //异步插入数据到数据库
        logUserActionService.asyncInsert(log);
        
        
    }
    
    
    

*/}

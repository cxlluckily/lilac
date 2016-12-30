package com.jzl.procotol;

import com.appcore.page.Page;
import com.jzl.to.PageTO;
/**
 * 公共的协议组装类
 * @Description
 * @author haojian 309444359@qq.com
 * @date 2016-5-19 上午11:05:00 
 *
 */
public class PublicProtocol {
    
    /**
     * 将Page对象转换成PageTO对象
     * @author haojian
     * @date 2016-5-19 上午11:05:16 
     * @param page
     * @return
     * @return PageTO
     */
    public static PageTO newPageTO(Page page){
        
        PageTO pageTO = new PageTO();
        pageTO.setCurrentPage(page.getPage());
        pageTO.setPageSize(page.getPageSize());
        
        int isLast = 0;
        if( (page.getPage()-1)*page.getPageSize() + page.getData().size() == page.getCount()){
            isLast = 1;
        }
        pageTO.setIsLast(isLast);
        
        pageTO.setCount((int)page.getCount());
        
        return pageTO;
    }

}

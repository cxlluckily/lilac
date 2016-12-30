/*package com.jzl.util;

import com.jzl.constant.BaseConstant;
import com.cib.fintech.framework.idgen.NetAddressIdWorker;
import com.cib.fintech.ifp.common.vo.BaseRequest;
*//**
 * 银行请求接口工具类
 * @author haoj 309444359@qq.com
 * @date 2016-11-3 下午7:33:31
 *//*
public class BaseRequestUtil {
	
	*//**
	 * 包装BaseRequest，添加必要的数据
	 * @author haoj 309444359@qq.com
	 * @date 2016-11-3 下午7:33:49
	 * @param baseRequest
	 * @return
	 *//*
	public static BaseRequest wrapBaseRequest(BaseRequest baseRequest){
		
		baseRequest.setAppId(BaseConstant.APPID);
		baseRequest.setTenantId(BaseConstant.TENANTID);
		baseRequest.setRequestId(BaseRequestUtil.getRequestId());
		
		return baseRequest;
	}
	
	*//**
	 * 包装BaseRequest，添加必要的数据,用于校验Token
	 * @author haoj 309444359@qq.com
	 * @date 2016-11-10 下午2:27:25
	 * @param baseRequest
	 * @return
	 *//*
	public static BaseRequest wrapBaseRequestForToken(BaseRequest baseRequest){
		
		baseRequest.setAppId(BaseConstant.APPID_FOR_TOKEN);
		baseRequest.setTenantId(BaseConstant.TENANTID);
		baseRequest.setRequestId(BaseRequestUtil.getRequestId());
		
		return baseRequest;
	}
	
	 *//**
     * 获取银行请求ID
     * @return
     *//*
    public static Long getRequestId(){
    	NetAddressIdWorker idgen=new NetAddressIdWorker();
    	return idgen.generate();
    }

}
*/
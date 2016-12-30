/*package com.jzl.util;

import com.jzl.to.ConsigneeTO;
import com.cib.fintech.ifp.user.vo.UserAddress;
*//**
 * 对象转换工具类
 * @author haoj 309444359@qq.com
 * @date 2016-11-14 下午6:56:45
 *//*
public class ObjectCastUtil {
	
    *//**
     * 将银行接口返回的收货地址对象  转换成 接口文档中的收货地址对象
     * @author haoj 309444359@qq.com
     * @date 2016-11-14 下午6:57:09
     * @param userAddress
     * @return
     *//*
    public static ConsigneeTO userAddressToConsigneeTo(UserAddress userAddress){
    	if(userAddress==null){
    		return null;
    	}
    	ConsigneeTO info=new ConsigneeTO();
		info.setAddrId(userAddress.getAddrId().toString());
		info.setName(userAddress.getName());
		info.setAlias(userAddress.getAlias());
		info.setMobileNo(userAddress.getMobile());
		info.setProvince(userAddress.getProvince());
		info.setCity(userAddress.getCity());
		info.setCounty(userAddress.getCounty());
		info.setAddress(userAddress.getAddress());
		info.setIsDefault(userAddress.getDefaultAddr()?1:0);
		
    	return info;
    }
	

}
*/
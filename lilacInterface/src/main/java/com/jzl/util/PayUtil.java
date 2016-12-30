/*package com.jzl.util;

import java.math.BigDecimal;
import java.util.Date;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.jzl.constant.BaseConstant;
import com.jzl.constant.PayConstant;
import com.cib.fintech.framework.exception.BaseException;
import com.cib.fintech.xlife.api.request.payment.PaymentRequest;
import com.cib.fintech.xlife.api.service.payment.PaymentService;
import com.cib.fintech.xlife.model.payment.Currency;
import com.cib.fintech.xlife.model.payment.PayWay;
import com.cib.fintech.xlife.model.payment.PaymentOrderInfo;
import com.cib.fintech.xlife.model.payment.SafetyToolsControl;
import com.cib.fintech.xlife.model.payment.SelectCardsControl;
import com.cib.fintech.xlife.model.payment.SelectCardsControl.SelectCardType;
*//**
 * 支付相关工具类
 * @author haoj 309444359@qq.com
 * @date 2016-11-3 下午8:01:31
 *//*
public class PayUtil {
	private static final Logger LOGGER = LoggerFactory.getLogger(PayUtil.class);
	*//**
	 * 获取预付数据
	 * @author haoj 309444359@qq.com
	 * @date 2016-11-3 下午8:01:40
	 * @param paymentService
	 * @param userId 	用户ID
	 * @param payOrderNo	支付流水号
	 * @param payWayType	 支付方式  1、自付金  2、信用卡积分  后续待开发：自付金+信用卡积分、信用卡分期 、 大贷方式 、小贷方式
	 * @param orderNo		订单编号
	 * @param orderAmount	订单金额 单位为元，即：十二位整数，两位小数
	 * @param orderPoint	积分（信用卡），积点（借记卡） 购买商品所消耗的积分积点， 如果是积分积点抵扣，订单金额进减去抵扣部分
	 * @param oderTitle		订单标题
	 * @param attach		自定义数据
	 * @param orderIp  		下单IP
	 * @return
	 * @throws BaseException
	 *//*
	public static String getPrepayData(PaymentService paymentService, Long userId,String payOrderNo,int payWayType
			,String orderNo,BigDecimal orderAmount, BigDecimal orderPoint,String oderTitle,String attach,String orderIp) throws BaseException{
		PaymentRequest request = new PaymentRequest();
		//订单信息
    	PaymentOrderInfo orderInfo =new PaymentOrderInfo();
		orderInfo.setMerchantType(BaseConstant.MCCCODE);//商户类型编号 MCC编号
		orderInfo.setMerchantNo(BaseConstant.MCCCODE); //商户编号
		
		orderInfo.setMerchantName(PayConstant.XY_MERCHANT_NAME);//商户名称
		orderInfo.setOrderNo(orderNo);//订单编号
		orderInfo.setOrderAmount(orderAmount);//订单金额
		orderInfo.setOrderPoint(orderPoint);//积分积点  购买商品所消耗的积分积点， 如果是积分积点抵扣，订单金额为减去抵扣部分
		orderInfo.setOrderTitle(oderTitle);//订单标题
		orderInfo.setOrderDesc("用户B2C购买商品");//订单描述
		orderInfo.setOrderIp(orderIp);//下单IP
		orderInfo.setAttach(attach);//商户自定义域
		orderInfo.setCreditStages(null);//信用卡分期期数
		orderInfo.setCurrency(Currency.CNY);//币种
		orderInfo.setOrderTime(new Date());//提交时间
		
		request.setTimestamp(new Date());
		request.setAppId(BaseConstant.APPID.toString());
		request.setTenantIid(BaseConstant.TENANTID.toString());
		
		request.setPayOrderNo((payOrderNo));//支付流水号
		request.setUserId(userId);//用户ID
		//支付方式 根据前台收款类型选择,选择对应的商户号
		if(payWayType==1){
			request.setPayWay(PayWay.CASH);  // 自付金
			orderInfo.setMerchantNo(PayConstant.CASH_MERCHANT_NO);//本金支付商户号
		}else if(payWayType==2){
			request.setPayWay(PayWay.CREDIT_POINT); // 信用卡积分
			orderInfo.setMerchantNo(PayConstant.CREDIT_POINT_MERCHANT_NO);//积分支付商户号
		}else{
			request.setPayWay(PayWay.CASH);// 临时类型
			orderInfo.setMerchantNo(PayConstant.CASH_MERCHANT_NO);//本金支付商户号
		}
		request.setPaymentOrderInfo(orderInfo);//支付订单信息
		
		else if(payWayType==3){
			request.setPayWay(PayWay.CASH_CREDIT_POINT); // 自付金+信用卡积分
			orderInfo.setMerchantNo(PayConstant.CASH_CREDIT_POINT_MERCHANT_NO);
		}else if(payWayType==4){
			request.setPayWay(PayWay.CREDIT_STAGES); // 信用卡分期
			orderInfo.setMerchantNo(PayConstant.CREDIT_STAGES_MERCHANT_NO);
		}else if(payWayType==5){
			request.setPayWay(PayWay.LARGE_WAY); // 大贷方式
		}else if(payWayType==6){
			request.setPayWay(PayWay.PETTY_WAY); // 小贷方式
		}
		
		
		//request.setNeedSelectCards(null);//是否需要选择银行卡 
		SelectCardsControl selectCardsControl=new SelectCardsControl();
		//默认是可以选择所有银行卡，如果是牵扯到积分支付，只能选择信用卡
		selectCardsControl.setSelectCardType(SelectCardType.ALL);
		if(payWayType==2){
			selectCardsControl.setSelectCardType(SelectCardType.CREDIT_CARD);
		}
		
		request.setSelectCardsControl(selectCardsControl);//选择信用卡控制  
		//request.setNeedSafetyTools(null);//是否需要安全校验 默认为true 如果为false 则安全控制工具失效
		SafetyToolsControl safetyToolsControl=new SafetyToolsControl();
		safetyToolsControl.setNeedPayPassword(true);
		request.setSafetyToolsControl(safetyToolsControl);//选择安全工具控制 默认设置（不需要短信，需要支付密码）
		
		//request.setDiscountWayControl((DiscountWayControl) paraMap.get("discountWayControl"));//选择优惠控制 该对象为预留信息
		
		LOGGER.info("创建支付订单，请求信息："+""+ToStringBuilder.reflectionToString(request)+"---"+ToStringBuilder.reflectionToString(request.getPaymentOrderInfo()));
		String payToken=paymentService.payment(request);
		LOGGER.info("创建支付订单，响应信息："+payToken);
		return payToken;
	}
	

}
*/
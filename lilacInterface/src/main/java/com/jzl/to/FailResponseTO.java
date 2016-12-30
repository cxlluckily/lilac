package com.jzl.to;

import com.appcore.model.AbstractObject;
import com.jzl.constant.BaseConstant;


public class FailResponseTO extends AbstractObject {
    
    private static final long serialVersionUID = 1L;
    
    //返回状态码    0、session过期  1、成功          
    private Integer returnCode;
    //附加消息
    private String returnMsg;
    
    private FailResponseTO(){
    }
    
    private FailResponseTO(Integer returnCode){
        this.returnCode = returnCode;
    }
    
    private FailResponseTO(Integer returnCode,String returnMsg){
        this.returnCode = returnCode;
        this.returnMsg = returnMsg;
    }
    
    public Integer getReturnCode() {
        return returnCode;
    }
    public void setReturnCode(Integer returnCode) {
        this.returnCode = returnCode;
    }
    public String getReturnMsg() {
        return returnMsg;
    }
    public void setReturnMsg(String returnMsg) {
        this.returnMsg = returnMsg;
    }
    
    public static FailResponseTO newFailResponseTO(){
        return new FailResponseTO(BaseConstant.STATUS_FAILURE);
    }
    
    public static FailResponseTO newFailResponseTO(Integer returnCode){
        return new FailResponseTO(returnCode);
    }
    
    public static FailResponseTO newFailResponseTO(Integer returnCode,String returnMsg){
        return new FailResponseTO(returnCode, returnMsg);
    }
    
}

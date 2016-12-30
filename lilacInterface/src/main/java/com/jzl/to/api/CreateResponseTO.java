package com.jzl.to.api;



import com.appcore.model.AbstractObject;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.jzl.to.api.UserTO;

/**
 * 【注册】Response
 * @author AutoCode 1129290218@qq.com
 */
@JsonIgnoreProperties
public class CreateResponseTO extends AbstractObject {

	private static final long serialVersionUID = 1L;

	//用户信息
	private UserTO user = new UserTO();

	private int returnCode;

    public CreateResponseTO(){
        this.returnCode = 1;
    }

    /**设置用户信息*/
	public void setUser(UserTO user){
		this.user=user;
	}
	/**获取用户信息*/
	public UserTO getUser(){
		return this.user;
	}


}

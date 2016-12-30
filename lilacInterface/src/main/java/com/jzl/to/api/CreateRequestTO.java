package com.jzl.to.api;



import com.appcore.model.AbstractObject;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * 【注册】Request
 * @author AutoCode 1129290218@qq.com
 */
@JsonIgnoreProperties
public class CreateRequestTO extends AbstractObject {

	private static final long serialVersionUID = 1L;

	//用户名
	private String userName;
	//密码
	private String loginPassword;


    /**设置用户名*/
	public void setUserName(String userName){
		this.userName=userName;
	}
	/**获取用户名*/
	public String getUserName(){
		return this.userName;
	}
    /**设置密码*/
	public void setLoginPassword(String loginPassword){
		this.loginPassword=loginPassword;
	}
	/**获取密码*/
	public String getLoginPassword(){
		return this.loginPassword;
	}


}

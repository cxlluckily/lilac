package com.jzl.to.api;



import com.appcore.model.AbstractObject;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
/**
 * 【用户信息】
 * @author AutoCode 1129290218@qq.com
 */
@JsonIgnoreProperties
public class UserTO extends AbstractObject {

    private static final long serialVersionUID = 1L;

	//id
	private Integer id;
	//用户名
	private String userName;
	//密码
	private String password;


    /**设置id*/
	public void setId(Integer id){
		this.id=id;
	}
	/**获取id*/
	public Integer getId(){
		return this.id;
	}
    /**设置用户名*/
	public void setUserName(String userName){
		this.userName=userName;
	}
	/**获取用户名*/
	public String getUserName(){
		return this.userName;
	}
    /**设置密码*/
	public void setPassword(String password){
		this.password=password;
	}
	/**获取密码*/
	public String getPassword(){
		return this.password;
	}


}

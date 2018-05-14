package com.actec.pnmp.bean.sys;

import com.actec.pnmp.bean.DataEntity;
import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.Table;

/**
 * Created by Yangchao on 2018-4-14.
 */
@Table(name = "sys_user")
public class User extends DataEntity<User> {
	private String userCode;
	private String userName;
	@JsonIgnore
	private String password;
	private String email;
	private String telephone;

	public String getUserCode() {
		return userCode;
	}

	public void setUserCode(String userCode) {
		this.userCode = userCode;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getTelephone() {
		return telephone;
	}

	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}
}
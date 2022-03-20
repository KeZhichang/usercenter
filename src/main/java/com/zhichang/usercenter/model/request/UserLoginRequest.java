package com.zhichang.usercenter.model.request;


import lombok.Data;

/**
 * 用户登录请求体
 *
 * @author pikario
 */
@Data
public class UserLoginRequest {

	private String userAccount;

	private String userPassword;
}

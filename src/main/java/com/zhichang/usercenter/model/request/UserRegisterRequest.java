package com.zhichang.usercenter.model.request;

import lombok.Data;

import java.io.Serializable;

/**
 * 用户注册请求体
 *
 * @author pikario
 */
@Data
public class UserRegisterRequest implements Serializable {
	//插件GenerateSerialVersionUID alt+insert 自动生成的序列化ID
	private static final long serialVersionUID = -2562182931584657432L;

	private String userAccount;

	private String userPassword;

	private String checkPassword;

}

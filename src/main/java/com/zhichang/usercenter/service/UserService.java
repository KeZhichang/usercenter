package com.zhichang.usercenter.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zhichang.usercenter.model.User;

import javax.servlet.http.HttpServletRequest;
import java.security.NoSuchAlgorithmException;

/**
 * 用户服务
 *
 * @author pikario
 */
public interface UserService extends IService<User> {

	/**
	 * 用户注册
	 *
	 * @param userAccount 用户帐户
	 * @param userPassword 用户密码
	 * @param checkPassword 校验码
	 * @return 新用户id
	 */
	long userRegister(String userAccount,String userPassword,String checkPassword) throws NoSuchAlgorithmException;

	/**
	 * 用户登录
	 *
	 * @param userAccount
	 * @param userPassword
	 * @param request
	 * @return 脱敏后的用户信息
	 */
	User userLogin(String userAccount, String userPassword, HttpServletRequest request);

	/**
	 * 用户脱敏
	 * @param originUser
	 * @return
	 */
	User getSafetyUser(User originUser);
}

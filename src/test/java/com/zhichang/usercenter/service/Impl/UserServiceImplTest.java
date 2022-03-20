package com.zhichang.usercenter.service;

import com.zhichang.usercenter.model.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.security.NoSuchAlgorithmException;

/**
 * 用户服务测试
 * @author pikario
 */

@SpringBootTest
public class UserServiceImplTest {

	@Resource
	private UserService userService;

	@Test
	public void testAddUser(){
		User user = new User();
		user.setUsername("pikario");
		user.setUserAccount("111");
		user.setAvatarUrl("");
		user.setGender((byte)0);
		user.setUserPassword("xxx");
		user.setPhone("123");
		user.setEmail("456");
		boolean result = userService.save(user);
		System.out.println(user.getId());
		Assertions.assertTrue(result);
	}

	@Test
	void userRegister() throws NoSuchAlgorithmException {
		String userAccount = "pikario";
		String userPassword = "";
		String checkPassword = "123456";
		long result = userService.userRegister(userAccount, userPassword, checkPassword);
		Assertions.assertEquals(-1,result);

		userAccount="pi";
		result = userService.userRegister(userAccount, userPassword, checkPassword);
		Assertions.assertEquals(-1,result);

		userAccount = "pikario";
		userPassword= "123456";
		result = userService.userRegister(userAccount, userPassword, checkPassword);
		Assertions.assertEquals(-1,result);

		userAccount = "pi ka";
		userPassword="12345678";
		result = userService.userRegister(userAccount, userPassword, checkPassword);
		Assertions.assertEquals(-1,result);

		checkPassword = "123456789";
		result = userService.userRegister(userAccount, userPassword, checkPassword);
		Assertions.assertEquals(-1,result);

		userAccount = "lucario";
		checkPassword="12345678";
		result = userService.userRegister(userAccount, userPassword, checkPassword);
		Assertions.assertTrue(result>0);
	}
}
package com.zhichang.usercenter.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.zhichang.usercenter.model.User;
import com.zhichang.usercenter.model.request.UserLoginRequest;
import com.zhichang.usercenter.model.request.UserRegisterRequest;
import com.zhichang.usercenter.service.UserService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static com.zhichang.usercenter.constant.UserConstant.ADMIN_ROLE;
import static com.zhichang.usercenter.constant.UserConstant.USER_LOGIN_STATE;

/**
 * 用户接口
 *
 * @author pikario
 */
@RestController
@RequestMapping("/user")
public class UserController {

	@Resource
	private UserService userService;

	@PostMapping("/register")
	public Long userRegister(@RequestBody UserRegisterRequest userRegisterRequest) throws NoSuchAlgorithmException {
		if (userRegisterRequest == null) {
			return null;
		}
		String userAccount = userRegisterRequest.getUserAccount();
		String userPassword = userRegisterRequest.getUserPassword();
		String checkPassword = userRegisterRequest.getCheckPassword();
		if (StringUtils.isAnyBlank(userAccount, userPassword, checkPassword)) {
			return null;
		}
		return userService.userRegister(userAccount, userPassword, checkPassword);
	}

	@PostMapping("/login")
	public User userLogin(@RequestBody UserLoginRequest userLoginRequest, HttpServletRequest request) throws NoSuchAlgorithmException {
		if (userLoginRequest == null) {
			return null;
		}
		String userAccount = userLoginRequest.getUserAccount();
		String userPassword = userLoginRequest.getUserPassword();

		return userService.userLogin(userAccount, userPassword, request);
	}

	@GetMapping("/search")
	public List<User> searchUser(String username,HttpServletRequest request){
		if(!isAdmin(request)){
			return new ArrayList<>();
		}
		QueryWrapper<User> queryWrapper = new QueryWrapper<>();
		if(StringUtils.isNotBlank(username)){
			queryWrapper.like("username",username);//模糊查询
		}
		List<User> userList = userService.list(queryWrapper);

		//过滤敏感信息
		return userList.stream().map(user -> {
			return userService.getSafetyUser(user);
		}).collect(Collectors.toList());
	}

	@PostMapping("/delete")
	public boolean deleteUser(@RequestBody long id,HttpServletRequest request){
		if(!isAdmin(request)){
			return false;
		}
		if(id<=0){
			return false;
		}
		return userService.removeById(id);//框架会自动逻辑删除的
	}

	/**
	 * 接口鉴权，仅管理员可查询
	 * @param request
	 * @return
	 */
	public boolean isAdmin(HttpServletRequest request){
		Object userObject = request.getSession().getAttribute(USER_LOGIN_STATE);
		User user = (User)userObject;
		if(user==null||user.getUserRole()!=ADMIN_ROLE){
			return false;
		}
		return true;
	}
}

package com.zhichang.usercenter.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zhichang.usercenter.mapper.UserMapper;
import com.zhichang.usercenter.model.User;
import com.zhichang.usercenter.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.security.NoSuchAlgorithmException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.zhichang.usercenter.constant.UserConstant.USER_LOGIN_STATE;

/**
 * 用户服务实现类
 *
 * @author pikario
 */
@Service
@Slf4j
public class UserServiceImpl extends ServiceImpl<UserMapper, User>
    implements UserService{

	@Resource
	private UserMapper userMapper;

	/**
	 * 盐值：加密用
	 */
	private static final String SALT = "pikario";

	@Override
	public long userRegister(String userAccount, String userPassword, String checkPassword) throws NoSuchAlgorithmException {
		//1、校验
		if(StringUtils.isEmpty(userAccount)||StringUtils.isEmpty(userPassword)
				||StringUtils.isEmpty(checkPassword)){
			return -1;
		}
		if(userAccount.length()<4){
			return -1;
		}
		if(userPassword.length()<8||checkPassword.length()<8){
			return -1;
		}
		//帐户不能包含特殊字符
		String validPattern = "[`~!@#$%^&*()+=|{}':;',\\\\[\\\\].<>/?~！@#￥%……&*（）——+|{}【】‘；：”“’。，、？]";
		Matcher matcher = Pattern.compile(validPattern).matcher(userAccount);
		if(matcher.find()){
			return -1;
		}
		//密码和校验密码相同
		if(!userPassword.equals(checkPassword)){
			return -1;
		}
		//帐户不能重复
		QueryWrapper<User> queryWrapper = new QueryWrapper<>();
		queryWrapper.eq("userAccount",userAccount);
		long count = userMapper.selectCount(queryWrapper);
		if(count>0){
			return -1;
		}
		//2、加密
		final String SALT = "pikario";
		String encryptPassword = DigestUtils.md5DigestAsHex((SALT+userPassword).getBytes());
		//3、插入数据
		User user = new User();
		user.setUserAccount(userAccount);
		user.setUserPassword(encryptPassword);
		boolean saveResult = this.save(user);
		if(!saveResult){
			return -1;
		}
		return user.getId();
	}

	@Override
	public User userLogin(String userAccount, String userPassword, HttpServletRequest request) {
		if(StringUtils.isEmpty(userAccount)||StringUtils.isEmpty(userPassword)){
			//todo 修改为自定义异常
			return null;
		}
		if(userAccount.length()<4){
			return null;
		}
		if(userPassword.length()<8){
			return null;
		}
		//帐户不能包含特殊字符
		String validPattern = "[`~!@#$%^&*()+=|{}':;',\\\\[\\\\].<>/?~！@#￥%……&*（）——+|{}【】‘；：”“’。，、？]";
		Matcher matcher = Pattern.compile(validPattern).matcher(userAccount);
		if(matcher.find()){
			return null;
		}
		//2、加密
		String encryptPassword = DigestUtils.md5DigestAsHex((SALT+userPassword).getBytes());
		//查询用户是否存在
		QueryWrapper<User> queryWrapper = new QueryWrapper<>();
		queryWrapper.eq("userAccount",userAccount);
		queryWrapper.eq("userPassword",encryptPassword);
		User user = userMapper.selectOne(queryWrapper);
		//用户不存在
		if(user==null){
			log.info("user login failed, userAccount cannot match userPassword ");
			return null;
		}
		//3、用户脱敏
		User saftyUser = getSafetyUser(user);
		//4、记录用户的登录态
		request.getSession().setAttribute(USER_LOGIN_STATE,user);

		return saftyUser;
	}

	@Override
	public User getSafetyUser(User originUser){
		if(originUser==null){
			return null;
		}
		User safetyUser = new User();
		safetyUser.setId(originUser.getId());
		safetyUser.setUsername(originUser.getUsername());
		safetyUser.setUserAccount(originUser.getUserAccount());
		safetyUser.setAvatarUrl(originUser.getAvatarUrl());
		safetyUser.setGender(originUser.getGender());
		safetyUser.setPhone(originUser.getPhone());
		safetyUser.setEmail(originUser.getEmail());
		safetyUser.setUserRole(originUser.getUserRole());
		safetyUser.setUserStatus(originUser.getUserStatus());
		safetyUser.setCreateTime(originUser.getCreateTime());
		return safetyUser;
	}
}





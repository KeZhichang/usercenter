package com.zhichang.usercenter;

import com.zhichang.usercenter.mapper.UserMapper;
import com.zhichang.usercenter.model.User;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.util.List;

@SpringBootTest
class UsercenterApplicationTests {

	@Resource
	private UserMapper userMapper;

	@Test
	void contextLoads() {
		System.out.println(("----- selectAll method test ------"));
		List<User> userList = userMapper.selectList(null);
		Assert.assertEquals(0, userList.size());//断言，判断预期是不是5条数据，不然就报错
		userList.forEach(System.out::println);
	}

}

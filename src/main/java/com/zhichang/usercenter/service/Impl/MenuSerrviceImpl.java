package com.zhichang.usercenter.service.Impl;

import com.zhichang.usercenter.mapper.MenuMapper;
import com.zhichang.usercenter.model.Menu;
import com.zhichang.usercenter.service.MenuService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class MenuSerrviceImpl implements MenuService {

	@Resource
	MenuMapper menuMapper;


	@Override
	public void update(List<Menu> menuList) {
		for (int i = 0; i < menuList.size(); i++) {
			menuMapper.updateById(menuList.get(i));
		}
	}
}

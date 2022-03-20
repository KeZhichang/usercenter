package com.zhichang.usercenter.controller;

import cn.hutool.poi.excel.ExcelReader;
import cn.hutool.poi.excel.ExcelUtil;
import com.zhichang.usercenter.model.Menu;
import com.zhichang.usercenter.mapper.MenuMapper;
import com.zhichang.usercenter.service.MenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.util.List;

@RestController
public class MenuController {
	@Autowired
	MenuMapper menuMapper;

	@Autowired
	MenuService menuService;

	@PostMapping("/uploadMenu")
	public String uploadMenu(@RequestParam("file")MultipartFile file) throws Exception{
		if (file.isEmpty()) {
			return "文件不能为空";
		}
		InputStream inputStream = file.getInputStream();
		ExcelReader reader = ExcelUtil.getReader(inputStream);
		List<List<Object>> read = reader.read();
		List<Menu> menuList= reader.readAll(Menu.class);
		for (int i = 0; i < menuList.size(); i++) {
			menuMapper.insert(menuList.get(i));
		}
		return "success";
	}

	@PostMapping("/updateMenu")
	public String updateMenu(@RequestParam("menuList") List<Menu> menuList){
		menuService.update(menuList);
		return "success";
	}
}

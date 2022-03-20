package com.zhichang.usercenter.model;

import lombok.Data;

import java.util.Date;

@Data
public class Menu {

	private Long id;
	private String type;
	private String idx;
	private String content;
	private Date date;


}

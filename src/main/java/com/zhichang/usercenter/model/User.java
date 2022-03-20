package com.zhichang.usercenter.model;

import com.baomidou.mybatisplus.annotation.*;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * 用户表
 * @TableName user
 */
@TableName(value ="user")
@Data
public class User implements Serializable {
	/**
	 * 主键
	 */
	@TableId(type = IdType.AUTO)
	private Long id;

	/**
	 * 用户名
	 */
	private String username;

	/**
	 * 登录账号
	 */
	private String userAccount;

	/**
	 * 头像
	 */
	private String avatarUrl;

	/**
	 * 性别
	 */
	private Byte gender;

	/**
	 * 登录密码
	 */
	private String userPassword;

	/**
	 * 电话号码
	 */
	private String phone;

	/**
	 * 邮箱
	 */
	private String email;

	/**
	 * 用户状态
	 0-正常
	 */
	private Integer userStatus;

	/**
	 * 创建日期
	 */
	private Date createTime;

	/**
	 * 更新时间
	 */
	private Date updateTime;

	/**
	 * 角色 0-管理员  1-普通用户
	 */
	private Integer userRole;

	/**
	 * 是否删除
	 0-否
	 */
	@TableLogic
	private Byte isDelete;

	@TableField(exist = false)
	private static final long serialVersionUID = 1L;
}


package com.xsbk.core.model.user;

import java.util.Date;

import lombok.Data;
import lombok.ToString;

/**
 * 角色表
 * @author chrilwe
 *
 */
@Data
@ToString
public class Role {
	private int id;
	private String name;
	private String roleCode;
	private Date createTime;
	private Date updateTime;
	private int status;
}

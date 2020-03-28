package com.xsbk.core.model.user;

import java.util.Date;

import lombok.Data;
import lombok.ToString;

/**
 * 权限表
 * @author chrilwe
 *
 */
@Data
@ToString
public class Permission {
	private int id;
	private String name;
	private String permissionCode;
	private int grade;//等级
	private int pid;//父类id
	private Date createTime;
	private String status;
	private int isLeaf;//是否为叶子节点
	private String url;//访问地址
}

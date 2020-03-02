package com.xsbk.core.model.user;

import java.util.Date;

import lombok.Data;
import lombok.ToString;

/**
 * 用户信息
 * @author chrilwe
 *
 */
@Data
@ToString
public class User {
	private int id;
	private String accountNo;//账号
	private String password;//密码
	private String nickName;//昵称
	private String status;//状态：1.正常 2.异常
	private String gender;//性别
	private int grade;//用户等级
	private Date createTime;
	private Date updateTime;
	private String email;//用户邮箱
	private String phoneNo;//手机号
	private int maxFrends;//最大好友数量
	private String userPic;
	private int articleNum;//发布文章数量
	private int fensiNum;//粉丝数量
	private int noticeNum;//关注数量
}

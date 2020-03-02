package com.xsbk.core.model.user;

import java.util.Date;

import lombok.Data;
import lombok.ToString;

/**
 * 用户粉丝表
 * @author chrilwe
 *
 */
@Data
@ToString
public class UserFensi {
	private int id;
	private int userId;
	private int fensiId;
	private Date addTime;//粉丝添加时间
}

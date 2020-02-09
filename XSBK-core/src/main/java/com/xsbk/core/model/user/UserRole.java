package com.xsbk.core.model.user;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class UserRole {
	private int id;
	private int roleId;
	private int userId;
}

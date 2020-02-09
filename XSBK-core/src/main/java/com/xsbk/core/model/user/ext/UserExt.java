package com.xsbk.core.model.user.ext;

import java.util.List;

import com.xsbk.core.model.user.Permission;
import com.xsbk.core.model.user.User;

import lombok.Data;
import lombok.ToString;

/**
 * 
 * @author Chrilwe
 *
 */
@Data
@ToString
public class UserExt extends User {
	private List<Permission> permission;
}

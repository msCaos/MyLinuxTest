package org.deepsl.hrm.dao.auth;

import org.deepsl.hrm.domain.auth.SysPermission;

import java.util.List;

/**
 * 

 */
public interface SysPermissionMapperCustom {
	
	//根据用户id查询菜单
	public List<SysPermission> findMenuListByUserId(String userid)throws Exception;
	//根据用户id查询权限id
	public String findPermissionByUserId(String userid)throws Exception;

	public List<SysPermission> findPermissionListByUserId(Integer id);

}

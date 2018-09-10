package org.deepsl.hrm.dao.auth;

import org.apache.ibatis.annotations.Param;
import org.deepsl.hrm.domain.auth.SysPermission;

import java.util.List;

public interface SysPermissionMapper {
	
	List<SysPermission> findPermission(String ids[]);
	
	


    int deleteByPrimaryKey(Long id);

    int insert(SysPermission record);

    int insertSelective(SysPermission record);


    SysPermission selectByPrimaryKey(Long id);



    int updateByPrimaryKeySelective(SysPermission record);

    int updateByPrimaryKey(SysPermission record);
}
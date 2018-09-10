package org.deepsl.hrm.dao.auth;

import org.deepsl.hrm.domain.auth.SysRole;

import java.util.List;

public interface SysRoleDao {

    List<SysRole> findSysRoleByUid(Integer uid);
}

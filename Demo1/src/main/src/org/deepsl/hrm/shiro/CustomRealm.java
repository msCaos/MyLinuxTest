package org.deepsl.hrm.shiro;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.deepsl.hrm.domain.User;
import org.deepsl.hrm.domain.auth.SysPermission;
import org.deepsl.hrm.service.HrmService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class CustomRealm extends AuthorizingRealm {
    @Autowired
    HrmService hrmService;

    @Override
    public String getName() {
        return "CustomRealm";
    }

    //认证的时候，需要我们去调用自己的realm，返回用户认证的信息，从数据库里返回
    //这里只是返回当前用户的认证信息
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {

        // 注意这里的principal是用户名
        String principal = (String) authenticationToken.getPrincipal();
        System.out.println("principal="+principal);
        User user =  hrmService.findUserByLogininName(principal);
        if (user==null){
            return null;
        }

          //参数一也就是身份信息，会被放入到Session域里。
        SimpleAuthenticationInfo simpleAuthenticationInfo=new SimpleAuthenticationInfo(
                user,
                user.getPassword(),
                getName()
        );

        return simpleAuthenticationInfo;
    }

    //授权
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {

        SimpleAuthorizationInfo authorizationInfo =new SimpleAuthorizationInfo();
        //根据用户信息去查询该用户的授权信息，
        User user = (User) principalCollection.getPrimaryPrincipal();
        List<SysPermission> premissions =  hrmService.getUserPermission(user.getId());
        //授权信息
        for (SysPermission premission:premissions) {
            authorizationInfo.addStringPermission(premission.getPercode());
        }
        authorizationInfo.addRole("");
        System.out.println("primaryPrincipal="+user);
        return authorizationInfo;
    }

    //缓存
    public void  cleanCache(){
        PrincipalCollection principals = SecurityUtils.getSubject().getPrincipals();
        super.clearCache(principals);
    }


}

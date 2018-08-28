package com.cdkj.schedule.shiro;

import com.cdkj.model.system.pojo.SysUser;
import com.cdkj.schedule.system.dao.SysUserMapper;
import com.cdkj.schedule.util.ShiroUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.HashSet;
import java.util.Set;

@Component
public class UserRealm extends AuthorizingRealm {
    @Resource
    private SysUserMapper sysUserMapper;

    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection arg0) {
        String userId = ShiroUtils.getUserId();
        /**
         * 获取权限
         */
        Set<String> perms = new HashSet<>(5);
        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
        info.setStringPermissions(perms);

        /**
         * 获取角色
         */
        Set<String> roleSet = new HashSet<>();
        info.setRoles(roleSet);
        return info;
    }

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        String username = (String) token.getPrincipal();

        String password = new String((char[]) token.getCredentials());
        // 查询用户信息
        SysUser resultUser = sysUserMapper.selectByUsername(username);

        // 账号不存在
        if (resultUser == null) {
            throw new UnknownAccountException("账号或密码不正确");
        }
        // 密码错误
        if (!password.equals(resultUser.getPassword())) {
            throw new IncorrectCredentialsException("账号或密码不正确");
        }
        // 账号锁定
        if (resultUser.getStatus() == 0) {
            throw new LockedAccountException("账号已被锁定,请联系管理员");
        }
        return new SimpleAuthenticationInfo(resultUser, password, getName());
    }

}

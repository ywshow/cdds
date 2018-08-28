package com.cdkj.manager.shiro;

import com.cdkj.manager.system.dao.SysUserMapper;
import com.cdkj.manager.system.service.api.SysPermissionService;
import com.cdkj.manager.system.service.api.SysRoleService;
import com.cdkj.model.system.pojo.SysRole;
import com.cdkj.model.system.pojo.SysUser;
import com.cdkj.manager.util.ShiroUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Component
public class UserRealm extends AuthorizingRealm {
    @Resource
    private SysUserMapper sysUserMapper;

    @Resource
    private SysPermissionService sysPermissionService;

    @Resource
    private SysRoleService sysRoleService;

    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection arg0) {
        String userId = ShiroUtils.getUserId();
        /**
         * 获取权限
         */
        Set<String> perms = sysPermissionService.listPermission(userId);
        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
        info.setStringPermissions(perms);

        /**
         * 获取角色
         */
        List<SysRole> roleList = sysRoleService.selectByUserId(userId);
        Set<String> roleSet = new HashSet<>();
        roleList.stream().forEach(item -> roleSet.add(item.getRoleName()));
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

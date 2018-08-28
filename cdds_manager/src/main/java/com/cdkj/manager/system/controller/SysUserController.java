/**
 * project name:saas
 * file name:SysUserController
 * package name:com.cdkj.system.controller
 * date:2018/3/16 16:46
 * author:WenJunChi
 * Copyright (c) CD Technology Co.,Ltd. All rights reserved.
 */
package com.cdkj.manager.system.controller;

import com.cdkj.common.base.controller.BaseController;
import com.cdkj.common.constant.Constant;
import com.cdkj.common.exception.CustException;
import com.cdkj.manager.file.service.api.FileService;
import com.cdkj.manager.system.service.api.SysDeptService;
import com.cdkj.manager.system.service.api.SysRoleService;
import com.cdkj.manager.system.service.api.SysUserService;
import com.cdkj.model.system.pojo.SysDept;
import com.cdkj.model.system.pojo.SysRole;
import com.cdkj.model.system.pojo.SysUser;
import com.cdkj.util.JsonUtils;
import org.apache.commons.collections.CollectionUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.List;

/**
 * description: 用户管理
 * date: 2018/3/16 16:46
 *
 * @author WenJunChi
 * @version 1.0
 * @since JDK 1.8
 */
@Controller
@RequestMapping("/sys/user")
public class SysUserController extends BaseController {
    private final String prefix = "page/system/user";
    @Resource
    private SysUserService sysUserService;
    @Resource
    private SysDeptService sysDeptService;
    @Resource
    private SysRoleService sysRoleService;

    @Resource
    private FileService fileService;

    /**
     * 用于页面跳转
     *
     * @param model
     * @return 跳转的页面List页面
     */
    @RequiresPermissions("sys:user:user")
    @GetMapping()
    public String user(Model model) {
        return prefix + "/user";
    }

    /**
     * @param model
     * @return 新增时跳转到新增页面
     */
    @RequiresPermissions("sys:user:add")
    @GetMapping("/add/{deptId}")
    String add(Model model, @PathVariable String deptId) {
        if (null == deptId) {
            return null;
        }

        SysRole sysRole = new SysRole();
        sysRole.setEnabled((short) 1);
        model.addAttribute("roles", sysRoleService.listByPrimaryKeySelective(sysRole));
        model.addAttribute("sysDept", sysDeptService.selectByPrimaryKey(deptId));
        return prefix + "/add";
    }


    /**
     * @param model
     * @return 编辑时跳转到编辑页面
     */
    @RequiresPermissions("sys:user:edit")
    @GetMapping("/edit/{userId}")
    String edit(Model model, @PathVariable String userId) {
        SysRole sysRole = new SysRole();
        sysRole.setEnabled((short) 1);
        /**
         * 查询：系统所有角色信息
         */
        List<SysRole> sysRoles = sysRoleService.listByPrimaryKeySelective(sysRole);
        /**
         *   根据用户Id,查询其角色
         */
        List<SysRole> userSysRoles = sysRoleService.selectByUserId(userId);
        setRoleSign(sysRoles, userSysRoles);
        model.addAttribute("roles", sysRoles);
        SysUser sysUser = sysUserService.selectByPrimaryKey(userId);
        model.addAttribute("sysUser", sysUser);
        List<SysDept> sysDepts = sysDeptService.selectByUserId(userId);
        model.addAttribute("sysDept", CollectionUtils.isEmpty(sysDepts) ? null : sysDepts.get(0));
        return prefix + "/edit";
    }

    /**
     * @param nickName 姓名
     * @param limit    分页(每页显示多少条)
     * @param offset   (分页,从第几条开始)
     * @param deptId   (部门id)
     * @return
     */
    @RequiresPermissions("sys:user:user")
    @RequestMapping("/list")
    @ResponseBody
    public String list(String nickName, int limit, int offset, String deptId) {
        SysUser sysUser = new SysUser();
        SysDept sysDept = new SysDept();
        sysUser.setNickName((StringUtils.isEmpty(nickName)) ? null : nickName.trim());
        if(!Constant.TREE_ROOT_ID.equals(deptId) && !StringUtils.isEmpty(deptId)){
            sysDept.setId(deptId.trim());
        }
        sysUser.setSysDept(sysDept);
        sysUser.setEnabled((short) 1);
        return JsonUtils.res(sysUserService.listByPrimaryKeySelective(sysUser));
    }

    /**
     * 校验用户名是否存在
     *
     * @param username
     * @return ture 不存在，false存在
     */
    @RequiresPermissions("sys:user:user")
    @RequestMapping("/check")
    @ResponseBody
    public boolean check(String username) {
        SysUser sysUser = sysUserService.selectByUsername(username);
        if (sysUser == null) {
            return true;
        } else {
            return false;
        }

    }

    @RequiresPermissions("sys:dept:add")
    @PostMapping("/save")
    @ResponseBody
    public String save(SysUser sysUser) {
        try {
            return JsonUtils.resCorU(sysUserService.merge(sysUser));
        } catch (CustException ce) {
            logger.error("SysUserController.save()方法异常!error={}", ce);
            return JsonUtils.resFailed(ce.getMsg());
        } catch (Exception e) {
            logger.error("SysUserController.save()方法系统异常!error={}", e);
            return JsonUtils.resFailed(101, 20002, "02", "系统异常");
        }
    }

    @PostMapping("/open/update/updateCustomerWhereParamNotNull")
    @ResponseBody
    public String updateCustomerWhereParamNotNull(HttpServletRequest request, @RequestParam("pic") MultipartFile pic) {
        try {
            String id = request.getParameter("sysUser");
            String nickName = request.getParameter("nickName");
            String memo = request.getParameter("memo");

            String result = fileService.upload(pic, "group1", "0");

            SysUser sysUser = new SysUser();
            sysUser.setId(id);
            sysUser.setNickName(nickName);
            sysUser.setMemo(memo);
            sysUser.setPic(result);

//            SysUser sysUser = JsonUtils.toObject(param, SysUser.class);
            return JsonUtils.resCorU(sysUserService.updateCustomerWhereParamNotNull(sysUser));
        } catch (CustException ce) {
            logger.error("SysUserController.updateCustomerWhereParamNotNull()方法异常!error={}", ce);
            return JsonUtils.resFailed(ce.getMsg());
        } catch (Exception e) {
            logger.error("SysUserController.updateCustomerWhereParamNotNull()方法系统异常!error={}", e);
            return JsonUtils.resFailed(101, 20002, "02", "系统异常");
        }
    }

    @RequiresPermissions("sys:user:remove")
    @PostMapping("/batchRemove")
    @ResponseBody()
    public String batchRemove(String ids) {
        try {
            if (StringUtils.isEmpty(ids)) {
                return JsonUtils.resFailed(102, 20002, "01", "参数为空");
            }
            return JsonUtils.resCorU(this.sysUserService.batchRemove(Arrays.asList(ids.split(","))));
        } catch (CustException ce) {
            logger.error("SysUserController.remove()方法异常!error={}", ce);
            return JsonUtils.resFailed(ce.getMsg());
        } catch (Exception e) {
            logger.error("SysUserController.remove()方法系统异常!error={}", e);
            return JsonUtils.resFailed(102, 20002, "02", "系统异常");
        }

    }

    /**
     * 设置已选角色
     *
     * @param sysRoles
     * @param userSysRoles
     */
    private void setRoleSign(List<SysRole> sysRoles, List<SysRole> userSysRoles) {
        for (SysRole userSysrole : userSysRoles) {
            for (int i = 0; i < sysRoles.size(); i++) {
                if (userSysrole.getId().equals(sysRoles.get(i).getId())) {
                    sysRoles.get(i).setRoleSign(true);
                }
            }
        }

    }

    /**
     * @param userId
     * @param model
     * @return 重置密码跳转到页面
     */
    @RequiresPermissions("sys:user:edit")
    @GetMapping("/editPwd/{userId}")
    String editPwd(@PathVariable String userId, Model model) {
        model.addAttribute("userId", userId);
        return prefix + "/editPwd";
    }

    @RequiresPermissions("sys:dept:edit")
    @PostMapping("/updatePwd")
    @ResponseBody
    public String updatePwd(SysUser sysUser) {
        try {
            return JsonUtils.resCorU(sysUserService.updatePwd(sysUser));
        } catch (CustException ce) {
            logger.error("SysUserController.updatePwd()方法异常!error={}", ce);
            return JsonUtils.resFailed(ce.getMsg());
        } catch (Exception e) {
            logger.error("SysUserController.updatePwd()方法系统异常!error={}", e);
            return JsonUtils.resFailed(101, 20002, "02", "系统异常");
        }
    }
}
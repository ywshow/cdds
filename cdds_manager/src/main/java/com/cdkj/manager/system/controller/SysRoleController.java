/**
 * project name:saas
 * file name:SysRoleController
 * package name:com.cdkj.system.controller
 * date:2018/3/2 上午10:46
 * author:bovine
 * Copyright (c) CD Technology Co.,Ltd. All rights reserved.
 */
package com.cdkj.manager.system.controller;

import com.cdkj.common.base.controller.BaseController;
import com.cdkj.common.exception.CustException;
import com.cdkj.manager.system.service.api.SysRoleService;
import com.cdkj.model.system.pojo.SysRole;
import com.cdkj.util.JsonUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * description: 角色管理 <br>
 * date: 2018/3/2 上午10:46
 *
 * @author bovine
 * @version 1.0
 * @since JDK 1.8
 */
@Controller
@RequestMapping("/sys/role")
public class SysRoleController extends BaseController {
    private String prefix = "page/system/role";

    @Autowired
    private SysRoleService sysRoleService;

    @RequiresPermissions("sys:role:role")
    @GetMapping()
    public String role(Model model) {
        return prefix + "/role";
    }

    @RequiresPermissions("sys:role:role")
    @RequestMapping("/list")
    @ResponseBody
    public List<SysRole> list() {
        SysRole role = new SysRole();
        role.setEnabled((short) 1);
        return sysRoleService.listByPrimaryKeySelective(role);
    }

    @RequiresPermissions("sys:role:add")
    @GetMapping("/add")
    String add() {
        return prefix + "/add";
    }

    @RequiresPermissions("sys:role:add")
    @PostMapping("/save")
    @ResponseBody()
    public String save(SysRole sysRole) {
        try {
            return JsonUtils.res(sysRoleService.insertWithPermission(sysRole));
        } catch (CustException ce) {
            logger.error("SysRoleController.save()方法异常!error={}", ce);
            return JsonUtils.resFailed(ce.getMsg());
        } catch (Exception e) {
            logger.error("SysRoleController.save()方法系统异常!error={}", e);
            return JsonUtils.resFailed(211, 20002, "02", "系统异常");
        }
    }

    @RequiresPermissions("sys:role:edit")
    @GetMapping("/edit/{id}")
    public String edit(@PathVariable("id") String id, Model model) {
        SysRole role = sysRoleService.selectByPrimaryKey(id);
        model.addAttribute("role", role);
        return prefix + "/edit";
    }

    @RequiresPermissions("sys:role:edit")
    @PostMapping("/update")
    @ResponseBody()
    public String update(SysRole sysRole) {
        try {
            return JsonUtils.res(sysRoleService.updateWithPermission(sysRole));
        } catch (CustException ce) {
            logger.error("SysRoleController.update()方法异常!error={}", ce);
            return JsonUtils.resFailed(ce.getMsg());
        } catch (Exception e) {
            logger.error("SysRoleController.update()方法系统异常!error={}", e);
            return JsonUtils.resFailed(211, 20002, "02", "系统异常");
        }
    }

    @RequiresPermissions("sys:role:remove")
    @PostMapping("/remove")
    @ResponseBody()
    public String remove(String id) {
        try {
            if (StringUtils.isEmpty(id)) {
                return JsonUtils.resFailed(211, 20002, "01", "参数为空");
            }
            return JsonUtils.res(sysRoleService.remove(id));
        } catch (CustException ce) {
            logger.error("PermissionController.remove()方法异常!update={}", ce);
            return JsonUtils.resFailed(ce.getMsg());
        } catch (Exception e) {
            logger.error("PermissionController.remove()方法系统异常!update={}", e);
            return JsonUtils.resFailed(211, 20002, "02", "系统异常");
        }
    }
}
/**
 * project name:saas
 * file name:PermissionController
 * package name:com.cdkj.system.controller
 * date:2018/3/1 下午2:25
 * author:bovine
 * Copyright (c) CD Technology Co.,Ltd. All rights reserved.
 */
package com.cdkj.manager.system.controller;

import com.cdkj.common.base.controller.BaseController;
import com.cdkj.common.exception.CustException;
import com.cdkj.manager.util.ShiroUtils;
import com.cdkj.manager.system.service.api.SysPermissionService;
import com.cdkj.model.system.pojo.SysPermission;
import com.cdkj.util.*;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * description: 权限管理 <br>
 * date: 2018/3/1 下午2:25
 *
 * @author bovine
 * @version 1.0
 * @since JDK 1.8
 */
@Controller
@RequestMapping("/sys/permission/")
public class SysPermissionController extends BaseController {
    @Autowired
    SysPermissionService sysPermissionService;
    private String prefix = "page/system/permission";

    @RequiresPermissions("sys:permission:index")
    @GetMapping("index")
    public String permission(Model model) {
        return prefix + "/permission";
    }

    @RequiresPermissions("sys:permission:list")
    @RequestMapping("list")
    @ResponseBody
    public List<SysPermission> list() {
        SysPermission sysPermission = new SysPermission();
        sysPermission.setEnabled((short) 1);
        return sysPermissionService.listByPrimaryKeySelective(sysPermission);
    }

    @RequiresPermissions("sys:permission:add")
    @GetMapping("add/{parentId}")
    String add(Model model, @PathVariable String parentId) {
        model.addAttribute("parentId", parentId);
        if ("0".equals(parentId)) {
            model.addAttribute("perName", "根目录");
        } else {
            model.addAttribute("perName", sysPermissionService.selectByPrimaryKey(parentId).getPerName());
        }
        return prefix + "/add";
    }

    @RequiresPermissions("sys:permission:edit")
    @GetMapping("edit/{id}")
    String edit(Model model, @PathVariable("id") String id) {
        SysPermission sysPermission = sysPermissionService.selectByPrimaryKey(id);
        String parentId = sysPermission.getParentId();
        model.addAttribute("parentId", sysPermission.getParentId());
        if ("0".equals(parentId)) {
            model.addAttribute("parentName", "根目录");
        } else {
            model.addAttribute("parentName", sysPermissionService.selectByPrimaryKey(parentId).getPerName());
        }
        model.addAttribute("menu", sysPermissionService.selectByPrimaryKey(id));
        return prefix + "/edit";
    }

    @RequiresPermissions("sys:permission:add")
    @PostMapping("save")
    @ResponseBody
    public String save(SysPermission sysPermission) {
        try {
            if (StringUtils.isEmpty(sysPermission.getId())) {
                sysPermission.setId(UUIDGenerator.randomUUID());
                sysPermission.setSysAccount(ShiroUtils.getUser().getSysAccount());
                sysPermission.setCreateBy(ShiroUtils.getUser().getUsername());
                sysPermission.setCreateDt(DateUtil.getNow());
            }
            return JsonUtils.resCorU(sysPermissionService.insertSelective(sysPermission));
        } catch (CustException ce) {
            logger.error("PermissionController.save()方法异常!error={}", ce);
            return JsonUtils.resFailed(ce.getMsg());
        } catch (Exception e) {
            logger.error("PermissionController.save()方法系统异常!error={}", e);
            return JsonUtils.resFailed(101, 20002, "02", "系统异常");
        }
    }

    @RequiresPermissions("sys:permission:edit")
    @PostMapping("update")
    @ResponseBody
    public String update(SysPermission sysPermission) {
        try {
            return JsonUtils.resCorU(sysPermissionService.updateByPrimaryKeySelective(sysPermission));
        } catch (CustException ce) {
            logger.error("PermissionController.update()方法异常!update={}", ce);
            return JsonUtils.resFailed(ce.getMsg());
        } catch (Exception e) {
            logger.error("PermissionController.update()方法系统异常!update={}", e);
            return JsonUtils.resFailed(101, 20002, "02", "系统异常");
        }

    }

    @RequiresPermissions("sys:permission:remove")
    @PostMapping("remove")
    @ResponseBody
    public String remove(String id) {
        SysPermission sysPermission = sysPermissionService.selectByPrimaryKey(id);
        sysPermission.setEnabled((short) 0);
        try {
            return JsonUtils.resCorU(sysPermissionService.updateByPrimaryKeySelective(sysPermission));
        } catch (CustException ce) {
            logger.error("PermissionController.remove()方法异常!update={}", ce);
            return JsonUtils.resFailed(ce.getMsg());
        } catch (Exception e) {
            logger.error("PermissionController.remove()方法系统异常!update={}", e);
            return JsonUtils.resFailed(101, 20002, "02", "系统异常");
        }
    }

    @GetMapping("tree")
    @ResponseBody
    public Tree<SysPermission> tree() {
        return sysPermissionService.permissionTree();
    }

    @GetMapping("tree/{roleId}")
    @ResponseBody
    public Tree<SysPermission> tree(@PathVariable("roleId") String roleId) {

        return sysPermissionService.rolePermissionTree(roleId);
    }
}
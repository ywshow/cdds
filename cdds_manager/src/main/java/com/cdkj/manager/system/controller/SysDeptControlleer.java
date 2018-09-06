/**
 * project name:saas
 * file name:DeptControlleer
 * package name:com.cdkj.system.controller
 * date:2018/3/16 9:43
 * author:WenJunChi
 * Copyright (c) CD Technology Co.,Ltd. All rights reserved.
 */
package com.cdkj.manager.system.controller;

import com.cdkj.common.base.controller.BaseController;
import com.cdkj.common.constant.Constant;
import com.cdkj.common.exception.CustException;
import com.cdkj.manager.system.service.api.SysDeptService;
import com.cdkj.model.system.pojo.SysDept;
import com.cdkj.util.JsonUtils;
import com.cdkj.util.Tree;
import com.cdkj.ztree.TreeNode;
import com.cdkj.ztree.TreeNodeInit;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * description: 组织管理 <br>
 * date: 2018/3/16 9:43
 *
 * @author WenJunChi
 * @version 1.0
 * @since JDK 1.8
 */
@Controller
@RequestMapping("/sys/dept")
public class SysDeptControlleer extends BaseController {
    @Resource
    private SysDeptService sysDeptService;
    private final String prefix = "page/system/dept";

    /**
     * 用于页面跳转
     *
     * @param model
     * @return 跳转的页面
     */
    @RequiresPermissions("sys:dept:dept")
    @GetMapping()
    public String dept(Model model) {
        return prefix + "/dept";
    }

    /**
     * @param model
     * @param parentId 组织父id
     * @return 新增时跳转到新增页面
     */
    @RequiresPermissions("sys:dept:add")
    @GetMapping("/add/{parentId}")
    String add(Model model, @PathVariable String parentId) {
        model.addAttribute("parentId", parentId);
        model.addAttribute("parentName", getDeptNameByPrimaryKey(parentId));
        return prefix + "/add";
    }

    /**
     * 显示列表
     *
     * @return 组织列表集合
     */
    @RequiresPermissions("sys:dept:dept")
    @RequestMapping("/list")
    @ResponseBody
    public List<SysDept> list() {
        SysDept sysDept = new SysDept();
        sysDept.setEnabled((short) 1);
        return sysDeptService.listByPrimaryKeySelective(sysDept);
    }

    @RequiresPermissions("sys:dept:edit")
    @GetMapping("/edit/{id}")
    String edit(Model model, @PathVariable("id") String id) {
        SysDept sysDept = sysDeptService.selectByPrimaryKey(id);
        String parentId = sysDept.getParentId();
        model.addAttribute("parentId", parentId);
        model.addAttribute("parentName", getDeptNameByPrimaryKey(parentId));
        model.addAttribute("sysDept", sysDeptService.selectByPrimaryKey(id));
        return prefix + "/edit";
    }

    @RequiresPermissions("sys:dept:add")
    @PostMapping("/save")
    @ResponseBody
    public String save(SysDept sysDept) {
        try {
            return JsonUtils.res(sysDeptService.merge(sysDept));
        } catch (CustException ce) {
            logger.error("DeptControlleer.save()方法异常!error={}", ce);
            return JsonUtils.resFailed(ce.getMsg());
        } catch (Exception e) {
            logger.error("DeptControlleer.save()方法系统异常!error={}", e);
            return JsonUtils.resFailed(101, 20002, "02", "系统异常");
        }
    }

    @RequiresPermissions("sys:dept:insert")
    @PostMapping("/insertTree")
    @ResponseBody
    public String insertTree(SysDept sysDept) {
        try {
            sysDeptService.merge(sysDept);
            TreeNodeInit init = new TreeNodeInit();
            List<SysDept> list = new ArrayList<>();
            list.add(sysDept);
            String[] property = {"deptName"};
            return JsonUtils.res(init.multipleTree(list, null, property, null));
        } catch (CustException ce) {
            logger.error("DeptControlleer.save()方法异常!error={}", ce);
            return JsonUtils.resFailed(ce.getMsg());
        } catch (Exception e) {
            logger.error("DeptControlleer.save()方法系统异常!error={}", e);
            return JsonUtils.resFailed(101, 20002, "02", "系统异常");
        }
    }

    @GetMapping("/tree")
    @ResponseBody
    public String tree() {
        try {
            SysDept sysDept = new SysDept();
            sysDept.setEnabled((short) 1);
            sysDept.setParentId(Constant.TREE_ROOT_ID);
            List<TreeNode> list = sysDeptService.sysDeptTree(sysDept);
            return JsonUtils.res(list);
        } catch (CustException ce) {
            logger.error("DeptControlleer.tree()方法异常!error={}", ce);
            return JsonUtils.resFailed(ce.getMsg());
        } catch (Exception e) {
            logger.error("DeptControlleer.tree()方法系统异常!error={}", e);
            return JsonUtils.resFailed(101, 20002, "03", "系统异常");
        }
    }

    @GetMapping("/selectChildrenTree/{parentId}")
    @ResponseBody
    public String selectChildrenTree(@PathVariable(value = "parentId") String parentId) {
        try {
            List<TreeNode> list = sysDeptService.selectChildrenTree(parentId);
            return JsonUtils.res(list);
        } catch (CustException ce) {
            logger.error("DeptControlleer.tree()方法异常!error={}", ce);
            return JsonUtils.resFailed(ce.getMsg());
        } catch (Exception e) {
            logger.error("DeptControlleer.tree()方法系统异常!error={}", e);
            return JsonUtils.resFailed(101, 20002, "04", "系统异常");
        }
    }

    @RequiresPermissions("sys:dept:remove")
    @PostMapping("/remove")
    @ResponseBody
    public String remove(String id) {
        SysDept sysDept = sysDeptService.selectByPrimaryKey(id);
        sysDept.setEnabled((short) 0);
        try {
            return JsonUtils.res(sysDeptService.updateByPrimaryKeySelective(sysDept));
        } catch (CustException ce) {
            logger.error("DeptControlleer.remove()方法异常!update={}", ce);
            return JsonUtils.resFailed(ce.getMsg());
        } catch (Exception e) {
            logger.error("DeptControlleer.remove()方法系统异常!update={}", e);
            return JsonUtils.resFailed(101, 20002, "05", "系统异常");
        }
    }

    /**
     * @param id
     * @return 通过Id返回组织名称
     */

    private String getDeptNameByPrimaryKey(String id) {
        if (Constant.TREE_ROOT_ID.equals(id)) {
            return "顶级组织";
        }
        SysDept sysDept = sysDeptService.selectByPrimaryKey(id);
        if (null == sysDept) {
            return null;
        }
        return sysDept.getDeptName();
    }
}
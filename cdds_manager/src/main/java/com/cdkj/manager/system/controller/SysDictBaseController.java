/**
 * project name:saas
 * file name:SysDictBaseController
 * package name:com.cdkj.system.controller
 * date:2018-03-30 15:18
 * author:yw
 * Copyright (c) CD Technology Co.,Ltd. All rights reserved.
 */
package com.cdkj.manager.system.controller;

import com.cdkj.common.base.controller.BaseController;
import com.cdkj.common.constant.Constant;
import com.cdkj.common.exception.CustException;
import com.cdkj.manager.system.service.api.SysDictBaseService;
import com.cdkj.model.system.pojo.SysDept;
import com.cdkj.model.system.pojo.SysDictBase;
import com.cdkj.util.JsonUtils;
import com.cdkj.util.StringUtil;
import com.cdkj.util.Tree;
import com.cdkj.ztree.TreeNode;
import com.cdkj.ztree.TreeNodeInit;
import org.apache.commons.collections.CollectionUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * description: //数据字典基础数据控制器 <br>
 * date: 2018-03-30 15:18
 *
 * @author yw
 * @version 1.0
 * @since JDK 1.8
 */
@Controller
@RequestMapping("/sys/dictbase/open/")
public class SysDictBaseController extends BaseController<SysDictBase> {

    @Resource
    private SysDictBaseService sysDictBaseService;

    private final String prefix = "page/system/dict/base/";

    @RequiresPermissions("sys:dictbase:index")
    @GetMapping("index")
    public String account(Model model) {
        return prefix + "index";
    }

    /**
     * 通过组代码获得数据字典基础信息
     *
     * @param groupCode 组代码
     * @return 数据字典主表信息
     */
    @GetMapping("get/{groupCode}")
    @ResponseBody
    public String get(@PathVariable(value = "groupCode") String groupCode) {
        if (StringUtil.isEmpty(groupCode)) {
            return JsonUtils.resFailed(208, 20001, "01", "参数为空");
        }
        List<SysDictBase> sysDictBaseList = sysDictBaseService.selectByGroupCode(groupCode);
        if (sysDictBaseList == null) {
            return JsonUtils.resFailed(208, 20002, "02", "系统错误");
        }
        return JsonUtils.res(CollectionUtils.isEmpty(sysDictBaseList) ? null : sysDictBaseList);
    }

    /**
     * 通过组代码新增数据字典基础信息
     *
     * @param sysDictBase 对象信息
     * @return 新增条数
     */
    @RequiresPermissions("sys:dictbase:insert")
    @PostMapping("insert/{groupCode}")
    @ResponseBody
    public String insert(SysDictBase sysDictBase) {
        try {
            sysDictBaseService.insertOrUpdateSysDictBase(sysDictBase);
            TreeNodeInit init = new TreeNodeInit();
            List<SysDictBase> list = new ArrayList<>();
            list.add(sysDictBase);
            String[] property = {"groupName"};
            return JsonUtils.res(init.multipleTree(list, null, property, null));
        } catch (CustException ce) {
            logger.error("SysDictBaseController.insert()方法异常!error={}", ce);
            return JsonUtils.resFailed(ce.getMsg());
        } catch (Exception e) {
            logger.error("SysDictBaseController.insert()方法系统异常!error={}", e);
            return JsonUtils.resFailed(208, 20002, "04", "系统异常");
        }
    }

    /**
     * 通过组代码修改数据字典基础信息
     *
     * @param groupCode 组代码
     * @param groupName 组名称
     * @return 修改条数
     */
    @RequiresPermissions("sys:dictbase:update")
    @PostMapping("update/{id}")
    @ResponseBody
    public String update(@PathVariable(value = "id") String id, String groupCode, String groupName) {
        try {
            SysDictBase sysDictBase = new SysDictBase();
            sysDictBase.setId(id);
            sysDictBase.setGroupName(groupName);
            sysDictBase.setGroupCode(groupCode);
            int insertRest = sysDictBaseService.insertOrUpdateSysDictBase(sysDictBase);
            return JsonUtils.res(insertRest);
        } catch (CustException ce) {
            logger.error("SysDictBaseController.update()方法异常!error={}", ce);
            return JsonUtils.resFailed(ce.getMsg());
        } catch (Exception e) {
            logger.error("SysDictBaseController.update()方法系统异常!error={}", e);
            return JsonUtils.resFailed(208, 20002, "06", "系统异常");
        }
    }

    /**
     * 通过主键修改数据字典基础信息
     *
     * @param id
     * @return 删除条数
     */
    @RequiresPermissions("sys:dictbase:delete")
    @PostMapping("delete/{id}")
    @ResponseBody
    public String delete(@PathVariable(value = "id") String id) {
        try {
            SysDictBase sysDictBase = new SysDictBase();
            sysDictBase.setId(id);
            sysDictBase.setEnabled(Constant.ENABLED_N);
            int insertRest = sysDictBaseService.updateByPrimaryKeySelective(sysDictBase);
            return JsonUtils.res(insertRest);
        } catch (CustException ce) {
            logger.error("SysDictBaseController.update()方法异常!error={}", ce);
            return JsonUtils.resFailed(ce.getMsg());
        } catch (Exception e) {
            logger.error("SysDictBaseController.update()方法系统异常!error={}", e);
            return JsonUtils.resFailed(208, 20002, "07", "系统异常");
        }
    }

    /**
     * 获取字典树
     *
     * @return 获取字典树
     */
    @GetMapping("getDictBaseTree")
    @ResponseBody
    public String getDictBaseTree() {
        try {
            SysDictBase sysDictBase = new SysDictBase();
            sysDictBase.setEnabled((short) 1);
            List<TreeNode> list = sysDictBaseService.getDictBaseTree(sysDictBase);
            return JsonUtils.res(list);
        } catch (CustException ce) {
            logger.error("SysDictBaseController.getDictBaseTree()方法异常!error={}", ce);
            return JsonUtils.resFailed(ce.getMsg());
        } catch (Exception e) {
            logger.error("SysDictBaseController.getDictBaseTree()方法系统异常!error={}", e);
            return JsonUtils.resFailed(208, 20002, "08", "系统异常");
        }
    }

    /**
     * 查询不包含在表SysDict中的字典信息
     *
     * @return 获取字典树
     */
    @GetMapping("selectNotIn/{sysAccount}")
    @ResponseBody
    public String selectBySysAccountWhereNotInSysDict(@PathVariable(value = "sysAccount") String sysAccount) {
        try {
            List<TreeNode> list = sysDictBaseService.selectBySysAccountWhereNotInSysDict(sysAccount);
            return JsonUtils.res(list);
        } catch (CustException ce) {
            logger.error("SysDictBaseController.selectBySysAccountWhereNotInSysDict()方法异常!error={}", ce);
            return JsonUtils.resFailed(ce.getMsg());
        } catch (Exception e) {
            logger.error("SysDictBaseController.selectBySysAccountWhereNotInSysDict()方法系统异常!error={}", e);
            return JsonUtils.resFailed(208, 20002, "09", "系统异常");
        }
    }
}

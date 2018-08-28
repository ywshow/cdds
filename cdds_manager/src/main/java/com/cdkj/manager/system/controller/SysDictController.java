/**
 * project name:saas
 * file name:SysDictController
 * package name:com.cdkj.system.controller
 * date:2018-03-30 10:16
 * author:yw
 * Copyright (c) CD Technology Co.,Ltd. All rights reserved.
 */
package com.cdkj.manager.system.controller;

import com.cdkj.common.base.controller.BaseController;
import com.cdkj.common.exception.CustException;
import com.cdkj.constant.ErrorCode;
import com.cdkj.constant.RedisKeys;
import com.cdkj.manager.system.service.api.SysDictBaseService;
import com.cdkj.manager.system.service.api.SysDictService;
import com.cdkj.manager.util.ShiroUtils;
import com.cdkj.model.system.pojo.SysDict;
import com.cdkj.util.JsonUtils;
import com.cdkj.util.PageDTO;
import com.cdkj.util.ResultInfo;
import com.cdkj.util.StringUtil;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * description: 账套号对应的数据字典 <br>
 * date: 2018-03-30 10:16
 *
 * @author yw
 * @version 1.0
 * @since JDK 1.8
 */
@Controller
@RequestMapping("/sys/dict/open/")
public class SysDictController extends BaseController<SysDict> {
    @Resource
    private SysDictService sysDictService;

    @Resource
    private SysDictBaseService sysDictBaseService;

    private final String prefix = "page/system/dict/";

    /**
     * description: 首页 <br>
     *
     * @param model
     * @return java.lang.String
     */
    @RequiresPermissions("sys:dict:index")
    @GetMapping("index")
    public String account(Model model) {
        return prefix + "index";
    }

    /**
     * description: 新增页 <br>
     *
     * @param model
     * @return java.lang.String
     */
    @GetMapping("add")
    public String add(Model model) {
        model.addAttribute("sysAccount", ShiroUtils.getUser().getSysAccount());
        return prefix + "add";
    }

    /**
     * description: 删除页 <br>
     *
     * @param model
     * @return java.lang.String
     */
    @GetMapping("edit/{id}")
    public String edit(@PathVariable(value = "id") String id, Model model) {
        SysDict instance = sysDictService.selectByPrimaryKey(id);
        model.addAttribute("instance", instance);
        return prefix + "edit";
    }

    /**
     * 通过组+键获得数据字典信息
     *
     * @param sysAccount 帐套号
     * @param groupCode  组代码
     * @param paramKey   键代码
     * @return 数据字典信息
     */
    @GetMapping("get/{sysAccount}")
    @ResponseBody
    public String get(@PathVariable(value = "sysAccount") String sysAccount, String groupCode, String paramKey) {
        if (!StringUtil.areNotEmpty(sysAccount, groupCode, paramKey)) {
            return JsonUtils.resFailed(207, ErrorCode.ERROR_20001, "01", "参数为空");
        }
        String redisResultValue = redisClient.get(RedisKeys.SYS_DICT + sysAccount + "-" + groupCode + "-" + paramKey);
        if (!StringUtil.isEmpty(redisResultValue)) {
            return redisResultValue;
        }
        SysDict sysDictList = sysDictService.selectBySysAccountAndGroupCodeAndParamKey(sysAccount, groupCode, paramKey);
        if (sysDictList == null) {
            return JsonUtils.resFailed(207, ErrorCode.ERROR_20002, "02", "数据不存在");
        }
        return JsonUtils.res(sysDictList == null ? null : sysDictList);
    }

    /**
     * 通过组+键新增数据字典信息
     *
     * @param sysDict 账套信息
     * @return 新增条数
     */
    @PostMapping("insert")
    @ResponseBody
    public String insert(SysDict sysDict) {
        try {
            int insertRest = sysDictService.insertBySysDictObj(sysDict);
            return JsonUtils.res(insertRest);
        } catch (CustException ce) {
            logger.error("SysDictController.insert()方法异常!error={}", ce);
            return JsonUtils.resFailed(ce.getMsg());
        } catch (Exception e) {
            logger.error("SysDictController.insert()方法系统异常!error={}", e);
            return JsonUtils.resFailed(207, ErrorCode.ERROR_20002, "06", "系统异常");
        }
    }

    /**
     * 通过组+键修改数据字典信息
     *
     * @param sysDict 账套信息
     * @return 修改条数
     */
    @PostMapping("update")
    @ResponseBody
    public String update(SysDict sysDict) {
        try {
            int num = sysDictService.update(sysDict);
            return JsonUtils.res(num);
        } catch (CustException ce) {
            logger.error("SysDictController.update()方法异常!error={}", ce);
            return JsonUtils.resFailed(ce.getMsg());
        } catch (Exception e) {
            logger.error("SysDictController.update()方法系统异常!error={}", e);
            return JsonUtils.resFailed(207, ErrorCode.ERROR_20002, "10", "系统异常");
        }
    }

    /**
     * id逻辑删除
     *
     * @return 删除条数
     */
    @PostMapping("delete")
    @ResponseBody
    public String delete(String ids) {
        try {
            int deleteRest = sysDictService.deleteLogic(ids);
            return JsonUtils.res(deleteRest);
        } catch (CustException ce) {
            logger.error("SysDictController.delete()方法异常!error={}", ce);
            return JsonUtils.resFailed(ce.getMsg());
        } catch (Exception e) {
            logger.error("SysDictController.delete()方法系统异常!error={}", e);
            return JsonUtils.resFailed(207, ErrorCode.ERROR_20002, "13", "系统异常");
        }
    }

    /**
     * description: 列表 <br>
     *
     * @param pageDTO
     * @return java.lang.String
     */
    @GetMapping("selectSysDictList")
    @ResponseBody
    public String selectSysDictList(PageDTO<SysDict> pageDTO) {
        try {
            ResultInfo<SysDict> resultInfo = sysDictService.selectSysDictList(pageDTO);
            return JsonUtils.res(resultInfo);
        } catch (CustException ce) {
            logger.error("SysDictController.selectSysDictList()方法异常!error={}", ce);
            return JsonUtils.resFailed(ce.getMsg());
        } catch (Exception e) {
            logger.error("SysDictController.selectSysDictList()方法系统异常!error={}", e);
            return JsonUtils.resFailed(207, ErrorCode.ERROR_20002, "14", "系统异常");
        }
    }

    /**
     * description: 从SysDictBase获取记录保存 <br>
     *
     * @return java.lang.String
     */
    @RequiresPermissions("sys:dict:sync")
    @PostMapping("batchInsertFromDictBase")
    @ResponseBody
    public String batchInsertFromDictBase() {
        try {
            int num = sysDictService.batchInsertFromDictBase();
            return JsonUtils.res(num);
        } catch (CustException ce) {
            logger.error("SysDictController.batchInsertFromDictBase()方法异常!error={}", ce);
            return JsonUtils.resFailed(ce.getMsg());
        } catch (Exception e) {
            logger.error("SysDictController.batchInsertFromDictBase()方法系统异常!error={}", e);
            return JsonUtils.resFailed(207, ErrorCode.ERROR_20002, "15", "系统异常");
        }
    }
}
